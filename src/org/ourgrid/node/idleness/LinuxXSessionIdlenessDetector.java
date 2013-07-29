package org.ourgrid.node.idleness;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.ourgrid.node.util.NodeProperties;

public class LinuxXSessionIdlenessDetector implements Runnable, IdlenessChecker {

	private Date lastModification;
	private String xSessionIdlenessFile;
	private Long idlenessTime;
	private Boolean enabled;
	private Boolean isIdle;
	private List<IdlenessListener> listeners = new LinkedList<IdlenessListener>();
	
	public LinuxXSessionIdlenessDetector(Properties properties) {
		String enabledStr = properties.getProperty(NodeProperties.IDLENESS_ENABLED);
		this.enabled = Boolean.valueOf(enabledStr);
		if (enabled) {
			this.xSessionIdlenessFile = properties.getProperty(NodeProperties.IDLENESS_FILE);
			String idlenessTimeStr = properties.getProperty(NodeProperties.IDLENESS_TIME);
			this.idlenessTime = Long.valueOf(idlenessTimeStr) * 1000;
			this.isIdle = false;
		} else {
			this.isIdle = true;
		}
	}
	
	public void init() {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);
	}
	
	@SuppressWarnings("unchecked")
	private Date getLastInput() {
		File idlenessDetectorFile = new File(xSessionIdlenessFile);
		Date lastInput = null;

		if (!idlenessDetectorFile.exists()) {
			return getLastModification();
		}
		List<String> idlenessFileContent = null;
		try {
			idlenessFileContent = IOUtils.readLines(new FileInputStream(
					idlenessDetectorFile));
		} catch (IOException e) {
			return getLastModification();
		}

		if (idlenessFileContent.isEmpty()) {
			return getLastModification();
		}

		String idlenessDataStr = idlenessFileContent.get(0);
		String[] idlenessData = idlenessDataStr.split(";");
		lastModification = new Date(Long.parseLong(idlenessData[0]) * 1000);
		Long idleTime = Long.parseLong(idlenessData[1]);
		lastInput = new Date(lastModification.getTime() - idleTime);

		return lastInput;
	}

	private Date getLastModification() {
		if (lastModification == null) {
			lastModification = new Date();
		}
		return lastModification;
	}

	private boolean checkIdle() {
		if (!enabled) {
			return true;
		}
		
		long idleTime = new Date().getTime() - getLastInput().getTime();
		return (idleTime >= idlenessTime);
	}

	/* (non-Javadoc)
	 * @see org.ourgrid.node.idleness.IdlenessChecker#isIdle()
	 */
	@Override
	public boolean isIdle() {
		return isIdle;
	}
	
	public void addListener(IdlenessListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void run() {
		boolean newIdlenessState = checkIdle();
		if (newIdlenessState != isIdle) {
			for (IdlenessListener listener : listeners) {
				try {
					listener.changed(newIdlenessState);
				} catch (Exception e) {}
			}
		}
		isIdle = newIdlenessState;
	}

}

package org.ourgrid.node.idleness;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ourgrid.node.util.NodeProperties;

public class LinuxDevInputIdlenessDetector implements Runnable, IdlenessChecker {

	private String idlenessCmdLocation;
	private Long idlenessTime;
	private Boolean enabled;
	private Boolean isIdle;
	private List<IdlenessListener> listeners = new LinkedList<IdlenessListener>();
	
	public LinuxDevInputIdlenessDetector(Properties properties) {
		String enabledStr = properties.getProperty(NodeProperties.IDLENESS_ENABLED);
		this.enabled = Boolean.valueOf(enabledStr);
		if (enabled) {
			this.idlenessCmdLocation = properties.getProperty(NodeProperties.IDLENESS_FILE);
			String idlenessTimeStr = properties.getProperty(NodeProperties.IDLENESS_TIME);
			this.idlenessTime = Long.valueOf(idlenessTimeStr);
			this.isIdle = false;
		} else {
			this.isIdle = true;
		}
	}
	
	public void init() {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);
	}
	
	private boolean checkIdle() {
		if (!enabled) {
			return true;
		}
		
		try {
			ProcessBuilder pb = new ProcessBuilder(idlenessCmdLocation, 
					idlenessTime.toString());
			int exitValue = pb.start().waitFor();
			return exitValue == 1;
		} catch (Exception e) {
			return false;
		}
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

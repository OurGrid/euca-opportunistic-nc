package org.ourgrid.node.idleness;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ourgrid.node.util.NodeProperties;

public abstract class AbstractIdlenessDetector implements Runnable, IdlenessDetector {

	private Long idlenessTime;
	private Boolean enabled;
	private Boolean isIdle;
	private List<IdlenessListener> listeners = new LinkedList<IdlenessListener>();
	
	public AbstractIdlenessDetector(Properties properties) {
		String enabledStr = properties.getProperty(NodeProperties.IDLENESS_ENABLED);
		this.enabled = Boolean.valueOf(enabledStr);
		if (enabled) {
			String idlenessTimeStr = properties.getProperty(NodeProperties.IDLENESS_TIME);
			this.idlenessTime = Long.valueOf(idlenessTimeStr);
			this.isIdle = false;
		} else {
			this.isIdle = true;
		}
	}
	
	public Boolean isEnabled() {
		return enabled;
	}
	
	public void init() {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);
	}
	
	@Override
	public boolean isIdle() {
		return isIdle;
	}
	
	public void addListener(IdlenessListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void run() {
		boolean newIdlenessState = checkIdle(idlenessTime);
		if (newIdlenessState != isIdle) {
			for (IdlenessListener listener : listeners) {
				try {
					listener.changed(newIdlenessState);
				} catch (Exception e) {}
			}
		}
		isIdle = newIdlenessState;
	}

	protected abstract boolean checkIdle(Long idlenessTime);
	
}

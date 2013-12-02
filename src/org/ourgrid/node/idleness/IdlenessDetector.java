package org.ourgrid.node.idleness;

public interface IdlenessDetector {
	
	/**
	 * @return True if node controller is idle. False, otherwise.
	 */
	public boolean isIdle();
	
	public void init();

	public void addListener(IdlenessListener listener);

}

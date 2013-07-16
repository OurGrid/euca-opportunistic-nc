package org.ourgrid.node.idleness;

public interface IdlenessChecker {
	
	/**
	 * @return True if node controller is idle. False, otherwise.
	 */
	public boolean isIdle();

}

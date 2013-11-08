package org.ourgrid.node.model.bundle;

public enum BundleTaskState {

	NONE("none"), 
	BUNDLING("bundling"), 
	SUCCEEDED("succeeded"), 
	FAILED("failed"), 
	CANCELLED("cancelled"); 
	
	private String name;
	
	BundleTaskState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}

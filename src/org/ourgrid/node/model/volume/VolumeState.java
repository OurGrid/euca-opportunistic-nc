package org.ourgrid.node.model.volume;

public enum VolumeState {
	
	ATTACHING("attaching"), 
	ATTACHED("attached"), 
	ATTACHING_FAILED("attaching failed"), 
	DETACHING("detaching"), 
	DETACHED("detached"), 
	DETACHING_FAILED("detaching failed");
	
	private String name;
	
	VolumeState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

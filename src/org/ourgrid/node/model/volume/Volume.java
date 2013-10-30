package org.ourgrid.node.model.volume;

public class Volume {
	
	private String id;
	private State state;
	
	public enum State {
		ATTACHING
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
}

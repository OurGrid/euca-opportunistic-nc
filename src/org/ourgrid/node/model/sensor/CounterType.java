package org.ourgrid.node.model.sensor;

public enum CounterType {
	SENSOR_UNUSED("SENSOR_UNUSED"),
	SENSOR_SUMMATION("SENSOR_SUMMATION"),
	SENSOR_AVERAGE("SENSOR_AVERAGE"),
	SENSOR_LATEST("SENSOR_LATEST");
	
	private String name;
	
	CounterType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

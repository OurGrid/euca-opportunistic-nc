package org.ourgrid.node.model.sensor;

import java.util.List;


public class SensorMetric {

	private String name;
	private List<SensorCounter> sensorCounters;
	
	public SensorMetric(String name, List<SensorCounter> sensorCounters) {
		super();
		this.name = name;
		this.sensorCounters = sensorCounters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SensorCounter> getSensorCounters() {
		return sensorCounters;
	}

	public void setSensorCounters(List<SensorCounter> sensorCounters) {
		this.sensorCounters = sensorCounters;
	}
	
}

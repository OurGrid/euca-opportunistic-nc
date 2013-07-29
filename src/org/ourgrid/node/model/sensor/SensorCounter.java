package org.ourgrid.node.model.sensor;

import java.util.List;



public class SensorCounter {

	private SensorCounterType sensorCtrType;
	private long collectionIntervalMs;
	private List<SensorDimension> sensorDimensions;
	
	public SensorCounter(SensorCounterType sensorCtrType,
			long collectionIntervalMs, List<SensorDimension> sensorDimensions) {
		super();
		this.sensorCtrType = sensorCtrType;
		this.collectionIntervalMs = collectionIntervalMs;
		this.sensorDimensions = sensorDimensions;
	}

	public SensorCounterType getSensorCtrType() {
		return sensorCtrType;
	}

	public void setSensorCtrType(SensorCounterType sensorCtrType) {
		this.sensorCtrType = sensorCtrType;
	}

	public long getCollectionIntervalMs() {
		return collectionIntervalMs;
	}

	public void setCollectionIntervalMs(long collectionIntervalMs) {
		this.collectionIntervalMs = collectionIntervalMs;
	}

	public List<SensorDimension> getSensorDimensions() {
		return sensorDimensions;
	}

	public void setSensorDimensions(List<SensorDimension> sensorDimensions) {
		this.sensorDimensions = sensorDimensions;
	}
	
}

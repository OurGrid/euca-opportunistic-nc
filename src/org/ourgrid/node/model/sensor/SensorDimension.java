package org.ourgrid.node.model.sensor;

import java.util.List;

public class SensorDimension {

	private String name;
	private String alias;
	private long sequenceNum;
	private List<SensorValue> sensorValues;
	private int firstValueIndex;
	private double shiftIndex;
	
	public SensorDimension(String name, String alias, long sequenceNum,
			List<SensorValue> sensorValues, int firstValueIndex,
			double shiftIndex) {
		super();
		this.name = name;
		this.alias = alias;
		this.sequenceNum = sequenceNum;
		this.sensorValues = sensorValues;
		this.firstValueIndex = firstValueIndex;
		this.shiftIndex = shiftIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public long getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(long sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public List<SensorValue> getSensorValues() {
		return sensorValues;
	}

	public void setSensorValues(List<SensorValue> sensorValues) {
		this.sensorValues = sensorValues;
	}

	public int getFirstValueIndex() {
		return firstValueIndex;
	}

	public void setFirstValueIndex(int firstValueIndex) {
		this.firstValueIndex = firstValueIndex;
	}

	public double getShiftIndex() {
		return shiftIndex;
	}

	public void setShiftIndex(double shiftIndex) {
		this.shiftIndex = shiftIndex;
	}
	
}

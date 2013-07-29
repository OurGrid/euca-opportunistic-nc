package org.ourgrid.node.model.sensor;

public class SensorValue {

	private long timeInMs;
	private double value;
	private boolean isValid;
	
	public SensorValue(long timeInMs, double value, boolean isValid) {
		super();
		this.timeInMs = timeInMs;
		this.value = value;
		this.isValid = isValid;
	}

	public long getTimeInMs() {
		return timeInMs;
	}

	public void setTimeInMs(long timeInMs) {
		this.timeInMs = timeInMs;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}	
}

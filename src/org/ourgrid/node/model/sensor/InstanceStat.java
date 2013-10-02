package org.ourgrid.node.model.sensor;

public class InstanceStat {
	
	private String instanceId;
	private long timestamp;
	private String metricName;
	private CounterType counterType;
	private String dimensionName;
	private double value;
	
	public InstanceStat(String instanceId) {
		super();
		this.instanceId = instanceId;
	}
	
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getMetricName() {
		return metricName;
	}
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
	public CounterType getCounterType() {
		return counterType;
	}
	public void setCounterType(CounterType counterType) {
		this.counterType = counterType;
	}
	public String getDimensionName() {
		return dimensionName;
	}
	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
}

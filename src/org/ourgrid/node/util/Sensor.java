package org.ourgrid.node.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ourgrid.node.model.sensor.CounterType;
import org.ourgrid.node.model.sensor.InstanceStat;
import org.ourgrid.node.model.sensor.MetricDimension.DimensionName;
import org.ourgrid.node.model.sensor.SensorMetric.MetricName;
import org.ourgrid.node.model.sensor.SensorResource;
import org.ourgrid.node.model.sensor.SensorResourceCache;
import org.ourgrid.virt.model.CPUStats;
import org.ourgrid.virt.model.DiskStats;
import org.ourgrid.virt.model.NetworkStats;

public class Sensor implements Runnable {
	
	private static final long DEFAULT_SLEEP_DURATION = 15000;
	private static final long MIN_SLEEP_DURATION = 5000;
	private long nextSleepDurationMs = DEFAULT_SLEEP_DURATION;
	private int pollingInterval;
	private static Sensor sensorM;
	private SensorResourceCache sensorState;
	
	public static final int MAX_SENSOR_RESOURCES = 2048;
	
	private Sensor(int pollingInterval) {
		this.setPollingInterval(pollingInterval);
	}
	
	public static Sensor getInstance(int pollingInterval) {
		
		if (sensorM == null) {
			sensorM = new Sensor(pollingInterval);
		}
		
		return sensorM;
	}
	
	public int getPollingInterval() {
		return pollingInterval;
	}
	
	public void setPollingInterval(int pollingInterval) {
		this.pollingInterval = pollingInterval;
	}
	
	@Override
	public void run() {
		if (sensorState == null) {
			sensorState = new SensorResourceCache();
		}
		
		try {
			monitorInstancesResources();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void monitorInstancesResources() throws Exception {
		while (true) {
			Thread.sleep(nextSleepDurationMs);
			
			long startMs = System.currentTimeMillis();
			
			refreshResources(getCurrentResourcesNames());
			
			long refreshDelay = System.currentTimeMillis() - startMs;
			
			nextSleepDurationMs = nextSleepDurationMs - refreshDelay;
			if (nextSleepDurationMs < MIN_SLEEP_DURATION) {
				nextSleepDurationMs = MIN_SLEEP_DURATION;
			}
		}
	}

	private void refreshResources(List<String> currentResourcesNames) throws Exception {
		Map<String,List<InstanceStat>> stats = getInstancesStats(currentResourcesNames);
		
		for (String instance : stats.keySet()) {
			for (InstanceStat iStat : stats.get(instance)) {
				addSensorValue(iStat);
			}
		}
		
	}

	private void addSensorValue(InstanceStat iStat) {
		//TODO
//		if (getSensorResource(iStat.getInstanceId())) {
//		}
		
	}

	private SensorResource getSensorResource(String resourceName) {
		
		for (SensorResource sR : sensorState.getSensorResources()) {
			if (sR.getResourceName().equals(resourceName)) {
				return sR;
			}
		}
		//FIXME
		return new SensorResource(resourceName, "instance", "", null);
	}

	private Map<String,List<InstanceStat>> getInstancesStats(
			List<String> currentResourcesNames) throws Exception {
		
		Map<String,List<InstanceStat>> instancesStats = new HashMap<String,List<InstanceStat>>();
		
		for (String resourceName : currentResourcesNames) {
			instancesStats.put(resourceName,buildInstanceStatObj(resourceName));
		}
		
		return instancesStats;
	}

	private List<InstanceStat> buildInstanceStatObj(String instanceId) throws Exception {
		
		List<InstanceStat> iStats = new ArrayList<InstanceStat>();
		
		CPUStats cStats = OurVirtUtils.getCPUStats(instanceId);
		InstanceStat cpuUtilization = new InstanceStat(instanceId);
		
		cpuUtilization.setTimestamp(cStats.getTimestamp());
		cpuUtilization.setMetricName(MetricName.CPUUtilization.getMetricNameStr());
		cpuUtilization.setCounterType(CounterType.SENSOR_SUMMATION);
		cpuUtilization.setDimensionName(DimensionName.DEFAULT.getDimensionNameStr());
		cpuUtilization.setValue(cStats.getCpuTime());
		
		iStats.add(cpuUtilization);
		
		NetworkStats netStats = OurVirtUtils.getNetworkStats(instanceId);
		InstanceStat netIn = new InstanceStat(instanceId);
		
		netIn.setTimestamp(netStats.getTimestamp());
		netIn.setMetricName(MetricName.NetworkIn.getMetricNameStr());
		netIn.setCounterType(CounterType.SENSOR_SUMMATION);
		netIn.setDimensionName(DimensionName.TOTAL.getDimensionNameStr());
		netIn.setValue(netStats.getReceivedBytes());
		
		iStats.add(netIn);

		InstanceStat netOut = new InstanceStat(instanceId);
		
		netOut.setTimestamp(netStats.getTimestamp());
		netOut.setMetricName(MetricName.NetworkOut.getMetricNameStr());
		netOut.setCounterType(CounterType.SENSOR_SUMMATION);
		netOut.setDimensionName(DimensionName.TOTAL.getDimensionNameStr());
		netOut.setValue(netStats.getTransferredBytes());
		
		iStats.add(netOut);
		
		List<DiskStats> disksStats = OurVirtUtils.getDiskStats(instanceId);
		
		for (DiskStats diskStat : disksStats) {
			InstanceStat diskReadOps = new InstanceStat(instanceId);
			diskReadOps.setTimestamp(diskStat.getTimestamp());
			diskReadOps.setMetricName(MetricName.DiskReadOps.getMetricNameStr());
			diskReadOps.setCounterType(CounterType.SENSOR_SUMMATION);
			diskReadOps.setDimensionName(diskStat.getDeviceName());
			diskReadOps.setValue(diskStat.getReadOps());
			
			iStats.add(diskReadOps);
			
			InstanceStat diskWriteOps = new InstanceStat(instanceId);
			diskWriteOps.setTimestamp(diskStat.getTimestamp());
			diskWriteOps.setMetricName(MetricName.DiskWriteOps.getMetricNameStr());
			diskWriteOps.setCounterType(CounterType.SENSOR_SUMMATION);
			diskWriteOps.setDimensionName(diskStat.getDeviceName());
			diskWriteOps.setValue(diskStat.getWriteOps());
			
			iStats.add(diskWriteOps);
			
			InstanceStat diskReadBytes = new InstanceStat(instanceId);
			diskReadBytes.setTimestamp(diskStat.getTimestamp());
			diskReadBytes.setMetricName(MetricName.DiskReadBytes.getMetricNameStr());
			diskReadBytes.setCounterType(CounterType.SENSOR_SUMMATION);
			diskReadBytes.setDimensionName(diskStat.getDeviceName());
			diskReadBytes.setValue(diskStat.getReadBytes());
			
			iStats.add(diskReadBytes);
			
			InstanceStat diskWriteBytes = new InstanceStat(instanceId);
			diskWriteBytes.setTimestamp(diskStat.getTimestamp());
			diskWriteBytes.setMetricName(MetricName.DiskWriteBytes.getMetricNameStr());
			diskWriteBytes.setCounterType(CounterType.SENSOR_SUMMATION);
			diskWriteBytes.setDimensionName(diskStat.getDeviceName());
			diskWriteBytes.setValue(diskStat.getWriteBytes());
			
			iStats.add(diskWriteBytes);
			
			InstanceStat diskTotalReadTime = new InstanceStat(instanceId);
			diskTotalReadTime.setTimestamp(diskStat.getTimestamp());
			diskTotalReadTime.setMetricName(MetricName.VolumeTotalReadTime.getMetricNameStr());
			diskTotalReadTime.setCounterType(CounterType.SENSOR_SUMMATION);
			diskTotalReadTime.setDimensionName(diskStat.getDeviceName());
			diskTotalReadTime.setValue(diskStat.getReadTotalTime());
			
			iStats.add(diskTotalReadTime);
			
			InstanceStat diskTotalWriteTime = new InstanceStat(instanceId);
			diskTotalWriteTime.setTimestamp(diskStat.getTimestamp());
			diskTotalWriteTime.setMetricName(MetricName.VolumeTotalWriteTime.getMetricNameStr());
			diskTotalWriteTime.setCounterType(CounterType.SENSOR_SUMMATION);
			diskTotalWriteTime.setDimensionName(diskStat.getDeviceName());
			diskTotalWriteTime.setValue(diskStat.getWriteTotalTime());
			
			iStats.add(diskTotalWriteTime);
		}
		
		return iStats;
	}

	private List<String> getCurrentResourcesNames() {
		List<String> resourcesNames = new ArrayList<String>();
		
		for (SensorResource sensorRes : sensorState.getSensorResources()) {
			resourcesNames.add(sensorRes.getResourceName());
		}
		return resourcesNames;
	}
}

package org.ourgrid.node.util;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.model.sensor.CounterType;
import org.ourgrid.node.model.sensor.InstanceStat;
import org.ourgrid.node.model.sensor.MetricCounter;
import org.ourgrid.node.model.sensor.MetricDimension;
import org.ourgrid.node.model.sensor.MetricDimension.DimensionName;
import org.ourgrid.node.model.sensor.MetricValue;
import org.ourgrid.node.model.sensor.SensorMetric;
import org.ourgrid.node.model.sensor.SensorMetric.MetricName;
import org.ourgrid.node.model.sensor.SensorResource;
import org.ourgrid.node.model.sensor.SensorCache;
import org.ourgrid.virt.model.CPUStats;
import org.ourgrid.virt.model.DiskStats;
import org.ourgrid.virt.model.NetworkStats;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.MetricCounterType;
import edu.ucsb.eucalyptus.MetricDimensionsType;
import edu.ucsb.eucalyptus.MetricDimensionsValuesType;
import edu.ucsb.eucalyptus.MetricsResourceType;

public class Sensor implements Runnable {
	
	public static final int MAX_SENSOR_RESOURCES = 2048;
	
	private static final Logger LOGGER = Logger.getLogger(Sensor.class);
	
	private long pollingInterval;
	private SensorCache sensorState;
	private InstanceRepository instanceRepository;
	
	public Sensor(long pollingInterval, InstanceRepository instanceRepository) {
		this.instanceRepository = instanceRepository;
		this.pollingInterval = pollingInterval;
	}
	
	@Override
	public void run() {
		if (sensorState == null) {
			sensorState = new SensorCache();
		}
		try {
			refreshResources(getCurrentResourcesNames());
		} catch (Exception e) {
			LOGGER.error("Could not retrieve metrics.", e);
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
		SensorResource sensorResource = getSensorResource(iStat.getInstanceId());
		String metricName = iStat.getMetricName();
		for (MetricsResourceType metric : sensorResource.getMetrics()) {
			if (!metric.getMetricName().equals(metricName)) {
				continue;
			}
			addToMetric(iStat, metric);
			return;
		}
		SensorMetric metric = new SensorMetric(metricName, new MetricCounterType[]{});
		sensorResource.addMetrics(metric);
		addToMetric(iStat, metric);
	}

	private void addToMetric(InstanceStat iStat, MetricsResourceType metric) {
		for (MetricCounterType counter : metric.getCounters()) {
			if (!counter.getType().equals(
					iStat.getCounterType().getName())) {
				continue;
			}
			addToCounter(iStat, counter);
			return;
		}
		MetricCounter metricCounter = new MetricCounter(pollingInterval, 
				new MetricDimensionsType[]{}, 0L, iStat.getCounterType().getName());
		metric.addCounters(metricCounter);
		addToCounter(iStat, metricCounter);
	}

	private void addToCounter(InstanceStat iStat, MetricCounterType counter) {
		for (MetricDimensionsType dimension : counter.getDimensions()) {
			if (!dimension.getDimensionName().equals(
					iStat.getDimensionName())) {
				continue;
			}
			addToDimension(iStat, dimension);
			return;
		}
		MetricDimension metricDimension = new MetricDimension(iStat.getDimensionName(), 
				new MetricDimensionsValuesType[]{});
		counter.addDimensions(metricDimension);
		addToDimension(iStat, metricDimension);
	}

	private void addToDimension(InstanceStat iStat, MetricDimensionsType dimension) {
		MetricDimension dimensionImpl = (MetricDimension) dimension;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(iStat.getTimestamp());
		MetricDimensionsValuesType value = new MetricValue(calendar, iStat.getValue());
		dimensionImpl.addValue(value);
	}
	
	private SensorResource getSensorResource(String resourceName) {
		for (SensorResource sR : sensorState.getSensorResources()) {
			if (sR.getResourceName().equals(resourceName)) {
				return sR;
			}
		}
		InstanceType instance = instanceRepository.getInstance(resourceName);
		
		SensorResource sensorResource = new SensorResource(resourceName, "instance", 
				instance.getUuid(), 
				new ArrayList<MetricsResourceType>());
		sensorState.addResource(sensorResource);
		return sensorResource;
	}

	private Map<String,List<InstanceStat>> getInstancesStats(
			List<String> currentResourcesNames) throws Exception {
		
		Map<String,List<InstanceStat>> instancesStats = new HashMap<String,List<InstanceStat>>();
		
		for (String resourceName : currentResourcesNames) {
			instancesStats.put(resourceName, buildInstanceStatObj(resourceName));
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
		List<InstanceType> instances = instanceRepository.getInstances();
		for (InstanceType instance : instances) {
			resourcesNames.add(instance.getInstanceId());
		}
		return resourcesNames;
	}
	
	public SensorCache getCache() {
		return sensorState;
	}
}

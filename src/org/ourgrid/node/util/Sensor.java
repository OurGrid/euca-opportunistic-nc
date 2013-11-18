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
import org.ourgrid.node.model.sensor.SensorCache;
import org.ourgrid.node.model.sensor.SensorMetric;
import org.ourgrid.node.model.sensor.SensorMetric.MetricName;
import org.ourgrid.node.model.sensor.SensorResource;
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
		MetricsResourceType[] metrics = sensorResource.getMetrics();
		if (metrics != null) {
			for (MetricsResourceType metric : metrics) {
				if (!metric.getMetricName().equals(metricName)) {
					continue;
				}
				addToMetric(iStat, metric);
				return;
			}
		}
		SensorMetric metric = new SensorMetric(metricName, new MetricCounterType[]{});
		sensorResource.addMetrics(metric);
		addToMetric(iStat, metric);
	}

	private void addToMetric(InstanceStat iStat, MetricsResourceType metric) {
		MetricCounterType[] counters = metric.getCounters();
		if (counters != null) {
			for (MetricCounterType counter : counters) {
				if (!counter.getType().equals(
						iStat.getCounterType().getName())) {
					continue;
				}
				addToCounter(iStat, counter);
				return;
			}
		}
		MetricCounter metricCounter = new MetricCounter(pollingInterval, 
				new MetricDimensionsType[]{}, 0L, iStat.getCounterType().getName());
		metric.addCounters(metricCounter);
		addToCounter(iStat, metricCounter);
	}

	private void addToCounter(InstanceStat iStat, MetricCounterType counter) {
		MetricDimensionsType[] dimensions = counter.getDimensions();
		if (dimensions != null) {
			for (MetricDimensionsType dimension : dimensions) {
				if (!dimension.getDimensionName().equals(
						iStat.getDimensionName())) {
					continue;
				}
				addToDimension(iStat, dimension);
				return;
			}
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

	private List<InstanceStat> buildInstanceStatObj(String instanceId) {
		
		List<InstanceStat> iStats = new ArrayList<InstanceStat>();
		
		CPUStats cStats = null;
		try {
			cStats = OurVirtUtils.getCPUStats(instanceId);
		} catch (Exception e) {
			LOGGER.warn("Could not get CPU stats.", e);
		}
		InstanceStat cpuUtilization = getCPUInstanceStat(instanceId, cStats);
		if (cpuUtilization != null) {
			iStats.add(cpuUtilization);
		}
		
		NetworkStats netStats = null;
		try {
			netStats = OurVirtUtils.getNetworkStats(instanceId);
		} catch (Exception e) {
			LOGGER.warn("Could not get network stats.", e);
		}
		InstanceStat netIn = getNetworkInInstanceStat(instanceId, netStats);
		if (netIn != null) {
			iStats.add(netIn);
		}
		InstanceStat netOut = getNetworkOutInstanceStat(instanceId, netStats);
		if (netOut != null) {
			iStats.add(netOut);
		}
		
		List<DiskStats> disksStats = null;
		try {
			disksStats = OurVirtUtils.getDiskStats(instanceId);
		} catch (Exception e) {
			LOGGER.warn("Could not get disk stats.", e);
		}
		if (disksStats != null) {
			for (DiskStats diskStat : disksStats) {
				InstanceStat diskReadOps = getDiskInstanceStat(instanceId, 
						diskStat, MetricName.DiskReadOps);
				if (diskReadOps != null) {
					iStats.add(diskReadOps);
				}
				InstanceStat diskWriteOps = getDiskInstanceStat(instanceId, 
						diskStat, MetricName.DiskWriteOps);
				if (diskWriteOps != null) {
					iStats.add(diskWriteOps);
				}
				InstanceStat diskReadBytes = getDiskInstanceStat(instanceId, 
						diskStat, MetricName.DiskReadBytes);
				if (diskReadBytes != null) {
					iStats.add(diskReadBytes);
				}
				InstanceStat diskWriteBytes = getDiskInstanceStat(instanceId, 
						diskStat, MetricName.DiskWriteBytes);
				if (diskWriteOps != null) {
					iStats.add(diskWriteBytes);
				}
				InstanceStat diskTotalReadTime = getDiskInstanceStat(instanceId, 
						diskStat, MetricName.VolumeTotalReadTime);
				if (diskTotalReadTime != null) {
					iStats.add(diskTotalReadTime);
				}
				InstanceStat diskTotalWriteTime = getDiskInstanceStat(instanceId, 
						diskStat, MetricName.VolumeTotalWriteTime);
				if (diskTotalWriteTime != null) {
					iStats.add(diskTotalWriteTime);
				}
			}
		}
		
		return iStats;
	}

	private InstanceStat getDiskInstanceStat(String instanceId,
			DiskStats diskStat, MetricName metricName) {
		if (diskStat == null) {
			return null;
		}
		InstanceStat diskInstanceStat = new InstanceStat(instanceId);
		diskInstanceStat.setTimestamp(diskStat.getTimestamp());
		diskInstanceStat.setMetricName(metricName.getMetricNameStr());
		diskInstanceStat.setCounterType(CounterType.SENSOR_SUMMATION);
		diskInstanceStat.setDimensionName(diskStat.getDeviceName());
		long value = 0L;
		switch (metricName) {
		case DiskReadOps:
			value = diskStat.getReadOps();
			break;
			
		case DiskWriteOps:
			value = diskStat.getWriteOps();
			break;
			
		case DiskReadBytes:
			value = diskStat.getReadBytes();
			break;
			
		case DiskWriteBytes:
			value = diskStat.getWriteBytes();
			break;
			
		case VolumeTotalReadTime:
			value = diskStat.getReadTotalTime();
			break;
			
		case VolumeTotalWriteTime:
			value = diskStat.getWriteTotalTime();
			break;
			
		default:
			break;
		}
		diskInstanceStat.setValue(value);
		
		return diskInstanceStat;
	}

	private InstanceStat getNetworkOutInstanceStat(String instanceId,
			NetworkStats netStats) {
		if (netStats == null) {
			return null;
		}
		InstanceStat netOut = new InstanceStat(instanceId);
		netOut.setTimestamp(netStats.getTimestamp());
		netOut.setMetricName(MetricName.NetworkOut.getMetricNameStr());
		netOut.setCounterType(CounterType.SENSOR_SUMMATION);
		netOut.setDimensionName(DimensionName.TOTAL.getDimensionNameStr());
		netOut.setValue(netStats.getTransferredBytes());
		return netOut;
	}

	private InstanceStat getNetworkInInstanceStat(String instanceId,
			NetworkStats netStats) {
		if (netStats == null) {
			return null;
		}
		InstanceStat netIn = new InstanceStat(instanceId);
		netIn.setTimestamp(netStats.getTimestamp());
		netIn.setMetricName(MetricName.NetworkIn.getMetricNameStr());
		netIn.setCounterType(CounterType.SENSOR_SUMMATION);
		netIn.setDimensionName(DimensionName.TOTAL.getDimensionNameStr());
		netIn.setValue(netStats.getReceivedBytes());
		return netIn;
	}

	private InstanceStat getCPUInstanceStat(String instanceId, CPUStats cStats) {
		if (cStats == null) {
			return null;
		}
		InstanceStat cpuUtilization = new InstanceStat(instanceId);
		cpuUtilization.setTimestamp(cStats.getTimestamp());
		cpuUtilization.setMetricName(MetricName.CPUUtilization.getMetricNameStr());
		cpuUtilization.setCounterType(CounterType.SENSOR_SUMMATION);
		cpuUtilization.setDimensionName(DimensionName.DEFAULT.getDimensionNameStr());
		cpuUtilization.setValue(cStats.getCpuTime());
		return cpuUtilization;
	}

	private List<String> getCurrentResourcesNames() {
		List<String> resourcesNames = new ArrayList<String>();
		List<InstanceType> instances = instanceRepository.getInstances();
		for (InstanceType instance : instances) {
			if (instance.getStateName().equals(InstanceRepository.EXTANT_STATE)) {
				resourcesNames.add(instance.getInstanceId());
			}
		}
		return resourcesNames;
	}
	
	public SensorCache getCache() {
		return sensorState;
	}
}

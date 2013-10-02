package org.ourgrid.node.model.sensor;

import edu.ucsb.eucalyptus.MetricCounterType;
import edu.ucsb.eucalyptus.MetricsResourceType;


public class SensorMetric extends MetricsResourceType {
	
	public static enum MetricName {
		CPUUtilization("CPUUtilization"),
		NetworkIn("NetworkIn"),
		NetworkOut("NetworkOut"),
		DiskReadOps("DiskReadOps"),
		DiskWriteOps("DiskWriteOps"),
		DiskReadBytes("DiskReadBytes"),
		DiskWriteBytes("DiskWriteBytes"),
		VolumeTotalReadTime("VolumeTotalReadTime"),
		VolumeTotalWriteTime("VolumeTotalWriteTime");
		
		private String metricNameStr;
		MetricName(String metricNameStr) {
			this.metricNameStr = metricNameStr;
		}

		public String getMetricNameStr() {
			return metricNameStr;
		}
	}

	private static final long serialVersionUID = 1L;
	
	public SensorMetric(String metricName, MetricCounterType[] counters) {
		setMetricName(metricName);
		setCounters(counters);
	}

	@Override
	public boolean equals(Object arg0) {
		
		if (! (arg0 instanceof MetricsResourceType)) {
			return false;
		}
		
		MetricsResourceType mRT = (MetricsResourceType)arg0;
		
		//Verify if metrics are equal
		for (int i = 0; i < mRT.getCounters().length; i++) {
			MetricCounterType mRTCounter = mRT.getCounters()[i];
			MetricCounterType myCounter = getCounters()[i];
			
			if (!mRTCounter.equals(myCounter)) {
				return false;
			}
		}
		
		return mRT.getMetricName().equals(getMetricName());
	}
	
}

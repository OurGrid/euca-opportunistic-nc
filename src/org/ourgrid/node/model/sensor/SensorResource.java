package org.ourgrid.node.model.sensor;

import java.util.ArrayList;

import edu.ucsb.eucalyptus.MetricsResourceType;
import edu.ucsb.eucalyptus.SensorsResourceType;

public class SensorResource extends SensorsResourceType {
	private static final long serialVersionUID = 1L;
	
	public SensorResource(String resourceName, String resourceType, String resourceUuid, 
			ArrayList<MetricsResourceType> metrics) {
		setResourceName(resourceName);
		setResourceType(resourceType);
		setResourceUuid(resourceUuid);
		//FIXME
		//setMetrics(MetricsResourceType[](metrics.toArray()));
	}
	
	@Override
	public boolean equals(Object arg0) {
		
		if (! (arg0 instanceof SensorsResourceType)) {
			return false;
		}
		
		SensorsResourceType sRT = (SensorsResourceType)arg0;
		
		//Verify if metrics are equal
		for (int i = 0; i < sRT.getMetrics().length; i++) {
			MetricsResourceType sRTMetric = sRT.getMetrics()[i];
			MetricsResourceType myMetric = getMetrics()[i];
			
			if (!sRTMetric.equals(myMetric)) {
				return false;
			}
		}
		
		boolean isEqual = sRT.getResourceName().equals(getResourceName())
				&& sRT.getResourceType().equals(getResourceType())
				&& sRT.getResourceUuid().equals(getResourceUuid());
				
		
		return isEqual;
	}
	
}

package org.ourgrid.node.model.sensor;

import java.util.Calendar;

import edu.ucsb.eucalyptus.MetricDimensionsValuesType;

public class MetricValue extends MetricDimensionsValuesType {
	
	public MetricValue(Calendar timestamp, double value) {
		setTimestamp(timestamp);
		setValue(value);
	}
	
	private static final long serialVersionUID = 1L;
	
//	TODO: Remove this method if tests work.
//	@Override
//	public boolean equals(Object arg0) {
//		
//		if (!(arg0 instanceof MetricDimensionsValuesType)) {
//			return false;
//		}
//		
//		MetricDimensionsValuesType mDVT = (MetricDimensionsValuesType)arg0;
//		
//		return mDVT.getValue() == getValue() && mDVT.getTimestamp().equals(getTimestamp());
//	}
}

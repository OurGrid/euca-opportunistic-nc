package org.ourgrid.node.model.sensor;

import edu.ucsb.eucalyptus.MetricCounterType;
import edu.ucsb.eucalyptus.MetricDimensionsType;

public class MetricCounter extends MetricCounterType {

	public MetricCounter(long collectionIntervalMs, MetricDimensionsType[] dimensions, 
		long sequenceNumber, String counterType) {
		
		setCollectionIntervalMs(collectionIntervalMs);
		if (dimensions != null && dimensions.length >= 1) {
			setDimensions(dimensions);
		}
		setSequenceNum(sequenceNumber);
		setType(counterType);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean equals(Object arg0) {
		
		if (! (arg0 instanceof MetricCounterType)) {
			return false;
		}
		
		MetricCounterType mCT = (MetricCounterType)arg0;
		
		//Verify if metrics are equal
		for (int i = 0; i < mCT.getDimensions().length; i++) {
			MetricDimensionsType mCTDimension = mCT.getDimensions()[i];
			MetricDimensionsType myDimension = getDimensions()[i];
			
			if (!mCTDimension.equals(myDimension)) {
				return false;
			}
		}
		
		boolean isEqual = mCT.getCollectionIntervalMs() == getCollectionIntervalMs()
			&& mCT.getSequenceNum() == getSequenceNum()
			&& mCT.getType().equals(getType());
		
		return isEqual;
	}
}

package org.ourgrid.node.model.sensor;

import java.util.Date;
import java.util.List;

import org.joda.time.Period;

public class SensorResourceCache {
	
	private long collectionIntervalTimeMs;
	private int historySize;
	private boolean initialized;
	private boolean suspendPolling;
	private int maxResources;
	private int usedResources;
	private Date lastPolled;
	private Period intervalPolled;
	private List<SensorResource> sensorResources;

}

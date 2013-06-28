package org.ourgrid.node.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.ucsb.eucalyptus.InstanceType;

public class InstanceRepository {

	public static final String PENDING_STATE = "Pending";
	public static final String EXTANT_STATE = "Extant";
	public static final String TEARDOWN_STATE = "Teardown";
	
	private Map<String, InstanceType> repository = new HashMap<String, InstanceType>();

	public void addInstance(InstanceType instance) {
		repository.put(instance.getInstanceId(), instance);
	}

	public InstanceType getInstance(String instanceId) {
		return repository.get(instanceId);
	}
	
	public void removeInstance(String instanceId) {
		repository.remove(instanceId);
	}

	public List<InstanceType> getInstances() {
		return new LinkedList<InstanceType>(repository.values());
	}
}

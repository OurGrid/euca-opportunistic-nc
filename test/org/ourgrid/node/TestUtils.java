package org.ourgrid.node;

import org.ourgrid.node.model.InstanceRepository;

import edu.ucsb.eucalyptus.InstanceType;

public class TestUtils {
	
	private static int instanceId = 0;
	private static int userId = 1;
	
	public static int correlationId = 0;
	public static final String INSTANCE_ID = "001"; 
	public static final String USER_ID = "user001";
	public static final String VDI_EXT = ".vdi";
	
			
	
	public static InstanceType addInstanceToRepository(NodeFacade facade) {
		InstanceType instance = new InstanceType();
		instance.setInstanceId(String.valueOf(++instanceId));
		instance.setUserId(String.valueOf(userId));
				
		InstanceRepository iRep = new InstanceRepository();
		iRep.addInstance(instance);
		
		facade.setInstanceRepository(iRep);
		
		return instance;
	}

}

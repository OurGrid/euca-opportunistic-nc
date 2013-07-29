package org.ourgrid.node;

import org.ourgrid.node.model.InstanceRepository;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.VirtualMachineType;

public class TestUtils {
	
	private static int instanceId = 0;
	private static int userId = 0;
	private static int correlationId = 0;
	private static int instUuId = 0;
	private static int instReservationId = 0;
	private static int instOwnerId = 0;
	private static int instAccountId = 0;
	
	public static final String DEFAULT_INSTANCE_ID = "inst001"; 
	public static final String DEFAULT_USER_ID = "user001";
	public static final String DEFAULT_SENSOR_ID = "sensor001";
	
	public static final int DEF_INST_NUM_CORES = 2;
	public static final int DEF_INST_MEM = 1024;
	public static final int DEF_INST_DISK = 100;
	
	
	public static String getNewInstanceId() {
		return String.valueOf(instanceId++);
	}
	
	public static String getNewUserId() {
		return String.valueOf(userId++);
	}
	
	public static String getNewInstanceUUID() {
		return String.valueOf(instUuId++);
	}
	
	public static String getNewInstanceReservationId() {
		return String.valueOf(instReservationId++);
	}
	
	public static String getNewInstanceOwnerId() {
		return String.valueOf(instOwnerId++);
	}
	
	public static String getNewInstanceAccountId() {
		return String.valueOf(instAccountId++);
	}
	
	public static String getNewCorrelationId() {
		return String.valueOf(correlationId++);
	}
	
	public static InstanceType addBasicInstanceToRepository(NodeFacade facade,
			InstanceRepository iRep) {
		InstanceType instance = createBasicInstance();
				
		return addInstanceToRepository(instance, facade, iRep);
	}
	
	public static InstanceType addInstanceWithSensorToRepository(NodeFacade facade,
			InstanceRepository iRep) {
		InstanceType instance = createBasicInstance();
				
		return addInstanceToRepository(instance, facade, iRep);
	}

	public static InstanceType addInstanceToRepository(int numCores, 
			int mem, int disk, NodeFacade facade, 
			InstanceRepository iRep) {
		
		VirtualMachineType vmType = new VirtualMachineType();
		vmType.setCores(numCores);
		vmType.setMemory(mem);
		vmType.setDisk(disk);
		
		InstanceType instance = createBasicInstance();
		instance.setInstanceType(vmType);
		instance.setStateName(InstanceRepository.EXTANT_STATE);
		
		return addInstanceToRepository(instance, facade, iRep);
	}
	
	public static VirtualMachineType createBasicVMType() {
		VirtualMachineType vmType = new VirtualMachineType();
		vmType.setCores(TestUtils.DEF_INST_NUM_CORES);
		vmType.setMemory(TestUtils.DEF_INST_MEM);
		vmType.setDisk(TestUtils.DEF_INST_DISK);
		return vmType;
	}
	
	public static InstanceType addInstanceToRepository(InstanceType instance,
			NodeFacade facade, InstanceRepository iRep) {
		iRep.addInstance(instance);
		
		return instance;
	}
	
	public static void removeInstanceFromRepository(InstanceType instance, 
			NodeFacade facade, InstanceRepository iRep) {
		iRep.removeInstance(instance.getInstanceId());
	}

	private static InstanceType createBasicInstance() {
		InstanceType instance = new InstanceType();
		instance.setInstanceId(getNewInstanceId());
		instance.setUserId(getNewUserId());
		return instance;
	}
	
}

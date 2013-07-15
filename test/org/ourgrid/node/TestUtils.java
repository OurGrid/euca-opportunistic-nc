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
	
	public static final String DEFAULT_INSTANCE_ID = "001"; 
	public static final String DEFAULT_USER_ID = "user001";
	public static final String VDI_EXT = ".vdi";
	
	public static final int DEF_INST_NUM_CORES = 2;
	public static final int DEF_INST_MEM = 1024;
	public static final int DEF_INST_DISK = 100;
	
	
	public static int getNewInstanceId() {
		return instanceId++;
	}
	
	public static int getNewUserId() {
		return userId++;
	}
	
	public static int getNewInstanceUUID() {
		return instUuId++;
	}
	
	public static int getNewInstanceReservationId() {
		return instReservationId++;
	}
	
	public static int getNewInstanceOwnerId() {
		return instOwnerId++;
	}
	
	public static int getNewInstanceAccountId() {
		return instAccountId++;
	}
	
	public static int getNewCorrelationId() {
		return correlationId++;
	}
	
	public static InstanceType addInstanceToRepository(NodeFacade facade) {
		InstanceType instance = createBasicInstance();
				
		return addInstanceToRepository(instance, facade);
	}

	public static InstanceType addInstanceToRepository(int numCores, 
			int mem, int disk, NodeFacade facade) {
		
		VirtualMachineType vmType = new VirtualMachineType();
		vmType.setCores(numCores);
		vmType.setMemory(mem);
		vmType.setDisk(disk);
		
		InstanceType instance = createBasicInstance();
		instance.setInstanceType(vmType);
		instance.setStateName(InstanceRepository.EXTANT_STATE);
		
		return addInstanceToRepository(instance, facade);
	}
	
	public static VirtualMachineType createBasicVMType() {
		VirtualMachineType vmType = new VirtualMachineType();
		vmType.setCores(TestUtils.DEF_INST_NUM_CORES);
		vmType.setMemory(TestUtils.DEF_INST_MEM);
		vmType.setDisk(TestUtils.DEF_INST_DISK);
		return vmType;
	}
	
	public static InstanceType addInstanceToRepository(InstanceType instance,
			NodeFacade facade) {
		InstanceRepository iRep = facade.getInstanceRepository();
		iRep.addInstance(instance);
		facade.setInstanceRepository(iRep);
		
		return instance;
	}
	
	public static void removeInstanceFromRepository(InstanceType instance, 
			NodeFacade facade) {
		InstanceRepository iRep = facade.getInstanceRepository();
		iRep.removeInstance(instance.getInstanceId());
		facade.setInstanceRepository(iRep);
		
	}

	private static InstanceType createBasicInstance() {
		InstanceType instance = new InstanceType();
		instance.setInstanceId(String.valueOf(getNewInstanceId()));
		instance.setUserId(String.valueOf(getNewUserId()));
		return instance;
	}
	
}

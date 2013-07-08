package org.ourgrid.node.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.ourgrid.node.model.VBR;
import org.ourgrid.virt.OurVirt;
import org.ourgrid.virt.model.HypervisorType;
import org.ourgrid.virt.model.VirtualMachineConstants;
import org.ourgrid.virt.model.VirtualMachineStatus;

import edu.ucsb.eucalyptus.NcRunInstanceType;
import edu.ucsb.eucalyptus.VirtualMachineType;

public class OurVirtUtils {

	private static OurVirt OURVIRT;
	
//	public static void setVBoxHome(Properties properties) {
//		System.setProperty(NodeProperties.VBOX_HOME, 
//				properties.getProperty(NodeProperties.VBOX_HOME));
//	}
	
	static {
		System.setProperty(NodeProperties.VBOX_HOME,"/usr/lib/virtualbox/");
		OURVIRT = new OurVirt();
	} 
	
	public static void setOurVirt(OurVirt ourvirt) {
		OURVIRT = ourvirt;
	}
	
	public static void runInstance(NcRunInstanceType instanceRequest, 
			VBR vbr, Properties properties) throws Exception {
		
		String imagePath = WalrusUtils.getImagePath(vbr.getMachineImageId(), properties);
		String vmName = instanceRequest.getInstanceId();
		VirtualMachineType instanceType = instanceRequest.getInstanceType();
		
		String tempImage = getCloneLocation(instanceRequest.getInstanceId(), properties);
		OURVIRT.clone(HypervisorType.VBOX, imagePath, tempImage);
		
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(VirtualMachineConstants.GUEST_USER, 
				properties.getProperty(NodeProperties.GUEST_USER));
		conf.put(VirtualMachineConstants.GUEST_PASSWORD, 
				properties.getProperty(NodeProperties.GUEST_PASSWD));
		conf.put(VirtualMachineConstants.MEMORY, String.valueOf(instanceType.getMemory()));
		conf.put(VirtualMachineConstants.OS, instanceRequest.getPlatform());
		conf.put(VirtualMachineConstants.NETWORK_TYPE, 
				properties.getProperty(NodeProperties.NETWORK_TYPE));
		conf.put(VirtualMachineConstants.OS_VERSION, 
				properties.getProperty(NodeProperties.OS_VERSION));
		conf.put(VirtualMachineConstants.DISK_TYPE, 
				properties.getProperty(NodeProperties.DISK_TYPE));
		conf.put(VirtualMachineConstants.BRIDGED_INTERFACE, 
				properties.getProperty(NodeProperties.BRIDGED_INTERFACE));
		conf.put(VirtualMachineConstants.PAE_ENABLED, 
				properties.getProperty(NodeProperties.PAE_ENABLED));
		conf.put(VirtualMachineConstants.DISK_IMAGE_PATH, tempImage);
		conf.put(VirtualMachineConstants.START_TIMEOUT, 
				properties.getProperty(NodeProperties.START_TIMEOUT));
        String macAddress = instanceRequest.getNetParams().getPrivateMacAddress();
        macAddress = macAddress.replace(":", "");
        conf.put(VirtualMachineConstants.MAC, macAddress);
        
        VirtualMachineStatus vmStatus = OURVIRT.status(HypervisorType.VBOXSDK, vmName);
        if (vmStatus.equals(VirtualMachineStatus.NOT_REGISTERED)) {
			OURVIRT.register(vmName, conf);
		}
        
		for (Entry<String, Object> confEntry : conf.entrySet()) {
        	OURVIRT.setProperty(HypervisorType.VBOXSDK, vmName, 
        			confEntry.getKey(), confEntry.getValue());
        }
        
		OURVIRT.create(HypervisorType.VBOXSDK, vmName);
		OURVIRT.start(HypervisorType.VBOXSDK, vmName);
	}
	
	private static String getCloneLocation(String instanceId, Properties properties) {
		String cloneRoot = properties.getProperty(NodeProperties.CLONEROOT);
		File cloneRootFile = new File(cloneRoot);
		if (!cloneRootFile.exists()) {
			cloneRootFile.mkdirs();
		}
		return cloneRoot + "/" + instanceId + ".vdi";
	}

	public static void terminateInstance(String instanceId, Properties properties) throws Exception {
		VirtualMachineStatus vmStatus = OURVIRT.status(HypervisorType.VBOXSDK, instanceId);
		
		new File(getCloneLocation(instanceId, properties)).delete();
		
		//TODO(patricia): The ideal solution is to adopt existing VMs when the NC starts
		if (vmStatus.equals(VirtualMachineStatus.NOT_REGISTERED)) {
			OURVIRT.register(instanceId, new HashMap<String, String>());
		}
		
		OURVIRT.destroy(HypervisorType.VBOXSDK, instanceId);
	}
	
	public static void rebootInstance(String instanceId, String imageId, Properties properties) throws Exception {
		
		VirtualMachineStatus vmStatus = OURVIRT.status(HypervisorType.VBOXSDK, 
				instanceId);
		
		while (! vmStatus.equals(VirtualMachineStatus.POWERED_OFF)) {
			
			if (vmStatus.equals(VirtualMachineStatus.NOT_CREATED)) {
				throw new IllegalStateException("Could not reboot instance [" + 
						instanceId + "] " + "because machine state is NOT CREATED. " +
						"To fix this, run terminateInstance and then runInstance " +
						"commands.");
			} else if (vmStatus.equals(VirtualMachineStatus.NOT_REGISTERED)) {
				OURVIRT.register(instanceId, new HashMap<String, String>());
			} 
			OURVIRT.stop(HypervisorType.VBOXSDK, instanceId);
			
			//TODO - Re-think this waiting process
			Thread.sleep(1000);
			vmStatus = OURVIRT.status(HypervisorType.VBOXSDK, instanceId);
		}
		
		OURVIRT.start(HypervisorType.VBOXSDK, instanceId);
	}

	public static String getInstanceIP(String instanceId) {
		try {
			return (String) OURVIRT.getProperty(HypervisorType.VBOXSDK, instanceId, VirtualMachineConstants.IP);
		} catch (Exception e) {
			return null;
		}
	}

	public static void assignAddress(String instanceId, String publicIp) throws Exception {
		VirtualMachineStatus vmStatus = OURVIRT.status(HypervisorType.VBOXSDK, instanceId);
		
		if (vmStatus.equals(VirtualMachineStatus.NOT_CREATED)) {
			throw new IllegalStateException(
					"Could not assign address for instance ["
							+ instanceId
							+ "] "
							+ "because machine state is NOT CREATED. "
							+ "To fix this, run terminateInstance and then runInstance "
							+ "commands.");
		} else if (vmStatus.equals(VirtualMachineStatus.POWERED_OFF)) {
			throw new IllegalStateException(
					"Could not assign address for instance ["
							+ instanceId
							+ "] "
							+ "because machine state is POWERED OFF. "
							+ "To fix this, run runInstance command.");
		} else if (vmStatus.equals(VirtualMachineStatus.NOT_REGISTERED)) {
			OURVIRT.register(instanceId, new HashMap<String, String>());
		}
			
		OURVIRT.setProperty(HypervisorType.VBOXSDK, instanceId, 
				VirtualMachineConstants.IP, publicIp);
	}
	
	//TODO 
	private static VirtualMachineStatus getStatus(String instanceId) throws Exception {
		return OURVIRT.status(HypervisorType.VBOXSDK, instanceId);
	}
}

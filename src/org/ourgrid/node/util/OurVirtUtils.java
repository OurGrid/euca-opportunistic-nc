package org.ourgrid.node.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.ourgrid.node.model.VBR;
import org.ourgrid.virt.OurVirt;
import org.ourgrid.virt.model.CPUStats;
import org.ourgrid.virt.model.DiskStats;
import org.ourgrid.virt.model.HypervisorType;
import org.ourgrid.virt.model.NetworkStats;
import org.ourgrid.virt.model.VirtualMachineConstants;
import org.ourgrid.virt.model.VirtualMachineStatus;

import edu.ucsb.eucalyptus.NcRunInstanceType;
import edu.ucsb.eucalyptus.VirtualMachineType;

public class OurVirtUtils {

	private static OurVirt OURVIRT;
	private static final HypervisorType HYPERVISOR = HypervisorType.QEMU;
	
	public static void setHypervisorEnvVars(Properties properties) {
		System.setProperty(
				properties.getProperty(NodeProperties.HYPERVISOR_LOCATION_NAME), 
				properties.getProperty(NodeProperties.HYPERVISOR_LOCATION_VALUE));
		OURVIRT = new OurVirt();
	}
	
	public static void setOurVirt(OurVirt ourvirt) {
		OURVIRT = ourvirt;
	}
	
	public static void runInstance(NcRunInstanceType instanceRequest, 
			VBR vbr, Properties properties) throws Exception {
		
		if (getStatus(instanceRequest.getInstanceId())
				== VirtualMachineStatus.RUNNING) {
			throw new IllegalStateException("Instance [" 
					+ instanceRequest.getInstanceId() 
					+ "] is already in RUNNING state. Run rebootInstance " 
					+ "command to restart it.");
		}
		
		String imagePath = WalrusUtils.getImagePath(vbr.getMachineImageId(), properties);
		String vmName = instanceRequest.getInstanceId();
		VirtualMachineType instanceType = instanceRequest.getInstanceType();
		
		String tempImage = getCloneLocation(instanceRequest.getInstanceId(), properties);
		clone(imagePath, tempImage);
		
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(VirtualMachineConstants.GUEST_USER, 
				properties.getProperty(NodeProperties.GUEST_USER));
		conf.put(VirtualMachineConstants.GUEST_PASSWORD, 
				properties.getProperty(NodeProperties.GUEST_PASSWD));
		conf.put(VirtualMachineConstants.MEMORY, String.valueOf(instanceType.getMemory()));
//		conf.put(VirtualMachineConstants.OS, instanceRequest.getPlatform());
		conf.put(VirtualMachineConstants.OS, "linux");
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
        conf.put(VirtualMachineConstants.MAC, macAddress);
        conf.put(VirtualMachineConstants.USE_USB_HUB, Boolean.TRUE.toString());
        conf.put(VirtualMachineConstants.USE_CONSOLE_OUTPUT_FILE, Boolean.TRUE.toString());
        conf.put(VirtualMachineConstants.TAP_WINDOWS_DIR, properties.getProperty(NodeProperties.TAP_WINDOWS_DIR));
        
        VirtualMachineStatus vmStatus = OURVIRT.status(HYPERVISOR, vmName);
        if (vmStatus.equals(VirtualMachineStatus.NOT_REGISTERED)) {
			OURVIRT.register(vmName, conf);
		}
        
		for (Entry<String, Object> confEntry : conf.entrySet()) {
        	OURVIRT.setProperty(HYPERVISOR, vmName, 
        			confEntry.getKey(), confEntry.getValue());
        }
        
		OURVIRT.create(HYPERVISOR, vmName);
		OURVIRT.start(HYPERVISOR, vmName);
	}

	public static void clone(String imagePath, String tempImage)
			throws Exception {
		OURVIRT.clone(HYPERVISOR, imagePath, tempImage);
	}
	
	public static String getCloneLocation(String instanceId, Properties properties) {
		String cloneRoot = properties.getProperty(NodeProperties.CLONEROOT);
		File cloneRootFile = new File(cloneRoot);
		if (!cloneRootFile.exists()) {
			cloneRootFile.mkdirs();
		}
		return cloneRoot + File.separator + instanceId + WalrusUtils.IMG_EXT;
	}

	public static void terminateInstance(String instanceId, Properties properties) throws Exception {
		VirtualMachineStatus vmStatus = OURVIRT.status(HYPERVISOR, instanceId);
		if (vmStatus.equals(VirtualMachineStatus.NOT_REGISTERED)) {
			OURVIRT.register(instanceId, new HashMap<String, String>());
		}
		OURVIRT.stop(HYPERVISOR, instanceId);
		OURVIRT.destroy(HYPERVISOR, instanceId);
	}
	
	public static void rebootInstance(String instanceId, String imageId, Properties properties) throws Exception {
		
		VirtualMachineStatus vmStatus = OURVIRT.status(HYPERVISOR, 
				instanceId);
		
		if (vmStatus.equals(VirtualMachineStatus.NOT_CREATED)) {
			throw new IllegalStateException("Could not reboot instance [" + 
					instanceId + "] " + "because machine state is NOT CREATED. " +
					"To fix this, run terminateInstance and then runInstance " +
					"commands.");
		} else if (vmStatus.equals(VirtualMachineStatus.POWERED_OFF)) {
			throw new IllegalStateException("Could not reboot instance [" + 
					instanceId + "] " + "because machine state is POWERED OFF. " +
					"To fix this, run runInstance command.");
		} else if (vmStatus.equals(VirtualMachineStatus.NOT_REGISTERED)) {
			OURVIRT.register(instanceId, new HashMap<String, String>());
		} 
		
		OURVIRT.reboot(HYPERVISOR, instanceId);
		
	}

	public static String getInstanceIP(String instanceId) {
		try {
			return (String) OURVIRT.getProperty(HYPERVISOR, instanceId, VirtualMachineConstants.IP);
		} catch (Exception e) {
			return null;
		}
	}

	public static void assignAddress(String instanceId, String publicIp) throws Exception {
		VirtualMachineStatus vmStatus = OURVIRT.status(HYPERVISOR, instanceId);
		
		if (vmStatus.equals(VirtualMachineStatus.NOT_REGISTERED)) {
			OURVIRT.register(instanceId, new HashMap<String, String>());
		}
			
		OURVIRT.setProperty(HYPERVISOR, instanceId, 
				VirtualMachineConstants.IP, publicIp);
	}
	
	public static CPUStats getCPUStats(String instanceId) throws Exception {
		return OURVIRT.getCPUStats(HYPERVISOR, instanceId);
	}
	
	public static NetworkStats getNetworkStats(String instanceId) throws Exception {
		return OURVIRT.getNetworkStats(HYPERVISOR, instanceId);
	}
	
	public static List<DiskStats> getDiskStats(String instanceId) throws Exception {
		return OURVIRT.getDiskStats(HYPERVISOR, instanceId);
	}
	
	private static VirtualMachineStatus getStatus(String instanceId) throws Exception {
		return OURVIRT.status(HYPERVISOR, instanceId);
	}
	
	public static void attachDevice(String instanceID, String localDevicePath) throws Exception {
		OURVIRT.attachDevice(HYPERVISOR, instanceID, localDevicePath);
	}

	public static void detachDevice(String instanceId, String localDevicePath) throws Exception {
		OURVIRT.detachDevice(HYPERVISOR, instanceId, localDevicePath);
	}

	public static String getConsoleOutput(String instanceId) throws Exception {
		return OURVIRT.getConsoleOutput(HYPERVISOR, instanceId);
	}
}

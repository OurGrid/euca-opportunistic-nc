package org.ourgrid.node;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.node.idleness.TestIdlenessChecker;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.model.VBR;
import org.ourgrid.node.util.NodeProperties;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.node.util.WalrusUtils;
import org.ourgrid.virt.OurVirt;
import org.ourgrid.virt.model.HypervisorType;
import org.ourgrid.virt.model.VirtualMachineConstants;
import org.ourgrid.virt.model.VirtualMachineStatus;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcRunInstance;

/**
 * @author tarciso
 * Tests methods which cannot be tested through normal test classes,
 * due to concurrency or other problems.
 */
public class TestOurVirtUtils {
	
	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private TestIdlenessChecker testIChecker = new TestIdlenessChecker();
	private InstanceRepository instanceRepository = new InstanceRepository();
	
	private Properties properties;
	
	@Before
	public void init() throws Exception {
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		facade = new NodeFacade(properties, testIChecker, null, instanceRepository);
		OurVirtUtils.setOurVirt(ourvirtMock);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRebootNotCreatedInstance() throws Exception {
		
		InstanceType instance = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.NOT_CREATED); 
		
		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
				instance.getImageId(), properties);
	}
	
	@Test
	public void testRebootNotRegisteredInstance() throws Exception {
		
		InstanceType instance = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.NOT_REGISTERED, 
						VirtualMachineStatus.POWERED_OFF);
		
		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
				instance.getImageId(), properties);
		
		Mockito.verify(ourvirtMock).register(instance.getInstanceId(), 
				new HashMap<String, String>());
		
		Mockito.verify(ourvirtMock).reboot(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId()));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRebootPoweredOffInstance() throws Exception {
		
		InstanceType instance = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.POWERED_OFF);
		
		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
				instance.getImageId(), properties);
		
	}
	
	@Test
	public void testRebootRunningInstance() throws Exception {
		
		InstanceType instance = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.RUNNING, 
						VirtualMachineStatus.POWERED_OFF);
		
		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
				instance.getImageId(), properties);
		
		Mockito.verify(ourvirtMock).reboot(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId()));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRunRunningInstance() throws Exception {
		
		NcRunInstance runInstanceReq = 
				TestNcRunInstance.getNewRunInstanceRequest();
		
		InstanceType instance = TestUtils.addInstanceToRepository(
				TestNcRunInstance.getInstanceFromRequest(runInstanceReq), 
				facade,instanceRepository);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.RUNNING);
		
		VBR instVBR = new VBR();
		instVBR.setMachineImageId(instance.getImageId());
		instVBR.setKernelId(instance.getKernelId());
		instVBR.setRamDiskId(instance.getRamdiskId());
		
		OurVirtUtils.runInstance(
				runInstanceReq.getNcRunInstance(), instVBR, properties);
	}
	
	@Test
	public void testRunNotRegisteredInstance() throws Exception {
		
		NcRunInstance runInstanceReq = 
				TestNcRunInstance.getNewRunInstanceRequest();
		
		InstanceType instance = TestUtils.addInstanceToRepository(
				TestNcRunInstance.getInstanceFromRequest(runInstanceReq), 
				facade,instanceRepository);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.NOT_REGISTERED);
		
		VBR instVBR = new VBR();
		instVBR.setMachineImageId(instance.getImageId());
		instVBR.setKernelId(instance.getKernelId());
		instVBR.setRamDiskId(instance.getRamdiskId());
		
		String imagePath = WalrusUtils.getImagePath(instVBR.getMachineImageId(), 
				properties);
		
		String tempImage = properties.getProperty(NodeProperties.CLONEROOT)
				+ File.separator 
				+ instance.getInstanceId() 
				+ WalrusUtils.IMG_EXT;
		
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(VirtualMachineConstants.GUEST_USER, 
				properties.getProperty(NodeProperties.GUEST_USER));
		conf.put(VirtualMachineConstants.GUEST_PASSWORD, 
				properties.getProperty(NodeProperties.GUEST_PASSWD));
		conf.put(VirtualMachineConstants.MEMORY, String.valueOf(
				instance.getInstanceType().getMemory()));
		conf.put(VirtualMachineConstants.OS, instance.getPlatform());
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
        String macAddress = instance.getNetParams().getPrivateMacAddress();
        macAddress = macAddress.replace(":", "");
        conf.put(VirtualMachineConstants.MAC, macAddress);
		
		
		OurVirtUtils.runInstance(
				runInstanceReq.getNcRunInstance(), instVBR, properties);
		
		Mockito.verify(ourvirtMock).clone(Mockito.any(HypervisorType.class),
				Mockito.eq(imagePath), Mockito.eq(tempImage));
		
		Mockito.verify(ourvirtMock).register(Mockito.eq(instance.getInstanceId()), 
				Mockito.eq(conf));
		
		for (Entry<String, Object> confEntry : conf.entrySet()) {
			Mockito.verify(ourvirtMock).setProperty(
					Mockito.any(HypervisorType.class),
					Mockito.eq(instance.getInstanceId()), 
					Mockito.eq(confEntry.getKey()), 
					Mockito.eq(confEntry.getValue()));
        }
		
		Mockito.verify(ourvirtMock).create(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));
		
		Mockito.verify(ourvirtMock).start(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));
		
	}
	
	@Test
	public void testRunPoweredOffInstance() throws Exception {
		
		NcRunInstance runInstanceReq = 
				TestNcRunInstance.getNewRunInstanceRequest();
		
		InstanceType instance = TestUtils.addInstanceToRepository(
				TestNcRunInstance.getInstanceFromRequest(runInstanceReq), 
				facade, instanceRepository);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.POWERED_OFF);
		
		VBR instVBR = new VBR();
		instVBR.setMachineImageId(instance.getImageId());
		instVBR.setKernelId(instance.getKernelId());
		instVBR.setRamDiskId(instance.getRamdiskId());
		
		String imagePath = WalrusUtils.getImagePath(instVBR.getMachineImageId(), 
				properties);
		
		String tempImage = properties.getProperty(NodeProperties.CLONEROOT)
				+ File.separator 
				+ instance.getInstanceId() 
				+ WalrusUtils.IMG_EXT;
		
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(VirtualMachineConstants.GUEST_USER, 
				properties.getProperty(NodeProperties.GUEST_USER));
		conf.put(VirtualMachineConstants.GUEST_PASSWORD, 
				properties.getProperty(NodeProperties.GUEST_PASSWD));
		conf.put(VirtualMachineConstants.MEMORY, String.valueOf(
				instance.getInstanceType().getMemory()));
		conf.put(VirtualMachineConstants.OS, instance.getPlatform());
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
        String macAddress = instance.getNetParams().getPrivateMacAddress();
        macAddress = macAddress.replace(":", "");
        conf.put(VirtualMachineConstants.MAC, macAddress);
		
		
		OurVirtUtils.runInstance(
				runInstanceReq.getNcRunInstance(), instVBR, properties);
		
		Mockito.verify(ourvirtMock).clone(Mockito.any(HypervisorType.class),
				Mockito.eq(imagePath), Mockito.eq(tempImage));
		
		for (Entry<String, Object> confEntry : conf.entrySet()) {
			Mockito.verify(ourvirtMock).setProperty(
					Mockito.any(HypervisorType.class),
					Mockito.eq(instance.getInstanceId()), 
					Mockito.eq(confEntry.getKey()), 
					Mockito.eq(confEntry.getValue()));
        }
		
		Mockito.verify(ourvirtMock).create(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));
		
		Mockito.verify(ourvirtMock).start(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));
		
	}
	
	
	
}

package org.ourgrid.node;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourgrid.node.model.VBR;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.virt.OurVirt;
import org.ourgrid.virt.model.HypervisorType;
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
	private Properties properties;
	private boolean idlenessEnabled = false;
	
	@Before
	public void init() throws FileNotFoundException, IOException {
		OurVirtUtils.setOurVirt(ourvirtMock);
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		properties.setProperty("idleness.enabled", String.valueOf(idlenessEnabled));
		facade = new NodeFacade(properties);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRebootNotCreatedInstance() throws Exception {
		
		InstanceType instance = TestUtils.addInstanceToRepository(facade);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.NOT_CREATED); 
		
		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
				instance.getImageId(), properties);
	}
	
	@Test
	public void testRebootNotRegisteredInstance() throws Exception {
		
		InstanceType instance = TestUtils.addInstanceToRepository(facade);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.NOT_REGISTERED, 
						VirtualMachineStatus.POWERED_OFF);
		
		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
				instance.getImageId(), properties);
		
		Mockito.verify(ourvirtMock).register(instance.getInstanceId(), 
				new HashMap<String, String>());
		
		Mockito.verify(ourvirtMock).stop(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId()));
		
		Mockito.verify(ourvirtMock).start(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId()));
	}
	
	@Test
	public void testRebootPoweredOffInstance() throws Exception {
		
		InstanceType instance = TestUtils.addInstanceToRepository(facade);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.POWERED_OFF);
		
		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
				instance.getImageId(), properties);
		
		Mockito.verify(ourvirtMock).start(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId()));
	}
	
	@Test
	public void testRebootRunningInstance() throws Exception {
		
		InstanceType instance = TestUtils.addInstanceToRepository(facade);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.RUNNING, 
						VirtualMachineStatus.POWERED_OFF);
		
		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
				instance.getImageId(), properties);
		
		Mockito.verify(ourvirtMock).stop(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId()));
		
		Mockito.verify(ourvirtMock).start(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId()));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRunRunningInstance() throws Exception {
		
		NcRunInstance runInstanceReq = 
				TestNcRunInstance.getNewRunInstanceRequest();
		
		InstanceType instance = TestUtils.addInstanceToRepository(
				TestNcRunInstance.getInstanceFromRequest(runInstanceReq), facade);
		
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

}

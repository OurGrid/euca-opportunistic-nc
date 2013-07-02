package org.ourgrid.node;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.util.NodeProperties;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.virt.OurVirt;
import org.ourgrid.virt.model.HypervisorType;
import org.ourgrid.virt.model.VirtualMachineStatus;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcTerminateInstance;
import edu.ucsb.eucalyptus.NcTerminateInstanceResponse;
import edu.ucsb.eucalyptus.NcTerminateInstanceResponseType;
import edu.ucsb.eucalyptus.NcTerminateInstanceType;


public class TestNcTerminateInstance {
	
	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private Properties properties;
	private boolean idlenessEnabled = false;
	private static final String INSTANCE_ID = "001"; 
	private static final String USER_ID = "user001";
	private static final String VDI_EXT = ".vdi";
	
	@Before
	public void init() throws FileNotFoundException, IOException {
		OurVirtUtils.setOurVirt(ourvirtMock);
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		properties.setProperty("idleness.enabled", String.valueOf(idlenessEnabled));
		facade = new NodeFacade(properties);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNCNotAvailable() throws Exception {
		
		idlenessEnabled = true;
		init();
		
		NcTerminateInstance termInstanceReq = createTermInstanceRequest(
				INSTANCE_ID, USER_ID);
		
		facade.terminateInstance(termInstanceReq);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInexistentInstance() throws Exception {
		
		NcTerminateInstance termInstanceReq = createTermInstanceRequest(
				INSTANCE_ID, USER_ID);
		
		facade.terminateInstance(termInstanceReq);				
	}
	
	@Test
	public void testInexistentCloneFile() throws Exception {
		
		addInstanceToRepository(INSTANCE_ID, USER_ID);
		
		NcTerminateInstance termInstanceReq = createTermInstanceRequest(
				INSTANCE_ID, USER_ID);
		
		String cloneFilePath = properties.getProperty(NodeProperties.CLONEROOT) +
				File.separator + INSTANCE_ID + VDI_EXT;
		
		File cloneFile = new File(cloneFilePath);
		
		if (cloneFile.exists()) {
			if (cloneFile.delete()) {
				throw new Exception("Could not delete clone file at: " + 
						cloneFilePath + " for test.");
			}
		}
		
		Mockito.when(ourvirtMock.status(HypervisorType.VBOXSDK, 
				INSTANCE_ID)).thenReturn(VirtualMachineStatus.RUNNING);
		
		facade.terminateInstance(termInstanceReq);
		
		Mockito.verify(ourvirtMock).destroy(HypervisorType.VBOXSDK, INSTANCE_ID);
		
		Assert.assertFalse(cloneFile.exists());
		
	}
	
	
	@Test
	public void testCloneFileDeleted() throws Exception {
		
		addInstanceToRepository(INSTANCE_ID, USER_ID);
		
		NcTerminateInstance termInstanceReq = createTermInstanceRequest(
				INSTANCE_ID, USER_ID);
		
		String cloneFilePath = properties.getProperty(NodeProperties.CLONEROOT) +
				File.separator + INSTANCE_ID + VDI_EXT;
		
		File cloneFile = new File(cloneFilePath);
		
		if (!cloneFile.createNewFile()) {
			throw new Exception("Could not create clone file at: " + 
					cloneFilePath + " for test.");
		}
		
		Mockito.when(ourvirtMock.status(HypervisorType.VBOXSDK, 
				INSTANCE_ID)).thenReturn(VirtualMachineStatus.RUNNING);
		
		facade.terminateInstance(termInstanceReq);
		
		Mockito.verify(ourvirtMock).destroy(HypervisorType.VBOXSDK, INSTANCE_ID);
		
		Assert.assertFalse(cloneFile.exists());
		
	}

	
	@Test
	public void testNotRegisteredVM() throws Exception {
		
		addInstanceToRepository(INSTANCE_ID, USER_ID);
		
		NcTerminateInstance termInstanceReq = createTermInstanceRequest(
				INSTANCE_ID, USER_ID);
		
		Mockito.when(ourvirtMock.status(HypervisorType.VBOXSDK, 
				INSTANCE_ID)).thenReturn(VirtualMachineStatus.NOT_REGISTERED);

		facade.terminateInstance(termInstanceReq);
		
		Mockito.verify(ourvirtMock).register(INSTANCE_ID, 
				new HashMap<String, String>());
		Mockito.verify(ourvirtMock).destroy(HypervisorType.VBOXSDK, INSTANCE_ID);
	}
	
	@Test
	public void testNotCreatedVM() throws Exception {
		
		addInstanceToRepository(INSTANCE_ID, USER_ID);
		
		NcTerminateInstance termInstanceReq = createTermInstanceRequest(
				INSTANCE_ID, USER_ID);
		
		Mockito.when(ourvirtMock.status(HypervisorType.VBOXSDK, 
				INSTANCE_ID)).thenReturn(VirtualMachineStatus.NOT_CREATED);

		facade.terminateInstance(termInstanceReq);
		
		Mockito.verify(ourvirtMock).destroy(HypervisorType.VBOXSDK, INSTANCE_ID);
	}
	
	@Test
	public void testPoweredOffVM() throws Exception {
		
		addInstanceToRepository(INSTANCE_ID, USER_ID);
		
		NcTerminateInstance termInstanceReq = createTermInstanceRequest(
				INSTANCE_ID, USER_ID);
		
		Mockito.when(ourvirtMock.status(HypervisorType.VBOXSDK, 
				INSTANCE_ID)).thenReturn(VirtualMachineStatus.POWERED_OFF);

		facade.terminateInstance(termInstanceReq);
		
		Mockito.verify(ourvirtMock).destroy(HypervisorType.VBOXSDK, INSTANCE_ID);
	}

	
	
	@Test
	public void testMainFlow() throws Exception {
		
		addInstanceToRepository(INSTANCE_ID, USER_ID);
		
		NcTerminateInstance termInstanceReq = createTermInstanceRequest(
				INSTANCE_ID, USER_ID);
		
		Mockito.when(ourvirtMock.status(HypervisorType.VBOXSDK, 
				INSTANCE_ID)).thenReturn(VirtualMachineStatus.RUNNING);

		NcTerminateInstanceResponse termInstanceResponse = 
				facade.terminateInstance(termInstanceReq);
		
		Mockito.verify(ourvirtMock).destroy(HypervisorType.VBOXSDK, INSTANCE_ID);
		
		NcTerminateInstanceResponseType termInstanceRespType = 
				termInstanceResponse.getNcTerminateInstanceResponse();
		
		Assert.assertTrue(termInstanceRespType.getInstanceId().equals(
				INSTANCE_ID));
		Assert.assertTrue(termInstanceRespType.getUserId().equals(
				USER_ID));
				
	}
	
	private NcTerminateInstance createTermInstanceRequest(String instanceId, String userId) {
		NcTerminateInstanceType termInstanceType = new NcTerminateInstanceType();
		termInstanceType.setInstanceId(instanceId);
		termInstanceType.setUserId(userId);
		
		NcTerminateInstance termInstanceReq = new NcTerminateInstance();
		termInstanceReq.setNcTerminateInstance(termInstanceType);
		return termInstanceReq;
	}
	
	private void addInstanceToRepository(String instanceId, String userId) {
		InstanceType instance = new InstanceType();
		instance.setInstanceId(instanceId);
		instance.setUserId(userId);
		
		InstanceRepository iRep = new InstanceRepository();
		iRep.addInstance(instance);
		
		facade.setInstanceRepository(iRep);
	}
}

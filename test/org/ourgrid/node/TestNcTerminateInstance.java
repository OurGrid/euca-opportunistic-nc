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
import org.ourgrid.node.util.NodeProperties;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.virt.OurVirt;
import org.ourgrid.virt.model.HypervisorType;
import org.ourgrid.virt.model.VirtualMachineStatus;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcTerminateInstance;
import edu.ucsb.eucalyptus.NcTerminateInstanceResponseType;
import edu.ucsb.eucalyptus.NcTerminateInstanceType;

public class TestNcTerminateInstance {

	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private Properties properties;
	private boolean idlenessEnabled = false;
	private static final String SHUTDOWN_STATE = "0";
	private static final String PREVIOUS_STATE = "0";

	@Before
	public void init() throws FileNotFoundException, IOException {
		OurVirtUtils.setOurVirt(ourvirtMock);
		properties = new Properties();
		properties
				.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		properties.setProperty("idleness.enabled",
				String.valueOf(idlenessEnabled));
		facade = new NodeFacade(properties);
	}

	@Test(expected = IllegalStateException.class)
	public void testNCNotAvailable() throws Exception {

		idlenessEnabled = true;
		init();

		InstanceType instance = TestUtils.addInstanceToRepository(facade);

		NcTerminateInstance terminateReq = createTermInstanceRequest(
				instance.getInstanceId(), instance.getUserId());

		facade.terminateInstance(terminateReq);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testInexistentInstance() throws Exception {

		NcTerminateInstance terminateReq = createTermInstanceRequest("", "");

		facade.terminateInstance(terminateReq);
	}

	@Test
	public void testInexistentCloneFile() throws Exception {

		InstanceType instance = TestUtils.addInstanceToRepository(facade);

		NcTerminateInstance terminateReq = createTermInstanceRequest(
				instance.getInstanceId(), instance.getUserId());

		String cloneFilePath = properties.getProperty(NodeProperties.CLONEROOT)
				+ File.separator + instance.getInstanceId() + TestUtils.VDI_EXT;

		File cloneFile = new File(cloneFilePath);

		if (cloneFile.exists()) {
			if (cloneFile.delete()) {
				throw new Exception("Could not delete clone file at: "
						+ cloneFilePath + " for test.");
			}
		}

		Mockito.when(
				ourvirtMock.status(Mockito.any(HypervisorType.class),
						Mockito.eq(instance.getInstanceId()))).thenReturn(
				VirtualMachineStatus.RUNNING);

		facade.terminateInstance(terminateReq);

		Mockito.verify(ourvirtMock).destroy(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));

		Assert.assertFalse(cloneFile.exists());

	}

	@Test
	public void testCloneFileDeleted() throws Exception {

		InstanceType instance = TestUtils.addInstanceToRepository(facade);

		NcTerminateInstance terminateReq = createTermInstanceRequest(
				instance.getInstanceId(), instance.getUserId());

		String cloneFilePath = properties.getProperty(NodeProperties.CLONEROOT)
				+ File.separator + instance.getInstanceId() + TestUtils.VDI_EXT;

		File cloneFile = new File(cloneFilePath);

		if (!cloneFile.createNewFile()) {
			throw new Exception("Could not create clone file at: "
					+ cloneFilePath + " for test.");
		}

		Mockito.when(
				ourvirtMock.status(Mockito.any(HypervisorType.class),
						Mockito.eq(instance.getInstanceId()))).thenReturn(
				VirtualMachineStatus.RUNNING);

		facade.terminateInstance(terminateReq);

		Mockito.verify(ourvirtMock).destroy(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));

		Assert.assertFalse(cloneFile.exists());

	}

	@Test
	public void testNotRegisteredVM() throws Exception {

		InstanceType instance = TestUtils.addInstanceToRepository(facade);

		NcTerminateInstance terminateReq = createTermInstanceRequest(
				instance.getInstanceId(), instance.getUserId());

		Mockito.when(
				ourvirtMock.status(Mockito.any(HypervisorType.class),
						Mockito.eq(instance.getInstanceId()))).thenReturn(
				VirtualMachineStatus.NOT_REGISTERED);

		facade.terminateInstance(terminateReq);

		Mockito.verify(ourvirtMock).register(instance.getInstanceId(),
				new HashMap<String, String>());
		Mockito.verify(ourvirtMock).destroy(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));
	}

	@Test
	public void testNotCreatedVM() throws Exception {

		InstanceType instance = TestUtils.addInstanceToRepository(facade);

		NcTerminateInstance terminateReq = createTermInstanceRequest(
				instance.getInstanceId(), instance.getUserId());

		Mockito.when(
				ourvirtMock.status(Mockito.any(HypervisorType.class),
						Mockito.eq(instance.getInstanceId()))).thenReturn(
				VirtualMachineStatus.NOT_CREATED);

		facade.terminateInstance(terminateReq);

		Mockito.verify(ourvirtMock).destroy(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));
	}

	@Test
	public void testPoweredOffVM() throws Exception {

		InstanceType instance = TestUtils.addInstanceToRepository(facade);

		NcTerminateInstance terminateReq = createTermInstanceRequest(
				instance.getInstanceId(), instance.getUserId());

		Mockito.when(
				ourvirtMock.status(Mockito.any(HypervisorType.class),
						Mockito.eq(instance.getInstanceId()))).thenReturn(
				VirtualMachineStatus.POWERED_OFF);

		facade.terminateInstance(terminateReq);

		Mockito.verify(ourvirtMock).destroy(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));
	}

	@Test
	public void testMainFlow() throws Exception {

		InstanceType instance = TestUtils.addInstanceToRepository(facade);

		NcTerminateInstance terminateReq = createTermInstanceRequest(
				instance.getInstanceId(), instance.getUserId());

		Mockito.when(
				ourvirtMock.status(Mockito.any(HypervisorType.class),
						Mockito.eq(instance.getInstanceId()))).thenReturn(
				VirtualMachineStatus.RUNNING);

		NcTerminateInstanceResponseType response = facade
				.terminateInstance(terminateReq).getNcTerminateInstanceResponse();

		Mockito.verify(ourvirtMock).destroy(Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()));

		Assert.assertTrue(response.get_return());
		Assert.assertEquals(response.getCorrelationId(),
				terminateReq.getNcTerminateInstance().getCorrelationId());
		Assert.assertEquals(response.getUserId(), instance.getUserId());

		Assert.assertEquals(response.getInstanceId(), instance.getInstanceId());
		Assert.assertTrue(response.getShutdownState() == SHUTDOWN_STATE);
		Assert.assertTrue(response.getPreviousState() == PREVIOUS_STATE);

	}

	private NcTerminateInstance createTermInstanceRequest(String instanceId,
			String userId) {
		NcTerminateInstanceType termInstanceType = new NcTerminateInstanceType();
		termInstanceType.setInstanceId(instanceId);
		termInstanceType.setUserId(userId);
		termInstanceType.setCorrelationId(
				String.valueOf(TestUtils.correlationId++));

		NcTerminateInstance terminateReq = new NcTerminateInstance();
		terminateReq.setNcTerminateInstance(termInstanceType);
		return terminateReq;
	}

}

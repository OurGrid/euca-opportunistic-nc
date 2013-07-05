package org.ourgrid.node;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.virt.OurVirt;
import org.ourgrid.virt.model.HypervisorType;
import org.ourgrid.virt.model.VirtualMachineStatus;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.OutputUtil;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcRebootInstance;
import edu.ucsb.eucalyptus.NcRebootInstanceResponse;
import edu.ucsb.eucalyptus.NcRebootInstanceResponseType;
import edu.ucsb.eucalyptus.NcRebootInstanceType;


public class TestNcRebootInstance {
	
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
	public void testNCNotAvailable() throws Exception {
		
		idlenessEnabled = true;
		init();
		
		InstanceType instance = TestUtils.addInstanceToRepository(facade);
		
		NcRebootInstance rebootInstanceReq = createRebootInstanceRequest(
				instance.getInstanceId(), instance.getUserId());
		
		facade.rebootInstance(rebootInstanceReq);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInexistentInstance() throws Exception {
		
		NcRebootInstance rebootInstanceReq = createRebootInstanceRequest(
				"","");
		
		facade.rebootInstance(rebootInstanceReq);			
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNotCreatedInstance() throws Exception {
		
		InstanceType instance = TestUtils.addInstanceToRepository(facade);
		
		NcRebootInstance rebootInstanceReq = createRebootInstanceRequest(
				instance.getInstanceId(), instance.getUserId());
		
		NcRebootInstanceResponse response = facade.rebootInstance(
				rebootInstanceReq);
		
		NcRebootInstanceResponseType responseType = 
				response.getNcRebootInstanceResponse();
				
		Thread.sleep(5000);
		
//		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
//				instance.getImageId(), properties);
	}
	
//	@Test()
	public void testNotRegisteredInstance() throws Exception {
		
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
	
//	@Test()
	public void testPoweredOffInstance() throws Exception {
		
		InstanceType instance = TestUtils.addInstanceToRepository(facade);
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.POWERED_OFF);
		
		OurVirtUtils.rebootInstance(instance.getInstanceId(), 
				instance.getImageId(), properties);
		
		Mockito.verify(ourvirtMock).start(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId()));
	}
	
	private NcRebootInstance createRebootInstanceRequest(String instanceId, String userId) {
		NcRebootInstanceType rebootInstanceType = new NcRebootInstanceType();
		rebootInstanceType.setInstanceId(instanceId);
		rebootInstanceType.setUserId(userId);
		
		NcRebootInstance rebootInstanceReq = new NcRebootInstance();
		rebootInstanceReq.setNcRebootInstance(rebootInstanceType);
		return rebootInstanceReq;
	}
	
}

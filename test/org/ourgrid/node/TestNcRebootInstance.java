package org.ourgrid.node;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.virt.OurVirt;
import org.ourgrid.virt.model.HypervisorType;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcRebootInstance;
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
	
//	@Test
	public void testNotCreatedInstance() throws Exception {
		
		InstanceType instance = TestUtils.addInstanceToRepository(facade);
		
		NcRebootInstance rebootInstanceReq = createRebootInstanceRequest(
				instance.getInstanceId(), instance.getUserId());
		
//		when(ourvirtMock.status(Mockito.any(), vmName));
		
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

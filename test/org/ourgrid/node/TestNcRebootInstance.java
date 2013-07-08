package org.ourgrid.node;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.virt.OurVirt;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcRebootInstance;
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
		
		NcRebootInstance rebootReq = createRebootInstanceRequest(
				instance.getInstanceId(), instance.getUserId());
		
		facade.rebootInstance(rebootReq);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInexistentInstance() throws Exception {
		
		NcRebootInstance rebootReq = createRebootInstanceRequest(
				"","");
		
		facade.rebootInstance(rebootReq);			
	}
	
	@Test
	public void testMainFlow() throws Exception {
		InstanceType instance = TestUtils.addInstanceToRepository(facade);
		
		NcRebootInstance rebootReq = createRebootInstanceRequest(
				instance.getInstanceId(), instance.getUserId());
		
		NcRebootInstanceResponseType response = facade.rebootInstance(
				rebootReq).getNcRebootInstanceResponse();
		
		Assert.assertTrue(response.get_return());
		Assert.assertEquals(response.getCorrelationId(),
				rebootReq.getNcRebootInstance().getCorrelationId());
		Assert.assertEquals(response.getUserId(),instance.getUserId());
		
		Assert.assertTrue(response.getStatus());
		
	}
	
	private NcRebootInstance createRebootInstanceRequest(String instanceId, String userId) {
		NcRebootInstanceType rebootInstanceType = new NcRebootInstanceType();
		rebootInstanceType.setInstanceId(instanceId);
		rebootInstanceType.setUserId(userId);
		rebootInstanceType.setCorrelationId(
				String.valueOf(TestUtils.correlationId++));
		
		NcRebootInstance rebootInstanceReq = new NcRebootInstance();
		rebootInstanceReq.setNcRebootInstance(rebootInstanceType);
		return rebootInstanceReq;
	}
	
}

package org.ourgrid.node;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.virt.OurVirt;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcDescribeInstances;
import edu.ucsb.eucalyptus.NcDescribeInstancesResponseType;
import edu.ucsb.eucalyptus.NcDescribeInstancesType;


public class TestNcDescribeInstances {
	
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
		
		NcDescribeInstances descInstancesReq = createDescribeInstancesRequest();
		
		facade.describeInstances(descInstancesReq);
		
	}
	
	@Test
	public void testNoInstances() throws Exception {
		
		NcDescribeInstances descInstancesReq = createDescribeInstancesRequest();
		
		NcDescribeInstancesResponseType response = 
				facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		Assert.assertTrue(response.get_return());
		Assert.assertEquals(response.getCorrelationId(),
				descInstancesReq.getNcDescribeInstances().getCorrelationId());
		Assert.assertEquals(response.getUserId(),
				descInstancesReq.getNcDescribeInstances().getUserId());
		
		Assert.assertTrue(response.getInstances().length == 0);
		
	}
	
	@Test
	public void testMainFlow() throws Exception {
		
		InstanceType instance1 = TestUtils.addInstanceToRepository(facade);
		InstanceType instance2 = TestUtils.addInstanceToRepository(facade);
		InstanceType instance3 = TestUtils.addInstanceToRepository(facade);
		
		NcDescribeInstances descInstancesReq = createDescribeInstancesRequest();
		
		NcDescribeInstancesResponseType response = 
				facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		Assert.assertTrue(response.get_return());
		Assert.assertEquals(response.getCorrelationId(),
				descInstancesReq.getNcDescribeInstances().getCorrelationId());
		Assert.assertEquals(response.getUserId(),
				descInstancesReq.getNcDescribeInstances().getUserId());
		
		Set<InstanceType> returnedInstances = new HashSet<InstanceType>(
				Arrays.asList(response.getInstances()));
		
		Assert.assertTrue(returnedInstances.size() == 3);
		Assert.assertTrue(returnedInstances.contains(instance1));
		Assert.assertTrue(returnedInstances.contains(instance2));
		Assert.assertTrue(returnedInstances.contains(instance3));
		
	}
	
	@Test
	public void testAddingInstances() throws Exception {
		
		InstanceType instance1 = TestUtils.addInstanceToRepository(facade);
		
		NcDescribeInstances descInstancesReq = createDescribeInstancesRequest();
		
		NcDescribeInstancesResponseType response = 
				facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		Set<InstanceType> returnedInstances = new HashSet<InstanceType>(
				Arrays.asList(response.getInstances()));
		
		Assert.assertTrue(returnedInstances.size() == 1);
		Assert.assertTrue(returnedInstances.contains(instance1));
		
		InstanceType instance2 = TestUtils.addInstanceToRepository(facade);
		
		response = facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		returnedInstances = new HashSet<InstanceType>(
				Arrays.asList(response.getInstances()));
		
		Assert.assertTrue(returnedInstances.size() == 2);
		Assert.assertTrue(returnedInstances.contains(instance1));
		Assert.assertTrue(returnedInstances.contains(instance2));
	}
	
	@Test
	public void testRemovingInstances() throws Exception {
		
		InstanceType instance1 = TestUtils.addInstanceToRepository(facade);
		
		NcDescribeInstances descInstancesReq = createDescribeInstancesRequest();
		
		NcDescribeInstancesResponseType response = 
				facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		Set<InstanceType> returnedInstances = new HashSet<InstanceType>(
				Arrays.asList(response.getInstances()));
		
		Assert.assertTrue(returnedInstances.size() == 1);
		Assert.assertTrue(returnedInstances.contains(instance1));
		
		TestUtils.removeInstanceFromRepository(instance1, facade);
		
		response = facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		Assert.assertTrue(response.getInstances().length == 0);
	}
	
	private NcDescribeInstances createDescribeInstancesRequest() {
		NcDescribeInstancesType descInstancesType = new NcDescribeInstancesType();
		descInstancesType.setUserId(TestUtils.DEFAULT_USER_ID);
		descInstancesType.setCorrelationId(
				String.valueOf(TestUtils.getNewCorrelationId()));
		
		NcDescribeInstances descInstancesReq = new NcDescribeInstances();
		descInstancesReq.setNcDescribeInstances(descInstancesType);
		return descInstancesReq;
	}
	
}

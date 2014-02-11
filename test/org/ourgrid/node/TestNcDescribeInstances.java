package org.ourgrid.node;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.node.idleness.TestIdlenessChecker;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.node.util.Sensor;
import org.ourgrid.virt.OurVirt;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcDescribeInstances;
import edu.ucsb.eucalyptus.NcDescribeInstancesResponseType;
import edu.ucsb.eucalyptus.NcDescribeInstancesType;


public class TestNcDescribeInstances {
	
	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private TestIdlenessChecker testIChecker = new TestIdlenessChecker();
	private InstanceRepository instanceRepository = new InstanceRepository();
	private Properties properties;
	
	@Before
	public void init() throws Exception {
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		Sensor sensor = new Sensor(0, instanceRepository);
		facade = new NodeFacade(properties, testIChecker, null, instanceRepository, sensor);
		OurVirtUtils.setOurVirt(ourvirtMock);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNCNotAvailable() throws Exception {
		
		testIChecker.setIdle(false);
		
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
		
		Assert.assertEquals(response.getInstances().length, 0);
		
	}
	
	@Test
	public void testMainFlow() throws Exception {
		
		InstanceType instance1 = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		InstanceType instance2 = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		InstanceType instance3 = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		
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
		
		Assert.assertEquals(returnedInstances.size(),3);
		Assert.assertTrue(returnedInstances.contains(instance1));
		Assert.assertTrue(returnedInstances.contains(instance2));
		Assert.assertTrue(returnedInstances.contains(instance3));
		
	}
	
	@Test
	public void testAddingInstances() throws Exception {
		
		InstanceType instance1 = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		
		NcDescribeInstances descInstancesReq = createDescribeInstancesRequest();
		
		NcDescribeInstancesResponseType response = 
				facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		Set<InstanceType> returnedInstances = new HashSet<InstanceType>(
				Arrays.asList(response.getInstances()));
		
		Assert.assertEquals(returnedInstances.size(),1);
		Assert.assertTrue(returnedInstances.contains(instance1));
		
		InstanceType instance2 = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		
		response = facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		returnedInstances = new HashSet<InstanceType>(
				Arrays.asList(response.getInstances()));
		
		Assert.assertEquals(returnedInstances.size(),2);
		Assert.assertTrue(returnedInstances.contains(instance1));
		Assert.assertTrue(returnedInstances.contains(instance2));
	}
	
	@Test
	public void testRemovingInstances() throws Exception {
		
		InstanceType instance1 = TestUtils.addBasicInstanceToRepository(
				facade,instanceRepository);
		
		NcDescribeInstances descInstancesReq = createDescribeInstancesRequest();
		
		NcDescribeInstancesResponseType response = 
				facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		Set<InstanceType> returnedInstances = new HashSet<InstanceType>(
				Arrays.asList(response.getInstances()));
		
		Assert.assertEquals(returnedInstances.size(),1);
		Assert.assertTrue(returnedInstances.contains(instance1));
		
		TestUtils.removeInstanceFromRepository(instance1, 
				facade, instanceRepository);
		
		response = facade.describeInstances(descInstancesReq)
					.getNcDescribeInstancesResponse();
		
		Assert.assertEquals(response.getInstances().length,0);
	}
	
	private NcDescribeInstances createDescribeInstancesRequest() {
		NcDescribeInstancesType descInstancesType = new NcDescribeInstancesType();
		descInstancesType.setUserId(TestUtils.DEFAULT_USER_ID);
		descInstancesType.setCorrelationId(TestUtils.getNewCorrelationId());
		
		NcDescribeInstances descInstancesReq = new NcDescribeInstances();
		descInstancesReq.setNcDescribeInstances(descInstancesType);
		return descInstancesReq;
	}
	
}

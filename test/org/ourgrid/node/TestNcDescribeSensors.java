package org.ourgrid.node;

import java.io.FileInputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.node.idleness.TestIdlenessChecker;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.virt.OurVirt;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcDescribeSensors;
import edu.ucsb.eucalyptus.NcDescribeSensorsType;

public class TestNcDescribeSensors {

	private static final int DEF_HISTORY_SIZE = 1;
	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private TestIdlenessChecker testIChecker = new TestIdlenessChecker();
	private InstanceRepository instanceRepository = new InstanceRepository();
	private Properties properties;
	private static final String STATUS_MSG = "0";
	private static final int DEF_COLLECTION_INT_TIME_MS = 30; 
	
	@Before
	public void init() throws Exception {
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		facade = new NodeFacade(properties, testIChecker, null, instanceRepository);
		OurVirtUtils.setOurVirt(ourvirtMock);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNCNotAvailable() throws Exception {
		
		testIChecker.setIdle(false);
		
		String[] emptyArray = new String[1];
		
		NcDescribeSensors describeSensorsReq = createDescribeSensorsRequest(
				emptyArray, emptyArray);
		
		facade.describeSensors(describeSensorsReq);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInexistentInstance() throws Exception {
		
		String[] instancesIds = {TestUtils.DEFAULT_INSTANCE_ID};
		
		NcDescribeSensors describeSensorsReq = createDescribeSensorsRequest(
				instancesIds, instancesIds);
		
		facade.describeSensors(describeSensorsReq);			
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNonEmptySensorsArray() throws Exception {
		
		InstanceType instance = TestUtils.addInstanceWithSensorToRepository(
				facade, instanceRepository);
		
		String[] instancesIds = {instance.getInstanceId()};
		String[] sensorsIds = {TestUtils.DEFAULT_INSTANCE_ID};
		
		NcDescribeSensors describeSensorsReq = createDescribeSensorsRequest(
				instancesIds, sensorsIds);
		
		facade.describeSensors(describeSensorsReq);			
	}
	
	
	
	@Test
	public void testMainFlow() throws Exception {
		
		InstanceType instance = TestUtils.addBasicInstanceToRepository(
				facade, instanceRepository);
		
		
		
//			NcAssignAddress assignReq = createAssignAddressRequest(
//				instance.getInstanceId(), 
//				instance.getUserId(),
//				getPublicIp());
//		
//		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
//				Mockito.eq(instance.getInstanceId())))).thenReturn(
//						VirtualMachineStatus.RUNNING);
//		
//		NcAssignAddressResponseType response = 
//				facade.assignAddress(assignReq).getNcAssignAddressResponse();
//		
//		Mockito.verify(ourvirtMock).setProperty(
//				Mockito.any(HypervisorType.class),
//				Mockito.eq(instance.getInstanceId()),
//				Mockito.eq(VirtualMachineConstants.IP),
//				Mockito.eq(assignReq.getNcAssignAddress().getPublicIp()));
//		
//		Assert.assertTrue(response.get_return());
//		Assert.assertEquals(response.getUserId(),instance.getUserId());
//		Assert.assertEquals(response.getCorrelationId(),
//				assignReq.getNcAssignAddress().getCorrelationId());
//		Assert.assertEquals(response.getStatusMessage(),
//				assignReq.getNcAssignAddress().getStatusMessage());
		
	}
	
	private NcDescribeSensors createDescribeSensorsRequest(String[] instancesIds,
			String[] sensorsIds) {
		NcDescribeSensorsType describeSensorsType = new NcDescribeSensorsType();
		describeSensorsType.setUserId(TestUtils.getNewUserId());
		describeSensorsType.setCorrelationId(TestUtils.getNewCorrelationId());
		describeSensorsType.setHistorySize(DEF_HISTORY_SIZE);
		describeSensorsType.setCollectionIntervalTimeMs(DEF_COLLECTION_INT_TIME_MS);
		describeSensorsType.setInstanceIds(instancesIds);
		describeSensorsType.setSensorIds(sensorsIds);

		NcDescribeSensors describeSensors = new NcDescribeSensors();
		describeSensors.setNcDescribeSensors(describeSensorsType);
		
		return describeSensors;
	}
}

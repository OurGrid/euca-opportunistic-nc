package org.ourgrid.node;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.node.idleness.TestIdlenessChecker;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.model.sensor.MetricValue;
import org.ourgrid.node.model.sensor.SensorResource;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.node.util.Sensor;
import org.ourgrid.virt.OurVirt;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcDescribeSensors;
import edu.ucsb.eucalyptus.NcDescribeSensorsResponseType;
import edu.ucsb.eucalyptus.NcDescribeSensorsType;

public class TestNcDescribeSensors {

	private static final int DEF_HISTORY_SIZE = 1;
	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private TestIdlenessChecker testIChecker = new TestIdlenessChecker();
	private InstanceRepository instanceRepository = new InstanceRepository();
	private Properties properties;
	private Sensor sensor;
	private static final int DEF_COLLECTION_INT_TIME_MS = 30; 
	
	@Before
	public void init() throws Exception {
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		this.sensor = new Sensor(0, instanceRepository);
		facade = new NodeFacade(properties, testIChecker, null, instanceRepository, sensor);
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
				facade, instanceRepository, sensor);
		
		String[] instancesIds = {instance.getInstanceId()};
		String[] sensorsIds = {TestUtils.DEFAULT_INSTANCE_ID};
		
		NcDescribeSensors describeSensorsReq = createDescribeSensorsRequest(
				instancesIds, sensorsIds);
		
		facade.describeSensors(describeSensorsReq);			
	}
	
	
	@Test
	public void testMainFlow() throws Exception {
		
		InstanceType instance = TestUtils.addInstanceWithSensorToRepository(
				facade, instanceRepository, sensor);
		sensor.run();
		String[] instancesIds = {instance.getInstanceId()};
		String[] sensorsIds =  new String[1];
		
		NcDescribeSensors describeSensorsReq = createDescribeSensorsRequest(
				instancesIds, sensorsIds);
		
		NcDescribeSensorsType descSensorsReqType = describeSensorsReq.getNcDescribeSensors();
		
		NcDescribeSensorsResponseType response = facade.describeSensors(describeSensorsReq)
				.getNcDescribeSensorsResponse();
		
		Assert.assertTrue(response.get_return());
		Assert.assertEquals(descSensorsReqType.getUserId(), response.getUserId());
		Assert.assertEquals(descSensorsReqType.getCorrelationId(), response.getCorrelationId());
		
		Assert.assertTrue(response.getSensorsResources().length == 1);

//		Assert.assertEquals(response.getSensorsResources()[0],sResource);
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
	
	private SensorResource buildInstanceSensorResource(InstanceType instance) {
		
		MetricValue metricValue = new MetricValue(Calendar.getInstance(), 300);
		return null;
	}
}

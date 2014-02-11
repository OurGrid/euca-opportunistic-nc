package org.ourgrid.node;

import java.io.FileInputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.node.idleness.TestIdlenessChecker;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.node.util.ResourcesInfoGatherer;
import org.ourgrid.node.util.Sensor;
import org.ourgrid.virt.OurVirt;

import edu.ucsb.eucalyptus.NcPowerDown;
import edu.ucsb.eucalyptus.NcPowerDownResponseType;
import edu.ucsb.eucalyptus.NcPowerDownType;

public class TestNcPowerDown {
	private static final String LINUX = "Linux";
	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private TestIdlenessChecker testIChecker = new TestIdlenessChecker();
	private InstanceRepository instanceRepository = new InstanceRepository();
	private Properties properties;
	private ResourcesInfoGatherer rIGMock = 
			Mockito.mock(ResourcesInfoGatherer.class);
	private static final String SUCCESS_STATUS_MSG = "0";
	private static final String UNSUCCESS_STATUS_MSG = "2";
	
	@Before
	public void init() throws Exception {
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		Sensor sensor = new Sensor(0, instanceRepository);
		facade = new NodeFacade(properties, testIChecker, rIGMock, instanceRepository, sensor);
		OurVirtUtils.setOurVirt(ourvirtMock);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNCNotAvailable() throws Exception {
		testIChecker.setIdle(false);
		
		NcPowerDown powerDownReq = createPowerDownRequest();
		
		facade.powerDown(powerDownReq);
	}
	
	@Test
	public void testNonUnixOS() throws Exception {
		NcPowerDown powerDownReq = createPowerDownRequest();
		
		Mockito.when(rIGMock.getOSType()).thenReturn("");
		
		NcPowerDownResponseType response =  facade.powerDown(powerDownReq)
				.getNcPowerDownResponse();
		
		NcPowerDownType requestType = powerDownReq.getNcPowerDown();
		
		Assert.assertTrue(response.get_return());
		Assert.assertEquals(response.getUserId(), requestType.getUserId());
		Assert.assertEquals(response.getCorrelationId(), 
				requestType.getCorrelationId());
		Assert.assertEquals(UNSUCCESS_STATUS_MSG, response.getStatusMessage());
	}
	
	@Test
	public void testMainFlow() throws Exception {
		NcPowerDown powerDownReq = createPowerDownRequest();
		
		Mockito.when(rIGMock.getOSType()).thenReturn(LINUX);
		
		NcPowerDownResponseType response =  facade.powerDown(powerDownReq)
				.getNcPowerDownResponse();
		
		NcPowerDownType requestType = powerDownReq.getNcPowerDown();
		
		Assert.assertTrue(response.get_return());
		Assert.assertEquals(response.getUserId(), requestType.getUserId());
		Assert.assertEquals(response.getCorrelationId(), 
				requestType.getCorrelationId());
		Assert.assertEquals(response.getStatusMessage(), SUCCESS_STATUS_MSG);
	}
	
	private NcPowerDown createPowerDownRequest() {
		NcPowerDownType powerDownType = new NcPowerDownType();
		powerDownType.setUserId(TestUtils.getNewUserId());
		powerDownType.setCorrelationId(TestUtils.getNewCorrelationId());

		NcPowerDown powerDownReq = new NcPowerDown();
		powerDownReq.setNcPowerDown(powerDownType);
		
		return powerDownReq;
	}
}

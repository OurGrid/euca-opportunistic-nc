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
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.virt.OurVirt;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcRunInstance;
import edu.ucsb.eucalyptus.NcRunInstanceResponseType;
import edu.ucsb.eucalyptus.NcRunInstanceType;
import edu.ucsb.eucalyptus.NetConfigType;
import edu.ucsb.eucalyptus.VirtualMachineType;


public class TestNcRunInstance {
	
	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private TestIdlenessChecker testIChecker = new TestIdlenessChecker();
	private Properties properties;
	
	private static final String instKeyName = "INST_KEY_NAME";
	private static final String instUserData = "INST_USER_DATA";
	private static final String instLaunchIndex = "1";
	private static final String instPlatform = "INST_PLATFORM";
	private static final String[] instGroupNames = {"NAME1","NAME2"};
	private static final String instImageId = "IMG1";
	private static final String instKernelId = "KERN1";
	private static final String instRamdiskId = "RAMDISK1";
	private static final String bundleTaskStateName = "none";
	private static final String createImageStateName = "";
	private static final int blkBytes = 0;
	private static final int netBytes = 0;
	
	
	@Before
	public void init() throws Exception {
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		facade = new NodeFacade(properties, testIChecker, null, null);
		OurVirtUtils.setOurVirt(ourvirtMock);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNCNotAvailable() throws Exception {
		
		testIChecker.setIdle(false);
		
		NcRunInstance runReq = createRunInstanceRequest();
		
		facade.runInstance(runReq);
		
	}
	
	@Test
	public void testMainFlow() throws Exception {

		NcRunInstance runReq = createRunInstanceRequest();
		
		NcRunInstanceResponseType response = facade.runInstance(
				runReq).getNcRunInstanceResponse();
		
		NcRunInstanceType runInstType = runReq.getNcRunInstance();
		
		Assert.assertTrue(response.get_return());
		Assert.assertEquals(response.getCorrelationId(),
				runInstType.getCorrelationId());
		Assert.assertEquals(response.getUserId(),
				runInstType.getUserId());
		
		InstanceType instance = response.getInstance();
		
		Assert.assertEquals(instance.getUuid(), runInstType.getUuid());
		Assert.assertEquals(instance.getInstanceId(), 
				runInstType.getInstanceId());
		Assert.assertEquals(instance.getReservationId(), 
				runInstType.getReservationId());
		Assert.assertEquals(instance.getUserId(), 
				runInstType.getUserId());
		Assert.assertEquals(instance.getOwnerId(), 
				runInstType.getOwnerId());
		Assert.assertEquals(instance.getAccountId(), 
				runInstType.getAccountId());
		Assert.assertEquals(instance.getInstanceType(), 
				runInstType.getInstanceType());
		Assert.assertEquals(instance.getNetParams(), 
				runInstType.getNetParams());
		Assert.assertEquals(instance.getUserData(), 
				runInstType.getUserData());
		Assert.assertEquals(instance.getLaunchIndex(), 
				runInstType.getLaunchIndex());
		Assert.assertEquals(instance.getPlatform(), 
				runInstType.getPlatform());
		
		for (int i = 0; i < instance.getGroupNames().length; i++) {
			Assert.assertEquals(instance.getGroupNames()[i],
					runInstType.getGroupNames()[i]);
		}
		
		Assert.assertEquals(instance.getExpiryTime(), 
				runInstType.getExpiryTime());
		Assert.assertEquals(instance.getExpiryTime(), 
				runInstType.getExpiryTime());
		Assert.assertEquals(instance.getImageId(), 
				runInstType.getImageId());
		Assert.assertEquals(instance.getKernelId(), 
				runInstType.getKernelId());
		Assert.assertEquals(instance.getRamdiskId(), 
				runInstType.getRamdiskId());
		
		Assert.assertEquals(instance.getStateName(), 
				InstanceRepository.PENDING_STATE);
		Assert.assertEquals(instance.getBundleTaskStateName(), 
				bundleTaskStateName);
		Assert.assertEquals(instance.getCreateImageStateName(), 
				createImageStateName);
		Assert.assertEquals(instance.getBlkbytes(),
				blkBytes);
		Assert.assertEquals(instance.getNetbytes(),
				netBytes);
	}
	
	public static NcRunInstance getNewRunInstanceRequest() {
		return createRunInstanceRequest();
	}
	
	public static InstanceType getInstanceFromRequest(
			NcRunInstance runInstanceReq) {
		
		NcRunInstanceType runInstType = runInstanceReq.getNcRunInstance();
		
		InstanceType instance = new InstanceType();

		instance.setUuid(runInstType.getUuid());
		instance.setInstanceId(runInstType.getInstanceId());
		instance.setReservationId(runInstType.getReservationId());
		instance.setUserId(runInstType.getUserId());
		instance.setOwnerId(runInstType.getOwnerId());
		instance.setAccountId(runInstType.getAccountId());
		instance.setKeyName(runInstType.getKeyName());
		
		instance.setInstanceType(runInstType.getInstanceType());
		instance.setNetParams(runInstType.getNetParams());
		instance.setUserData(runInstType.getUserData());
		instance.setLaunchIndex(runInstType.getLaunchIndex());
		instance.setPlatform(runInstType.getPlatform());
		instance.setGroupNames(runInstType.getGroupNames());
		
		instance.setImageId(runInstType.getImageId());
		instance.setKernelId(runInstType.getKernelId());
		instance.setRamdiskId(runInstType.getRamdiskId());
		
		return instance;
	}
	
	private static NcRunInstance createRunInstanceRequest() {
		NcRunInstanceType runInstanceType = new NcRunInstanceType();
		
		runInstanceType.setUuid(TestUtils.getNewInstanceUUID());
		runInstanceType.setInstanceId(TestUtils.getNewInstanceId());
		runInstanceType.setReservationId(TestUtils.getNewInstanceReservationId());
		runInstanceType.setUserId(TestUtils.getNewUserId());
		runInstanceType.setCorrelationId(TestUtils.getNewCorrelationId());
		runInstanceType.setOwnerId(TestUtils.getNewInstanceOwnerId());
		runInstanceType.setAccountId(TestUtils.getNewInstanceAccountId());
		runInstanceType.setKeyName(instKeyName);
		
		VirtualMachineType vmType = TestUtils.createBasicVMType();
		
		runInstanceType.setInstanceType(vmType);
		
		NetConfigType netConfig = new NetConfigType();
		netConfig.setPrivateMacAddress("11:22:33:44:55:66");
		
		runInstanceType.setNetParams(netConfig);
		runInstanceType.setUserData(instUserData);
		runInstanceType.setLaunchIndex(instLaunchIndex);
		runInstanceType.setPlatform(instPlatform);
		runInstanceType.setGroupNames(instGroupNames);
		runInstanceType.setExpiryTime(Calendar.getInstance());
	
		runInstanceType.setImageId(instImageId);
		runInstanceType.setKernelId(instKernelId);
		runInstanceType.setRamdiskId(instRamdiskId);
		
		NcRunInstance runInstanceReq = new NcRunInstance();
		runInstanceReq.setNcRunInstance(runInstanceType);

		return runInstanceReq;
	}
}

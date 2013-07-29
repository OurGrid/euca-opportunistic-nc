package org.ourgrid.node;
import java.io.FileInputStream;
import java.util.Properties;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.node.idleness.TestIdlenessChecker;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.util.NodeProperties;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.node.util.ResourcesInfoGatherer;
import org.ourgrid.virt.OurVirt;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcDescribeResource;
import edu.ucsb.eucalyptus.NcDescribeResourceResponseType;
import edu.ucsb.eucalyptus.NcDescribeResourceType;


public class TestNcDescribeResource {
	
	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private TestIdlenessChecker testIChecker = new TestIdlenessChecker();
	private InstanceRepository instanceRepository = new InstanceRepository();
	private Sigar sigarMock = Mockito.mock(Sigar.class);
	private CpuInfo cpuInfoMock = Mockito.mock(CpuInfo.class);
	private FileSystemUsage fsUsageMock = Mockito.mock(FileSystemUsage.class);
	private Mem memMock = Mockito.mock(Mem.class);
	private Properties properties;
	
	private static double MEGA = ResourcesInfoGatherer.MEGA;
	
	private static final int TOTAL_MACHINE_NUM_CORES = 8;
	private static final long TOTAL_MACHINE_MEM = 4096;
	private static final long TOTAL_MACHINE_DISK = 500;
	private static final long AV_MACHINE_MEM = 3400;
	private static final long AV_MACHINE_DISK = 450;
	
	@Before
	public void init() throws Exception {
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		
		setSigarMocks();
		ResourcesInfoGatherer resIG = 
				new ResourcesInfoGatherer(properties, sigarMock);
		
		facade = new NodeFacade(properties, testIChecker, resIG, instanceRepository);
		OurVirtUtils.setOurVirt(ourvirtMock);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNCNotAvailable() throws Exception {
		
		testIChecker.setIdle(false);
		
		setMockReturnValues(TOTAL_MACHINE_MEM, TOTAL_MACHINE_DISK,
				TOTAL_MACHINE_NUM_CORES, AV_MACHINE_MEM, AV_MACHINE_DISK);
		
		NcDescribeResource descRes = createDescribeResourceRequest();
		facade.describeResource(descRes);
	}
	
	@Test
	public void testNoRunningInstances() throws Exception {
		
		NcDescribeResource descRes = createDescribeResourceRequest();
		
		setMockReturnValues(TOTAL_MACHINE_MEM, TOTAL_MACHINE_DISK,
				TOTAL_MACHINE_NUM_CORES, AV_MACHINE_MEM, AV_MACHINE_DISK);
		
		NcDescribeResourceResponseType response = 
				facade.describeResource(descRes).getNcDescribeResourceResponse();
		
		Assert.assertTrue(response.get_return());
		Assert.assertEquals(response.getCorrelationId(), 
				descRes.getNcDescribeResource().getCorrelationId());
		Assert.assertEquals(response.getUserId(),  	
				descRes.getNcDescribeResource().getUserId());
		
		Assert.assertEquals(response.getDiskSizeMax(), TOTAL_MACHINE_DISK);
		Assert.assertEquals(response.getMemorySizeMax(),TOTAL_MACHINE_MEM);
		Assert.assertEquals(response.getNumberOfCoresMax(), 
				TOTAL_MACHINE_NUM_CORES);
		
		Assert.assertEquals(response.getDiskSizeAvailable(), AV_MACHINE_DISK);
		Assert.assertEquals(response.getMemorySizeAvailable(), AV_MACHINE_MEM);
		Assert.assertEquals(response.getNumberOfCoresAvailable(), 
				TOTAL_MACHINE_NUM_CORES);
		
		Assert.assertEquals(response.getIqn(),properties.getProperty(
				NodeProperties.ISCSI_IQN));
		Assert.assertEquals(response.getNodeStatus(), 
				ResourcesInfoGatherer.NODE_STATUS_OK);
		Assert.assertEquals(response.getPublicSubnets(), 
				ResourcesInfoGatherer.PUBLIC_SUBNETS);
	}

	@Test
	public void testAddingInstances() throws Exception {
		
		NcDescribeResource descRes = createDescribeResourceRequest();
		
		setMockReturnValues(TOTAL_MACHINE_MEM, TOTAL_MACHINE_DISK,
				TOTAL_MACHINE_NUM_CORES, AV_MACHINE_MEM, AV_MACHINE_DISK);
		
		NcDescribeResourceResponseType response = 
				facade.describeResource(descRes).getNcDescribeResourceResponse();
		
		Assert.assertEquals(response.getDiskSizeAvailable(), AV_MACHINE_DISK);
		Assert.assertEquals(response.getMemorySizeAvailable(), AV_MACHINE_MEM);
		Assert.assertEquals(response.getNumberOfCoresAvailable(), 
				TOTAL_MACHINE_NUM_CORES);
				
		TestUtils.addInstanceToRepository(TestUtils.DEF_INST_NUM_CORES,
				TestUtils.DEF_INST_MEM, TestUtils.DEF_INST_DISK, facade,
				instanceRepository);
		
		response = facade.describeResource(descRes).getNcDescribeResourceResponse();
		
		Assert.assertEquals(response.getDiskSizeAvailable(),
				TOTAL_MACHINE_DISK - TestUtils.DEF_INST_DISK);
		Assert.assertEquals(response.getMemorySizeAvailable(), 
				TOTAL_MACHINE_MEM - TestUtils.DEF_INST_MEM);
		Assert.assertEquals(response.getNumberOfCoresAvailable(), 
				TOTAL_MACHINE_NUM_CORES - TestUtils.DEF_INST_NUM_CORES);
		
	}
	
	@Test
	public void testRemovingInstances() throws Exception {
		
		NcDescribeResource descRes = createDescribeResourceRequest();
		
		InstanceType i1 = TestUtils.addInstanceToRepository(
				TestUtils.DEF_INST_NUM_CORES, 
				TestUtils.DEF_INST_MEM, 
				TestUtils.DEF_INST_DISK, 
				facade, instanceRepository);
		
		setMockReturnValues(TOTAL_MACHINE_MEM, TOTAL_MACHINE_DISK,
				TOTAL_MACHINE_NUM_CORES, AV_MACHINE_MEM, AV_MACHINE_DISK);
		
		NcDescribeResourceResponseType response = 
				facade.describeResource(descRes).getNcDescribeResourceResponse();
		
		Assert.assertEquals(response.getDiskSizeAvailable(),
				TOTAL_MACHINE_DISK - TestUtils.DEF_INST_DISK);
		Assert.assertEquals(response.getMemorySizeAvailable(), 
				TOTAL_MACHINE_MEM - TestUtils.DEF_INST_MEM);
		Assert.assertEquals(response.getNumberOfCoresAvailable(), 
				TOTAL_MACHINE_NUM_CORES - TestUtils.DEF_INST_NUM_CORES);
		
		TestUtils.removeInstanceFromRepository(i1, facade, instanceRepository);
		
		response = facade.describeResource(descRes).getNcDescribeResourceResponse();
		
		Assert.assertEquals(response.getDiskSizeAvailable(), AV_MACHINE_DISK);
		Assert.assertEquals(response.getMemorySizeAvailable(),
				AV_MACHINE_MEM);
		Assert.assertEquals(response.getNumberOfCoresAvailable(),
				TOTAL_MACHINE_NUM_CORES);
				
	}
	
	private NcDescribeResource createDescribeResourceRequest() {
		NcDescribeResourceType descResourceType = new NcDescribeResourceType();
		descResourceType.setUserId(TestUtils.DEFAULT_USER_ID);
		descResourceType.setCorrelationId(TestUtils.getNewCorrelationId());
		
		NcDescribeResource descResource = new NcDescribeResource();
		descResource.setNcDescribeResource(descResourceType);
		
		return descResource;
	}
	
	private void setSigarMocks() throws SigarException {
		CpuInfo[] cpuInfoList = {cpuInfoMock}; 
		
		Mockito.when(sigarMock.getCpuInfoList()).thenReturn(cpuInfoList);
		Mockito.when(sigarMock.getMem()).thenReturn(memMock);
		Mockito.when(sigarMock.getFileSystemUsage(Mockito.anyString()))
			.thenReturn(fsUsageMock);
	}
	
	private void setMockReturnValues(long totalMachineMem, long totalMachineDisk,
			int totalMachineNumCores, long avMachineMem, long avMachineDisk) {
		
		Mockito.when(memMock.getRam()).thenReturn(totalMachineMem);
		Mockito.when(fsUsageMock.getTotal()).thenReturn(
				Math.round(totalMachineDisk*MEGA));
		Mockito.when(cpuInfoMock.getTotalCores()).thenReturn(totalMachineNumCores);
		Mockito.when(memMock.getActualFree()).thenReturn(
				Math.round(avMachineMem*MEGA));
		Mockito.when(fsUsageMock.getAvail()).thenReturn(
				Math.round(avMachineDisk*MEGA));
	}
}

package org.ourgrid.node;
import java.io.FileInputStream;
import java.util.Properties;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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
	private CpuInfo cpuInfoMock = Mockito.mock(CpuInfo.class);
	private FileSystemUsage fSUsageMock = Mockito.mock(FileSystemUsage.class);
	private Mem memMock = Mockito.mock(Mem.class);
	private Properties properties;
	private boolean idlenessEnabled = false;
	
	private static double MEGA = ResourcesInfoGatherer.MEGA;
	
	private static final int TOTAL_MACHINE_NUM_CORES = 8;
	private static final long TOTAL_MACHINE_MEM = Math.round(4096*MEGA);
	private static final long TOTAL_MACHINE_DISK = Math.round(500*MEGA);
	private static final long AV_MACHINE_MEM = Math.round(3400*MEGA);
	private static final long AV_MACHINE_DISK = Math.round(450*MEGA);
	
	private static final int DEF_INST_NUM_CORES = 2;
	private static final int DEF_INST_MEM = 1024;
	private static final int DEF_INST_DISK = 100;
	
	@Before
	public void init() throws Exception {
		OurVirtUtils.setOurVirt(ourvirtMock);
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		properties.setProperty("idleness.enabled", String.valueOf(idlenessEnabled));
		ResourcesInfoGatherer resIG = new ResourcesInfoGatherer(
				properties, cpuInfoMock, fSUsageMock, memMock);
		facade = new NodeFacade(properties, resIG);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNCNotAvailable() throws Exception {
		
		idlenessEnabled = true;
		init();
		
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
		
		Assert.assertTrue(response.getDiskSizeMax() == TOTAL_MACHINE_DISK/MEGA);
		Assert.assertTrue(response.getMemorySizeMax() == TOTAL_MACHINE_MEM/MEGA);
		Assert.assertTrue(response.getNumberOfCoresMax() 
				== TOTAL_MACHINE_NUM_CORES);
		
		Assert.assertTrue(response.getDiskSizeAvailable() == AV_MACHINE_DISK/MEGA);
		Assert.assertTrue(response.getMemorySizeAvailable() == 
				AV_MACHINE_MEM/MEGA);
		Assert.assertTrue(response.getNumberOfCoresAvailable() 
				== TOTAL_MACHINE_NUM_CORES);
		
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
		
		Assert.assertTrue(response.getDiskSizeAvailable() == AV_MACHINE_DISK/MEGA);
		Assert.assertTrue(response.getMemorySizeAvailable() == 
				AV_MACHINE_MEM/MEGA);
		Assert.assertTrue(response.getNumberOfCoresAvailable() 
				== TOTAL_MACHINE_NUM_CORES);
				
		TestUtils.addInstanceToRepository(DEF_INST_NUM_CORES,
				DEF_INST_MEM, DEF_INST_DISK, facade);
		
		response = facade.describeResource(descRes).getNcDescribeResourceResponse();
		
		Assert.assertTrue(response.getDiskSizeAvailable() 
				== TOTAL_MACHINE_DISK/MEGA - DEF_INST_DISK);
		Assert.assertTrue(response.getMemorySizeAvailable() 
				== TOTAL_MACHINE_MEM/MEGA - DEF_INST_MEM);
		Assert.assertTrue(response.getNumberOfCoresAvailable() 
				== TOTAL_MACHINE_NUM_CORES - DEF_INST_NUM_CORES);
		
	}
	
	@Test
	public void testRemovingInstances() throws Exception {
		
		NcDescribeResource descRes = createDescribeResourceRequest();
		
		InstanceType i1 = TestUtils.addInstanceToRepository(DEF_INST_NUM_CORES,
				DEF_INST_MEM, DEF_INST_DISK, facade);
		
		setMockReturnValues(TOTAL_MACHINE_MEM, TOTAL_MACHINE_DISK,
				TOTAL_MACHINE_NUM_CORES, AV_MACHINE_MEM, AV_MACHINE_DISK);
		
		NcDescribeResourceResponseType response = 
				facade.describeResource(descRes).getNcDescribeResourceResponse();
		
		Assert.assertTrue(response.getDiskSizeAvailable() 
				== TOTAL_MACHINE_DISK/MEGA - DEF_INST_DISK);
		Assert.assertTrue(response.getMemorySizeAvailable() 
				== TOTAL_MACHINE_MEM/MEGA - DEF_INST_MEM);
		Assert.assertTrue(response.getNumberOfCoresAvailable() 
				== TOTAL_MACHINE_NUM_CORES - DEF_INST_NUM_CORES);
		
		TestUtils.removeInstanceFromRepository(i1, facade);
		
		response = facade.describeResource(descRes).getNcDescribeResourceResponse();
		
		Assert.assertTrue(response.getDiskSizeAvailable() == AV_MACHINE_DISK/MEGA);
		Assert.assertTrue(response.getMemorySizeAvailable() == 
				AV_MACHINE_MEM/MEGA);
		Assert.assertTrue(response.getNumberOfCoresAvailable() 
				== TOTAL_MACHINE_NUM_CORES);
				
	}
	
	private NcDescribeResource createDescribeResourceRequest() {
		NcDescribeResourceType descResourceType = new NcDescribeResourceType();
		descResourceType.setUserId(TestUtils.DEFAULT_USER_ID);
		descResourceType.setCorrelationId(
				String.valueOf(TestUtils.correlationId++));
		
		NcDescribeResource descResource = new NcDescribeResource();
		descResource.setNcDescribeResource(descResourceType);
		
		return descResource;
		
		
	}
	
	private void setMockReturnValues(long totalMachineMem, long totalMachineDisk,
			int totalMachineNumCores, long avMachineMem, long avMachineDisk) {
		Mockito.when(memMock.getRam()).thenReturn(totalMachineMem);
		Mockito.when(fSUsageMock.getTotal()).thenReturn(totalMachineDisk);
		Mockito.when(cpuInfoMock.getTotalCores()).thenReturn(totalMachineNumCores);
		
		Mockito.when(memMock.getActualFree()).thenReturn(avMachineMem);
		Mockito.when(fSUsageMock.getAvail()).thenReturn(avMachineDisk);
	}
}

package org.ourgrid.node.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.model.Resources;

import edu.ucsb.eucalyptus.InstanceType;

public class ResourcesInfoGatherer {
	
	public static final double MEGA = 1024*1024; 
	
	private Sigar sigarUtils;
	private CpuInfo cpuInfo;
	private FileSystemUsage fileSysUsage;
	private Mem memory;
	private String imageCloneRootDir;
	private String imageCacheRootDir;
	
	public static String ISCSI_IQN;
	public static String PUBLIC_SUBNETS = "none";
	public static String NODE_STATUS_OK = "OK";
	
	public ResourcesInfoGatherer(Properties properties) throws SigarException {
		imageCloneRootDir = properties.getProperty(NodeProperties.CLONEROOT);
		imageCacheRootDir = properties.getProperty(NodeProperties.CACHEROOT);
		ISCSI_IQN = properties.getProperty(NodeProperties.ISCSI_IQN);
		sigarUtils = new Sigar();
		createCacheAndCloneDirectories();
		reloadSigarInfo();
	}
	
	public ResourcesInfoGatherer(Properties properties, Sigar sigar) throws SigarException {
		imageCloneRootDir = properties.getProperty(NodeProperties.CLONEROOT);
		imageCacheRootDir = properties.getProperty(NodeProperties.CACHEROOT);
		ISCSI_IQN = properties.getProperty(NodeProperties.ISCSI_IQN);
		sigarUtils = sigar;
		createCacheAndCloneDirectories();
		reloadSigarInfo();
	}
	
	private void createCacheAndCloneDirectories() {
		File imageCloneDir = new File(imageCloneRootDir);
		File imageCacheDir = new File(imageCacheRootDir);
		
		if (!imageCloneDir.exists()) {
			imageCloneDir.mkdirs();
		}
		
		if (!imageCacheDir.exists()) {
			imageCacheDir.mkdirs();
		}
	}
	
	private void reloadSigarInfo() throws SigarException {
		try {
			cpuInfo = sigarUtils.getCpuInfoList()[0];
			fileSysUsage = sigarUtils.getFileSystemUsage(imageCloneRootDir);
			memory = sigarUtils.getMem();
		} catch (SigarException e) {
			throw e;
		}
	}
	
	private int getAvailMem() {
		return (int)(memory.getActualFree()/MEGA);
	}
	
	public int getTotalMem() {
		return (int)(memory.getRam());
	}
	
	private int getAvailDiskSpace() {
		return (int)(fileSysUsage.getAvail()/MEGA);
	}
	
	public int getTotalDiskSpace() {
		return (int)(fileSysUsage.getTotal()/MEGA);
	}
	
	public int getTotalNumCores() {
		return (int)(cpuInfo.getTotalCores());
	}
	
	public Resources describeAvailable(InstanceRepository instanceRepository) throws Exception {
		
		reloadSigarInfo();
		
		int availCores = getTotalNumCores();
		int availMemory = getTotalMem();
		int availDiskSpace = getTotalDiskSpace();
		
		for (InstanceType instance : instanceRepository.getInstances()) {
			if (!instance.getStateName().equals(InstanceRepository.TEARDOWN_STATE)) {
				availCores -= instance.getInstanceType().getCores();
				availMemory -= instance.getInstanceType().getMemory();
				availDiskSpace -= instance.getInstanceType().getDisk();
			}
		}
		
		int actualAvailMemory = getAvailMem();
		int actualAvailDiskSpace = getAvailDiskSpace();
		
		Resources available = new Resources();
		available.setDisk(actualAvailDiskSpace < availDiskSpace? actualAvailDiskSpace : availDiskSpace);
		available.setMem(actualAvailMemory < availMemory? actualAvailMemory : availMemory);
		available.setCores(availCores);
		
		return available;
	}
	
	public String getOSType() {
		return OperatingSystem.getInstance().getName();
	}
	
	//TODO Remove this after testing
	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		ResourcesInfoGatherer rig = new ResourcesInfoGatherer(properties);
		
		InstanceRepository iRep = new InstanceRepository();
		
		Resources res1, res2;
//		res1 = rig.describeAvailable(iRep);
		
//		res2 = rig.describeAvailable(iRep);
		
		System.out.println(rig.getOSType());
		System.out.println("Finished!");		
	}

	
	
}

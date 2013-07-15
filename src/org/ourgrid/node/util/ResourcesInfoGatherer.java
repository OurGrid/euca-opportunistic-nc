package org.ourgrid.node.util;

import java.util.Properties;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
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
	
	private boolean mocked = false;
	
	private static String imageCloneRootDir;
	public static String ISCSI_IQN;
	public static String PUBLIC_SUBNETS = "none";
	public static String NODE_STATUS_OK = "OK";
	
	public ResourcesInfoGatherer(Properties properties) throws SigarException {
		imageCloneRootDir = properties.getProperty(NodeProperties.CLONEROOT);
		ISCSI_IQN = properties.getProperty(NodeProperties.ISCSI_IQN);
		reloadSigarInfo();
	}
	
	public ResourcesInfoGatherer(Properties properties, CpuInfo cpuInfo, 
			FileSystemUsage fileSysUsage, Mem mem) throws SigarException {
		imageCloneRootDir = properties.getProperty(NodeProperties.CLONEROOT);
		ISCSI_IQN = properties.getProperty(NodeProperties.ISCSI_IQN);
		reloadSigarInfo(cpuInfo, fileSysUsage, mem);
		mocked = true;
	}
	
	private void reloadSigarInfo() throws SigarException {
		try {
			sigarUtils = new Sigar();
			cpuInfo = sigarUtils.getCpuInfoList()[0];
			fileSysUsage = sigarUtils.getFileSystemUsage(imageCloneRootDir);
			memory = sigarUtils.getMem();
		} catch (SigarException e) {
			throw e;
		}
	}
	
	private void reloadSigarInfo(CpuInfo cpuI, FileSystemUsage fSUsage, Mem mem)
			throws SigarException {
		cpuInfo = cpuI;
		fileSysUsage = fSUsage;
		memory = mem;
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
		if (mocked) {
			reloadSigarInfo(cpuInfo, fileSysUsage, memory);
		} else {
			reloadSigarInfo();
		}
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
}

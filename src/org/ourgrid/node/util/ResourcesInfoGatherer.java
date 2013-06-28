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

//TODO Change class name. Ideas: ResourcesInfoGatherer, ResourceInfoUtils
public class ResourcesInfoGatherer {
	
	private static final double MEGA = 1024*1024; 
	
	private Sigar sigarUtils;
	private CpuInfo cpuInfo;
	private FileSystemUsage fileSysUsage;
	private Mem memory;
	
	private String imageCloneRootDir;
	
	public ResourcesInfoGatherer(Properties properties) throws SigarException {
		imageCloneRootDir = properties.getProperty("image.clone.root");
		reloadSigarInfo();
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
		available.setDisk(actualAvailDiskSpace < availDiskSpace? actualAvailDiskSpace : actualAvailDiskSpace);
		available.setMem(actualAvailMemory < availMemory? actualAvailMemory : availMemory);
		available.setCores(availCores);
		
		return available;
	}
}

package org.ourgrid.node.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.ourgrid.node.model.VBR;

import edu.ucsb.eucalyptus.NcRunInstanceType;
import edu.ucsb.eucalyptus.ServiceInfoType;
import edu.ucsb.eucalyptus.VirtualBootRecordType;
import edu.ucsb.eucalyptus.VirtualMachineType;

public class VBRUtils {

	public static VBR syncBootRecords(NcRunInstanceType instanceRequest, Properties properties) {

		VirtualMachineType instanceType = instanceRequest.getInstanceType();
		VBR vbr = new VBR();
		for (VirtualBootRecordType virtualBootRecordType : instanceType.getVirtualBootRecord()) {
			if(!virtualBootRecordType.getResourceLocation().equals("none")) {
				String resourceLocation = virtualBootRecordType.getResourceLocation();
				URI resourceURI = null;
				try {
					resourceURI = new URI(resourceLocation);
				} catch (URISyntaxException e) {
					throw new RuntimeException(e);
				}
				String scheme = resourceURI.getScheme();
				String[] serviceLocations = getServiceUris(instanceRequest, scheme);
				
				boolean hasDownloaded = false;
				
				fillVBR(vbr, virtualBootRecordType);
				
				for (String serviceLocation : serviceLocations) {
					String resourceFullURL = resourceLocation.replace(
							scheme + ":/", serviceLocation);
					try {
						WalrusUtils.downloadImage(resourceFullURL, 
								virtualBootRecordType.getId(), properties);
						hasDownloaded = true;
						break;
					} catch (Exception e) {
					}
				}
				
				if (!hasDownloaded) {
					throw new RuntimeException("Could not download image.");
				}
			}
		}
		return vbr;
	}

	private static void fillVBR(VBR vbr,
			VirtualBootRecordType virtualBootRecordType) {
		if (virtualBootRecordType.getType().equals("machine")) {
			vbr.setMachineImageId(virtualBootRecordType.getId());
		} else if (virtualBootRecordType.getType().equals("kernel")) {
			vbr.setKernelId(virtualBootRecordType.getId());
		} else if (virtualBootRecordType.getType().equals("ramdisk")) {
			vbr.setRamDiskId(virtualBootRecordType.getId());
		}
	}

	private static String[] getServiceUris(NcRunInstanceType instanceRequest,
			String scheme) {
		for (ServiceInfoType serviceInfo : instanceRequest.getServices()) {
			if (serviceInfo.getType().equals(scheme)) {
				return serviceInfo.getUris();
			}
		}
		return null;
	}
	
}

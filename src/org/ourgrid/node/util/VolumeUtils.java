package org.ourgrid.node.util;

import java.io.File;
import java.util.Properties;

import org.ourgrid.node.model.volume.VolumeData;
import org.ourgrid.virt.strategies.HypervisorUtils;

import edu.ucsb.eucalyptus.NcAttachVolumeType;
import edu.ucsb.eucalyptus.ServiceInfoType;

public class VolumeUtils {
	
	public static String getServiceUrl(NcAttachVolumeType request) {
		for (ServiceInfoType service : request.getServices()) {
			if (service.getType().equals("storage")) {
				return service.getUris()[0];
			}
		}
		return null;
	}

	public static void connectEBSVolume(String instanceId, String attachmentToken,
			Properties properties) throws Exception {
		VolumeData volumeData = VolumeData.parse(attachmentToken);
		if (volumeData.getLun() != null) {
			//TODO
		} else {
			String decryptedPassword = decryptToken(volumeData.getEncryptedPassword(), properties);
			String devicePath = getDeviceName(properties, volumeData,
					decryptedPassword);
			if (devicePath != null) {
				OurVirtUtils.attachDevice(instanceId, devicePath);
			}
		}
	}

	private static String getDeviceName(Properties properties,
			VolumeData volumeData, String decryptedPassword) throws Exception {
		String devicePath = connectISCSITarget(volumeData, decryptedPassword, properties);
		if (checkDevice(devicePath)) {
			return devicePath;
		} else {
			return null;
		}
	}
	
	private static boolean checkDevice(String devicePath) {
		if (!HypervisorUtils.isLinuxHost()) {
			return false;
		}
		if (!new File(devicePath).exists()) {
			return false;
		}
		
		if (!new File(devicePath).canRead() || !new File(devicePath).canWrite()) {
			return false;
		}
		return true;
	}

	private static String connectISCSITarget(VolumeData volumeData,
			String decryptedPassword, Properties properties) throws Exception {
		
		ISCSIUtils.discovery(volumeData.getIp(), properties);
		String username = "eucalyptus";
		ISCSIUtils.login(volumeData.getStore(), username, decryptedPassword, properties);
		String devicePath = ISCSIUtils.getDeviceName(volumeData.getStore(), properties);
		int retries = 3;
		while (retries > 0 && !(new File(devicePath).exists())) {
			retries--;
			Thread.sleep(3000);
		}
		String systemUser = System.getProperty("user.name");
		ISCSIUtils.deviceOwn(devicePath, systemUser, properties);
		return devicePath;
	}

	private static String decryptToken(String token, Properties properties) throws Exception {
		return AuthUtils.decryptWithNode(token, properties);
	}

}

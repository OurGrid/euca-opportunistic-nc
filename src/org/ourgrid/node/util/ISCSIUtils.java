package org.ourgrid.node.util;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class ISCSIUtils {
	
	private static final String CMD = "./iscsi-utils";
	
	public static void discovery(String ip, Properties properties) throws Exception {
		String cmdLocation = getCmdLocation(properties);
		
		ProcessBuilder processBuilder = new ProcessBuilder("sudo", CMD, "discovery", ip);
		if (cmdLocation != null) {
			processBuilder.directory(new File(cmdLocation));
		}
		Process process = processBuilder.start();
		if (process.waitFor() != 0) {
			throw new RuntimeException("ISCSI Error: could not discover targets");
		}
	}

	private static String getCmdLocation(Properties properties) {
		String cmdLocation = System.getProperty(properties.getProperty(
				NodeProperties.HYPERVISOR_LOCATION_NAME));
		return cmdLocation;
	}
	
	public static void login(String store, String username, String password, Properties properties) throws Exception {
		String cmdLocation = getCmdLocation(properties);
		
		ProcessBuilder processBuilder = new ProcessBuilder("sudo", CMD, "login", store, 
				username, password);
		if (cmdLocation != null) {
			processBuilder.directory(new File(cmdLocation));
		}
		Process process = processBuilder.start();
		if (process.waitFor() != 0) {
			throw new RuntimeException("ISCSI Error: could not login to target");
		}
	}
	
	public static String getLocalDevicePath(String store, Properties properties) throws Exception {
		String cmdLocation = getCmdLocation(properties);
		
		ProcessBuilder processBuilder = new ProcessBuilder("sudo", CMD, "getdev", store);
		if (cmdLocation != null) {
			processBuilder.directory(new File(cmdLocation));
		}
		Process process = processBuilder.start();
		if (process.waitFor() != 0) {
			throw new RuntimeException("ISCSI Error: could not find device name");
		} else {
			return IOUtils.toString(process.getInputStream()).trim();
		}
	}
	
	public static void deviceOwn(String devicePath, String user, Properties properties) throws Exception {
		String cmdLocation = getCmdLocation(properties);
		
		ProcessBuilder processBuilder = new ProcessBuilder("sudo", CMD, "devown", devicePath, user);
		if (cmdLocation != null) {
			processBuilder.directory(new File(cmdLocation));
		}
		Process process = processBuilder.start();
		if (process.waitFor() != 0) {
			throw new RuntimeException("ISCSI Error: could not change device owner");
		}
	}
	
	public static void logout(String store, Properties properties) throws Exception {
		String cmdLocation = getCmdLocation(properties);
		
		ProcessBuilder processBuilder = new ProcessBuilder("sudo", CMD, "logout", store);
		if (cmdLocation != null) {
			processBuilder.directory(new File(cmdLocation));
		}
		Process process = processBuilder.start();
		if (process.waitFor() != 0) {
			throw new RuntimeException("ISCSI Error: could not logout from target");
		}
	}
}

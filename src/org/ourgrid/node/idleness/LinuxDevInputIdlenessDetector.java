package org.ourgrid.node.idleness;

import java.util.Properties;

import org.ourgrid.node.util.NodeProperties;

public class LinuxDevInputIdlenessDetector extends AbstractIdlenessDetector {

	private String idlenessCmdLocation;
	
	public LinuxDevInputIdlenessDetector(Properties properties) {
		super(properties);
		if (isEnabled()) {
			this.idlenessCmdLocation = properties.getProperty(NodeProperties.IDLENESS_FILE);
		}
	}
	
	protected boolean checkIdle(Long idlenessTime) {
		if (!isEnabled()) {
			return true;
		}
		try {
			ProcessBuilder pb = new ProcessBuilder(idlenessCmdLocation, 
					idlenessTime.toString());
			int exitValue = pb.start().waitFor();
			return exitValue == 1;
		} catch (Exception e) {
			return false;
		}
	}
}

package org.ourgrid.node.util;

import java.util.Properties;

import edu.ucsb.eucalyptus.NcRunInstanceType;
import edu.ucsb.eucalyptus.NetConfigType;

public class NetUtils {

	private static final String MANAGED = "MANAGED";
	private static final String MANAGED_NOVLAN = "MANAGED_NOVLAN";
	private static final String SYSTEM = "SYSTEM";
	private static final String STATIC = "STATIC";
	
	public static NetConfigType getParams(NcRunInstanceType instanceRequest, Properties properties) {
		NetConfigType netParams = instanceRequest.getNetParams();
		
		String networkMode = properties.getProperty(NodeProperties.NETWORK_MODE);
		if (networkMode.equals(SYSTEM) || networkMode.equals(STATIC)) {
			String instanceIp = OurVirtUtils.getInstanceIP(instanceRequest.getInstanceId());
			netParams.setPublicIp(instanceIp);
		}
		
		return netParams;
	}

}

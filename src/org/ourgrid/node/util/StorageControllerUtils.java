package org.ourgrid.node.util;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.client.ServiceClient;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.apache.rampart.RampartMessageData;

import edu.ucsb.eucalyptus.storagecontroller.EucalyptusSCStub;
import edu.ucsb.eucalyptus.storagecontroller.EucalyptusSCStub.ExportVolume;
import edu.ucsb.eucalyptus.storagecontroller.EucalyptusSCStub.ExportVolumeResponse;
import edu.ucsb.eucalyptus.storagecontroller.EucalyptusSCStub.ExportVolumeType;

public class StorageControllerUtils {
	
	public static String exportVolume(boolean useWsSec, String wsSecPolicyFilePath, 
			String volumeId, String token, String ip, String iqn, String scURL) 
					throws Exception {
		
//		EucalyptusSCStub eucalyptusSCStub = new EucalyptusSCStub(scURL);
		EucalyptusSCStub eucalyptusSCStub = new EucalyptusSCStub("http://localhost:8773/services/Storage");
		if (useWsSec) {
			configureRampart(eucalyptusSCStub, wsSecPolicyFilePath);
		}
		ExportVolume exportVolumeRequest = new ExportVolume();
		ExportVolumeType exportVolume = new ExportVolumeType();
		exportVolume.setIp(ip);
		exportVolume.setIqn(iqn);
		exportVolume.setVolumeId(volumeId);
		exportVolume.setToken(token);
		exportVolumeRequest.setExportVolume(exportVolume);
		
		ExportVolumeResponse exportVolumeResponse = eucalyptusSCStub.exportVolume(exportVolumeRequest);
		return exportVolumeResponse.getExportVolumeResponse().getConnectionString();
	}
	
	private static void configureRampart(EucalyptusSCStub eucalyptusSCStub,
			String wsSecPolicyFilePath) throws Exception {
		
		ServiceClient serviceClient = eucalyptusSCStub._getServiceClient();
		serviceClient.engageModule("addressing");
		serviceClient.engageModule("rampart");
		serviceClient.getOptions().setProperty(RampartMessageData.KEY_RAMPART_POLICY, 
				loadPolicy(wsSecPolicyFilePath));
	}

	private static Policy loadPolicy(String xmlPath) throws Exception {
        StAXOMBuilder builder = new StAXOMBuilder(xmlPath);
        return PolicyEngine.getPolicy(builder.getDocumentElement());
    }
   
}

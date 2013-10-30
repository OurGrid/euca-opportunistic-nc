package org.ourgrid.node.model.volume;

public class VolumeData {
	
	private String ip;
	private String store;
	private String encryptedPassword;
	private String lun;
	private String authMode;
	
	public static VolumeData parse(String attachmentToken) {
		VolumeData volumeData = new VolumeData();
		String[] tokenPieces = attachmentToken.split(",");
		volumeData.ip = tokenPieces[0];
		volumeData.store = tokenPieces[1];
		volumeData.encryptedPassword = tokenPieces[2];
		return volumeData;
	}

	public String getIp() {
		return ip;
	}

	public String getStore() {
		return store;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public String getLun() {
		return lun;
	}

	public String getAuthMode() {
		return authMode;
	}
}

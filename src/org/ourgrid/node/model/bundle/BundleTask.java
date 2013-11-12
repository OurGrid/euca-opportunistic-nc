package org.ourgrid.node.model.bundle;

public class BundleTask {

	private BundleTaskState state;
	private String instanceId;
	private String manifest;
	private Process currentProcess;
	private String bucketName;
	private String filePrefix;
	private String walrusURL;
	private String userPublicKey;
	private String s3Policy;
	private String s3PolicySig;
	private boolean bundleBucketExists;

	public BundleTaskState getState() {
		return state;
	}

	public void setState(BundleTaskState state) {
		this.state = state;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getManifest() {
		return manifest;
	}

	public void setManifest(String manifest) {
		this.manifest = manifest;
	}
	
	public Process getCurrentProcess() {
		return currentProcess;
	}

	public void setCurrentProcess(Process currentProcess) {
		this.currentProcess = currentProcess;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public String getWalrusURL() {
		return walrusURL;
	}

	public void setWalrusURL(String walrusURL) {
		this.walrusURL = walrusURL;
	}

	public String getUserPublicKey() {
		return userPublicKey;
	}

	public void setUserPublicKey(String userPublicKey) {
		this.userPublicKey = userPublicKey;
	}

	public String getS3Policy() {
		return s3Policy;
	}

	public void setS3Policy(String s3Policy) {
		this.s3Policy = s3Policy;
	}

	public String getS3PolicySig() {
		return s3PolicySig;
	}

	public void setS3PolicySig(String s3PolicySig) {
		this.s3PolicySig = s3PolicySig;
	}

	public boolean isBundleBucketExists() {
		return bundleBucketExists;
	}

	public void setBundleBucketExists(boolean bundleBucketExists) {
		this.bundleBucketExists = bundleBucketExists;
	}
}

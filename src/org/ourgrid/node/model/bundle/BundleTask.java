package org.ourgrid.node.model.bundle;

public class BundleTask {

	private BundleTaskState state;
	private String instanceId;
	private String manifest;

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

}

/**
 * InstanceType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

package edu.ucsb.eucalyptus;

/**
 * InstanceType bean class
 */
@SuppressWarnings({ "unchecked", "unused" })
public class InstanceType implements org.apache.axis2.databinding.ADBBean {
	/*
	 * This type was generated from the piece of schema that had name =
	 * instanceType Namespace URI = http://eucalyptus.ucsb.edu/ Namespace Prefix
	 * = ns1
	 */

	/**
	 * field for Uuid
	 */

	protected java.lang.String localUuid;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUuid() {
		return localUuid;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Uuid
	 */
	public void setUuid(java.lang.String param) {

		this.localUuid = param;

	}

	/**
	 * field for ReservationId
	 */

	protected java.lang.String localReservationId;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getReservationId() {
		return localReservationId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            ReservationId
	 */
	public void setReservationId(java.lang.String param) {

		this.localReservationId = param;

	}

	/**
	 * field for InstanceId
	 */

	protected java.lang.String localInstanceId;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getInstanceId() {
		return localInstanceId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            InstanceId
	 */
	public void setInstanceId(java.lang.String param) {

		this.localInstanceId = param;

	}

	/**
	 * field for ImageId
	 */

	protected java.lang.String localImageId;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getImageId() {
		return localImageId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            ImageId
	 */
	public void setImageId(java.lang.String param) {

		this.localImageId = param;

	}

	/**
	 * field for KernelId
	 */

	protected java.lang.String localKernelId;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localKernelIdTracker = false;

	public boolean isKernelIdSpecified() {
		return localKernelIdTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getKernelId() {
		return localKernelId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            KernelId
	 */
	public void setKernelId(java.lang.String param) {
		localKernelIdTracker = param != null;

		this.localKernelId = param;

	}

	/**
	 * field for RamdiskId
	 */

	protected java.lang.String localRamdiskId;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localRamdiskIdTracker = false;

	public boolean isRamdiskIdSpecified() {
		return localRamdiskIdTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getRamdiskId() {
		return localRamdiskId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            RamdiskId
	 */
	public void setRamdiskId(java.lang.String param) {
		localRamdiskIdTracker = param != null;

		this.localRamdiskId = param;

	}

	/**
	 * field for UserId
	 */

	protected java.lang.String localUserId;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUserId() {
		return localUserId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            UserId
	 */
	public void setUserId(java.lang.String param) {

		this.localUserId = param;

	}

	/**
	 * field for OwnerId
	 */

	protected java.lang.String localOwnerId;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getOwnerId() {
		return localOwnerId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            OwnerId
	 */
	public void setOwnerId(java.lang.String param) {

		this.localOwnerId = param;

	}

	/**
	 * field for AccountId
	 */

	protected java.lang.String localAccountId;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAccountId() {
		return localAccountId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            AccountId
	 */
	public void setAccountId(java.lang.String param) {

		this.localAccountId = param;

	}

	/**
	 * field for KeyName
	 */

	protected java.lang.String localKeyName;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getKeyName() {
		return localKeyName;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            KeyName
	 */
	public void setKeyName(java.lang.String param) {

		this.localKeyName = param;

	}

	/**
	 * field for InstanceType
	 */

	protected edu.ucsb.eucalyptus.VirtualMachineType localInstanceType;

	/**
	 * Auto generated getter method
	 * 
	 * @return edu.ucsb.eucalyptus.VirtualMachineType
	 */
	public edu.ucsb.eucalyptus.VirtualMachineType getInstanceType() {
		return localInstanceType;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            InstanceType
	 */
	public void setInstanceType(edu.ucsb.eucalyptus.VirtualMachineType param) {

		this.localInstanceType = param;

	}

	/**
	 * field for NetParams
	 */

	protected edu.ucsb.eucalyptus.NetConfigType localNetParams;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localNetParamsTracker = false;

	public boolean isNetParamsSpecified() {
		return localNetParamsTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return edu.ucsb.eucalyptus.NetConfigType
	 */
	public edu.ucsb.eucalyptus.NetConfigType getNetParams() {
		return localNetParams;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            NetParams
	 */
	public void setNetParams(edu.ucsb.eucalyptus.NetConfigType param) {
		localNetParamsTracker = param != null;

		this.localNetParams = param;

	}

	/**
	 * field for StateName
	 */

	protected java.lang.String localStateName;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getStateName() {
		return localStateName;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            StateName
	 */
	public void setStateName(java.lang.String param) {

		this.localStateName = param;

	}

	/**
	 * field for BundleTaskStateName
	 */

	protected java.lang.String localBundleTaskStateName;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localBundleTaskStateNameTracker = false;

	public boolean isBundleTaskStateNameSpecified() {
		return localBundleTaskStateNameTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getBundleTaskStateName() {
		return localBundleTaskStateName;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            BundleTaskStateName
	 */
	public void setBundleTaskStateName(java.lang.String param) {
		localBundleTaskStateNameTracker = param != null;

		this.localBundleTaskStateName = param;

	}

	/**
	 * field for CreateImageStateName
	 */

	protected java.lang.String localCreateImageStateName;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localCreateImageStateNameTracker = false;

	public boolean isCreateImageStateNameSpecified() {
		return localCreateImageStateNameTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getCreateImageStateName() {
		return localCreateImageStateName;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            CreateImageStateName
	 */
	public void setCreateImageStateName(java.lang.String param) {
		localCreateImageStateNameTracker = param != null;

		this.localCreateImageStateName = param;

	}

	/**
	 * field for LaunchTime
	 */

	protected java.util.Calendar localLaunchTime;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localLaunchTimeTracker = false;

	public boolean isLaunchTimeSpecified() {
		return localLaunchTimeTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.util.Calendar
	 */
	public java.util.Calendar getLaunchTime() {
		return localLaunchTime;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            LaunchTime
	 */
	public void setLaunchTime(java.util.Calendar param) {
		localLaunchTimeTracker = param != null;

		this.localLaunchTime = param;

	}

	/**
	 * field for ExpiryTime
	 */

	protected java.util.Calendar localExpiryTime;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localExpiryTimeTracker = false;

	public boolean isExpiryTimeSpecified() {
		return localExpiryTimeTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.util.Calendar
	 */
	public java.util.Calendar getExpiryTime() {
		return localExpiryTime;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            ExpiryTime
	 */
	public void setExpiryTime(java.util.Calendar param) {
		localExpiryTimeTracker = param != null;

		this.localExpiryTime = param;

	}

	/**
	 * field for Blkbytes
	 */

	protected int localBlkbytes;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localBlkbytesTracker = false;

	public boolean isBlkbytesSpecified() {
		return localBlkbytesTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return int
	 */
	public int getBlkbytes() {
		return localBlkbytes;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Blkbytes
	 */
	public void setBlkbytes(int param) {

		// setting primitive attribute tracker to true
		localBlkbytesTracker = param != java.lang.Integer.MIN_VALUE;

		this.localBlkbytes = param;

	}

	/**
	 * field for Netbytes
	 */

	protected int localNetbytes;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localNetbytesTracker = false;

	public boolean isNetbytesSpecified() {
		return localNetbytesTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return int
	 */
	public int getNetbytes() {
		return localNetbytes;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Netbytes
	 */
	public void setNetbytes(int param) {

		// setting primitive attribute tracker to true
		localNetbytesTracker = param != java.lang.Integer.MIN_VALUE;

		this.localNetbytes = param;

	}

	/**
	 * field for UserData
	 */

	protected java.lang.String localUserData;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localUserDataTracker = false;

	public boolean isUserDataSpecified() {
		return localUserDataTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUserData() {
		return localUserData;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            UserData
	 */
	public void setUserData(java.lang.String param) {
		localUserDataTracker = param != null;

		this.localUserData = param;

	}

	/**
	 * field for LaunchIndex
	 */

	protected java.lang.String localLaunchIndex;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localLaunchIndexTracker = false;

	public boolean isLaunchIndexSpecified() {
		return localLaunchIndexTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getLaunchIndex() {
		return localLaunchIndex;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            LaunchIndex
	 */
	public void setLaunchIndex(java.lang.String param) {
		localLaunchIndexTracker = param != null;

		this.localLaunchIndex = param;

	}

	/**
	 * field for Platform
	 */

	protected java.lang.String localPlatform;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localPlatformTracker = false;

	public boolean isPlatformSpecified() {
		return localPlatformTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPlatform() {
		return localPlatform;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Platform
	 */
	public void setPlatform(java.lang.String param) {
		localPlatformTracker = param != null;

		this.localPlatform = param;

	}

	/**
	 * field for GroupNames This was an Array!
	 */

	protected java.lang.String[] localGroupNames;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localGroupNamesTracker = false;

	public boolean isGroupNamesSpecified() {
		return localGroupNamesTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getGroupNames() {
		return localGroupNames;
	}

	/**
	 * validate the array for GroupNames
	 */
	protected void validateGroupNames(java.lang.String[] param) {

		if ((param != null) && (param.length > 64)) {
			throw new java.lang.RuntimeException();
		}

	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            GroupNames
	 */
	public void setGroupNames(java.lang.String[] param) {

		validateGroupNames(param);

		localGroupNamesTracker = param != null;

		this.localGroupNames = param;
	}

	/**
	 * Auto generated add method for the array for convenience
	 * 
	 * @param param
	 *            java.lang.String
	 */
	public void addGroupNames(java.lang.String param) {
		if (localGroupNames == null) {
			localGroupNames = new java.lang.String[] {};
		}

		// update the setting tracker
		localGroupNamesTracker = true;

		java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
				.toList(localGroupNames);
		list.add(param);
		this.localGroupNames = (java.lang.String[]) list
				.toArray(new java.lang.String[list.size()]);

	}

	/**
	 * field for Volumes This was an Array!
	 */

	protected edu.ucsb.eucalyptus.VolumeType[] localVolumes;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localVolumesTracker = false;

	public boolean isVolumesSpecified() {
		return localVolumesTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return edu.ucsb.eucalyptus.VolumeType[]
	 */
	public edu.ucsb.eucalyptus.VolumeType[] getVolumes() {
		return localVolumes;
	}

	/**
	 * validate the array for Volumes
	 */
	protected void validateVolumes(edu.ucsb.eucalyptus.VolumeType[] param) {

		if ((param != null) && (param.length > 64)) {
			throw new java.lang.RuntimeException();
		}

	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Volumes
	 */
	public void setVolumes(edu.ucsb.eucalyptus.VolumeType[] param) {

		validateVolumes(param);

		localVolumesTracker = param != null;

		this.localVolumes = param;
	}

	/**
	 * Auto generated add method for the array for convenience
	 * 
	 * @param param
	 *            edu.ucsb.eucalyptus.VolumeType
	 */
	public void addVolumes(edu.ucsb.eucalyptus.VolumeType param) {
		if (localVolumes == null) {
			localVolumes = new edu.ucsb.eucalyptus.VolumeType[] {};
		}

		// update the setting tracker
		localVolumesTracker = true;

		java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
				.toList(localVolumes);
		list.add(param);
		this.localVolumes = (edu.ucsb.eucalyptus.VolumeType[]) list
				.toArray(new edu.ucsb.eucalyptus.VolumeType[list.size()]);

	}

	/**
	 * field for ServiceTag
	 */

	protected java.lang.String localServiceTag;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localServiceTagTracker = false;

	public boolean isServiceTagSpecified() {
		return localServiceTagTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getServiceTag() {
		return localServiceTag;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            ServiceTag
	 */
	public void setServiceTag(java.lang.String param) {
		localServiceTagTracker = param != null;

		this.localServiceTag = param;

	}

	/**
	 * 
	 * @param parentQName
	 * @param factory
	 * @return org.apache.axiom.om.OMElement
	 */
	public org.apache.axiom.om.OMElement getOMElement(
			final javax.xml.namespace.QName parentQName,
			final org.apache.axiom.om.OMFactory factory)
			throws org.apache.axis2.databinding.ADBException {

		org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
				this, parentQName);
		return factory.createOMElement(dataSource, parentQName);

	}

	public void serialize(final javax.xml.namespace.QName parentQName,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
		serialize(parentQName, xmlWriter, false);
	}

	public void serialize(final javax.xml.namespace.QName parentQName,
			javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
			throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {

		java.lang.String prefix = null;
		java.lang.String namespace = null;

		prefix = parentQName.getPrefix();
		namespace = parentQName.getNamespaceURI();
		writeStartElement(prefix, namespace, parentQName.getLocalPart(),
				xmlWriter);

		if (serializeType) {

			java.lang.String namespacePrefix = registerPrefix(xmlWriter,
					"http://eucalyptus.ucsb.edu/");
			if ((namespacePrefix != null)
					&& (namespacePrefix.trim().length() > 0)) {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						namespacePrefix + ":instanceType", xmlWriter);
			} else {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						"instanceType", xmlWriter);
			}

		}

		namespace = "http://eucalyptus.ucsb.edu/";
		writeStartElement(null, namespace, "uuid", xmlWriter);

		if (localUuid == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"uuid cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localUuid);

		}

		xmlWriter.writeEndElement();

		namespace = "http://eucalyptus.ucsb.edu/";
		writeStartElement(null, namespace, "reservationId", xmlWriter);

		if (localReservationId == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"reservationId cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localReservationId);

		}

		xmlWriter.writeEndElement();

		namespace = "http://eucalyptus.ucsb.edu/";
		writeStartElement(null, namespace, "instanceId", xmlWriter);

		if (localInstanceId == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"instanceId cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localInstanceId);

		}

		xmlWriter.writeEndElement();

		namespace = "http://eucalyptus.ucsb.edu/";
		writeStartElement(null, namespace, "imageId", xmlWriter);

		if (localImageId == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"imageId cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localImageId);

		}

		xmlWriter.writeEndElement();
		if (localKernelIdTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "kernelId", xmlWriter);

			if (localKernelId == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"kernelId cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localKernelId);

			}

			xmlWriter.writeEndElement();
		}
		if (localRamdiskIdTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "ramdiskId", xmlWriter);

			if (localRamdiskId == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"ramdiskId cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localRamdiskId);

			}

			xmlWriter.writeEndElement();
		}
		namespace = "http://eucalyptus.ucsb.edu/";
		writeStartElement(null, namespace, "userId", xmlWriter);

		if (localUserId == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"userId cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localUserId);

		}

		xmlWriter.writeEndElement();

		namespace = "http://eucalyptus.ucsb.edu/";
		writeStartElement(null, namespace, "ownerId", xmlWriter);

		if (localOwnerId == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"ownerId cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localOwnerId);

		}

		xmlWriter.writeEndElement();

		namespace = "http://eucalyptus.ucsb.edu/";
		writeStartElement(null, namespace, "accountId", xmlWriter);

		if (localAccountId == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"accountId cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localAccountId);

		}

		xmlWriter.writeEndElement();

		namespace = "http://eucalyptus.ucsb.edu/";
		writeStartElement(null, namespace, "keyName", xmlWriter);

		if (localKeyName == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"keyName cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localKeyName);

		}

		xmlWriter.writeEndElement();

		if (localInstanceType == null) {
			throw new org.apache.axis2.databinding.ADBException(
					"instanceType cannot be null!!");
		}
		localInstanceType.serialize(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "instanceType"), xmlWriter);
		if (localNetParamsTracker) {
			if (localNetParams == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"netParams cannot be null!!");
			}
			localNetParams.serialize(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "netParams"), xmlWriter);
		}
		namespace = "http://eucalyptus.ucsb.edu/";
		writeStartElement(null, namespace, "stateName", xmlWriter);

		if (localStateName == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"stateName cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localStateName);

		}

		xmlWriter.writeEndElement();
		if (localBundleTaskStateNameTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "bundleTaskStateName", xmlWriter);

			if (localBundleTaskStateName == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"bundleTaskStateName cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localBundleTaskStateName);

			}

			xmlWriter.writeEndElement();
		}
		if (localCreateImageStateNameTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "createImageStateName",
					xmlWriter);

			if (localCreateImageStateName == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"createImageStateName cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localCreateImageStateName);

			}

			xmlWriter.writeEndElement();
		}
		if (localLaunchTimeTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "launchTime", xmlWriter);

			if (localLaunchTime == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"launchTime cannot be null!!");

			} else {

				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localLaunchTime));

			}

			xmlWriter.writeEndElement();
		}
		if (localExpiryTimeTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "expiryTime", xmlWriter);

			if (localExpiryTime == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"expiryTime cannot be null!!");

			} else {

				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localExpiryTime));

			}

			xmlWriter.writeEndElement();
		}
		if (localBlkbytesTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "blkbytes", xmlWriter);

			if (localBlkbytes == java.lang.Integer.MIN_VALUE) {

				throw new org.apache.axis2.databinding.ADBException(
						"blkbytes cannot be null!!");

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localBlkbytes));
			}

			xmlWriter.writeEndElement();
		}
		if (localNetbytesTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "netbytes", xmlWriter);

			if (localNetbytes == java.lang.Integer.MIN_VALUE) {

				throw new org.apache.axis2.databinding.ADBException(
						"netbytes cannot be null!!");

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localNetbytes));
			}

			xmlWriter.writeEndElement();
		}
		if (localUserDataTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "userData", xmlWriter);

			if (localUserData == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"userData cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localUserData);

			}

			xmlWriter.writeEndElement();
		}
		if (localLaunchIndexTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "launchIndex", xmlWriter);

			if (localLaunchIndex == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"launchIndex cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localLaunchIndex);

			}

			xmlWriter.writeEndElement();
		}
		if (localPlatformTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "platform", xmlWriter);

			if (localPlatform == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"platform cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localPlatform);

			}

			xmlWriter.writeEndElement();
		}
		if (localGroupNamesTracker) {
			if (localGroupNames != null) {
				namespace = "http://eucalyptus.ucsb.edu/";
				for (int i = 0; i < localGroupNames.length; i++) {

					if (localGroupNames[i] != null) {

						writeStartElement(null, namespace, "groupNames",
								xmlWriter);

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localGroupNames[i]));

						xmlWriter.writeEndElement();

					} else {

						// we have to do nothing since minOccurs is zero

					}

				}
			} else {

				throw new org.apache.axis2.databinding.ADBException(
						"groupNames cannot be null!!");

			}

		}
		if (localVolumesTracker) {
			if (localVolumes != null) {
				for (int i = 0; i < localVolumes.length; i++) {
					if (localVolumes[i] != null) {
						localVolumes[i].serialize(
								new javax.xml.namespace.QName(
										"http://eucalyptus.ucsb.edu/",
										"volumes"), xmlWriter);
					} else {

						// we don't have to do any thing since minOccures is
						// zero

					}

				}
			} else {

				throw new org.apache.axis2.databinding.ADBException(
						"volumes cannot be null!!");

			}
		}
		if (localServiceTagTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "serviceTag", xmlWriter);

			if (localServiceTag == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"serviceTag cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localServiceTag);

			}

			xmlWriter.writeEndElement();
		}
		xmlWriter.writeEndElement();

	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {
		if (namespace.equals("http://eucalyptus.ucsb.edu/")) {
			return "ns1";
		}
		return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
	}

	/**
	 * Utility method to write an element start tag.
	 */
	private void writeStartElement(java.lang.String prefix,
			java.lang.String namespace, java.lang.String localPart,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
		if (writerPrefix != null) {
			xmlWriter.writeStartElement(namespace, localPart);
		} else {
			if (namespace.length() == 0) {
				prefix = "";
			} else if (prefix == null) {
				prefix = generatePrefix(namespace);
			}

			xmlWriter.writeStartElement(prefix, localPart, namespace);
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
	}

	/**
	 * Util method to write an attribute with the ns prefix
	 */
	private void writeAttribute(java.lang.String prefix,
			java.lang.String namespace, java.lang.String attName,
			java.lang.String attValue,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		if (xmlWriter.getPrefix(namespace) == null) {
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
		xmlWriter.writeAttribute(namespace, attName, attValue);
	}

	/**
	 * Util method to write an attribute without the ns prefix
	 */
	private void writeAttribute(java.lang.String namespace,
			java.lang.String attName, java.lang.String attValue,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		if (namespace.equals("")) {
			xmlWriter.writeAttribute(attName, attValue);
		} else {
			registerPrefix(xmlWriter, namespace);
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}
	}

	/**
	 * Util method to write an attribute without the ns prefix
	 */
	private void writeQNameAttribute(java.lang.String namespace,
			java.lang.String attName, javax.xml.namespace.QName qname,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {

		java.lang.String attributeNamespace = qname.getNamespaceURI();
		java.lang.String attributePrefix = xmlWriter
				.getPrefix(attributeNamespace);
		if (attributePrefix == null) {
			attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
		}
		java.lang.String attributeValue;
		if (attributePrefix.trim().length() > 0) {
			attributeValue = attributePrefix + ":" + qname.getLocalPart();
		} else {
			attributeValue = qname.getLocalPart();
		}

		if (namespace.equals("")) {
			xmlWriter.writeAttribute(attName, attributeValue);
		} else {
			registerPrefix(xmlWriter, namespace);
			xmlWriter.writeAttribute(namespace, attName, attributeValue);
		}
	}

	/**
	 * method to handle Qnames
	 */

	private void writeQName(javax.xml.namespace.QName qname,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String namespaceURI = qname.getNamespaceURI();
		if (namespaceURI != null) {
			java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
			if (prefix == null) {
				prefix = generatePrefix(namespaceURI);
				xmlWriter.writeNamespace(prefix, namespaceURI);
				xmlWriter.setPrefix(prefix, namespaceURI);
			}

			if (prefix.trim().length() > 0) {
				xmlWriter.writeCharacters(prefix
						+ ":"
						+ org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			} else {
				// i.e this is the default namespace
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}

		} else {
			xmlWriter
					.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(qname));
		}
	}

	private void writeQNames(javax.xml.namespace.QName[] qnames,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {

		if (qnames != null) {
			// we have to store this data until last moment since it is not
			// possible to write any
			// namespace data after writing the charactor data
			java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
			java.lang.String namespaceURI = null;
			java.lang.String prefix = null;

			for (int i = 0; i < qnames.length; i++) {
				if (i > 0) {
					stringToWrite.append(" ");
				}
				namespaceURI = qnames[i].getNamespaceURI();
				if (namespaceURI != null) {
					prefix = xmlWriter.getPrefix(namespaceURI);
					if ((prefix == null) || (prefix.length() == 0)) {
						prefix = generatePrefix(namespaceURI);
						xmlWriter.writeNamespace(prefix, namespaceURI);
						xmlWriter.setPrefix(prefix, namespaceURI);
					}

					if (prefix.trim().length() > 0) {
						stringToWrite
								.append(prefix)
								.append(":")
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				} else {
					stringToWrite
							.append(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qnames[i]));
				}
			}
			xmlWriter.writeCharacters(stringToWrite.toString());
		}

	}

	/**
	 * Register a namespace prefix
	 */
	private java.lang.String registerPrefix(
			javax.xml.stream.XMLStreamWriter xmlWriter,
			java.lang.String namespace)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String prefix = xmlWriter.getPrefix(namespace);
		if (prefix == null) {
			prefix = generatePrefix(namespace);
			javax.xml.namespace.NamespaceContext nsContext = xmlWriter
					.getNamespaceContext();
			while (true) {
				java.lang.String uri = nsContext.getNamespaceURI(prefix);
				if (uri == null || uri.length() == 0) {
					break;
				}
				prefix = org.apache.axis2.databinding.utils.BeanUtil
						.getUniquePrefix();
			}
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
		return prefix;
	}

	/**
	 * databinding method to get an XML representation of this object
	 * 
	 */
	public javax.xml.stream.XMLStreamReader getPullParser(
			javax.xml.namespace.QName qName)
			throws org.apache.axis2.databinding.ADBException {

		java.util.ArrayList elementList = new java.util.ArrayList();
		java.util.ArrayList attribList = new java.util.ArrayList();

		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "uuid"));

		if (localUuid != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localUuid));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"uuid cannot be null!!");
		}

		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "reservationId"));

		if (localReservationId != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localReservationId));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"reservationId cannot be null!!");
		}

		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "instanceId"));

		if (localInstanceId != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localInstanceId));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"instanceId cannot be null!!");
		}

		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "imageId"));

		if (localImageId != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localImageId));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"imageId cannot be null!!");
		}
		if (localKernelIdTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "kernelId"));

			if (localKernelId != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localKernelId));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"kernelId cannot be null!!");
			}
		}
		if (localRamdiskIdTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "ramdiskId"));

			if (localRamdiskId != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localRamdiskId));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"ramdiskId cannot be null!!");
			}
		}
		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "userId"));

		if (localUserId != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localUserId));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"userId cannot be null!!");
		}

		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "ownerId"));

		if (localOwnerId != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localOwnerId));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"ownerId cannot be null!!");
		}

		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "accountId"));

		if (localAccountId != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localAccountId));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"accountId cannot be null!!");
		}

		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "keyName"));

		if (localKeyName != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localKeyName));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"keyName cannot be null!!");
		}

		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "instanceType"));

		if (localInstanceType == null) {
			throw new org.apache.axis2.databinding.ADBException(
					"instanceType cannot be null!!");
		}
		elementList.add(localInstanceType);
		if (localNetParamsTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "netParams"));

			if (localNetParams == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"netParams cannot be null!!");
			}
			elementList.add(localNetParams);
		}
		elementList.add(new javax.xml.namespace.QName(
				"http://eucalyptus.ucsb.edu/", "stateName"));

		if (localStateName != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localStateName));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"stateName cannot be null!!");
		}
		if (localBundleTaskStateNameTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "bundleTaskStateName"));

			if (localBundleTaskStateName != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localBundleTaskStateName));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"bundleTaskStateName cannot be null!!");
			}
		}
		if (localCreateImageStateNameTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "createImageStateName"));

			if (localCreateImageStateName != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localCreateImageStateName));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"createImageStateName cannot be null!!");
			}
		}
		if (localLaunchTimeTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "launchTime"));

			if (localLaunchTime != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localLaunchTime));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"launchTime cannot be null!!");
			}
		}
		if (localExpiryTimeTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "expiryTime"));

			if (localExpiryTime != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localExpiryTime));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"expiryTime cannot be null!!");
			}
		}
		if (localBlkbytesTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "blkbytes"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localBlkbytes));
		}
		if (localNetbytesTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "netbytes"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localNetbytes));
		}
		if (localUserDataTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "userData"));

			if (localUserData != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localUserData));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"userData cannot be null!!");
			}
		}
		if (localLaunchIndexTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "launchIndex"));

			if (localLaunchIndex != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localLaunchIndex));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"launchIndex cannot be null!!");
			}
		}
		if (localPlatformTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "platform"));

			if (localPlatform != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localPlatform));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"platform cannot be null!!");
			}
		}
		if (localGroupNamesTracker) {
			if (localGroupNames != null) {
				for (int i = 0; i < localGroupNames.length; i++) {

					if (localGroupNames[i] != null) {
						elementList.add(new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "groupNames"));
						elementList
								.add(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localGroupNames[i]));
					} else {

						// have to do nothing

					}

				}
			} else {

				throw new org.apache.axis2.databinding.ADBException(
						"groupNames cannot be null!!");

			}

		}
		if (localVolumesTracker) {
			if (localVolumes != null) {
				for (int i = 0; i < localVolumes.length; i++) {

					if (localVolumes[i] != null) {
						elementList.add(new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "volumes"));
						elementList.add(localVolumes[i]);
					} else {

						// nothing to do

					}

				}
			} else {

				throw new org.apache.axis2.databinding.ADBException(
						"volumes cannot be null!!");

			}

		}
		if (localServiceTagTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "serviceTag"));

			if (localServiceTag != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localServiceTag));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"serviceTag cannot be null!!");
			}
		}

		return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
				qName, elementList.toArray(), attribList.toArray());

	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

		/**
		 * static method to create the object Precondition: If this object is an
		 * element, the current or next start element starts this object and any
		 * intervening reader events are ignorable If this object is not an
		 * element, it is a complex type and the reader is at the event just
		 * after the outer start element Postcondition: If this object is an
		 * element, the reader is positioned at its end element If this object
		 * is a complex type, the reader is positioned at the end element of its
		 * outer element
		 */
		public static InstanceType parse(javax.xml.stream.XMLStreamReader reader)
				throws java.lang.Exception {
			InstanceType object = new InstanceType();

			int event;
			java.lang.String nillableValue = null;
			java.lang.String prefix = "";
			java.lang.String namespaceuri = "";
			try {

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.getAttributeValue(
						"http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
					java.lang.String fullTypeName = reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type");
					if (fullTypeName != null) {
						java.lang.String nsPrefix = null;
						if (fullTypeName.indexOf(":") > -1) {
							nsPrefix = fullTypeName.substring(0,
									fullTypeName.indexOf(":"));
						}
						nsPrefix = nsPrefix == null ? "" : nsPrefix;

						java.lang.String type = fullTypeName
								.substring(fullTypeName.indexOf(":") + 1);

						if (!"instanceType".equals(type)) {
							// find namespace for the prefix
							java.lang.String nsUri = reader
									.getNamespaceContext().getNamespaceURI(
											nsPrefix);
							return (InstanceType) edu.ucsb.eucalyptus.ExtensionMapper
									.getTypeObject(nsUri, type, reader);
						}

					}

				}

				// Note all attributes that were handled. Used to differ normal
				// attributes
				// from anyAttributes.
				java.util.Vector handledAttributes = new java.util.Vector();

				reader.next();

				java.util.ArrayList list23 = new java.util.ArrayList();

				java.util.ArrayList list24 = new java.util.ArrayList();

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "uuid")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "uuid" + "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setUuid(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "reservationId")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "reservationId"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setReservationId(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "instanceId")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "instanceId"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setInstanceId(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "imageId")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "imageId"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setImageId(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "kernelId")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "kernelId"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setKernelId(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "ramdiskId")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "ramdiskId"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setRamdiskId(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "userId")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "userId" + "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setUserId(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "ownerId")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "ownerId"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setOwnerId(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "accountId")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "accountId"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setAccountId(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "keyName")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "keyName"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setKeyName(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "instanceType")
								.equals(reader.getName())) {

					object.setInstanceType(edu.ucsb.eucalyptus.VirtualMachineType.Factory
							.parse(reader));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "netParams")
								.equals(reader.getName())) {

					object.setNetParams(edu.ucsb.eucalyptus.NetConfigType.Factory
							.parse(reader));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "stateName")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "stateName"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setStateName(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/",
								"bundleTaskStateName").equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "bundleTaskStateName"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setBundleTaskStateName(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/",
								"createImageStateName")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "createImageStateName"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setCreateImageStateName(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "launchTime")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "launchTime"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setLaunchTime(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToDateTime(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "expiryTime")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "expiryTime"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setExpiryTime(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToDateTime(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "blkbytes")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "blkbytes"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setBlkbytes(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToInt(content));

					reader.next();

				} // End of if for expected property start element

				else {

					object.setBlkbytes(java.lang.Integer.MIN_VALUE);

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "netbytes")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "netbytes"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setNetbytes(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToInt(content));

					reader.next();

				} // End of if for expected property start element

				else {

					object.setNetbytes(java.lang.Integer.MIN_VALUE);

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "userData")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "userData"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setUserData(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "launchIndex")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "launchIndex"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setLaunchIndex(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "platform")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "platform"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setPlatform(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "groupNames")
								.equals(reader.getName())) {

					// Process the array and step past its final element's end.
					list23.add(reader.getElementText());

					// loop until we find a start element that is not part of
					// this array
					boolean loopDone23 = false;
					while (!loopDone23) {
						// Ensure we are at the EndElement
						while (!reader.isEndElement()) {
							reader.next();
						}
						// Step out of this element
						reader.next();
						// Step to next element event.
						while (!reader.isStartElement()
								&& !reader.isEndElement())
							reader.next();
						if (reader.isEndElement()) {
							// two continuous end elements means we are exiting
							// the xml structure
							loopDone23 = true;
						} else {
							if (new javax.xml.namespace.QName(
									"http://eucalyptus.ucsb.edu/", "groupNames")
									.equals(reader.getName())) {
								list23.add(reader.getElementText());

							} else {
								loopDone23 = true;
							}
						}
					}
					// call the converter utility to convert and set the array

					object.setGroupNames((java.lang.String[]) list23
							.toArray(new java.lang.String[list23.size()]));

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "volumes")
								.equals(reader.getName())) {

					// Process the array and step past its final element's end.
					list24.add(edu.ucsb.eucalyptus.VolumeType.Factory
							.parse(reader));

					// loop until we find a start element that is not part of
					// this array
					boolean loopDone24 = false;
					while (!loopDone24) {
						// We should be at the end element, but make sure
						while (!reader.isEndElement())
							reader.next();
						// Step out of this element
						reader.next();
						// Step to next element event.
						while (!reader.isStartElement()
								&& !reader.isEndElement())
							reader.next();
						if (reader.isEndElement()) {
							// two continuous end elements means we are exiting
							// the xml structure
							loopDone24 = true;
						} else {
							if (new javax.xml.namespace.QName(
									"http://eucalyptus.ucsb.edu/", "volumes")
									.equals(reader.getName())) {
								list24.add(edu.ucsb.eucalyptus.VolumeType.Factory
										.parse(reader));

							} else {
								loopDone24 = true;
							}
						}
					}
					// call the converter utility to convert and set the array

					object.setVolumes((edu.ucsb.eucalyptus.VolumeType[]) org.apache.axis2.databinding.utils.ConverterUtil
							.convertToArray(
									edu.ucsb.eucalyptus.VolumeType.class,
									list24));

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "serviceTag")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "serviceTag"
										+ "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setServiceTag(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement())
					// A start element we are not expecting indicates a trailing
					// invalid property
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());

			} catch (javax.xml.stream.XMLStreamException e) {
				throw new java.lang.Exception(e);
			}

			return object;
		}

	}// end of factory class

}

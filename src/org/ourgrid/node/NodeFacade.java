package org.ourgrid.node;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.hyperic.sigar.SigarException;
import org.ourgrid.node.idleness.IdlenessDetector;
import org.ourgrid.node.idleness.IdlenessListener;
import org.ourgrid.node.idleness.LinuxDevInputIdlenessDetector;
import org.ourgrid.node.idleness.Win32IdlenessDetector;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.model.Resources;
import org.ourgrid.node.model.VBR;
import org.ourgrid.node.model.bundle.BundleTask;
import org.ourgrid.node.model.bundle.BundleTaskState;
import org.ourgrid.node.model.sensor.SensorCache;
import org.ourgrid.node.model.sensor.SensorResource;
import org.ourgrid.node.model.volume.VolumeData;
import org.ourgrid.node.model.volume.VolumeState;
import org.ourgrid.node.util.ISCSIUtils;
import org.ourgrid.node.util.NetUtils;
import org.ourgrid.node.util.NodeProperties;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.node.util.ResourcesInfoGatherer;
import org.ourgrid.node.util.Sensor;
import org.ourgrid.node.util.VBRUtils;
import org.ourgrid.node.util.VolumeUtils;
import org.ourgrid.node.util.WalrusUtils;

import edu.ucsb.eucalyptus.BundleTaskType;
import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcAssignAddress;
import edu.ucsb.eucalyptus.NcAssignAddressResponse;
import edu.ucsb.eucalyptus.NcAssignAddressResponseType;
import edu.ucsb.eucalyptus.NcAssignAddressType;
import edu.ucsb.eucalyptus.NcAttachVolume;
import edu.ucsb.eucalyptus.NcAttachVolumeResponse;
import edu.ucsb.eucalyptus.NcAttachVolumeResponseType;
import edu.ucsb.eucalyptus.NcAttachVolumeType;
import edu.ucsb.eucalyptus.NcBundleInstance;
import edu.ucsb.eucalyptus.NcBundleInstanceResponse;
import edu.ucsb.eucalyptus.NcBundleInstanceResponseType;
import edu.ucsb.eucalyptus.NcBundleInstanceType;
import edu.ucsb.eucalyptus.NcCancelBundleTask;
import edu.ucsb.eucalyptus.NcCancelBundleTaskResponse;
import edu.ucsb.eucalyptus.NcCancelBundleTaskResponseType;
import edu.ucsb.eucalyptus.NcCancelBundleTaskType;
import edu.ucsb.eucalyptus.NcDescribeBundleTasks;
import edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse;
import edu.ucsb.eucalyptus.NcDescribeBundleTasksResponseType;
import edu.ucsb.eucalyptus.NcDescribeBundleTasksType;
import edu.ucsb.eucalyptus.NcDescribeInstances;
import edu.ucsb.eucalyptus.NcDescribeInstancesResponse;
import edu.ucsb.eucalyptus.NcDescribeInstancesResponseType;
import edu.ucsb.eucalyptus.NcDescribeInstancesType;
import edu.ucsb.eucalyptus.NcDescribeResource;
import edu.ucsb.eucalyptus.NcDescribeResourceResponse;
import edu.ucsb.eucalyptus.NcDescribeResourceResponseType;
import edu.ucsb.eucalyptus.NcDescribeResourceType;
import edu.ucsb.eucalyptus.NcDescribeSensors;
import edu.ucsb.eucalyptus.NcDescribeSensorsResponse;
import edu.ucsb.eucalyptus.NcDescribeSensorsResponseType;
import edu.ucsb.eucalyptus.NcDescribeSensorsType;
import edu.ucsb.eucalyptus.NcDetachVolume;
import edu.ucsb.eucalyptus.NcDetachVolumeResponse;
import edu.ucsb.eucalyptus.NcDetachVolumeResponseType;
import edu.ucsb.eucalyptus.NcDetachVolumeType;
import edu.ucsb.eucalyptus.NcGetConsoleOutput;
import edu.ucsb.eucalyptus.NcGetConsoleOutputResponse;
import edu.ucsb.eucalyptus.NcGetConsoleOutputResponseType;
import edu.ucsb.eucalyptus.NcGetConsoleOutputType;
import edu.ucsb.eucalyptus.NcPowerDown;
import edu.ucsb.eucalyptus.NcPowerDownResponse;
import edu.ucsb.eucalyptus.NcPowerDownResponseType;
import edu.ucsb.eucalyptus.NcPowerDownType;
import edu.ucsb.eucalyptus.NcRebootInstance;
import edu.ucsb.eucalyptus.NcRebootInstanceResponse;
import edu.ucsb.eucalyptus.NcRebootInstanceResponseType;
import edu.ucsb.eucalyptus.NcRebootInstanceType;
import edu.ucsb.eucalyptus.NcRunInstance;
import edu.ucsb.eucalyptus.NcRunInstanceResponse;
import edu.ucsb.eucalyptus.NcRunInstanceResponseType;
import edu.ucsb.eucalyptus.NcRunInstanceType;
import edu.ucsb.eucalyptus.NcStartNetwork;
import edu.ucsb.eucalyptus.NcStartNetworkResponse;
import edu.ucsb.eucalyptus.NcStartNetworkResponseType;
import edu.ucsb.eucalyptus.NcStartNetworkType;
import edu.ucsb.eucalyptus.NcTerminateInstance;
import edu.ucsb.eucalyptus.NcTerminateInstanceResponse;
import edu.ucsb.eucalyptus.NcTerminateInstanceResponseType;
import edu.ucsb.eucalyptus.NcTerminateInstanceType;
import edu.ucsb.eucalyptus.NetConfigType;
import edu.ucsb.eucalyptus.SensorsResourceType;
import edu.ucsb.eucalyptus.VolumeType;

public class NodeFacade implements IdlenessListener {

	private static final int DEF_POLLING_INTERVAL = 300000;
	private static final String SUCCESS_STATE = "0";
	private static final String UNSUCCESS_STATE = "2";
	private final static Logger LOGGER = Logger.getLogger(NodeFacade.class);
	private static NodeFacade instance = null;
	
	private InstanceRepository instanceRepository = new InstanceRepository();
	private ResourcesInfoGatherer resourcesGatherer;
	private Properties properties;
	private IdlenessDetector idlenessChecker;
	private Executor threadPool = Executors.newFixedThreadPool(20);
	private ScheduledExecutorService sensorExecutor = Executors.newScheduledThreadPool(1);
	private Sensor sensor;
	private Map<String, BundleTask> bundleTasks = new HashMap<String, BundleTask>();

	public NodeFacade(Properties properties, 
			IdlenessDetector iChecker,
			ResourcesInfoGatherer resIG,
			InstanceRepository iRep) throws Exception {
		
		this(properties);
		
		if (resIG != null) { 
			this.resourcesGatherer = resIG;
		}
		
		if (iRep != null) {
			this.instanceRepository = iRep;
		}
		
		this.idlenessChecker = iChecker;
	}
	
	public NodeFacade(Properties properties) {
		try {
			this.properties = properties;
			this.resourcesGatherer = new ResourcesInfoGatherer(properties);
			OurVirtUtils.setHypervisorEnvVars(properties);
		} catch (SigarException e) {
			LOGGER.error("Error while retrieving machine resources info.", e);
			throw new RuntimeException(e);
		}

		this.idlenessChecker = createIdlenessDetector(properties);
		
		String pollingIntervalStr = properties.getProperty(
				NodeProperties.SENSOR_POLLING_INTERVAL);
		
		long pollingInterval = DEF_POLLING_INTERVAL;
		if (pollingIntervalStr != null) {
			pollingInterval = Long.valueOf(pollingIntervalStr) * 1000;
		}
		
		this.sensor = new Sensor(pollingInterval, instanceRepository);
		sensorExecutor.scheduleWithFixedDelay(sensor, 0, pollingInterval, TimeUnit.MILLISECONDS);
	}
	
	private IdlenessDetector createIdlenessDetector(Properties properties) {
		IdlenessDetector idlenessDetector = null;
		String osType = resourcesGatherer.getOSType();
		if (osType.equals("Linux")) {
			idlenessDetector = new LinuxDevInputIdlenessDetector(properties);
		} else if (osType.equals("Win32")) {
			idlenessDetector = new Win32IdlenessDetector(properties);
		}
		if (idlenessDetector != null) {
			idlenessDetector.addListener(this);
			idlenessDetector.init();
		}
		return idlenessDetector;
	}

	private NodeFacade() {
		this(loadProperties());
	}

	private static Properties loadProperties() {
		try {
			Properties properties = new Properties();
			properties.load(NodeFacade.class.getClassLoader().getResourceAsStream(
					"../conf/euca.conf"));
			return properties;
		} catch (IOException e) {
			LOGGER.error("Error while loading properties file.", e);
			throw new RuntimeException(e);
		}
	}

	public static NodeFacade getInstance() {
		if (instance == null) {
			instance = new NodeFacade();
		}
		return instance;
	}
	
	
	private void checkNodeControllerAvailable() {
		if (!idlenessChecker.isIdle()) {
			throw new IllegalStateException("The node controller is not available.");
		}
	}

	private InstanceType[] getRunningInstances() {
		return instanceRepository.getInstances().toArray(new InstanceType[]{});
	}

	public NcDescribeResourceResponse describeResource(
			NcDescribeResource ncDescribeResource) {
		
		LOGGER.info("Describing Node Controller resources.");

		checkNodeControllerAvailable();

		NcDescribeResourceResponse response = new NcDescribeResourceResponse();
		NcDescribeResourceResponseType rType = new NcDescribeResourceResponseType();
		NcDescribeResourceType resourceRequest = ncDescribeResource.getNcDescribeResource();

		Resources available;
		
		try {
			available = resourcesGatherer.describeAvailable(instanceRepository);
		} catch (Exception e) {
			LOGGER.error("Error while retrieving machine resources info.", e);
			throw new RuntimeException(e);
		}

		//Set standard output fields
		rType.set_return(true);
		rType.setCorrelationId(resourceRequest.getCorrelationId());
		rType.setUserId(resourceRequest.getUserId());
		
		//Set operation-specific output fields
		rType.setMemorySizeMax(resourcesGatherer.getTotalMem());
		rType.setDiskSizeMax(resourcesGatherer.getTotalDiskSpace());
		rType.setNumberOfCoresMax(resourcesGatherer.getTotalNumCores());

		rType.setMemorySizeAvailable(available.getMem());
		rType.setDiskSizeAvailable(available.getDisk());
		rType.setNumberOfCoresAvailable(available.getCores());
		
		rType.setIqn(ResourcesInfoGatherer.ISCSI_IQN);
		rType.setNodeStatus(ResourcesInfoGatherer.NODE_STATUS_OK);
		rType.setPublicSubnets(ResourcesInfoGatherer.PUBLIC_SUBNETS);

		response.setNcDescribeResourceResponse(rType);
		return response;
	}


	public NcDescribeInstancesResponse describeInstances(
			NcDescribeInstances ncDescribeInstances) {
		
		LOGGER.info("Describing Node Controller instances.");

		checkNodeControllerAvailable();

		NcDescribeInstancesResponse response = new NcDescribeInstancesResponse();
		NcDescribeInstancesType describeInstanceRequest = ncDescribeInstances.getNcDescribeInstances();
		NcDescribeInstancesResponseType iResponseType = new NcDescribeInstancesResponseType();

		//Set standard output fields
		iResponseType.set_return(true);
		iResponseType.setCorrelationId(describeInstanceRequest.getCorrelationId());
		iResponseType.setUserId(describeInstanceRequest.getUserId());
		
		//Set operation-specific output fields
		iResponseType.setInstances(getRunningInstances());
		
		response.setNcDescribeInstancesResponse(iResponseType);
		return response;
	}

	private void terminateInstances() {
		for (InstanceType instance : instanceRepository.getInstances()) {
			terminateInstance(instance.getInstanceId());
		}
	}
	
	private void terminateInstance(String instanceId) {
		terminateInstance(instanceId, true);
	}

	private void terminateInstance(String instanceId, boolean changeToTeardown) {
		try {
			OurVirtUtils.terminateInstance(instanceId, properties);
			//			instanceRepository.removeInstance(instanceId);
			disconnectVolumes(instanceId);
			InstanceType instance = instanceRepository.getInstance(instanceId);
			if (changeToTeardown) {
				instance.setStateName(InstanceRepository.TEARDOWN_STATE);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void disconnectVolumes(String instanceId) {
		InstanceType instance = instanceRepository.getInstance(instanceId);
		VolumeType[] volumes = instance.getVolumes();
		if (volumes == null) {
			return;
		}
		for (VolumeType volume : volumes) {
			try {
				String remoteDev = volume.getRemoteDev();
				ISCSIUtils.logout(VolumeData.parse(remoteDev).getStore(), properties);
			} catch (Exception e) {
				LOGGER.warn("Error while disconnecting ISCSI target.", e);
			}
		}
	}

	public NcTerminateInstanceResponse terminateInstance(
			NcTerminateInstance ncTerminateInstance) {
		
		checkNodeControllerAvailable();

		NcTerminateInstanceResponse terminateInstanceResponse = new NcTerminateInstanceResponse();
		NcTerminateInstanceResponseType terminateInstance = new NcTerminateInstanceResponseType();
		NcTerminateInstanceType terminateRequest = ncTerminateInstance.getNcTerminateInstance();
		
		LOGGER.info("Terminating Instance [" + terminateRequest.getInstanceId() + "].");
		
		checkInstanceExists(terminateRequest.getInstanceId());
		
		terminateInstance(terminateRequest.getInstanceId());
		
		//Set standard output fields
		terminateInstance.set_return(true);
		terminateInstance.setCorrelationId(terminateRequest.getCorrelationId());
		terminateInstance.setUserId(terminateRequest.getUserId());
		
		//Set operation-specific output fields
		terminateInstance.setInstanceId(terminateRequest.getInstanceId());
		
		terminateInstance.setShutdownState(SUCCESS_STATE);
		terminateInstance.setPreviousState(SUCCESS_STATE);

		terminateInstanceResponse.setNcTerminateInstanceResponse(terminateInstance);
		return terminateInstanceResponse;
	}

	private void checkInstanceExists(String instanceId) {
		if (instanceRepository.getInstance(instanceId) == null) {
			throw new IllegalArgumentException("Instance " + instanceId + " does not exist.");
		}
	}

	private static String getXMLString(String str) {
		return str == null ? "" : str;
	}

	public NcRunInstanceResponse runInstance(NcRunInstance ncRunInstance) {

		checkNodeControllerAvailable();

		NcRunInstanceResponse response = new NcRunInstanceResponse();
		NcRunInstanceResponseType runInstanceResponse =  new NcRunInstanceResponseType();
		InstanceType instance = new InstanceType();
		NcRunInstanceType instanceRequest = ncRunInstance.getNcRunInstance();
		
		LOGGER.info("Running instance [" + instanceRequest.getInstanceId() + "] of type " + 
				instanceRequest.getInstanceType().getName()  + " with image " + "[" + 
				instanceRequest.getImageId() + "].");

		//Build instance object		
		instance.setUuid(instanceRequest.getUuid());
		instance.setInstanceId(instanceRequest.getInstanceId());
		instance.setReservationId(instanceRequest.getReservationId());
		instance.setUserId(instanceRequest.getUserId());
		instance.setOwnerId(instanceRequest.getOwnerId());
		instance.setAccountId(instanceRequest.getAccountId());
		instance.setKeyName(instanceRequest.getKeyName());
		instance.setInstanceType(instanceRequest.getInstanceType());
		instance.setNetParams(instanceRequest.getNetParams());
		instance.setUserData(instanceRequest.getUserData());
		instance.setLaunchIndex(instanceRequest.getLaunchIndex());
		instance.setPlatform(instanceRequest.getPlatform());
		instance.setGroupNames(instanceRequest.getGroupNames());
		instance.setExpiryTime(instanceRequest.getExpiryTime());

		instance.setImageId(getXMLString(instanceRequest.getImageId()));
		instance.setKernelId(getXMLString(instanceRequest.getKernelId()));
		instance.setRamdiskId(getXMLString(instanceRequest.getRamdiskId()));
		
		//TODO
		instance.setStateName(InstanceRepository.PENDING_STATE);
		instance.setBundleTaskStateName("none");
		instance.setCreateImageStateName("");
		instance.setLaunchTime(Calendar.getInstance());
		instance.setBlkbytes(0);
		instance.setNetbytes(0);

		//Set standard output fields
		runInstanceResponse.set_return(true);
		runInstanceResponse.setCorrelationId(instanceRequest.getCorrelationId());
		runInstanceResponse.setUserId(instanceRequest.getUserId());

		//Set operation-specific output fields
		runInstanceResponse.setInstance(instance);
		
		instanceRepository.addInstance(instance);
		response.setNcRunInstanceResponse(runInstanceResponse);

		threadPool.execute(createStartupRunnable(instanceRequest));
		
		return response;
	}
	//TODO To check if the VM was started: ping or telnet insted of doing SSH 
	private Runnable createStartupRunnable(final NcRunInstanceType instanceRequest) {
		return new Runnable() {
			public void run() {
				LOGGER.info("Lauching instance [" + 
						instanceRequest.getInstanceId() + "] startup thread.");
				
				checkInstanceExists(instanceRequest.getInstanceId());
				
				InstanceType instance = instanceRepository.getInstance(
						instanceRequest.getInstanceId());
				VBR vbr = VBRUtils.syncBootRecords(instanceRequest, properties);
				
				try {
					OurVirtUtils.runInstance(instanceRequest, vbr, properties);
				} catch (Exception e) {
					LOGGER.error("Failure in startup thread for instance [" + 
							instanceRequest.getInstanceId() + "].", e);
					
					try {
						OurVirtUtils.terminateInstance(
								instanceRequest.getInstanceId(), properties);
					} catch (Exception e1) {
						LOGGER.error("Failure to terminate instance[" + 
								instanceRequest.getInstanceId() + "].", e1);
					}
					
					instance.setStateName(InstanceRepository.TEARDOWN_STATE);
					throw new RuntimeException(e);
				}
				
				instance.setStateName(InstanceRepository.EXTANT_STATE);
				NetConfigType params = NetUtils.getParams(instanceRequest, 
						properties);
				instance.setNetParams(params);
			}
		};
	}

	public NcAssignAddressResponse assignAddress(NcAssignAddress ncAssignAddress) {

		checkNodeControllerAvailable();

		NcAssignAddressResponseType assignAddressResponse = new NcAssignAddressResponseType();
		NcAssignAddressResponse response = new NcAssignAddressResponse();
		NcAssignAddressType assignRequest = ncAssignAddress.getNcAssignAddress();
				
		LOGGER.info("Assigning address for instance [" + assignRequest.getInstanceId() + "].");
		
		checkInstanceExists(assignRequest.getInstanceId());
		
		//Set standard output fields
		assignAddressResponse.set_return(true);
		assignAddressResponse.setCorrelationId(assignRequest.getCorrelationId());
		assignAddressResponse.setUserId(assignRequest.getUserId());
		
		//Set operation-specific output fields
		assignAddressResponse.setStatusMessage(assignRequest.getStatusMessage());

		try {
			OurVirtUtils.assignAddress(assignRequest.getInstanceId(), 
					assignRequest.getPublicIp());
		} catch (Exception e) {
			LOGGER.error("Failure while assinging address to instance [" + 
					assignRequest.getInstanceId() + "].", e);
			throw new RuntimeException(e);
		}

		response.setNcAssignAddressResponse(assignAddressResponse);
		return response;
	}

	public NcStartNetworkResponse startNetwork(NcStartNetwork ncStartNetwork) {

		checkNodeControllerAvailable();

		//TODO(patricia) If we're dealing with MANAGED mode, start a bridge 
		NcStartNetworkResponseType startNetworkResponse = new NcStartNetworkResponseType();
		NcStartNetworkResponse response = new NcStartNetworkResponse();
		NcStartNetworkType startNetRequest =  ncStartNetwork.getNcStartNetwork();

		LOGGER.info("Starting machine network.");
		
		startNetworkResponse.set_return(true);
		startNetworkResponse.setUserId(startNetRequest.getUserId());
		startNetworkResponse.setCorrelationId(startNetRequest.getCorrelationId());
		
		startNetworkResponse.setStatusMessage("0");
		startNetworkResponse.setNetworkStatus("SUCCESS");
		//TODO
		response.setNcStartNetworkResponse(startNetworkResponse);
		return response;
	}

	@Override
	public void changed(boolean isIdle) {
		if (!isIdle) {
			terminateInstances();
		}
	}

	public NcRebootInstanceResponse rebootInstance(
			NcRebootInstance ncRebootInstance) {
		
		checkNodeControllerAvailable();

		NcRebootInstanceResponse rebootInstanceResponse = new NcRebootInstanceResponse();
		NcRebootInstanceResponseType rebootInstance = new NcRebootInstanceResponseType();
		NcRebootInstanceType rebootRequest = ncRebootInstance.getNcRebootInstance();
		
		LOGGER.info("Rebooting Instance [" + rebootRequest.getInstanceId() + "].");
		
		checkInstanceExists(rebootRequest.getInstanceId());
		
		//Set standard output fields
		rebootInstance.set_return(true); 
		rebootInstance.setUserId(rebootRequest.getUserId());
		rebootInstance.setCorrelationId(rebootRequest.getCorrelationId());

		//Set operation-specific output fields
		rebootInstance.setStatus(true);
		
		rebootInstanceResponse.setNcRebootInstanceResponse(rebootInstance);

		threadPool.execute(createRebootRunnable(rebootRequest));
		
		return rebootInstanceResponse;
	}

	private Runnable createRebootRunnable(final NcRebootInstanceType rebootRequest) {
		return new Runnable() {
			public void run() {
				try {
					InstanceType instance = instanceRepository.
							getInstance(rebootRequest.getInstanceId());
					OurVirtUtils.rebootInstance(instance.getInstanceId(), 
							instance.getImageId(), properties);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	public NcBundleInstanceResponse bundleInstance(
			NcBundleInstance ncBundleInstance) {
		
		checkNodeControllerAvailable();
		
		NcBundleInstanceType bundleRequest = ncBundleInstance.getNcBundleInstance();
		String instanceId = bundleRequest.getInstanceId();
		
		LOGGER.info("Bundling Instance [" + instanceId + "].");
		
		terminateInstance(instanceId, false);
		final BundleTask bt = new BundleTask();
		bt.setInstanceId(instanceId);
		bt.setState(BundleTaskState.NONE);
		bt.setBucketName(bundleRequest.getBucketName());
		bt.setFilePrefix(bundleRequest.getFilePrefix());
		bt.setManifest(bt.getBucketName() + "/" + bt.getFilePrefix() + ".manifest.xml");
		bt.setWalrusURL(bundleRequest.getWalrusURL());
		bt.setUserPublicKey(bundleRequest.getUserPublicKey());
		bt.setS3Policy(bundleRequest.getS3Policy());
		bt.setS3PolicySig(bundleRequest.getS3PolicySig());
		
		bundleTasks.put(instanceId, bt);
		setBundlingState(bt, BundleTaskState.NONE);
		
		startBundleThread(bt);

		NcBundleInstanceResponse bundleInstanceResponse = new NcBundleInstanceResponse();
		NcBundleInstanceResponseType bundleInstance = new NcBundleInstanceResponseType();
		bundleInstance.setUserId(bundleRequest.getUserId());
		bundleInstance.setCorrelationId(bundleRequest.getCorrelationId());
		bundleInstance.set_return(true);
		bundleInstanceResponse.setNcBundleInstanceResponse(bundleInstance);
		
		return bundleInstanceResponse;
	}

	private void startBundleThread(final BundleTask bt) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					setBundlingState(bt, BundleTaskState.BUNDLING);
					bt.setBundleBucketExists(WalrusUtils.checkBucket(bt, properties));
					
					if (bt.getState().equals(BundleTaskState.BUNDLING)) {
						doBundle(bt);
						setBundlingState(bt, BundleTaskState.SUCCEEDED);
					}
				} catch (Exception e) {
					if (bt.getState().equals(BundleTaskState.BUNDLING)) {
						setBundlingState(bt, BundleTaskState.FAILED);
						LOGGER.error("Error while trying to bundle instance [" 
								+ bt.getInstanceId() + "].", e);
						try {
							WalrusUtils.deleteBundle(bt, properties);
						} catch (Exception e1) {
							// Best effort
						}
					}
				} finally {
					moveToTeardown(bt.getInstanceId());
				}
			}
		}).start();
	}
	
	private void moveToTeardown(String instanceId) {
		InstanceType instance = instanceRepository.getInstance(instanceId);
		instance.setStateName(InstanceRepository.TEARDOWN_STATE);
	}

	private void setBundlingState(BundleTask bt, BundleTaskState bundleTaskState) {
		if (bt.getState().equals(BundleTaskState.CANCELLED) || 
				bt.getState().equals(BundleTaskState.FAILED) || 
				bt.getState().equals(BundleTaskState.SUCCEEDED)) {
			return;
		}
		final InstanceType instance = instanceRepository.getInstance(bt.getInstanceId());
		instance.setBundleTaskStateName(bundleTaskState.getName());
		bt.setState(bundleTaskState);
	}

	private void doBundle(BundleTask bt) throws Exception {
		
		String cloneLocation = OurVirtUtils.getCloneLocation(bt.getInstanceId(), properties);
		File imageFile = new File(cloneLocation);
		File cloneFile = new File(imageFile.getParentFile(), bt.getFilePrefix());
		OurVirtUtils.clone(imageFile.getAbsolutePath(), cloneFile.getAbsolutePath());
		WalrusUtils.bundleUpload(bt, cloneFile.getAbsolutePath(), properties);
	}

	public NcPowerDownResponse powerDown(NcPowerDown ncPowerDown) {
		
		checkNodeControllerAvailable();
		
		NcPowerDownResponse powerDownResponse = new NcPowerDownResponse();
		NcPowerDownResponseType powerDown = new NcPowerDownResponseType();
		NcPowerDownType powerDownRequest = ncPowerDown.getNcPowerDown();
		
		LOGGER.info("Powering down Node Controller.");
		
		ProcessBuilder powerDownPB = null;
		
		if (resourcesGatherer.getOSType().equals("Linux")) {
			powerDownPB = new ProcessBuilder("sudo", "/usr/sbin/powernap-now");
		} else if (resourcesGatherer.getOSType().equals("Win32")) {
			powerDownPB = new ProcessBuilder("shutdown /h");
		}
		
		if (powerDownPB != null) {
			try {
				powerDownPB.start();
			} catch (Exception e) {
				//Best-effort approach: Does not check for command successful run 
			}
			powerDown.setStatusMessage(SUCCESS_STATE);
		} else {
			LOGGER.warn("Power Down operation cannot be executed " +
					"because OS Type is not Linux");
			powerDown.setStatusMessage(UNSUCCESS_STATE);
		}
		
		//Set standard output fields
		powerDown.set_return(true);
		powerDown.setUserId(powerDownRequest.getUserId());
		powerDown.setCorrelationId(powerDownRequest.getCorrelationId());
		
		powerDownResponse.setNcPowerDownResponse(powerDown);
		
		return powerDownResponse;
	}

	public NcDescribeSensorsResponse describeSensors(
			NcDescribeSensors ncDescribeSensors) {
		
		checkNodeControllerAvailable();
		
		NcDescribeSensorsResponse describeSensorsResponse = new NcDescribeSensorsResponse();
		NcDescribeSensorsResponseType describeSensors = new NcDescribeSensorsResponseType();
		NcDescribeSensorsType describeSensorsRequest = ncDescribeSensors.getNcDescribeSensors();
		
		for (String instanceId : describeSensorsRequest.getInstanceIds()) {
			checkInstanceExists(instanceId);
		}
		
		if (!isArrayEmpty(describeSensorsRequest.getSensorIds())) {
			throw new IllegalArgumentException("No support for sensorIds[]");
		}
		
		describeSensors.set_return(true);
		describeSensors.setUserId(describeSensorsRequest.getUserId());
		describeSensors.setCorrelationId(describeSensorsRequest.getCorrelationId());
		
		SensorCache cache = sensor.getCache();
		List<SensorResource> resources = cache.getSensorResources();
		SensorsResourceType[] sensorResources = new SensorsResourceType[resources.size()];
		for (int i = 0; i < sensorResources.length; i++) {
			sensorResources[i] = resources.get(i);
		}
		describeSensors.setSensorsResources(sensorResources);
		
		describeSensorsResponse.setNcDescribeSensorsResponse(describeSensors);
		
		return describeSensorsResponse;
	}
	
	private boolean isArrayEmpty(String[] array) {
		for (String string : array) {
			if (string != null) {
				return false;
			}
		}
		
		return true;
	}

	public NcAttachVolumeResponse attachVolume(NcAttachVolume ncAttachVolume) {
		
		checkNodeControllerAvailable();
		
		NcAttachVolumeType attachVolumeRequest = ncAttachVolume.getNcAttachVolume();
		String instanceId = attachVolumeRequest.getInstanceId();
		String volumeId = attachVolumeRequest.getVolumeId();
		String attachmentToken = attachVolumeRequest.getRemoteDev();
		
		LOGGER.info("Attaching volume [" + volumeId + "] to instance [" + 
				attachVolumeRequest.getInstanceId()  + "].");
		
		VolumeType volume = new VolumeType();
		volume.setVolumeId(volumeId);
		volume.setLocalDev(attachVolumeRequest.getLocalDev());
		volume.setRemoteDev(attachVolumeRequest.getRemoteDev());
		volume.setState(VolumeState.ATTACHING.getName());
		InstanceType instance = instanceRepository.getInstance(instanceId);
		instance.addVolumes(volume);
		try {
			String localDevicePath = VolumeUtils.connectEBSVolume(instanceId, 
					attachmentToken, properties);
			volume.setState(VolumeState.ATTACHED.getName());
			if (localDevicePath != null) {
				volume.setLocalDev(localDevicePath.split("/")[2]);
			}
		} catch (Exception e) {
			volume.setState(VolumeState.ATTACHING_FAILED.getName());
			throw new RuntimeException(e);
		}
		
		NcAttachVolumeResponse response = new NcAttachVolumeResponse();
		NcAttachVolumeResponseType attachVolumeResponse = new NcAttachVolumeResponseType();
		attachVolumeResponse.set_return(true);
		attachVolumeResponse.setCorrelationId(attachVolumeRequest.getCorrelationId());
		attachVolumeResponse.setUserId(attachVolumeRequest.getUserId());
		response.setNcAttachVolumeResponse(attachVolumeResponse);
		
		return response;
	}

	public NcDetachVolumeResponse detachVolume(NcDetachVolume ncDetachVolume) {
		
		checkNodeControllerAvailable();
		
		NcDetachVolumeType detachVolumeRequest = ncDetachVolume.getNcDetachVolume();
		String instanceId = detachVolumeRequest.getInstanceId();
		String attachmentToken = detachVolumeRequest.getRemoteDev();
		String volumeId = detachVolumeRequest.getVolumeId();
		
		LOGGER.info("Detaching volume [" + volumeId + "] from instance [" + 
				instanceId  + "].");
		
		InstanceType instance = instanceRepository.getInstance(instanceId);
		VolumeType volume = getVolume(instance, volumeId);
		volume.setState(VolumeState.DETACHING.getName());
		try {
			VolumeUtils.disconnectEBSVolume(instanceId, 
					attachmentToken, properties);
			volume.setState(VolumeState.DETACHED.getName());
		} catch (Exception e) {
			volume.setState(VolumeState.DETACHING_FAILED.getName());
			throw new RuntimeException(e);
		}
		
		NcDetachVolumeResponse response = new NcDetachVolumeResponse();
		NcDetachVolumeResponseType detachVolumeResponse = new NcDetachVolumeResponseType();
		detachVolumeResponse.set_return(true);
		detachVolumeResponse.setCorrelationId(detachVolumeRequest.getCorrelationId());
		detachVolumeResponse.setUserId(detachVolumeRequest.getUserId());
		response.setNcDetachVolumeResponse(detachVolumeResponse);
		
		return response;
	}

	private VolumeType getVolume(InstanceType instance, String volumeId) {
		for (VolumeType volume : instance.getVolumes()) {
			if (volume.getVolumeId().equals(volumeId)) {
				return volume;
			}
		}
		return null;
	}

	public NcGetConsoleOutputResponse getConsoleOutput(
			NcGetConsoleOutput ncGetConsoleOutput) {
		
		checkNodeControllerAvailable();
		
		NcGetConsoleOutputType getConsoleOutputResquest = 
				ncGetConsoleOutput.getNcGetConsoleOutput();
		String instanceId = getConsoleOutputResquest.getInstanceId();
		
		LOGGER.info("Getting console output from instance [" + 
				instanceId  + "].");
		
		String encodedConsoleOutput = null;
		try {
			String consoleOutput = OurVirtUtils.getConsoleOutput(instanceId);
			encodedConsoleOutput = new String(Base64.encodeBase64(consoleOutput.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error when trying to get console output", e);
		}
		
		NcGetConsoleOutputResponse response = new NcGetConsoleOutputResponse();
		NcGetConsoleOutputResponseType getConsoleOutputResponse = 
				new NcGetConsoleOutputResponseType();
		getConsoleOutputResponse.set_return(true);
		getConsoleOutputResponse.setCorrelationId(
				getConsoleOutputResquest.getCorrelationId());
		getConsoleOutputResponse.setUserId(getConsoleOutputResquest.getUserId());
		getConsoleOutputResponse.setConsoleOutput(encodedConsoleOutput);
		response.setNcGetConsoleOutputResponse(getConsoleOutputResponse);
		
		return response;
	}

	public NcDescribeBundleTasksResponse describeBundleTasks(
			NcDescribeBundleTasks ncDescribeBundleTasks) {
		
		checkNodeControllerAvailable();
		
		LOGGER.info("Describing bundle tasks.");
		
		NcDescribeBundleTasksType request = ncDescribeBundleTasks.getNcDescribeBundleTasks();
		
		List<BundleTaskType> bundleTasksResponse = new LinkedList<BundleTaskType>();
		for (BundleTask bundleTask : bundleTasks.values()) {
			BundleTaskType btt = new BundleTaskType();
			btt.setInstanceId(bundleTask.getInstanceId());
			btt.setState(bundleTask.getState().getName());
			btt.setManifest(bundleTask.getManifest());
			bundleTasksResponse.add(btt);
		}
		
		NcDescribeBundleTasksResponse response = new NcDescribeBundleTasksResponse();
		NcDescribeBundleTasksResponseType describeBundleTasksResponse = 
				new NcDescribeBundleTasksResponseType();
		describeBundleTasksResponse.set_return(true);
		describeBundleTasksResponse.setCorrelationId(request.getCorrelationId());
		describeBundleTasksResponse.setUserId(request.getUserId());
		describeBundleTasksResponse.setBundleTasks(
				bundleTasksResponse.toArray(new BundleTaskType[]{}));
		response.setNcDescribeBundleTasksResponse(describeBundleTasksResponse);
		
		return response;
	}

	public NcCancelBundleTaskResponse cancelBundleTask(
			NcCancelBundleTask ncCancelBundleTask) {
		
		checkNodeControllerAvailable();
		
		NcCancelBundleTaskType request = ncCancelBundleTask.getNcCancelBundleTask();
		String instanceId = request.getInstanceId();
		
		LOGGER.info("Cancelling bundle task for instance [" + instanceId + "].");
		
		final BundleTask bt = bundleTasks.get(instanceId);
		setBundlingState(bt, BundleTaskState.CANCELLED);
		Process process = bt.getCurrentProcess();
		if (process != null) {
			process.destroy();
			bt.setCurrentProcess(null);
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					WalrusUtils.deleteBundle(bt, properties);
				} catch (Exception e1) {
					LOGGER.error(e1.getMessage(), e1);
					// Best effort
				}
			}
		}).start();

		
		NcCancelBundleTaskResponseType response = new NcCancelBundleTaskResponseType();
		response.set_return(true);
		response.setCorrelationId(request.getCorrelationId());
		response.setUserId(request.getUserId());
		NcCancelBundleTaskResponse cancelBundleTaskResponse = new NcCancelBundleTaskResponse();
		cancelBundleTaskResponse.setNcCancelBundleTaskResponse(response);
		
		return cancelBundleTaskResponse;
	}
}

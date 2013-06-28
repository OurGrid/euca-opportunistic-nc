package org.ourgrid.node;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.hyperic.sigar.SigarException;
import org.ourgrid.node.idleness.IdlenessListener;
import org.ourgrid.node.idleness.LinuxXSessionIdlenessDetector;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.model.Resources;
import org.ourgrid.node.model.VBR;
import org.ourgrid.node.util.NetUtils;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.node.util.ResourcesInfoGatherer;
import org.ourgrid.node.util.VBRUtils;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcAssignAddress;
import edu.ucsb.eucalyptus.NcAssignAddressResponse;
import edu.ucsb.eucalyptus.NcAssignAddressResponseType;
import edu.ucsb.eucalyptus.NcAssignAddressType;
import edu.ucsb.eucalyptus.NcBundleInstance;
import edu.ucsb.eucalyptus.NcBundleInstanceResponse;
import edu.ucsb.eucalyptus.NcBundleInstanceResponseType;
import edu.ucsb.eucalyptus.NcBundleInstanceType;
import edu.ucsb.eucalyptus.NcDescribeInstances;
import edu.ucsb.eucalyptus.NcDescribeInstancesResponse;
import edu.ucsb.eucalyptus.NcDescribeInstancesResponseType;
import edu.ucsb.eucalyptus.NcDescribeInstancesType;
import edu.ucsb.eucalyptus.NcDescribeResource;
import edu.ucsb.eucalyptus.NcDescribeResourceResponse;
import edu.ucsb.eucalyptus.NcDescribeResourceResponseType;
import edu.ucsb.eucalyptus.NcDescribeResourceType;
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

public class NodeFacade implements IdlenessListener {

	private final static Logger LOGGER = Logger.getLogger(NodeFacade.class);
	private static NodeFacade instance = null;
	
	private InstanceRepository instanceRepository = new InstanceRepository();
	private ResourcesInfoGatherer resourceUtils;
	private Properties properties;
	private LinuxXSessionIdlenessDetector idlenessDetector;
	private Executor threadPool = Executors.newFixedThreadPool(10);

	public NodeFacade() {
		try {
			this.properties = new Properties();
			this.properties.load(getClass().getClassLoader().getResourceAsStream(
					"../conf/euca.conf"));
			this.resourceUtils = new ResourcesInfoGatherer(properties);
//			OurVirtUtils.setVBoxHome(properties);
		} catch (IOException e) {
			LOGGER.error("Error while loading properties file.", e);
			throw new RuntimeException(e);
		} catch (SigarException e) {
			LOGGER.error("Error while retrieving machine resources info.", e);
			throw new RuntimeException(e);
		}

		this.idlenessDetector = new LinuxXSessionIdlenessDetector(properties);
		this.idlenessDetector.addListener(this);
		this.idlenessDetector.init();
	}

	public static NodeFacade getInstance() {
		if (instance == null) {
			instance = new NodeFacade();
		}
		return instance;
	}

	private void checkNodeControllerAvailable() {
		if (!idlenessDetector.isIdle()) {
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

		rType.setMemorySizeMax(resourceUtils.getTotalMem());
		rType.setDiskSizeMax(resourceUtils.getTotalDiskSpace());
		rType.setNumberOfCoresMax(resourceUtils.getTotalNumCores());
		
		Resources available;
		
		try {
			available = resourceUtils.describeAvailable(instanceRepository);
		} catch (Exception e) {
			LOGGER.error("Error while retrieving machine resources info.", e);
			throw new RuntimeException(e);
		}

		rType.setMemorySizeAvailable(available.getMem());
		rType.setDiskSizeAvailable(available.getDisk());
		rType.setNumberOfCoresAvailable(available.getCores());
		rType.setUserId(resourceRequest.getUserId());
		rType.set_return(true);

		//TODO
		rType.setIqn("iqn.1993-08.org.debian:01:247ad1c41511");
		rType.setNodeStatus("OK");
		rType.setPublicSubnets("none");

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
		iResponseType.setInstances(getRunningInstances());
		iResponseType.setUserId(describeInstanceRequest.getUserId());
		iResponseType.set_return(true);
		response.setNcDescribeInstancesResponse(iResponseType);
		return response;
	}

	private void terminateInstances() {
		for (InstanceType instance : instanceRepository.getInstances()) {
			terminateInstance(instance.getInstanceId());
		}
	}

	private void terminateInstance(String instanceId) {
		try {
			OurVirtUtils.terminateInstance(instanceId, properties);
			//			instanceRepository.removeInstance(instanceId);
			InstanceType instance = instanceRepository.getInstance(instanceId);
			instance.setStateName(InstanceRepository.TEARDOWN_STATE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public NcTerminateInstanceResponse terminateInstance(
			NcTerminateInstance ncTerminateInstance) {
		
		checkNodeControllerAvailable();

		NcTerminateInstanceResponse terminateInstanceResponse = new NcTerminateInstanceResponse();
		NcTerminateInstanceResponseType terminateInstance = new NcTerminateInstanceResponseType();
		NcTerminateInstanceType terminateRequest = ncTerminateInstance.getNcTerminateInstance();
		
		LOGGER.info("Terminating Instance [" + terminateRequest.getInstanceId() + "].");
		
		terminateInstance(terminateRequest.getInstanceId());
		
		terminateInstance.setUserId(terminateRequest.getUserId());
		terminateInstance.setInstanceId(terminateRequest.getInstanceId());
		//TODO
		terminateInstance.set_return(true); 
		terminateInstance.setShutdownState("14");
		terminateInstance.setPreviousState("14");

		terminateInstanceResponse .setNcTerminateInstanceResponse(terminateInstance);
		return terminateInstanceResponse;
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

		instance.setImageId(getXMLString(instanceRequest.getImageId()));
		instance.setKernelId(getXMLString(instanceRequest.getKernelId()));
		instance.setRamdiskId(getXMLString(instanceRequest.getRamdiskId()));

		instance.setUuid(instanceRequest.getUuid());
		instance.setReservationId(instanceRequest.getReservationId());
		instance.setInstanceId(instanceRequest.getInstanceId());
		instance.setUserId(instanceRequest.getUserId());
		instance.setOwnerId(instanceRequest.getOwnerId());
		instance.setAccountId(instanceRequest.getAccountId());
		instance.setKeyName(instanceRequest.getKeyName());
		instance.setInstanceType(instanceRequest.getInstanceType());
		instance.setGroupNames(instanceRequest.getGroupNames());
		instance.setLaunchIndex(instanceRequest.getLaunchIndex());
		instance.setPlatform(instanceRequest.getPlatform());
		instance.setNetParams(instanceRequest.getNetParams());

		//TODO
		instance.setStateName(InstanceRepository.PENDING_STATE);
		instance.setBundleTaskStateName("none");
		instance.setCreateImageStateName("");
		instance.setLaunchTime(Calendar.getInstance());
		instance.setBlkbytes(0);
		instance.setNetbytes(0);
		instance.setUserData("");

		runInstanceResponse.setInstance(instance);
		runInstanceResponse.set_return(true);
		runInstanceResponse.setUserId(instanceRequest.getUserId());

		instanceRepository.addInstance(instance);
		response.setNcRunInstanceResponse(runInstanceResponse);

		threadPool.execute(createStartupRunnable(instanceRequest));
		
		return response;
	}
	//TODO To check if the VM was started: ping or telnet insted of doing SSH 
	private Runnable createStartupRunnable(final NcRunInstanceType instanceRequest) {
		return new Runnable() {
			public void run() {
				LOGGER.info("Lauching instance [" + instanceRequest.getInstanceId() + "] startup thread.");
				//TODO Restrict indentation levels in method
				InstanceType instance = instanceRepository.getInstance(instanceRequest.getInstanceId());
				VBR vbr = VBRUtils.syncBootRecords(instanceRequest, properties);
				try {
					OurVirtUtils.runInstance(instanceRequest, vbr, properties);
				} catch (Exception e) {
					LOGGER.error("Failure in startup thread for instance [" + 
							instanceRequest.getInstanceId() + "].", e);
					try {
						OurVirtUtils.terminateInstance(instanceRequest.getInstanceId(), properties);
					} catch (Exception e1) {
						LOGGER.error("Failure to terminate instance[" + 
								instanceRequest.getInstanceId() + "].", e1);
					}
					instance.setStateName(InstanceRepository.TEARDOWN_STATE);
					throw new RuntimeException(e);
				}
				instance.setStateName(InstanceRepository.EXTANT_STATE);
				NetConfigType params = NetUtils.getParams(instanceRequest, properties);
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
				
		assignAddressResponse.setUserId(assignRequest.getUserId());
		assignAddressResponse.setStatusMessage(assignRequest.getStatusMessage());

		try {
			OurVirtUtils.assignAddress(assignRequest.getInstanceId(), 
					assignRequest.getPublicIp());
		} catch (Exception e) {
			LOGGER.error("Failure while assinging address to instance [" + 
					assignRequest.getInstanceId() + "].", e);
			throw new RuntimeException(e);
		}

		assignAddressResponse.set_return(true);
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
		
		startNetworkResponse.setUserId(startNetRequest.getUserId());
		startNetworkResponse.setStatusMessage(startNetRequest.getStatusMessage());
		//TODO
		startNetworkResponse.set_return(true);
		startNetworkResponse.setNetworkStatus("SUCCESS");
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
		
		rebootInstance.setUserId(rebootRequest.getUserId());
		rebootInstance.setStatus(true);
		//TODO
		rebootInstance.set_return(true); 
//		rebootInstance.setShutdownState("14");
//		rebootInstance.setPreviousState("14");
		
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
//					instance.setStateName(InstanceRepository.EXTANT_STATE);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	public NcBundleInstanceResponse bundleInstance(
			NcBundleInstance ncBundleInstance) {
		
		checkNodeControllerAvailable();

		NcBundleInstanceResponse bundleInstanceResponse = new NcBundleInstanceResponse();
		NcBundleInstanceResponseType bundleInstance = new NcBundleInstanceResponseType();
		NcBundleInstanceType bundleRequest = ncBundleInstance.getNcBundleInstance();
		
		LOGGER.info("Bundling Instance [" + bundleRequest.getInstanceId() + "].");
		
		
		
		bundleInstance.setUserId(bundleRequest.getUserId());
		//TODO
		bundleInstance.set_return(true);
		
		bundleInstanceResponse.setNcBundleInstanceResponse(bundleInstance);
		
		return bundleInstanceResponse;
	}
}

/**
 * EucalyptusNCMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package edu.ucsb.eucalyptus;

/**
 * EucalyptusNCMessageReceiverInOut message receiver
 */

public class EucalyptusNCMessageReceiverInOut extends
org.apache.axis2.receivers.AbstractInOutMessageReceiver {

	public void invokeBusinessLogic(
			org.apache.axis2.context.MessageContext msgContext,
			org.apache.axis2.context.MessageContext newMsgContext)
					throws org.apache.axis2.AxisFault {

		try {

			// get the implementation class for the Web Service
			Object obj = getTheImplementationObject(msgContext);

			EucalyptusNCSkeleton skel = (EucalyptusNCSkeleton) obj;
			// Out Envelop
			org.apache.axiom.soap.SOAPEnvelope envelope = null;
			// Find the axisOperation that has been set by the Dispatch phase.
			org.apache.axis2.description.AxisOperation op = msgContext
					.getOperationContext().getAxisOperation();
			if (op == null) {
				throw new org.apache.axis2.AxisFault(
						"Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
			}

			java.lang.String methodName;
			if ((op.getName() != null)
					&& ((methodName = org.apache.axis2.util.JavaUtils
					.xmlNameToJavaIdentifier(op.getName()
							.getLocalPart())) != null)) {

				if ("ncDescribeSensors".equals(methodName)) {
					edu.ucsb.eucalyptus.NcDescribeSensorsResponse ncDescribeSensorsResponse1 = null;
					edu.ucsb.eucalyptus.NcDescribeSensors wrappedParam = (edu.ucsb.eucalyptus.NcDescribeSensors) fromOM(
							msgContext.getEnvelope().getBody()
							.getFirstElement(),
							edu.ucsb.eucalyptus.NcDescribeSensors.class,
							getEnvelopeNamespaces(msgContext.getEnvelope()));
					ncDescribeSensorsResponse1 = skel
							.ncDescribeSensors(wrappedParam);
					envelope = toEnvelope(getSOAPFactory(msgContext),
							ncDescribeSensorsResponse1, false,
							new javax.xml.namespace.QName(
									"http://eucalyptus.ucsb.edu/",
									"ncDescribeSensors"));

				} else

					if ("ncDescribeBundleTasks".equals(methodName)) {

						edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse ncDescribeBundleTasksResponse3 = null;
						edu.ucsb.eucalyptus.NcDescribeBundleTasks wrappedParam = (edu.ucsb.eucalyptus.NcDescribeBundleTasks) fromOM(
								msgContext.getEnvelope().getBody()
								.getFirstElement(),
								edu.ucsb.eucalyptus.NcDescribeBundleTasks.class,
								getEnvelopeNamespaces(msgContext.getEnvelope()));

						ncDescribeBundleTasksResponse3 =

								skel.ncDescribeBundleTasks(wrappedParam);

						envelope = toEnvelope(getSOAPFactory(msgContext),
								ncDescribeBundleTasksResponse3, false,
								new javax.xml.namespace.QName(
										"http://eucalyptus.ucsb.edu/",
										"ncDescribeBundleTasks"));
					} else

						if ("ncRunInstance".equals(methodName)) {

							edu.ucsb.eucalyptus.NcRunInstanceResponse ncRunInstanceResponse5 = null;
							edu.ucsb.eucalyptus.NcRunInstance wrappedParam = (edu.ucsb.eucalyptus.NcRunInstance) fromOM(
									msgContext.getEnvelope().getBody()
									.getFirstElement(),
									edu.ucsb.eucalyptus.NcRunInstance.class,
									getEnvelopeNamespaces(msgContext.getEnvelope()));

							ncRunInstanceResponse5 =

									skel.ncRunInstance(wrappedParam);

							envelope = toEnvelope(getSOAPFactory(msgContext),
									ncRunInstanceResponse5, false,
									new javax.xml.namespace.QName(
											"http://eucalyptus.ucsb.edu/",
											"ncRunInstance"));
						} else

							if ("ncRebootInstance".equals(methodName)) {

								edu.ucsb.eucalyptus.NcRebootInstanceResponse ncRebootInstanceResponse7 = null;
								edu.ucsb.eucalyptus.NcRebootInstance wrappedParam = (edu.ucsb.eucalyptus.NcRebootInstance) fromOM(
										msgContext.getEnvelope().getBody()
										.getFirstElement(),
										edu.ucsb.eucalyptus.NcRebootInstance.class,
										getEnvelopeNamespaces(msgContext.getEnvelope()));

								ncRebootInstanceResponse7 =

										skel.ncRebootInstance(wrappedParam);

								envelope = toEnvelope(getSOAPFactory(msgContext),
										ncRebootInstanceResponse7, false,
										new javax.xml.namespace.QName(
												"http://eucalyptus.ucsb.edu/",
												"ncRebootInstance"));
							} else

								if ("ncGetConsoleOutput".equals(methodName)) {

									edu.ucsb.eucalyptus.NcGetConsoleOutputResponse ncGetConsoleOutputResponse9 = null;
									edu.ucsb.eucalyptus.NcGetConsoleOutput wrappedParam = (edu.ucsb.eucalyptus.NcGetConsoleOutput) fromOM(
											msgContext.getEnvelope().getBody()
											.getFirstElement(),
											edu.ucsb.eucalyptus.NcGetConsoleOutput.class,
											getEnvelopeNamespaces(msgContext.getEnvelope()));

									ncGetConsoleOutputResponse9 =

											skel.ncGetConsoleOutput(wrappedParam);

									envelope = toEnvelope(getSOAPFactory(msgContext),
											ncGetConsoleOutputResponse9, false,
											new javax.xml.namespace.QName(
													"http://eucalyptus.ucsb.edu/",
													"ncGetConsoleOutput"));
								} else

									if ("ncCreateImage".equals(methodName)) {

										edu.ucsb.eucalyptus.NcCreateImageResponse ncCreateImageResponse11 = null;
										edu.ucsb.eucalyptus.NcCreateImage wrappedParam = (edu.ucsb.eucalyptus.NcCreateImage) fromOM(
												msgContext.getEnvelope().getBody()
												.getFirstElement(),
												edu.ucsb.eucalyptus.NcCreateImage.class,
												getEnvelopeNamespaces(msgContext.getEnvelope()));

										ncCreateImageResponse11 =

												skel.ncCreateImage(wrappedParam);

										envelope = toEnvelope(getSOAPFactory(msgContext),
												ncCreateImageResponse11, false,
												new javax.xml.namespace.QName(
														"http://eucalyptus.ucsb.edu/",
														"ncCreateImage"));
									} else

										if ("ncDetachVolume".equals(methodName)) {

											edu.ucsb.eucalyptus.NcDetachVolumeResponse ncDetachVolumeResponse13 = null;
											edu.ucsb.eucalyptus.NcDetachVolume wrappedParam = (edu.ucsb.eucalyptus.NcDetachVolume) fromOM(
													msgContext.getEnvelope().getBody()
													.getFirstElement(),
													edu.ucsb.eucalyptus.NcDetachVolume.class,
													getEnvelopeNamespaces(msgContext.getEnvelope()));

											ncDetachVolumeResponse13 =

													skel.ncDetachVolume(wrappedParam);

											envelope = toEnvelope(getSOAPFactory(msgContext),
													ncDetachVolumeResponse13, false,
													new javax.xml.namespace.QName(
															"http://eucalyptus.ucsb.edu/",
															"ncDetachVolume"));
										} else

											if ("ncDescribeInstances".equals(methodName)) {

												edu.ucsb.eucalyptus.NcDescribeInstancesResponse ncDescribeInstancesResponse15 = null;
												edu.ucsb.eucalyptus.NcDescribeInstances wrappedParam = (edu.ucsb.eucalyptus.NcDescribeInstances) fromOM(
														msgContext.getEnvelope().getBody()
														.getFirstElement(),
														edu.ucsb.eucalyptus.NcDescribeInstances.class,
														getEnvelopeNamespaces(msgContext.getEnvelope()));

												ncDescribeInstancesResponse15 =

														skel.ncDescribeInstances(wrappedParam);

												envelope = toEnvelope(getSOAPFactory(msgContext),
														ncDescribeInstancesResponse15, false,
														new javax.xml.namespace.QName(
																"http://eucalyptus.ucsb.edu/",
																"ncDescribeInstances"));
											} else

												if ("ncAttachVolume".equals(methodName)) {

													edu.ucsb.eucalyptus.NcAttachVolumeResponse ncAttachVolumeResponse17 = null;
													edu.ucsb.eucalyptus.NcAttachVolume wrappedParam = (edu.ucsb.eucalyptus.NcAttachVolume) fromOM(
															msgContext.getEnvelope().getBody()
															.getFirstElement(),
															edu.ucsb.eucalyptus.NcAttachVolume.class,
															getEnvelopeNamespaces(msgContext.getEnvelope()));

													ncAttachVolumeResponse17 =

															skel.ncAttachVolume(wrappedParam);

													envelope = toEnvelope(getSOAPFactory(msgContext),
															ncAttachVolumeResponse17, false,
															new javax.xml.namespace.QName(
																	"http://eucalyptus.ucsb.edu/",
																	"ncAttachVolume"));
												} else

													if ("ncPowerDown".equals(methodName)) {

														edu.ucsb.eucalyptus.NcPowerDownResponse ncPowerDownResponse19 = null;
														edu.ucsb.eucalyptus.NcPowerDown wrappedParam = (edu.ucsb.eucalyptus.NcPowerDown) fromOM(
																msgContext.getEnvelope().getBody()
																.getFirstElement(),
																edu.ucsb.eucalyptus.NcPowerDown.class,
																getEnvelopeNamespaces(msgContext.getEnvelope()));

														ncPowerDownResponse19 =

																skel.ncPowerDown(wrappedParam);

														envelope = toEnvelope(getSOAPFactory(msgContext),
																ncPowerDownResponse19, false,
																new javax.xml.namespace.QName(
																		"http://eucalyptus.ucsb.edu/",
																		"ncPowerDown"));
													} else

														if ("ncDescribeResource".equals(methodName)) {

															edu.ucsb.eucalyptus.NcDescribeResourceResponse ncDescribeResourceResponse21 = null;
															edu.ucsb.eucalyptus.NcDescribeResource wrappedParam = (edu.ucsb.eucalyptus.NcDescribeResource) fromOM(
																	msgContext.getEnvelope().getBody()
																	.getFirstElement(),
																	edu.ucsb.eucalyptus.NcDescribeResource.class,
																	getEnvelopeNamespaces(msgContext.getEnvelope()));
															ncDescribeResourceResponse21 
															= skel.ncDescribeResource(wrappedParam);
															envelope = toEnvelope(getSOAPFactory(msgContext),
																	ncDescribeResourceResponse21, false,
																	new javax.xml.namespace.QName(
																			"http://eucalyptus.ucsb.edu/",
																			"ncDescribeResource"));
														} else

															if ("ncCancelBundleTask".equals(methodName)) {

																edu.ucsb.eucalyptus.NcCancelBundleTaskResponse ncCancelBundleTaskResponse23 = null;
																edu.ucsb.eucalyptus.NcCancelBundleTask wrappedParam = (edu.ucsb.eucalyptus.NcCancelBundleTask) fromOM(
																		msgContext.getEnvelope().getBody()
																		.getFirstElement(),
																		edu.ucsb.eucalyptus.NcCancelBundleTask.class,
																		getEnvelopeNamespaces(msgContext.getEnvelope()));

																ncCancelBundleTaskResponse23 =

																		skel.ncCancelBundleTask(wrappedParam);

																envelope = toEnvelope(getSOAPFactory(msgContext),
																		ncCancelBundleTaskResponse23, false,
																		new javax.xml.namespace.QName(
																				"http://eucalyptus.ucsb.edu/",
																				"ncCancelBundleTask"));
															} else

																if ("ncBundleRestartInstance".equals(methodName)) {

																	edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse ncBundleRestartInstanceResponse25 = null;
																	edu.ucsb.eucalyptus.NcBundleRestartInstance wrappedParam = (edu.ucsb.eucalyptus.NcBundleRestartInstance) fromOM(
																			msgContext.getEnvelope().getBody()
																			.getFirstElement(),
																			edu.ucsb.eucalyptus.NcBundleRestartInstance.class,
																			getEnvelopeNamespaces(msgContext.getEnvelope()));

																	ncBundleRestartInstanceResponse25 =

																			skel.ncBundleRestartInstance(wrappedParam);

																	envelope = toEnvelope(getSOAPFactory(msgContext),
																			ncBundleRestartInstanceResponse25, false,
																			new javax.xml.namespace.QName(
																					"http://eucalyptus.ucsb.edu/",
																					"ncBundleRestartInstance"));
																} else

																	if ("ncBundleInstance".equals(methodName)) {

																		edu.ucsb.eucalyptus.NcBundleInstanceResponse ncBundleInstanceResponse27 = null;
																		edu.ucsb.eucalyptus.NcBundleInstance wrappedParam = (edu.ucsb.eucalyptus.NcBundleInstance) fromOM(
																				msgContext.getEnvelope().getBody()
																				.getFirstElement(),
																				edu.ucsb.eucalyptus.NcBundleInstance.class,
																				getEnvelopeNamespaces(msgContext.getEnvelope()));

																		ncBundleInstanceResponse27 =

																				skel.ncBundleInstance(wrappedParam);

																		envelope = toEnvelope(getSOAPFactory(msgContext),
																				ncBundleInstanceResponse27, false,
																				new javax.xml.namespace.QName(
																						"http://eucalyptus.ucsb.edu/",
																						"ncBundleInstance"));
																	} else

																		if ("ncTerminateInstance".equals(methodName)) {

																			edu.ucsb.eucalyptus.NcTerminateInstanceResponse ncTerminateInstanceResponse29 = null;
																			edu.ucsb.eucalyptus.NcTerminateInstance wrappedParam = (edu.ucsb.eucalyptus.NcTerminateInstance) fromOM(
																					msgContext.getEnvelope().getBody()
																					.getFirstElement(),
																					edu.ucsb.eucalyptus.NcTerminateInstance.class,
																					getEnvelopeNamespaces(msgContext.getEnvelope()));

																			ncTerminateInstanceResponse29 =

																					skel.ncTerminateInstance(wrappedParam);

																			envelope = toEnvelope(getSOAPFactory(msgContext),
																					ncTerminateInstanceResponse29, false,
																					new javax.xml.namespace.QName(
																							"http://eucalyptus.ucsb.edu/",
																							"ncTerminateInstance"));
																		} else

																			if ("ncAssignAddress".equals(methodName)) {

																				edu.ucsb.eucalyptus.NcAssignAddressResponse ncAssignAddressResponse31 = null;
																				edu.ucsb.eucalyptus.NcAssignAddress wrappedParam = (edu.ucsb.eucalyptus.NcAssignAddress) fromOM(
																						msgContext.getEnvelope().getBody()
																						.getFirstElement(),
																						edu.ucsb.eucalyptus.NcAssignAddress.class,
																						getEnvelopeNamespaces(msgContext.getEnvelope()));
																				ncAssignAddressResponse31 = skel
																						.ncAssignAddress(wrappedParam);
																				envelope = toEnvelope(getSOAPFactory(msgContext),
																						ncAssignAddressResponse31, false,
																						new javax.xml.namespace.QName(
																								"http://eucalyptus.ucsb.edu/",
																								"ncAssignAddress"));
																			} else

																				if ("ncStartNetwork".equals(methodName)) {

																					edu.ucsb.eucalyptus.NcStartNetworkResponse ncStartNetworkResponse33 = null;
																					edu.ucsb.eucalyptus.NcStartNetwork wrappedParam = (edu.ucsb.eucalyptus.NcStartNetwork) fromOM(
																							msgContext.getEnvelope().getBody()
																							.getFirstElement(),
																							edu.ucsb.eucalyptus.NcStartNetwork.class,
																							getEnvelopeNamespaces(msgContext.getEnvelope()));
																					ncStartNetworkResponse33 = skel
																							.ncStartNetwork(wrappedParam);
																					envelope = toEnvelope(getSOAPFactory(msgContext),
																							ncStartNetworkResponse33, false,
																							new javax.xml.namespace.QName(
																									"http://eucalyptus.ucsb.edu/",
																									"ncStartNetwork"));

																				} else {
																					throw new java.lang.RuntimeException("method not found");
																				}

				newMsgContext.setEnvelope(envelope);
			}
		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	//
	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDescribeSensors param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDescribeSensors.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDescribeSensorsResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDescribeSensorsResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDescribeBundleTasks param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDescribeBundleTasks.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcRunInstance param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcRunInstance.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcRunInstanceResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcRunInstanceResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcRebootInstance param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcRebootInstance.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcRebootInstanceResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcRebootInstanceResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcGetConsoleOutput param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcGetConsoleOutput.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcGetConsoleOutputResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcGetConsoleOutputResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcCreateImage param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcCreateImage.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcCreateImageResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcCreateImageResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDetachVolume param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDetachVolume.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDetachVolumeResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDetachVolumeResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDescribeInstances param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDescribeInstances.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDescribeInstancesResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDescribeInstancesResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcAttachVolume param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcAttachVolume.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcAttachVolumeResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcAttachVolumeResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcPowerDown param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(edu.ucsb.eucalyptus.NcPowerDown.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcPowerDownResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcPowerDownResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDescribeResource param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDescribeResource.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcDescribeResourceResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcDescribeResourceResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcCancelBundleTask param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcCancelBundleTask.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcCancelBundleTaskResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcCancelBundleTaskResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcBundleRestartInstance param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcBundleRestartInstance.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
							.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcBundleInstance param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcBundleInstance.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcBundleInstanceResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcBundleInstanceResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcTerminateInstance param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcTerminateInstance.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcTerminateInstanceResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcTerminateInstanceResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcAssignAddress param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcAssignAddress.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcAssignAddressResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcAssignAddressResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcStartNetwork param, boolean optimizeContent)
					throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcStartNetwork.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			edu.ucsb.eucalyptus.NcStartNetworkResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					edu.ucsb.eucalyptus.NcStartNetworkResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcDescribeSensorsResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcDescribeSensorsResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcDescribeSensorsResponse wrapncDescribeSensors() {
		edu.ucsb.eucalyptus.NcDescribeSensorsResponse wrappedElement = new edu.ucsb.eucalyptus.NcDescribeSensorsResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse wrapncDescribeBundleTasks() {
		edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse wrappedElement = new edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcRunInstanceResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcRunInstanceResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcRunInstanceResponse wrapncRunInstance() {
		edu.ucsb.eucalyptus.NcRunInstanceResponse wrappedElement = new edu.ucsb.eucalyptus.NcRunInstanceResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcRebootInstanceResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcRebootInstanceResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcRebootInstanceResponse wrapncRebootInstance() {
		edu.ucsb.eucalyptus.NcRebootInstanceResponse wrappedElement = new edu.ucsb.eucalyptus.NcRebootInstanceResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcGetConsoleOutputResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcGetConsoleOutputResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcGetConsoleOutputResponse wrapncGetConsoleOutput() {
		edu.ucsb.eucalyptus.NcGetConsoleOutputResponse wrappedElement = new edu.ucsb.eucalyptus.NcGetConsoleOutputResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcCreateImageResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcCreateImageResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcCreateImageResponse wrapncCreateImage() {
		edu.ucsb.eucalyptus.NcCreateImageResponse wrappedElement = new edu.ucsb.eucalyptus.NcCreateImageResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcDetachVolumeResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcDetachVolumeResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcDetachVolumeResponse wrapncDetachVolume() {
		edu.ucsb.eucalyptus.NcDetachVolumeResponse wrappedElement = new edu.ucsb.eucalyptus.NcDetachVolumeResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcDescribeInstancesResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcDescribeInstancesResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcDescribeInstancesResponse wrapncDescribeInstances() {
		edu.ucsb.eucalyptus.NcDescribeInstancesResponse wrappedElement = new edu.ucsb.eucalyptus.NcDescribeInstancesResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcAttachVolumeResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcAttachVolumeResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcAttachVolumeResponse wrapncAttachVolume() {
		edu.ucsb.eucalyptus.NcAttachVolumeResponse wrappedElement = new edu.ucsb.eucalyptus.NcAttachVolumeResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcPowerDownResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcPowerDownResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcPowerDownResponse wrapncPowerDown() {
		edu.ucsb.eucalyptus.NcPowerDownResponse wrappedElement = new edu.ucsb.eucalyptus.NcPowerDownResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcDescribeResourceResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcDescribeResourceResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcDescribeResourceResponse wrapncDescribeResource() {
		edu.ucsb.eucalyptus.NcDescribeResourceResponse wrappedElement = new edu.ucsb.eucalyptus.NcDescribeResourceResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcCancelBundleTaskResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcCancelBundleTaskResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcCancelBundleTaskResponse wrapncCancelBundleTask() {
		edu.ucsb.eucalyptus.NcCancelBundleTaskResponse wrappedElement = new edu.ucsb.eucalyptus.NcCancelBundleTaskResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse wrapncBundleRestartInstance() {
		edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse wrappedElement = new edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcBundleInstanceResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcBundleInstanceResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcBundleInstanceResponse wrapncBundleInstance() {
		edu.ucsb.eucalyptus.NcBundleInstanceResponse wrappedElement = new edu.ucsb.eucalyptus.NcBundleInstanceResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcTerminateInstanceResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcTerminateInstanceResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcTerminateInstanceResponse wrapncTerminateInstance() {
		edu.ucsb.eucalyptus.NcTerminateInstanceResponse wrappedElement = new edu.ucsb.eucalyptus.NcTerminateInstanceResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcAssignAddressResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcAssignAddressResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcAssignAddressResponse wrapncAssignAddress() {
		edu.ucsb.eucalyptus.NcAssignAddressResponse wrappedElement = new edu.ucsb.eucalyptus.NcAssignAddressResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			edu.ucsb.eucalyptus.NcStartNetworkResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
					throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
			.getBody()
			.addChild(
					param.getOMElement(
							edu.ucsb.eucalyptus.NcStartNetworkResponse.MY_QNAME,
							factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private edu.ucsb.eucalyptus.NcStartNetworkResponse wrapncStartNetwork() {
		edu.ucsb.eucalyptus.NcStartNetworkResponse wrappedElement = new edu.ucsb.eucalyptus.NcStartNetworkResponse();
		return wrappedElement;
	}

	/**
	 * get the default envelope
	 */
	 private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			 org.apache.axiom.soap.SOAPFactory factory) {
		 return factory.getDefaultEnvelope();
	 }

	 private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
			 java.lang.Class type, java.util.Map extraNamespaces)
					 throws org.apache.axis2.AxisFault {

		 try {

			 if (edu.ucsb.eucalyptus.NcDescribeSensors.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcDescribeSensors.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcDescribeSensorsResponse.class
					 .equals(type)) {

				 return edu.ucsb.eucalyptus.NcDescribeSensorsResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcDescribeBundleTasks.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcDescribeBundleTasks.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse.class
					 .equals(type)) {

				 return edu.ucsb.eucalyptus.NcDescribeBundleTasksResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcRunInstance.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcRunInstance.Factory.parse(param
						 .getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcRunInstanceResponse.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcRunInstanceResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcRebootInstance.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcRebootInstance.Factory.parse(param
						 .getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcRebootInstanceResponse.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcRebootInstanceResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcGetConsoleOutput.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcGetConsoleOutput.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcGetConsoleOutputResponse.class
					 .equals(type)) {

				 return edu.ucsb.eucalyptus.NcGetConsoleOutputResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcCreateImage.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcCreateImage.Factory.parse(param
						 .getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcCreateImageResponse.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcCreateImageResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcDetachVolume.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcDetachVolume.Factory.parse(param
						 .getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcDetachVolumeResponse.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcDetachVolumeResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcDescribeInstances.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcDescribeInstances.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcDescribeInstancesResponse.class
					 .equals(type)) {

				 return edu.ucsb.eucalyptus.NcDescribeInstancesResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcAttachVolume.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcAttachVolume.Factory.parse(param
						 .getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcAttachVolumeResponse.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcAttachVolumeResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcPowerDown.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcPowerDown.Factory.parse(param
						 .getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcPowerDownResponse.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcPowerDownResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcDescribeResource.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcDescribeResource.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcDescribeResourceResponse.class
					 .equals(type)) {

				 return edu.ucsb.eucalyptus.NcDescribeResourceResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcCancelBundleTask.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcCancelBundleTask.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcCancelBundleTaskResponse.class
					 .equals(type)) {

				 return edu.ucsb.eucalyptus.NcCancelBundleTaskResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcBundleRestartInstance.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcBundleRestartInstance.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse.class
					 .equals(type)) {

				 return edu.ucsb.eucalyptus.NcBundleRestartInstanceResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcBundleInstance.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcBundleInstance.Factory.parse(param
						 .getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcBundleInstanceResponse.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcBundleInstanceResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcTerminateInstance.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcTerminateInstance.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcTerminateInstanceResponse.class
					 .equals(type)) {

				 return edu.ucsb.eucalyptus.NcTerminateInstanceResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcAssignAddress.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcAssignAddress.Factory.parse(param
						 .getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcAssignAddressResponse.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcAssignAddressResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcStartNetwork.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcStartNetwork.Factory.parse(param
						 .getXMLStreamReaderWithoutCaching());

			 }

			 if (edu.ucsb.eucalyptus.NcStartNetworkResponse.class.equals(type)) {

				 return edu.ucsb.eucalyptus.NcStartNetworkResponse.Factory
						 .parse(param.getXMLStreamReaderWithoutCaching());

			 }

		 } catch (java.lang.Exception e) {
			 throw org.apache.axis2.AxisFault.makeFault(e);
		 }
		 return null;
	 }

	 /**
	  * A utility method that copies the namepaces from the SOAPEnvelope
	  */
	 private java.util.Map getEnvelopeNamespaces(
			 org.apache.axiom.soap.SOAPEnvelope env) {
		 java.util.Map returnMap = new java.util.HashMap();
		 java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
		 while (namespaceIterator.hasNext()) {
			 org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator
					 .next();
			 returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
		 }
		 return returnMap;
	 }

	 private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
		 org.apache.axis2.AxisFault f;
		 Throwable cause = e.getCause();
		 if (cause != null) {
			 f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
		 } else {
			 f = new org.apache.axis2.AxisFault(e.getMessage());
		 }

		 return f;
	 }

}// end of class

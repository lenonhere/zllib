/**
 * MsgWServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ws.basedata.cmpp;

public class MsgWServiceImplServiceLocator extends
		org.apache.axis.client.Service implements MsgWServiceImplService {

	public MsgWServiceImplServiceLocator() {
	}

	public MsgWServiceImplServiceLocator(
			org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public MsgWServiceImplServiceLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for MsgWServiceImpl
	private java.lang.String MsgWServiceImpl_address = "http://oa.furongwang.com:80/cmppService/services/smsPort";

	public java.lang.String getMsgWServiceImplAddress() {
		return MsgWServiceImpl_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String MsgWServiceImplWSDDServiceName = "MsgWServiceImpl";

	public java.lang.String getMsgWServiceImplWSDDServiceName() {
		return MsgWServiceImplWSDDServiceName;
	}

	public void setMsgWServiceImplWSDDServiceName(java.lang.String name) {
		MsgWServiceImplWSDDServiceName = name;
	}

	public MsgWServiceImpl getMsgWServiceImpl()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(MsgWServiceImpl_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getMsgWServiceImpl(endpoint);
	}

	public MsgWServiceImpl getMsgWServiceImpl(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			MsgWServiceImplSoapBindingStub _stub = new MsgWServiceImplSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getMsgWServiceImplWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setMsgWServiceImplEndpointAddress(java.lang.String address) {
		MsgWServiceImpl_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (MsgWServiceImpl.class
					.isAssignableFrom(serviceEndpointInterface)) {
				MsgWServiceImplSoapBindingStub _stub = new MsgWServiceImplSoapBindingStub(
						new java.net.URL(MsgWServiceImpl_address), this);
				_stub.setPortName(getMsgWServiceImplWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("MsgWServiceImpl".equals(inputPortName)) {
			return getMsgWServiceImpl();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName(
				"http://cmpp.presentation.cmpp.cdcf.net",
				"MsgWServiceImplService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"http://cmpp.presentation.cmpp.cdcf.net", "MsgWServiceImpl"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("MsgWServiceImpl".equals(portName)) {
			setMsgWServiceImplEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}

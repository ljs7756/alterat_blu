/**
 * CommonLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cha.transcoder.nps.soapinterface;

public class CommonLocator extends org.apache.axis.client.Service implements com.cha.transcoder.nps.soapinterface.Common {

    public CommonLocator() {
    }


    public CommonLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CommonLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CommonPort
    private java.lang.String CommonPort_address = "http://10.160.77.193:8090/interface/app/common.php";

    public java.lang.String getCommonPortAddress() {
        return CommonPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CommonPortWSDDServiceName = "CommonPort";

    public java.lang.String getCommonPortWSDDServiceName() {
        return CommonPortWSDDServiceName;
    }

    public void setCommonPortWSDDServiceName(java.lang.String name) {
        CommonPortWSDDServiceName = name;
    }

    public com.cha.transcoder.nps.soapinterface.CommonPortType getCommonPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CommonPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCommonPort(endpoint);
    }

    public com.cha.transcoder.nps.soapinterface.CommonPortType getCommonPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.cha.transcoder.nps.soapinterface.CommonBindingStub _stub = new com.cha.transcoder.nps.soapinterface.CommonBindingStub(portAddress, this);
            _stub.setPortName(getCommonPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCommonPortEndpointAddress(java.lang.String address) {
        CommonPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.cha.transcoder.nps.soapinterface.CommonPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.cha.transcoder.nps.soapinterface.CommonBindingStub _stub = new com.cha.transcoder.nps.soapinterface.CommonBindingStub(new java.net.URL(CommonPort_address), this);
                _stub.setPortName(getCommonPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CommonPort".equals(inputPortName)) {
            return getCommonPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:interface/app/common", "Common");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:interface/app/common", "CommonPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CommonPort".equals(portName)) {
            setCommonPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

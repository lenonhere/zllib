package com.surekam.services.contshippment;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

import com.ws.basedata.contshippment.TransContShippment;
import com.ws.basedata.contshippment.TransContShippmentResponse;

public class GSXTMsgSetSOAP_HTTP_ServiceClient {

	private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
	private HashMap endpoints = new HashMap();
	private Service service0;

	public GSXTMsgSetSOAP_HTTP_ServiceClient() {
		create0();
		Endpoint GSXTMsgSetPortTypeLocalEndpointEP = service0.addEndpoint(
				new QName("http://surekam.com/services/ContShippment",
						"GSXTMsgSetPortTypeLocalEndpoint"), new QName(
						"http://surekam.com/services/ContShippment",
						"GSXTMsgSetPortTypeLocalBinding"),
				"xfire.local://GSXTMsgSetSOAP_HTTP_Service");
		endpoints.put(new QName("http://surekam.com/services/ContShippment",
				"GSXTMsgSetPortTypeLocalEndpoint"),
				GSXTMsgSetPortTypeLocalEndpointEP);
		Endpoint SOAP_HTTP_PortEP = service0.addEndpoint(new QName(
				"http://surekam.com/services/ContShippment", "SOAP_HTTP_Port"),
				new QName("http://surekam.com/services/ContShippment",
						"GSXTMsgSetSOAP_HTTP_Binding"),
				"http://10.156.0.136:7802/services/gs/ContShippmentHttpPort");
		endpoints.put(new QName("http://surekam.com/services/ContShippment",
				"SOAP_HTTP_Port"), SOAP_HTTP_PortEP);
	}

	public Object getEndpoint(Endpoint endpoint) {
		try {
			return proxyFactory.create((endpoint).getBinding(),
					(endpoint).getUrl());
		} catch (MalformedURLException e) {
			throw new XFireRuntimeException("Invalid URL", e);
		}
	}

	public Object getEndpoint(QName name) {
		Endpoint endpoint = ((Endpoint) endpoints.get((name)));
		if ((endpoint) == null) {
			throw new IllegalStateException("No such endpoint!");
		}
		return getEndpoint((endpoint));
	}

	public Collection getEndpoints() {
		return endpoints.values();
	}

	private void create0() {
		TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance()
				.getXFire().getTransportManager());
		HashMap props = new HashMap();
		props.put("annotations.allow.interface", true);
		AnnotationServiceFactory asf = new AnnotationServiceFactory(
				new Jsr181WebAnnotations(), tm, new AegisBindingProvider(
						new JaxbTypeRegistry()));
		asf.setBindingCreationEnabled(false);
		service0 = asf.create(
				(com.surekam.services.contshippment.GSXTMsgSetPortType.class),
				props);
		{
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0,
					new QName("http://surekam.com/services/ContShippment",
							"GSXTMsgSetPortTypeLocalBinding"),
					"urn:xfire:transport:local");
		}
		{
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0,
					new QName("http://surekam.com/services/ContShippment",
							"GSXTMsgSetSOAP_HTTP_Binding"),
					"http://schemas.xmlsoap.org/soap/http");
		}
	}

	public GSXTMsgSetPortType getGSXTMsgSetPortTypeLocalEndpoint() {
		return ((GSXTMsgSetPortType) (this).getEndpoint(new QName(
				"http://surekam.com/services/ContShippment",
				"GSXTMsgSetPortTypeLocalEndpoint")));
	}

	public GSXTMsgSetPortType getGSXTMsgSetPortTypeLocalEndpoint(String url) {
		GSXTMsgSetPortType var = getGSXTMsgSetPortTypeLocalEndpoint();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}

	public GSXTMsgSetPortType getSOAP_HTTP_Port() {
		return ((GSXTMsgSetPortType) (this).getEndpoint(new QName(
				"http://surekam.com/services/ContShippment", "SOAP_HTTP_Port")));
	}

	public GSXTMsgSetPortType getSOAP_HTTP_Port(String url) {
		GSXTMsgSetPortType var = getSOAP_HTTP_Port();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}

	public static void main(String[] args) {

		GSXTMsgSetSOAP_HTTP_ServiceClient client = new GSXTMsgSetSOAP_HTTP_ServiceClient();

		// create a default service endpoint
		GSXTMsgSetPortType service = client.getSOAP_HTTP_Port();

		TransContShippment t = new TransContShippment();
		t.setComCode("11320301 ");
		t.setContShippment("<?xml version=\"1.0\" encoding=\"gb2312\"?><DATASETS><DATASET><CONT_NUM>100169196</CONT_NUM><SHIPPMENT_DATE>20061116</SHIPPMENT_DATE><PLAN_ARRIVAL_DATE>20061116</PLAN_ARRIVAL_DATE><TRANSLICE_CODE>100169196</TRANSLICE_CODE><TRANS_KIND>01</TRANS_KIND><CARNO>23A87431</CARNO><DRIVERNAME>ddd</DRIVERNAME><PHONE>13901020304</PHONE><MODEL>11223456756</MODEL><ZONE>北京</ZONE></DATASET></DATASETS>");
		t.setIndCode("20420001");

		TransContShippmentResponse a = service.getContShippment(t);
		System.out.println(a.getStatus());
	}

}

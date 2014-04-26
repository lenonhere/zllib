package com.surekam.services.contshippment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.ws.basedata.contshippment.TransContShippment;
import com.ws.basedata.contshippment.TransContShippmentResponse;

@WebService(name = "GSXTMsgSetPortType", targetNamespace = "http://surekam.com/services/ContShippment")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface GSXTMsgSetPortType {

	@WebMethod(operationName = "getContShippment", action = "")
	@WebResult(name = "transContShippmentResponse", targetNamespace = "http://tobacco/ecs/com/bpm/contshippment")
	public TransContShippmentResponse getContShippment(
			@WebParam(name = "transContShippment", targetNamespace = "http://tobacco/ecs/com/bpm/contshippment") TransContShippment transContShippment);

}

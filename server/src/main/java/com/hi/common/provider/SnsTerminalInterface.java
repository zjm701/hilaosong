package com.hi.common.provider;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.0-milestone2
 * 2014-06-11T15:13:41.106+08:00 Generated source version: 3.0.0-milestone2
 * 
 */
@WebService(targetNamespace = "http://services.hdl.bcinfo.com", name = "SnsTerminalInterface")
public interface SnsTerminalInterface {

	@WebResult(name = "terminalUserLoginReturn", targetNamespace = "http://services.hdl.bcinfo.com")
	@RequestWrapper(localName = "terminalUserLogin", targetNamespace = "http://services.hdl.bcinfo.com", className = "com.bcinfo.hdl.services.TerminalUserLogin")
	@WebMethod
	@ResponseWrapper(localName = "terminalUserLoginResponse", targetNamespace = "http://services.hdl.bcinfo.com", className = "com.bcinfo.hdl.services.TerminalUserLoginResponse")
	public java.lang.String terminalUserLogin(
			@WebParam(name = "inJson", targetNamespace = "http://services.hdl.bcinfo.com") java.lang.String inJson);

	@WebResult(name = "getUserInfoReturn", targetNamespace = "http://services.hdl.bcinfo.com")
	@RequestWrapper(localName = "getUserInfo", targetNamespace = "http://services.hdl.bcinfo.com", className = "com.bcinfo.hdl.services.GetUserInfo")
	@WebMethod
	@ResponseWrapper(localName = "getUserInfoResponse", targetNamespace = "http://services.hdl.bcinfo.com", className = "com.bcinfo.hdl.services.GetUserInfoResponse")
	public java.lang.String getUserInfo(
			@WebParam(name = "inJson", targetNamespace = "http://services.hdl.bcinfo.com") java.lang.String inJson);

	@WebResult(name = "terminalUserLoginOutReturn", targetNamespace = "http://services.hdl.bcinfo.com")
	@RequestWrapper(localName = "terminalUserLoginOut", targetNamespace = "http://services.hdl.bcinfo.com", className = "com.bcinfo.hdl.services.TerminalUserLoginOut")
	@WebMethod
	@ResponseWrapper(localName = "terminalUserLoginOutResponse", targetNamespace = "http://services.hdl.bcinfo.com", className = "com.bcinfo.hdl.services.TerminalUserLoginOutResponse")
	public java.lang.String terminalUserLoginOut(
			@WebParam(name = "inJson", targetNamespace = "http://services.hdl.bcinfo.com") java.lang.String inJson);

	@WebResult(name = "terminalUserRegisterReturn", targetNamespace = "http://services.hdl.bcinfo.com")
	@RequestWrapper(localName = "terminalUserRegister", targetNamespace = "http://services.hdl.bcinfo.com", className = "com.bcinfo.hdl.services.TerminalUserRegister")
	@WebMethod
	@ResponseWrapper(localName = "terminalUserRegisterResponse", targetNamespace = "http://services.hdl.bcinfo.com", className = "com.bcinfo.hdl.services.TerminalUserRegisterResponse")
	public java.lang.String terminalUserRegister(
			@WebParam(name = "inJson", targetNamespace = "http://services.hdl.bcinfo.com") java.lang.String inJson);

}

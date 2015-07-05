package com.hi.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.GsonBuilder;

public abstract class BaseAction {

	protected Response getSuccessJsonResponse(Object obj) {
		Response.ResponseBuilder b = Response.status(Status.OK);
		GsonBuilder gb = new GsonBuilder();
		b.entity(gb.create().toJson(obj));
		return b.build();
	}
	
	protected Response getSuccessResponse(Object obj) {
		Response.ResponseBuilder b = Response.status(Status.OK);
		b.entity(obj);
		return b.build();
	}
	
	protected Response getFailedJsonResponse(Object obj) {
		Response.ResponseBuilder b = Response.status(Status.BAD_REQUEST);
		GsonBuilder gb = new GsonBuilder();
		b.entity(gb.create().toJson(obj));
		return b.build();
	}
	
	protected String getMessageJsonResult(String msg) {
		return "{\"respMsg\":\""+msg+"\"}";
	}
	
	protected HttpSession getSession() {
		return getRequest().getSession();
	}
	
	protected HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
}

package com.hi.control;

import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.GsonBuilder;

public abstract class BaseAction {

	protected Response getSuccessJsonResponse(Object obj) {
		Response.ResponseBuilder b = Response.status(Status.OK);
		GsonBuilder gb = new GsonBuilder();
		b.entity(gb.create().toJson(obj));
		return b.build();
	}
	
	protected String getFailedJsonResult(String msg) {
		return "{\"respMsg\":\""+msg+"\"}";
	}
}

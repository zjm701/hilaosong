package com.hi.tools;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ResponseTools {

	private static Response.ResponseBuilder rb = Response.status(Status.OK);

	public static Response getResponse(Object entity) {
		rb.entity(entity);
		return rb.build();
	}
}

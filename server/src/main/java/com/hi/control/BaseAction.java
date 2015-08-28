package com.hi.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.GsonBuilder;
import com.hi.common.MessageCode;
import com.hi.common.Pagination;

public abstract class BaseAction {

	protected Response getSuccessJsonResponse(Object obj) {
		Response.ResponseBuilder b = Response.status(Status.OK);
		GsonBuilder gb = new GsonBuilder();
		b.entity(gb.create().toJson(obj));
		return b.build();
	}

	protected Response getSuccessJsonResponse(MessageCode error) {
		return getSuccessJsonResponse(new com.hi.json.Response(error.getKey(), error.getDesc()));
	}

	protected Response getFailedJsonResponse(Object obj) {
		Response.ResponseBuilder b = Response.status(Status.BAD_REQUEST);
		GsonBuilder gb = new GsonBuilder();
		b.entity(gb.create().toJson(obj));
		return b.build();
	}

	protected String getJsonString(MessageCode error) {
		return "{\"respCode\":\"" + error.getKey() + "\",\"respMsg\":\"" + error.getDesc() + "\"}";
	}

	protected String getJsonString(Map<String, Object> results) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (String key : results.keySet()) {
			if (sb.length() > 1) {
				sb.append(",");
			}
			sb.append("\"").append(key).append("\":\"").append(results.get(key)).append("\"");
		}
		sb.append("}");
		return sb.toString();
	}

	protected String getJsonString(int totalRowsCount) {
		Pagination pagn = new Pagination();
		pagn.setTotalRowsCount(totalRowsCount);
		return "{\"totalRowsCount\":" + pagn.getTotalRowsCount() + ", \"totalPagesCount\":" + pagn.getTotalPagesCount() + "}";
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
}

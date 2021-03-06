package com.hi.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.google.gson.GsonBuilder;
import com.hi.common.MessageCode;
import com.hi.common.Pagination;

public abstract class BaseAction {
	
	public Log logger = LogFactory.getLog(getClass());

	protected Response getSuccessJsonResponse(Object obj) {
		Response.ResponseBuilder b = Response.status(Status.OK);
		GsonBuilder gb = new GsonBuilder();
		String rep = gb.create().toJson(obj);
		logger.debug(rep);
		b.entity(rep);
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

	protected Response getFailedJsonResponse(MessageCode error) {
		return getFailedJsonResponse(new com.hi.json.Response(error.getKey(), error.getDesc()));
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

	protected String getJsonString(int totalRowsCount, int pageSize) {
		Pagination pagn = new Pagination(pageSize);
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
	protected HttpServletResponse getResponse() {
		HttpServletResponse resp = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		return resp;
	}
}

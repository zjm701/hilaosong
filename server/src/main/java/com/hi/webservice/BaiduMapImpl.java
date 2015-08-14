package com.hi.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.GsonBuilder;
import com.hi.tools.BaiduTools;

@Path("/")
public class BaiduMapImpl {
	
	@GET
	@Path("/testbaidu")
	@Produces("application/json")
	public Response testbaiduapi() {
		List<String> result = new ArrayList<String>();
		result.add(""+BaiduTools.getDIstance("40.056878,116.30815", "39.915285,116.403857"));
		
		String desc = BaiduTools.getDescByLocation("116.420593,40.076381");
		result.add(desc);
		
		result.add(BaiduTools.getCusPointByAddress(desc));
		Response.ResponseBuilder b = Response.status(Status.OK);
		GsonBuilder gb = new GsonBuilder();
		b.entity(gb.create().toJson(result));
		return b.build();
	}
}

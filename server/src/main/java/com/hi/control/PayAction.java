package com.hi.control;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.service.PayChannelService;

@Path("/")
public class PayAction extends BaseAction {

	@Autowired
	private PayChannelService payChannelService;

	@GET
	@Path(value = "/getpaychannels")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPayChannels() {
		return getSuccessJsonResponse(payChannelService.getPayChannels());
	}
}

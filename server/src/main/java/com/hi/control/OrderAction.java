package com.hi.control;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.model.Order;
import com.hi.service.OrderService;

@Path("/")
public class OrderAction extends BaseAction {

	@Autowired
	private OrderService orderService;

	@GET
	@Path("/gethistoryorders")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistoryOrders(@FormParam("userId") String userId) {
		List<Order> orders = orderService.getHistoryOrders(userId);
		return getSuccessJsonResponse(orders);
	}
}

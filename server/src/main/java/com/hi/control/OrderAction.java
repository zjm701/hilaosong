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
import com.hi.model.OrderAddress;
import com.hi.service.OrderService;

@Path("/")
public class OrderAction extends BaseAction {

	@Autowired
	private OrderService orderService;
	
	@GET
	@Path("/getlatestaddress")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLatestAddress(@FormParam("userId") String userId) {
		OrderAddress address = orderService.getLatestAddress(userId);
		return getSuccessJsonResponse(address);
	}
	
	/**
	 * "/wap/historyOrderList" (Mobile version)
	 * 
	 * @param userId
	 * @param pageIndex
	 * @return
	 */
	@GET
	@Path("/gethistoryorders")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistoryOrders(@FormParam("userId") String userId, @FormParam("pageIndex") int pageIndex) {
		List<Order> orders = orderService.getHistoryOrders(userId, pageIndex);
		return getSuccessJsonResponse(orders);
	}
	
	/**
	 * "/wap/historyOrderDetail" (Mobile version)
	 * 
	 * @param userId
	 * @param pageIndex
	 * @return
	 */
	@GET
	@Path("/getorderinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderInfo(@FormParam("orderId") String orderId) {
		Order order = orderService.getOrderInfo(orderId);
		return getSuccessJsonResponse(order);
	}
}

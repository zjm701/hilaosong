package com.hi.control;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.hi.common.HIConstants;
import com.hi.model.Order;
import com.hi.model.OrderAddress;
import com.hi.model.User;
import com.hi.service.OrderService;
import com.hi.service.StoreService;
import com.hi.tools.StringTools;

@Path("/")
public class OrderAction extends BaseAction {

	@Autowired
	private OrderService orderService;

	@Autowired
	private StoreService storeService;

	/**
	 * @param userId
	 * @return
	 */
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
	 * @param orderId
	 * @return
	 */
	@GET
	@Path("/getorderinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderInfo(@FormParam("orderId") String orderId) {
		Order order = orderService.getOrderInfo(orderId);
		return getSuccessJsonResponse(order);
	}

	/**
	 * "/wap/createOrder" "/wap3/seatingOrder" (Mobile version)
	 * storeId = 020115
	 * storeId = 020102
	 * @param content
	 * @return
	 */
	@POST
	@Path("/createorder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createOrder(String content) {
		getSession().removeAttribute(HIConstants.ORDER_ID);
		System.out.println("==> content:" + content);
		
		Gson gson = new Gson();
		Order order = gson.fromJson(content, Order.class);

		String message = "";
		if (StringUtils.isEmpty(order.getContactName())) {
			message = "\u8ba2\u5355\u8054\u7cfb\u4eba\u4e0d\u80fd\u4e3a\u7a7a"; //订单联系人不能为空
		} else if (StringUtils.isEmpty(order.getContactPhone())) {
			message = "\u8ba2\u5355\u8054\u7cfb\u7535\u8bdd\u4e0d\u80fd\u4e3a\u7a7a"; //订单联系电话不能为空
		} else if (StringUtils.isEmpty(order.getDinningTime())) {
			message = "\u8ba2\u9910\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a"; //订餐时间不能为空
		} else if (storeService.isShutdown(order.getStoreId())) { // 门店停业
			message = storeService.getShutdownNotice(order.getStoreId());
		} else if (storeService.isClose(order.getStoreId(), StringTools.timeStr2Date(order.getDinningTime(), StringTools.IPHONETIME))) { // 打样
			message = order.getDinningTime() + "\u505c\u6b62\u8425\u4e1a"; //停止营业
		}
		if (StringUtils.isEmpty(message)) {
			order.setCustomerId((String) getSession().getAttribute(HIConstants.LOGIN_ID));
			order.setSex(((User) getSession().getAttribute(HIConstants.USER)).getSex() + "");
			order.setDinningTimeType(storeService.getDinningTimeType(order.getDinningTime(), order.getStoreId()));
			
			String orderId = orderService.createOrder(order);
			if(orderId != null ){
				getSession().setAttribute(HIConstants.ORDER_ID, orderId);
				message = "\u521b\u5efa\u5b9a\u5355\u6210\u529f, \u8ba2\u5355\u7f16\u53f7: " + orderId; //创建定单成功
			}else{
				message = "\u521b\u5efa\u5b9a\u5355\u5931\u8d25"; //创建定单失败
			}
		} 
		return getMessageJsonResult(message);
	}
}

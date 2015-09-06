package com.hi.control;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hi.common.MessageCode;
import com.hi.common.OrderType;
import com.hi.model.Order;
import com.hi.model.OrderAddress;
import com.hi.model.User;
import com.hi.service.OrderService;
import com.hi.service.RecieptDeptService;
import com.hi.service.StoreService;
import com.hi.tools.CalendarTools;

@Path("/")
public class OrderAction extends BaseAction {

	@Autowired
	private OrderService orderService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private RecieptDeptService recieptDeptService;

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
	 * 
	 * @param userId
	 * @return
	 */
	@GET
	@Path("/cnthistoryorders")
	@Produces(MediaType.APPLICATION_JSON)
	public String countHistoryOrders(@FormParam("userId") String userId) {
		return getJsonString(orderService.countHistoryOrders(userId));
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
	 * "/wap/createOrder" "/wap/deliveringOrder" (Mobile version)
	 * 
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

		User user = (User) getSession().getAttribute(HIConstants.USER);

		if (user == null) {
			return getJsonString(MessageCode.ERROR_NO_LOGGEDIN_USER);
		} else {
			Gson gson = new Gson();
			Order order = gson.fromJson(content, Order.class);

			Date dinningTime = null;
			if (StringUtils.isEmpty(order.getDinningTime())) {
				return getJsonString(MessageCode.VERIFICATION_EMPTY_DINNINGTIME);
			} else {
				dinningTime = CalendarTools.timeStr2Date(order.getDinningTime(), CalendarTools.DATETIME_DEFAULT);
				if (dinningTime.before(CalendarTools.now())) {
					return getJsonString(MessageCode.VERIFICATION_PASSED_DINNINGTIME);
				}
			}
			if (StringUtils.isEmpty(order.getContactName())) {
				return getJsonString(MessageCode.VERIFICATION_EMPTY_CONTACTNAME);
			} else if (StringUtils.isEmpty(order.getContactPhone())) {
				return getJsonString(MessageCode.VERIFICATION_EMPTY_CONTACTPHONE);
			} else if (StringUtils.isEmpty(order.getStoreId())) {
				return getJsonString(MessageCode.VERIFICATION_EMPTY_STORE);
			} else if (order.getTotalDishesCount() == 0) {
				return getJsonString(MessageCode.VERIFICATION_EMPTY_DISHES);
			} else if (StringUtils.isEmpty(order.getOrderType())) {
				return getJsonString(MessageCode.VERIFICATION_EMPTY_ORDERTYPE);
			} else if (OrderType.SEND_OUT.getKey().equals(order.getOrderType()) && order.getAddress() != null
					&& StringUtils.isEmpty(order.getAddress().getDetailAddress())) {
				return getJsonString(MessageCode.VERIFICATION_EMPTY_ADDRESS);
			} else if (StringUtils.isEmpty(order.getPayChannel())) {
				return getJsonString(MessageCode.VERIFICATION_EMPTY_PAYCHANNEL);
			}

			order.setCustomerId((String) getSession().getAttribute(HIConstants.LOGIN_ID));
			order.setSex(((User) getSession().getAttribute(HIConstants.USER)).getSex() + "");
			order.setDinningTimeType(storeService.getDinningTimeType(order.getDinningTime(), order.getStoreId()));

			String orderId = orderService.createOrder(order);
			if (orderId != null) {
				getSession().setAttribute(HIConstants.ORDER_ID, orderId);
				if (order.isNeedReciept()) {
					recieptDeptService.createRecieptDept(order.getCustomerId(), order.getRecieptType(), order.getRecieptDept());
				}

				Map<String, Object> m = new HashMap<String, Object>();
				m.put("orderId", orderId);
				m.put("respCode", MessageCode.SUCCESS_CREATE_ORDER.getKey());
				m.put("respMsg", MessageCode.SUCCESS_CREATE_ORDER.getDesc());
				return getJsonString(m);
			} else {
				return getJsonString(MessageCode.ERROR_CREATE_ORDER);
			}
		}
	}
}

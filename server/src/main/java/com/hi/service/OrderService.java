package com.hi.service;

import java.util.List;
import java.util.Map;

import com.hi.model.Order;
import com.hi.model.OrderAddress;

public interface OrderService {

	OrderAddress getLatestAddress(String userId);

	int countHistoryOrders(String userId);
	
	List<Order> getHistoryOrders(String userId, int pageIndex);
	
	Order getOrderInfo(String orderId);
	
	String createOrder(Order order);
	
	/**
	 * 银联支付回调修改订单状态
	 * @param orderId
	 * @param payStatus
	 * @return
	 */
	boolean unionBackUpdateOrderPayStatus(String orderId,int payStatus);
	
	/**
	 * 检查订单知否可以支付
	 * @param orderId
	 * @param amt
	 * @return
	 */
	Map<String, Object> checkOrderIsCanPay(String orderId,Integer txAmt);
}

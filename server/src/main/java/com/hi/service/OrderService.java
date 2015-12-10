package com.hi.service;

import java.util.List;
import java.util.Map;

import com.hi.model.Order;
import com.hi.model.OrderAddress;

public interface OrderService {

	/**
	 * 查询用户订单地址信息
	 * @param userId
	 * @return
	 */
	OrderAddress getLatestAddress(String userId);

	/**
	 * 查询用户的历史订单数量
	 * @param userId
	 * @return
	 */
	int countHistoryOrders(String userId);
	/**
	 * 查询用户的历史订单
	 * @param userId
	 * @param pageIndex
	 * @return
	 */
	List<Order> getHistoryOrders(String userId, int pageIndex);
	/**
	 * 查询订单
	 * @param orderId
	 * @return
	 */
	Order getOrderInfo(String orderId);
	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
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

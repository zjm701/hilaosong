package com.hi.dao;

import java.util.List;

import com.hi.model.Order;
import com.hi.model.OrderAddress;

public interface OrderDao {

	OrderAddress getLatestAddress(String userId);

	int countHistoryOrders(String userId);
	
	List<Order> getHistoryOrders(String userId, int pageIndex);

	Order getOrderInfo(String orderId);

	long getOrderSerialId();
	
	boolean createOrder(Order order);
	
	/**
	 * 根据订单号跟新订单状态
	 * @param orderId
	 * @return
	 */
	boolean updateOrderPayStatus(String orderId,int payStatus);
}

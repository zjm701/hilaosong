package com.hi.dao;

import java.util.List;

import com.hi.model.Order;
import com.hi.model.OrderAddress;

public interface OrderDao {

	OrderAddress getLatestAddress(String userId);

	List<Order> getHistoryOrders(String userId, int pageIndex);

	Order getOrderInfo(String orderId);

	long getOrderSerialId();
	
	boolean createOrder(Order order);
}

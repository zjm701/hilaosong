package com.hi.service;

import java.util.List;

import com.hi.model.Order;
import com.hi.model.OrderAddress;

public interface OrderService {

	OrderAddress getLatestAddress(String userId);
	
	List<Order> getHistoryOrders(String userId, int pageIndex);
	
	Order getOrderInfo(String orderId);
}

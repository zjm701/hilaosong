package com.hi.dao;

import java.util.List;

import com.hi.model.Order;

public interface OrderDao {

	List<Order> getHistoryOrders(String userId, int pageIndex);

	public Order getOrderInfo(String orderId);
}

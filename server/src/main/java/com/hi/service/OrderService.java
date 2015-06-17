package com.hi.service;

import java.util.List;

import com.hi.model.Order;

public interface OrderService {
	List<Order> getHistoryOrders(String userId);
}

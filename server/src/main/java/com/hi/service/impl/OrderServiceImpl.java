package com.hi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.OrderDao;
import com.hi.model.Order;
import com.hi.service.OrderService;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao dao;

	public List<Order> getHistoryOrders(String userId, int pageIndex) {
		List<Order> orders = dao.getHistoryOrders(userId, pageIndex);
		return orders;
	}
}

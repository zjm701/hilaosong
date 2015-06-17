package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.OrderDao;
import com.hi.model.Order;

@Repository("orderDao")
public class OrderDaoImpl extends AbstractDao implements OrderDao {

	/* 
	 * refer to query:findOrdersHasDishByCustIdAndPage of cater-order-hql.xml
	 */
	@Override
	public List<Order> getHistoryOrders(String userId) {
		String sql = "select serialId as \"serialId\", orderId as \"orderId\", customerId as \"customerId\", "
				+ " storeId as \"storeId\", contactName as \"contactName\", contactPhone as \"contactPhone\", "
				+ " status as \"status\", payStatus as \"payStatus\", orderType as \"orderType\", "
				+ " deliveryType as \"deliveryType\" from T_CATER_ORDERMAININFO o "
				+ " where o.customerId = :userId and o.status not in ('11','12') order by created_dt desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		List<Order> orders = this.getBeansBySql(Order.class, sql, params);
		return orders;
	}
}
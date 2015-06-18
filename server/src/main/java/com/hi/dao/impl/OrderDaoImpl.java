package com.hi.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.common.Pagination;
import com.hi.dao.AbstractDao;
import com.hi.dao.OrderDao;
import com.hi.model.Order;

@Repository("orderDao")
public class OrderDaoImpl extends AbstractDao implements OrderDao {

	/*
	 * refer to query:findOrdersHasDishByCustIdAndPage of cater-order-hql.xml
	 */
	@Override
	public List<Order> getHistoryOrders(String userId, int pageIndex) {
		String sql = "select serialId as \"serialId\", orderId as \"orderId\", customerId as \"customerId\", "
				+ " storeId as \"storeId\", contactName as \"contactName\", contactPhone as \"contactPhone\", "
				+ " status as \"status\", payStatus as \"payStatus\", orderType as \"orderType\", "
				+ " to_char(created_dt, 'yyyy-mm-dd hh24:mi:ss') as \"createdDt\", deliveryType as \"deliveryType\" "
				+ " from T_CATER_ORDERMAININFO o "
				+ " where o.status not in ('11','12') "
				+ "		and o.orderid in "
				+ "			(	select distinct(d.orderid) from T_CATER_ORDERDISHES d "
				+ "				inner join T_CATER_ORDERMAININFO o on d.orderid = o.orderid "
				+ "				where o.customerId = :userId )"
//				+ "				where o.customerId = :userId and o.created_by = :userId)"
				+ " order by created_dt desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		Pagination pagn = new Pagination();
		pagn.setPageIndex(pageIndex);
		List<Order> orders = this.getBeansBySql(Order.class, sql, params, pagn);
		return orders;
	}
}
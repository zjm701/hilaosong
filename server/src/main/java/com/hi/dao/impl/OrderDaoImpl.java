package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.common.Pagination;
import com.hi.dao.AbstractDao;
import com.hi.dao.OrderDao;
import com.hi.model.OrderAddress;
import com.hi.model.Order;
import com.hi.model.OrderDish;
import com.hi.model.OrderExpenses;
import com.hi.model.OrderPack;
import com.hi.model.OrderPackDish;

@Repository("orderDao")
public class OrderDaoImpl extends AbstractDao implements OrderDao {

	/*
	 * refer to query:findOrdersHasDishByCustIdAndPage of cater-order-hql.xml
	 */
	@Override
	public List<Order> getHistoryOrders(String userId, int pageIndex) {
		String sql = "select serialId as \"serialId\", orderId as \"orderId\", customerId as \"customerId\", "
				+ " o.storeId as \"storeId\", s.storename as \"storeName\", contactName as \"contactName\", contactPhone as \"contactPhone\", "
				+ " to_char(dinningTime, " + DT_FORMAT + ") as \"dinningTime\", status as \"status\", "
				+ " orderType as \"orderType\", deliveryType as \"deliveryType\", "
				+ " payChannel as \"payChannel\", custMemo as \"custMemo\", "
				+ " orderNature as \"orderNature\", payStatus as \"payStatus\", "
				+ " to_char(created_dt, " + DT_FORMAT + ") as \"createdDt\" "
				+ " from T_CATER_ORDERMAININFO o "
				+ " left outer join T_CATER_STORE s ON o.storeid = s.storeid "
				+ " where o.status not in ('11','12') and o.order_Src in ('W','S') "
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
	
	public Order getOrderInfo(String orderId){
		String ordersql = "select o.serialId as \"serialId\", o.orderId as \"orderId\", o.customerId as \"customerId\", "
				+ " o.storeId as \"storeId\", s.storename as \"storeName\", o.contactName as \"contactName\", o.contactPhone as \"contactPhone\", "
				+ " to_char(o.dinningTime, " + DT_FORMAT + ") as \"dinningTime\", o.status as \"status\", "
				+ " o.potStatus as \"potStatus\", o.orderType as \"orderType\", o.deliveryType as \"deliveryType\", "
				+ " o.recieptDept as \"recieptDept\", o.payChannel as \"payChannel\", o.custMemo as \"custMemo\", "
				+ " o.orderNature as \"orderNature\", o.payStatus as \"payStatus\", "
				+ " to_char(created_dt, " + DT_FORMAT + ") as \"createdDt\" "
				+ " from T_CATER_ORDERMAININFO o "
				+ " left outer join T_CATER_STORE s ON o.storeid = s.storeid "
				+ " where o.orderid = :orderId ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		Order order = this.getBeanBySql(Order.class, ordersql, params);
		
		String addresssql = "select addressId as \"addressId\", customerPhone as \"customerPhone\", "
				+ " provinceId as \"provinceId\", cityId as \"cityId\", regionId as \"regionId\", "
				+ " detailAddress as \"detailAddress\", postCode as \"postCode\", village as \"village\" "
				+ " from T_CATER_DELIVERYADDRESS a "
				+ " where a.orderid = :orderId ";

		OrderAddress address = this.getBeanBySql(OrderAddress.class, addresssql, params);
		order.setAddress(address);

		String expensessql = "select id as \"expensesId\", deposit as \"deposit\", "
				+ " waiterFee as \"waiterFee\", deliveryFee as \"deliveryFee\", feeMemo as \"feeMemo\", "
				+ " dishPrice as \"dishPrice\", totalPrice as \"totalPrice\", kilometre as \"kilometre\", "
				+ " jgxh as \"storeName\", to_char(jzsj, " + DT_FORMAT + ") as \"payTime\", "
				+ " zdbh as \"tzxNo\", jzzt as \"payStatus\" "
				+ " from T_CATER_ORDEREXPENSESINFO e "
				+ " where e.orderid = :orderId ";

		OrderExpenses expenses = this.getBeanBySql(OrderExpenses.class, expensessql, params);
		order.setExpenses(expenses);

		String packsql = "select h.dishId as \"packId\", h.dishname as \"packName\", "
				+ " h.unitPrice as \"packPrice\", d.packCount as \"packCount\" "
				+ " from T_CATER_ORDERDISHES d "
				+ " inner join T_CATER_DISH h on d.packid = h.dishid "
				+ " where d.orderid = :orderId and d.dishid = :dishId ";
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("orderId", orderId);
		
		String packdishsql = "select d.dishId as \"dishId\", h.dishname as \"dishName\", "
				+ " d.unitPrice as \"unitPrice\", d.dishNumber as \"dishNumber\", d.dishType as \"dishType\", "
				+ " d.packId as \"packId\", d.innerId as \"innerId\" "
				+ " from T_CATER_ORDERDISHES d "
				+ " inner join T_CATER_DISH h on d.dishid = h.dishid "
				+ " where d.orderid = :orderId and d.dishtype='1'";
		List<OrderPackDish> packdishes = this.getBeansBySql(OrderPackDish.class, packdishsql, params);

		if (packdishes != null && packdishes.size() > 0) {
			for (OrderPackDish packdish : packdishes) {
				OrderPack pack = order.getPack(packdish.getPackId());
				if(pack == null){
					params2.put("dishId", packdish.getDishId());
					pack = this.getBeanBySql(OrderPack.class, packsql, params2);
					order.addPack(pack);
				}
				pack.addDish(packdish);
			}
		}
		
		String dishsql = "select d.dishId as \"dishId\", h.dishname as \"dishName\", "
				+ " d.unitPrice as \"unitPrice\", d.dishNumber as \"dishNumber\", d.dishType as \"dishType\" "
				+ " from T_CATER_ORDERDISHES d "
				+ " inner join T_CATER_DISH h on d.dishid = h.dishid "
				+ " where d.orderid = :orderId and d.dishtype in ('0', '2')";
		List<OrderDish> dishes = this.getBeansBySql(OrderDish.class, dishsql, params);
		order.setDishes(dishes);
		
		return order;
	}
}
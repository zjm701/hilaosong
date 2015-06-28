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
import com.hi.tools.StringTools;

@Repository("orderDao")
public class OrderDaoImpl extends AbstractDao implements OrderDao {

	@Override
	public OrderAddress getLatestAddress(String userId){
		String sql = "select addressId as \"addressId\", customerPhone as \"customerPhone\", "
				+ " provinceId as \"provinceId\", cityId as \"cityId\", regionId as \"regionId\", "
				+ " detailAddress as \"detailAddress\", postCode as \"postCode\", village as \"village\" "
				+ " from T_CATER_DELIVERYADDRESS a "
				+ " where a.customerId = :userId and a.provinceid is not null and a.cityid is not null "
				+ " 	and a.detailaddress is not null and a.isdel = '0' "
				+ " order by a.addressid desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return this.getBeanBySql(OrderAddress.class, sql, params);
	}
	
	/*
	 * refer to query:findOrdersHasDishByCustIdAndPage of cater-order-hql.xml
	 */
	@Override
	public List<Order> getHistoryOrders(String userId, int pageIndex) {
		String sql = "select serialId as \"serialId\", o.orderId as \"orderId\", customerId as \"customerId\", "
				+ " o.storeId as \"storeId\", s.storename as \"storeName\", contactName as \"contactName\", "
				+ " contactPhone as \"contactPhone\", participantNumber as \"participantNumber\", "
				+ " to_char(dinningTime, " + DT_FORMAT + ") as \"dinningTime\", status as \"status\", "
				+ " orderType as \"orderType\", deliveryType as \"deliveryType\", "
				+ " payChannel as \"payChannel\", custMemo as \"custMemo\", "
				+ " orderNature as \"orderNature\", payStatus as \"payStatus\", "
				+ " to_char(created_dt, " + DT_FORMAT + ") as \"createdDt\", e.totalPrice as \"totalPrice\" "
				+ " from T_CATER_ORDERMAININFO o "
				+ " inner join T_CATER_ORDEREXPENSESINFO e on e.orderid = o.orderid "
				+ " inner join T_CATER_STORE s ON o.storeid = s.storeid "
				+ " where o.status not in ('11','12') and o.orderType in ('0', '2') and o.order_Src in ('W','S') "
				+ "		and o.customerId = :userId "
//				+ "		and o.customerId = :userId and o.created_by = :userId "
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
				+ " o.storeId as \"storeId\", s.storename as \"storeName\", o.contactName as \"contactName\", "
				+ " o.contactPhone as \"contactPhone\", participantNumber as \"participantNumber\", "
				+ " to_char(o.dinningTime, " + DT_FORMAT + ") as \"dinningTime\", o.status as \"status\", "
				+ " o.potNumber as \"potNumber\", o.potStatus as \"potStatus\", o.orderType as \"orderType\", o.deliveryType as \"deliveryType\", "
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
		if (address != null) {
			order.setAddress(address);
		}

		String expensessql = "select id as \"expensesId\", deposit as \"deposit\", "
				+ " waiterFee as \"waiterFee\", deliveryFee as \"deliveryFee\", feeMemo as \"feeMemo\", "
				+ " dishPrice as \"dishPrice\", totalPrice as \"totalPrice\", kilometre as \"kilometre\", "
				+ " jgxh as \"storeName\", to_char(jzsj, " + DT_FORMAT + ") as \"payTime\", "
				+ " zdbh as \"tzxNo\", jzzt as \"payStatus\" "
				+ " from T_CATER_ORDEREXPENSESINFO e "
				+ " where e.orderid = :orderId ";

		OrderExpenses expenses = this.getBeanBySql(OrderExpenses.class, expensessql, params);
		if (expenses != null) {
			order.setExpenses(expenses);
		}

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
		if (dishes != null && dishes.size() > 0) {
			order.setDishes(dishes);
		}
		
		return order;
	}

	@Override
	public long getOrderSerialId() {
		return this.getNextvalBySeqName("SEQ_CATER_ORDERSERIEID");
	}

	@Override
	public boolean createOrder(Order o) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into t_cater_ordermaininfo ")
				.append("(serialId, orderId, customerId, storeId, contactName, contactPhone, sex, participantNumber, dinningTime, order_dinning_time_type, ")
				.append(" status, potNumber, potStatus, orderType, deliveryType, recieptDept, payChannel, payStatus, custMemo, order_src, created_by, ")
				.append(" created_dt, ordernature, feedbackable, channel, arrived) values ('").append(o.getSerialId()).append("', '")
				.append(o.getOrderId()).append("', '").append(o.getCustomerId()).append("', '").append(o.getStoreId()).append("', '")
				.append(o.getContactName()).append("', '").append(o.getContactPhone()).append("', '").append(o.getSex()).append("', '")
				.append(o.getParticipantNumber()).append("', to_date('").append(o.getDinningTime()).append("', ").append(DT_FORMAT).append("), '")
				.append(o.getDinningTimeType()).append("', '").append(o.getStatus()).append("', '").append(o.getPotNumber()).append("', '")
				.append(o.getPotStatus()).append("', '").append(o.getOrderType()).append("', '").append(o.getDeliveryType()).append("', '")
				.append(o.getRecieptDept()).append("', '").append(o.getPayChannel()).append("', '").append(o.getPayStatus()).append("', '")
				.append(o.getCustMemo()).append("', '").append(o.getOrderSrc()).append("', '").append(o.getCustomerId()).append("', sysdate, '2', '1', '0', '0') ");

		return this.executiveSql(sb.toString(), null) == 1;
	}
}
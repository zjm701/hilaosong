package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.common.OrderType;
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
		return this.getFirstBeanBySql(OrderAddress.class, sql, params);
	}
	
	@Override
	public int countHistoryOrders(String userId) {
		String sql = "select * from T_CATER_ORDERMAININFO o " 
				+ " inner join T_CATER_ORDEREXPENSESINFO e on e.orderid = o.orderid "
				+ " inner join T_CATER_STORE s ON o.storeid = s.storeid "
				+ " where o.status not in ('11','12') and o.orderType in ('0', '2') and o.order_Src in ('W','S') "
				+ "		and o.customerId = :userId ";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return this.countBySql(sql, params);
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
		Order order = this.getUniqueBeanBySql(Order.class, ordersql, params);
		
		String addresssql = "select addressId as \"addressId\", customerPhone as \"customerPhone\", "
				+ " provinceId as \"provinceId\", cityId as \"cityId\", regionId as \"regionId\", "
				+ " detailAddress as \"detailAddress\", postCode as \"postCode\", village as \"village\" "
				+ " from T_CATER_DELIVERYADDRESS a "
				+ " where a.orderid = :orderId ";

		OrderAddress address = this.getUniqueBeanBySql(OrderAddress.class, addresssql, params);
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

		OrderExpenses expenses = this.getUniqueBeanBySql(OrderExpenses.class, expensessql, params);
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
					pack = this.getUniqueBeanBySql(OrderPack.class, packsql, params2);
					order.addPack(pack);
				}
				pack.addDish(packdish);
			}
		}
		
		String dishsql = "select d.dishId as \"dishId\", case when (d.guodiId is not null) then d.detail else h.dishname end as \"dishName\", "
				+ " d.unitPrice as \"unitPrice\", d.dishNumber as \"dishNumber\", d.dishType as \"dishType\", d.guodiId as \"guodiId\" "
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
		sb.append("insert into T_CATER_ORDERMAININFO ")
				.append("(serialId, orderId, customerId, storeId, contactName, contactPhone, sex, participantNumber, dinningTime, order_dinning_time_type, ")
				.append(" status, potNumber, potStatus, orderType, deliveryType, recieptDept, payChannel, payStatus, custMemo, order_src, created_by, ")
				.append(" created_dt, ordernature, feedbackable, channel, arrived) values ('").append(o.getSerialId()).append("', '")
				.append(o.getOrderId()).append("', '").append(o.getCustomerId()).append("', '").append(o.getStoreId()).append("', '")
				.append(o.getContactName()).append("', '").append(o.getContactPhone()).append("', '").append(o.getSex()).append("', '")
				.append(o.getParticipantNumber()).append("', to_date('").append(o.getDinningTime()).append("', ").append(DT_FORMAT)
				.append("), '").append(o.getDinningTimeType()).append("', '").append(o.getStatus()).append("', '").append(o.getPotNumber())
				.append("', '").append(o.getPotStatus()).append("', '").append(o.getOrderType()).append("', '").append(o.getDeliveryType())
				.append("', '").append(o.getRecieptDept()).append("', '").append(o.getPayChannel()).append("', '").append(o.getPayStatus())
				.append("', '").append(o.getCustMemo()).append("', '").append(o.getOrderSrc()).append("', '").append(o.getCustomerId())
				.append("', sysdate, '2', '1', '0', '0') ");

		boolean saveflag = (this.executiveSql(sb.toString(), null) == 1);
		if (saveflag) {
			if (OrderType.SEND_OUT.getKey().equals(o.getOrderType())) {
				sb.setLength(0);
				OrderAddress a = o.getAddress();
				sb.append("insert into T_CATER_DELIVERYADDRESS ")
						.append("(addressId, customerId, customerPhone, provinceId, cityId, detailAddress, orderId, isdel) ")
						.append("values (SEQ_CATER_DELIVERYADDRESS.nextval, '").append(o.getCustomerId()).append("', '")
						.append(a.getCustomerPhone()).append("', '").append(a.getProvinceId()).append("', '").append(a.getCityId())
						.append("', '").append(a.getDetailAddress()).append("', '").append(o.getOrderId()).append("', '0') ");
				saveflag = (this.executiveSql(sb.toString(), null) == 1);
			}
		}
		if (saveflag) {
			if (o.getExpenses() != null) {
				sb.setLength(0);
				OrderExpenses e = o.getExpenses();
				sb.append("insert into T_CATER_ORDEREXPENSESINFO ").append("(id, waiterFee, deliveryFee, dishPrice, totalPrice, orderId) ")
						.append("values (seq_cater_orderexpensesinfo.nextval, '").append(e.getWaiterFee()).append("', '")
						.append(e.getDeliveryFee()).append("', '").append(e.getDishPrice()).append("', '").append(e.getTotalPrice())
						.append("', '").append(o.getOrderId()).append("') ");
				saveflag = (this.executiveSql(sb.toString(), null) == 1);
			}
		}
		if (saveflag) {
			if (o.getPacks() != null && o.getPacks().size() > 0) {
				String part1 = "insert into T_CATER_ORDERDISHES (id, orderid, dishid, dishnumber, dishtype, packid, unitprice, packcount, innerid) "
						+ "select SEQ_CATER_ORDERDISHES.Nextval , '" + o.getOrderId() + "', pd.dishid, pd.dishnumber, '1', pd.packid, d.unitprice, '";
				String part2 = "', pd.innerid from T_CATER_PACKDISH pd inner join T_CATER_DISH d on pd.dishid = d.dishid where pd.packId= :packId ";
				String part3 = "and pd.innerid not in ('1','2') ";
				
				String part4 = "insert into T_CATER_ORDERDISHES (id, orderid, dishid, dishnumber, dishtype, packid, unitprice, packcount, innerid) "
						+ "select SEQ_CATER_ORDERDISHES.Nextval , '" + o.getOrderId() + "', pd.dishid, '";
				String part5 = "', '1', pd.packid, d.unitprice, '";
				String part6 = "and pd.dishId = :dishId ";

				Map<String, Object> params = new HashMap<String, Object>();
				for (OrderPack pack : o.getPacks()) {
					sb.setLength(0);
					sb.append(part1).append(pack.getPackCount()).append(part2).append(part3);
					params.put("packId", pack.getPackId());
					this.executiveSql(sb.toString(), params);// insert 除锅底，小料外的固定的套餐菜品
					
					for (OrderPackDish dish : pack.getDishes()) { // insert 锅底，小料
						sb.setLength(0);
						int dishNumber = 1;
						if (dish.getDishNumber() != null) {
							dishNumber = dish.getDishNumber().intValue();
						}
						sb.append(part4).append(dishNumber).append(part5).append(pack.getPackCount()).append(part2).append(part6);
						params.put("dishId", dish.getDishId());
						saveflag = (this.executiveSql(sb.toString(), params) == 1);
					}
				}
			}
		}
		if (saveflag) {
			if (o.getDishes() != null && o.getDishes().size() > 0) {
				String part1 = "insert into T_CATER_ORDERDISHES (id, orderid, dishid, dishnumber, dishtype, unitprice) "
						+ "select SEQ_CATER_ORDERDISHES.Nextval, '" + o.getOrderId() + "', d.dishid, '";
				String part2 = "', '0', d.unitprice from T_CATER_DISH d where d.dishId = :dishId ";
				
				//with diy guodi
				String part3 = "insert into T_CATER_ORDERDISHES (id, orderid, dishid, dishnumber, dishtype, unitprice, guodiid, detail) "
						+ "select SEQ_CATER_ORDERDISHES.Nextval, '" + o.getOrderId() + "', g.main_guodiid, '";
				String part4 = "', '0', case when (d2.unitprice is null) or (d1.unitprice >= d2.unitprice) then d1.unitprice else d2.unitprice end, "
						+ "g.id, g.detail from T_CATER_GUODIDIY g "
						+ "inner join T_CATER_DISH d1 on g.main_guodiid = d1.dishid "
						+ "left outer join T_CATER_DISH d2 on g.assist_guodiid = d2.dishid where g.id = :guodiId ";

				Map<String, Object> params = new HashMap<String, Object>();
				for (OrderDish dish : o.getDishes()) {
					sb.setLength(0);
					params.clear();
					if (StringTools.isEmpty(dish.getGuodiId())) {
						sb.append(part1).append(dish.getDishNumber()).append(part2);
						params.put("dishId", dish.getDishId());
					} else {//with diy guodi
						sb.append(part3).append(dish.getDishNumber()).append(part4);
						params.put("guodiId", dish.getGuodiId());
					}
					saveflag = (this.executiveSql(sb.toString(), params) == 1);
				}
			}
		}
		return saveflag;
	}
}
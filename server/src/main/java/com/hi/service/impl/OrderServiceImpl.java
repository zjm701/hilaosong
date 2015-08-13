package com.hi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.common.OrderDeliveryType;
import com.hi.common.OrderPayStatus;
import com.hi.common.OrderSrc;
import com.hi.common.OrderStatus;
import com.hi.common.OrderType;
import com.hi.dao.OrderDao;
import com.hi.dao.StoreDao;
import com.hi.model.Order;
import com.hi.model.OrderAddress;
import com.hi.model.OrderExpenses;
import com.hi.model.Store;
import com.hi.service.OrderService;
import com.hi.tools.CalendarTools;
import com.hi.tools.StringTools;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao odao;
	
	@Autowired
	private StoreDao sdao;
	
	@Override
	public OrderAddress getLatestAddress(String userId) {
		return odao.getLatestAddress(userId);
	}

	@Override
	public int countHistoryOrders(String userId){
		return odao.countHistoryOrders(userId);
	}
	
	@Override
	public List<Order> getHistoryOrders(String userId, int pageIndex) {
		return odao.getHistoryOrders(userId, pageIndex);
	}

	@Override
	public Order getOrderInfo(String orderId) {
		return odao.getOrderInfo(orderId);
	}

	@Override
	public String createOrder(Order order) {
		order.setSerialId(generateSerialId(odao.getOrderSerialId()));
		order.setOrderId(generateOrderId(order.getStoreId(), order.getSerialId()));
		order.setStatus(OrderStatus.PENDING_APPROVE.getKey());
		order.setPayStatus(OrderPayStatus.WAITING_PAY.getKey());
		order.setOrderSrc(OrderSrc.WEBSITE.getKey());
		if (OrderType.SEND_OUT.getKey().equals(order.getOrderType())) {
			order.setDeliveryType(OrderDeliveryType.SEND_OUT.getKey());
		} else if (OrderType.TAKE_AWAY.getKey().equals(order.getOrderType())) {
			order.setDeliveryType(OrderDeliveryType.ARRIVE_STORE.getKey());
		}
		if (order.getPotNumber() != null && order.getPotNumber().intValue() >= 1) {
			order.setPotStatus("1");
		} else {
			order.setPotStatus("0");
		}

		OrderAddress a = order.getAddress();
		if(a != null){
			a.setOrderId(order.getOrderId());
			a.setCustomerPhone(order.getContactPhone());
		}

		OrderExpenses e = order.getExpenses();
		e.setOrderId(order.getOrderId());
		if (StringTools.isEmpty(e.getWaiterFee())) {
			e.setWaiterFee("0");
		}
		if (StringTools.isEmpty(e.getDeliveryFee())){
			e.setDeliveryFee("0");
		}
		
		if(odao.createOrder(order)){
			return order.getOrderId();
		}else{
			return null;
		}
	}
	
	private String generateSerialId(long serialId) {
		String sid = serialId + "";

		int length = sid.length();
		switch (length) {
		case 1:
			sid = "0000" + sid;
			break;
		case 2:
			sid = "000" + sid;
			break;
		case 3:
			sid = "00" + sid;
			break;
		case 4:
			sid = "0" + sid;
			break;
		default:
			break;
		}
		return CalendarTools.nowString("yyyyMMdd") + sid;
	}
	
	/**
	 * 准备订单号
	 * 
	 * @param storeId
	 * @param serialId
	 * @return
	 */
	private String generateOrderId(String storeId, String serialId) {
		String orderId = "";
		Store store = sdao.getStore(storeId);
		if (store != null) {
			if (store.getStoreCode() == null) {
				orderId = "ZABCD" + store.getStoreId() + serialId;
			} else {
				orderId = OrderSrc.WEBSITE.getKey() + store.getStoreCode() + serialId;
			}
		} else {
			orderId = "ZABCD01" + serialId;// 临时虚假数据
		}
		return orderId;
	}
}

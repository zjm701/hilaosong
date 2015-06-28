package com.hi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.common.OrderPayStatus;
import com.hi.common.OrderSrc;
import com.hi.common.OrderStatus;
import com.hi.dao.OrderDao;
import com.hi.dao.StoreDao;
import com.hi.model.Order;
import com.hi.model.OrderAddress;
import com.hi.model.Store;
import com.hi.service.OrderService;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	public static final String ORDER_SRC = "W"; //W-网站

	@Autowired
	private OrderDao odao;
	
	@Autowired
	private StoreDao sdao;

	@Override
	public OrderAddress getLatestAddress(String userId) {
		return odao.getLatestAddress(userId);
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
	public String createOrder(Order order){
		order.setSerialId(odao.getOrderSerialId()+"");
		order.setOrderId(generateOrderId(order.getStoreId(), order.getSerialId()));
		order.setStatus(OrderStatus.PENDING_APPROVE.getKey());
		order.setPayStatus(OrderPayStatus.WAITING_PAY.getKey());
		order.setOrderSrc(OrderSrc.WEBSITE.getKey());
		if (order.getPotNumber() != null && order.getPotNumber().intValue() >= 1) {
			order.setPotStatus("1");
		} else {
			order.setPotStatus("0");
		}

		order.getAddress().setCustomerPhone(order.getContactPhone());
		order.getAddress().setCityId(order.getAddress().getCityId());
		order.getAddress().setRegionId(order.getAddress().getRegionId());
		order.getAddress().setDetailAddress(order.getAddress().getDetailAddress());
		order.getAddress().setVillage(order.getAddress().getVillage());
		
		order.getExpenses().setDeliveryFee(order.getExpenses().getDeliveryFee());
		
		if(odao.createOrder(order)){
			return order.getOrderId();
		}else{
			return null;
		}
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
				orderId = ORDER_SRC + store.getStoreCode() + serialId;
			}
		} else {
			orderId = "ZABCD01" + serialId;// 临时虚假数据
		}
		return orderId;
	}
}

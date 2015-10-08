package com.hi.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
	public int countHistoryOrders(String userId) {
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
		if (a != null) {
			a.setOrderId(order.getOrderId());
			a.setCustomerPhone(order.getContactPhone());
		}

		OrderExpenses e = order.getExpenses();
		e.setOrderId(order.getOrderId());
		if (StringTools.isEmpty(e.getWaiterFee())) {
			e.setWaiterFee("0");
		}
		if (StringTools.isEmpty(e.getDeliveryFee())) {
			e.setDeliveryFee("0");
		}

		if (odao.createOrder(order)) {
			return order.getOrderId();
		} else {
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
				orderId = serialId;
			} else {
				orderId = store.getStoreCode() + serialId;
			}
		} else {
			orderId = "ABCD01" + serialId;// 临时虚假数据
		}
		orderId = OrderSrc.WEBSITE.getKey() + orderId;
		return orderId;
	}

	/**
	 * 银联回调跟新订单支付状态
	 */
	@Override
	public boolean unionBackUpdateOrderPayStatus(String orderId,int payStatus) {
		return odao.updateOrderPayStatus(orderId,payStatus);
	}
	
	
	/**
	 * 检查订单知否可以支付
	 * @param orderId
	 * @return
	 */
	@Override
	public Map<String, Object> checkOrderIsCanPay(String orderId,Integer txAmt){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//检查订单号
			if(!StringUtils.hasLength(orderId) || null==txAmt || txAmt.compareTo(new Integer(0))==0){
				map.put("resultCode", 0);
				map.put("resultMsg", "订单号或交易金额缺省!");
				return map;
			}
			//检查订单
			Order order = this.getOrderInfo(orderId);
			if(null == order){
				map.put("resultCode", 0);
				map.put("resultMsg", "订单不存在!");
				return map;
			}
			//检查订单是否已经支付
			if("1".equals(order.getPayStatus())){
				map.put("resultCode", 0);
				map.put("resultMsg", "订单已支付完成!");
				return map;
			}
			//判断订单价格是否匹配
			if(null == order.getExpenses() || null == order.getExpenses().getTotalPrice() || order.getExpenses().getTotalPrice().compareTo(new BigDecimal(txAmt/100)) != 0){
				map.put("resultCode", 0);
				map.put("resultMsg", "订单价格存在错误!");
				return map;
			}
			map.put("resultCode", 1);
			//订单价格
			map.put("orderTolPrice", order.getExpenses().getTotalPrice().intValue()*100);
			//下单时间
			String orderCreateTime = order.getCreatedDt();
			orderCreateTime = orderCreateTime.replace(" ", "");
			orderCreateTime = orderCreateTime.replace(":", "");
			orderCreateTime = orderCreateTime.replace("-", "");
			map.put("orderCreateTime", orderCreateTime);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resultCode", 0);
			map.put("resultMsg", "操作错误!");
			return map;
		}
	}
	
}

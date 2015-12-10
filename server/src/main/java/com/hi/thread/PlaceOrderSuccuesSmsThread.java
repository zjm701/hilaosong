package com.hi.thread;


import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.hi.common.SystemSetting;
import com.hi.common.provider.SmsProvider;
import com.hi.model.Order;

/**
 * 下订单成功发短信线程
 * @author 蒋先彪
 *
 */
public class PlaceOrderSuccuesSmsThread implements Runnable {

	private Logger log = LoggerFactory.getLogger(PlaceOrderSuccuesSmsThread.class);
	
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 送餐到达时间
	 */
	private String dinningTime;
	/**
	 * 菜品总价
	 */
	private String dishPrice;
	/**
	 * 外送费
	 */
	private String deliveryFee;
	/**
	 * 服务费
	 */
	private String waiterFee;
	/**
	 * 订单总价
	 */
	private String totalPrice; 
	/**
	 * 顾客手机号
	 */
	private String phone;
	
	public PlaceOrderSuccuesSmsThread(Order order){
		if(null == order) return;
		this.phone = order.getContactPhone();
		this.orderId = order.getOrderId();
		this.dinningTime = !StringUtils.hasLength(order.getDinningTime()) ? "0000-00-00 00:00:00" :order.getDinningTime();
		this.dishPrice = (order.getExpenses()==null || order.getExpenses().getDishPrice()==null) ? "0.0" : order.getExpenses().getDishPrice().doubleValue()+"";
		this.totalPrice = (order.getExpenses()==null || order.getExpenses().getTotalPrice()==null) ? "0.0" : order.getExpenses().getTotalPrice().doubleValue()+"";
		this.deliveryFee = (order.getExpenses()==null || StringUtils.hasLength(order.getExpenses().getDeliveryFee()))? "0.0" :order.getExpenses().getDeliveryFee();
		this.waiterFee = (order.getExpenses()==null || StringUtils.hasLength(order.getExpenses().getWaiterFee()))? "0.0" :order.getExpenses().getWaiterFee();
	}
	
	/**
	 * 订单成功后下发短信给顾客
	 */
	@Override
	public void run() {
		try {
			//发送短信，提醒顾客下单成功
			String smsContent = SystemSetting.getPlaceOrderSuccuesSmsContent();
			smsContent = MessageFormat.format(smsContent, orderId,dinningTime,dishPrice,deliveryFee,waiterFee,totalPrice);
			SmsProvider.sendSms(smsContent, phone);
			log.info("发短信提醒顾客下单成功！订单号为:"+orderId+" 手机号===>["+phone+"] 短信内容====>["+smsContent+"]");
		} catch (Exception e) {
			log.info("发短信提醒顾客下单失败！订单号为:"+orderId+" 失败信息===>"+e.getMessage());
		}
		
	}

}

package com.hi.common.unionpay;

import java.text.MessageFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hi.model.Order;
import com.hi.service.OrderService;
import com.hi.service.UnionPayOrderService;
import com.hi.tools.SpringBeanUtil;

/**
 * 处理银联小概率事件
 * @author 蒋先彪
 *
 */
public class UnionHandleSmallChanceThread implements Runnable {

	private static Logger log = LoggerFactory.getLogger(UnionHandleSmallChanceThread.class);
	
	/**
	 * 订单 service
	 */
	private OrderService orderService;
	/**
	 * 银联回调订单 service
	 */
	private UnionPayOrderService unionPayOrderService;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 订单支付交易时间
	 */
	private String txTime;
	
	/**
	 * 等待银联回到后 睡眠时间
	 */
	private long sleepTime = 5*60*1000;
	
	public UnionHandleSmallChanceThread(String orderId,String txTime){
		this.orderId = orderId;
		this.txTime = txTime;
		orderService = (OrderService)SpringBeanUtil.getApplicationContext().getBean("orderService");
		unionPayOrderService = (UnionPayOrderService)SpringBeanUtil.getApplicationContext().getBean("unionPayOrderServiceImpl");
	}
	
	/**
	 * 处理业务
	 */
	@Override
	public void run() {
		try {
			//睡眠十分钟
			Thread.sleep(sleepTime);
			//查询订单
			Order order = orderService.getOrderInfo(orderId);
			//订单存在并且支付状态 为 未支付状态
			if(null != order && "0".equals(order.getPayStatus())){
				//查询银联支付状态
				Map<String, String> resultMap = UnionPayProvider.queryOrderUnionPayStatus(orderId, txTime);
				if(null != resultMap && "00".equals(resultMap.get("origRespCode"))){
					//由于异常原因，银联支付成功，但回调失败（手动改变订单支付状态和记录回调信息）
					unionPayOrderService.unionPaySmallHandle(resultMap, true);
					log.info(MessageFormat.format("银联处理小概率事件:银联支付成功，但回调失败，所以订单:{0}启动小概率处理!", orderId));
				}else{//订单没有经过银联支付 (记录下订单没有支付操作)
					unionPayOrderService.unionPaySmallHandle(resultMap, false);
					log.info(MessageFormat.format("银联处理小概率事件:订单:{0}未完成支付操作!", orderId));
				}
			}else{
				log.info(MessageFormat.format("银联处理小概率事件:订单:{0}银联已经回调成功!!", orderId));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}

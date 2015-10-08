package com.hi.control;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hi.common.unionpay.UnionHandleSmallChanceThread;
import com.hi.common.unionpay.UnionPayBase;
import com.hi.common.unionpay.UnionPayProvider;
import com.hi.model.Order;
import com.hi.model.UnionPayOrder;
import com.hi.service.OrderService;
import com.hi.service.UnionPayOrderService;
import com.hi.tools.CalendarTools;

/**
 * 银联支付 control
 * @author 蒋先彪
 *
 */
@Controller
@RequestMapping("/pay")
public class UnionPayAction {

	private final Logger log = LoggerFactory.getLogger(UnionPayAction.class);
	
	/**
	 * 订单服务
	 */
	@Autowired
	private OrderService orderService;
	
	/**
	 * 银联支付订单服务
	 */
	@Autowired
	private UnionPayOrderService unionPayOrderService;
	
	
	/**
	 * 订单预支付
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/prePayOrder")
	public @ResponseBody Map<String, Object> prePayOrder(String orderId,Integer txAmt,HttpServletRequest request){
		return orderService.checkOrderIsCanPay(orderId, txAmt);
	}
	
	
	/**
	 * 请求银联支付页面
	 * @param content
	 * @param resp
	 */
	@RequestMapping("/unionPay")
	public void toUnionPay(String orderId,Integer txAmt,HttpServletResponse resp){
		try {
			//校验知否可以支付,不成功则拦截
			Map<String, Object> resultMap = orderService.checkOrderIsCanPay(orderId, txAmt);
			if("0".equals(resultMap.get("resultCode").toString())){
				String interceptHtml = UnionPayBase.interceptBackHtml(resultMap.get("resultMsg").toString());
				resp.getWriter().write(interceptHtml);
				resp.getWriter().close();
				return;
			}
			//请求银联支付服务器
			Integer orderPrice = Integer.valueOf(resultMap.get("orderTolPrice").toString());
			String orderCreateTime = resultMap.get("orderCreateTime").toString();
			String payHtml = UnionPayProvider.getUnionPayHtml(orderPrice,orderId,orderCreateTime);
			//跳转到银联支付页面
			resp.getWriter().write(payHtml);
			resp.getWriter().close();
			//起线程监听支付状态。防止小概率支付失败
			new Thread(new UnionHandleSmallChanceThread(orderId, orderCreateTime)).start();
			
		} catch (Exception e) {
			log.info("请求银联支付页面失败!");
		}
	}
	
	/**
	 * 银联后台回调函数
	 */
	@RequestMapping("/backSuccessUnionPay")
	public void backSuccessUnionPay(HttpServletRequest request){
		try {
			//获取银联返回参数
			Map<String, String> unionBackParam = UnionPayBase.getBackAllRequestParam(request);
			if(null == unionBackParam){
				log.info("银联回调:应答报文缺省!");
				return;
			}
			//用银联sdkUtil校验返回参数,防止别人调用
			boolean checkFlag = UnionPayBase.validateBackAllRequestParam(unionBackParam);
			if(!checkFlag){
				log.info("银联回调:应答报文格式不正确!");
				return;
			}
			//查询订单消息
			String orderId = unionBackParam.get("orderId").toString();
			Order order = orderService.getOrderInfo(orderId);
			if(null == order){
				log.info("银联回调:订单不存在!");
				return;
			}
			//判断是否已经支付
			if("1".equals(order.getPayStatus())){
				log.info("银联回调:订单已经支付!");
				return;
			}
			//获取银联支付支付状态
			String respCode = unionBackParam.get("respCode").toString();
			if("00".equals(respCode)){
				log.info(MessageFormat.format("银联回调:订单{0}支付成功!", orderId));
			}else{
				log.info(MessageFormat.format("银联回调:订单{0}支付失败!", orderId));
			}
			//处理订单支付状态(根据银联支付返回的支付结果) , 记录银联支付返回信息
			unionPayOrderService.unionPayBackHandle(unionBackParam);
		} catch (Exception e) {
			log.info("银联回调操作失败!");
		}
	}
	
	@RequestMapping("/testOrderTime")
	public void test(){
		Order order = orderService.getOrderInfo("WBJ132015092402938");
		String newTime = CalendarTools.date2TimeStr(CalendarTools.timeStr2Date(order.getCreatedDt(), CalendarTools.DATATIME_FILE), CalendarTools.DATATIME_FILE);
		System.out.println("时间====>"+newTime);
	}
	
	
}

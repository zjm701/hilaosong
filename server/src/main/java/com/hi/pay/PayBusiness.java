package com.hi.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hi.pay.models.TCaterPayChannel;
import com.hi.pay.models.TCaterThirdmerchantinfo;
import com.hi.pay.models.TCaterThirdorderinfo;

public interface PayBusiness {

	/**
	 * 得到web支付链接
	 * 
	 * @param tCaterPayChannel
	 * @param orderId
	 * @param orderAmount
	 * @param request
	 * @param response
	 * @return
	 */
	public String getWebPayUrl(TCaterThirdmerchantinfo tCaterThirdmerchantinfo, String orderId, int orderAmount,
			HttpServletRequest request, HttpServletResponse response);

	/**
	 * 网页版回调通知
	 * 
	 * @param tCaterPayChannel
	 * @param request
	 * @param response
	 * @return
	 */
	public String getWebPayResult(TCaterPayChannel tCaterPayChannel, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 退款接口
	 * 
	 * @param tCaterPayChannel
	 * @param tCaterThirdorderinfo
	 * @return
	 */

	public String getRefund(TCaterPayChannel tCaterPayChannel, TCaterThirdorderinfo tCaterThirdorderinfo);

	/**
	 * 得到native支付参数（支付宝快捷支付）
	 * 
	 * @param tCaterPayChannel
	 * @param orderId
	 * @param orderAmount
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, String> getNativePayUrl(TCaterThirdmerchantinfo tCaterThirdmerchantinfo, String orderId, int orderAmount,
			HttpServletRequest request, HttpServletResponse response);

	/**
	 * 快捷支付异步通知
	 * 
	 * @param tCaterPayChannel
	 * @param request
	 * @param response
	 * @return
	 */
	public String getNativePayResult(TCaterPayChannel tCaterPayChannel, HttpServletRequest request, HttpServletResponse response);
}

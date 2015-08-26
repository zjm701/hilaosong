package com.hi.pay.forms;

import com.hi.tools.StringTools;

public class RefundForm {

	private String orderNo;
	private String orderAmount;

	private String respMsg;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	/**
	 * 验证支付初始化参数
	 * 
	 * @return
	 */
	public String validateRefund() {

		if (StringTools.isEmpty(orderNo)) {
			respMsg = "订单号不能为空！";
		} else if (orderNo.length() > 32) {
			respMsg = "订单号长度不能超过32位！";
		}

		return respMsg;
	}
}

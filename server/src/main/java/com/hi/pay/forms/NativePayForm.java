package com.hi.pay.forms;

import com.hi.tools.StringTools;

public class NativePayForm {

	private String orderNo;
	private String orderAmount;
	private String channelNo;
	private String desc;

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

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
	public String validateWapPayInit() {

		if (StringTools.isEmpty(orderNo)) {
			respMsg = "订单号不能为空！";
		} else if (orderNo.length() > 32) {
			respMsg = "订单号长度不能超过32位！";
		} else if (StringTools.isEmpty(orderAmount)) {
			respMsg = "订单金额不能为空！";
		} else if (!StringTools.isDouble(orderAmount)) {
			respMsg = "订单金额不正确！";
		} else if (StringTools.isEmpty(channelNo)) {
			respMsg = "支付渠道参数不能为空！";
		}

		return respMsg;
	}
}

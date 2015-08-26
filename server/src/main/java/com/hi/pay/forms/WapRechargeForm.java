package com.hi.pay.forms;

import com.hi.tools.StringTools;

public class WapRechargeForm {

	private String orderNo;

	private String channelNo;

	private String orderAmount;

	private String openId;

	private String backUrl;

	private String desc;

	private int clientType;

	private String respMsg;

	public WapRechargeForm() {

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

	public String validateWapRechargeForm() {

		if (StringTools.isEmpty(this.orderNo)) {
			respMsg = "订单号不能为空";
		}
		// 充值方式
		if (StringTools.isEmpty(this.channelNo)) {
			respMsg = "充值方式不能为空";
		}
		// 充值金额
		if (StringTools.isEmpty(this.orderAmount)) {
			respMsg = "充值金额不能为空";
		}

		return respMsg;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

}

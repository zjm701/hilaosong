package com.hi.pay.forms;

import com.hi.tools.StringTools;

public class WapPayForm {

	private String orderNo;
	private String orderAmount;
	private String channelNo;
	private String desc;
	private int clientType;
	// 2015-5-30
	private String customerKey;// 用户key

	private String redEnvelopeIds;// 红包编码集

	private String redAmount;// 优惠金额

	private String operateType;
	private String openId;

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

	/**
	 * @return the clientType
	 */
	public int getClientType() {
		return clientType;
	}

	/**
	 * @param clientType
	 *            the clientType to set
	 */
	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getRedEnvelopeIds() {
		return redEnvelopeIds;
	}

	public void setRedEnvelopeIds(String redEnvelopeIds) {
		this.redEnvelopeIds = redEnvelopeIds;
	}

	public String getRedAmount() {
		return redAmount;
	}

	public void setRedAmount(String redAmount) {
		this.redAmount = redAmount;
	}
}

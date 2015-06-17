package com.hi.model;

import java.util.Date;

/**
 * from OrderMainInfo
 * 
 * @author jimmy.zhang
 * 
 */
public class Order {

	/**
	 * 订单ID,流水号
	 */
	private String serialId;

	/**
	 * 规则订单号
	 */
	private String orderId;

	/**
	 * 客户ID
	 */
	private String customerId;

	/**
	 * 门店ID
	 */
	private String storeId;

	/**
	 * 联系人姓名
	 */
	private String contactName;

	/**
	 * 联系人电话
	 */
	private String contactPhone;

	/**
	 * 订单状态
	 */
	private String status;

	/**
	 * 支付状态
	 */
	private String payStatus;

	/**
	 * 订餐类型,0-外卖;1-订桌;2-外带3-排号;
	 */
	private String orderType;
	
	/**
	 * 
	 */
	private Date created_dt;

	/**
	 * 外送种类,0-外送 1-自助 2-豪华 3-上门自取
	 */
	private String deliveryType;

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Date getCreated_dt() {
		return created_dt;
	}

	public void setCreated_dt(Date created_dt) {
		this.created_dt = created_dt;
	}
}

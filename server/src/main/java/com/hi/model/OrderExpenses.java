package com.hi.model;

import java.math.BigDecimal;


/**
 * [简要描述]:订单费用信息
 * 
 */
public class OrderExpenses {

	/**
	 * 编号
	 */
	private BigDecimal expensesId;

	/**
	 * 规则订单号
	 */
	private String orderId;

	/**
	 * 炉具押金
	 */
	private String deposit;

	/**
	 * 服务员费用
	 */
	private String waiterFee;

	/**
	 * 送餐费用
	 */
	private String deliveryFee;
	
	/**
	 * 炉锅 押金
	 */
	private BigDecimal depositFee;

	/**
	 * 送餐费用备注（表示选择指定费用时必填的备注信息）
	 */
	private String feeMemo;

	/**
	 * 菜品总价
	 */
	private BigDecimal dishPrice;

	/**
	 * 订单总价
	 */
	private BigDecimal totalPrice;

	/**
	 * 公里数
	 */
	private String kilometre;

	/**
	 * 门店名称 jgxh
	 */
	private String storeName;

	/**
	 * 结账时间 jzsj
	 */
	private String payTime;

	/**
	 * 天子星账号编号 zdbh
	 */
	private String tzxNo;

	/**
	 * 结账状态【1已结】 jzzt
	 */
	private String payStatus;

	public BigDecimal getExpensesId() {
		return expensesId;
	}

	public void setExpensesId(BigDecimal expensesId) {
		this.expensesId = expensesId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getWaiterFee() {
		return waiterFee;
	}

	public void setWaiterFee(String waiterFee) {
		this.waiterFee = waiterFee;
	}

	public String getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public BigDecimal getDepositFee() {
		return depositFee;
	}

	public void setDepositFee(BigDecimal depositFee) {
		this.depositFee = depositFee;
	}

	public String getFeeMemo() {
		return feeMemo;
	}

	public void setFeeMemo(String feeMemo) {
		this.feeMemo = feeMemo;
	}

	public BigDecimal getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(BigDecimal dishPrice) {
		this.dishPrice = dishPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getKilometre() {
		return kilometre;
	}

	public void setKilometre(String kilometre) {
		this.kilometre = kilometre;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getTzxNo() {
		return tzxNo;
	}

	public void setTzxNo(String tzxNo) {
		this.tzxNo = tzxNo;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
}

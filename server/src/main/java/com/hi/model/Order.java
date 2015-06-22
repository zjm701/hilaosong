package com.hi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * from OrderMainInfo
 * 
 * @author jimmy.zhang
 * 
 */
public class Order extends Pagenation {

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
	 * 门店Name
	 */
	private String storeName;

	/**
	 * 联系人姓名
	 */
	private String contactName;

	/**
	 * 联系人电话
	 */
	private String contactPhone;

	/**
	 * 用餐时间(外送的送餐时间、预订的到店时间、餐带的取餐时间)
	 */
	private String dinningTime;

	/**
	 * 订单状态
	 */
	private String status;

	/**
	 * 炉具套数
	 */
	private BigDecimal potNumber;

	/**
	 * 炉具回收状态0-无锅；1-有锅，未收；2-有锅，已收
	 */
	private String potStatus;

	/**
	 * 订餐类型,0-外卖;1-订桌;2-外带3-排号;
	 */
	private String orderType;

	/**
	 * 外送种类,0-外送 1-自助 2-豪华 3-上门自取
	 */
	private String deliveryType;

	/**
	 * 发票抬头
	 */
	private String recieptDept;

	/**
	 * 付款方式
	 */
	private String payChannel;
	/**
	 * 客户备注
	 */
	private String custMemo;

	/**
	 * 订单来源:W-网站；Z-坐席；M-门店；S-手机
	 */
	private String orderSrc;

	/**
	 * 订单性质的类别：0－尽快; 1-准时; 2-普通
	 */
	private String orderNature;

	/**
	 * 支付状态
	 */
	private String payStatus;

	/**
	 * 
	 */
	private String createdDt;

	/**
	 * 送餐地址
	 */
	private OrderAddress address = new OrderAddress();

	private OrderExpenses expenses = new OrderExpenses();

	private List<OrderPack> packs = new ArrayList<OrderPack>();

	private List<OrderDish> dishes = new ArrayList<OrderDish>();

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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public String getDinningTime() {
		return dinningTime;
	}

	public void setDinningTime(String dinningTime) {
		this.dinningTime = dinningTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getPotNumber() {
		return potNumber;
	}

	public void setPotNumber(BigDecimal potNumber) {
		this.potNumber = potNumber;
	}

	public String getPotStatus() {
		return potStatus;
	}

	public void setPotStatus(String potStatus) {
		this.potStatus = potStatus;
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

	public String getRecieptDept() {
		return recieptDept;
	}

	public void setRecieptDept(String recieptDept) {
		this.recieptDept = recieptDept;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getCustMemo() {
		return custMemo;
	}

	public void setCustMemo(String custMemo) {
		this.custMemo = custMemo;
	}

	public String getOrderSrc() {
		return orderSrc;
	}

	public void setOrderSrc(String orderSrc) {
		this.orderSrc = orderSrc;
	}

	public String getOrderNature() {
		return orderNature;
	}

	public void setOrderNature(String orderNature) {
		this.orderNature = orderNature;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}

	public OrderAddress getAddress() {
		return address;
	}

	public void setAddress(OrderAddress address) {
		this.address = address;
	}

	public OrderExpenses getExpenses() {
		return expenses;
	}

	public void setExpenses(OrderExpenses expenses) {
		this.expenses = expenses;
	}

	public List<OrderPack> getPacks() {
		return packs;
	}

	public void setPacks(List<OrderPack> packs) {
		this.packs = packs;
	}

	public void addPack(OrderPack pack) {
		this.packs.add(pack);
	}

	public OrderPack getPack(String packId) {
		if (this.packs != null && this.packs.size() > 0) {
			for (OrderPack pack : this.packs) {
				if (pack.getPackId().equals(packId)) {
					return pack;
				}
			}
		}
		return null;
	}

	public List<OrderDish> getDishes() {
		return dishes;
	}

	public void setDishes(List<OrderDish> dishes) {
		this.dishes = dishes;
	}

	public void addDish(OrderDish dish) {
		this.dishes.add(dish);
	}
}

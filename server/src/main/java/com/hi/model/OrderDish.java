package com.hi.model;

import java.math.BigDecimal;

/**
 * [简要描述]:订单菜品信息表
 * 
 */
public class OrderDish {

	/**
	 * 菜品ID
	 */
	private String dishId;

	/**
	 * 菜品名
	 */
	private String dishName;

	/**
	 * 销售单价
	 */
	private String unitPrice;

	/**
	 * 菜品数量
	 */
	private BigDecimal dishNumber;
	
	/**
	 * DIY锅底
	 */
	private String guodiId;

	/**
	 * 菜品类别， 0-菜品; 1-套餐; 2-赠品
	 */
	private String dishType;

	public String getDishId() {
		return dishId;
	}

	public void setDishId(String dishId) {
		this.dishId = dishId;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getDishNumber() {
		return dishNumber;
	}

	public void setDishNumber(BigDecimal dishNumber) {
		this.dishNumber = dishNumber;
	}

	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
	}

	public String getGuodiId() {
		return guodiId;
	}

	public void setGuodiId(String guodiId) {
		this.guodiId = guodiId;
	}
}

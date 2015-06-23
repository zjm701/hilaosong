package com.hi.model;

public class DishVO extends Pagenation {
	private String dishId;

	private String storeDishId;

	private String storeDishName;

	private String unitPrice;

	private String bigImageAddr;

	// 1: normal dish, 2: package dish
	private String type;

	private String halfStoreDishId;

	// if this dish has half, then set the price of half dish
	private String halfPrice;

	public String getDishId() {
		return dishId;
	}

	public void setDishId(String dishId) {
		this.dishId = dishId;
	}

	public String getStoreDishId() {
		return storeDishId;
	}

	public void setStoreDishId(String storeDishId) {
		this.storeDishId = storeDishId;
	}

	public String getStoreDishName() {
		return storeDishName;
	}

	public void setStoreDishName(String storeDishName) {
		this.storeDishName = storeDishName;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getBigImageAddr() {
		return bigImageAddr;
	}

	public void setBigImageAddr(String bigImageAddr) {
		this.bigImageAddr = bigImageAddr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHalfStoreDishId() {
		return halfStoreDishId;
	}

	public void setHalfStoreDishId(String halfStoreDishId) {
		this.halfStoreDishId = halfStoreDishId;
	}

	public String getHalfPrice() {
		return halfPrice;
	}

	public void setHalfPrice(String halfPrice) {
		this.halfPrice = halfPrice;
	}

}

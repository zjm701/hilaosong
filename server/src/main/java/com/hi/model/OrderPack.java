package com.hi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * [简要描述]:订单套餐信息表
 * 
 */
public class OrderPack {

	/**
	 * 套餐编号
	 */
	private String packId;

	/**
	 * 套餐名称 DishName
	 */
	private String packName;

	/**
	 * 套餐价格 UnitPrice
	 */
	private String packPrice;

	/**
	 * 套餐份数
	 */
	private String packCount;
	
	private List<OrderPackDish> dishes = new ArrayList<OrderPackDish>();

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public String getPackPrice() {
		return packPrice;
	}

	public void setPackPrice(String packPrice) {
		this.packPrice = packPrice;
	}

	public String getPackCount() {
		return packCount;
	}

	public void setPackCount(String packCount) {
		this.packCount = packCount;
	}

	public List<OrderPackDish> getDishes() {
		return dishes;
	}

	public void setDishes(List<OrderPackDish> dishes) {
		this.dishes = dishes;
	}

	public void addDish(OrderPackDish dish){
		this.dishes.add(dish);
	}
}

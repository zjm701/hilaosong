package com.hi.model;

/**
 * [简要描述]:订单套餐中的菜品信息表
 * 
 */
public class OrderPackDish extends OrderDish{

	/**
	 * 套餐编号
	 */
	private String packId;
	
	/**
	 * 套餐组编号
	 */
	private String innerId;

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public String getInnerId() {
		return innerId;
	}

	public void setInnerId(String innerId) {
		this.innerId = innerId;
	}
}

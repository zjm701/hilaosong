package com.hi.model;

import java.math.BigDecimal;

/**
 * [简要描述]:DIY锅底
 * 
 */
public class DiyGuodi extends Dish {

	private BigDecimal guodiId;

	private String guodiName;

	private String dishId2;

	private String userId;

	public BigDecimal getGuodiId() {
		return guodiId;
	}

	public void setGuodiId(BigDecimal guodiId) {
		this.guodiId = guodiId;
	}

	public String getGuodiName() {
		return guodiName;
	}

	public void setGuodiName(String guodiName) {
		this.guodiName = guodiName;
	}

	public String getDishId2() {
		return dishId2;
	}

	public void setDishId2(String dishId2) {
		this.dishId2 = dishId2;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
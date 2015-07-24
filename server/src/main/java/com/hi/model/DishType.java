package com.hi.model;

public class DishType {
	private String dishTypeId;

	private String dishTypeName;

	private String parentId;

	private String isRequired;

	public DishType() {
	}
	
	public DishType(String dishTypeId, String dishTypeName) {
		super();
		this.dishTypeId = dishTypeId;
		this.dishTypeName = dishTypeName;
	}

	public String getDishTypeId() {
		return dishTypeId;
	}

	public void setDishTypeId(String dishTypeId) {
		this.dishTypeId = dishTypeId;
	}

	public String getDishTypeName() {
		return dishTypeName;
	}

	public void setDishTypeName(String dishTypeName) {
		this.dishTypeName = dishTypeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

}

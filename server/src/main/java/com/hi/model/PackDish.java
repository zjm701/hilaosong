package com.hi.model;

import java.math.BigDecimal;

public class PackDish {

	/**
	 * 主键
	 */
	private Long packDishId;

	/**
	 * 套餐id
	 */
	private String packId;

	/**
	 * 菜品id
	 */
	private String dishId;

	/**
	 * 套餐内此菜品数量 总份数
	 */
	private BigDecimal dishNumber;

	/**
	 * 所选菜品+数量，进行一个编号
	 */
	private String innerId;

	/**
	 * 该编号最多能出现几次 --类型ID 页面： 本组菜最多可选几种
	 */
	private BigDecimal innerNumber;

	/**
	 * 同一编号的记录编为一组，提供组名
	 */
	private String innerName;

	/**
	 * 菜品的名称，不映射到数据库字段
	 */
	private String dishName;

	/**
	 * 菜品销售单价
	 */
	private String unitPrice;

	private String bigImageAddr;

	private String mediumImageAddr;

	// /**
	// *
	// * <DishID>13068</DishID><!--菜品ID-->
	// * <DishNumber>5.0000</DishNumber><!--菜品数量-->
	// * <PackNumber>6</PackNumber><!--套餐可选择数-->
	// * <Price>60</Price><!--菜品销售单价-->
	// * <Type>1</Type><!-- 1为单品，2为项目组 -->
	// * <ZbNumber>60</ZbNumber><!-- 是 如果为项目组时 ，可选择的数-->
	// */
	// private String zbNumber; // 如果为项目组时 ，可选择的数-
	// private String packNumber; //组别时，可选总数
	// private String type; //1为单品，2为组别
	// private String dishNumberCon;
	//
	// public String getPackNumber() {
	// return packNumber;
	// }
	//
	// public void setPackNumber(String packNumber) {
	// this.packNumber = packNumber;
	// }
	//
	//
	// public String getDisNumberCon(){
	// return dishNumberCon;
	// }
	//
	// public String getType() {
	// return type;
	// }
	//
	// public void setType(String type) {
	// this.type = type;
	// }
	//
	// public String getZbNumber() {
	// return zbNumber;
	// }
	//
	// public void setZbNumber(String zbNumber) {
	// this.zbNumber = zbNumber;
	// }

	private Dish dish;

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getInnerName() {
		return innerName;
	}

	public void setInnerName(String innerName) {
		this.innerName = innerName;
	}

	public Long getPackDishId() {
		return packDishId;
	}

	public void setPackDishId(Long packDishId) {
		this.packDishId = packDishId;
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public String getDishId() {
		return dishId;
	}

	public void setDishId(String dishId) {
		this.dishId = dishId;
	}

	public BigDecimal getDishNumber() {
		return dishNumber;
	}

	public void setDishNumber(BigDecimal dishNumber) {
		this.dishNumber = dishNumber;
	}

	public BigDecimal getInnerNumber() {
		return innerNumber;
	}

	public void setInnerNumber(BigDecimal innerNumber) {
		this.innerNumber = innerNumber;
	}

	public String getInnerId() {
		return innerId;
	}

	public void setInnerId(String innerId) {
		this.innerId = innerId;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public String getBigImageAddr() {
		return bigImageAddr;
	}

	public void setBigImageAddr(String bigImageAddr) {
		this.bigImageAddr = bigImageAddr;
	}

	public String getMediumImageAddr() {
		return mediumImageAddr;
	}

	public void setMediumImageAddr(String mediumImageAddr) {
		this.mediumImageAddr = mediumImageAddr;
	}

}

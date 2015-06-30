package com.hi.model;

import java.math.BigDecimal;

/**
 * [简要描述]:用户订单地址信息
 * 
 */
public class OrderAddress {

	/**
	 * 地址编号ID
	 */
	private BigDecimal addressId;
	
	/**
	 * 规则订单号
	 */
	private String orderId;

	/**
	 * 用户手机号
	 */
	private String customerPhone;

	/**
	 * 用户地址省
	 */
	private String provinceId;

	/**
	 * 用户地址市
	 */
	private String cityId;

	/**
	 * 用户地址区
	 */
	private String regionId;

	/**
	 * 详细地址
	 */
	private String detailAddress;

	/**
	 * 邮编
	 */
	private String postCode;


	/**
	 * 小区/路
	 */
	private String village;

	public BigDecimal getAddressId() {
		return addressId;
	}

	public void setAddressId(BigDecimal addressId) {
		this.addressId = addressId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}
}

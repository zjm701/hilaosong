package com.hi.model;

import java.math.BigDecimal;

public class Store{
	
	/**
	 * 门店Id
	 */
	private String storeId;
	
	/**
	 * 门店名称
	 */
	private String storeName;
	
	/**
	 * 门店地址
	 */
	private String storeAddress;
	
	/**
	 * 门店电话
	 */
	private String storeTele;
	
	/**
	 * 门店描述*
	 */
	private String storeDescription;

	/**
	 * 门店编号
	 */
	private String storeCode;
	
	/**
	 * 门店类型，取值：社区店、旗舰店、商圈店等
	 */
	private String storeType;
	
	/**
	 * 门店所在省/自辖市
	 */
	private String provinceId;
	
	/**
	 * 门店所在市/区
	 */
	private String cityId;

	/**
	 * 邮编
	 */
	private String postCode;
	
	/**
	 * 坐标*
	 */
	private String coordinate;

	/**
	 * 百度全景图iid
	 */
	private String baiduIid;

	/**
	 * 部门类型，0-总部、1-部门、2-分公司、3-片区、4-门店
	 */
	private String deptType;
	
	private String deptId;
	
	private String deptName;
	
	private BigDecimal distance;
	
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

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	public String getStoreTele() {
		return storeTele;
	}

	public void setStoreTele(String storeTele) {
		this.storeTele = storeTele;
	}

	public String getStoreDescription() {
		return storeDescription;
	}

	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getBaiduIid() {
		return baiduIid;
	}

	public void setBaiduIid(String baiduIid) {
		this.baiduIid = baiduIid;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}
}

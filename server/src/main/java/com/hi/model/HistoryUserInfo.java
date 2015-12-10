package com.hi.model;

import java.math.BigDecimal;

/**
 * 用户历史信息
 * @author zhangsheng
 *
 */
public class HistoryUserInfo {
	
	private String id;
	/**
	 * 信息
	 */
	private String info;
	/**
	 * 类型
	 * 1:用户名   2:手机号 3:地址 4:发票
	 */
	private BigDecimal types;
	private String userId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public BigDecimal getTypes() {
		return types;
	}
	public void setTypes(BigDecimal types) {
		this.types = types;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}	

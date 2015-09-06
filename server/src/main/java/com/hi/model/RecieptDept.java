package com.hi.model;

import java.math.BigDecimal;

/**
 * [简要描述]:发票信息
 * 
 */
public class RecieptDept {

	/**
	 * Id
	 */
	private BigDecimal deptId;
	
	/**
	 * 用户Id
	 */
	private String customerId;

	/**
	 * 发票抬头
	 */
	private String department;
	
	/**
	 * 发票类型
	 */
	private String recieptType;

	public BigDecimal getDeptId() {
		return deptId;
	}

	public void setDeptId(BigDecimal deptId) {
		this.deptId = deptId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRecieptType() {
		return recieptType;
	}

	public void setRecieptType(String recieptType) {
		this.recieptType = recieptType;
	}
}

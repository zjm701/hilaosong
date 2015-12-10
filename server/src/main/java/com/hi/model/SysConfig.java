package com.hi.model;

import java.math.BigDecimal;
import java.sql.Clob;

import com.hi.tools.StringTools;

/**
 * [简要描述]:系统参数
 * 
 */
public class SysConfig {

	private BigDecimal id;

	/**
	 * 分类 ：0-门店级、1-片区级、2-系统级
	 */
	private String typeCode;

	/**
	 * 系统配置参数类型代码 .店门ID、片区的ID
	 */
	private String paramType;

	/**
	 * 系统配置参数代码 .
	 */
	private String paramCode;

	/**
	 * 系统配置参数名称 .
	 */
	private String paramName;

    /**
     * 系统配置参数描述 .
     */
    private String paramSrc;
    
	/**
	 * 系统配置参数值/开始
	 */
	private Clob startValue;

	/**
	 * 系统配置参数值/结束
	 */
	private String endValue;

	/**
	 * 参数是否启用（默认0：未启用，1:启用).
	 */
	private String status;
	

	/**
	 * 转换成时间
	 */
	private String startValueS;

	
	public String getStartValueS() {
		return startValueS;
	}


	public void setStartValueS(String startValueS) {
		this.startValueS = StringTools.clobToString(getStartValue());
	}


	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamSrc() {
		return paramSrc;
	}

	public void setParamSrc(String paramSrc) {
		this.paramSrc = paramSrc;
	}

	public Clob getStartValue() {
		return startValue;
	}

	public void setStartValue(Clob startValue) {
		this.startValue = startValue;
	}

	public String getEndValue() {
		return endValue;
	}

	public void setEndValue(String endValue) {
		this.endValue = endValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
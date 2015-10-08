package com.hi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 银联支付回调记录订单
 * @author 蒋先彪
 *
 */
public class UnionPayOrder implements Serializable {

	private Long id;
	//商户号
	private String merId;
	//商户订单号
	private String orderId;
	//银联订单流水后
	private String queryId;
	//支付金额
	private double payAmt;
	//支付账号
	private String accountNo;
	//银联支付返回码
	private String respCode;
	//银联支付返回消息
	private String respMsg;
	//支付时间
	private Date payDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public double getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(double payAmt) {
		this.payAmt = payAmt;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	
}

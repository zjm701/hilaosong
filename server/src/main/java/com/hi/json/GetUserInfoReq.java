package com.hi.json;

import com.hi.tools.annotation.XMLMapping;

/**
 * [简要描述]:请求用户信息
 * 
 */
@XMLMapping("Message")
public class GetUserInfoReq {
	/**
	 * 请求通用对象
	 */
	@XMLMapping("reqInfo")
	private ReqForm reqInfo;

	/**
	 * 请求用户ID
	 */
	@XMLMapping("fromUid")
	private Long fromUid;

	@XMLMapping("toUid")
	private Long toUid;

	/**
	 * 返回reqInfo属性
	 * 
	 * @return reqInfo属性
	 */
	public ReqForm getReqInfo() {
		return reqInfo;
	}

	/**
	 * 设置reqInfo属性
	 * 
	 * @param reqInfo
	 *            reqInfo属性
	 */
	public void setReqInfo(ReqForm reqInfo) {
		this.reqInfo = reqInfo;
	}

	/**
	 * 返回fromUid属性
	 * 
	 * @return fromUid属性
	 */
	public Long getFromUid() {
		return fromUid;
	}

	/**
	 * 设置fromUid属性
	 * 
	 * @param fromUid
	 *            fromUid属性
	 */
	public void setFromUid(Long fromUid) {
		this.fromUid = fromUid;
	}

	/**
	 * 返回toUid属性
	 * 
	 * @return toUid属性
	 */
	public Long getToUid() {
		return toUid;
	}

	/**
	 * 设置toUid属性
	 * 
	 * @param toUid
	 *            toUid属性
	 */
	public void setToUid(Long toUid) {
		this.toUid = toUid;
	}

}

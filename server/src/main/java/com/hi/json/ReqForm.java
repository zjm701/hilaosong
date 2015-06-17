package com.hi.json;

import java.util.Date;

import com.hi.tools.StringTools;
import com.hi.tools.annotation.XMLMapping;

@XMLMapping("reqInfo")
public class ReqForm {
	@XMLMapping("msgName")
	private String msgName;

	@XMLMapping("license")
	private String license = "123123123123";

	@XMLMapping("req_time")
	private String req_time;

	/**
	 * 1:web 2:iphone 3:ipad
	 */
	@XMLMapping("req_device")
	private String req_device;

	/**
	 * 请求语言 1中文 2 英文
	 */
	@XMLMapping("req_language")
	private String req_language;

	public ReqForm() {
	}

	public ReqForm(String msgName) {
		// web user
		// 中文
		this(msgName, "1", "1", StringTools.date2TimeStr(new Date(), StringTools.DATETIME_PATTERN));
	}

	public ReqForm(String msgName, String req_device, String req_language, String req_time) {
		super();
		this.msgName = msgName;
		this.req_device = req_device;
		this.req_language = req_language;
		this.req_time = req_time;
	}

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getReq_time() {
		return req_time;
	}

	public void setReq_time(String reqTime) {
		req_time = reqTime;
	}

	public String getReq_device() {
		return req_device;
	}

	public void setReq_device(String reqDevice) {
		req_device = reqDevice;
	}

	public String getReq_language() {
		return req_language;
	}

	public void setReq_language(String reqLanguage) {
		req_language = reqLanguage;
	}
}

package com.hi.thread;


import java.text.MessageFormat;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hi.common.SystemSetting;
import com.hi.common.provider.SmsProvider;
import com.hi.tools.Tools;

/**
 * 发送并获取手机验证码
 * @author 蒋先彪
 *
 */
public class PlaceOrderPhoneCodeSmsThread implements Runnable {

	private Logger log = LoggerFactory.getLogger(PlaceOrderPhoneCodeSmsThread.class);
	
	/**
	 * 顾客手机号
	 */
	private String phone;
	
	/**
	 * session
	 */
	private HttpSession session;
	
	
	public PlaceOrderPhoneCodeSmsThread(String phone,HttpSession session){
		this.phone = phone;
		this.session = session;
	}
	
	/**
	 * 发送并获取手机验证码
	 */
	@Override
	public void run() {
		try {
			//获取手机验证码内容，并下发短信 存入缓存
			String code = Tools.stringGen(6);
			session.setAttribute("phoneVerificationCode_"+phone, code);
			String smsContent = SystemSetting.getPlaceOrderPhoneCodeSmsContent();
			smsContent = MessageFormat.format(smsContent, code);
			SmsProvider.sendSms(smsContent, phone);
			log.info("顾客获取【"+phone+"】手机验证码成功！验证码为:"+code);
		} catch (Exception e) {
			log.info("顾客获取【"+phone+"】手机验证码失败");
		}
		
	}

}

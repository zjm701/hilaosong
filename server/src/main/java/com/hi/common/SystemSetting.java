package com.hi.common;

import java.io.IOException;
import java.util.Properties;

import com.hi.common.provider.SnsProvider;

public class SystemSetting {

	private static String SNS_JSON_ADDRESS = "sns.json.address";

	private static String WINE = "wine";
	
	private static String OUT_OF_DISH_CATEGORIES = "out.of.dish.categories";

	private static String ENV = "env";

	private static String RED_CORE_URI = "red.core.uri";

	private static String SIGN_KEY = "sign.key";

	private static String APP_ID = "app.id";

	private static String DELAY_DISTANCES = "delay.distances";
	
	private static String DELAY_MINS = "delay.mins";

	private static String FILENAME = "system.properties";
	
	/**
	 * sms 短信服务URL
	 */
	private static String SMS_SERVICE_URL = "sms.service.url";
	/**
	 * 下订单通知顾客短信内容
	 */
	private static String PLACE_ORDER_SMS_CONTENT = "place.order.sms.content";
	/**
	 * 获取手机验证码短信内容
	 */
	private static String PLACE_ORDER_SMS_PHONECODE = "place.order.sms.phoneCode";
	
	private static Properties prop = null;

	private SystemSetting() {
	}

	private static synchronized Properties getProperties() {
		if (prop == null) {
			prop = new Properties();
			try {
				prop.load(SnsProvider.class.getClassLoader().getResourceAsStream(FILENAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("==> Setting:" + prop);
		}
		return prop;
	}

	public static String getSnsJsonAddress() {
		return getSetting(SNS_JSON_ADDRESS);
	}

	public static String getWine() {
		return getSetting(WINE);
	}

	public static String getOutOfDishCategories() {
		return getSetting(OUT_OF_DISH_CATEGORIES);
	}

	public static String getEnv() {
		return getSetting(ENV);
	}

	public static String getRedCoreUri() {
		return getSetting(RED_CORE_URI);
	}

	public static String getSignKey() {
		return getSetting(SIGN_KEY);
	}

	public static String getAppId() {
		return getSetting(APP_ID);
	}
	
	public static String[] getDelayDistances() {
		return getSetting(DELAY_DISTANCES).split(",");
	}
	
	public static String[] getDelayMins() {
		return getSetting(DELAY_MINS).split(",");
	}

	public static String getSetting(String key) {
		return getProperties().getProperty(key);
	}
	
	/**
	 * 获得短信服务地址
	 * @return
	 */
	public static String getSmsServiceURL(){
		return getProperties().getProperty(SMS_SERVICE_URL);
	}
	
	/**
	 * 下订单通知顾客短信内容
	 * @return
	 */
	public static String getPlaceOrderSuccuesSmsContent(){
		return getProperties().getProperty(PLACE_ORDER_SMS_CONTENT);
	}
	
	/**
	 * 获取手机验证码短信内容
	 * @return
	 */
	public static String getPlaceOrderPhoneCodeSmsContent(){
		return getProperties().getProperty(PLACE_ORDER_SMS_PHONECODE);
	}
}

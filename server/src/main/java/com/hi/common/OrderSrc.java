package com.hi.common;

/**
 * [简要描述]订单来源的常量枚举类
 * 
 */
public enum OrderSrc {
	/**
	 * W - 网站
	 */
	WEBSITE("W", "网站"),
	/**
	 * Z - 坐席
	 */
	TELPHONE("Z", "坐席"),
	/**
	 * M - 门店
	 */
	STORE("M", "门店"),
	/**
	 * S - 手机
	 */
	MOBILE_PHONE("S", "手机"),
	/**
	 * T - 支付宝
	 */
	TAOBAO("T", "支付宝"),
	/**
	 * X - 微信
	 */
	WEIXIN("X", "微信"),
	/**
	 * B - 百度
	 */
	BAIDU("B", "百度轻应用"),
	/**
	 * D - 百度地图PC
	 */
	BAIDUMAPPC("D", "百度地图PC"),
	/**
	 * P0 - 贴吧PC
	 */
	BAIDUTIEBA("P0", "贴吧PC"),
	/**
	 * P1 - 贴吧移动
	 */
	BAIDUTIEBAMOBLIE("P1", "贴吧移动");

	private String key;

	private String desc;

	private OrderSrc(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * return the key of this enum.
	 * 
	 * @return the key of this enum constant
	 */
	@Override
	public String toString() {
		return this.key;
	}
}

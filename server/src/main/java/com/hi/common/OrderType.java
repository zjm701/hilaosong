package com.hi.common;

/**
 * [简要描述]订单类型的常量枚举类
 * 
 */
public enum OrderType {
	/**
	 * 0 - 外送
	 */
	SEND_OUT("0", "外送"),
	/**
	 * 1 - 订座
	 */
	BOOK_SEAT("1", "订座"),
	/**
	 * 2 - 外带
	 */
	TAKE_AWAY("2", "外带"),
	/**
	 * 3 -排号
	 */
	TAKE_ARRANGING("3", "排号");

	private String key;

	private String desc;

	private OrderType(String key, String desc) {
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

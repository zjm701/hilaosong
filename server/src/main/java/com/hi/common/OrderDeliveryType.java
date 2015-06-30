package com.hi.common;

/**
 * [简要描述]外送类型的常量枚举类
 * 
 */
public enum OrderDeliveryType {
	/**
	 * 0 - 外送
	 */
	SEND_OUT("0", "外送"),
	/**
	 * 1 - 自助
	 */
	SELF_HELP("1", "自助"),
	/**
	 * 2 - 豪华
	 */
	LUXURY("2", "外带"),
	/**
	 * 3 - 上门自取
	 */
	ARRIVE_STORE("3", "排号");

	private String key;

	private String desc;

	private OrderDeliveryType(String key, String desc) {
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

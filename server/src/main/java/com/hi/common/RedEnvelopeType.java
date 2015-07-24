package com.hi.common;

/**
 * [简要描述]红包类型的常量枚举类
 * 
 */
public enum RedEnvelopeType {
	/**
	 * 1 - 现金红包
	 */
	CASH("1", "现金红包"),
	/**
	 * 2 - 折扣红包
	 */
	DISCOUNT("2", "折扣红包"),
	/**
	 * 3 - 菜券
	 */
	DISH("3", "菜券");

	private String key;

	private String desc;

	private RedEnvelopeType(String key, String desc) {
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

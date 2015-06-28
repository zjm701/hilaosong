package com.hi.common;

/**
 * [简要描述]订单支付状态常量枚举类
 * 
 */
public enum OrderPayStatus {
	/**
	 * 0 - 未支付
	 */
	WAITING_PAY("0", "未支付"),
	/**
	 * 1 - 支付成功
	 */
	PAY_SUCCESS("1", "支付成功"),
	/**
	 * 2 - 支付失败
	 */
	PAY_FAIL("2", "支付失败"),
	/**
	 * 3 - 退款成功
	 */
	REFUND_SUCCESS("3", "退款成功"),
	/**
	 * 4 - 退款失败
	 */
	REFUND_FAIL("4", "退款失败"),
	/**
	 * 5 - 退款处理中
	 */
	REFUND_ING("5", "退款处理中"),
	/**
	 * 6 - 退款未知，需要重新发起退款
	 */
	REFUND_UNKOWN("6", "退款未知，需要重新发起退款"),
	/**
	 * 7 - 钱退到商户账号中，需要商户介入
	 */
	REFUND_LABOUR("7", "钱退到商户账号中，需要商户介入");

	private String key;

	private String desc;

	private OrderPayStatus(String key, String desc) {
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

	public static String getDescByKey(String key) {
		if (WAITING_PAY.getKey().equals(key)) {
			return WAITING_PAY.getDesc();
		} else if (PAY_SUCCESS.getKey().equals(key)) {
			return PAY_SUCCESS.getDesc();
		} else if (PAY_FAIL.getKey().equals(key)) {
			return PAY_FAIL.getDesc();
		} else if (REFUND_FAIL.getKey().equals(key)) {
			return REFUND_FAIL.getDesc();
		} else if (REFUND_SUCCESS.getKey().equals(key)) {
			return REFUND_SUCCESS.getDesc();
		} else if (REFUND_ING.getKey().equals(key)) {
			return REFUND_ING.getDesc();
		} else if (REFUND_UNKOWN.getKey().equals(key)) {
			return REFUND_UNKOWN.getDesc();
		} else if (REFUND_LABOUR.getKey().equals(key)) {
			return REFUND_LABOUR.getDesc();
		} else {
			return "未支付";
		}
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

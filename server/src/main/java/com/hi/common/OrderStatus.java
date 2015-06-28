package com.hi.common;

/**
 * [简要描述]订单状态常量枚举类
 * 
 */
public enum OrderStatus {
	/**
	 * 0 - 待审核
	 */
	PENDING_APPROVE("0", "\u5f85\u5ba1\u6838"),
	/**
	 * 1 - 待打印
	 */
	PENDING_PRINT("1", "\u5f85\u6253\u5370"),
	/**
	 * 2 - 待配菜
	 */
	PENDING_PC("2", "\u5f85\u914d\u83dc/\u5f85\u5c31\u9910/\u5f85\u53d6\u83dc"),
	/**
	 * 3 - 已派送(没有炉具)
	 */
	SENT("3", "已派送(无锅)/已自取(无锅)"),
	/**
	 * 4 - 待收锅(有炉具)
	 */
	PENDING_COLLECT_POT("4", "已派送(待收锅)/已自取(待收锅)"),
	/**
	 * 5 - 待关闭
	 */
	PENDING_CLOSE("5", "\u5f85\u5173\u95ed"),
	/**
	 * 6 - 已删除
	 */
	DELETED("6", "\u5df2\u5220\u9664"),
	/**
	 * 7 - 作废
	 */
	FAKE("7", "作废"),

	/**
	 * 8 - 待下发
	 */
	PENDING_DESCEND("8", "\u5f85\u4e0b\u53d1"),

	/**
	 * 9 - 下发失败
	 */
	DESCEND_FAILED("9", "下发失败"),

	/**
	 * 10 - 作废/删除失败
	 */
	CANCEL_FAILED("10", "作废/删除失败"),

	/**
	 * 11 - 未挽留
	 */
	PENDING_RETAIN("11", "未挽留"),

	/**
	 * 12 - 已挽留
	 */
	RETAIN("12", "已挽留");

	private String key;

	private String desc;

	private OrderStatus(String key, String desc) {
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

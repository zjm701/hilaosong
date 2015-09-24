package com.hi.common;

/**
 * [简要描述]Message常量枚举类
 * 
 */
public enum MessageCode {

	/*
	 * 001~099: Common
	 */
	/**
	 * 编码异常
	 */
	ERROR_ENCODING("001", "\u7f16\u7801\u5f02\u5e38"),

	/**
	 * 未取到数据
	 */
	ERROR_NO_DATA("002", "\u672a\u53d6\u5230\u6570\u636e"),

	/*
	 * 100~199: 用户系统 (100~109: Error 110~119: Success 120~199: Verification)
	 */
	/**
	 * 用户系统异常
	 */
	ERROR_USER("100", "\u7528\u6237\u7cfb\u7edf\u5f02\u5e38"),

	/**
	 * 用户未登录或已过期
	 */
	ERROR_NO_LOGGEDIN_USER("101", "\u7528\u6237\u672a\u767b\u5f55\u6216\u5df2\u8fc7\u671f"),

	/**
	 * 用户名不能为空
	 */
	VERIFICATION_EMPTY_USERNAME("120", "\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a"),

	/**
	 * 密码不能为空
	 */
	VERIFICATION_EMPTY_PASSWORD("121", "\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a"),

	/**
	 * LoginId不能为空
	 */
	VERIFICATION_EMPTY_LOGINID("122", "LoginId\u4e0d\u80fd\u4e3a\u7a7a"),

	/*
	 * 200~299: DIY锅底系统(200~209: Error 210~219: Success 220~299: Verification)
	 */
	/**
	 * DIY锅底系统异常
	 */
	ERROR_GUODI("200", "DIY\u9505\u5e95\u7cfb\u7edf\u5f02\u5e38"),

	/**
	 * 创建DIY锅底失败
	 */
	ERROR_CREATE_GUODI("201", "\u521b\u5efaDIY\u9505\u5e95\u5931\u8d25"),

	/**
	 * 更新DIY锅底失败
	 */
	ERROR_UPDATE_GUODI("202", "\u66f4\u65b0DIY\u9505\u5e95\u5931\u8d25"),

	/**
	 * 删除DIY锅底失败
	 */
	ERROR_DELETE_GUODI("203", "\u5220\u9664DIY\u9505\u5e95\u5931\u8d25"),

	/**
	 * 创建DIY锅底成功
	 */
	SUCCESS_CREATE_GUODI("210", "\u521b\u5efaDIY\u9505\u5e95\u6210\u529f"),

	/**
	 * 更新DIY锅底成功
	 */
	SUCCESS_UPDATE_GUODI("211", "\u66f4\u65b0DIY\u9505\u5e95\u6210\u529f"),

	/**
	 * 删除DIY锅底成功
	 */
	SUCCESS_DELETE_GUODI("212", "\u5220\u9664DIY\u9505\u5e95\u6210\u529f"),

	/**
	 * DIY锅底名不能为空
	 */
	VERIFICATION_EMPTY_GUODI_NAME("220", "DIY\u9505\u5e95\u540d\u4e0d\u80fd\u4e3a\u7a7a"),

	/**
	 * 主锅底Id不能为空
	 */
	VERIFICATION_EMPTY_GUODI_DISHID1("221", "\u4e3b\u9505\u5e95Id\u4e0d\u80fd\u4e3a\u7a7a"),

	/**
	 * 次锅底Id不能为空
	 */
	VERIFICATION_EMPTY_GUODI_DISHID2("222", "\u6b21\u9505\u5e95Id\u4e0d\u80fd\u4e3a\u7a7a"),

	/**
	 * 锅底Id不能为空
	 */
	VERIFICATION_EMPTY_GUODIID("223", "\u9505\u5e95Id\u4e0d\u80fd\u4e3a\u7a7a"),

	/*
	 * 300~399: 订单系统(300~309: Error 310~319: Success 320~399: Verification)
	 */
	/**
	 * 订单系统异常
	 */
	ERROR_ORDER("300", "DIY\u9505\u5e95\u7cfb\u7edf\u5f02\u5e38"),

	/**
	 * 创建订单失败
	 */
	ERROR_CREATE_ORDER("301", "\u521b\u5efa\u5b9a\u5355\u5931\u8d25"),

	/**
	 * 创建订单成功
	 */
	SUCCESS_CREATE_ORDER("310", "\u521b\u5efa\u5b9a\u5355\u6210\u529f"),

	/**
	 * 订餐时间不能为空
	 */
	VERIFICATION_EMPTY_DINNINGTIME("320", "\u8ba2\u9910\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a"),

	/**
	 * 订餐时间不得小于当前时间
	 */
	VERIFICATION_PASSED_DINNINGTIME("321", "\u8ba2\u9910\u65f6\u95f4\u4e0d\u5f97\u5c0f\u4e8e\u5f53\u524d\u65f6\u95f4"),

	/**
	 * 此订餐时间不送餐
	 */
	VERIFICATION_NO_SERVICE_DINNINGTIME("322", "\u6B64\u8BA2\u9910\u65F6\u95F4\u4E0D\u9001\u9910"),

	/**
	 * 此订餐时间无法送达
	 */
	VERIFICATION_SO_EARLY_DINNINGTIME("323", "\u6B64\u8BA2\u9910\u65F6\u95F4\u65E0\u6CD5\u9001\u8FBE"),

	/**
	 * 订单联系人不能为空
	 */
	VERIFICATION_EMPTY_CONTACTNAME("330", "\u8ba2\u5355\u8054\u7cfb\u4eba\u4e0d\u80fd\u4e3a\u7a7a"),
	
	/**
	 * 订单联系电话不能为空
	 */
	VERIFICATION_EMPTY_CONTACTPHONE("331", "\u8ba2\u5355\u8054\u7cfb\u7535\u8bdd\u4e0d\u80fd\u4e3a\u7a7a"),
	
	/**
	 * 预订门店不能为空
	 */
	VERIFICATION_EMPTY_STORE("332", "\u9884\u8ba2\u95e8\u5e97\u4e0d\u80fd\u4e3a\u7a7a"),
	
	/**
	 * 订单菜品不能为空
	 */
	VERIFICATION_EMPTY_DISHES("333", "\u8ba2\u5355\u83dc\u54c1\u4e0d\u80fd\u4e3a\u7a7a"),
	
	/**
	 * 订单类型不能为空
	 */
	VERIFICATION_EMPTY_ORDERTYPE("334", "\u8ba2\u5355\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a"),
	
	/**
	 * 外送地址不能为空
	 */
	VERIFICATION_EMPTY_ADDRESS("335", "\u5916\u9001\u5730\u5740\u4e0d\u80fd\u4e3a\u7a7a"),
	
	/**
	 * 支付类型不能为空
	 */
	VERIFICATION_EMPTY_PAYCHANNEL("336", "\u652f\u4ed8\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a"),


	/*
	 * 400~499: 优惠劵系统(400~409: Error 410~419: Success 420~499: Verification)
	 */
	/**
	 * 优惠劵系统异常
	 */
	ERROR_COUPON("400", "\u4F18\u60E0\u52B5\u7CFB\u7EDF\u5F02\u5E38"),

	/**
	 * 优惠劵编码不能为空
	 */
	VERIFICATION_EMPTY_COUPONID("420", "\u4F18\u60E0\u52B5\u7F16\u7801\u4E0D\u80FD\u4E3A\u7A7A"),

	/**
	 * 订单编号不能为空
	 */
	VERIFICATION_EMPTY_ORDERID("421", "\u8BA2\u5355\u7F16\u53F7\u4E0D\u80FD\u4E3A\u7A7A");
	
	private String key;

	private String desc;

	private MessageCode(String key, String desc) {
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

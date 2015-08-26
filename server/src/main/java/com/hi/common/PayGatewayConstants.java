package com.hi.common;

public class PayGatewayConstants {

	public static final String ORDER_INIT_FAIL_RESPCODE = ""; // 订单初始化失败返回的respCode

	public static final String ORDER_CREATE_SUCCESS_STATUS = "0"; // 订单创建成功状态

	public static final String ORDER_PAY_SUCCESS_STATUS = "1"; // 订单支付成功状态

	public static final String ORDER_PAY_FAILE_STATUS = "2"; // 订单支付失败状态

	public static final String ORDER_REFUND_SUCCESS_STATUS = "3"; // 订单退款成功

	public static final String ORDER_REFUND_FAILE_STATUS = "4"; // 订单退款失败

	public static final String ORDER_REFUND_KEEPING_STATUS = "5"; // 订单退款处理中

	public static final String ORDER_REFUND_UNKNOWN_STATUS = "6"; // 订单退款未知

	public static final String ORDER_REFUND_MERCHANT_STATUS = "7"; // 订单需要商户介入

	public static final String ORDER_THIRD_PAYTYPE_WAP = "0"; // 第三方支付方式wap

	public static final String ORDER_THIRD_PAYTYPE_WEB = "1"; // 第三方支付方式web

}

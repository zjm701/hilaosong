package com.hi.pay.alipay.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.hi.pay.alipay.AlipayConfig;
import com.hi.tools.sign.RSA;

/* *
 *类名：AlipayNotify
 *功能：支付宝通知处理类
 *详细：处理支付宝各接口通知返回
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考

 *************************注意*************************
 *调试通知返回时，可查看或改写log日志的写入TXT里的数据，来检查通知返回是否正常
 */
public class AlipayNativeNotify {

	/**
	 * 支付宝消息验证地址
	 */
	private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

	/**
	 * 验证消息是否是支付宝发出的合法消息
	 * 
	 * @param params
	 *            通知返回来的参数数组
	 * @return 验证结果
	 */
	public static boolean verify(Map<String, String> params) {

		// 判断responsetTxt是否为true，isSign是否为true
		// responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
		// isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		String responseTxt = "true";
		if (params.get("notify_id") != null) {
			String notify_id = params.get("notify_id");
			responseTxt = verifyResponse(notify_id);
		}
		String sign = "";
		if (params.get("sign") != null) {
			sign = params.get("sign");
		}
		boolean isSign = getSignVeryfy(params, sign);

		// 写日志记录（若要调试，请取消下面两行注释）
		// String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign +
		// "\n sign="+sign+"\n返回回来的参数：" + AlipayCore.createLinkString(params);
		// AlipayCore.logResult(sWord);

		if (isSign && responseTxt.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据反馈回来的信息，生成签名结果
	 * 
	 * @param Params
	 *            通知返回来的参数数组
	 * @param sign
	 *            比对的签名结果
	 * @return 生成的签名结果
	 */
	private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
		// 过滤空值、sign与sign_type参数
		Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
		// 获取待签名字符串
		String preSignStr = AlipayCore.createLinkString(sParaNew);
		// 获得签名验证结果
		boolean isSign = false;
		if (AlipayConfig.native_sign_type.equals("RSA")) {
			isSign = RSA.verify(preSignStr, sign, AlipayConfig.ali_public_key, AlipayConfig.input_charset);
		}
		return isSign;
	}

	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param notify_id
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private static String verifyResponse(String notify_id) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

		String partner = AlipayConfig.partner;
		String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

		return checkUrl(veryfy_url);
	}

	/**
	 * 获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private static String checkUrl(String urlvalue) {
		String inputLine = "";

		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			e.printStackTrace();
			inputLine = "";
		}

		return inputLine;
	}

	public static boolean very(String preSignStr, String sign) {
		boolean isSign = false;
		if (AlipayConfig.native_sign_type.equals("RSA")) {
			isSign = RSA.verify(preSignStr, sign, AlipayConfig.ali_public_key, AlipayConfig.input_charset);
		}
		return isSign;
	}

	public static void main(String[] args) {
		String perSignStr = "body=北京十五店&buyer_email=277915365@qq.com&buyer_id=2088102103166772&discount=0.00&gmt_create=2014-12-21 15:38:03&gmt_payment=2014-12-21 15:38:04&is_total_fee_adjust=N&notify_id=4841d61d4a557f5e541288a583a1d27b6a&notify_time=2014-12-21 17:02:37&notify_type=trade_status_sync&out_trade_no=S0BJ152014122110973-67nM4A&payment_type=1&price=0.01&quantity=1&seller_email=bjhhd@haidilao.com&seller_id=2088311122773056&sign=oYIUmANpIpaF7ZOAlosWKTZgxj3/Ytv1Yn0C6iZymcOpMiXvHXrSoKJbpucxn9afrX+on6meY4RW65KbmPDtcNrLDAzoH0hnZUz5mw5233BRztPwUt6i8/PSQthPOfE7yWbDvGVoZ1GH1Lf79KFIXXPhHAT0f3l7w5KgnqT45Cs=&sign_type=RSA&subject=海底捞火锅订单&total_fee=0.01&trade_no=2014122100990477&trade_status=TRADE_SUCCESS&use_coupon=N";
		String sign = "oYIUmANpIpaF7ZOAlosWKTZgxj3/Ytv1Yn0C6iZymcOpMiXvHXrSoKJbpucxn9afrX+on6meY4RW65KbmPDtcNrLDAzoH0hnZUz5mw5233BRztPwUt6i8/PSQthPOfE7yWbDvGVoZ1GH1Lf79KFIXXPhHAT0f3l7w5KgnqT45Cs=";
		Map<String, String> map = new HashMap<String, String>();
		String[] kvs = perSignStr.split("&");
		for (int i = 0; i < kvs.length; i++) {
			map.put(kvs[i].split("=")[0], kvs[i].split("=")[1]);
		}
		// map.put("_input_charset", "utf-8");
		Map<String, String> sParaNew = AlipayCore.paraFilter(map);
		perSignStr = AlipayCore.createLinkString(sParaNew);
		System.out.println(perSignStr);
		boolean flag = very(perSignStr, sign);
		System.out.println(flag);
	}
}

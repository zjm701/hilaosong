package com.hi.common.unionpay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unionpay.acp.sdk.SDKConfig;

/**
 * 银联支付提供者
 * @author 蒋先彪
 *
 */
public class UnionPayProvider extends UnionPayBase {

	/**
	 * log日志
	 */
	private static final Logger log = LoggerFactory.getLogger(UnionPayProvider.class);
	
	/**
	 * 得到支付链接
	 * @param txAmt
	 * @param orderId
	 * @param txTime
	 * @return
	 */
	public static String getUnionPayHtml(Integer txAmt,String orderId,String txTime){
		
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", UnionPayBase.version);
		// 字符集编码 默认"UTF-8"
		data.put("encoding", UnionPayBase.encoding);
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "07");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", SDKConfig.getConfig().getProperties().getProperty("customer.front.url"));
		// 后台通知地址
		data.put("backUrl", SDKConfig.getConfig().getProperties().getProperty("customer.back.url"));
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", SDKConfig.getConfig().getProperties().getProperty("customer.merId"));
		// 商户订单号，8-40位数字字母
		data.put("orderId", orderId);
		// 订单发送时间，取系统时间
		data.put("txnTime", txTime);
		// 交易金额，单位分
		data.put("txnAmt", String.valueOf(txAmt.intValue()));
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		// data.put("orderDesc", "订单描述");

		Map<String, String> submitFromData = signData(data);

		// 交易请求url 从配置文件读取
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
		
		/**
		 * 创建表单
		 */
		String html = createHtml(requestFrontUrl, submitFromData);
		log.info(html);
		return html;
	}
	
	
	/**
	 * 查询订单支付状态
	 * @param orderId
	 * @param txTime
	 * @return
	 */
	public static Map<String, String> queryOrderUnionPayStatus(String orderId,String txTime){
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", UnionPayBase.version);
		// 字符集编码 默认"UTF-8"
		data.put("encoding", UnionPayBase.encoding);
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 
		data.put("txnType", "00");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", SDKConfig.getConfig().getProperties().getProperty("customer.merId"));
		// 商户订单号，请修改被查询的交易的订单号
		data.put("orderId", orderId);
		// 订单发送时间，请修改被查询的交易的订单发送时间
		data.put("txnTime", txTime);

		data = signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getSingleQueryUrl();

		Map<String, String> resmap = submitUrl(data, url);

		log.info("请求报文=["+data.toString()+"]");
		log.info("应答报文=["+resmap.toString()+"]");
		
		return resmap;
	}
}

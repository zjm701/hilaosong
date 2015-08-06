package com.hi.common.provider;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hi.common.SystemSetting;
import com.hi.tools.CalendarTools;
import com.hi.tools.HttpUtil;
import com.hi.tools.StringTools;

public class CouponProvider {

	private static Log log = LogFactory.getLog(CouponProvider.class);
	private static String SIGN_KEY;
	private static String APP_ID;
	private static String RED_COUR_SUFFIX = ".json";// 红包核心URL地址后缀

	/**
	 * 
	 * @Description: TODO 获得请求流水号
	 * @Detail:
	 * @return String
	 * 
	 */
	private static String getSeq() {
		StringBuffer sb = new StringBuffer();
		sb.append(CalendarTools.now().getTime()).append(StringTools.generateRandomCode(6));
		return sb.toString();
	}

	/**
	 * 获得URL
	 * 
	 * @param method
	 * @return
	 */
	private static String getURL(String method) {
		StringBuffer sb = new StringBuffer(SystemSetting.getRedCoreUri());
		sb.append(method).append(RED_COUR_SUFFIX);
		return sb.toString();
	}

	public static String parseStreamToString(InputStream in) {
		StringBuffer str = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(in), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	/**
	 * 
	 * @Description: TODO 从核心红包换取数据
	 * @Detail:
	 * @param method
	 * @param params
	 * @return JSONObject
	 * 
	 */
	private static JSONObject requestRedCore(String method, TreeMap<String, String> params) {
		APP_ID = SystemSetting.getAppId();
		SIGN_KEY = SystemSetting.getSignKey();
		params.put("seq", getSeq());
		params.put("appId", APP_ID);
		params.put("ts", CalendarTools.nowString(CalendarTools.DATETIME_DEFAULT));
		// 签名时不考虑sign
		String sign = SHA1MessageSigner.sign(SIGN_KEY, params);
		// 签名后put sign
		params.put("sign", sign);
		System.out.println(params.toString());
		String s = "";
		try {
			s = HttpUtil.sendPost(getURL(method), params);
		} catch (Exception e) {
			System.out.println(e);
		}

		if (s == null || "".equals(s)) {
			JSONObject json = new JSONObject();
			Map<String, String> map = new HashMap<String, String>();
			map.put("errorCode", "9999");
			map.put("errorText", "系统异常");
			json.put("status", map);
			log.error(" 9999 从红包系统请求数据为空，网络异常或红包系统异常。");
			return json;
		}
		return JSONObject.fromObject(s);
	}

	/**
	 * 查询红包
	 * 
	 * @param userId
	 * @param userPhone
	 * @return
	 */
	public static JSONObject get(String userId, String redEnvelopeId) {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("userId", userId);
		params.put("redEnvelopeId", redEnvelopeId);
		return requestRedCore("query", params);
	}

	/**
	 * 我的红包
	 * 
	 * @param userId
	 * @param userPhone
	 * @return
	 */
	public static JSONObject list(String userId) {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("userId", userId);
		params.put("userPhone", "");
		return requestRedCore("getList", params);
	}

	/**
	 * 使用红包
	 * 
	 * @param orderId
	 * @param userId
	 * @param redEnvelopeIds
	 * @return
	 */
	public static JSONObject use(String userId, String orderId, String redEnvelopeIds) {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("userId", userId);
		params.put("orderNo", orderId);
		params.put("redEnvelopeIds", redEnvelopeIds);
		return requestRedCore("use", params);
	}

	public static JSONObject reback(String userId, String orderId, String redEnvelopeId) {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("userId", userId);
		params.put("orderNo", orderId);
		params.put("redEnvelopeId", redEnvelopeId);
		return requestRedCore("reback", params);
	}

	public static JSONObject cancel(String orderId, String userId) {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("userId", userId);
		params.put("orderNo", orderId);
		return requestRedCore("cancel", params);
	}

	public static void main(String[] args) {
		TreeMap<String, String> params = new TreeMap<String, String>();
//		params.put("orderNo", "XBJ1520150529130131");
//		params.put("userId", "22479");
//		params.put("redEnvelopeIds", "1002410347380177");
//		System.out.println(requestRedCore("use", params).toString());

//		params = new TreeMap<String, String>();
//		params.put("userId", "21517");
//		params.put("redEnvelopeId", "1002410347380177");
//		System.out.println(requestRedCore("query", params).toString());

		params = new TreeMap<String, String>();
		params.put("userId", "22479");
		params.put("userPhone", "");
		System.out.println(requestRedCore("getList", params));
	}
}

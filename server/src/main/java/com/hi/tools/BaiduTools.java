package com.hi.tools;

import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hi.common.SystemSetting;



public class BaiduTools {
	private static Logger logger = LogManager.getLogger(BaiduTools.class);
	
	/**
	 * 
	 * [简要描述]:获取两坐标之间的驾车距离
	 * 
	 * @param origin
	 *            起点坐标，例（"40.056878,116.30815"）
	 * @param destination
	 *            终点坐标，例（"39.915285,116.403857"）
	 */
	public static long getDIstance(String origin, String destination) {
		String cityName = "";
		String apiurl = SystemSetting.getSetting("baidu.url");
		String ak = SystemSetting.getSetting("baidu.apiKey");
		String url = "";
		String response = "";
		long distance = -1;

		if (!StringTools.isEmpty(origin) && !StringTools.isEmpty(destination)
				&& !StringTools.isEmpty(apiurl) && !StringTools.isEmpty(ak)) {
			url = apiurl + "?mode=driving&origin=" + origin + "&destination="
					+ destination + "&origin_region=" + cityName
					+ "&destination_region=" + cityName + "&output=json&ak="
					+ ak;
			try {
				response = HttpUtil.sendGet(url);
				JSONObject object = JSONObject.fromObject(response);
				if (object.getInt("status") == 0) {
					distance = object.getJSONObject("result")
							.getJSONArray("routes").getJSONObject(0)
							.getLong("distance");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn(e.getMessage());
			}
		}
		return distance;
	}

	/**
	 * 获取某个坐标点的地理位置描述
	 * @param cuspoint
	 * 					坐标位置，例(116.420593,40.076381)
	 * @return  北京市 海淀区 学院南路
	 */
	public static String getDescByLocation(String cuspoint) {
		String response = "";
		String result = "";
		if (!StringTools.isEmpty(cuspoint)) {
			cuspoint = cuspoint.split(",")[1] + "," + cuspoint.split(",")[0];
			String apiurl = SystemSetting.getSetting("baidu.geocodingUrl");
			String ak = SystemSetting.getSetting("baidu.apiKey");
			try {
				apiurl += "?ak=" + ak + "&location=" + cuspoint
						+ "&output=json";
				response = HttpUtil.sendGet(apiurl);
				System.out.println(response);
				JSONObject object = JSONObject.fromObject(response);
				if (object.getInt("status") == 0) {
					result = object.getJSONObject("result")
							.getJSONObject("addressComponent")
							.getString("province")
							+ "-"
							+ object.getJSONObject("result")
									.getJSONObject("addressComponent")
									.getString("district")
							+ "-"
							+ object.getJSONObject("result")
									.getJSONObject("addressComponent")
									.getString("street");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (StringTools.isEmpty(result) || "--".equals(result)) {
			result = "";
		}
		return result;
	}
	
	/**
	 * 根据地理位置描述获取某个坐标点
	 * @param 地理位置描述 
	 * 					北京市 海淀区 学院南路
	 * @return  坐标位置，(116.3555855618,39.963757558199)
	 */
	public static String getCusPointByAddress(String address) {
		String response = "";
		String result = "";
		if (!StringTools.isEmpty(address)) {
			String apiurl = SystemSetting.getSetting("baidu.geocodingUrl");
			String ak = SystemSetting.getSetting("baidu.apiKey");
			try {
				apiurl += "?ak=" + ak + "&address=" + URLEncoder.encode(address, "UTF-8")
						+ "&output=json";
				System.out.println(apiurl);
				response = HttpUtil.sendGet(apiurl);
				System.out.println(response);
				JSONObject object = JSONObject.fromObject(response);
				if (object.getInt("status") == 0) {
					result = object.getJSONObject("result")
							.getJSONObject("location")
							.getString("lng")
							+ ","
							+ object.getJSONObject("result")
									.getJSONObject("location")
									.getString("lat");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}

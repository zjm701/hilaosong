package com.hi.tools;


public class CityTools {
	/**
	 * 判断是否是北上天重直辖市
	 * 
	 * @param cityId
	 * @return
	 */
	public static boolean isDirectMunicipalities(String provinceId) {
		if ("110000".equals(provinceId) || "310000".equals(provinceId) || "120000".equals(provinceId) || "500000".equals(provinceId)) {
			return true;
		} else {
			return false;
		}
	}
}

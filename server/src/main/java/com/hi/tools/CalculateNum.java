package com.hi.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * [简要描述]:
 * 
 */
public class CalculateNum {
	private static final String ZERO_STR = "0";

	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;

	/**
	 * 
	 * [简要描述]:获取流水号
	 * 
	 */
	public static String getCode(String temp) {

		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
		String todayAsString = simpleDate.format(date);

		int length = temp.length();
		switch (length) {
		case ONE:
			temp = "0000" + temp;
			break;
		case TWO:
			temp = "000" + temp;
			break;
		case THREE:
			temp = "00" + temp;
			break;
		case FOUR:
			temp = ZERO_STR + temp;
			break;
		default:
			break;
		}
		return todayAsString + temp;
	}
}

package com.hi.tools;

import java.util.Random;

public class Tools {
	
	/**
	 * 随机生成固定长度字串
	 * @param  length 随机数长度
	 * @return 固定长度字串
	 * */
	public static String stringGen(int length) {
		char[] number = ("1234567890").toCharArray();
		Random random = new Random();
		char[] genNumber = new char[length];
		for (int i = 0; i < length; i++) {
			genNumber[i] = number[random.nextInt(10)];
		}
		number = null;
		return new String(genNumber);
	}
}

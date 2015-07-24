package com.hi.common.provider;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SHA1MessageSigner {

	private static final String ALGORITHM = "HmacSHA1";
	private static final String CHARSET = "UTF-8";

	public boolean verify(String key, String signature, String content) {

		if (key == null || "".equals(key))
			throw new IllegalArgumentException("Invalid key specified.");
		if (signature == null || "".equals(signature))
			throw new IllegalArgumentException("Invalid signature specified.");
		if (content == null || "".equals(content))
			throw new IllegalArgumentException("Invalid content specified.");

		// byte[] signatureBytes = Base64.decodeBase64(signature);
		String signatureExpected = sign(key, content);
		return signature.equals(signatureExpected);
	}

	/**
	 * 
	 * @Description: TODO 传入treemap 对象签名
	 * @Detail:
	 * @param key
	 *            授权码
	 * @param treeMap
	 *            除了sign之外的所有参数 键值对。
	 * @return String
	 * 
	 */
	public static String sign(String key, TreeMap<String, String> content) {

		if (key == null || "".equals(key))
			throw new IllegalArgumentException("Invalid key specified.");
		if (content == null)
			throw new IllegalArgumentException("Invalid content specified.");
		return sign(key, mapToUrlParams(content));
	}

	private static String mapToUrlParams(Map<String, String> params) {

		// 合并参数到查询串
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (value == null || "".equals(value))
				continue; // skip empty or
							// null value
			if (sb.length() > 0)
				sb.append('&');
			sb.append(key);
			sb.append('=');
			sb.append(value);
		}
		return sb.toString();
	}

	private static String sign(String key, String content) {

		if (key == null || "".equals(key))
			throw new IllegalArgumentException("Invalid key specified.");
		if (content == null || "".equals(content))
			throw new IllegalArgumentException("Invalid content specified.");

		try {
			byte[] signatureBytes = sign(key.getBytes(CHARSET), content.getBytes(CHARSET));
			String signature = byteArrayToHexString(signatureBytes).toUpperCase();
			return signature;
		} catch (IOException e) {
			throw new RuntimeException("Unsupported encoding : " + CHARSET);
		}
	}

	/**
	 * 把byte数组转换成16进制
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteArrayToHexString(byte[] byteArray) {

		StringBuilder result = new StringBuilder();

		if (byteArray == null) {
			return null;
		}

		for (int i = 0; i < byteArray.length; i++) {
			result.append(Character.forDigit(byteArray[i] >>> 4 & 0xf, 16));
			result.append(Character.forDigit(byteArray[i] & 0xf, 16));
		}

		return result.toString();
	}

	private static byte[] sign(byte[] key, byte[] content) {

		try {
			Mac signer = Mac.getInstance(ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
			signer.init(keySpec);
			return signer.doFinal(content);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException("No such algorithm :" + ALGORITHM);
		}
	}

	public static void main(String[] args) {

		String content = "appId=100&redEnvelopeType=1&seq=4&ts=2015-04-22T10:42:60&userId=670321";
		TreeMap<String, String> s = new TreeMap<String, String>();
		s.put("redEnvelopeType", "1");
		s.put("seq", "4");
		s.put("ts", "2015-04-22T10:42:60");
		s.put("userId", "670321");
		s.put("appId", "100");
		String signature = SHA1MessageSigner.sign("0123456789", content);
		try {
			System.out.println("signature=" + signature);
			System.out.println("signature=" + SHA1MessageSigner.sign("0123456789", s));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

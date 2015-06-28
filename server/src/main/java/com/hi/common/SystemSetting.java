package com.hi.common;

import java.io.IOException;
import java.util.Properties;


public class SystemSetting {

	private static String SNS_JSON_ADDRESS = "sns.json.address";

	private static String FILENAME = "system.properties";

	private static Properties prop = null;

	private SystemSetting() {
	}

	private static Properties getProperties() {
		if (prop == null) {
			prop = new Properties();
			try {
				prop.load(SnsProvider.class.getClassLoader().getResourceAsStream(FILENAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("==> Setting:" + prop);
		}
		return prop;
	}

	public static String getSnsJsonAddress() {
		return getProperties().getProperty(SNS_JSON_ADDRESS);
	}
	
	public static String getSetting(String key){
		return getProperties().getProperty(key);
	}
}

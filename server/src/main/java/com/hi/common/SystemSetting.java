/*
 * IBM Confidential
 * OCO Source Materials
 *
 * IBM DemandTec Assortment Optimization
 *
 * © Copyright IBM Corp 2011-2015
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with
 * the U.S. Copyright Office.
 */
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
}

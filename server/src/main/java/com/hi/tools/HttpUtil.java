package com.hi.tools;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public final class HttpUtil {
	public static String sendGet(String url) throws Exception {
		try {
			HttpClient client = new DefaultHttpClient();

			HttpGet request = new HttpGet(url);

			HttpResponse response = client.execute(request);

			String result = EntityUtils.toString(response.getEntity());
			return result;
		} catch (Exception e) {
			throw new Exception("can not get response from URL: " + url, e);
		}

	}
}

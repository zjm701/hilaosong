package com.hi.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public final class HttpUtil {
	public static String sendGet(String url) throws Exception {
		try {
			HttpClient client = new DefaultHttpClient();

			HttpGet request = new HttpGet(url);

			HttpResponse response = client.execute(request);

			HttpEntity entityRsp = response.getEntity();
			StringBuffer result = new StringBuffer();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					entityRsp.getContent(), HTTP.UTF_8));
			String tempLine = rd.readLine();
			while (tempLine != null) {
				result.append(tempLine);
				tempLine = rd.readLine();
			}
			if (entityRsp != null) {
				entityRsp.consumeContent();
			}
			return result.toString();
		} catch (Exception e) {
			throw new Exception("can not get response from URL: " + url, e);
		}

	}

	public static String sendPost(String url, Map<String, String> params)
			throws Exception {
		try {
			HttpClient client = new DefaultHttpClient();

			HttpPost httpRequest = new HttpPost(url);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				pairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			HttpEntity httpEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
			httpRequest.setEntity(httpEntity);

			HttpResponse response = client.execute(httpRequest);

			HttpEntity entityRsp = response.getEntity();
			StringBuffer result = new StringBuffer();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					entityRsp.getContent(), HTTP.UTF_8));
			String tempLine = rd.readLine();
			while (tempLine != null) {
				result.append(tempLine);
				tempLine = rd.readLine();
			}
			if (entityRsp != null) {
				entityRsp.consumeContent();
			}
			return result.toString();
		} catch (Exception e) {
			throw new Exception("can not get response from URL: " + url, e);
		}

	}
}

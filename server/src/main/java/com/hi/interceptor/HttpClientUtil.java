package com.hi.interceptor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	private static Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);
	private static int defaultConnectionTimeout = 8000;
	private static final long defaultHttpConnectionManagerTimeout = 60 * 1000;

	/** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
	private static int defaultSoTimeout = 30000;

	/** 闲置连接超时时间, 由bean factory设置，缺省为60秒钟 */
	private static int defaultIdleConnTimeout = 60000;

	private static int defaultMaxConnPerHost = 30;

	private static int defaultMaxTotalConn = 80;

	private static HttpConnectionManager connectionManager;
	static {
		try {
			if (connectionManager == null)// 创建一个线程安全的HTTP连接池
			{
				connectionManager = new MultiThreadedHttpConnectionManager();
				connectionManager.getParams().setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
				connectionManager.getParams().setMaxTotalConnections(defaultMaxTotalConn);
				IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
				ict.addConnectionManager(connectionManager);
				ict.setConnectionTimeout(defaultIdleConnTimeout);
				ict.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1 HttpClientByPost
	 * 
	 * @param checkURL
	 * @param paramsJson
	 * @return
	 * @throws Exception
	 */
	public static String HttpClientByPost(String checkURL, String paramsJson) throws Exception {
		String responseContent = "";
		HttpClient httpClient = new HttpClient(HttpClientUtil.connectionManager);
		PostMethod postMethod = new PostMethod(checkURL);

		try {
			LOG.error("请求地址" + checkURL + ":" + paramsJson);

			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(defaultConnectionTimeout);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(defaultSoTimeout);
			httpClient.getParams().setConnectionManagerTimeout(defaultHttpConnectionManagerTimeout);

			postMethod.setRequestEntity(new StringRequestEntity(paramsJson, "application/json", "UTF-8"));
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new Exception("statusCode:"+statusCode+"  HTTP is not ok!");
			}

			// responseContent = postMethod.getResponseBodyAsString();
			InputStream resStream = postMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			responseContent = resBuffer.toString();
			LOG.error("请求地址" + checkURL + "返回信息:" + responseContent);

		} catch (ConnectException e) {
			LOG.info("无法连接，调用地址:" + checkURL);
		} catch (ConnectTimeoutException e) {
			LOG.info("连接超时，调用地址:" + checkURL);
		} finally {
			postMethod.releaseConnection();
		}
		return responseContent;
	}

	public static String HttpClientByGet(String checkURL) throws Exception {
		String responseContent = "";
		HttpClient httpClient = new HttpClient(HttpClientUtil.connectionManager);
		GetMethod getMethod = new GetMethod(checkURL);

		try {
			LOG.error("请求地址" + checkURL);

			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(defaultConnectionTimeout);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(defaultSoTimeout);
			httpClient.getParams().setConnectionManagerTimeout(defaultHttpConnectionManagerTimeout);

			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new Exception("HTTP is not ok!");
			}

			InputStream resStream = getMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			responseContent = resBuffer.toString();
			LOG.error("请求地址" + checkURL + "返回信息:" + responseContent);

		} catch (ConnectException e) {
			e.printStackTrace();
			LOG.info("无法连接，调用地址:" + checkURL);
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			LOG.info("连接超时，调用地址:" + checkURL);
		} finally {
			getMethod.releaseConnection();
		}
		return responseContent;
	}
	public static void main(String[] args) {

        try {
			HttpClientUtil.HttpClientByGet("http://103.240.244.31/hilaosong/rest/getareastore?cityId=110000&_=1442229867754");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

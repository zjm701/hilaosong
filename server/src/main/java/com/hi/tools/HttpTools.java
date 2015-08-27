package com.hi.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 * 此类包含了通过HTTP协议的请求响应等方法。 此类属于公共方法，负责提供接口给业务调用。 目前请求支持4种请求方式： 1
 * xml请求报文通过POST方法请求URL，返回响应报文 2 xml请求报文通过PUT方法请求URL，返回响应报文 3
 * 无参数的请求通过GET方法请求URL，返回响应报文 4 无参数的请求通过DELETE方法请求URL，返回操作结果 使用方法： 先得到HttpUtil的实例
 * （使用连接池方式），通过HttpUtil.getInstance().getHttpClient()返回HttpClient对象 然后调用指定的方法即可
 * 
 */
public final class HttpTools {
	/**
	 * http的header中的content-type属性的名字
	 */
	public static final String CONTENT_TYPE_NAME = "content-type";

	/**
	 * http的header中的content-type属性的内容
	 */
	public static final String CONTENT_TYPE_VALUE_XML_UTF_8 = "text/xml; charset=UTF-8";

	/**
	 * http的header中的content-type属性的传输类型
	 */
	public static final String TEXT_XML = "text/xml";

	/**
	 * http的header中的content-type属性的字符编码
	 */
	public static final String UTF_8 = "UTF-8";

	/**
	 * HttpUtil类的实例
	 */
	private static HttpTools instance = null;

	/**
	 * HttpClient实例
	 */
	private static HttpClient httpClient = null;

	/**
	 * 链接的超时数,默认为15秒
	 */
	private static final int CONNECTION_TIME_OUT = 15000;

	/**
	 * 链接的失效时间,默认为15秒
	 */
	private static final int CONNECTION_LINGER_TIME_OUT = 15000;

	/**
	 * 1000
	 */
	private static final int THOUSAND = 1000;

	/**
	 * 每个主机的最大并行链接数，默认为2
	 */
	private static final int MAX_CONNECTIONS_PER_HOST = 200;

	/**
	 * 客户端总并行链接最大数，默认为20
	 */
	private static final int MAX_TOTAL_CONNECTIONS = 1000;

	/**
	 * 日志对象
	 */
	private static Log logger = LogFactory.getLog(HttpTools.class);

	/**
	 * HttpUtil类构造函数
	 */
	private HttpTools() {
	}

	/**
	 * 单例模式返回唯一的HttpUtil的实例 在创建HttpUtil实例的时候创建HttpClient对象，并且设置HttpClient超时的属性。
	 * 创建HttpClient实例，默认是SimpleHttpConnectionManager创建的，不支持多线程。
	 * 使用多线程技术就是说，client可以在多个线程中被用来执行多个方法。 每次调用HttpClient.executeMethod()
	 * 方法，都会去链接管理器申请一个连接实例， 申请成功这个链接实例被签出(checkout)，随之在链接使用完后必须归还管理器。 管理器支持两个设置：
	 * maxConnectionsPerHost 每个主机的最大并行链接数，默认为2 maxTotalConnections
	 * 客户端总并行链接最大数，默认为20
	 * 
	 * @return HttpUtil
	 */
	public static HttpTools getInstance() {
		if (instance == null) {
			// 首次调用，创建HttpUtil，唯一实例
			instance = new HttpTools();

			// 此处运用连接池技术。
			MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();

			// 设定参数：与每个主机的最大连接数
			manager.getParams().setDefaultMaxConnectionsPerHost(MAX_CONNECTIONS_PER_HOST);

			// 设定参数：客户端的总连接数
			manager.getParams().setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);

			// 使用连接池技术创建HttpClient对象
			httpClient = new HttpClient(manager);

			// 设置超时时间
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIME_OUT);

			// 设置连接失效时间
			httpClient.getHttpConnectionManager().getParams().setLinger(CONNECTION_LINGER_TIME_OUT / THOUSAND);

			// add by caogan for 性能优化 on20101020 begin
			httpClient.getParams().setVersion(HttpVersion.HTTP_1_1);
			// add by caogan for 性能优化 on20101020 end
		}

		return instance;
	}

	/**
	 * 向指定的url发起http请求，返回respone中的字符串 使用httpclient的POST方法。 成功返回url位置响应的报文，失败异常抛出
	 * 向指定URL发起http请求,使用httpclinet默认的重链机制 并且打印影响时间的debug日志，以便于性能问题的定位
	 * 
	 * @param url
	 *            指定的url，具体到请求的命令字
	 * @param sendXml
	 *            xml的报文
	 * @return String xml格式的字符串
	 * @throws Exception
	 *             发送http异常
	 */
	public String sendByPost(String url, String sendXml) throws Exception {
		// post方法对象
		PostMethod post = getPostMethod(url);
		// 请求返回的内容，类型为字符串
		String ret = "";

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("the request url is:" + url);
			}

			// 请求前的系统时间，便于时间分析
			long beginTime = System.currentTimeMillis();

			// 发送请求
			int result = 0;
			try {
				result = send(post, sendXml);
			} catch (ConnectException e) {
				e.printStackTrace();
				// throw new
				// BusinessException(ast.getText(CommonConstants.CONNECTERROR));
			}

			// http状态码，如果成功则取得返回的xml字符串
			if (result == HttpStatus.SC_OK) {
				ret = post.getResponseBodyAsString();

				// 耗费的操作时间
				if (logger.isDebugEnabled()) {
					logger.debug("the time-consuming of process in Http Request is:" + (System.currentTimeMillis() -

					beginTime));
				}
			} else {
				// 如果http_notok,抛出异常
				logger.error("the error httpstatus is :" + result);

				throw new IllegalStateException("http_notok");
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("post occur UnsupportEncoding Exception", e);

			throw e;
		}

		catch (ConnectTimeoutException e) {
			logger.error("post occur ConnectTimeout Exception", e);

			throw e;
		}

		catch (NoHttpResponseException e) {
			logger.error("post occur NoHttpResponse Exception", e);

			throw e;
		}

		catch (HttpException e) {

			logger.error("post occur HttpException", e);

			throw e;
		}

		catch (IOException e) {
			logger.error("post occur IOException", e);

			throw e;
		}

		catch (Exception e) {
			logger.error("post occur Exception", e);

			throw e;
		}

		finally {
			if (null != post) {
				// 释放链接
				post.releaseConnection();
			}
		}

		return ret;

	}

	/**
	 * 向指定的url发起http请求，返回respone中的字符串 使用httpclient的POST方法。 成功返回url位置响应的报文，失败异常抛出
	 * 
	 * @param url
	 *            指定的url，具体到请求的命令字
	 * @param sendXml
	 *            xml的报文
	 * @return int 返回状态
	 * @throws Exception
	 *             发送http异常
	 */
	public int postSend(String url, String sendXml) throws Exception {

		// post方法对象
		PostMethod post = getPostMethod(url);
		// 请求返回的内容，类型为字符串
		int result;

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("the request url is:" + url);
			}

			// 请求前的系统时间，便于时间分析
			long beginTime = System.currentTimeMillis();

			// 发送请求
			result = send(post, sendXml);

			// http状态码，如果成功则取得返回的xml字符串
			if (result == HttpStatus.SC_OK) {
				// 耗费的操作时间
				if (logger.isDebugEnabled()) {
					logger.debug("the time-consuming of process in Http Request is:" + (System.currentTimeMillis() -

					beginTime));
				}
			} else {
				// 如果http_notok,抛出异常
				logger.error("the error httpstatus is :" + result);
				throw new IllegalStateException("http_notok");
			}
		} catch (Exception e) {
			logger.error("post occur Exception", e);
			throw e;
		} finally {
			if (null != post) {
				// 释放链接
				post.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * 向指定的url发起http请求，返回respone中的字符串 使用httpclient的PUT方法。 成功返回url位置响应的报文，失败异常抛出
	 * 
	 * @param url
	 *            指定的url，具体到请求的命令字
	 * @param sendXml
	 *            xml的报文
	 * @return int 返回状态
	 * @throws Exception
	 *             发送http异常
	 */
	public int putSend(String url, String sendXml) throws Exception {
		// Put方法对象
		PutMethod put = getPutMethod(url);

		// 请求返回的内容，类型为字符串
		int result;

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("the request url is:" + url);
			}

			// 请求前的系统时间，便于时间分析
			long beginTime = System.currentTimeMillis();

			// 发送请求
			result = send(put, sendXml);

			// http状态码，如果成功则取得返回的xml字符串
			if (result == HttpStatus.SC_OK) {
				// 耗费的操作时间
				if (logger.isDebugEnabled()) {
					logger.debug("the time-consuming of process in Http Request is:" + (System.currentTimeMillis() -

					beginTime));
				}
			} else {
				// 如果http_notok,抛出异常
				logger.error("the error httpstatus is" + result);

				throw new IllegalStateException("http_notok");
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("put occur UnsupportEncoding Exception", e);

			throw e;
		}

		catch (Exception e) {
			logger.error("put occur Exception", e);

			throw e;
		} finally {
			if (null != put) {
				// 释放链接
				put.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * 向指定的url发起http请求，返回respone中的字符串 使用httpclient的PUT方法。 成功返回url位置响应的报文，失败异常抛出
	 * 向指定URL发起http请求,使用httpclinet默认的重链机制 并且打印影响时间的debug日志，以便于性能问题的定位
	 * 
	 * @param url
	 *            指定的url，具体到请求的命令字
	 * @param sendXml
	 *            xml的报文
	 * @return String xml格式的字符串
	 * @throws Exception
	 *             发送http异常
	 */
	public String sendByPut(String url, String sendXml) throws Exception {
		// Put方法对象
		PutMethod put = getPutMethod(url);

		// 请求返回的内容，类型为字符串
		String ret = "";

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("the request url is:" + url);
			}

			// 请求前的系统时间，便于时间分析
			long beginTime = System.currentTimeMillis();

			// 发送请求
			int result = send(put, sendXml);

			// http状态码，如果成功则取得返回的xml字符串
			if (result == HttpStatus.SC_OK) {
				ret = put.getResponseBodyAsString();

				// 耗费的操作时间
				if (logger.isDebugEnabled()) {
					logger.debug("the time-consuming of process in Http Request is:" + (System.currentTimeMillis() -

					beginTime));
				}
			} else {
				// 如果http_notok,抛出异常
				logger.error("the error httpstatus is" + result);

				throw new IllegalStateException("http_notok");
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("put occur UnsupportEncoding Exception", e);

			throw e;
		}

		catch (ConnectTimeoutException e) {
			logger.error("put occur ConnectTimeout Exception", e);

			throw e;
		}

		catch (NoHttpResponseException e) {
			logger.error("put occur NoHttpResponse Exception", e);

			throw e;
		}

		catch (HttpException e) {

			logger.error("put occur HttpException", e);

			throw e;
		}

		catch (IOException e) {
			logger.error("put occur IOException", e);

			throw e;
		}

		catch (Exception e) {
			logger.error("put occur Exception", e);

			throw e;
		}

		finally {
			if (null != put) {
				// 释放链接
				put.releaseConnection();
			}
		}

		return ret;
	}

	/**
	 * 向指定的url发起http请求，返回respone中的字符串 使用httpclient的GET方法。 成功返回url位置响应的报文，失败异常抛出
	 * 向指定URL发起http请求,使用httpclinet默认的重链机制 并且打印影响时间的debug日志，以便于性能问题的定位
	 * 
	 * @param url
	 *            指定的url，具体到请求的命令字
	 * @return String xml格式的字符串
	 * @throws Exception
	 *             发送http异常
	 */
	public String sendByGet(String url) throws Exception {
		// Get方法对象
		GetMethod get = getGetMethod(url);

		// 请求返回的内容，类型为字符串
		String ret = "";

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("the request url is:" + url);
			}

			// 请求前的系统时间，便于时间分析
			long beginTime = System.currentTimeMillis();

			// 发送请求
			int result = send(get);

			// http状态码，如果成功则取得返回的xml字符串
			if (result == HttpStatus.SC_OK) {
				ret = get.getResponseBodyAsString();

				// 耗费的操作时间
				if (logger.isDebugEnabled()) {
					logger.debug("the time-consuming of process in Http Request is:" + (System.currentTimeMillis() -

					beginTime));
				}
			} else {
				// 如果http_notok,抛出异常
				logger.error("the error httpstatus is" + result);

				throw new IllegalStateException("http_notok");
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("get occur UnsupportEncoding Exception", e);

			throw e;
		}

		catch (ConnectTimeoutException e) {
			logger.error("get occur ConnectTimeout Exception", e);

			throw e;
		}

		catch (NoHttpResponseException e) {
			logger.error("get occur NoHttpResponse Exception", e);

			throw e;
		}

		catch (HttpException e) {

			logger.error("get occur HttpException", e);

			throw e;
		}

		catch (IOException e) {
			logger.error("get occur IOException", e);

			throw e;
		}

		catch (Exception e) {
			logger.error("get occur Exception", e);

			throw e;
		}

		finally {
			if (null != get) {
				// 释放链接
				get.releaseConnection();
			}
		}

		return ret;
	}

	/**
	 * 向指定的url发起http请求，使用httpclient的DELETE方法。 成功返回true，失败返回false，异常抛出
	 * 向指定URL发起http请求,使用httpclinet默认的重链机制 并且打印影响时间的debug日志，以便于性能问题的定位
	 * 
	 * @param url
	 *            指定的url，具体到请求的命令字
	 * @return String xml格式的字符串
	 * @throws Exception
	 *             发送http异常
	 */
	public boolean sendByDelete(String url) throws Exception {
		// delete方法对象
		DeleteMethod delete = getDeleteMethod(url);

		// 请求返回的内容，类型为字符串
		boolean ret = false;

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("the request url is:" + url);
			}

			// 请求前的系统时间，便于时间分析
			long beginTime = System.currentTimeMillis();

			// 发送请求
			int result = send(delete);

			// http状态码，如果成功则取得返回的xml字符串
			if (result == HttpStatus.SC_OK) {
				ret = true;

				// 耗费的操作时间
				if (logger.isDebugEnabled()) {
					logger.debug("the time-consuming of process in Http Request is:" + (System.currentTimeMillis() -

					beginTime));
				}
			} else {
				// 如果http_notok,抛出异常
				logger.error("the error httpstatus is" + result);

				throw new IllegalStateException("http_notok");
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("delete occur UnsupportEncoding Exception", e);

			throw e;
		}

		catch (ConnectTimeoutException e) {
			logger.error("delete occur ConnectTimeout Exception", e);

			throw e;
		}

		catch (NoHttpResponseException e) {
			logger.error("delete occur NoHttpResponse Exception", e);

			throw e;
		}

		catch (HttpException e) {

			logger.error("delete occur HttpException", e);

			throw e;
		}

		catch (IOException e) {
			logger.error("delete occur IOException", e);

			throw e;
		}

		catch (Exception e) {
			logger.error("delete occur Exception", e);

			throw e;
		}

		finally {
			if (null != delete) {
				// 释放链接
				delete.releaseConnection();
			}
		}
		return ret;
	}

	/**
	 * 此方法将传入xml以指定方法进行发送 实际上入参支持EntityEnclosingMethod的2个子类：PostMethod,
	 * PutMethod 返回当前操作的响应码
	 * 
	 * @param method
	 *            PostMethod或者PutMethod的实例
	 * @param sendXml
	 *            要发送的XML
	 * @return 当前操作的响应码
	 * @throws IOException
	 * @throws HttpException
	 * @throws IOException
	 *             http发送的异常情况
	 */
	private int send(EntityEnclosingMethod method, String sendXml) throws IOException {
		// 设置header信息，传输XML格式的
		method.setRequestHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE_XML_UTF_8);
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, CONNECTION_TIME_OUT);
		// 发送含xml消息体的对象
		RequestEntity entity = new StringRequestEntity(sendXml, TEXT_XML, UTF_8);

		method.setRequestEntity(entity);

		return httpClient.executeMethod(method);

	}

	/**
	 * 此方法直接请求给定的URL 实际上入参支持HttpMethodBase的常见的5个子类：DeleteMethod, GetMethod,
	 * HeadMethod, OptionsMethod, TraceMethod 返回当前操作的响应码
	 * 
	 * @param method
	 *            5个子类的实例
	 * @return 当前操作的响应码
	 * @throws IOException
	 *             http发送的异常情况
	 */
	private int send(HttpMethodBase method) throws IOException {
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, CONNECTION_TIME_OUT);
		return httpClient.executeMethod(method);
	}

	/**
	 * 私有方法，根据URL返回PostMethod对象
	 * 
	 * @param url
	 *            要请求的路径
	 * @return PostMethod PostMethod对象
	 */
	private PostMethod getPostMethod(String url) {
		PostMethod post = new PostMethod(url);

		// add by caogan for 性能优化 on20101020 begin
		post.setRequestHeader("Connection", "Keep-Alive");
		// add by caogan for 性能优化 on20101020 end

		return post;
	}

	/**
	 * 私有方法，根据URL返回GetMethod对象
	 * 
	 * @param url
	 *            要请求的路径
	 * @return PostMethod PostMethod对象
	 */
	private GetMethod getGetMethod(String url) {
		return new GetMethod(url);
	}

	/**
	 * 私有方法，根据URL返回PutMethod对象
	 * 
	 * @param url
	 *            要请求的路径
	 * @return PostMethod PostMethod对象
	 */
	private PutMethod getPutMethod(String url) {
		return new PutMethod(url);
	}

	/**
	 * 私有方法，根据URL返回DeleteMethod对象
	 * 
	 * @param url
	 *            要请求的路径
	 * @return PostMethod PostMethod对象
	 */
	private DeleteMethod getDeleteMethod(String url) {
		return new DeleteMethod(url);
	}

	public static String sendGet(String url) throws Exception {
		try {
			org.apache.http.impl.client.DefaultHttpClient client = new DefaultHttpClient();

			HttpGet request = new HttpGet(url);

			HttpResponse response = client.execute(request);

			HttpEntity entityRsp = response.getEntity();
			StringBuffer result = new StringBuffer();
			BufferedReader rd = new BufferedReader(new InputStreamReader(entityRsp.getContent(), HTTP.UTF_8));
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

	public static String sendPost(String url, Map<String, String> params) throws Exception {
		try {
			org.apache.http.impl.client.DefaultHttpClient client = new DefaultHttpClient();

			HttpPost httpRequest = new HttpPost(url);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			HttpEntity httpEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
			httpRequest.setEntity(httpEntity);

			HttpResponse response = client.execute(httpRequest);

			HttpEntity entityRsp = response.getEntity();
			StringBuffer result = new StringBuffer();
			BufferedReader rd = new BufferedReader(new InputStreamReader(entityRsp.getContent(), HTTP.UTF_8));
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

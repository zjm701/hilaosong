package com.hi.common.unionpay;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unionpay.acp.sdk.SDKConfig;

/**
 * 初始化银联 配置文件到内存
 * @author 蒋先彪
 *
 */
public class InitUnionPayConfigServlet extends HttpServlet {
	
	private static Logger log = LoggerFactory.getLogger(InitUnionPayConfigServlet.class);
	
	/**
	 * 加载银联 配置
	 */
	@Override
	public void init() throws ServletException {
		log.info("初始化银联配置....!");
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		super.init();
	}
}

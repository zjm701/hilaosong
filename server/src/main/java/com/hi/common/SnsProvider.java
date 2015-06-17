package com.hi.common;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class SnsProvider {

	public SnsProvider() {
	}

	public static synchronized SnsTerminalInterface getSNSJsonCxfClient() {
		// 定义客户端代理
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

		// 设置服务端地址和服务类
		factory.setAddress(SystemSetting.getSnsJsonAddress());
		factory.setServiceClass(SnsTerminalInterface.class);
		SnsTerminalInterface service = (SnsTerminalInterface) factory.create();

		return service;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SnsProvider.getSNSJsonCxfClient();
	}
}

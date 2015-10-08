package com.hi.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 根据接口名称得到相应的bean
 */
@Component("springBeanUtil")
public final class SpringBeanUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext)

	throws BeansException {

		if (SpringBeanUtil.applicationContext == null) {

			SpringBeanUtil.applicationContext = applicationContext;

		}

	}

	public static ApplicationContext getApplicationContext() {

		return applicationContext;

	}

	public Object getBean(String name) {

		return getApplicationContext().getBean(name);

	}

}

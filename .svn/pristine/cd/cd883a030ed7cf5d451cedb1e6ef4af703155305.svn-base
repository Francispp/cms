package com.cyberway.core.utils;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cyberway.core.exception.BizException;

/**
 * @author caiw
 *
 */
public class ServiceLocator {

	private static BeanFactory servicesbeanFactory;
	
	protected ServiceLocator() { // 不允许实例化，全部使用static函数。

	}
	/**
	 * 供web初始化时候调用把context注入
	 * @param sctx
	 */
	public static synchronized void initBeanFactory(ServletContext sctx){
		if (servicesbeanFactory == null) {
			ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(sctx);
			servicesbeanFactory = (BeanFactory) appContext;
		}
	}
	
	private static synchronized void getServicesBeanFactory(){
		if (servicesbeanFactory == null) {
			try {		
				ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath*:context/*.xml");
				servicesbeanFactory = (BeanFactory) appContext;
			} catch (Exception e) {
				throw new BizException("ENV_SPRING_FAIL_INITIAL_BEANFACTORY", e);
			}
		}
	}

	public static Object getBean(String beanName){
		if (servicesbeanFactory == null) {
			getServicesBeanFactory();//让队列中的所有要求getBeanFactory的线程只有一个真正执行了初始化的工作。
		}
		try {
			return servicesbeanFactory.getBean(beanName);
		} catch (Exception e) {
			throw new BizException("ENV_SPRING_FAIL_LOADING_BEAN", e);
		}
	}

	public static Class getBeanType(String beanName) {
		if (servicesbeanFactory == null) {
			getServicesBeanFactory();//让队列中的所有要求getBeanFactory的线程只有一个真正执行了初始化的工作。
		}
		try {
			return servicesbeanFactory.getType(beanName);
		} catch (Exception e) {
			throw new BizException("ENV_SPRING_FAIL_LOADING_BEAN", e);
		}
	}
}


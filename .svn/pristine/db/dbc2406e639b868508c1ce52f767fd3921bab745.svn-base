package com.cyberway.core.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import com.cyberway.core.utils.ServiceLocator;

/**
 * @author caiw
 *
 */
public class InitBeanFactoryListener extends HttpServlet implements ServletContextListener {
	static final long serialVersionUID = 504011604;
	public void contextInitialized(ServletContextEvent sce) {
		ServiceLocator.initBeanFactory(sce.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
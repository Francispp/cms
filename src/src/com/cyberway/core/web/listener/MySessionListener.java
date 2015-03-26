package com.cyberway.core.web.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.web.service.LoginLogService;


public class MySessionListener implements HttpSessionListener,HttpSessionAttributeListener {
	private Log logger = LogFactory.getLog(getClass());
	private LoginLogService loginLogService ;
	
	public void sessionCreated(HttpSessionEvent hse) { 
        OnLine.raise();
    } 
    public void sessionDestroyed(HttpSessionEvent hse) { 
        OnLine.reduce();
    }
    public void attributeAdded(HttpSessionBindingEvent eve) {
		if(eve.getName().equals(Loginer.LOGININFO_SESSION))
			OnLine.logRaise();
	}

	public void attributeRemoved(HttpSessionBindingEvent eve) {
		if(eve.getName().equals(Loginer.LOGININFO_SESSION)){
			OnLine.logReduce();
			//保存登录日志
			Object loginer = eve.getValue();
			getLoginLogService().save(loginer);
		}
	}
	
	

	public void attributeReplaced(HttpSessionBindingEvent eve) {
		if(eve.getName().equals(Loginer.LOGININFO_SESSION)){
			//保存登录日志
			Object loginer = eve.getValue();
			getLoginLogService().save(loginer);
		}
	}
	
	public LoginLogService getLoginLogService() {
		if(loginLogService == null)
			loginLogService = (LoginLogService) ServiceLocator.getBean("LoginLogService");
		return loginLogService;
	}
	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}
	
	
}

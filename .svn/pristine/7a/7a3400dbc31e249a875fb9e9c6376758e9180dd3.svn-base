package com.cyberway.core.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.cyberway.common.domain.CoreSystemLog;
import com.cyberway.common.service.SystemLogService;
import com.cyberway.core.utils.ServiceLocator;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LogInterceptor extends AbstractInterceptor {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2498919080786276204L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation)
			throws Exception {
		SystemLogService service=(SystemLogService)ServiceLocator.getBean("systemLogService");
		HttpServletRequest request=(HttpServletRequest)invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		CoreSystemLog log=SystemLogService.getSystemLog(request);
		if(log!=null){
		 service.insertSystemLog(log);	
		 Map session=invocation.getInvocationContext().getSession();
		 session.put("LATELY_URL", log.getAccessurl());
		}
		return invocation.invoke();
	}

}

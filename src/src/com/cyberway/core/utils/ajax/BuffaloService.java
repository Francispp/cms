package com.cyberway.core.utils.ajax;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Buffalo Service is a service for those service want to get information 
 * from ServletContext or ServletRequest. See the number guess demo 
 * for a demostration. 
 * 
 * A common java bean (with an constructor for no argument) 
 * is good for most case.
 *  
 * @author caiw
 * @version 1.0
 *
 */
public class BuffaloService {
	
	private HttpServletRequest request;
	private ServletContext context;
	
	/**
	 * Init this service. You should never invoke this method since
	 * Buffalo will do it. 
	 * 
	 * @param context The ServletContext
	 * @param request The Servlet Request
	 */
	public void init(ServletContext context, ServletRequest request) {
		this.context = context;
		this.request = (HttpServletRequest)request;
	}
	
	/**
	 * @return Returns the servlet context.
	 */
	public ServletContext getContext() {
		return context;
	}
	
	/**
	 * @return Returns the servlet request.
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
}

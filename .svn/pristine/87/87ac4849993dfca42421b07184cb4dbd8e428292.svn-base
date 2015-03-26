package com.cyberway.core.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.MDC;

/**
 * 记录当前使用者的信息到log4j.MDC
 *
 * @author caiw
 */
public class Log4JUserFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put(getPropertyName(), getUserId(request));
        chain.doFilter(request, response);
    }

    /**
     * 设定MDC中userid的变量名，可在子类重载
     */
    protected String getPropertyName() {
        return "userId";
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
	
	/**
	 * 这里记录了IP地址，可以根据情况扩展
	 * @param request
	 * @return
	 */
	protected String getUserId(ServletRequest request) {
		return request.getRemoteAddr();
    }
}

package com.cyberway.staticer.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.core.objects.Loginer;
import com.cyberway.staticer.Configuration;
import com.cyberway.staticer.cache.PageCache;
import com.cyberway.staticer.cache.PageKey;
import com.cyberway.staticer.gather.VariableCollector;

/**
 * 页面过滤器<br />
 * 判断当前请求是否已经被缓存，如果是则直接输出内空，则否交给下级过滤器处理
 * @author helfen
 *
 */
public class PageFilter implements Filter
{
	private PageCache _pageCache;
	private SiteCache _siteCache;
	
	public void setPageCache(PageCache pageCache)
	{
		_pageCache = pageCache;
	}
	
	public void setSiteCache(SiteCache siteCache)
	{
		_siteCache = siteCache;
	}
	
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		Loginer loginer = (Loginer)request.getSession().getValue(Loginer.LOGININFO_SESSION);
		
		if (loginer != null && 
			!StringUtils.isBlank(loginer.getRoleCode()) && 
			!VariableCollector.isGatherPhase(request))
		{
			Map<String, String> parameters = new HashMap<String, String> ();
			
			for (String parameterName : (Set<String>)request.getParameterMap().keySet())
			{
				parameters.put(parameterName, request.getParameter(parameterName));
			}
			
			PageKey key = new PageKey(request.getServerName(), request.getServletPath(), parameters, loginer.getRoleCode());
			
			if (!_pageCache.contains(key))
			{
				key = new PageKey(request.getServerName(), request.getServletPath(), parameters, Configuration.defaultRole());
				
				if (!_pageCache.contains(key))
				{
					CmsSite cmsSite = _siteCache.getSiteFromMainSite(request.getServerName());
					
					if (cmsSite != null && !StringUtils.isBlank(cmsSite.getSitehttp()))
					{
						key = new PageKey(cmsSite.getSitehttp(), request.getServletPath(), parameters, loginer.getRoleCode());
						
						if (!_pageCache.contains(key))
						{
							key = new PageKey(cmsSite.getSitehttp(), request.getServletPath(), parameters, Configuration.defaultRole());
						}
					}
				}
			}
			
			if (_pageCache.contains(key))
			{
				//request.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Type", "text/html;charset=UTF-8");
				response.getWriter().write(new String (_pageCache.get(key), response.getCharacterEncoding()));
				
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	public void destroy()
	{
	}
}

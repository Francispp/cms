package com.cyberway.cms.component.oscache.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyberway.cms.component.oscache.domain.CacheURL;
import com.cyberway.cms.component.oscache.service.CMSCacheURLService;
import com.cyberway.core.utils.ServiceLocator;


public class CMSCacheURLFilter extends com.opensymphony.oscache.web.filter.CacheFilter {

	private final Log log = LogFactory.getLog(this.getClass());
	private CMSCacheURLService service = null;


	@Override
	public void init(FilterConfig filterConfig) {
		service = (CMSCacheURLService) ServiceLocator.getBean("CMSCacheURLService");
		service.init();
		super.init(filterConfig);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest hreq = (HttpServletRequest) request;
		String currentURL =hreq.getRequestURI();
		String contextPath = hreq.getContextPath();
		if (currentURL.startsWith(contextPath)) {
			currentURL = currentURL.substring(contextPath.length());
		}
		String matchstr = currentURL + "?" + hreq.getQueryString();
		//不缓存
		if (!this.isInclude(request, matchstr)){
			chain.doFilter(request, response);
			return ;
		}
		super.doFilter(request, response, chain);
	}
	/**
	 * 是否是需要缓存的url
	 * @param matchstr
	 * @return
	 */
	public boolean isInclude(ServletRequest request, String matchstr) {
		Iterator<Entry<Long, Pattern>> iter = service.getPatterns().entrySet().iterator();
		while(iter.hasNext()){
			Entry<Long, Pattern> entry = iter.next();
			Pattern p = entry.getValue();
			Matcher matcher = p.matcher(matchstr);
			if(matcher.matches()) {
				CacheURL vo = service.get(entry.getKey());
				Integer t = vo.getPeriods();
				if(t!=null)
					setTime(t); //
				setCron(vo.getCron());
				request.setAttribute("_OSCACHEGROUPID", vo.getOid()+""); //在CacheGroupsProvider中用到  缓存分组便于管理
				return true;
			}
		}
		return false;
	}
	
}

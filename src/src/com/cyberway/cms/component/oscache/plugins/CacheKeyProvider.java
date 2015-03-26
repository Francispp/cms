package com.cyberway.cms.component.oscache.plugins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.oscache.web.filter.ICacheKeyProvider;

public class CacheKeyProvider implements ICacheKeyProvider {

	public String createCacheKey(HttpServletRequest httpRequest, ServletCacheAdministrator scAdmin,
			Cache cache) {


		return scAdmin.generateEntryKey(null, httpRequest, PageContext.APPLICATION_SCOPE);
	}

}

package com.cyberway.cms.component.oscache.plugins;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.oscache.web.filter.ICacheGroupsProvider;

public class CacheGroupsProvider implements ICacheGroupsProvider{

	public String[] createCacheGroups(HttpServletRequest httpRequest, ServletCacheAdministrator scAdmin, Cache cache) {
		// groupid _OSCACHEGROUPID  CMSCacheURLFilter.isInclude 中设置
		return new String[]{(String)httpRequest.getAttribute("_OSCACHEGROUPID")};
	}

}

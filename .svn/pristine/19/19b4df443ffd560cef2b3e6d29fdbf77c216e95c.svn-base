package com.cyberway.core.acegi.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cyberway.core.acegi.cache.AcegiCacheManager;
import com.cyberway.core.acegi.resource.ResourceDetails;

/**
 * 页面资源权限验证工具类
 *
 * @author lib
 */
public class AuthUtils {
	
	protected Log logger = LogFactory.getLog(getClass());

    //~ Methods ========================================================================================================

    /**
     * 是否具有指定资源串的权限
     * @param pageContext
     * @param res
     * @return
     */
    public static synchronized boolean hasAuthorize(PageContext pageContext,String res) {
    	//Log.debug("========= 页面资源管理 =========");
    	//logger.debug("当前资源串:"+res);
        ServletContext sc = pageContext.getServletContext();
        WebApplicationContext webAppCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        AcegiCacheManager acegiCacheManager = (AcegiCacheManager) webAppCtx.getBean("acegiCacheManager");
        //acegiCacheManager.initResourceCache();

        final Collection granted = getPrincipalAuthorities();
        String resource = res;
        Collection required;
        ResourceDetails rd = acegiCacheManager.getResourceFromCache(resource);
        if (rd == null) {
        	required = Collections.EMPTY_LIST;
        	//如果没有设置资源,则默认有权限(lib)
        	return true;
        }
        else required = Arrays.asList(rd.getAuthorities());
        Collection grantedCopy = copy(granted);

        if ((null != resource) && !"".equals(resource)) {
            grantedCopy.retainAll(required);
            if (grantedCopy.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static Collection getPrincipalAuthorities() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (null == currentUser) {
            return Collections.EMPTY_LIST;
        }
        if ((null == currentUser.getAuthorities()) || (currentUser.getAuthorities().length < 1)) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(currentUser.getAuthorities());
    }

    private static Set copy(Collection c) {
        Set target = new HashSet();
        for (Iterator iterator = c.iterator(); iterator.hasNext();) {
            target.add(iterator.next());
        }
        return target;
    }
}

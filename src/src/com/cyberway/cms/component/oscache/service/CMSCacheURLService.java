package com.cyberway.cms.component.oscache.service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.component.oscache.domain.CacheURL;
import com.cyberway.core.dao.HibernateEntityDao;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class CMSCacheURLService extends HibernateEntityDao<CacheURL> {

	private ConcurrentHashMap<Long, Pattern> patterns ;
	private GeneralCacheAdministrator cacheAdmin;
	
	
	
	/**
	 * 加载patterns ， CMSCacheURLFilter 中调用
	 * @throws Exception
	 */
	public void init() {
		patterns = new ConcurrentHashMap<Long, Pattern>();
		List<CacheURL> urls = getAll();
		String incurl = "";
		for(CacheURL curl:urls){
			incurl = curl.getIncludeurl();
			if(incurl!=null&& StringUtils.isNotBlank(incurl)){
				//缓存Pattern 对象
				Pattern p = Pattern.compile(".*"+incurl.trim().replace(".", "\\.").replace("?", "\\?")+".*");
				patterns.put(curl.getOid(), p);
			}
			//更新缓存
			cacheAdmin.putInCache(curl.getOid().toString(), curl);
		}
	}
	
	
	/**
	 * 更新缓存
	 * @param curl
	 */
	public void updateCache(CacheURL curl){
		String incurl = curl.getIncludeurl();
		if(incurl!=null&& StringUtils.isNotBlank(incurl) && curl.getActivated() != null && curl.getActivated()){
			Pattern p = Pattern.compile(".*"+incurl.trim().replace(".", "\\.").replace("?", "\\?")+".*");
			patterns.put(curl.getOid(), p);
			cacheAdmin.removeEntry(curl.getOid().toString());
			cacheAdmin.putInCache(curl.getOid().toString(), curl);
		}
	}
	


	@Override
	public CacheURL saveOrUpdate(CacheURL cu) {
		cu.setIncludeurl(cu.getIncludeurl().trim());
		cu = super.saveOrUpdate(cu);
		updateCache(cu);
		return cu;
	}
	
	
	@Override
	public CacheURL get(Serializable id) {
		CacheURL c = null;
        try {
            // try to find an object
        	c = (CacheURL)cacheAdmin.getFromCache(id.toString());
        } catch (NeedsRefreshException nre) {
            try {
            	c = super.get(id);
                this.cacheAdmin.putInCache(id.toString(), c);
            } catch (Exception ex) {
                this.cacheAdmin.cancelUpdate(id.toString());
            }
        }
		return c;
	}
	
	

	@Override
	public void removeById(Class entityClass, Serializable id) {
		super.removeById(entityClass, id);
		patterns.remove(id);
		cacheAdmin.removeEntry(id.toString());
	}



	public GeneralCacheAdministrator getCacheAdmin() {
		return cacheAdmin;
	}

	public void setCacheAdmin(GeneralCacheAdministrator cacheAdmin) {
		this.cacheAdmin = cacheAdmin;
	}
	
	public ConcurrentHashMap<Long, Pattern> getPatterns() {
		return patterns;
	}
}

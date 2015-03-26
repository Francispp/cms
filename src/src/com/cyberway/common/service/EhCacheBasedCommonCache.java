package com.cyberway.common.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataRetrievalFailureException;

import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.domain.CoreResource;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.domain.CoreUser;

/**
 * @author caiw 公用缓存实现类
 */
public class EhCacheBasedCommonCache implements CommonCache {

	// ~ Static fields/initializers
	// =============================================

	private static final Log	logger	      = LogFactory.getLog(EhCacheBasedCommonCache.class);
	private static final String	SITE_SIGN	  = "S_";
	private static final String	RESOURCE_SIGN	= "R_";
	private static final String TYPE = "T";
	/**
	 * 分隔符
	 */
	private static final String	FLAG_SEP	  = "_";
	// ~ Instance fields
	// ========================================================

	Cache	                    coreResourceCache;	                                              // 公共资源缓存
	Cache	                    coreUserCache;	                                                  // 用户缓存
	Cache	                    coreOrgCache;	                                                  // 组织机构缓存
	Cache	                    coreFlowCache;	                                                  // 流程缓存
	Cache	                    coreSiteDistributionCache;	                                      // 站点分发配置缓存
	Cache	                    roleCache;	                                      				// 角色缓存

	// ~ Methods
	// ================================================================
	public Cache getCoreResourceCache() {
		return coreResourceCache;
	}

	public void setCoreResourceCache(Cache coreResourceCache) {
		this.coreResourceCache = coreResourceCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#getResourceFromCache(java.lang
	 * .String)
	 */
	public CoreResource getResourceFromCache(String resCode) {
		Element element = null;

		try {
			element = coreResourceCache.get(resCode);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (logger.isDebugEnabled()) {
			// logger.debug("Cache hit: " + (element != null) + "; resString: "+
			// resString);
		}

		if (element == null) {
			return null;
		} else {
			return (CoreResource) element.getValue();
		}
	}

	public String getSiteDistributionCacheKey(Long siteId, String resourceType, Long ftpId) {
		return SITE_SIGN + siteId + FLAG_SEP + RESOURCE_SIGN + resourceType + FLAG_SEP + ftpId;
	}

	public String getSiteDistributionCacheKey(Long siteId, String resourceType) {
		return SITE_SIGN + siteId + FLAG_SEP + RESOURCE_SIGN + resourceType + FLAG_SEP;
	}

	public void putSiteDistributionInCache(Long siteId, String resourceType, CoreSiteDistribution resourceDetails) {
		String key = getSiteDistributionCacheKey(siteId, resourceType, resourceDetails.getId());
		Element element = new Element(key, resourceDetails);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		coreSiteDistributionCache.put(element);
	}

	public CoreSiteDistribution getSiteDistributionByKey(Long siteId, String resourceType, Long ftpId) {
		String key = getSiteDistributionCacheKey(siteId, resourceType, ftpId);
		Element element = null;
		try {
			element = coreSiteDistributionCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return (CoreSiteDistribution) element.getValue();
		}
	}

	public List<CoreSiteDistribution> getDistributionBySiteAndResource(Long siteId, String resourceType) {
		List<String> keys = coreSiteDistributionCache.getKeys();
		List<CoreSiteDistribution> ftpList = new ArrayList<CoreSiteDistribution>();
		String matchKey = getSiteDistributionCacheKey(siteId, resourceType);
		for (String key : keys) {
			if (key.startsWith(matchKey)) {
				ftpList.add(getSiteDistributionFromCache(key));
			}
		}
		return ftpList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#getResourceStringFromCache(java
	 * .lang.String)
	 */
	public String getResourceStringFromCache(String resCode) {
		CoreResource res = getResourceFromCache(resCode);
		if (res != null)
			return res.getResourcestring();
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#putResourceInCache(com.cyberway
	 * .common.domain.CoreResource)
	 */
	public void putResourceInCache(CoreResource resourceDetails) {
		Element element = new Element(resourceDetails.getResourcekey(), resourceDetails);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		coreResourceCache.put(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#removeResourceFromCache(java.
	 * lang.String)
	 */
	public void removeResourceFromCache(String resString) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resString);
		}
		coreResourceCache.remove(resString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#putUserInCache(com.cyberway.common
	 * .domain.CoreUser)
	 */
	public void putUserInCache(CoreUser user) {
		Element element = new Element(user.getUserid().toString(), user);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		coreUserCache.put(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#getUserFromCache(java.lang.String
	 * )
	 */
	public CoreUser getUserFromCache(String userid) {
		Element element = null;

		try {
			element = coreUserCache.get(userid);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (logger.isDebugEnabled()) {
			// logger.debug("Cache hit: " + (element != null) + "; resString: "+
			// resString);
		}

		if (element == null) {
			return null;
		} else {
			return (CoreUser) element.getValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#removeUserFromCache(java.lang
	 * .String)
	 */
	public void removeUserFromCache(String userid) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + userid);
		}
		coreUserCache.remove(userid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.common.service.CommonCache#getAllUsers()
	 */
	public List<CoreUser> getAllUsers() {
		List<String> keys = coreUserCache.getKeys();
		List<CoreUser> cus = new ArrayList();
		if (keys != null && keys.size() > 0) {
			for (String key : keys)
				cus.add(getUserFromCache(key));
		}
		return cus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#putOrgInCache(com.cyberway.common
	 * .domain.CoreOrg)
	 */
	public void putOrgInCache(CoreOrg org) {
		Element element = new Element(org.getOid().toString(), org);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		coreOrgCache.put(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#getOrgFromCache(java.lang.String)
	 */
	public CoreOrg getOrgFromCache(String orgid) {
		Element element = null;

		try {
			element = coreOrgCache.get(orgid);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (logger.isDebugEnabled()) {
			// logger.debug("Cache hit: " + (element != null) + "; resString: "+
			// resString);
		}

		if (element == null) {
			return null;
		} else {
			return (CoreOrg) element.getValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#removeOrgFromCache(java.lang.
	 * String)
	 */
	public void removeOrgFromCache(String orgid) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + orgid);
		}
		coreOrgCache.remove(orgid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.common.service.CommonCache#getAllOrgs()
	 */
	public List<CoreOrg> getAllOrgs() {
		List<String> keys = coreOrgCache.getKeys();
		List<CoreOrg> cus = new ArrayList();
		if (keys != null && keys.size() > 0) {
			for (String key : keys)
				cus.add(getOrgFromCache(key));
		}
		return cus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#putFlowDataInCache(java.lang.
	 * String, java.lang.Object)
	 */
	public void putFlowDataInCache(String key, Object value) {
		Element element = new Element(key, value);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		coreFlowCache.put(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#getFlowDataFromCache(java.lang
	 * .String)
	 */
	public Object getFlowDataFromCache(String key) {
		Element element = null;

		try {
			element = coreFlowCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (logger.isDebugEnabled()) {
			// logger.debug("Cache hit: " + (element != null) + "; resString: "+
			// resString);
		}

		if (element == null) {
			return null;
		} else {
			return element.getValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.common.service.CommonCache#removeFlowDataFromCache(java.
	 * lang.String)
	 */
	public void removeFlowDataFromCache(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + key);
		}
		coreFlowCache.remove(key);
	}

	public List getAllResources() {
		return coreResourceCache.getKeys();
	}

	public void removeAllResources() {
		coreResourceCache.removeAll();
	}

	public void setCoreUserCache(Cache coreUserCache) {
		this.coreUserCache = coreUserCache;
	}

	public void setCoreOrgCache(Cache coreOrgCache) {
		this.coreOrgCache = coreOrgCache;
	}

	public Cache getCoreFlowCache() {
		return coreFlowCache;
	}

	public void setCoreFlowCache(Cache coreFlowCache) {
		this.coreFlowCache = coreFlowCache;
	}

	public Cache getCoreSiteDistributionCache() {
		return coreSiteDistributionCache;
	}

	public void setCoreSiteDistributionCache(Cache coreSiteDistributionCache) {
		this.coreSiteDistributionCache = coreSiteDistributionCache;
	}

	public CoreSiteDistribution getSiteDistributionFromCache(String id) {
		Element element = null;
		try {
			element = coreSiteDistributionCache.get(id);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (logger.isDebugEnabled()) {
			// logger.debug("Cache hit: " + (element != null) + "; resString: "+
			// resString);
		}

		if (element == null) {
			return null;
		} else {
			return (CoreSiteDistribution) element.getValue();
		}
	}

	public List<CoreSiteDistribution> getAllSiteDistributions() {
		List<String> keys = coreSiteDistributionCache.getKeys();
		List<CoreSiteDistribution> csd = new ArrayList();
		if (keys != null && keys.size() > 0) {
			for (String key : keys)
				csd.add(getSiteDistributionFromCache(key));
		}
		return csd;
	}

	public void removeSiteDistributionFromCache(String resString) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resString);
		}
		coreSiteDistributionCache.remove(resString);

	}

	public void removeSiteDistributionFromCache(Long siteId, String resourceType, Long ftpId) {
		String resString = getSiteDistributionCacheKey(siteId, resourceType, ftpId);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resString);
		}
		coreSiteDistributionCache.remove(resString);

	}

	public void removeAllSiteDistributions() {
		coreSiteDistributionCache.removeAll();
	}

	public Element getElementFromCoreResource(String key) {
		Element element = null;

		try {
			element = coreResourceCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}

	public void removeAllCoreUser() {
		coreUserCache.removeAll();
	}

	public List<String> getCoreUserCacheKeys() {
		return coreUserCache.getKeys();
	}

	public Element getElementFromCoreUser(String key) {
		Element element = null;

		try {
			element = coreUserCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}

	public void removeAllCoreOrg() {
		coreOrgCache.removeAll();
	}

	public List<String> getCoreOrgCacheKeys() {
		return coreOrgCache.getKeys();
	}

	public Element getElementFromCoreOrg(String key) {
		Element element = null;

		try {
			element = coreOrgCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}
	
	public void removeAllFtpServer() {
		coreSiteDistributionCache.removeAll();
	}

	public List<String> getFtpServerCacheKeys() {
		return coreSiteDistributionCache.getKeys();
	}

	public Element getElementFromFtpServer(String key) {
		Element element = null;

		try {
			element = coreSiteDistributionCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}

	public Cache getRoleCache() {
    	return roleCache;
    }

	public void setRoleCache(Cache roleCache) {
    	this.roleCache = roleCache;
    }
	
	public void putRoleCacheInCache(CoreRole role) {
		String key = role.getOid() + FLAG_SEP + TYPE + role.getRoleType();
		Element element = new Element(key, role);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		roleCache.put(element);
	}
	
	public void removeAllRole() {
		roleCache.removeAll();
	}

	public List<String> getRoleCacheKeys() {
		return roleCache.getKeys();
	}

	public Element getElementFromRole(String key) {
		Element element = null;

		try {
			element = roleCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}
	
	public CoreRole getRoleFromCache(String id) {
		Element element = null;
		try {
			element = roleCache.get(id);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return (CoreRole) element.getValue();
		}
	}
	
	/**
	 * 根据身份和角色的组合从缓存中获取CoreRole对象
	 * @param searchKey 身份和角色的组合
	 * @return 角色列表
	 */
	public List<CoreRole> getRoleByGrade(String searchKey){
		List<String> keys = roleCache.getKeys();
		List<CoreRole> roleList = new ArrayList<CoreRole>();
		for(String key : keys){
			if(key.indexOf(searchKey) != -1){
				roleList.add(getRoleFromCache(key));
			}
		}
		return roleList;
	}
	
	public boolean removeRoleById(String id){
		boolean flag = false;
		List<String> keys = roleCache.getKeys();
		for(String key : keys){
			if(key.startsWith(id + FLAG_SEP + TYPE)){
				roleCache.remove(key);
				flag = true;
				break;
			}
		}
		return flag;
	}
}
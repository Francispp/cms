package com.cyberway.cms.site.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataRetrievalFailureException;

import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.LogLucene;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.domain.TemplateGather;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.permission.domain.CmsPermission;
import com.cyberway.cms.permission.domain.CmsResource;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.common.domain.CoreArea;
import com.cyberway.common.domain.CoreCommonInfo;
import com.cyberway.common.domain.CoreCommonType;
import com.cyberway.common.media.album.domain.MediaIntermediate;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class EhCacheBasedSiteCache implements SiteCache {

	// ~ Static fields/initializers
	// =============================================

	private static final Log	logger	     = LogFactory.getLog(EhCacheBasedSiteCache.class);
	private static final String	SIGN	     = "_CHANNEL_";

	/**
	 * 频道前缀
	 */
	private static final String	SITE_SIGN	 = "S";

	/**
	 * 频道前缀
	 */
	private static final String	CHANNEL_SIGN	= "C";

	/**
	 * 分隔符
	 */
	private static final String	FLAG_SEP	 = "_";

	// ~ Instance fields
	// ========================================================
	Cache						coreAreaCache;												//地区缓存(省市县)
	Cache	                    coreCommonInfoCache;	                                                       
	Cache	                    coreCommonTypeCache;	                                                       
	Cache	                    cache;	                                                       // 站点缓存
	Cache	                    channelCache;	                                               // 频道缓存
	Cache	                    permissionCache;	                                           // 权限缓冲
	Cache	                    resourceCache;	                                               // 权限资源缓冲
	Cache	                    templateFormCache;	                                           // 模板表单缓冲
	Cache	                    templateUrlCache;	                                           // 模板的缓存
	Cache	                    templateGatherCache;	                                       // 静态采集--增量发布
	Cache	                    luceneCache;	                                               // lucene
	Cache                       mediaCache;                                                    //文档与流媒体对应缓存
	// ~ Methods
	// ================================================================

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public Cache getCache() {
		return this.cache;
	}
	
	public void setCoreAreaCache(Cache coreAreaCache) {
		this.coreAreaCache = coreAreaCache;
	}

	public Cache getMediaCache() {
		return mediaCache;
	}

	public void setMediaCache(Cache mediaCache) {
		this.mediaCache = mediaCache;
	}

	// 获得chnnel在缓冲中的key,格式是：siteid+_CHANNEL_+channelid
	public String getChannelCacheKey(String siteid, String channelid) {
		return siteid + SIGN + channelid;
	}

	/**
	 * 根据站点和资源名称，获得资源Key
	 * 
	 * @param siteid
	 * @param resName
	 * @return
	 */
	public String getStaticResourceKey(String siteid, String resName) {
		return siteid + "_SITE_" + resName;
	}

	/**
	 * 根据频道id,获得的频道对象
	 * 
	 * @param channelid
	 * @return
	 */
	public Channel getChannelFromCach(Long channelid) {
		List keys = cache.getKeys();// 站点keys
		Channel channel = null;
		List<CmsSite> values = new ArrayList();
		for (int i = 0; i < keys.size(); i++) {
			String key = getChannelCacheKey((String) keys.get(i), channelid.toString());
			if (channelCache.getKeys().contains(key)) {
				channel = getChannelFromCache(key);
				break;
			}
		}
		return channel;
	}

	/**
	 * 获得指定站点下所有频道对象
	 * 
	 * @param siteid
	 * @return
	 */
	public List<Channel> getChannelsFromcachBySite(String siteid) {
		List<Channel> channels = new ArrayList();
		List<String> keys = channelCache.getKeys();
		String presix = siteid + SIGN;
		for (String key : keys) {
			if (key.startsWith(presix))
				channels.add(this.getChannelFromCache(key));
		}
		return channels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springside.components.acegi.cache.ResourceCache#getResourceFromCache
	 * (java.lang.String)
	 */
	public CmsSite getSiteFromCache(String resString) {
		Element element = null;

		try {
			element = cache.get(resString);
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
			return (CmsSite) element.getValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springside.components.acegi.cache.ResourceCache#putResourceInCache
	 * (org.springside.components.acegi.model.ResourceDetails)
	 */
	public void putSiteInCache(CmsSite resourceDetails) {
		Element element = new Element(resourceDetails.getOid().toString(), resourceDetails);
		// logger.info("put site cache:"+element.getKey()+"_"+resourceDetails.getSitehttp());
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		cache.put(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springside.components.acegi.cache.ResourceCache#removeResourceFromCache
	 * (java.lang.String)
	 */
	public void removeSiteFromCache(String resString) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resString);
		}
		cache.remove(resString);
	}

	public List getAllSitesKey() {
		return cache.getKeys();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.cms.site.cache.SiteCache#getAllSites()
	 */
	public List getAllSites() {
		List temps = cache.getKeys();
		List keys = new ArrayList();
		for (int i = 0; i < temps.size(); i++) {
			keys.add(new Long((String) temps.get(i)));
		}
		Collections.sort(keys);
		List<CmsSite> values = new ArrayList();
		for (int i = 0; i < keys.size(); i++) {
			values.add(getSiteFromCache(((Long) keys.get(i)).toString()));
		}
		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springside.components.acegi.cache.ResourceCache#putChannelInCache
	 * (CmsSite resourceDetails)
	 */
	public void putChannelInCache(Channel resourceDetails) {
		String key = getChannelCacheKey(resourceDetails.getSite().getOid().toString(), resourceDetails.getId().toString());
		Element element = new Element(key, resourceDetails);
		// logger.info("put site cache:"+element.getKey()+"_"+resourceDetails.getName());
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		channelCache.put(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springside.components.acegi.cache.ResourceCache#removeResourceFromCache
	 * (java.lang.String)
	 */
	public void removeChannelFromCache(String resString) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resString);
		}
		this.channelCache.remove(resString);
	}

	/**
	 * 获得所有频道
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllChannels() {
		List keys = channelCache.getKeys();
		List<Channel> values = new ArrayList();
		for (int i = 0; i < keys.size(); i++) {
			values.add(getChannelFromCache((String) keys.get(i)));
		}
		// 按. SortOrder排序
		java.util.Collections.sort(values, new java.util.Comparator() {
			public int compare(Object chn, Object chn2) {
				int i = 0;
				try {
					long sort1 = ((Channel) chn).getSortOrder();
					long sort2 = ((Channel) chn2).getSortOrder();
					if (sort1 > sort2)
						i = 1;
					else if (sort1 == sort2)
						i = 0;
					else
						i = -1;

				} catch (Exception e) {

				}
				return i;
			}
		});
		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.cms.site.cache.SiteCache#getChannelFromCache(java.lang.String
	 * )
	 */
	public Channel getChannelFromCache(String resString) {
		Element element = null;

		try {
			element = channelCache.get(resString);
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
			return (Channel) element.getValue();
		}
	}

	/**
	 * put Permission　information
	 * 
	 * @param key
	 * @param resourceDetails
	 */
	public void putCmsPermissionInCache(String key, CmsPermission resourceDetails) {
		Element element = new Element(key, resourceDetails);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.permissionCache.put(element);
	}

	/**
	 * 从缓冲中获得权限信息
	 * 
	 * @param resString
	 * @return
	 */
	public CmsPermission getCmsPermissionFromCache(String resString) {
		Element element = null;

		try {
			// logger.info("CacheManager:"+permissionCache.getCacheManager().getName());
			element = permissionCache.get(resString);
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
			return (CmsPermission) element.getValue();
		}
	}

	/**
	 * 移除指定权限信息
	 * 
	 * @param resString
	 */
	public void removeCmsPermissionFromCache(String resString) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resString);
		}
		this.permissionCache.remove(resString);
	}

	/**
	 * 从缓冲中获得资源
	 * 
	 * @param resString
	 * @return
	 */
	public CmsResource getResourceFromCache(String resString) {
		Element element = null;

		try {
			element = resourceCache.get(resString);
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
			return (CmsResource) element.getValue();
		}
	}

	/**
	 * 获得所有资源主健
	 * 
	 * @return
	 */
	public List getAllResourcesKey() {
		return resourceCache.getKeys();
	}

	/**
	 * 获得静态资源串
	 * 
	 * @param resKey
	 * @return
	 */
	public String getStaticResourceFromCache(String resKey) {
		Element element = null;

		try {
			element = resourceCache.get(resKey);
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
			return (String) element.getValue();
		}
	}

	/**
	 * put static Resource to cache
	 * 
	 * @param resKey
	 * @param value
	 */
	public void putStaticResourceInCache(String resKey, String value) {
		Element element = new Element(resKey, value);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.resourceCache.put(element);
	}

	/**
	 * 移除指定静态资源
	 * 
	 * @param resString
	 */
	public void removeStaticResourceFromCache(String resKey) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resKey);
		}
		this.resourceCache.remove(resKey);
	}

	/**
	 * 设置资源到缓冲中
	 * 
	 * @param resourceDetails
	 */
	public void putResourceInCache(CmsResource resourceDetails) {
		Element element = new Element(resourceDetails.getResourceCode(), resourceDetails);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.resourceCache.put(element);
	}

	// 表单模板缓冲
	/**
	 * put CoreForm　information
	 * 
	 * @param key
	 *            --模板id
	 * @param resourceDetails
	 *            --表单对象
	 */
	public void putTemplateFormInCache(String key, CoreForm resourceDetails) {
		Element element = new Element(key, resourceDetails);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.templateFormCache.put(element);
	}

	/**
	 * 从缓冲中获得模板表单信息
	 * 
	 * @param resString
	 * @return
	 */
	public CoreForm getTemplateFormFromCache(String resString) {
		Element element = null;

		try {
			element = templateFormCache.get(resString);
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
			return (CoreForm) element.getValue();
		}
	}

	/**
	 * 移除指定模板表单
	 * 
	 * @param resString
	 */
	public void removeTemplateFormFromCache(String resKey) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resKey);
		}
		this.templateFormCache.remove(resKey);
	}

	/**
	 * put template url to cache
	 * 
	 * @param key
	 *            --templateId
	 * @param url
	 *            --template Url
	 */
	public void putTemplateUrlInCache(String key, String url) {
		Element element = new Element(key, url);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.templateUrlCache.put(element);
	}

	/**
	 * 从缓冲中获得模板url
	 * 
	 * @param resString
	 * @return
	 */
	public String getTemplateUrlFromCache(String resString) {
		againInitTemplateUrlCache();
		Element element = null;

		try {
			element = templateUrlCache.get(resString);
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
			return (String) element.getValue();
		}
	}

	/**
	 * 移除指定url
	 * 
	 * @param resKey
	 */
	public void removeTemplateUrlFromCache(String resKey) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resKey);
		}
		this.templateUrlCache.remove(resKey);
	}

	/**
	 * put template object to cache
	 * 
	 * @param key
	 * @param template
	 */
	public void putTemplateObjectInCache(String key, Template template) {
		Element element = new Element(key, template);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.templateUrlCache.put(element);
	}

	/**
	 * 从缓冲中获得模板 object
	 * 
	 * @param resString
	 * @return
	 */
	public Template getTemplateObjectFromCache(String resString) {
		againInitTemplateUrlCache();
		Element element = null;

		try {
			element = templateUrlCache.get(resString);
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
			Object obj = element.getValue();
			// return (Template) obj;
			if (obj instanceof Template)
				return (Template) obj;
			return null;
		}
	}

	/**
	 * 移除指定template object
	 * 
	 * @param resKey
	 */
	public void removeTemplateObjectFromCache(String resKey) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resKey);
		}
		// 若缓存中存在，则删除缓存
		if (templateUrlCache.getKeys().contains(resKey))
			this.templateUrlCache.remove(resKey);
	}

	public Cache getChannelCache() {
		return channelCache;
	}

	public void setChannelCache(Cache channelCache) {
		this.channelCache = channelCache;
	}

	public Cache getPermissionCache() {
		return permissionCache;
	}

	public void setPermissionCache(Cache permissionCache) {
		this.permissionCache = permissionCache;
	}

	public Cache getResourceCache() {
		return resourceCache;
	}

	public void setResourceCache(Cache resourceCache) {
		this.resourceCache = resourceCache;
	}

	public void setTemplateFormCache(Cache templateFormCache) {
		this.templateFormCache = templateFormCache;
	}

	public Cache getTemplateUrlCache() {
		return templateUrlCache;
	}

	public void setTemplateUrlCache(Cache templateUrlCache) {
		this.templateUrlCache = templateUrlCache;
	}

	public Cache getTemplateGatherCache() {
		return templateGatherCache;
	}

	public void setTemplateGatherCache(Cache templateGatherCache) {
		this.templateGatherCache = templateGatherCache;
	}

	public CmsSite getSiteFromCache(String siteHttp, int port) {
		Element element = null;
		List keys = cache.getKeys();
		for (int i = 0; i < keys.size(); i++) {
			String id = (String) keys.get(i);
			CmsSite site = getSiteFromCache(id);
			// && site.getSiteport().intValue()==port
			if (site != null && site.getSitehttp().equalsIgnoreCase(siteHttp)) {
				element = cache.get(keys.get(i));
				break;
			}
			// 增加查找扩展域名,多个域名之间用;分隔
			if (element == null && site != null && !StringUtil.isEmpty(site.getDomainNames())) {
				String[] domainNames = site.getDomainNames().split(";");
				for(int j = 0; j < domainNames.length; j++){
					if(StringUtils.equalsIgnoreCase(domainNames[j], siteHttp)){
						element = cache.get(keys.get(i));
						break;
					}
				}
			}
		}
		if (element == null) {
			return null;
		} else {
			return (CmsSite) element.getValue();
		}
	}

	/*
	 * public CmsSite getSiteFromCache(String siteHttp,int port) { Element
	 * element = null; List keys=cache.getKeys(); for(int
	 * i=0;i<keys.size();i++){ String id=(String)keys.get(i); CmsSite
	 * site=getSiteFromCache(id); //&& site.getSiteport().intValue()==port
	 * if(site!=null && site.getSitehttp().equalsIgnoreCase(siteHttp)){ element
	 * =cache.get(keys.get(i)); break; } } if (element == null) { return null; }
	 * else { return (CmsSite) element.getValue(); } }
	 */

	public CmsSite getSiteFromMainSite(String siteHttp) {
		List<CmsSite> list = getAllSites();
		CmsSite rsite = null;
		for (CmsSite site : list) {
			if (site != null && site.getSitehttp().equalsIgnoreCase(siteHttp)) {
				rsite = site;
				break;
			} else if (site != null && !StringUtil.isEmpty(site.getDomainNames())) {
				String[] domainNames = site.getDomainNames().split(";");
				for(int i = 0; i < domainNames.length; i++){
					if(StringUtils.equalsIgnoreCase(domainNames[i], siteHttp)){
						rsite = site;
						break;
					}
				}
			}
		}
		return rsite;
	}

	/**
	 * 根据频道id,模板类型,是否静态采集,模板id,获取模板Key
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @return
	 */
	private String getTemplateKey(Long channelId, int templateType, Boolean isPublishStatic, Long templateId) {
		if(isPublishStatic == null)
			isPublishStatic = false;
		if (templateType == Template.TYPE_ANY || templateType == Template.TYPE_INDEX) {
			return isPublishStatic ? SITE_SIGN + channelId + FLAG_SEP + templateType + FLAG_SEP + "1" + FLAG_SEP + templateId : SITE_SIGN
			        + channelId + FLAG_SEP + templateType + FLAG_SEP + "0" + FLAG_SEP + templateId;
		} else {
			return isPublishStatic ? CHANNEL_SIGN + channelId + FLAG_SEP + templateType + FLAG_SEP + "1" + FLAG_SEP + templateId
			        : CHANNEL_SIGN + channelId + FLAG_SEP + templateType + FLAG_SEP + "0" + FLAG_SEP + templateId;
		}
		
	}

	/**
	 * 根据频道id,模板类型,是否静态采集,获取模板Key
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @return
	 */
	private String getTemplateKey(Long channelId, int templateType, Boolean isPublishStatic) {
		if (templateType == Template.TYPE_ANY || templateType == Template.TYPE_INDEX) {
			return isPublishStatic ? SITE_SIGN + channelId + FLAG_SEP + templateType + FLAG_SEP + "1" + FLAG_SEP : SITE_SIGN + channelId
			        + FLAG_SEP + templateType + FLAG_SEP + "0" + FLAG_SEP;
		} else {
			return isPublishStatic ? CHANNEL_SIGN + channelId + FLAG_SEP + templateType + FLAG_SEP + "1" + FLAG_SEP : CHANNEL_SIGN
			        + channelId + FLAG_SEP + templateType + FLAG_SEP + "0" + FLAG_SEP;
		}
	}

	/**
	 * 根据频道id,模板类型,获取模板Key
	 * 
	 * @param channelId
	 * @param templateType
	 * @return
	 */
	private String getTemplateKey(Long channelId, int templateType) {
		if (templateType == Template.TYPE_ANY || templateType == Template.TYPE_INDEX) {
			return SITE_SIGN + channelId + FLAG_SEP + templateType + FLAG_SEP;
		} else {
			return CHANNEL_SIGN + channelId + FLAG_SEP + templateType + FLAG_SEP;
		}
	}

	/**
	 * 
	 * 根据频道id,获取模板Key
	 * 
	 * @param channelId
	 * @return
	 */
	private String getTemplateKeyByChannelId(Long channelId) {
		return CHANNEL_SIGN + channelId + FLAG_SEP;
	}

	/**
	 * 
	 * 根据频道id,获取模板Key
	 * 
	 * @param channelId
	 * @return
	 */
	private String getTemplateKeyBySiteId(Long siteId) {
		return SITE_SIGN + siteId + FLAG_SEP;
	}

	/**
	 * 根据频道id,模板类型,是否静态采集,获取包含模板的列表
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @return
	 */
	public List<Template> getTemplateList(Long channelId, int templateType, Boolean isPublishStatic) {
		againInitTemplateUrlCache();
		List<Template> templateList = new ArrayList();
		List<String> keys = templateUrlCache.getKeys();
		String presix = getTemplateKey(channelId, templateType, isPublishStatic);
		for (String key : keys) {
			if (key.startsWith(presix))
				templateList.add(getTemplateObjectFromCache(key));
		}
		return templateList;
	}

	/**
	 * 根据频道id,模板类型,获取包含模板的列表
	 * 
	 * @param channelId
	 * @param templateType
	 * @return
	 */
	public List<Template> getTemplateList(Long channelId, int templateType) {
		againInitTemplateUrlCache();
		List<Template> templateList = new ArrayList();
		List<String> keys = templateUrlCache.getKeys();
		String presix = getTemplateKey(channelId, templateType);
		for (String key : keys) {
			if (key.startsWith(presix))
				templateList.add(getTemplateObjectFromCache(key));
		}
		return templateList;
	}

	/**
	 * 根据频道id,获取包含模板的列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<Template> getTemplateListByChannelId(Long channelId) {
		againInitTemplateUrlCache();
		List<Template> templateList = new ArrayList();
		List<String> keys = templateUrlCache.getKeys();
		String presix = getTemplateKeyByChannelId(channelId);
		for (String key : keys) {
			if (key.startsWith(presix))
				templateList.add(getTemplateObjectFromCache(key));
		}
		return templateList;
	}

	/**
	 * 根据频道id,获取包含模板的列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<Template> getTemplateListBySiteId(Long siteId) {
		againInitTemplateUrlCache();
		List<Template> templateList = new ArrayList();
		List<String> keys = templateUrlCache.getKeys();
		String presix = getTemplateKeyBySiteId(siteId);
		for (String key : keys) {
			if (key.startsWith(presix))
				templateList.add(getTemplateObjectFromCache(key));
		}
		return templateList;
	}

	/**
	 * put template object to cache
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @param templateId
	 * @param template
	 */
	public void putTemplate(Long channelId, int templateType, Boolean isPublishStatic, Long templateId, Template template) {
		Element element = new Element(getTemplateKey(channelId, templateType, isPublishStatic, templateId), template);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.templateUrlCache.put(element);
	}

	/**
	 * 根据频道id,获取包含模板的列表
	 * 
	 * @param channelId
	 * @return
	 */
	public Template getTemplate(Long templateId) {
		againInitTemplateUrlCache();
		List<Template> templateList = new ArrayList();
		List<String> keys = templateUrlCache.getKeys();
		String presix = FLAG_SEP + templateId;
		for (String key : keys) {
			if (key.endsWith(presix))
				return getTemplateObjectFromCache(key);
		}
		return null;
	}

	/**
	 * 根据频道id,模板类型,是否静态采集,模板id,从缓存中获取模板
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @param templateId
	 * @return
	 */
	public Template getTemplate(Long channelId, int templateType, Boolean isPublishStatic, Long templateId) {
		Element element = null;
		againInitTemplateUrlCache();
		try {
			element = templateUrlCache.get(getTemplateKey(channelId, templateType, isPublishStatic, templateId));
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return (Template) element.getValue();
		}
	}

	/**
	 * 当模板缓存的数量为0时,重新初始化cache
	 */
	private void againInitTemplateUrlCache() {
		if (templateUrlCache.getSize() == 0) {
			TemplateManagerService templateManageService = (TemplateManagerService) ServiceLocator.getBean("templateManagerService");
			templateManageService.init();
		}
	}

	/**
	 * 删除模板缓存
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @param templateId
	 */
	public void removeTemplate(Long channelId, int templateType, Boolean isPublishStatic, Long templateId) {
		if(isPublishStatic == null)
			isPublishStatic = false;
		String key = getTemplateKey(channelId, templateType, isPublishStatic, templateId);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + key);
		}
		this.templateUrlCache.remove(key);
	}

	public TemplateGather getTemplateGather(String resKeys) {
		Element element = null;
		try {
			element = this.templateGatherCache.get(resKeys);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}
		if (logger.isDebugEnabled()) {
		}
		if (element == null) {
			return null;
		} else {
			return (TemplateGather) element.getValue();
		}
	}

	public void putTemplateGather(TemplateGather templateGather) {
		String key = getTemplateGatherKey(templateGather);
		Element element = new Element(key, templateGather);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.templateGatherCache.put(element);
	}

	public void removeTemplateGather(String resKeys) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resKeys);
		}
		this.templateGatherCache.remove(resKeys);
	}

	/**
	 * 静态采集--缓存templateGather时，生成Key
	 * 
	 * @param templateGather
	 * @return
	 */
	private String getTemplateGatherKey(TemplateGather templateGather) {
		return templateGather.getIncludeChannelId() + "_" + templateGather.getTemplateType() + "_" + templateGather.getTemplateId() + "_"
		        + templateGather.getChannelId();
	}

	public List<TemplateGather> getTemplateGathers(String resKeys) {
		List<TemplateGather> list = new ArrayList<TemplateGather>();

		List<String> keys = this.templateGatherCache.getKeys();
		for (String key : keys) {
			if (key.startsWith(resKeys)) {
				list.add(getTemplateGather(key));
			}
		}
		return list;
	}

	public String getTemplateGathersKey(Long channelId, int templateType) {
		return channelId + "_" + templateType;
	}

	public void putLuneceCache(LogLucene logLucene) {
		Element element = new Element(logLucene.getFileName(), logLucene);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		luceneCache.put(element);

	}

	public void removeLuneceFromCache(String logLuceneKey) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + logLuceneKey);
		}
		luceneCache.remove(logLuceneKey);

	}

	public List getAllLuneces() {
		List<LogLucene> list = new ArrayList<LogLucene>();

		List<String> keys = luceneCache.getKeys();
		for (String key : keys) {
			list.add(getLucene(key));
		}
		return list;
	}

	private LogLucene getLucene(String logLuceneKey) {
		Element element = null;
		try {
			element = luceneCache.get(logLuceneKey);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}
		if (logger.isDebugEnabled()) {
		}
		if (element == null) {
			return null;
		} else {
			return (LogLucene) element.getValue();
		}
	}

	public Cache getLuceneCache() {
		return luceneCache;
	}

	public void setLuceneCache(Cache luceneCache) {
		this.luceneCache = luceneCache;
	}

	public void removeAllSites() {
		cache.removeAll();
	}

	public void removeAllChannel() {
		channelCache.removeAll();
	}

	public void removeAllPermission() {
		permissionCache.removeAll();
	}

	public void removeAllResource() {
		resourceCache.removeAll();
	}

	public void removeAllTemplateForm() {
		templateFormCache.removeAll();
	}

	public void removeAllTemplateUrl() {
		templateUrlCache.removeAll();
	}

	public void removeAllTemplateGather() {
		templateGatherCache.removeAll();
	}

	public void removeAllLucene() {
		luceneCache.removeAll();
	}

	public List<String> getTemplateFormCacheKeys() {
		return templateFormCache.getKeys();
	}
	
	public Element getElementFromTemplateForm(String key) {
		Element element = null;

		try {
			element = templateFormCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}

	public List<String> getTemplateUrlCacheKeys() {
		return templateUrlCache.getKeys();
	}
	
	public Element getElementFromTemplate(String key) {
		Element element = null;

		try {
			element = templateUrlCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}
	
	public Element getElementFromSite(String key) {
		Element element = null;

		try {
			element = cache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}
	
	public List<String> getChannelCacheKeys() {
		return channelCache.getKeys();
	}
	
	public Element getElementFromChannel(String key) {
		Element element = null;

		try {
			element = channelCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}
	
	public List<String> getPermissionCacheKeys() {
		return permissionCache.getKeys();
	}
	
	public Element getElementFromPermission(String key) {
		Element element = null;

		try {
			element = permissionCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}
	
	public Element getElementFromResource(String key) {
		Element element = null;

		try {
			element = resourceCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}
	
	public List<String> getTemplateGatherCacheKeys() {
		return templateGatherCache.getKeys();
	}
	
	public Element getElementFromTemplateGather(String key) {
		Element element = null;

		try {
			element = templateGatherCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}
	
	
	
	public List getAllMediaKeys() {
		return mediaCache.getKeys();
	}


	public Element getElementFromMedia(String key) {
		Element element = null;

		try {
			element = mediaCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("CacheMedia failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return element;
		}
	}
	
	public MediaIntermediate getMeidaFromCache(String key) {
		Element element = null;
		try {
			element = mediaCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return (MediaIntermediate) element.getValue();
		}
	}
	
	public List<MediaIntermediate> getMediaIntermediateByStartWith(String resKeys) {
		List<MediaIntermediate> list = new ArrayList<MediaIntermediate>();
		List<String> keys = this.mediaCache.getKeys();
		for (String key : keys) {
			if (key.startsWith(resKeys)) {
				list.add(getMeidaFromCache(key));
			}
		}
		return list;
	}

	public List<MediaIntermediate> getMediaIntermediateByEndWith(String resKeys) {
		List<MediaIntermediate> list = new ArrayList<MediaIntermediate>();
		List<String> keys = this.mediaCache.getKeys();
		for (String key : keys) {
			if (key.endsWith(resKeys)) {
				list.add(getMeidaFromCache(key));
			}
		}
		return list;
	}


	public void putMedia(MediaIntermediate mediaIntermediate) {
		Element element = new Element(getMediaKey(mediaIntermediate),mediaIntermediate);
		if (logger.isDebugEnabled()) {
			logger.debug("CacheMedia put: " + element.getKey());
		}
		mediaCache.put(element);	
	}

	public void removeMedia(MediaIntermediate mediaIntermediate) {
		if (logger.isDebugEnabled()) {
			logger.debug("CacheMedia remove: " + getMediaKey(mediaIntermediate));
		}
		mediaCache.remove(getMediaKey(mediaIntermediate));
		
	}

	public void removeAllMedias() {
		mediaCache.removeAll();
	}
	
	private String getMediaKey(MediaIntermediate mediaIntermediate){
		return mediaIntermediate.getDocId()+"_"+mediaIntermediate.getType()+"_"+mediaIntermediate.getMediaId();
	}

	public CoreCommonInfo getCoreCommonInfoFromCache(String key) {
		Element element = null;
		try {
			element = coreCommonInfoCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}
		return element != null ? (CoreCommonInfo) element.getValue() : null;
	}

	public CoreCommonType getCoreCommonTypeFromCache(String key) {
		Element element = null;
		try {
			element = coreCommonTypeCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}
		return element != null ? (CoreCommonType) element.getValue() : null;
	}

	public void putCoreCommonInfoInCache(CoreCommonInfo coreCommonInfo) {
		Element element = new Element(coreCommonInfo.getId().toString(), coreCommonInfo);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		coreCommonInfoCache.put(element);
		if(coreCommonInfo.getCoreCommonType()!=null){
			Element element1 = new Element("keyword_"+coreCommonInfo.getCoreCommonType().getKeyword()+"_code_"+coreCommonInfo.getCode(), coreCommonInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Cache put: " + element1.getKey());
			}
			coreCommonInfoCache.put(element1);
		}
		Element element2 = new Element("typeId_"+coreCommonInfo.getCoreCommonType().getId()+"_code_"+coreCommonInfo.getCode(), coreCommonInfo);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element2.getKey());
		}
		coreCommonInfoCache.put(element2);
	}

	public void putCoreCommonTypeInCache(CoreCommonType coreCommonType) {
		Element element = new Element(coreCommonType.getId().toString(), coreCommonType);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		coreCommonTypeCache.put(element);
	}
	
	public void putCoreAreaInCache(CoreArea coreArea) {
		Element element = new Element(coreArea.getId(), coreArea);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		coreAreaCache.put(element);
	}
	
	public CoreArea getCoreAreaFromCache(String key) {
		Element element = null;
		try {
			element = coreAreaCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}
		return element != null ? (CoreArea) element.getValue() : null;
	}

	public Cache getCoreCommonInfoCache() {
		return coreCommonInfoCache;
	}

	public void setCoreCommonInfoCache(Cache coreCommonInfoCache) {
		this.coreCommonInfoCache = coreCommonInfoCache;
	}

	public Cache getCoreCommonTypeCache() {
		return coreCommonTypeCache;
	}

	public void setCoreCommonTypeCache(Cache coreCommonTypeCache) {
		this.coreCommonTypeCache = coreCommonTypeCache;
	}

}
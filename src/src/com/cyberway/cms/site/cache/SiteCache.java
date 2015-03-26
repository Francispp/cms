package com.cyberway.cms.site.cache;

import java.util.List;

import net.sf.ehcache.Element;

import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.LogLucene;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.domain.TemplateGather;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.permission.domain.CmsPermission;
import com.cyberway.cms.permission.domain.CmsResource;
import com.cyberway.common.domain.CoreArea;
import com.cyberway.common.domain.CoreCommonInfo;
import com.cyberway.common.domain.CoreCommonType;
import com.cyberway.common.media.album.domain.MediaIntermediate;

/**
 * 站点缓存
 * 
 */
public interface SiteCache {
	
	public CoreArea getCoreAreaFromCache(String key);
	
	public void putCoreAreaInCache(CoreArea coreArea);
	
	public CoreCommonInfo getCoreCommonInfoFromCache(String key);
	
	public CoreCommonType getCoreCommonTypeFromCache(String key);
	
	public void putCoreCommonInfoInCache(CoreCommonInfo coreCommonInfo);
	
	public void putCoreCommonTypeInCache(CoreCommonType coreCommonType);
	
	public CmsSite getSiteFromMainSite(String siteHttp);

	public CmsSite getSiteFromCache(String siteKey);

	public CmsSite getSiteFromCache(String siteHttp, int port);

	public void putSiteInCache(CmsSite site);

	public void removeSiteFromCache(String siteKey);

	public List getAllSites();

	public List getAllChannels();

	public void removeChannelFromCache(String resString);

	public void putChannelInCache(Channel resourceDetails);

	// 获得chnnel在缓冲中的key,格式是：siteid+_CHANNEL_+channelid
	public String getChannelCacheKey(String siteid, String channelid);

	public Channel getChannelFromCache(String resString);

	/**
	 * 根据频道id,获得的频道对象
	 * 
	 * @param channelid
	 * @return
	 */
	public Channel getChannelFromCach(Long channelid);

	/**
	 * 获得指定站点下所有频道对象
	 * 
	 * @param siteid
	 * @return
	 */
	public List<Channel> getChannelsFromcachBySite(String siteid);

	/**
	 * put Permission　information
	 * 
	 * @param key
	 * @param resourceDetails
	 */
	public void putCmsPermissionInCache(String key, CmsPermission resourceDetails);

	/**
	 * 从缓冲中获得权限信息
	 * 
	 * @param resString
	 * @return
	 */
	public CmsPermission getCmsPermissionFromCache(String resString);

	/**
	 * 移除指定权限信息
	 * 
	 * @param resString
	 */
	public void removeCmsPermissionFromCache(String resString);

	/**
	 * 从缓冲中获得资源
	 * 
	 * @param resString
	 * @return
	 */
	public CmsResource getResourceFromCache(String resString);

	/**
	 * 获得所有资源主健
	 * 
	 * @return
	 */
	public List getAllResourcesKey();

	/**
	 * 设置资源到缓冲中
	 * 
	 * @param resourceDetails
	 */
	public void putResourceInCache(CmsResource resourceDetails);

	/**
	 * 获得静态资源串
	 * 
	 * @param resKey
	 * @return
	 */
	public String getStaticResourceFromCache(String resKey);

	/**
	 * put static Resource to cache
	 * 
	 * @param resKey
	 * @param value
	 */
	public void putStaticResourceInCache(String resKey, String value);

	/**
	 * 根据站点和资源名称，获得资源Key
	 * 
	 * @param siteid
	 * @param resName
	 * @return
	 */
	public String getStaticResourceKey(String siteid, String resName);

	/**
	 * 移除指定静态资源
	 * 
	 * @param resString
	 */
	public void removeStaticResourceFromCache(String resKey);

	/**
	 * 从缓冲中获得模板表单信息
	 * 
	 * @param resString
	 * @return
	 */
	public CoreForm getTemplateFormFromCache(String resString);

	/**
	 * put CoreForm　information
	 * 
	 * @param key
	 *            --模板id
	 * @param resourceDetails
	 *            --表单对象
	 */
	public void putTemplateFormInCache(String key, CoreForm resourceDetails);

	/**
	 * 移除指定模板表单
	 * 
	 * @param resString
	 */
	public void removeTemplateFormFromCache(String resKey);

	/**
	 * put template url to cache
	 * 
	 * @param key
	 *            --templateId
	 * @param url
	 *            --template Url
	 */
	public void putTemplateUrlInCache(String key, String url);

	/**
	 * 移除指定url
	 * 
	 * @param resKey
	 */
	public void removeTemplateUrlFromCache(String resKey);

	/**
	 * 从缓冲中获得模板url
	 * 
	 * @param resString
	 * @return
	 */
	public String getTemplateUrlFromCache(String resString);

	/**
	 * put template object to cache
	 * 
	 * @param key
	 * @param template
	 */
	public void putTemplateObjectInCache(String key, Template template);

	/**
	 * 从缓冲中获得模板 object
	 * 
	 * @param resString
	 * @return
	 */
	public Template getTemplateObjectFromCache(String resString);

	/**
	 * 移除指定template object
	 * 
	 * @param resKey
	 */
	public void removeTemplateObjectFromCache(String resKey);

	/**
	 * 根据频道id,模板类型,是否静态采集,获取包含模板的列表
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @return
	 */
	public List<Template> getTemplateList(Long channelId, int templateType, Boolean isPublishStatic);

	/**
	 * 根据频道id,模板类型,获取包含模板的列表
	 * 
	 * @param channelId
	 * @param templateType
	 * @return
	 */
	public List<Template> getTemplateList(Long channelId, int templateType);

	/**
	 * 根据频道id,获取包含模板的列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<Template> getTemplateListByChannelId(Long channelId);

	/**
	 * 根据频道id,获取包含模板的列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<Template> getTemplateListBySiteId(Long siteId);

	/**
	 * put template object to cache
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @param templateId
	 * @param template
	 */
	public void putTemplate(Long channelId, int templateType, Boolean isPublishStatic, Long templateId, Template template);

	/**
	 * 根据频道id,获取包含模板的列表
	 * 
	 * @param channelId
	 * @return
	 */
	public Template getTemplate(Long templateId);

	/**
	 * 根据频道id,模板类型,是否静态采集,模板id,从缓存中获取模板
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @param templateId
	 * @return
	 */
	public Template getTemplate(Long channelId, int templateType, Boolean isPublishStatic, Long templateId);

	/**
	 * 删除模板缓存
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @param templateId
	 */
	public void removeTemplate(Long channelId, int templateType, Boolean isPublishStatic, Long templateId);

	/**
	 * 静态采集--根据key得到TemplateGather
	 * 
	 * @param resKeys
	 * @return
	 */
	public TemplateGather getTemplateGather(String resKeys);

	/**
	 * 静态采集--添加
	 * 
	 * @param templateGather
	 */
	public void putTemplateGather(TemplateGather templateGather);

	/**
	 * 静态采集--移除
	 * 
	 * @param templateGather
	 */
	public void removeTemplateGather(String resKeys);

	/**
	 * 静态采集--根据Key得到是有的TemplateGather
	 * 
	 * @param resKeys
	 * @return
	 */
	public List<TemplateGather> getTemplateGathers(String resKeys);

	/**
	 * 静态采集--得到Key
	 * 
	 * @param channelId
	 * @param templateId
	 * @param templateType
	 * @return
	 */
	public String getTemplateGathersKey(Long channelId, int templateType);

	public void putLuneceCache(LogLucene logLucene);

	public void removeLuneceFromCache(String logLuceneKey);

	public List getAllLuneces();

	/**
	 * 清除所有站点缓存
	 */
	public void removeAllSites();

	/**
	 * 清除所有频道缓存
	 */
	public void removeAllChannel();

	/**
	 * 清除所有权限缓存
	 */
	public void removeAllPermission();

	/**
	 * 清除所有资源缓存
	 */
	public void removeAllResource();

	/**
	 * 清除所有模板表单缓存
	 */
	public void removeAllTemplateForm();

	/**
	 * 清除所有模板缓存
	 */
	public void removeAllTemplateUrl();

	/**
	 * 
	 */
	public void removeAllTemplateGather();

	/**
	 * 清除所有Lucene缓存
	 */
	public void removeAllLucene();

	/**
	 * 获取所有模板表单缓存的keys
	 * 
	 * @return
	 */
	public List<String> getTemplateFormCacheKeys();

	/**
	 * 根据key从模板表单缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromTemplateForm(String key);

	/**
	 * 从模板缓存中获得所有的key
	 * 
	 * @return
	 */
	public List<String> getTemplateUrlCacheKeys();

	/**
	 * 根据key从模板缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromTemplate(String key);

	/**
	 * 获取所有站点缓存的key
	 * 
	 * @return
	 */
	public List getAllSitesKey();

	/**
	 * 根据key从站点缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromSite(String key);

	/**
	 * 从频道缓存中获得所有的key
	 * 
	 * @return
	 */
	public List<String> getChannelCacheKeys();

	/**
	 * 
	 * 根据key从频道缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromChannel(String key);

	/**
	 * 从权限缓存中获得所有的key
	 * 
	 * @return
	 */
	public List<String> getPermissionCacheKeys();

	/**
	 * 根据key从权限缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromPermission(String key);
	
	/**
	 * 根据key从资源缓存中获得对应的缓存
	 * @param key
	 * @return
	 */
	public Element getElementFromResource(String key);
	
	/**
	 * @return
	 */
	public List<String> getTemplateGatherCacheKeys();
	
	/**
	 * @param key
	 * @return
	 */
	public Element getElementFromTemplateGather(String key);
	
	/**
	 * 添加文档与流媒体对应缓存
	 */
	public void putMedia(MediaIntermediate mediaIntermediate);
	
	/**
	 * 清除文档与流媒体对应缓存
	 */
	public void removeMedia(MediaIntermediate mediaIntermediate);
	/**
	 * 清除所有文档与流媒体对应缓存
	 */
	public void removeAllMedias();
	/**
	 * 获取所有流媒体缓存的key
	 * 
	 * @return
	 */
	public List getAllMediaKeys();

	/**
	 * 根据key【documentId】从流媒体缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public List<MediaIntermediate> getMediaIntermediateByStartWith(String resKeys);
	
	/**
	 * 根据key【mediaType_mediaId】从流媒体缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public List<MediaIntermediate> getMediaIntermediateByEndWith(String resKeys);
	
	public Element getElementFromMedia(String key);
	/**
	 * 根据key从流媒体缓存中获得对应的缓存
	 * 
	 * @param docId+video|audio+标签名字
	 * @return
	 */
	public MediaIntermediate getMeidaFromCache(String key);
}
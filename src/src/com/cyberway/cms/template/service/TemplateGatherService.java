package com.cyberway.cms.template.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.TemplateGather;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.core.dao.HibernateEntityDao;

public class TemplateGatherService extends HibernateEntityDao<TemplateGather> {
	private SiteCache siteCache;

	public SiteCache getSiteCache() {
		return siteCache;
	}

	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}
	
	
	public Channel getChannel(Long channleId){
		return siteCache.getChannelFromCach(channleId);
	}
	/**
	 * 静态采集中间表初始化
	 */
	public synchronized void init(){
		List<TemplateGather> templateGathers=getAll();
		if(!templateGathers.isEmpty()){			
			for(TemplateGather templateGather:templateGathers){
				siteCache.putTemplateGather(templateGather);
			}
		}
	}
	
	public void delete(List<Long> ids)throws Exception{
		if(ids!=null){
			TemplateGather templateGather;
			for(Long id:ids){
				templateGather = get(id);
				if(templateGather!=null){
					siteCache.removeTemplateGather(getTemplateGatherKey(templateGather));
					remove(templateGather);
				}
			}
		}
	}
	
	public void deleteOfTemplate(Long templateId){
		List<TemplateGather> templateGathers=find("from TemplateGather where templateId=?", templateId);
		for(TemplateGather templateGather:templateGathers){
			siteCache.removeTemplateGather(getTemplateGatherKey(templateGather));
			remove(templateGather);
		}
	}
	
	public void save(TemplateGather templateGather)throws Exception{
		saveOrUpdate(templateGather);
		siteCache.putTemplateGather(templateGather);
	}
	
	private String getTemplateGatherKey(TemplateGather templateGather){
		return templateGather.getIncludeChannelId()+"_"+templateGather.getTemplateType()+"_"+templateGather.getTemplateId()+"_"+templateGather.getChannelId();
	}
	
	/**
	 * 清除所有缓存
	 */
	public void removeAllCache(){
		siteCache.removeAllTemplateGather();
	}
	
	/**
	 * 获取所有缓存中的key
	 * @return
	 */
	public List<String> getAllCacheKeys(){
		return siteCache.getTemplateGatherCacheKeys();
	}
	
	/**
	 * 获得指定的缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)){
			net.sf.ehcache.Element element = null;
			element = siteCache.getElementFromTemplateGather(key);
			return element;
		}
		else
			return null;
	}
}

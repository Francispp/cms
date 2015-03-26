package com.cyberway.common.area.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.common.domain.CoreArea;
import com.cyberway.core.dao.HibernateEntityDao;

public class AreaService extends HibernateEntityDao<CoreArea> {
	
	private SiteCache siteCache;
	
	public synchronized void init(){
		List<CoreArea> coreAreaList=getAll();
		if(!coreAreaList.isEmpty()){			
			for(CoreArea area:coreAreaList){
				siteCache.putCoreAreaInCache(area);
			}
		}
	}
	
	/**
	 * 根据市来查找下面的县
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CoreArea> getCounties(String id){
		return this.find("from CoreArea where id like ?", id+"__");
	}
	
	/**
	 * 根据省来查找下面的市
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CoreArea> getCities(String id){
		return this.find("from CoreArea where id like ?", id+"__");
	}
	
	/**
	 * 查找省
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CoreArea> getProvinces(){
		return this.find("from CoreArea where length(id)=2");
	}

	/**
	 * 根据id获取名称
	 * @return
	 */
	public String getDetailById(String id){
		CoreArea area = siteCache.getCoreAreaFromCache(id);
		if(area != null){
			return area.getDetail();
		}
		return "";
	}

	/**
	 * 根据id获取名称
	 * @return
	 */
	public String getDetailById(String provinceId,String cityId){
		CoreArea area = null;
		if(StringUtils.isNotBlank(cityId)){
			area = siteCache.getCoreAreaFromCache(cityId);
		} else {
			area = siteCache.getCoreAreaFromCache(provinceId);
		}
		if(area != null){
			return area.getDetail();
		}
		return "";
	}
	
	public SiteCache getSiteCache() {
		return siteCache;
	}

	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}
	
}

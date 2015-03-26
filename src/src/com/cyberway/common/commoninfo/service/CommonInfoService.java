package com.cyberway.common.commoninfo.service;

import java.util.List;

import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.common.domain.CoreCommonInfo;
import com.cyberway.common.domain.CoreCommonType;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.exception.BizException;

/**
 * @author caiw
 * 
 * 公共信息管理service
 *
 */
public class CommonInfoService extends HibernateEntityDao<CoreCommonInfo>{
	private SiteCache siteCache;
	
	public synchronized void init(){
		List<CoreCommonInfo> coreCommonInfoList=getAll();
		if(!coreCommonInfoList.isEmpty()){			
			for(CoreCommonInfo coreCommonInfo:coreCommonInfoList){
				siteCache.putCoreCommonInfoInCache(coreCommonInfo);
			}
		}
	}
	
	/**
	 * ajax调用
	 * 显示类型下拉列表
	 * @return
	 */
	public String[] getCommonInfoTypeList(Long typeid){
		String sql = "select distinct keyword from CoreCommonType where id="+typeid;
		return (String[]) this.createQuery(sql).list().toArray(new String[]{});
	}
	
	/**
	 * 根据code和所属coreCommonType的id获取对应的content值
	 * @return
	 */
	public String getCommonInfoContentByCodeAndType(Long coreCommonTypeId, String code){
		CoreCommonInfo info = siteCache.getCoreCommonInfoFromCache("typeId_"+coreCommonTypeId+"_code_"+code);
		if(info!=null){
			return info.getContent();
		}
		return "";
	}
	
	/**
	 * 根据code和所属coreCommonType的keyword获取对应的content值
	 * @return
	 */
	public String getCommonInfoContentByKeywordAndCode(String keyword, String code){
		CoreCommonInfo info = siteCache.getCoreCommonInfoFromCache("keyword_"+keyword+"_code_"+code);
		if(info!=null){
			return info.getContent();
		}
		return "";
	}
	
	/**
	 * 获得所有类别
	 * @return
	 */
	public List<CoreCommonType> getCommonTypes(){
		return this.find(" from CoreCommonType ", new Object[]{});
	}
 
	public CoreCommonInfo saveOrUpdate(CoreCommonInfo obj){
		Boolean unique=this.isNotUnique(obj, "type,code");
		if(unique)			
				throw new BizException("同一类别下信息代码不能重复！");
		CoreCommonInfo commonInfo = super.saveOrUpdate(obj);
		siteCache.putCoreCommonInfoInCache(commonInfo);
		return commonInfo;
	}
	/**
	 * 根据类型，获得常用信息列表
	 * @param type
	 * @return
	 */
	public List<CoreCommonInfo> getCoreCommonInfos(String type){
		List<CoreCommonInfo> cci=this.find(" from CoreCommonInfo where coreCommonType.keyword=? order by sortOrder asc", new Object[]{type});
		return cci;
	}
	
	/**
	 * 根据类型(coreCommonTypeId)，获得常用信息列表
	 * @remark add by liaozhiyong 2012-03-13
	 * @param coreCommonTypeId
	 * @return List<CoreCommonInfo>
	 */
	public List<CoreCommonInfo> getCoreCommonInfosByCommonTypeId(Long coreCommonTypeId){
		List<CoreCommonInfo> cci=this.find(" from CoreCommonInfo where coreCommonType.id=?  order by sortOrder asc", new Object[]{coreCommonTypeId});
		return cci;
	}
	
	/**
	 * dwr 验证 : 
	 * 删除类型(CoreCommonType)时,判断有没有并联 CoreCommonInfo 
	 * 根据类型(coreCommonTypeId)，获得常用信息列表
	 * @remark add by liaozhiyong 2012-03-13
	 * @param coreCommonTypeId
	 * @return String
	 */
	public String dwrCoreCommonInfosByCommonTypeId(String coreCommonTypeIds){
		StringBuilder strCoreCommonInfo = new StringBuilder();
		if (coreCommonTypeIds != null && coreCommonTypeIds.length() > 0) {
			String [] tempArray = coreCommonTypeIds.split(",");
			String tempEmpty = "  ";
			for (String string : tempArray) {
				Long coreCommonTypeId = Long.valueOf(string);
				List<CoreCommonInfo> tempList = this.getCoreCommonInfosByCommonTypeId(coreCommonTypeId);
				if (tempList != null && tempList.size() > 0) {
					for (CoreCommonInfo coreCommonInfo : tempList) {
						strCoreCommonInfo.append(tempEmpty + coreCommonInfo.getCode()).append(",").append("\n");
					} 
				}
			}
			if(strCoreCommonInfo.length()>1){
				strCoreCommonInfo.insert(0, "选择删除的类型有关联信息:\n").append("\n");
			}
		}else{
			strCoreCommonInfo.append("");
		}
		return strCoreCommonInfo.toString();
	}
	public SiteCache getSiteCache() {
		return siteCache;
	}
	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}
}

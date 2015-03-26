package com.cyberway.common.resource.service;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cyberway.common.domain.CorePermission;
import com.cyberway.common.domain.CorePermissionPK;
import com.cyberway.common.domain.CoreResource;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.permission.service.AuthorityManager;
import com.cyberway.common.service.CommonCache;
import com.cyberway.core.dao.HibernateEntityDao;

public class ResourceManagerService extends HibernateEntityDao<CoreResource>  {
	//private static GeneralCacheAdministrator resCache = new GeneralCacheAdministrator();
	private CommonCache commonCache;
	//资源编码--资源串
	public static final String RESOURCE_TYPE_URL = "URL";

	public static final String RESOURCE_TYPE_FUNCTION = "FUNCTION";

	public static final String RESOURCE_TYPE_COMPONENT = "COMPONENT";
	/**
	 * 对权限缓存进行统一管理
	 */
	private AuthorityManager authorityManager;
	
	/**
	 * @param authorityManager the authorityManager to set
	 */
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}
	/**
	 * 设置到缓冲区中
	 * @param resCode
	 * @param resString
	 */
	public void putResourceInCache(CoreResource resString) {		
		commonCache.putResourceInCache(resString);
	}
	
	
	/**
	 * 从缓冲区内取出
	 * @param resCode
	 * @return
	 */
	public CoreResource getResourceCache(String resCode) {		
			return commonCache.getResourceFromCache(resCode);
		
	}
	/**
	 * 从缓冲区内获得资源串
	 * @param resCode
	 * @return
	 */
	public String getResourceStringFromCache(String resCode){
		return commonCache.getResourceStringFromCache(resCode);
	}
	/**
	 * 初始化资源
	 */
	public synchronized void init(){
		List<CoreResource> ress=getAll();
		if(!ress.isEmpty()){			
			for(CoreResource res:ress){
				putResourceInCache(res);
			}
		}
	}	
	/* (non-Javadoc)
	 * @see com.cyberway.core.dao.HibernateEntityDao#saveOrUpdate(java.lang.Object)
	 */
	public CoreResource saveOrUpdate(CoreResource res){
		boolean isAdd=res.getResourceid()==null?true:false;
		save(res);
		//同步更新资源
		authorityManager.renovateResource(res.getResourcestring(), res);
		putResourceInCache(res);
		if(isAdd){
		//新增资源自动会符给系统管理员组中
		CoreRole role=(CoreRole)get(CoreRole.class,new Long(com.cyberway.core.Constants.DEFAULT_ADMINISTRATORS_ID));
		//处理权限缓存
		String resString = res.getResourcestring();
		String resType = res.getResourcetype();
		if(role!=null){
		String roleCode = role.getOid().toString();
		
		//持久保存权限
		CorePermission cp = new CorePermission();
		CorePermissionPK comp_id = new CorePermissionPK();
		comp_id.setObjectid(role.getOid());
		comp_id.setTargettype(com.cyberway.common.permission.service.PermissionService.types[1]);
		comp_id.setResourceid(res.getResourceid());
		cp.setComp_id(comp_id);
		save(cp);
		authorityManager.renovateResource(resString, resType, roleCode, true);	
		}	
		}
		return res;
	}
	/**
	 * 根据ID移除对象.
	 */
	public void removeById(Serializable id) {
		CoreResource cr=this.get(id);
		if(cr.getCorePermissions()!=null && cr.getCorePermissions().size()>0){
		   Iterator it=cr.getCorePermissions().iterator();
		   while(it.hasNext())
		    remove(it.next());
		}
		removeById(CoreResource.class, id);
	}
	/**
	 * 更新资源缓存
	 * @param orgResourceId
	 * @param curResourceId
	 * @return
	 */
	public boolean modifyAuthorization(String orgResString, Serializable curResourceId){
		
		CoreResource resource = this.get(curResourceId);
		
		this.authorityManager.renovateResource(orgResString, resource);
		
		return true;
	}
	public void setCommonCache(CommonCache commonCache) {
		this.commonCache = commonCache;
	}
	
	/**
	 * 清除所有公共资源缓存
	 */
	public void removeAllCache(){
		commonCache.removeAllResources();
	}
	
	/**
	 * 获取所有公共资源缓存中的key
	 * @return
	 */
	public List<String> getAllCacheKeys(){
		return commonCache.getAllResources();
	}
	
	/**
	 * 获得指定的公共资源缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)){
			net.sf.ehcache.Element element = null;
			element = commonCache.getElementFromCoreResource(key);
			return element;
		}
		else
			return null;
	}
}

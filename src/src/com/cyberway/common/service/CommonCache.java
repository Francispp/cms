package com.cyberway.common.service;

import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;

import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.domain.CoreResource;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.domain.CoreUser;

/**
 * @author caiw 公用缓存接口
 */
public interface CommonCache {

	/**
	 * 从缓存中获得资源
	 * 
	 * @param resString
	 * @return
	 */
	public CoreResource getResourceFromCache(String resString);

	public String getResourceStringFromCache(String resCode);

	public void putResourceInCache(CoreResource resourceDetails);

	public void putSiteDistributionInCache(Long siteId, String resourceType, CoreSiteDistribution resourceDetails);

	/**
	 * 获得CoreSiteDistribution在缓冲中的key,格式是：S + siteId + "_" + R + resourceType +
	 * "_" + ftpId
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型
	 * @param ftpId
	 *            ftp服务器id
	 * @return
	 */
	public String getSiteDistributionCacheKey(Long siteId, String resourceType, Long ftpId);

	public String getSiteDistributionCacheKey(Long siteId, String resourceType);

	/**
	 * 根据站点id,站点资源类型,获取站点资源类型跟ftp服务器的关系集合
	 * 
	 * @param siteId
	 * @param resourceType
	 * @return
	 */
	public List<CoreSiteDistribution> getDistributionBySiteAndResource(Long siteId, String resourceType);

	public void removeResourceFromCache(String resString);

	public List getAllResources();

	public void removeAllResources();

	/**
	 * put指定用户的信息到缓存中
	 * 
	 * @param user
	 */
	public void putUserInCache(CoreUser user);

	/**
	 * 从缓存中获得指定用户信息
	 * 
	 * @param userid
	 * @return
	 */
	public CoreUser getUserFromCache(String userid);

	/**
	 * 从缓冲中删除指定用户信息
	 * 
	 * @param userid
	 */
	public void removeUserFromCache(String userid);

	/**
	 * 从缓冲中获得所有用户信息
	 * 
	 * @return
	 */
	public List<CoreUser> getAllUsers();

	/**
	 * put指定组织机构的信息到缓存中
	 * 
	 * @param org
	 */
	public void putOrgInCache(CoreOrg org);

	/**
	 * 从缓存中获得指定组织机构信息
	 * 
	 * @param orgid
	 * @return
	 */
	public CoreOrg getOrgFromCache(String orgid);

	/**
	 * 从缓冲中删除指定组织机构信息
	 * 
	 * @param orgid
	 */
	public void removeOrgFromCache(String orgid);

	/**
	 * 从缓冲中获得所有组织机构
	 * 
	 * @return
	 */
	public List<CoreOrg> getAllOrgs();

	/**
	 * 删除指定流程key数据
	 * 
	 * @param key
	 */
	public void removeFlowDataFromCache(String key);

	/**
	 * 从缓冲中获得流程key对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getFlowDataFromCache(String key);

	/**
	 * put指定流程的信息到缓存中
	 * 
	 * @param key
	 * @param value
	 */
	public void putFlowDataInCache(String key, Object value);

	/**
	 * 从缓存中获得指定站点分发配置信息
	 * 
	 * @param userid
	 * @return
	 */
	public CoreSiteDistribution getSiteDistributionFromCache(String id);

	/**
	 * 从缓存中获得所有站点分发配置信息
	 * 
	 * @return
	 */
	public List<CoreSiteDistribution> getAllSiteDistributions();

	public void removeSiteDistributionFromCache(String key);

	public void removeSiteDistributionFromCache(Long siteId, String resourceType, Long ftpId);

	public void removeAllSiteDistributions();

	public CoreSiteDistribution getSiteDistributionByKey(Long siteId, String resourceType, Long ftpId);

	/**
	 * 根据key从公共资源缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromCoreResource(String key);

	/**
	 * 清除所有用户缓存
	 */
	public void removeAllCoreUser();

	/**
	 * 从用户缓存中获得所有的key
	 * 
	 * @return
	 */
	public List<String> getCoreUserCacheKeys();

	/**
	 * 根据key从用户缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromCoreUser(String key);

	/**
	 * 清除所有组织机构缓存
	 */
	public void removeAllCoreOrg();

	/**
	 * 从组织机构缓存中获得所有的key
	 * 
	 * @return
	 */
	public List<String> getCoreOrgCacheKeys();

	/**
	 * 根据key从组织机构缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromCoreOrg(String key);
	
	/**
	 * 清除所有ftp服务器缓存
	 */
	public void removeAllFtpServer();

	/**
	 * 从ftp服务器缓存中获得所有的key
	 * 
	 * @return
	 */
	public List<String> getFtpServerCacheKeys();

	/**
	 * 根据key从ftp服务器缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromFtpServer(String key);
	
	/**
	 * 将角色对象放到缓存中
	 * @param role
	 */
	public void putRoleCacheInCache(CoreRole role);
	
	/**
	 * 清除所有角色缓存
	 */
	public void removeAllRole();

	/**
	 * 从角色缓存中获得所有的key
	 * 
	 * @return
	 */
	public List<String> getRoleCacheKeys();

	/**
	 * 根据key从角色缓存中获得对应的缓存
	 * 
	 * @param key
	 * @return
	 */
	public Element getElementFromRole(String key);
	
	/**
	 * 根据CoreRole对象的id,从缓存中获取对象
	 * @param id
	 * @return
	 */
	public CoreRole getRoleFromCache(String id);
	
	/**
	 * 根据身份和角色的组合从缓存中获取CoreRole对象
	 * @param searchKey 身份和角色的组合
	 * @return 角色列表
	 */
	public List<CoreRole> getRoleByGrade(String searchKey);
	
	/**
	 * 根据角色id,从缓存中移除掉对应的角色缓存
	 * 
	 * @param id
	 *            角色对象id
	 * @return true为删除成功,false为删除失败
	 */
	public boolean removeRoleById(String id);
}
package com.cyberway.common.org.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.permission.service.PermissionService;
import com.cyberway.common.resource.service.ResourceManagerService;
import com.cyberway.common.service.CommonCache;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;

/**
 * 
 * @author caiw
 * 
 */
public class OrgManagerService extends HibernateEntityDao<CoreOrg> {

	private CommonCache	      commonCache;	       // 公共缓存管理器
	private PermissionService	permissionService; // 权限管理器

	/**
	 * 初始化用户缓存
	 */
	public synchronized void init() {
		List<CoreOrg> orgs = getAll();
		if (!orgs.isEmpty()) {
			for (CoreOrg org : orgs) {
				commonCache.putOrgInCache(org);
			}
		}
	}

	/**
	 * 从缓存中取组织机构信息
	 * 
	 * @param userid
	 * @return
	 */
	public CoreOrg getCoreOrgFromCache(String orgid) {
		return commonCache.getOrgFromCache(orgid);
	}

	/**
	 * 从缓存中获得所有用户信息
	 * 
	 * @return
	 */
	public List<CoreOrg> getAllOrgsFromCache() {
		return commonCache.getAllOrgs();
	}

	/**
	 * 根据当前用户获得机构
	 * 
	 * @param loginer
	 * @return
	 */
	public List<CoreOrg> getOrgs(Loginer loginer) {
		List<CoreOrg> cos = null;
		if (loginer.checkIsAdministratorUser())
			cos = getAllOrgsFromCache();
		else {
			cos = new ArrayList();
			ResourceManagerService resourceManagerService = (ResourceManagerService) ServiceLocator.getBean("resourceManagerService");

			cos.add(getCoreOrgFromCache(loginer.getOrgid().toString()));
			// 是否拥有子机构的管理权限
			if (permissionService.haveThePermission(loginer, resourceManagerService.getResourceStringFromCache("SYS_ORG_SUBORG_MANAGER"))) {
				List<CoreOrg> allcos = getAllOrgsFromCache();
				for (CoreOrg org : allcos) {
					if (org.getPorgid() != null && org.getPorgid().intValue() == org.getOid().intValue())
						cos.add(org);
				}
			}
		}
		return cos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.core.dao.HibernateEntityDao#saveOrUpdate(java.lang.Object)
	 */
	public CoreOrg saveOrUpdate(CoreOrg obj) {
		Boolean unique = this.isNotUnique(obj, "orgCode");
		// 如果已经存在相同的portalCode
		if (unique) {
			throw new BizException("orgCode已经存在！");
		}
		if (obj.getOid() != null && obj.getPorgid() != null && obj.getOid().intValue() == obj.getPorgid().intValue())
			throw new BizException("父机构不能指向自己！");
		obj = super.saveOrUpdate(obj);
		// 更新组织机构缓存
		commonCache.putOrgInCache(obj);
		return super.saveOrUpdate(obj);
	}

	/**
	 * 按站点编码获得站点对象
	 * 
	 * @param portalcode
	 * @return
	 */
	public CoreOrg getCoreOrgByOrgCode(String orgCode) {

		// Portal portal=new Portal();
		// List<CorePortal> list=this.findUniqueBy("portalCode", portalcode);
		// if(list!=null && list.size()>0){
		CoreOrg cp = this.findUniqueBy("orgCode", orgCode);
		// Portal portal = getPortalByCorePortal(cp);
		/*
		 * portal.setPortalid(cp.getOid().toString());
		 * portal.setPortalcode(cp.getPortalCode());
		 * portal.setCname(cp.getPortalName());
		 */

		// }
		return cp;
	}

	public CommonCache getCommonCache() {
		return commonCache;
	}

	public void setCommonCache(CommonCache commonCache) {
		this.commonCache = commonCache;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	/**
	 * 从CorePortal转换成Portal对象
	 * 
	 * @param cp
	 * @return
	 */
	/*
	 * public static Portal getPortalByCorePortal(CoreOrg cp) { Portal portal =
	 * new Portal(); portal.setPortalid(cp.getOid().toString());
	 * portal.setPortalcode(cp.getOrgCode()); portal.setCname(cp.getOrgName());
	 * return portal; }
	 */

	/**
	 * 清除所有组织机构缓存
	 */
	public void removeAllCache() {
		commonCache.removeAllCoreOrg();
	}

	/**
	 * 获取所有组织机构缓存中的key
	 * 
	 * @return
	 */
	public List<String> getAllCacheKeys() {
		return commonCache.getCoreOrgCacheKeys();
	}

	/**
	 * 获得指定的组织机构缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)) {
			net.sf.ehcache.Element element = null;
			element = commonCache.getElementFromCoreOrg(key);
			return element;
		} else
			return null;
	}

	/**
	 * 根据上级组织id获取下级组织的集合
	 * 
	 * @param id
	 *            上级组织id
	 * @return
	 */
	public List<CoreOrg> getSubOrg(Long id) {
		Criteria c = getEntityCriteria();
		c.add(Restrictions.eq("porgid", id));
		return c.list();
	}

	/**
	 * 根据id从缓存中清除掉组织
	 * 
	 * @param id
	 */
	public void removeOrgFromCache(Long id) {
		commonCache.removeOrgFromCache(id.toString());
	}

	/**
	 * 判断组织代码是否唯一
	 * 
	 * @param id
	 *            组织id
	 * @param menuCode
	 *            组织代码
	 * @return "0"表示不唯一,"1"表示唯一
	 */
	public int orgCodeIsUnique(Long id, String orgCode) {
		List<CoreOrg> orgList;
		if (!id.equals(0L)) {
			CoreOrg org = get(id);
			if (org.getOrgCode().equals(orgCode)) {
				return 1;
			} else {
				orgList = findBy("orgCode", orgCode);
				if (orgList.size() > 0) {
					return 0;
				} else {
					return 1;
				}
			}
		} else {
			orgList = findBy("orgCode", orgCode);
			if (orgList.size() > 0) {
				return 0;
			} else {
				return 1;
			}
		}
	}
}

package com.cyberway.common.permission.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.acegisecurity.userdetails.UserDetails;
import org.ecside.table.limit.Limit;

import com.cyberway.common.dept.service.DeptManagerService;
import com.cyberway.common.domain.CorePermission;
import com.cyberway.common.domain.CorePermissionPK;
import com.cyberway.common.domain.CoreResource;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.domain.VcorePermission;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.acegi.cache.AcegiCacheManager;
import com.cyberway.core.acegi.resource.ResourceDetails;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class PermissionService extends HibernateEntityDao<VcorePermission> {
	public static final Long[] types = { new Long(0), new Long(1), new Long(2),
			new Long(3) };

	public static final String[] typeNames = { "部门", "角色", "用户", "其他" };

	private Class clazz = VcorePermission.class;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.dao.HibernateEntityDao#getEntityClass()
	 */
	protected Class getEntityClass() {
		return clazz;
	}
	/**
	 * 对缓存进行统一管理
	 */
	private AcegiCacheManager acegiCacheManager;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.dao.HibernateEntityDao#findECPage(org.ecside.table.limit.Limit,
	 *      com.cyberway.core.dao.support.CriteriaSetup)
	 */
	public Page findECPage(Limit limit, CriteriaSetup criteriaSetup)
			throws Exception {
		// clazz=VcorePermission.class;
		Page page = super.findECPage(limit, criteriaSetup);
		// clazz=CorePermission.class;
		return page;
	}

	/**
	 * 取得权限类型名称
	 * 
	 * @param type
	 * @return
	 */
	public static String getTypeName(Long type) {
		if (type == null)
			return "";
		int t = type.intValue();
		if (t < types.length)
			return typeNames[t];
		else
			return "";
	}

	public List getAllResourcesAccreditedByUser(Long userid) {
		List resources = new ArrayList();

		return resources;
	}

	/**
	 * 取得已授的权限对象集
	 * 
	 * @param objectid
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List getAllResourcesAccredited(Long objectid, Long type)
			throws Exception {
		List resources = null;
		if (type.intValue() == types[0].intValue()) {// 对部门授权
			resources = getResourcesByDept(objectid);
		}
		if (type.intValue() == types[1].intValue()) {// 对角色授权
			resources = getResourcesByRole(objectid);
		}
		if (type.intValue() == types[2].intValue()) {// 对用户授权
			UserManagerService userService = (UserManagerService) ServiceLocator
					.getBean("userManagerService");
			CoreUser user = userService.get(objectid);
			logger.info("user.getDeptcode:" + user.getCoreDept().getDeptid());
			resources = getResourcesByDept(user.getCoreDept().getDeptid());
			// 获得角色权限
			resources.addAll(this.getResourceRolesByUser(objectid));

			resources.addAll(getResourcesByUser(objectid));
		}
		return resources;
	}

	/**
	 * 保存权限
	 * 
	 * @param objectid
	 * @param type
	 * @param resourceids
	 * @return
	 * @throws Exception
	 */
	public boolean savePermissions(Long objectid, Long type,
			List<Long> resourceids) throws Exception {
		if (objectid == null || type == null || resourceids == null)
			return false;
		List<Long> trids = resourceids;
		if (type.intValue() == types[0].intValue()) {// 对部门授权
			deletePermission(objectid, type, trids);
			savePermission(objectid, type, trids);
		} else if (type.intValue() == types[1].intValue()) {// 对用户组授权
			deletePermission(objectid, type, trids);
			savePermission(objectid, type, trids);
		} else if (type.intValue() == types[2].intValue()) {// 对用户授权
			deletePermission(objectid, type, trids);
			savePermission(objectid, type, trids);
		} else {
			logger.error("当前操作类型不存在！……" + type);
		}
		return true;
	}

	/**
	 * 删除指定类型的已赋权限
	 * 
	 * @param objectid
	 * @param type
	 * @return
	 */
	private boolean deletePermission(Long objectid, Long type,
			List<Long> resourceids) {
		List<CorePermission> perms = this
				.find(
						"from CorePermission where comp_id.targettype=? and comp_id.objectid=?",
						new Object[] { type, objectid });
		logger.info("delete size:" + perms.size());
		for (CorePermission cp : perms) {
			// this.remove(cp);
			Long rsid = cp.getComp_id().getResourceid();
			if (resourceids.contains(rsid)) {
				resourceids.remove(rsid);
				//logger.info("not delete resoucreid:" + rsid);
			} else {
				//logger.info("delete resoucreid:" + rsid);
				this.remove(cp);
				// this.removeById(cp.getComp_id());
				// 更新权限缓存
				CoreResource res = (CoreResource) this.get(CoreResource.class,
						rsid);
				if(res != null)
				this.authorityManager.renovateResource(res.getResourcestring(),
						res.getResourcetype(), objectid.toString(),
						false);
			}
		}
		logger.info("delete success!");
		return true;
	}

	/**
	 * 保存权限的对象
	 * 
	 * @param objectid
	 * @param type
	 * @param resourceids
	 * @return
	 */
	private boolean savePermission(Long objectid, Long type,
			List<Long> resourceids) {
		List list = new ArrayList();
		for (Long rid : resourceids) {
			CorePermission cp = new CorePermission();
			CorePermissionPK comp_id = new CorePermissionPK();
			comp_id.setObjectid(objectid);
			comp_id.setTargettype(type);
			comp_id.setResourceid(rid);
			cp.setComp_id(comp_id);
			// list.add(cp);
			this.save(cp);
			// 更新权限缓存
			CoreResource res = (CoreResource) this.get(CoreResource.class, rid);
			this.authorityManager.renovateResource(res.getResourcestring(), res
					.getResourcetype(), objectid.toString(), true);
		}
		// saveOrUpdateAll(list);
		return true;
	}

	/**
	 * 获得指定部门的所有权限(包括上级部门的)
	 * 
	 * @param objectid
	 * @return
	 * @throws Exception
	 */
	private List getResourcesByDept(Long objectid) throws Exception {
		List resources = null;
		DeptManagerService deptService = (DeptManagerService) ServiceLocator
				.getBean("deptManagerService");
		Map superDept = deptService.getSuperDepts(objectid);
		Iterator it = superDept.keySet().iterator();
		List<Long> deptids = new ArrayList();
		String ids = "";
		while (it.hasNext()) {
			Long id = (Long) it.next();
			deptids.add(id);
			ids += id + ",";
		}
		if (ids.length() > 0)
			ids = ids.substring(0, ids.length() - 1);
		if (StringUtil.isEmpty(ids))// 若部门为空时,没设置为-1,条件不成立
			ids = "-1";
		logger.info("superDept size:" + superDept.size());
		String sql = "from VcorePermission this where this.targettype=? and this.objectid in ("
				+ ids + ") order by this.resourceid";
		/*
		 * resources=this.find("from VcorePermission this where
		 * this.targettype=? and this.objectid in (?) order by this.resourceid",
		 * new Object[]{types[0],deptids.toArray()});
		 */
		resources = find(sql, new Object[] { types[0] });

		return resources;
	}

	/**
	 * 获得当前对象的权限（不包括上级，仅仅当前对象与权限建立的关系）
	 * 
	 * @param objectid
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private List getResourcesByCurrentObject(Long objectid, Long type)
			throws Exception {
		List resources = null;
		resources = this
				.find(
						"from VcorePermission this where this.targettype=? and objectid=? order by this.resourceid",
						new Object[] { type, objectid });
		return resources;
	}

	/**
	 * 获得指定角色的权限
	 * 
	 * @param objectid
	 * @return
	 */
	private List getResourcesByRole(Long objectid) {
		List resources = null;
		resources = this
				.find(
						"from VcorePermission this where this.targettype=? and objectid=? order by this.resourceid",
						new Object[] { types[1], objectid });
		return resources;
	}

	/**
	 * 根据用户ＩＤ，获得所获角色的所有权限
	 * 
	 * @param objectid
	 * @return
	 */
	private List getResourceRolesByUser(Long objectid) {
		List resources = null;
		RoleManagerService roleService = (RoleManagerService) ServiceLocator
				.getBean("roleManagerService");
		List<CoreRole> roles = roleService.getRolesByUserId(objectid);
		// List<Long> roleids=new ArrayList();
		String ids = "";
		for (CoreRole cr : roles)
			// roleids.add(cr.getOid());
			ids += cr.getOid() + ",";
		if (ids.length() > 0)
			ids = ids.substring(0, ids.length() - 1);
		if (StringUtil.isEmpty(ids))// 若角色为空时,没设置为-1,条件不成立
			ids = "-1";
		resources = this.find(
				"from VcorePermission this where this.targettype=? and objectid in ("
						+ ids + ") order by this.resourceid",
				new Object[] { types[2] });
		return resources;
	}

	private List getResourcesByUser(Long objectid) {
		List resources = null;
		resources = this
				.find(
						"from VcorePermission this where this.targettype=? and objectid=? order by this.resourceid",
						new Object[] { types[2], objectid });
		return resources;
	}

	/**
	 * 对权限缓存进行统一管理
	 */
	private AuthorityManager authorityManager;

	/**
	 * @param authorityManager
	 *            the authorityManager to set
	 */
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

	/**
	 * 修改权限操作
	 * 
	 * @param resId
	 * @param roleId
	 * @param isAuth
	 *            为1表示授权 否则撤消授权
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean modifyAuthorization(String roleId, String resId,
			boolean isAuth) {

		// 处理角色与资源的关系
		CoreRole role = (CoreRole) this.get(CoreRole.class, roleId);
		CoreResource res = (CoreResource) this.get(CoreResource.class, resId);

		// Set resources = role.getResources();
		// 是授权还是撤销授权

		super.save(role);

		// 处理权限缓存
		String resString = res.getResourcestring();
		String resType = res.getResourcetype();
		String roleCode = role.getRolecode();
		this.authorityManager.renovateResource(resString, resType, roleCode,
				isAuth);

		return true;
	}

	/**
	 * 获得用户当前的权限
	 * 
	 * @param userid
	 * @return
	 */
	public List getPermissionsStringByUserid(Long userid) {
		// PermissionService
		// permService=(PermissionService)ServiceLocator.getBean("permissionService");
		List permkeys = null;
		try {
			List<VcorePermission> ps = getAllResourcesAccredited(userid,
					types[2]);
			permkeys = new ArrayList();
			for (VcorePermission vp : ps) {
				permkeys.add(vp.getResourcekey());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permkeys;
	}
	/**
	 * 检测是否某用户是否拥有某权限
	 * @param loginer
	 * @param tag
	 * @return
	 */
	public boolean haveThePermission(com.cyberway.core.objects.Loginer loginer,String tag){
		boolean isperm=false;
		if ((null != tag) && !"".equals(tag) && loginer!=null) {
		//找出相应的资源
		ResourceDetails rd = acegiCacheManager.getResourceFromCache(tag);
		UserDetails ud=acegiCacheManager.getUser(loginer.getLoginid());
		if(rd!=null && ud!=null){
			Collection required=Arrays.asList(rd.getAuthorities());
			Collection usauths=Arrays.asList(ud.getAuthorities());
			Collection grantedCopy = copy(usauths);
			//判断权限

				grantedCopy.retainAll(required);
				if (!grantedCopy.isEmpty()) 
				{
					isperm=true;
				}
			}
		}
		return isperm;
		
	}
	
	private Set copy(Collection c) {
		Set target = new HashSet();
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			target.add(iterator.next());
		}
		return target;
	}
	public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
		this.acegiCacheManager = acegiCacheManager;
	}
}

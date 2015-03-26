package com.cyberway.common.role.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import com.cyberway.cms.Constants;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.webservice.service.CacheSynchroismService;
import com.cyberway.common.domain.CoreGrade;

import com.cyberway.common.domain.CoreIdentity;
import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.grade.service.GradeManagerService;
import com.cyberway.common.identity.service.IdentityManagerService;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class RoleController extends BaseBizController<CoreRole> {

	RoleManagerService	   service	          = (RoleManagerService) this.getServiceById("roleManagerService");
	GradeManagerService	   gservice	          = (GradeManagerService) this.getServiceById("gradeManagerService");
	IdentityManagerService	iservice	      = (IdentityManagerService) this.getServiceById("identityManagerService");
	CmsPermissionService	permissionService	= (CmsPermissionService) this.getServiceById("cmsPermissionService");

	private String	       roleCode;
	private String	       objectId;	                                                                                // 对象id
	private Long	       userId;

	private String	       _treeXml;

	private String	       xml;	                                                                                    // 选择角色返回的xml
	private String	       isOne;	                                                                                    // 是否单选
	private boolean	       isLocalUser;

	private List	       coreRoles;	                                                                                // 存放角色
	                                                                                                                    // 集合

	public List getCoreRoles() {
		return coreRoles;
	}

	public void setCoreRoles(List coreRoles) {
		this.coreRoles = coreRoles;
	}

	// WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued"
	// list="#{true:'是',false:'否'}" />
	// 改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Long, String>	trueOfFalseMap1	= null;
	private Map<Long, String>	trueOfFalseMap2	= null;

	private String	          identityGradeMap	= "";

	private String	          rolestatus;	            // 是站点管理中的角色还是系统管理管理中的角色

	public boolean getIsLocalUser() {
		return Constants.LOGON_USER.equals(Loginer.USER_LOCAL);
	}

	public Boolean getIsSearchUsers() {
		return Constants.IS_SEARCH_USERS;
	}

	public String getIsOne() {
		return isOne;
	}

	public void setIsOne(String isOne) {
		this.isOne = isOne;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}

	/*
	 * 列表
	 * 
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	@Override
	public String execute() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		if (!StringUtil.isEmpty(objectId)) {
			return extlist();
		}

		// criteria.addFilter("siteId", null);
		List<Criterion> criterias = new ArrayList<Criterion>();
		criterias.add(Restrictions.sqlRestriction("siteId is null or siteId =0"));
		criteria.setAddCriterions(criterias);

		if (!getLoginer().checkIsAdministratorUser()) {
			criteria.addFilter("coreOrg.oid", new Long(getLoginer().getOrgid()));
		}

		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		// getHttpServletRequest().setAttribute("portals", getPortals());

		return LIST_RESULT;
	}

	@Override
	public String delete() throws Exception {
		// 1.删除权限关联 2.删除角色
		// @remark add by liaozhiyong 2012-03-22
		if (!StringUtil.isEmpty(keys)) {
			List<Long> list = StringUtil.splitToList(keys, ",");
			for (Long id : list) {
				permissionService.deletePermissionByRoleId(id.toString());
				service.removeRoleFormCache(id);
				
				//删除运行平台的角色缓存
				CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this.getServiceById("cacheSynchroismService");
				try {
					cacheSynchroismService.deleteRole(id);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				service.removeById(id);
			}
			addActionMessage(this.getText("RESOURCE.HINTINFO.DELETESUCCESS"));
		} else {
			addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		}

		if (rolestatus.equalsIgnoreCase("site")) {
			return extlist();
		} else {
			return execute();
		}
	}

	// 根据对象（站点）管理的对象，获得编号
	private String getObjectCode(String code) {

		return "ROLE_CODE_" + code;
	}

	/**
	 * 扩展显示角色列表(站点管理中的角色管理列表)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String extlist() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		/*
		 * if(!getLoginer().checkIsAdministratorUser())//若不为超级用户,则只列出当前登录门户的资源
		 * criteria.addFilter("corePortal.oid", new
		 * Long(getLoginer().getPortal().getPortalid()));
		 */
		if (getLoginer() != null) {
			criteria.addFilter("siteId", getLoginer().getSiteId());
		}
		if (!StringUtil.isEmpty(objectId)) {
			criteria.addFilter("rolecode", getObjectCode(objectId));
		} else {
			criteria.addFilter("rolecode", "");
		}
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);

		return "extlist";
	}

	// 显示列表
	@Override
	public String list() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		if (userId != null) {
			// RoleManagerService roleService =
			// (RoleManagerService)getServiceById("roleManagerService");
			List<CoreRole> roles = service.getRolesByUserIdAndSiteId(userId);
			List ids = new ArrayList();
			for (CoreRole cu : roles) {
				ids.add(cu.getOid());
			}
			if (!ids.isEmpty()) {
				criteria.setInCriterion(Restrictions.in("oid", ids));
				// criteria.addFilter("userid", ids);
			} else {
				criteria.setInCriterion(Restrictions.isNull("oid"));
			}
		}
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}

	/**
	 * 站点级别角色添加
	 * @author Dicky
	 * @time 2012-9-17 10:25:28
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	@Override
	public String add() throws Exception {
		String rt = super.add();
		Long siteId = getLoginer().getSiteId();
		domain.setCoreOrg(new CoreOrg(new Long(getLoginer().getOrgid())));
		domain.setSiteId(siteId);
		if (!StringUtil.isEmpty(objectId)) {
			domain.setRolecode(getObjectCode(objectId));
		} else {
			domain.setRolecode(getDefaultRoleCode());
		}
		List<CoreGrade> coreGradeList = gservice.findBy("siteId", siteId);
		List<CoreIdentity> coreIdentityList = iservice.findBy("siteId", siteId);
		getHttpServletRequest().setAttribute("coreGradeList", coreGradeList);
		getHttpServletRequest().setAttribute("coreIdentityList", coreIdentityList);
		getHttpServletRequest().setAttribute("rolestatus", rolestatus);
		return rt;
	}

	/**
	 * 编辑role方法
	 * 
	 * @return
	 * @throws Exception
	 */
	Long[]	num;

	@Override
	public String edit() throws Exception {

		if (id != null) {
			get();
			List<CoreGrade> coreGradeList = gservice.getAll();
			List<CoreIdentity> coreIdentityList = iservice.getAll();
			getHttpServletRequest().setAttribute("coreGradeList", coreGradeList);
			getHttpServletRequest().setAttribute("coreIdentityList", coreIdentityList);

			if (domain.getRoleType().equals(2L)) {
				if (StringUtils.isNotBlank(domain.getIdentityGradeString()) && StringUtils.isNotBlank(domain.getIdentityGradeText())) {
					String[] identityGradeStringList = domain.getIdentityGradeString().split("&");
					String[] identityGradeTextList = domain.getIdentityGradeText().split("&");
					for (int i = 0; i < identityGradeStringList.length; i++) {
						identityGradeMap += identityGradeStringList[i] + "&" + identityGradeTextList[i] + "&";
					}
					identityGradeMap = identityGradeMap.substring(0, identityGradeMap.length() - 1);
				}
			}
		} else {
			domain = getDomainClass().newInstance();
		}
		getHttpServletRequest().setAttribute("rolestatus", rolestatus);
		return EDIT_RESULT;
	}

	/**
	 * 通用保存方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public String saveOrUpdate() throws Exception {
		String identityGradeString = (String) getParameterValue("roleCore");
		String identityGradeText = (String) getParameterValue("roleString");

		if (StringUtils.isNotBlank(identityGradeString) && StringUtils.isNotBlank(identityGradeText)) {
			String[] identityGradeStringList = identityGradeString.split("&");
			String s = "";
			String[] identity;
			for (int i = 0; i < identityGradeStringList.length; i++) {
				identity = identityGradeStringList[i].split("_");
				CoreIdentity coreIdentity = iservice.findUniqueBy(CoreIdentity.class, "icode", identity[0]);
				if (coreIdentity != null) {
					s += coreIdentity.getIcode() + "_" + identity[1] + "&";
				}

			}
			domain.setIdentityGradeString(s.substring(0, s.length() - 1));
			domain.setIdentityGradeText(identityGradeText);
		}

		if (rolestatus.equalsIgnoreCase("site")) {
			if (getLoginerSiteId() != 0) {
				domain.setSiteId(getLoginerSiteId());
			}
		}
		domain = (CoreRole) getService().saveOrUpdate(domain);
		List<CoreGrade> coreGradeList = gservice.getAll();
		List<CoreIdentity> coreIdentityList = iservice.getAll();
		getHttpServletRequest().setAttribute("coreGradeList", coreGradeList);
		getHttpServletRequest().setAttribute("coreIdentityList", coreIdentityList);

		if (domain.getRoleType().equals(2L)) {
			if (StringUtils.isNotBlank(domain.getIdentityGradeString()) && StringUtils.isNotBlank(domain.getIdentityGradeText())) {
				String[] identityGradeStringList = domain.getIdentityGradeString().split("&");
				String[] identityGradeTextList = domain.getIdentityGradeText().split("&");
				for (int i = 0; i < identityGradeStringList.length; i++) {
					identityGradeMap += identityGradeStringList[i] + "&" + identityGradeTextList[i] + "&";
				}
				identityGradeMap = identityGradeMap.substring(0, identityGradeMap.length() - 1);
			}
		}

		// 更新发布平台缓存
		service.removeRoleFormCache(domain.getOid());
		service.putRoleInCache(domain);

		// 更新运行平台的角色缓存
		CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this.getServiceById("cacheSynchroismService");
		try {
			cacheSynchroismService.updateRole(domain.getOid());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		addActionMessage("保存成功!");
		return EDIT_RESULT;
	}

	/**
	 * 角色树有复选框 Frank
	 */
	public String roleTree() throws Exception {
		if (userId != null) {
			coreRoles = service.getRolesByUserIdAndSiteId(userId);
		}
		items = service.getSysRoles(getLoginerSiteId());
		return "view_tree";
	}

	/**
	 * 角色树 Frank
	 */
	public String roleXtree() {
		return "view_itree";
	}

	public String xml() {
		_treeXml = service.getDefaultTreeXml();
		return HTMLXTREE_RESULT;
	}

	/**
	 * 角色详细信息 Frank
	 */
	public String roleDetail() throws Exception {
		edit();

		return "detail";
	}
	
	public String getIdentityGradeTreeXml(){
		_treeXml = service.getIdentityGradeTreeXml(getLoginerSiteId(), Long.valueOf(objectId));
		return HTMLXTREE_RESULT;
	}

	/**
	 * 获得默认的角色代码
	 * 
	 * @return
	 */
	public String getDefaultRoleCode() throws Exception {
		return "ROLE_" + getLoginer().getOrgCode() + "_";
	}

	/**
	 * 选择角色 ,搜索的方式
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectRole() throws Exception {

		return "select_role";
	}

	/**
	 * 搜索角色,返回满足条件的，供选择
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchRole() throws Exception {
		xml = service.getXMLFromRoleString(keys, getLoginerSiteId());
		// xml =
		// service.getXMLFromRoleString(StringUtil.toUTF8(keys),getLoginerSiteId());
		// xml =
		// service.getXMLFromRoleString(StringUtil.toGB2312(keys),getLoginerSiteId());
		return "search_role";
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long[] getNum() {
		return num;
	}

	public void setNum(Long[] num) {
		this.num = num;
	}

	public Map<Long, String> getTrueOfFalseMap1() {
		if (trueOfFalseMap1 != null) {
			return trueOfFalseMap1;
		} else {
			trueOfFalseMap1 = new HashMap<Long, String>();
			trueOfFalseMap1.put(new Long(2), "外部角色");
			trueOfFalseMap1.put(new Long(1), "LDAP角色");
			trueOfFalseMap1.put(new Long(0), "普通角色");
			return trueOfFalseMap1;
		}
	}

	public Map<Long, String> getTrueOfFalseMap2() {
		if (trueOfFalseMap2 != null) {
			return trueOfFalseMap2;
		} else {
			trueOfFalseMap2 = new HashMap<Long, String>();
			trueOfFalseMap2.put(new Long(1), "无效");
			trueOfFalseMap2.put(new Long(0), "正常");
			return trueOfFalseMap2;
		}
	}

	public String getIdentityGradeMap() {
		return identityGradeMap;
	}

	public void setIdentityGradeMap(String identityGradeMap) {
		this.identityGradeMap = identityGradeMap;
	}

	public String getTreeXml() {
		return _treeXml;
	}

	public void setTreeXml(String treeXml) {
		_treeXml = treeXml;
	}

	public String getRolestatus() {
		return rolestatus;
	}

	public void setRolestatus(String rolestatus) {
		this.rolestatus = rolestatus;
	}
}

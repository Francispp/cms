package com.cyberway.cms.component.webuser.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.cms.component.webuser.service.WebRoleService;
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

/**
 * 系统级别角色（非站点）
 * @author Dicky
 * @time 2012-9-17 10:23:42
 * @version 1.0
 */
public class WebRoleController extends BaseBizController<CoreRole> { 
	
	
	protected String jumppath;
	private Long webUserId;
	private String objectId;
	//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Long, String> trueOfFalseMap1 = null;
	private Map<Long, String> trueOfFalseMap2 = null;
	private String identityGradeMap = "";
	private boolean isLocalUser;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6453816704492939722L;
	private WebRoleService service = (WebRoleService) this.getServiceById("WebRoleService");
	RoleManagerService roleService=(RoleManagerService)this.getServiceById("roleManagerService");
	GradeManagerService gservice=(GradeManagerService)this.getServiceById("gradeManagerService");
	IdentityManagerService iservice=(IdentityManagerService)this.getServiceById("identityManagerService");
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
	


	/*@Override
	public String saveOrUpdate() throws Exception {
		// TODO Auto-generated method stub
		return super.saveOrUpdate();
	}*/

	public String jump() throws Exception {
		return "jump";
	}


	@Override
	public String execute() throws Exception {
		return super.list();
	}
	
	/**
	 * 获得默认的角色代码
	 * @return
	 */
	public String getDefaultRoleCode()throws Exception{
		return "ROLE_"+getLoginer().getOrgCode()+"_";
	}
	/* 
	 * 增加角色方法
	 * @see com.cyberway.core.web.BaseBizController#add()
	 */
	public String add()throws Exception{
		String rt=super.add();		
		domain.setCoreOrg(new CoreOrg(new Long(getLoginer().getOrgid())));

		if(!StringUtil.isEmpty(objectId)){
			domain.setRolecode(getObjectCode(objectId));
		}else{
			domain.setRolecode(getDefaultRoleCode());
		}
		
		List<CoreGrade> coreGradeList=gservice.getAll();
		List<CoreIdentity> coreIdentityList=iservice.getAll();
		getHttpServletRequest().setAttribute("coreGradeList",coreGradeList);
		getHttpServletRequest().setAttribute("coreIdentityList",coreIdentityList);

		return rt;
	}
	
	

	//显示列表
	public String list() throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		if(webUserId!=null){
			//RoleManagerService roleService = (RoleManagerService)getServiceById("roleManagerService");
			List<CoreRole> roles=service.getRolesByUserId(webUserId);
			List ids=new ArrayList();
			for(CoreRole cu:roles)
				ids.add(cu.getOid());
			if(!ids.isEmpty()){
				criteria.setInCriterion(Restrictions.in("oid", ids));
				//criteria.addFilter("userid", ids);
			}else
				criteria.setInCriterion(Restrictions.isNull("oid"));
		}
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);		
		return "view_list";
	}
	
	
	
	
	/**
	 * 通用保存方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdate() throws Exception {
		String identityGradeString = (String) getParameterValue("roleCore");
		String identityGradeText = (String) getParameterValue("roleString");
		
		if (StringUtils.isNotBlank(identityGradeString) && StringUtils.isNotBlank(identityGradeText)) {
			String[] identityGradeStringList = identityGradeString.split("&");
			String s = "";
			String[] identity;
			for(int i = 0; i < identityGradeStringList.length; i++){
				identity = identityGradeStringList[i].split("_");
				CoreIdentity coreIdentity = iservice.get(Long.valueOf(identity[0]));
				if(coreIdentity != null){
					s += coreIdentity.getIcode() + "_" + identity[1] + "&";
				}
				
			}
			domain.setIdentityGradeString(s.substring(0, s.length() - 1));
			domain.setIdentityGradeText(identityGradeText);
		}
		domain.setSiteId(getLoginer().getSiteId());
		domain = (CoreRole) getService().saveOrUpdate(domain);
		List<CoreGrade> coreGradeList = gservice.getAll();
		List<CoreIdentity> coreIdentityList = iservice.getAll();
		getHttpServletRequest().setAttribute("coreGradeList", coreGradeList);
		getHttpServletRequest().setAttribute("coreIdentityList", coreIdentityList);

		if(domain.getRoleType().equals(2L)){
			if(StringUtils.isNotBlank(domain.getIdentityGradeString()) && StringUtils.isNotBlank(domain.getIdentityGradeText())){
				String[] identityGradeStringList = domain.getIdentityGradeString().split("&");
				String[] identityGradeTextList = domain.getIdentityGradeText().split("&");
				for(int i = 0; i < identityGradeStringList.length; i++){
					identityGradeMap += identityGradeStringList[i] + "&" + identityGradeTextList[i] + "&";	
				}
				identityGradeMap = identityGradeMap.substring(0, identityGradeMap.length() - 1);
			}
		}
		return EDIT_RESULT;
	}
	/**
	 * 扩展显示角色列表
	 * @return
	 * @throws Exception
	 */
	public String extlist()throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		/*if(!getLoginer().checkIsAdministratorUser())//若不为超级用户,则只列出当前登录门户的资源
			criteria.addFilter("corePortal.oid", new Long(getLoginer().getPortal().getPortalid()));*/
		if(getLoginer()!=null){
			criteria.addFilter("siteId", getLoginer().getSiteId());
		}
        if(!StringUtil.isEmpty(objectId)){
        	criteria.addFilter("rolecode", getObjectCode(objectId));
        }else
        	criteria.addFilter("rolecode", "");
        
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);		
		
		return "extlist";
	}

	public String getJumppath() {
		return jumppath;
	} 

	public void setJumppath(String jumppath) {
		this.jumppath = jumppath;
	}




	public Long getWebUserId() {
		return webUserId;
	}




	public void setWebUserId(Long webUserId) {
		this.webUserId = webUserId;
	}




	public String getObjectId() {
		return objectId;
	}

	public Map<Long, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Long, String>();
			trueOfFalseMap1.put(new Long(2), "外部角色");
			trueOfFalseMap1.put(new Long(1), "LDAP角色");
			trueOfFalseMap1.put(new Long(0), "普通角色");
			return trueOfFalseMap1;
		}
	}
	public Map<Long, String> getTrueOfFalseMap2() {
		if(trueOfFalseMap2 != null){
			return trueOfFalseMap2;
		}else{
			trueOfFalseMap2 = new HashMap<Long, String>();
			trueOfFalseMap2.put(new Long(1), "无效");
			trueOfFalseMap2.put(new Long(0), "正常");
			return trueOfFalseMap2;
		}
	}


	public void setObjectId(String objectId) {
		this.objectId = objectId;
	} 
	//根据对象（站点）管理的对象，获得编号
	private String getObjectCode(String code){
		
		return "ROLE_CODE_"+code;
	}
	public String getIdentityGradeMap() {
    	return identityGradeMap;
    }
	public void setIdentityGradeMap(String identityGradeMap) {
    	this.identityGradeMap = identityGradeMap;
    }
	
	public boolean getIsLocalUser() {
		return Constants.LOGON_USER.equals(Loginer.USER_LOCAL);
	}
}

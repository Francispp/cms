package com.cyberway.common.user.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.common.dept.service.DeptManagerService;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.message.utils.Limit;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.utils.property.DefaultProperty;
import com.cyberway.core.web.BaseBizController;

public class UserController extends BaseBizController<CoreUser> {
	UserManagerService service = (UserManagerService) getServiceById("userManagerService");
	RoleManagerService roleService=(RoleManagerService)this.getServiceById("roleManagerService");
	@Override
	protected EntityDao getService() {
		return service;
	}
	
	private Long deptid;
	private Long roleid;
	private List indexstyles;
	private String selected;
	private String selectedname;

	private String oldpassword;

	private String newpassword;
	
	private String newpassword1;
	
	private int pageIndex;
	private int pageSize;
	
	private String xml;
	private String isOne;
	
	private List<CoreDept> coreDepts;
	

	public List<CoreDept> getCoreDepts() {
		return coreDepts;
	}
	public void setCoreDepts(List<CoreDept> coreDepts) {
		this.coreDepts = coreDepts;
	}

		//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
	  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	  	private Map<String, String> trueOfFalseMap1 = null;
	  	private Map<Long, String> trueOfFalseMap2 = null;
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
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public String getSelectedname() {
		return selectedname;
	}
	public void setSelectedname(String selectedname) {
		this.selectedname = selectedname;
	}
	/*
	 * 列表
	 * 
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute() throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		if(deptid!=null){
			criteria.addFilter("coreDept.deptid", deptid);
		}
		/*if(!getLoginer().checkIsAdministratorUser())//若不为超级用户,则只列出当前登录门户的资源
			criteria.addFilter("coreDept.coreOrg.oid", new Long(getLoginer().getOrgid()));*/

		if(roleid!=null){
			RoleManagerService roleService = (RoleManagerService)getServiceById("roleManagerService");
			List<CoreUser> users=roleService.getUsersByRoleId(roleid);
			List ids=new ArrayList();
			for(CoreUser cu:users)
				ids.add(cu.getUserid());
			if(!ids.isEmpty()){
				criteria.setInCriterion(Restrictions.in("userid", ids));
				//criteria.addFilter("userid", ids);
			}else
				criteria.setInCriterion(Restrictions.isNull("userid"));
		}
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);		
		return LIST_RESULT;
	}
	
	public String listInclude() throws Exception{
		return "list_include";
	}
	//显示列表
	public String list() throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		if(deptid!=null){
			criteria.addFilter("coreDept.deptid", deptid);
		}
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);		
		return "view_list";
	}
    //显示AD列表
	public String listAD() throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		if(roleid!=null){
			RoleManagerService roleService = (RoleManagerService)getServiceById("roleManagerService");
			List<CoreUser> users=roleService.getADUsersByRoleId(roleid);
			if(getPageIndex() <1)
			{
				setPageIndex(1);
			}
			if(getPageSize()== 0)
			{
				setPageSize(Constants.MESSAGE_PAGESIZE);
			}
			Limit limit = new Limit (getPageIndex(), getPageSize());
			Page page = new Page(pageIndex, users.size(), pageSize, users);
			this.getHttpServletRequest().setAttribute("_data",page);

	}
		return "ad_list";
	}
	/**
	 * 树型管理人员
	 * @return
	 */
	public String frame(){
		
		return "frame";
	}
	/* 编辑
	 * @see com.cyberway.core.web.BaseBizController#edit()
	 */
	public String edit() throws Exception{
		CoreUser user;
		DeptManagerService deptService = (DeptManagerService) this.getServiceById("deptManagerService");
		if(id==null){
		  user=new CoreUser();
		  //设置默认值
		  user.setState(new Long(1));
		  if(deptid!=null){
			  //DeptManagerService deptService = (DeptManagerService)getServiceById("deptManagerService");
               //user.setCoreDept(deptService.get(deptid));
		  }
		}else{
		  user=service.get(id);  
		}
		this.setDomain(user);
		if(coreDepts==null){
			setCoreDepts(deptService.getAll());
		}
		return INPUT;
	}
	
	public String selectUser() throws Exception{
		return "select_user";
	}
	public String searchUser() throws Exception{
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml +="<RECORDSET>";
		if(Constants.LOGON_USER.equals(Loginer.USER_LOCAL))
		{
		xml = service.toUserString(StringUtil.toGB2312(keys),xml);
		}
		else if(Constants.LOGON_USER.equals(Loginer.USER_AD))
		{
		xml = service.toUserStringFromAEP(StringUtil.toGB2312(keys),xml);
		}
		else{
			xml = service.toUserStringFromAEPAndLocal(StringUtil.toGB2312(keys), xml);
		}
		
		xml +="</RECORDSET>";
		System.out.println(xml);
		return "search_user";
	}

	/* 保存
	 * @see com.cyberway.core.web.BaseBizController#saveOrUpdate()
	 */
	public String saveOrUpdate() {
		DeptManagerService deptService = (DeptManagerService) this.getServiceById("deptManagerService");
		if(domain!=null){
			/*if(domain.getUserid()==null){
				String _pwd=BasicService.getEncString(domain.getLoginid(), Constants.USER_INITIALIZE_PASSWORD);
			    domain.setPassword(_pwd);
			}*/
		   String deptid = getHttpServletRequest().getParameter("domain.coreDept.deptid");
		   if(deptid!=null && !"".equals(deptid.trim())){
			   CoreDept coreDept= deptService.get(Long.valueOf(deptid));
			   if(coreDept!=null){
					domain.setCoreDept(coreDept);
			   }
		   } 
			if(domain.getCoreDept()==null||domain.getCoreDept().getDeptid()==null){
				CoreDept coreDept=new CoreDept();
				coreDept.setDeptid(Constants.KMS_DEPTID);
				domain.setCoreDept(coreDept);
			}
			if(domain.getUserid()==null || domain.getUserid()==0){
				Boolean unique = service.isNotUnique(domain, "loginid");
				String tempError = "";
				// 如果已经存在相同的登录ID
				if (unique){
					tempError += "用户ID已经存在！" ;
				}
				unique = service.isNotUnique(domain, "empcode");
		        // 如果已经存在相同的员工编号
				if (unique){
					tempError += "员工编号已经存在！" ;
				}
				if(!"".equals(tempError)){
					this.addActionMessage(tempError);
					try {
						edit();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return SUCCESS;
				}
			}
			domain=service.saveOrUpdate(domain);
			if(keys!=null){
				roleService.removeCoreUserRolesByUserId(domain.getUserid());
				if (!StringUtil.isEmpty(keys)) {
					List<Long> list = StringUtil.splitToList(keys, ",");
					for(Long l:list){
						roleService.insertUsersToRole(l+"", domain.getUserid()+"");
					}
					
				}
			}
			this.addActionMessage("保存成功！");
		}else
			this.addActionError("保存对象为空！");
		if(coreDepts==null){
			setCoreDepts(deptService.getAll());
		}
		return SUCCESS;
	}

	// 修改用户密码
	public String updateUserPWD()throws Exception {

		Long userid = getLoginer().getUserid();
		String password = getLoginer().getPassword();

		if (!StringUtil.isEmpty(oldpassword)) {
			String encodepwd = UserManagerService.encodePassword(oldpassword);
			if (password.equals(encodepwd)) {
				service.modifyUsersPassword(userid.toString(), newpassword);
				this.addActionMessage("修改用户密码成功!");
				newpassword=null;
			} else {
				this.addActionMessage("用户密码不正确，请重新输入!");
			}
		}
		return "updatepwd";

	}	
	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}



	public List getIndexstyles() {
		if(indexstyles==null){
			indexstyles=new ArrayList();
			String s=DefaultProperty.getProperty(com.cyberway.common.base.objects.Constants.INDEX_PAGE_STYLE);
			String [] array=StringUtil.split(s, ",");
			   if(array!=null){
				   for(String style:array)
					   indexstyles.add(style);
			   }	
		}
		return indexstyles;
	}

	public void setIndexstyles(List indexstyles) {
		this.indexstyles = indexstyles;
	}
	public Long getRoleid() {
		return roleid;
	}
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getNewpassword1() {
		return newpassword1;
	}
	public void setNewpassword1(String newpassword1) {
		this.newpassword1 = newpassword1;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Map<String, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<String, String>();
			trueOfFalseMap1.put("男", "男");
			trueOfFalseMap1.put("女", "女");
			return trueOfFalseMap1;
		}
	}
	public Map<Long, String> getTrueOfFalseMap2() {
		if(trueOfFalseMap2 != null){
			return trueOfFalseMap2;
		}else{
			trueOfFalseMap2 = new HashMap<Long, String>();
			trueOfFalseMap2.put(new Long(1), "正常");
			trueOfFalseMap2.put(new Long(0), "无效");
			return trueOfFalseMap2;
		}
	}
}

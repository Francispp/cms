package com.cyberway.common.dept.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyberway.cms.component.webuser.view.WebUserController;
import com.cyberway.common.dept.service.DeptManagerService;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.org.service.OrgManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class DeptController extends BaseBizController<CoreDept> {
	DeptManagerService service = (DeptManagerService) this
			.getServiceById("deptManagerService");

	private static final String ADMIN_AJAX = "adminAjax";
	private static final String ADMIN_LIST = "adminList";
	private static final String ADMIN_INPUT = "adminInput";
	
    private Long pdeptid;
    private Long orgId;//机构id
    
  //WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
      	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
    private Map<Integer, String> trueOfFalseMap1 = null;
    
	@Override
	protected EntityDao getService() {
		return service;
	}

	/*
	 * 部门列表
	 * 
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute() throws Exception {
		//显示树型结构
		if(pageStyle==0){
			return "frame";
		}
		CriteriaSetup criteria = new CriteriaSetup();
		if(!getLoginer().checkIsAdministratorUser())//若不为超级用户,则只列出当前登录门户的
			criteria.addFilter("coreOrg.orgCode", getLoginer().getOrgCode());

		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);			
/*		List list = service.getAll();
		getHttpServletRequest().setAttribute("items", list);*/
		return LIST_RESULT;
	}
	/**
	 * 树型结构
	 * 
	 * @return
	 */
	public String tree() throws Exception{
		if(getLoginer().checkIsAdministratorUser())//若不为超级用户,则只列出当前登录门户的
		   items = service.getAll();
		else
		   items=service.find("from CoreDept where coreOrg.orgCode=?", new Object[]{getLoginer().getOrgCode()});
		getHttpServletRequest().setAttribute("items", items);
		OrgManagerService orgService=(OrgManagerService)getServiceById("orgManagerService");
		//根据当前用户，获得机构
		getHttpServletRequest().setAttribute("orgs", orgService.getOrgs(getLoginer()));
		return TREE_RESULT;
	}

	/**
	 * 树型结构
	 * 
	 * @return
	 */
	public String select() {
		items = service.getAll();
		String MULTISELECT=getHttpServletRequest().getParameter("MULTISELECT");
		if(!StringUtil.isEmpty(MULTISELECT)&&MULTISELECT.equalsIgnoreCase("true"));
		  getHttpServletRequest().setAttribute("MULTISELECT", "true");
		  
		getHttpServletRequest().setAttribute("items", items);
		return TREE_RESULT;
	}
	/* 编辑
	 * @see com.cyberway.core.web.BaseBizController#edit()
	 */
	public String edit()throws Exception{

		if(id!=null){
			get();
			/*if(!getLoginer().haveThePermission("SYS_DEPT_MODI"))
			  setIsEdit(false);	*/
			//logger.info("portalcode:"+domain.getCorePortal().getPortalCode());
		}else{
			domain=new CoreDept();
			domain.setPdeptid(pdeptid);
			//设置站点名称和id
			CoreOrg cp=new CoreOrg();
			if(orgId==null){
				orgId=new Long(getLoginer().getOrgid());
				//cp.setPortalName(getLoginer().getPortal().getCname());
			}
			cp.setOid(orgId);
			//logger.info("portalId:"+portalId);
			domain.setCoreOrg(cp);
		}
		//设置显示父部门名称
		if(domain.getPdeptid()!=null){
			CoreDept pdept=service.get(domain.getPdeptid());
			if(pdept!=null)
				domain.setPdeptname(pdept.getDeptname());
		}
		//根据当前用户，获得机构
		OrgManagerService orgService=(OrgManagerService)getServiceById("orgManagerService");
		getHttpServletRequest().setAttribute("orgs", orgService.getOrgs(getLoginer()));
		
		return EDIT_RESULT;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete() throws Exception{
		if(!StringUtil.isEmpty(keys)){
			List list=StringUtil.splitToList(keys,",");
			
			boolean canDel=service.isCanDeleteDept(list);
			if(canDel){
			 getService().removeByIds(list);
			 this.addActionMessage("删除成功！");
			}else
				addActionMessage("请先删除选择部门的下级部门、用户和员工！");
		}else
			addActionError("请选择需删除的记录！");
		return adminList();
	}	
	
	public String adminList() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);		
		return ADMIN_LIST;
	}
	
	public String adminAdd() throws Exception {
		super.add();
		return ADMIN_INPUT;
	}
	
	public String adminEdit() throws Exception {
		super.edit();
		return ADMIN_INPUT;
	}
	
	public String adminDelete() throws Exception {
		delete();
		WebUserController.resetDeptList();
		return ADMIN_LIST;
	}
	
	public String adminSaveOrUpdate() throws Exception {
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("{\"success\": ");
		domain = service.saveOrUpdate(domain);
		jsonString.append("true, \"msg\": \"保存成功\"}");
		this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
		WebUserController.resetDeptList();
		return ADMIN_AJAX;
	}

	public Long getPdeptid() {
		return pdeptid;
	}

	public void setPdeptid(Long pdeptid) {
		this.pdeptid = pdeptid;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

   public Map<Integer, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Integer, String>();
			trueOfFalseMap1.put(new Integer(1), "无效");
			trueOfFalseMap1.put(new Integer(0), "正常");
			return trueOfFalseMap1;
		}
	}
}

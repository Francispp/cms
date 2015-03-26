package com.cyberway.common.user.view;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.common.dept.service.DeptManagerService;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.org.service.OrgManagerService;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.Constants;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;

/**
 * 用户选择
 * @author caiw
 *
 */
public class UserSelectController extends BaseBizController<CoreUser> {
	UserManagerService service = (UserManagerService) getServiceById("userManagerService");
	DeptManagerService deptService = (DeptManagerService) getServiceById("deptManagerService");
	RoleManagerService roleService = (RoleManagerService) getServiceById("roleManagerService");
	
	private String multiSelect;
	private String selectChild;
	private List depts;
	private List users;
	private List roles;
	private String  selectedids;
	
	@Override
	protected EntityDao getService() {
		return service;
	}

	/* (non-Javadoc)
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute() throws Exception {
		OrgManagerService orgService=(OrgManagerService)getServiceById("orgManagerService");
		//根据当前用户，获得机构
		getHttpServletRequest().setAttribute("orgs", orgService.getOrgs(getLoginer()));
		depts=deptService.getAll();
		users=service.getAll();
		
		return "select";
	}
	
	/**
	 * 选择部门、人员
	 * @return
	 * @throws Exception
	 */
	public String selectAll() throws Exception {
		OrgManagerService orgService=(OrgManagerService)getServiceById("orgManagerService");
		//根据当前用户，获得机构
		getHttpServletRequest().setAttribute("orgs", orgService.getOrgs(getLoginer()));
		depts=deptService.getAll();
		//roles=roleService.getAll();
		users=service.getAll();	
		
		return "selectAll";
	}
	public String getMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(String multiSelect) {
		this.multiSelect = multiSelect;
	}

	public List getDepts() {
		return depts;
	}

	public void setDepts(List depts) {
		this.depts = depts;
	}

	public String getSelectChild() {
		return selectChild;
	}

	public void setSelectChild(String selectChild) {
		this.selectChild = selectChild;
	}

	public List getUsers() {
		return users;
	}

	public void setUsers(List users) {
		this.users = users;
	}
	public String getSelectedids() {
		return selectedids;
	}

	public void setSelectedids(String selectedids) {
		this.selectedids = selectedids;
	}

	public List getRoles() {
		return roles;
	}

	public void setRoles(List roles) {
		this.roles = roles;
	}

}

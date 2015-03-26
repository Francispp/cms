package com.cyberway.common.role.view;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.org.service.OrgManagerService;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.core.Constants;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;

public class RoleSelectController extends BaseBizController<CoreRole> {
	//DeptManagerService deptService = (DeptManagerService) getServiceById("deptManagerService");
	RoleManagerService service = (RoleManagerService) getServiceById("roleManagerService");

	private String multiSelect;
	private String selectChild;
	private List roles;
	private String  selectedids;
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String execute() throws Exception {
		OrgManagerService orgService=(OrgManagerService)getServiceById("orgManagerService");
		//根据当前用户，获得机构
		getHttpServletRequest().setAttribute("orgs", orgService.getOrgs(getLoginer()));
		//depts=deptService.getAll();
		roles=service.getAll();
		
		return "select";
	}


	public String getMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(String multiSelect) {
		this.multiSelect = multiSelect;
	}

	public String getSelectChild() {
		return selectChild;
	}

	public void setSelectChild(String selectChild) {
		this.selectChild = selectChild;
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

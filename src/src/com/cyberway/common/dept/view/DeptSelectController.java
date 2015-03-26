package com.cyberway.common.dept.view;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.common.dept.service.DeptManagerService;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.org.service.OrgManagerService;
import com.cyberway.core.Constants;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;

public class DeptSelectController extends BaseBizController<CoreDept> {
	DeptManagerService service = (DeptManagerService) getServiceById("deptManagerService");

	@Override
	protected EntityDao getService() {
		
		return service;
	}
	private String multiSelect;
	private String selectChild;
	private List depts;
	private String  selectedids;
	
	/* (non-Javadoc)
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute() throws Exception {
		
		OrgManagerService orgService=(OrgManagerService)getServiceById("orgManagerService");
		//根据当前用户，获得机构
		getHttpServletRequest().setAttribute("orgs", orgService.getOrgs(getLoginer()));
		depts=service.getAll();
		
		return "select";
	}

	public List getDepts() {
		return depts;
	}

	public void setDepts(List depts) {
		this.depts = depts;
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
}

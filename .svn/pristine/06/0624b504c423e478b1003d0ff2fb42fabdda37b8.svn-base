package com.cyberway.common.org.view;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.org.service.OrgManagerService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class OrgController extends BaseBizController<CoreOrg> {
	OrgManagerService	service	= (OrgManagerService) this.getServiceById("orgManagerService");

	@Override
	protected OrgManagerService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
    public String execute() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		if (!getLoginer().checkIsAdministratorUser())// 若不为超级用户,则只列出当前用户机构名称
			criteria.addFilter("oid", new Long(getLoginer().getOrgid()));

		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);

		if (items != null && items.size() > 0) {// 获得当前对象的父机构名称
			List orgs = new ArrayList();
			for (int i = 0; i < items.size(); i++) {
				CoreOrg org = (CoreOrg) items.get(i);
				if (org != null && org.getPorgid() != null)
					org.setPorgName((service.getCoreOrgFromCache(org.getPorgid().toString())).getOrgName());
				orgs.add(org);
			}
			this.setItems(orgs);
		}

		return LIST_RESULT;
	}

	@Override
    public String edit() throws Exception {
	    super.edit();
	    List<CoreOrg> orgList = service.getAllOrgsFromCache();
	    if(id != null){
	    	CoreOrg org = service.getCoreOrgFromCache(id.toString());
	    	orgList.remove(org);
	    }
	    getHttpServletRequest().setAttribute("orgs", orgList);
	    return INPUT;
    }

	@Override
    public String saveOrUpdate() throws Exception {
	    super.saveOrUpdate();
	    getHttpServletRequest().setAttribute("orgs", service.getAllOrgsFromCache());
	    addActionMessage("保存成功!");
	    return EDIT_RESULT;
    }

	@Override
    public String delete() throws Exception {
		if(!StringUtil.isEmpty(keys)){
			List<Long> list=StringUtil.splitToList(keys,",");
			for(Long id : list){
				List<CoreOrg> l = service.getSubOrg(id);
				if(l.size() != 0){
					addActionMessage("该组织还有下级组织,请先删除下级组织后再删除该组织!");
					return execute();
				}else{
					getService().removeById(id);
					getService().removeOrgFromCache(id);
				}
			}
			
			this.addActionMessage(this.getText("RESOURCE.HINTINFO.DELETESUCCESS"));
			
		}else
			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return execute();
    }
}

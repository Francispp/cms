package com.cyberway.common.identity.view;

import org.hibernate.criterion.Order;

import com.cyberway.common.domain.CoreIdentity;
import com.cyberway.common.identity.service.IdentityManagerService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;

/**
 * 身份管理
 * 
 * @author taoz
 * 
 *         2011-10-26下午05:27:02
 */
public class IdentityController extends BaseBizController<CoreIdentity> {
	IdentityManagerService	service	= (IdentityManagerService) this.getServiceById("identityManagerService");

	@Override
	protected IdentityManagerService getService() {
		return service;
	}
	
	@Override
	public String execute() throws Exception {
		return list();
	}
	
	@Override
    public String list() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		criteria.addFilter("siteId", getLoginerSiteId());
		criteria.setInOrder(Order.asc("orderno"));
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
    }

	@Override
    public String saveOrUpdate() throws Exception {
		if (getLoginerSiteId() != 0 && domain.getSiteId()==null) {
			domain.setSiteId(getLoginerSiteId());
		}
		if(getService().identityCodeIsUnique(domain.getIid(), domain.getIcode(), domain.getSiteId())){
			domain=getService().saveOrUpdate(domain);
			addActionMessage("保存成功!");
		}else{
			addActionError("身份代码已存在，请重新输入!");
		}
	    return EDIT_RESULT;
    }

}

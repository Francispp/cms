package com.cyberway.common.portal.view;

import com.cyberway.common.domain.CorePortal;
import com.cyberway.common.portal.service.PortalManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;

/**
 * 门户管理控制器
 * @author caiw
 *
 */
public class PortalController extends BaseBizController<CorePortal> {
	PortalManagerService service=(PortalManagerService)this.getServiceById("portalManagerService");
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
	/* 列表
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute()throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		if(!getLoginer().checkIsAdministratorUser())//若不为超级用户,则只列出当前登录门户的资源
			criteria.addFilter("code", new Long(getLoginer().getPortal().getPortalcode()));

		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		//getHttpServletRequest().setAttribute("portals", getPortals());
		
		return LIST_RESULT;
	}	

}

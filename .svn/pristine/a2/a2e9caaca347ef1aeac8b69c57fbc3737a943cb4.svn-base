package com.cyberway.cms.exposed;

import java.util.List;

import com.cyberway.common.domain.CoreMenu;
import com.cyberway.common.menu.service.MenuManagerService;
import com.cyberway.core.utils.ServiceLocator;

public class MenuServiceJsUtil {
	private MenuManagerService menuManagerService ;


	
	public MenuManagerService getMenuManagerService() {
		return menuManagerService;
	}
	public void setMenuManagerService(MenuManagerService menuManagerService) {
		this.menuManagerService = menuManagerService;
	}
	public List<CoreMenu> getMenusByPortalid(Long portalid)
	{
		//menuManggerService = (MenuManager)ServiceLocator.getBean("menuManggerService");
		
		return menuManagerService.getMenusByPortalid(portalid);
	}
	public List<CoreMenu> getMenusBypmip(Long pmid)
	{
		return menuManagerService.getMenusBypmip(pmid);
	}

}

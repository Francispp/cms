package com.cyberway.common.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.menu.domain.MenuResource;
import com.cyberway.common.menu.service.MenuResourceService;
import com.cyberway.core.objects.Constants;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseController;

public class IndexController extends BaseController {

	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	private String language;
	private String style;
	private List assigns;
	private List allSite;
	private Long siteId;
	private String status;
	private String siteHomeUrl;
	
	public String getSiteHomeUrl() {
		return siteHomeUrl;
	}
	public void setSiteHomeUrl(String siteHomeUrl) {
		this.siteHomeUrl = siteHomeUrl;
	}
	/**
	 * 菜单代码
	 */
	private String menuCode;
	
	/**
	 * 子菜单
	 */
	private Set<MenuResource> subMenu;
	
	/**
	 * 顶级菜单
	 */
	private List<MenuResource> topMenu;
	
	MenuResourceService	menuResourceService	= (MenuResourceService) this.getServiceById("menuResourceService");
	
	/* 后台管理主页
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute()throws Exception{
		topMenu = menuResourceService.getTopMenu();
		SiteManagerService siteManagerService=(SiteManagerService)getServiceById("siteManagerService");
		Loginer loginer=(Loginer)getSession().get(Loginer.LOGININFO_SESSION);
		if(loginer != null)
		{
		//String indexUrl="";
		if(siteId!=null&&siteId >= 0){
			loginer.setSiteId(siteId);
			getSession().put(Loginer.LOGININFO_SESSION,loginer);
		}
		else if(loginer.getSiteId() == null)
		{
			loginer.setSiteId(0l);
		getSession().put(Loginer.LOGININFO_SESSION,loginer);
		}
		
		//System.out.println(loginer.getSiteId()+"========================");
		setAllSite(siteManagerService.getListSelectCmsSite(loginer,1));
		}
		
		return "index";
	}
	/**
	 * 系统配置菜单
	 * @return
	 * @throws Exception
	 */
	public String menu()throws Exception{
		//Loginer loginer=(Loginer)this.getSession().get(Loginer.LOGININFO_SESSION);
		
		//getHttpServletRequest().setAttribute("items", loginer.getSystemConfigMenus());
		Map apps=getActionContext().getApplication();
		 
		if(!apps.containsKey(com.cyberway.cms.Constants.SITES_IN_APPLICATION)){
		 SiteManagerService siteservice=(SiteManagerService)this.getServiceById("siteManagerService");	
		 apps.put(com.cyberway.cms.Constants.SITES_IN_APPLICATION, siteservice.getAllSites());
		 //apps.put(com.cyberway.cms.Constants.SITES_IN_APPLICATION+"_map", siteservice.getAllSitesMap());
		}
		return "configmenu";
	}
	
	/**
	 * 返回站点首页
	 * @return
	 * @throws Exception
	 */
	public String siteHome() throws Exception
	{
		Loginer loginer=(Loginer)getSession().get(Loginer.LOGININFO_SESSION);
		 SiteManagerService siteservice=(SiteManagerService)this.getServiceById("siteManagerService");	
		 String url = com.cyberway.cms.Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL;
		 int siteport = getHttpServletRequest().getServerPort();
		if(loginer != null && loginer.getSiteId() >0)
		{
			CmsSite site = siteservice.getSiteFromCache(loginer.getSiteId());
			url += site.getSitehttp();
		}
		else
			url +=getHttpServletRequest().getServerName();
		//FIXME 2012-9-14 11:24:08 Dicky
//		url = "http://localhost";
		url +=(siteport == 80 ? "" : ":" + siteport);
		setSiteHomeUrl(url);
		
		return "site_home";
		
	}
	
	/**
	 * 系统配置菜单
	 * @return
	 * @throws Exception
	 */
	public String left()throws Exception{
		
		return "left";
	}
	
    public String systemMenu()throws Exception{
    	if(StringUtils.isNotBlank(menuCode)){
    		List<MenuResource> menus = menuResourceService.find("from MenuResource where menuCode =? and ( pid is null or pid=0)", new Object[]{menuCode});
    		if(menus.size() > 0){
    			subMenu = menus.get(0).getSubMenuResources();
    		}
    	}
		return "system_menu";
	}
	
	//主内容体
	public String main()throws Exception{
		/*
		FlowCommonServcie flowservice=(FlowCommonServcie)this.getServiceById("flowCommon");
		Loginer loginer=(Loginer)this.getSession().get(Loginer.LOGININFO_SESSION);
		assigns=flowservice.getAssignments(loginer, 0, null,null);
		*/
		return "main";
	}
	//设置语言类型
	public String language()throws Exception{
		if(!StringUtil.isEmpty(language)){
			getSession().put(Constants.LOCALE_LANGUAGE, language);
			if(language.endsWith(Constants.LANGUAGE_ZH_CN))
				  getActionContext().setLocale(Locale.CHINESE);
			    else if(language.endsWith(Constants.LANGUAGE_EN))
			    	getActionContext().setLocale(Locale.US);
			    else if(language.endsWith(Constants.LANGUAGE_ZH_TW))
			    	getActionContext().setLocale(Locale.TAIWAN);
			    else
			    	getActionContext().setLocale(Locale.CHINESE);			
		}
		if(!StringUtil.isEmpty(style)){
			logger.info("style:"+style);
			getSession().put(com.cyberway.core.Constants.STYLE_IN_SESSION, style);
		}
		return "language";
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public List getAssigns() {
		if(assigns==null)
			assigns=new ArrayList();
		return assigns;
	}
	public void setAssigns(List assigns) {
		this.assigns = assigns;
	}
	public List getAllSite() {
		return allSite;
	}
	public void setAllSite(List allSite) {
		this.allSite = allSite;
	}
	public String getMenuCode() {
    	return menuCode;
    }
	public void setMenuCode(String menuCode) {
    	this.menuCode = menuCode;
    }
	public Set<MenuResource> getSubMenu() {
    	return subMenu;
    }
	public void setSubMenu(Set<MenuResource> subMenu) {
    	this.subMenu = subMenu;
    }
	public List<MenuResource> getTopMenu() {
    	return topMenu;
    }
	public void setTopMenu(List<MenuResource> topMenu) {
    	this.topMenu = topMenu;
    }
	
	
	
}
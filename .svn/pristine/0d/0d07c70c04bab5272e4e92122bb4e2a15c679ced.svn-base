package com.cyberway.cms.site.view;

import java.net.URL;

import com.cyberway.cms.Constants;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.site.service.SiteCommonService;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.template.service.TemplateService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class IndexController extends BaseBizController {

	@Override
	protected EntityDao getService() {

		return null;
	}
	private TemplateService templateService=(TemplateService)getServiceById("templateService");
	private TemplateManagerService templateManagerService;//模板管理service
	public TemplateManagerService getTemplateManagerService() {
		templateManagerService=(TemplateManagerService)getServiceById("templateManagerService");
		return templateManagerService;
	}
	
	private SiteManagerService service ;
	
    
	private Long templateId;
	private String templateName;
	private String templateUrl; 
	private Long siteId;
	private int preview;//预览
	
	/**
	 * 首页模板访问方法
	 * @return
	 * @throws Exception
	 */
	public String execute() throws Exception
	{  
		CmsSite site=null;		
		if(siteId!=null)
		 site=getSiteManagerService().getSiteFromCache(siteId);
		else
		 site=getSiteManagerService().getSite(getHttpServletRequest().getServerName(), getHttpServletRequest().getServerPort());
		if(site==null) new Exception("站点不存在，请使用域名访问！");		 
		if(new Integer(1).equals(site.getIsLogined())){
			//未登录,跳转到当前登录地址
			if(getObjectLoginer()==null){
				if(StringUtil.isEmpty(site.getLoginUrl())){
					templateUrl=Constants.LOGON_DEFAULT_URL;
				}else{//指定登录地址
					templateUrl=site.getLoginUrl();
				}
				return "input_redirect";
			}
		}
		if(!StringUtil.isEmpty(templateName)){//按模板名称，获得模板id
			Template template=getTemplateManagerService().getTemplateByName(siteId,templateName);
			if(template!=null)
				templateId=template.getId();
		}
		//获得指定模板id 的访问地址
		if(templateId==null)
			new Exception("未设置默认的首页模板！");		
		//更新频道点击率
		((SiteCommonService)getServiceById("siteCommonService")).updateClickCount(siteId,SiteCommonService.SITE_TYPE);
		templateUrl=templateService.getTemplatePage(templateId);
		return EDIT_RESULT; 
	}
	
	/**
	 * 执行通用模板
	 * @return
	 * @throws Exception
	 */
	public String view()throws Exception
	{  
		if(templateId==null && !StringUtil.isEmpty(templateName)){//按模板名称，获得模板id
			Template template=getTemplateManagerService().getTemplateByName(siteId,templateName);
			if(template!=null)
				templateId=template.getId();
		}
		if(templateId==null)
			new Exception("未设置模板Id！");	
		templateUrl=templateService.getTemplatePage(templateId);		
		return EDIT_RESULT;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public int getPreview() {
		return preview;
	}

	public void setPreview(int preview) {
		this.preview = preview;
	}

	//获得站点service
	public SiteManagerService getSiteManagerService(){
		if(service==null)
			service = (SiteManagerService)getServiceById("siteManagerService");
		return service;
	}
    /**
     * 
     * @return
     * @throws Exception
     */
    protected Object getObjectLoginer() throws Exception{
    	return getSession().get(Loginer.LOGININFO_SESSION);    	
    } 

}

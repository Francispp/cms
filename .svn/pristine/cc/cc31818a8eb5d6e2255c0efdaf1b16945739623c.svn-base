package com.cyberway.cms.component.leaveword.view;

import java.util.Date;

import org.hibernate.criterion.Order;

import com.cyberway.cms.component.leaveword.domain.LeaveWord;
import com.cyberway.cms.component.leaveword.service.LeaveWordService;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class LeaveWordController extends BaseBizController<LeaveWord> {

	private LeaveWordService service = (LeaveWordService) this.getServiceById("leaveWordService");
	private TemplateManagerService templateManagerService = (TemplateManagerService)this.getServiceById("templateManagerService");
	@Override
	protected EntityDao<LeaveWord> getService() { 
		return service;
	} 
	private String to;
	private String subject;
	
	private String templateUrl;
	private String templateName;
	
	/**
	 * 列表使用的action
	 */
	public String list() throws Exception{
		CriteriaSetup setup  = new CriteriaSetup();
		setup.setInOrder(Order.desc("leaveTime"));
		//设默认 以时间，单位 排序  
		doListEntity(setup, getTableId(), Page.DEFAULT_PAGE_SIZE);
		
		return LIST_RESULT;
	}
	
	
	/**
	 * 通用保存方法
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdate() throws Exception{
		if(getId() == null) 
			init(); 
		
		//添加默认值  
		super.saveOrUpdate();
		
		if(!StringUtil.isEmpty(templateName)){	
			//统一获取地址
			templateUrl=templateManagerService.getTemplateUrl(getHttpServletRequest().getServerName(), templateName);
			
			return "url";
		}else{
			 setId( domain.getOid()); 
			return edit();
		}
	}
	
	
	/**
	 * 通用保存方法
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception{ 
		 return "create";
	}
	
	
	/**
	 * 通用保存方法
	 * @return
	 * @throws Exception
	 */
	public String sendEmail() throws Exception{
		if(id!=null)
			get();
		 return "email";
	}
	
	/**
	 * 通用保存方法
	 * @return
	 * @throws Exception
	 */
	public String deleteLeave() throws Exception{  
		super.delete();
		//进入到List页
		return  list();
	}
	
	/**
	 * 通用的编辑方法
	 * @return
	 * @throws Exception
	 */
	public String edit()throws Exception{ 
		
		domain=getDomainClass().newInstance();
		 
		//获取当前留言信息
		getHttpServletRequest().setAttribute("leaveWord", getService().get(id));
		
		if(!StringUtil.isEmpty(templateName)){
			//统一获取地址
			templateUrl=templateManagerService.getTemplateUrl(getHttpServletRequest().getServerName(), templateName);
			return "url";
		}else{
			return EDIT_RESULT;
		} 
	}
	
	
	/**
	 * 通用的编辑方法
	 * @return
	 * @throws Exception
	 */
	public String issue()throws Exception{ 
		 
		String status = getHttpServletRequest().getParameter("status") == null ? "1" : getHttpServletRequest().getParameter("status");
		
		if(!StringUtil.isEmpty(keys)){
			for (String id : keys.split(",")) {
				 LeaveWord leave = service.get(Long.parseLong(id));
				 leave.setStatus(Long.parseLong(status));
				 service.saveOrUpdate(leave);
			}
		}
		
		return list(); 
	}
	
	/**
	 * 通用的编辑方法
	 * @return
	 * @throws Exception
	 */
	public String issueItem()throws Exception{ 
		 
		String status = getHttpServletRequest().getParameter("status") == null ? "1" : getHttpServletRequest().getParameter("status");
		
		if(!StringUtil.isEmpty(keys)){ 
			 LeaveWord leave = service.get(Long.parseLong(keys));
			 leave.setStatus(Long.parseLong(status));
			 service.saveOrUpdate(leave); 
		} 
		
		return edit(); 
	}
	
	
	private void init() throws Exception{
		
		if(domain.getLeaveTime() == null)
			this.domain.setLeaveTime(new Date());
		if(domain.getRemoveIp() == null)
			this.domain.setRemoveIp(getHttpServletRequest().getRemoteAddr());
		
		if(this.domain.getSiteId() <= 0){
			//获取站点信息
			CmsSite site  = ((SiteManagerService) this.getServiceById("siteManagerService")).getSite(getHttpServletRequest().getServerName(),0);
			if(site != null){
				this.domain.setSiteId(site.getOid());
				this.domain.setSiteName(site.getSitename());
			}
		}
		
		if(this.domain.getUserId() <= 0){
			//判断是否登录
			Loginer loginer = (Loginer)getHttpServletRequest().getSession().getAttribute(Loginer.LOGININFO_SESSION);
			if(loginer != null && loginer.getUsername() != null){
				this.domain.setUserName(loginer.getUsername());
				this.domain.setUserId(loginer.getUserid().longValue());
			}
		}
	} 

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}
	
}

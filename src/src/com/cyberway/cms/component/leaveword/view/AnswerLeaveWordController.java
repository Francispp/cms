package com.cyberway.cms.component.leaveword.view;

import java.io.Serializable;
import java.util.Date;

import com.cyberway.cms.component.leaveword.domain.AnswerLeaveWord;
import com.cyberway.cms.component.leaveword.domain.LeaveWord;
import com.cyberway.cms.component.leaveword.service.AnswerLeaveWordService;
import com.cyberway.cms.component.leaveword.service.LeaveWordService; 
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class AnswerLeaveWordController extends BaseBizController<AnswerLeaveWord> {

	private AnswerLeaveWordService service = (AnswerLeaveWordService) this.getServiceById("answerLeaveWordService");
	private LeaveWordService leaveWordService = (LeaveWordService) this.getServiceById("leaveWordService");
	private TemplateManagerService templateManagerService = (TemplateManagerService)this.getServiceById("templateManagerService");
	@Override
	protected EntityDao<AnswerLeaveWord> getService() { 
		return service;
	} 
	
	private String templateUrl;
	private String templateName;
	
	 
	
	
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
			setId( domain.getTopicid()); 
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
	 * 通用删除方法
	 * @return
	 * @throws Exception
	 */
	public String deleteAnswer() throws Exception{   
		get();
		
		if(this.domain != null && this.domain.getTopicid() != null){ 
			long topicid = this.domain.getTopicid().longValue();
			service.removeById(id); 
		
			if(!StringUtil.isEmpty(templateName)){	
				//统一获取地址
				templateUrl=templateManagerService.getTemplateUrl(getHttpServletRequest().getServerName(), templateName);
				return "url";
			}else{ 
				setId(topicid);
				return edit();
			}
		}
		//进入到List页
		return  list();
	}
	
	/**
	 * 添加默认值 
	 * @throws Exception
	 */
	private void init() throws Exception{
		
		if(domain.getAnswerTime() == null)
			this.domain.setAnswerTime(new Date()); 
		
		if(StringUtil.isEmpty(this.domain.getUserName())){ 
			//判断是否登录
			Loginer loginer = (Loginer)getHttpServletRequest().getSession().getAttribute(Loginer.LOGININFO_SESSION);
			if(loginer != null && loginer.getUsername() != null){
				this.domain.setUserName(loginer.getUsername());
				this.domain.setUserId(loginer.getUserid().longValue());
			}
		}
	} 
	
	/**
	 * 通用的编辑方法
	 * @return
	 * @throws Exception
	 */
	public String edit()throws Exception{
		 
		//获取当前留言信息
		getHttpServletRequest().setAttribute("leaveWord",leaveWordService.get(LeaveWord.class,getId()));
		setDomain(getDomainClass().newInstance());
		
		if(!StringUtil.isEmpty(templateName)){
			//统一获取地址
			templateUrl=templateManagerService.getTemplateUrl(getHttpServletRequest().getServerName(), templateName);
			return "url";
		}else{
			return EDIT_RESULT;
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
	
	
}

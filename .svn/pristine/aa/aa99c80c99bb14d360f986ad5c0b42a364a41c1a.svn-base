package com.cyberway.cms.template.view;

import java.util.List;

import ognl.Ognl;
import ognl.OgnlException;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.TemplateGather;
import com.cyberway.cms.template.service.TemplateGatherService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class TemplateGatherController extends BaseBizController<TemplateGather>{

	TemplateGatherService service = (TemplateGatherService) this.getServiceById("templateGatherService");
	
	private Long templateId;

	private Long channelId;

	private Integer templateType;

	private Long siteId;

	private Long includeChannelId;
	private String includeChannelName;
	
	public String getIncludeChannelName() {
		return includeChannelName;
	}

	public void setIncludeChannelName(String includeChannelName) {
		this.includeChannelName = includeChannelName;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	
	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getIncludeChannelId() {
		return includeChannelId;
	}

	public void setIncludeChannelId(Long includeChannelId) {
		this.includeChannelId = includeChannelId;
	}
	
	@Override
	protected TemplateGatherService getService() {
		return service;
	}
	
	public String list() throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		if(templateType!=null&&templateType!=5){
			criteria.addFilter("channelId", channelId);
		}
		criteria.addFilter("templateId", templateId);
		criteria.addFilter("templateType", templateType);
		criteria.addFilter("siteId", siteId);
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	
	
	/**
	 * 得到所有频道
	 * @throws Exception
	 */
	  public void gathertree() throws Exception {
		  ChannelManagerService channelManagerService =(ChannelManagerService)getServiceById("channelManagerService"); 
		  setItems(channelManagerService.getChannelsBySite(siteId));
		}
	  
	  /**
	   * 保存中间表信息
	 * @throws Exception 
	   */
	  public String save() throws Exception{
		  TemplateGather gather=new TemplateGather();
		  try {
			Ognl.setValue("templateId", gather, templateId);
			Ognl.setValue("channelId", gather, channelId);
			Ognl.setValue("templateType", gather, templateType);
			Ognl.setValue("siteId", gather, siteId);
			Ognl.setValue("includeChannelId", gather, includeChannelId);
			
			Channel _channel=service.getChannel(includeChannelId);
			if(_channel!=null){
				Ognl.setValue("includeChannelName", gather, _channel.getName());
			}
			service.save(gather);
		} catch (OgnlException e) {
			e.printStackTrace();
		}
		return list();
	  }

		/**
		 * 删除
		 * @return
		 */
		public String delete() throws Exception{
			if(!StringUtil.isEmpty(keys)){
				List list=StringUtil.splitToList(keys,",");
				service.delete(list);
			}else
				this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
			return list();
		}
	
}

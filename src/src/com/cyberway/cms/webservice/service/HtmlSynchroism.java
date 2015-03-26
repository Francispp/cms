package com.cyberway.cms.webservice.service;

import com.cyberway.core.utils.ServiceLocator;

/**
 * 同步静态HTML文件
 * @author Dicky
 * @time 2012-10-25 14:07:20
 * @version 1.0
 */
public class HtmlSynchroism {
	
	HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
	
	public void publishStaticHtml(String url, Long channelId, Long templateId, String roleCode){
		htmlSynchroismService.publishStaticHtml(url, channelId, templateId, roleCode);
	}
}

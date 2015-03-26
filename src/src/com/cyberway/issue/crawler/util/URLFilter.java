package com.cyberway.issue.crawler.util;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.issue.net.UURI;

public class URLFilter implements Serializable{
	
	private static final String PERMISSION_DENY_PAGE = "/errors/accessDenied.jsp";
	private static final String SITE_INDEX_URL="/cms/index.action";
	private static final String SITE_LIST_URL="/cms/docInfo!list.action";
	private static final String SITE_DETAIL_URL="/cms/docInfo!view.action";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String ExtracetUrl(String currentURL,UURI uuri) throws Exception
	{
		if(currentURL.indexOf("?id") != -1)
			System.out.println(currentURL+"------------");
		String query = "";
		currentURL = currentURL.replaceAll("'", "");
		if(currentURL.indexOf("?") != -1)
		{
			query = currentURL.substring(currentURL.indexOf("?")+1);
			currentURL = currentURL.substring(0,currentURL.indexOf("?"));
		}
		String lastUrl = currentURL;
		String channelPath=null;
		if (lastUrl.startsWith("/")) {
			lastUrl = lastUrl.substring(1);
		}
		if (lastUrl.endsWith("/")) {
			lastUrl = lastUrl.substring(0, lastUrl.length() - 1);
		}		
		SiteManagerService siteService=(SiteManagerService)ServiceLocator.getBean("siteManagerService");		
		CmsSite site=siteService.getSite(uuri.getReferencedHost(),80);
		if(site==null)
			return currentURL;
		if (lastUrl.equals("")||lastUrl.startsWith("index.htm")||lastUrl.startsWith("index.html")) { // 表示站点浏览
			if (site !=null && site.getIspublished() != null
					&& site.getIspublished().intValue() == 1) {
				return "/";
			} else {
				return currentURL;
			}
		}
		else {//访问站点下其他页面
			ChannelManagerService channelService=(ChannelManagerService)ServiceLocator.getBean("channelManagerService");
			Channel channel=null;
			if (lastUrl.indexOf(".") > 0) {
				int po = lastUrl.lastIndexOf("/");
				if (po > 0) {
					channelPath = lastUrl.substring(0, po);
					lastUrl = lastUrl.substring(po + 1);
				}
				int commaPO = lastUrl.lastIndexOf(".");			
				currentURL = lastUrl;
				String extName = lastUrl.substring(commaPO + 1);
				lastUrl = lastUrl.substring(0, commaPO);
				if(extName.trim().equals(com.cyberway.cms.Constants.SITE_URL_LIST_POSTFIX)||
						extName.trim().equalsIgnoreCase(com.cyberway.cms.Constants.SITE_URL_DETAIL_POSTFIX)){
				
				channel=channelService.getChannelByPath(site.getOid(), channelPath);
				Long templateid=null;
				if(channel == null)
					return currentURL;
				if (extName.trim().equalsIgnoreCase(com.cyberway.cms.Constants.SITE_URL_LIST_POSTFIX)) { //浏览频道，指定的概览模板
					String formName = lastUrl;
					
					if(StringUtil.isEmpty(formName))
						templateid=channelService.getDefualutTemplateId(channel.getId(), Template.TYPE_SUMMARY);
					else {
						TemplateManagerService templateService=(TemplateManagerService)ServiceLocator.getBean("templateManagerService");
						Template template=templateService.getTemplateByName(site.getOid(),formName);
						if(template==null)
							return currentURL;
						templateid=template.getId();
					}
					if ((templateid==null))
						return currentURL;
					
					return getUrlAndParameters(getArgs(query),SITE_LIST_URL+"?templateId="+ templateid + "&channelId="+ channel.getId());
				}else if (extName.trim().equalsIgnoreCase(com.cyberway.cms.Constants.SITE_URL_DETAIL_POSTFIX)) {//细览模板
					String docID = lastUrl.trim();
					Map map = getArgs(uuri.getQuery());
					if(map.containsKey("templateName")){//指定了模板名称
					 TemplateManagerService templateService=(TemplateManagerService)ServiceLocator.getBean("templateManagerService");
					 String formName=map.get("templateName").toString();
					 Template template=templateService.getTemplateByName(site.getOid(),formName);
					 if(template==null)
						 return currentURL;
					templateid=template.getId();						
					}else
						templateid=channelService.getDefualutTemplateId(channel.getId(), Template.TYPE_DETAILS);
					if ((templateid==null))
						return currentURL;	
					 return getUrlAndParameters(getArgs(query),this.SITE_DETAIL_URL+"?id=" + docID + "&templateId="+templateid+"&channelId="+ channel.getId());
				}
				}
				else if (currentURL.indexOf("docInfo!view.action") != -1 && getArgs(query).containsKey("id"))
				{
					return getUrlAndParameters(getArgs(query),currentURL);
				}
						
				else {//浏览频道，缺省的概览模板
					if(lastUrl.endsWith("/list"))
						lastUrl=lastUrl.replaceAll("/list", "");
					channel=channelService.getChannelByPath(site.getOid(), lastUrl);
					if ((channel != null)){
					Long templateid=channelService.getDefualutTemplateId(channel.getId(), Template.TYPE_SUMMARY);
					if (templateid != null)
					{
						return getUrlAndParameters(getArgs(query),this.SITE_LIST_URL+"?templateId=" + templateid + "&channelId=" + channel.getId());
					}
					}
				}
			}
			else {//浏览频道，缺省的概览模板
				if(lastUrl.endsWith("j_acegi_security_check")||lastUrl.endsWith("j_acegi_logout"))
					return currentURL;
				if(lastUrl.endsWith("ajax"))
					return currentURL;
				if(lastUrl.endsWith("/list"))
					lastUrl=lastUrl.replaceAll("/list", "");
				channel=channelService.getChannelByPath(site.getOid(), lastUrl);
				if ((channel != null)){
				Long templateid=channelService.getDefualutTemplateId(channel.getId(), Template.TYPE_SUMMARY);
				if (templateid != null)
				{
					return getUrlAndParameters(getArgs(query),this.SITE_LIST_URL+"?templateId=" + templateid + "&channelId=" + channel.getId());
				}
				}else{//若地址为其他类型
					
				}
			}
		}
		return currentURL;
	}

	public static Map<String, String> getArgs(String query)
	{
		Map<String, String> map = new HashMap();
		if(query != null && StringUtils.isNotEmpty(query) && query.indexOf("&") != -1)
		{
		String[] strArray = query.split("&");
		for(String str : strArray)
		{
			if(query.indexOf("=") != -1)
			{
			String[] s = str.split("=");
			if(s.length == 2)
			{
				map.put(s[0], s[1]);
			}
			}
		}
		}
		else if (query != null && StringUtils.isNotEmpty(query)&& query.indexOf("=") != -1)
		{
			String[] s = query.split("=");
			if(s.length == 2)
			{
				map.put(s[0], s[1]);
			}
		}
		return map;
	}
    private String getUrlAndParameters(Map parms,String url){
    	
    	Iterator it=parms.keySet().iterator();
    	StringBuffer sb=new StringBuffer();
    	sb.append(url);
    	while(it.hasNext()){
    		String parmname=(String)it.next();
    		if(url.indexOf("&"+parmname+"=")<0 && url.indexOf("?"+parmname+"=")<0){
    			if(url.indexOf("?") >0)
    			sb.append("&").append(parmname).append("=").append(parms.get(parmname));
    			else
    			sb.append("?").append(parmname).append("=").append(parms.get(parmname));	
    		}
    	}
    	return UrlHandler(sb.toString());
    }
    private String UrlHandler(String url)
    {
    	String filePath = "";
    	if (url.indexOf("docInfo!list.action") != -1 && url.indexOf("docid=") == -1)
    		filePath = "/summary_template/";
    	else if (url.indexOf("docInfo!view.action") != -1 || url.indexOf("docid=") > 0)
    		filePath = "/details_template/";
    	
    	if(url.indexOf("?") != -1 && url.lastIndexOf(".") != -1)
    	{
    		url = url.substring(0, url.lastIndexOf("."))+ url.substring(url.indexOf("?"));
    		String urlPath = url.substring(0,url.indexOf("?"));
    		if(url.indexOf("/") != -1)
    		url = urlPath.substring(urlPath.lastIndexOf("/"))+url.substring(url.indexOf("?")+1);
    		else
    		url = (urlPath + url.substring(url.indexOf("?")+1)).replace("/", "");
    	}
    	if(url.indexOf("?") != -1)
    	{
    		String urlPath = url.substring(0,url.indexOf("?"));
    		if(url.indexOf("/") != -1)
    		url = urlPath.substring(urlPath.lastIndexOf("/"))+url.substring(url.indexOf("?")+1);
    		else
    		url = (urlPath+url.substring(url.indexOf("?")+1)).replace("/", "");
    	}
    	else if(url.lastIndexOf(".") != -1)
    	{
    		url = url.substring(0,url.lastIndexOf("."));
    		if(url.indexOf("/") != -1)
    		url = url.substring(url.lastIndexOf("/"));
    	}
    	url = filePath+url.replace("/", "")+".html";
    	return url;
    }

}

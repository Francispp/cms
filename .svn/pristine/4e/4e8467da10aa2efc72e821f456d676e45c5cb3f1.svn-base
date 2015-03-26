package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

/**
 * @author caiw
 * HTML控件的过滤器
 *
 */
public class TreeWriter extends ComponentWriter
{
	public TreeWriter()
	{
		super ("Tree", "div");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token) 
	{
		return false;
	}
	@Override
	protected void writeAttribute(AttributeToken attribute)
	{
		/*if (StringUtils.equalsIgnoreCase(attribute.getName(), "_name"))
		{
			if ((ObjectUtils.equals(getTemplate().getType(), Template.TYPE_FORM) || 
				ObjectUtils.equals(getTemplate().getType(), Template.TYPE_DETAILS)) &&
				!ObjectUtils.toString(attribute.getValue()).startsWith("domain."))
			{
				getMarkupWriter().attributes("name", "domain." + attribute.getValue());
			}
			else
			{
				getMarkupWriter().attributes("name", attribute.getValue());
			}
		}
		else
		{
			super.writeAttribute(attribute);
		}*/
		super.writeAttribute(attribute);
	}	
	@Override
	protected void writeComponentStart(StartElementToken startElement) 
	{
		String name=TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		getComponentIdStack().push(id);
		getMarkupWriter().element(getElementName(), "id", getComponentIdStack().peek());
		/*if ((ObjectUtils.equals(getTemplate().getType(), Template.TYPE_FORM) || 
				ObjectUtils.equals(getTemplate().getType(), Template.TYPE_DETAILS)) &&
				!ObjectUtils.toString(name).startsWith("domain."))
			{
				getMarkupWriter().attributes("name", "domain." + name);
			}
			else
			{
				getMarkupWriter().attributes("name", name);
			}*/

		//super.writeComponentStart(startElement);
	}
	
	@Override
	protected void writeComponentEnd() 
	{
		//获得名称
		String name=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_name");
		String channelId=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "channelId");
		String link=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "link");
		String deployStyle=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "deployStyle");
		String isShowNodeImg=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "isShowNodeImg");
		String isCustomCss=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "isCustomCss");
		
		super.writeComponentEnd();

		//String type = TokenUtils.getAttributeValue(startElement, getTemplate(), "type");
		StringBuffer sb=new StringBuffer();
		//sb.append("<%@ include file=\"/common/dhtmlxtree/tree.inc\"%>\n");
		//是否增加默认样式
		if(StringUtil.isEmpty(isCustomCss)||isCustomCss.equals("false"))
			sb.append("<link href=\"${ctx}/common/dhtmlxtree/css/dhtmlXTree.css\" rel=\"stylesheet\" type=\"text/css\">\n");
		//引用树js
		sb.append("<script type=\"text/javascript\" src=\"${ctx}/common/dhtmlxtree/js/dhtmlXCommon.js\"></script>\n");
		sb.append("<script type=\"text/javascript\" src=\"${ctx}/common/dhtmlxtree/js/dhtmlXTree.js\"></script>\n");
		//指定树图片路径
		sb.append("<script type=\"text/javascript\">var dhtmlXTreeImPath=\"${ctx}/common/dhtmlxtree/imgs/\";</script>\n");
		
		
		if(StringUtils.isEmpty(channelId) ){//默认为当前站点为根节点 
			sb.append("<%{  SiteManagerService siteService = (SiteManagerService)ServiceLocator.getBean (\"siteManagerService\");\n");
			sb.append(" com.cyberway.cms.domain.CmsSite site=siteService.getSite(request.getServerName(),request.getServerPort());\n");
			sb.append(" request.setAttribute (\"site\", site);\n");				
			sb.append(" }%>\n");
			
			sb.append("<script type=\"text/javascript\"> \n");
			sb.append("//点击树节点执行的方法\n");
			sb.append("function onclickItem(nodeId) {\n");
			sb.append(" var siteTree=tree${site.oid};\n");
				
			sb.append("if(!isNaN(nodeId)){\n");
			sb.append(" if(siteTree && siteTree.hasChildren(nodeId)>0 ){ \n");
		    sb.append(" if(siteTree.getOpenState(nodeId)==-1) siteTree.openItem(nodeId); else siteTree.closeItem(nodeId);\n");
		     sb.append(" }else{\n");		
			if(!StringUtil.isEmpty(link))
			 sb.append(link+"\n");
			sb.append("}}\n");
			sb.append("}\n");
			sb.append("</script>\n");			

		sb.append("<div id=\"siteTree_${site.oid}\"></div>\n");
		sb.append("<script type=\"text/javascript\"> \n");
		sb.append("<cms:CmsAuth resCode=\"CMS_DOCUMENT_VIEW\" objectId=\"${site.oid}\" objectType=\"1\">\n");   
		sb.append("tree${site.oid}=new dhtmlXTreeObject(\"siteTree_${site.oid}\",\"100%\",\"100%\",'S_${site.oid}');\n");
		sb.append("tree${site.oid}.setImagePath(\"/common/dhtmlxtree/imgs/tree/csh_vista/\");\n");
		sb.append("tree${site.oid}.enableDragAndDrop(0);\n");
		sb.append("tree${site.oid}.loadXML(\"${ctx}/cms/site!xml.action?id=${site.oid}&loginType=1\");\n");
		sb.append("tree${site.oid}.setOnClickHandler(onclickItem);\n");
		//是否显示树节点图标isShowNodeImg
		if(!StringUtil.isEmpty(isShowNodeImg) && isShowNodeImg.equalsIgnoreCase("false"))
		 sb.append("tree${site.oid}.timgen=false;\n");
		sb.append("tree${site.oid}.setOnClickHandler(onclickItem);\n");
		sb.append("tree${site.oid}.setXMLAutoLoading(\"${ctx}/cms/site!channelxml.action?id=${site.oid}&loginType=1\");\n");
		if(!StringUtil.isEmpty(deployStyle)){//增加自动展开代码
			sb.append("setTimeout('openItem()',2000);\n");//setTimeout setInterval
			sb.append("function openItem() {\n");
			 if(deployStyle.equals("1"))
			  sb.append("tree${site.oid}.openItem(\"T_${site.oid}\");\n");
			 else if(deployStyle.equals("2"))
				  sb.append("tree${site.oid}.openAllItems(\"S_${site.oid}\");\n");
			sb.append("}\n");
			}
		sb.append("</cms:CmsAuth>\n");		
		sb.append("</script>\n");
		}else{//指定频道为根节点
			sb.append("<script type=\"text/javascript\"> \n");
			sb.append("<%{  ChannelManagerService channelService = (ChannelManagerService)ServiceLocator.getBean (\"channelManagerService\");\n");
			Object obj = channelId;
			
			//判断是否为固定的频道ID  
			if(obj instanceof Long){
				sb.append(" com.cyberway.cms.domain.Channel channel=channelService.getChannelFromCache(new Long(\""+channelId+"\"));\n");
			}else{
				sb.append(" com.cyberway.cms.domain.Channel channel=channelService.getChannelFromCache(new Long(request.getParameter(\"channelId\")));\n");
			} 
			sb.append(" request.setAttribute (\"channel\", channel);\n");				
			sb.append(" }%>\n");
			
			sb.append("//点击树节点执行的方法\n");
			sb.append("function onclickItem(nodeId) {\n");
			sb.append(" var siteTree=tree${channel.id};\n");	
			sb.append("if(!isNaN(nodeId)){\n");
			sb.append(" if(siteTree && siteTree.hasChildren(nodeId)>0 ){ \n");
		    sb.append(" if(siteTree.getOpenState(nodeId)==-1) siteTree.openItem(nodeId); else siteTree.closeItem(nodeId);\n");
		     sb.append(" }else{\n");		
			if(!StringUtil.isEmpty(link))
			 sb.append(link+"\n");
			sb.append("}}\n");
			sb.append("}\n");
			sb.append("</script>\n");
			
			sb.append("<div id=\"siteTree_${channel.id}\"></div>\n");
			sb.append("<script type=\"text/javascript\"> \n");
			sb.append("<cms:CmsAuth resCode=\"CMS_DOCUMENT_VIEW\" objectId=\"${channel.id}\" objectType=\"2\">\n");   
			sb.append("tree${channel.id}=new dhtmlXTreeObject(\"siteTree_${channel.id}\",\"100%\",\"100%\",'S_${channel.id}');\n");
			sb.append("tree${channel.id}.setImagePath(\"/common/dhtmlxtree/imgs/tree/csh_vista/\");\n");
			sb.append("tree${channel.id}.enableDragAndDrop(0);\n");
			sb.append("tree${channel.id}.loadXML(\"${ctx}/cms/site!xml.action?pid=${channel.id}&loginType=1\");\n");
			sb.append("tree${channel.id}.setOnClickHandler(onclickItem);\n");
			sb.append("tree${channel.id}.setXMLAutoLoading(\"${ctx}/cms/site!channelxml.action?id=${channel.id}&loginType=1\");\n");
			sb.append("</cms:CmsAuth>\n");
			if(!StringUtil.isEmpty(deployStyle)){//增加自动展开代码
			sb.append("setTimeout('openItem()',2000);\n");
			sb.append("function openItem() {\n");
			 if(deployStyle.equals("1"))
			  sb.append("tree${channel.id}.openItem(\""+channelId+"\");\n");
			 else if(deployStyle.equals("2"))
				  sb.append("tree${channel.id}.openAllItems(\""+channelId+"\");\n");
			sb.append("}\n");
			}
			sb.append("</script>\n");			
		}
		
		//getMarkupWriter().element("script", "type", "text/javascript");
		getComponentIdStack().peek();
		getMarkupWriter().writeRaw(sb.toString());
		//getMarkupWriter().end ();
	}
}

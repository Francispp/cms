package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

public class MenuWriter extends ComponentWriter{
	public MenuWriter()
	{
		super("Menu","");
	}
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String onclick = TokenUtils.getAttributeValue(startElement, getTemplate(), "_onclick");
		String optionFileValueList = TokenUtils.getAttributeValue(startElement, getTemplate(), "optionFileValueList");
		String deployStyle=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "deployStyle");
		String siteIdField=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "siteIdField");
		String portalid=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "portalid");
		String script = TokenUtils.getAttributeValue(startElement, getTemplate(), "scriptValue");
		String buttonScriptTypeField = TokenUtils.getAttributeValue(startElement, getTemplate(), "buttonScriptTypeField");
		
		
		
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		if(StringUtils.isBlank(style))
		{
			style ="";
		}
		if(StringUtils.isBlank(onclick))
		{
			onclick ="";
		}
		if(StringUtils.isBlank(deployStyle))
		{
			deployStyle ="";
		}
		if(StringUtils.isBlank(optionFileValueList))
		{
			optionFileValueList ="";
		}
		if(StringUtils.isBlank(siteIdField))
		{
			siteIdField ="0";
		}
		if(StringUtils.isBlank(portalid))
		{
			portalid ="0";
		}
		getComponentIdStack().push(id);
		//自定义
		if(StringUtils.isNotBlank(buttonScriptTypeField) && buttonScriptTypeField.equals("1"))
		{
              script = StringEscapeUtils.unescapeHtml(script);
			  script = StringEscapeUtils.escapeJava(script);
			  getMarkupWriter().writeRaw("<%" + String.format("((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\")", TemplateConstants.ScriptEngineAttribute, script) + ";%>");
		}
		else if(StringUtils.isNotBlank(buttonScriptTypeField) && buttonScriptTypeField.equals("2"))//栏目树
		{
			StringBuffer sb=new StringBuffer();
			if(deployStyle.equals("collapse"))
				sb.append("<ul id=\""+id+"\" class=\"dbMenu onClick\">");
			else
				sb.append("<ul id=\""+id+"\" class=\"dbMenu\">");
			sb.append("</ul>");
			sb.append("<script src='${ctx}/dwr/interface/MenuManagerService.js' type='text/javascript'></script>");
			sb.append("<script src='${ctx}/dwr/engine.js' type='text/javascript'></script>");
			sb.append("<script language='javascript'>");
			//若选择栏目
			if(optionFileValueList.length()>0)
			{
				sb.append("MenuManagerService.getAllChildrenByMenus(\""+optionFileValueList+"\",function(data){jQuery(\"#"+id+"\").html(data);dbMenu.init();});");
			}
			else//取整个portal
			{
			sb.append("MenuManagerService.getAllMenusByPortalid("+Long.parseLong(portalid)+",function(data){jQuery(\"#"+id+"\").html(data);dbMenu.init();});");
			}
			sb.append("</script>");

			getMarkupWriter().writeRaw(sb.toString());
			
		}
		else//站点树
		{
		StringBuffer sb=new StringBuffer();
		if(deployStyle.equals("collapse"))
			sb.append("<ul id=\""+id+"\" class=\"dbMenu onClick\">");
		else
			sb.append("<ul id=\""+id+"\" class=\"dbMenu\">");
		sb.append("</ul>");
		sb.append("<script src='${ctx}/dwr/interface/SiteManagerService.js' type='text/javascript'></script>");
		sb.append("<script src='${ctx}/dwr/engine.js' type='text/javascript'></script>");
		sb.append("<script language='javascript'>");
		//若选择频道
		if(optionFileValueList.length()>0)
		{
			sb.append("SiteManagerService.getChannelByIds(\""+optionFileValueList+"\",function(data){jQuery(\"#"+id+"\").html(data);dbMenu.init();});");
		}
		else//取整个站点
		{
		sb.append("SiteManagerService.getChannelBySite("+Long.parseLong(siteIdField)+",function(data){jQuery(\"#"+id+"\").html(data);dbMenu.init();});");
		}
		sb.append("</script>");

		getMarkupWriter().writeRaw(sb.toString());
		}
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}

}

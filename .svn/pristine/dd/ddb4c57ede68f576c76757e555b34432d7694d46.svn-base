package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

public class ButtonWriter extends ComponentWriter{
	public ButtonWriter()
	{
		super("Button","");
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
		String value = TokenUtils.getAttributeValue(startElement, getTemplate(), "_value");
		String buttonScriptTypeField = TokenUtils.getAttributeValue(startElement, getTemplate(), "buttonScriptTypeField");
		String icon = TokenUtils.getAttributeValue(startElement, getTemplate(), "iconList");
		String onclick = TokenUtils.getAttributeValue(startElement, getTemplate(), "_onclick");
		String title = TokenUtils.getAttributeValue(startElement, getTemplate(), "title");
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
		if(StringUtils.isBlank(value))
		{
			value ="";
		}
		if(StringUtils.isBlank(icon))
		{
			icon ="";
		}
		getComponentIdStack().push(id);

		StringBuffer sb=new StringBuffer();
		onclick = !StringUtil.isEmpty(onclick)? StringEscapeUtils.unescapeHtml(onclick) : "";
		if(buttonScriptTypeField.equals("0"))
		{
			sb.append("<button class =\""+style+"\" onclick=\""+onclick+"\">");
		}
		else
		{
		sb.append(" <img class = \""+style+"\" onclick=\""+onclick+"\" src=\"images/"+icon+"\" >");
		}
		getMarkupWriter().writeRaw(sb.toString());
	}
	
	@Override
	protected void writeComponentEnd()
	{
		String buttonScriptTypeField=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "buttonScriptTypeField");
		StringBuffer sb=new StringBuffer();
		if(buttonScriptTypeField.equals("0"))
		{
		sb.append("</button>");
		}
		else{
			sb.append("</img>");
		}
		getMarkupWriter().writeRaw(sb.toString());
	}

}

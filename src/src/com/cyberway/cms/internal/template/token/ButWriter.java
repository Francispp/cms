package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class ButWriter extends ComponentWriter{
	public ButWriter()
	{
		super("But","div");
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
		String title = TokenUtils.getAttributeValue(startElement, getTemplate(), "title");
		String buttonField = TokenUtils.getAttributeValue(startElement, getTemplate(), "buttonField");
		String icon = TokenUtils.getAttributeValue(startElement, getTemplate(), "iconList");
		if(StringUtils.isNotBlank(icon))
		{
			icon ="<img src=\"images/"+icon+"\" ></img>";
		}
		else
		{
			icon="";
		}
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		
		getComponentIdStack().push(id);
		if(buttonField != null && !StringUtils.isEmpty(buttonField))
		{
	    style += "_"+buttonField;
		StringBuffer sb=new StringBuffer();
		if(buttonField.equals("editItem"))
		{
			sb.append("<div class = \""+style+"\" onclick=\""+buttonField+"('${id}','${channelId}');\">"+icon+"</div>\n");
		}
		else
		{
		sb.append(" <div style='float:left;' class = \""+style+"\" onclick=\""+buttonField+"();\">"+icon+"</div>\n");
		}
		getMarkupWriter().writeRaw(sb.toString());
		}
	}
	
	@Override
	protected void writeComponentEnd()
	{
		
	}

}

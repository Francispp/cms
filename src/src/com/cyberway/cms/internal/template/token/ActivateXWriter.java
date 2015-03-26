package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TemplateMessages;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.TemplateException;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.cms.template.token.TokenType;

public class ActivateXWriter extends AbstractComponentWriter
{
	@Override
	protected boolean isComponent(StartElementToken startElement)
	{
		boolean result = TokenUtils.hasAttribute(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute, "ActivateX");
		
		if (result)
		{
			if (!TokenUtils.hasAttribute(startElement, getTemplate(), "classid"))
			{
				throw new TemplateException (TemplateMessages.missionAttribute("ActivateX", "classid"));
			}
		}
		
		return result;
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{
		if (ObjectUtils.equals(token.getTokenType(), TokenType.StartElement))
		{
			return StringUtils.equalsIgnoreCase(((StartElementToken)token).getName(), "param");
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Text))
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return !ObjectUtils.equals(token.getName(), TemplateConstants.ComponentTypeAttribute);
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		getMarkupWriter().element("object");
	}
}

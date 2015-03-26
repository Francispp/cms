package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

public class TextFieldWriter extends FormFieldWriter
{
	public TextFieldWriter()
	{
		super ("TextField", "input");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		boolean result = super.isAllowAttribute(token);
		
		if (result)
		{
			result = !ObjectUtils.equals(token.getName(), "type");
			if(!result)
				return false;
			result = !ObjectUtils.equals(token.getName(), "typeField");
			if(!result)
				return false;
			result = !ObjectUtils.equals(token.getName(), "typeRadio");
			if(!result)
				return false;
			result = !ObjectUtils.equals(token.getName(), "_value");
			if(!result)
				return false;
			result = !ObjectUtils.equals(token.getName(), "dpieagent_iecontroltype");
			if(!result)
				return false;
		}
		
		return result;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		String type = TokenUtils.getAttributeValue(startElement, getTemplate(), "typeField");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String value = TokenUtils.getAttributeValue(startElement, getTemplate(), "_value");
		//String theme = TokenUtils.getAttributeValue(startElement, getTemplate(), "theme");
		if(StringUtil.isEmpty(value))
			value = "";
		if(StringUtil.isEmpty(type))
			type = "";
		//增加样式控制
		if(!StringUtil.isEmpty(style)){
			  getMarkupWriter().writeRaw("<div class=\""+style+"\">");
			}
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		
		getComponentIdStack().push(id);
		
		
		if (ObjectUtils.equals(type, "password"))
		{
			getMarkupWriter().element("ww:password", "id", getComponentIdStack().peek());
		}
		else if (ObjectUtils.equals(type, "hidden"))
		{
			getMarkupWriter().element("ww:hidden", "id", getComponentIdStack().peek());
		}
		else
		{
			getMarkupWriter().element("ww:textfield", "id", getComponentIdStack().peek());
		}
	}
}

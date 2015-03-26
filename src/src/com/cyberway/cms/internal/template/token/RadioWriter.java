package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;

import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

public class RadioWriter extends FormFieldWriter
{
	public RadioWriter() 
	{
		super ("Radio", "input");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		boolean result = super.isAllowAttribute(token);
		
		if (!result)
		{
			result = ObjectUtils.equals(token.getName(), "value");
		}
		
		return result;
	}
	
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		//String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		//String type = TokenUtils.getAttributeValue(startElement, getTemplate(), "type");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
	
		//增加样式控制
		if(!StringUtil.isEmpty(style)){
			  getMarkupWriter().writeRaw("<div class=\""+style+"\">");
			}
		super.writeComponentStart(startElement);
		}
	
	@Override
	protected void writeComponentEnd()
	{
		String id = getComponentIdStack().peek();
		String name = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_name");
		super.writeComponentEnd();
		if(!name.startsWith("domain.")){
			name = "domain."+name;
		}
		getMarkupWriter().element("script", "type", "text/javascript");
		getMarkupWriter().writeRaw(String.format("$(\"%s\").checked = $(\"%s\").value == ", id, id) + "\"${" + name + "}\";"); 
		getMarkupWriter().end();		
	}
}

package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;

public class WdatePickerWriter extends FormFieldWriter
{

	public WdatePickerWriter() {
		super("WdatePicker", "input");
	}

	@Override
	protected boolean isAllowBody(TemplateToken token) 
	{
		return true;
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		boolean result = super.isAllowAttribute(token);
		
		if (result)
		{
			result = !ObjectUtils.equals(token.getName(), "t_type")||
				!ObjectUtils.equals(token.getName(), "format")||
				!ObjectUtils.equals(token.getName(), "_value")||
				!ObjectUtils.equals(token.getName(), "_name");
		}
		
		return result;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement) {
		super.writeComponentStart(startElement);
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		String format = TokenUtils.getAttributeValue(startElement, getTemplate(), "format");
		if (StringUtils.isBlank(format))
		{
			format = "yyyy-MM-dd";
		}
		String onclick = TokenUtils.getAttributeValue(startElement, getTemplate(), "onclick");
		if (StringUtils.isBlank(id))
		{
			id = getComponentIdStack().peek();
		}
		if (StringUtils.isBlank(onclick))
		{
		getMarkupWriter().attributes("onclick","WdatePicker({el:$dp.$('"+id+"'),dateFmt:'"+format+"'})");
		}
		if(!name.startsWith("domain.")){
			name = "domain."+name;
		}
		getMarkupWriter().attributes("value","<fmt:formatDate value=\"${"+name+"}\" pattern=\""+format+"\"/>");
	}
}

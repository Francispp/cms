package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;


public class TextAreaWriter extends FormFieldWriter
{
	public TextAreaWriter()
	{
		super ("TextArea", "");
		//super ("TextArea", "ww:textarea");
	}
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		//String type = TokenUtils.getAttributeValue(startElement, getTemplate(), "type");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String cssStyle = TokenUtils.getAttributeValue(startElement, getTemplate(), "cssStyle");
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		
		getComponentIdStack().push(id);
		
	    if(cssStyle == null || StringUtils.isEmpty(cssStyle))
	    	cssStyle = "";
		//增加样式控制
		if(!StringUtil.isEmpty(style)){
			  getMarkupWriter().writeRaw("<div class=\""+style+"\">");
			}
		getMarkupWriter().element("ww:textarea", "id", getComponentIdStack().peek(),"cssStyle",cssStyle);
		//super.writeComponentStart(startElement);
		}		
}

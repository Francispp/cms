package com.cyberway.cms.internal.template.token;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.core.utils.StringUtil;

public class DatePickerWriter extends FormFieldWriter
{
	private Map<String, Document> _specificationMap;
	private Element _specification;
	
	public void setSpecificationMap(Map<String, Document> specificationMap)
	{
		_specificationMap = specificationMap;
		
		Document document = _specificationMap.get("ww");
		for (Element element : (Collection<Element>)document.getRootElement().elements("tag"))
		{
			if (ObjectUtils.equals(element.elementTextTrim("name"), "datepicker"))
			{
				_specification = element;
				break;
			}
		}
	}

	public DatePickerWriter()
	{
		super ("DatePicker", "ww:datepicker");
	}
	
	@Override
	protected void writeAttribute(AttributeToken attribute)
	{
		if (StringUtils.equalsIgnoreCase(attribute.getName(), "format")) 
			getMarkupWriter().attributes("format", attribute.getValue());  
		
		super.writeAttribute(attribute);
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
	
		//增加样式控制
		if(!StringUtil.isEmpty(style)){
			  getMarkupWriter().writeRaw("<div class=\""+style+"\">");
			}
		super.writeComponentStart(startElement);
		}
		
	@Override
	protected boolean isAllowAttribute(final AttributeToken token)
	{
		return super.isAllowAttribute(token) && !StringUtils.equalsIgnoreCase(token.getName(), "type");
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{
		return false;
	}
}

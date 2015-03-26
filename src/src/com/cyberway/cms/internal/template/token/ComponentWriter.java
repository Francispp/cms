package com.cyberway.cms.internal.template.token;

import java.util.Stack;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class ComponentWriter extends AbstractComponentWriter
{
	private Stack<String>_componentIdStack = new Stack<String> ();
	private String _componentType;
	private String _elementName;
	
	public ComponentWriter(String componentType, String elementName)
	{
		_componentType = componentType;
		_elementName = elementName;
	}
	
	protected Stack<String> getComponentIdStack ()
	{
		return _componentIdStack;
	}
	
	protected String getComponentType()
	{
		return _componentType;
	}
	
	protected String getElementName()
	{
		return _elementName;
	}
	
	@Override
	protected boolean isComponent(StartElementToken startElement)
	{
		return TokenUtils.hasAttribute(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute, getComponentType());
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return !ObjectUtils.equals(token.getName(), TemplateConstants.ComponentTypeAttribute);
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		
		getComponentIdStack().push(id);
		getMarkupWriter().element(getElementName(), "id", getComponentIdStack().peek());
	}
	
	@Override
	protected void writeAttribute(AttributeToken attribute)
	{
		if (ObjectUtils.equals(attribute.getName(), "id"))
		{
			attribute.setValue(getComponentIdStack().peek());
		}
		
		super.writeAttribute(attribute);
	}
}

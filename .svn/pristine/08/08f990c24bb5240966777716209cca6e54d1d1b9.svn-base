package com.cyberway.cms.internal.template.token;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TemplateMessages;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;

public class TagWriter extends AbstractComponentWriter
{
	private Map<String, Document> _specificationMap;
	private Map<StartElementToken, Element> _cached = new HashMap<StartElementToken, Element> ();

	public Map<String, Document> getSpecificationMap()
	{
		return _specificationMap;
	}
	
	public void setSpecificationMap(Map<String, Document> specificationMap)
	{
		_specificationMap = specificationMap;
	}
	
	@Override
	protected boolean isComponent(final StartElementToken startElement)
	{ 
		final AttributeToken attribute = TokenUtils.getAttribute(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute);

		if (attribute != null && StringUtils.countMatches(attribute.getValue(), ":") == 1)
		{
			String namespace = attribute.getValue().substring(0, attribute.getValue().indexOf(":"));
			final String tagName = attribute.getValue().substring(attribute.getValue().indexOf(":") + 1);
			
			Element specification = getComponentSpecification(startElement, namespace, tagName);
			if (specification != null)
			{
				CollectionUtils.forAllDo(specification.elements("attribute"), new Closure ()
				{
					public void execute(Object obj)
					{
						final Element specification = (Element)obj;
						boolean required = false;
						
						try
						{
							required = StringUtils.isBlank(specification.elementTextTrim("rtexprvalue")) ? false : Boolean.valueOf(specification.elementTextTrim("rtexprvalue"));
						}
						catch (Exception ex)
						{
						}
						
						if (required)
						{
							boolean exists = CollectionUtils.exists(TokenUtils.collectAttributeTokens(startElement, getTemplate()), new Predicate ()
							{
								public boolean evaluate(Object obj)
								{
									return ObjectUtils.equals(((AttributeToken)obj).getName(), specification.elementTextTrim("name"));
								}
							});
							
							if (!exists)
							{
								TemplateMessages.missionAttribute(attribute.getValue(), specification.elementTextTrim("name"));
							}
						}
					}
				});
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{	
		return StringUtils.equalsIgnoreCase(getComponentSpecificationFromCache(getComponentStack().peek()).elementText("body-content"), "empty") ? false : true;
	}
	
	@Override
	protected boolean isAllowAttribute(final AttributeToken token)
	{
		Element specification = getComponentSpecificationFromCache(getComponentStack().peek());
		
		return CollectionUtils.exists(specification.elements("attribute"), new Predicate ()
		{
			public boolean evaluate(Object obj)
			{
				return ObjectUtils.equals(token.getName(), ((Element)obj).elementTextTrim("name"));
			}
		});
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		AttributeToken attribute = TokenUtils.getAttribute(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute);
		
		getMarkupWriter().element(attribute.getValue());
	}
	
	protected Element getComponentSpecificationFromCache (StartElementToken startElement)
	{
		return _cached.get(startElement);
	}
	
	protected Element getComponentSpecification (StartElementToken startElement, String namespace, final String tagName)
	{
		if (!_cached.containsKey(startElement))
		{
			Element result = null;
			Document specification = (Document)MapUtils.getObject(getSpecificationMap(), namespace);
			if (specification != null)
			{
				result = (Element)CollectionUtils.find(specification.getRootElement().elements("tag"), new Predicate ()
				{
					public boolean evaluate(Object obj)
					{
						return ObjectUtils.equals(((Element)obj).elementTextTrim("name"), tagName);
					}
				});
				
				if (result != null)
				{
					_cached.put(startElement, result);
				}
			}
		}
		
		return _cached.get(startElement);
	}
}


package com.cyberway.cms.internal.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;

import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.EndElementToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.cms.template.token.TokenType;

public class TokenUtils
{
	public static EndElementToken getEndElement (final StartElementToken startElement, final Template template)
	{
		EndElementToken retval = null;
		for (int index = template.getTokens().indexOf(startElement); index < template.getTokens().size(); index++)
		{
			if (ObjectUtils.equals(template.getTokens().get(index).getTokenType(), TokenType.EndElement))
			{
				EndElementToken endElement = (EndElementToken)template.getTokens().get(index);
				
				if (ObjectUtils.equals(startElement.getName(), endElement.getName()))
				{
					retval = endElement;
					break;
				}
			}
		}
		
		return retval;
	}
	
	public static StartElementToken getStartElement (final EndElementToken endElement, final Template template)
	{
		StartElementToken startElement = null;
		Stack<TemplateToken> stack = new Stack<TemplateToken> ();
		
		for (TemplateToken token : template.getTokens())
		{
			if (token instanceof StartElementToken && 
					ObjectUtils.equals(((StartElementToken)token).getName(), endElement.getName()))
			{
				stack.push(token);
			}
			else if (token instanceof EndElementToken &&
					ObjectUtils.equals(((EndElementToken)token).getName(), endElement.getName()))
			{
				if (token == endElement)
				{
					startElement = (StartElementToken)stack.pop();
				}
				else
				{
					stack.pop();
				}
			}
		}
					
		return startElement;
	}
	
	public static StartElementToken getStartElement (final AttributeToken attribute, final Template template)
	{
		StartElementToken startElement = null;
		
		for (int index = template.getTokens().indexOf(attribute); index >= 0; index--)
		{
			if (ObjectUtils.equals(template.getTokens().get(index).getTokenType(), TokenType.StartElement))
			{
				startElement = (StartElementToken)template.getTokens().get(index);
				break;
			}
		}
		
		return startElement;
	}
	
	public static StartElementToken getStartElement (StartElementToken begin, EndElementToken end, final String elementName, final Template template)
	{
		StartElementToken startElement = null;
		for (int index = template.getTokens().indexOf(begin); index < template.getTokens().indexOf(end); index++)
		{
			if (ObjectUtils.equals(template.getTokens().get(index).getTokenType(), TokenType.StartElement) &&
				ObjectUtils.equals(((StartElementToken)template.getTokens().get(index)).getName(), elementName))
			{
				startElement = (StartElementToken)template.getTokens().get(index);
				break;
			}
		}
		
		return startElement;
	}
	
	public static String getAttributeValue (StartElementToken startElement, Template template, String attributeName)
	{
		AttributeToken attribute = getAttribute(startElement, template, attributeName);
		
		return attribute == null ? null : attribute.getValue();
	}
	
	public static AttributeToken getAttribute (StartElementToken startElement, Template template, final String attributeName)
	{
		return (AttributeToken)CollectionUtils.find(collectAttributeTokens(startElement, template), new Predicate ()
		{
			public boolean evaluate(Object obj)
			{
				return ObjectUtils.equals(((AttributeToken)obj).getName(), attributeName); 
			}
		});
	}
	
	public static boolean hasAttribute (StartElementToken startElement, Template template, final String attrubuateName)
	{
		return getAttribute(startElement, template, attrubuateName) != null;
	}
	
	public static boolean hasAttribute (StartElementToken startElement, Template template, final String attrubuateName, final String attributeValue)
	{
		AttributeToken attribute = getAttribute(startElement, template,  attrubuateName);
		
		return attribute != null && ObjectUtils.equals(attribute.getValue(), attributeValue);
	}
	
	public static Collection<AttributeToken> collectAttributeTokens (StartElementToken startElement, Template template)
	{
		List<AttributeToken> result = new ArrayList<AttributeToken> ();
		for (int index = template.getTokens().indexOf(startElement) + 1; index < template.getTokens().size(); index++)
		{
			TemplateToken token = template.getTokens().get(index);
			
			if (ObjectUtils.equals(token.getTokenType(), TokenType.Attribute))
			{
				result.add((AttributeToken)token);
			}
			else
			{
				break;
			}
		}
		
		return result;
	}
}

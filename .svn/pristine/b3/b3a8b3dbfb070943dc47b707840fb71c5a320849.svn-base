package com.cyberway.cms.internal.template.token;

import java.util.Stack;

import org.apache.commons.lang.ObjectUtils;

import com.cyberway.cms.internal.template.IdAllocator;
import com.cyberway.cms.internal.template.PrivilegeWriter;
import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.internal.template.TokenWriterFilter;
import com.cyberway.cms.internal.template.TokenWriterFilterChain;
import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.CommentToken;
import com.cyberway.cms.template.token.EndElementToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.cms.template.token.TokenType;
import com.cyberway.common.service.MarkupWriter;

public abstract class AbstractComponentWriter implements TokenWriterFilter
{
	private Stack<StartElementToken> _stack;
	private Template _template;
	private MarkupWriter _markupWriter;
	private IdAllocator _idAllocator = new IdAllocator ();
	
	protected Stack<StartElementToken> getComponentStack()
	{
		return _stack;
	}
	
	protected Template getTemplate()
	{
		return _template;
	}
	
	protected MarkupWriter getMarkupWriter()
	{
		return _markupWriter;
	}
	
	protected IdAllocator getIdAllocator()
	{
		return _idAllocator;
	}
	
	protected abstract boolean isComponent (StartElementToken startElement);
	
	protected boolean isAllowBody (TemplateToken token)
	{
		return true;
	}
	
	protected boolean isAllowAttribute (AttributeToken token)
	{
		return true;
	}
	
	final public void prepare(Template template, MarkupWriter writer, TokenWriterFilterChain chain)
	{
		_stack = new Stack<StartElementToken> ();
		_template = template;
		_markupWriter = writer;
		
		chain.doPrepare(template, writer);
	}
	
	final public void write(TemplateToken token, TokenWriterFilterChain chain)
	{	
		if (ObjectUtils.equals(token.getTokenType(), TokenType.StartElement))
		{
			StartElementToken startElement = (StartElementToken)token;
			
			if (isComponent(startElement))
			{
				getComponentStack().push(startElement);
				writePopedomStart(startElement);
				writeComponentStart(startElement);
			}
			else if (getComponentStack().isEmpty() || isAllowBody(token))
			{
				chain.doWrite(token);
			}
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Attribute))
		{
			AttributeToken attribute = (AttributeToken)token;
			StartElementToken startElement = (StartElementToken)TokenUtils.getStartElement(attribute, getTemplate());
			
			if (isComponent(startElement))
			{
				if (isAllowAttribute(attribute))
				{
					writeAttribute(attribute);
				}
			}
			else
			{
				chain.doWrite(token);
			}
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.EndElement))
		{
			EndElementToken endElement = (EndElementToken)token;
			StartElementToken startElement = TokenUtils.getStartElement(endElement, getTemplate());
			
			if (isComponent(startElement))
			{
				writeComponentEnd();
				writePopedomEnd (getComponentStack().peek());
					
				getComponentStack().pop();
			}
			else if (getComponentStack().isEmpty() || isAllowBody(token))
			{
				chain.doWrite(token);
			}
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Comment))
		{
			//CommentToken endElement = (CommentToken)token;
			
		}
		else
		{
			if (getComponentStack().isEmpty() || isAllowBody(token))
			{
				chain.doWrite(token);
			}
		}
	}
	
	final public void write(TemplateToken token, TokenWriterFilterChain chain,Object object1,Object object2)
	{	
		if (ObjectUtils.equals(token.getTokenType(), TokenType.StartElement))
		{
			StartElementToken startElement = (StartElementToken)token;
			
			if (isComponent(startElement))
			{
				getComponentStack().push(startElement);
				writeComponentStart(startElement,object1,object2);
			}
			else if (getComponentStack().isEmpty() || isAllowBody(token))
			{
				chain.doWrite(token,object1,object2);
			}
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Attribute))
		{
			AttributeToken attribute = (AttributeToken)token;
			StartElementToken startElement = (StartElementToken)TokenUtils.getStartElement(attribute, getTemplate());
			
			if (isComponent(startElement))
			{
				if (isAllowAttribute(attribute))
				{
					writeAttribute(attribute);
				}
			}
			else
			{
				chain.doWrite(token,object1,object2);
			}
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.EndElement))
		{
			EndElementToken endElement = (EndElementToken)token;
			StartElementToken startElement = TokenUtils.getStartElement(endElement, getTemplate());
			
			if (isComponent(startElement))
			{
				writeComponentEnd(object1,object2);
				getComponentStack().pop();
			}
			else if (getComponentStack().isEmpty() || isAllowBody(token))
			{
				chain.doWrite(token,object1,object2);
			}
		}
		else
		{
			if (getComponentStack().isEmpty() || isAllowBody(token))
			{
				chain.doWrite(token,object1,object2);
			}
		}
	}
	
	final public void end(TokenWriterFilterChain chain)
	{
		_stack = null;
		_template = null;
		_markupWriter = null;
		
		chain.doEnd();
	}
	protected void writePopedomStart (StartElementToken startElement)
	{
		PrivilegeWriter.writePrivilegeScript(TemplateConstants.TagStart,startElement,getTemplate(),getMarkupWriter());
	}
	protected void writePopedomEnd (StartElementToken startElement)
	{
		PrivilegeWriter.writePrivilegeScript(TemplateConstants.TagEnd,startElement,getTemplate(),getMarkupWriter());
	}
	
	protected void writeComponentStart (StartElementToken startElement)
	{
		getMarkupWriter().element(startElement.getName());
	}
	protected void writeComponentStart (StartElementToken startElement,Object object1,Object object2)
	{
		getMarkupWriter().element(startElement.getName());
	}
	
	protected void writeAttribute (AttributeToken attribute)
	{
		getMarkupWriter().attributes(attribute.getName(), attribute.getValue());
	}
	
	protected void writeComponentEnd ()
	{
		getMarkupWriter().end();
	}
	protected void writeComponentEnd (Object object1,Object object2)
	{
		getMarkupWriter().end();
	}
	
}

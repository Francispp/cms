package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;

import com.cyberway.cms.internal.template.TokenWriterFilter;
import com.cyberway.cms.internal.template.TokenWriterFilterChain;
import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.CodeToken;
import com.cyberway.cms.template.token.CommentToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.cms.template.token.TextToken;
import com.cyberway.cms.template.token.TokenType;
import com.cyberway.common.service.MarkupWriter;

public class JSPWriter implements TokenWriterFilter
{
	private Template _template;
	private MarkupWriter _markupWriter;
	
	public void prepare(Template template, MarkupWriter writer, TokenWriterFilterChain chain)
	{
		_template = template;
		_markupWriter = writer;
		
		chain.doPrepare(template, writer);
	}
	
	public void write(TemplateToken token, TokenWriterFilterChain chain)
	{
		if (ObjectUtils.equals(token.getTokenType(), TokenType.StartElement))
		{
			_markupWriter.element(((StartElementToken)token).getName());
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Attribute))
		{
			_markupWriter.attributes(((AttributeToken)token).getName(), ((AttributeToken)token).getValue());
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.EndElement))
		{	
			_markupWriter.end();
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Text))
		{
			_markupWriter.writeRaw(((TextToken)token).getText());
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Comment))
		{
			_markupWriter.comment(((CommentToken)token).getComment());
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Code))
		{
			_markupWriter.writeRaw("<%" + ((CodeToken)token).getCode() + "%>");
		}
		else
		{
			chain.doWrite(token);
		}
	}
	public void write(TemplateToken token, TokenWriterFilterChain chain,Object object1,Object object2)
	{
		if (ObjectUtils.equals(token.getTokenType(), TokenType.StartElement))
		{
			_markupWriter.element(((StartElementToken)token).getName());
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Attribute))
		{
			_markupWriter.attributes(((AttributeToken)token).getName(), ((AttributeToken)token).getValue());
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.EndElement))
		{	
			_markupWriter.end();
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Text))
		{
			_markupWriter.writeRaw(((TextToken)token).getText());
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Comment))
		{
			_markupWriter.comment(((CommentToken)token).getComment());
		}
		else if (ObjectUtils.equals(token.getTokenType(), TokenType.Code))
		{
			_markupWriter.writeRaw("<%" + ((CodeToken)token).getCode() + "%>");
		}
		else
		{
			chain.doWrite(token);
		}
	}
	
	public void end(TokenWriterFilterChain chain)
	{
		_template = null;
		_markupWriter = null;
		
		chain.doEnd();
	}
}

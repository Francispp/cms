package com.cyberway.cms.internal.template;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Attribute;
import org.htmlparser.Parser;
import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.TemplateException;
import com.cyberway.cms.template.TemplateParser;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.CodeToken;
import com.cyberway.cms.template.token.CommentToken;
import com.cyberway.cms.template.token.EndElementToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.cms.template.token.TextToken;
import com.cyberway.cms.template.token.TokenType;

public class DefaultTemplateParser implements TemplateParser
{
	private Integer _templateType = com.cyberway.cms.domain.Template.TYPE_ANY;
	
	private Boolean _isWap = false;
	
	public void setTemplateType(Integer templateType)
	{
		_templateType = templateType;
		

		if (com.cyberway.cms.domain.Template.TYPE_ANY_WAP == _templateType
				|| com.cyberway.cms.domain.Template.TYPE_INDEX_WAP == _templateType
				|| com.cyberway.cms.domain.Template.TYPE_DETAILS_WAP == _templateType
				|| com.cyberway.cms.domain.Template.TYPE_SUMMARY_WAP == _templateType) {
			_isWap = true;
		}
	}
	
	public Template parseTemplate(String templateBody)
	{
		templateBody = commentScript (templateBody);
		
		final List<TemplateToken> tokens = new ArrayList<TemplateToken> ();
		
		try
		{
			Parser parser = new Parser (new Lexer (templateBody));
			parser.visitAllNodesWith(new NodeVisitor ()
			{
				@Override
				public void visitTag(Tag tag)
				{
					if (tag.getRawTagName().startsWith("%"))
					{
						if (!tag.getText().endsWith("%"))
							throw new TemplateException (TemplateMessages.missionCodeEnd());
						
						tokens.add(new CodeToken (tag.getText().substring(1, tag.getText().length() - 1)));
					}
					else
					{
						tokens.add(new StartElementToken (tag.getRawTagName()));
						
						for (int index = 0; index < tag.getAttributesEx().size(); index++)
						{
							Attribute attr = (Attribute)tag.getAttributesEx().get(index);
							if (index > 0 && attr != null && 
								attr.getName() != null && 
								Pattern.matches("_?[a-zA-Z]\\w*", attr.getName()))
							{
								if (StringUtils.equalsIgnoreCase(tag.getRawTagName(), "body"))
								{
									if (!StringUtils.equalsIgnoreCase(attr.getName(), "contentEditable"))
									{
										tokens.add(new AttributeToken (attr.getName(), attr.getValue()));
									}
								}
								else
								{
									tokens.add(new AttributeToken (attr.getName(), attr.getValue()));
								}
							}
						}
						
						if (tag.getAttributeEx("/") != null)
						{	
							tokens.add(new EndElementToken (tag.getRawTagName()));
						}
					}
				}
				
				@Override
				public void visitEndTag(Tag tag)
				{
					tokens.add(new EndElementToken (tag.getRawTagName().substring(1)));
				}
				
				@Override
				public void visitStringNode(Text text)
				{
					tokens.add(new TextToken (text.getText()));
				}
				
				@Override
				public void visitRemarkNode(Remark remark)
				{
					tokens.add(new CommentToken (remark.getText()));
				}
			});
		}
		catch (ParserException ex)
		{
			throw new RuntimeException (ex);
		}
		
		final Template template = new Template (templateBody, tokens, _templateType,_isWap);
		
		CollectionUtils.forAllDo(template.getTokens(), new Closure ()
		{
			public void execute(Object obj)
			{
				if (ObjectUtils.equals(((TemplateToken)obj).getTokenType(), TokenType.Attribute))
				{
					AttributeToken attribute = (AttributeToken)obj;
					
					if (StringUtils.equalsIgnoreCase(attribute.getName(), "contentEditable") && !StringUtils.isBlank(attribute.getValue()))
					{
						StartElementToken startElement = (StartElementToken)TokenUtils.getStartElement((AttributeToken)obj, template);
					
						if (StringUtils.equalsIgnoreCase(startElement.getName(), "body"))
						{
							template.getTokens().remove(attribute);
						}
					}
				}
			}
		});
	
		return template;
	}
	
	protected String commentScript (String templateBody)
	{
		templateBody = Pattern.compile("(<script[\\s\\S]*?>)\\s*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(templateBody).replaceAll("$1<!--\n ");
		templateBody = Pattern.compile("\\s*(</script>)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(templateBody).replaceAll("\n -->$1");
		
		return templateBody;
	}
}

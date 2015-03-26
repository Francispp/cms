package com.cyberway.cms.internal.template.token;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.PageWriter;
import com.cyberway.cms.template.TemplateException;
import com.cyberway.cms.template.TemplateParser;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;

public class IncludeWriter extends AbstractComponentWriter implements ApplicationContextAware
{
	private static final Pattern REGEX_BODY = Pattern.compile("<body[^>/]*>(.*?)</body>", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	
	private ApplicationContext _applicationContext;
	private TemplateManagerService _templateManagerService;
	private Map<Integer, TemplateParser> _templateParserMap;
	private String _pageWriterBeanName;
	
	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException
	{
		_applicationContext = applicationcontext;
	}
	
	public String getPageWriterBeanName()
	{
		return _pageWriterBeanName;
	}

	public void setPageWriterBeanName(String pageWriterBeanName)
	{
		_pageWriterBeanName = pageWriterBeanName;
	}

	public TemplateManagerService getTemplateManagerService()
	{
		return _templateManagerService;
	}

	public void setTemplateManagerService(TemplateManagerService templateManagerService)
	{
		_templateManagerService = templateManagerService;
	}

	public Map<Integer, TemplateParser> getTemplateParserMap()
	{
		return _templateParserMap;
	}

	public void setTemplateParserMap(Map<Integer, TemplateParser> templateParserMap)
	{
		_templateParserMap = templateParserMap;
	}

	protected PageWriter getPageWriter()
	{
		return (PageWriter)_applicationContext.getBean(getPageWriterBeanName());
	}

	@Override
	protected boolean isComponent(StartElementToken startElement)
	{
		boolean result = TokenUtils.hasAttribute(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute, "Include");
		
		if (result)
		{
			result = TokenUtils.hasAttribute(startElement, getTemplate(), "template") || TokenUtils.hasAttribute(startElement, getTemplate(), "file");
		}
		
		return result;
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{
		return false;
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		if (TokenUtils.hasAttribute(startElement, getTemplate(), "file"))
		{
			getMarkupWriter().writeRaw("<%@ include file=\"" + TokenUtils.getAttributeValue(startElement, getTemplate(), "file") + "\" %>"); 
		}
		else
		{
			ByteArrayOutputStream outputStream = null;
			try
			{
				com.cyberway.cms.domain.Template template = getTemplateManagerService().get(Long.valueOf(TokenUtils.getAttributeValue(startElement, getTemplate(), "template"))); 
				String templateBody = template.getBody();
				Matcher matcher = REGEX_BODY.matcher(templateBody);
				if (matcher.find())
				{
					templateBody = matcher.group(1);
				}
				 
				outputStream = new ByteArrayOutputStream ();
				
				getPageWriter().write(getTemplateParserMap().get(template.getType()).parseTemplate(templateBody), outputStream);
				outputStream.flush();
				
				getMarkupWriter().writeRaw(outputStream.toString());
			}
			catch (Exception ex)
			{
				throw new TemplateException (ex);
			}
			finally
			{
				IOUtils.closeQuietly(outputStream);
			}
		}
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}
}

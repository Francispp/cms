package com.cyberway.cms.internal.template.convert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.TemplateConverter;
import com.cyberway.cms.template.TemplateParser;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.cms.template.token.TokenType;

public class DetailsToSummary implements TemplateConverter
{
	private String _prefix;
	private String _suffix;
	private TemplateParser _templateParser;

	public String getPrefix()
	{
		return _prefix;
	}

	public void setPrefix(String prefix)
	{
		_prefix = prefix;
	}

	public String getSuffix()
	{
		return _suffix;
	}

	public void setSuffix(String suffix)
	{
		_suffix = suffix;
	}

	public TemplateParser getTemplateParser()
	{
		return _templateParser;
	}

	public void setTemplateParser(TemplateParser templateParser)
	{
		_templateParser = templateParser;
	}

	public Template convert(Template template)
	{
		final Map<String, String> displayFields = new LinkedHashMap<String, String> ();
		
		for (TemplateToken token : template.getTokens())
		{
			if (ObjectUtils.equals(token.getTokenType(), TokenType.StartElement))
			{
				StartElementToken startElement = (StartElementToken)token;
				String name = TokenUtils.getAttributeValue(startElement, template, "_name");
				
				if (StringUtils.equalsIgnoreCase(startElement.getName(), "input") || 
					StringUtils.equalsIgnoreCase(startElement.getName(), "select") &&
					!StringUtils.isBlank(name) &&
					!ObjectUtils.equals(TokenUtils.getAttributeValue(startElement, template, "hiddenInList"), "true"))
				{
					displayFields.put(name, name);
				}
			}
		}
		
		StringWriter stringWriter = new StringWriter ();
		PrintWriter writer = new PrintWriter (stringWriter);
		for (Entry<String, String> pair : displayFields.entrySet())
		{
			//默认自动生成列
			writer.write(String.format("<td t_type=\"ec:column\" property=\"%s\" onclick=\"editItem('${item.id}','',1)\" title=\"%s\" filterable=\"true\" cssStyle=\"cursor:pointer;\" />", pair.getValue(), pair.getKey()));
		}
		writer.flush();
		writer.close();
		
		String templateBody = StringUtils.join(new Object[] { getPrefix(), stringWriter.toString(), getSuffix() });
		
		return getTemplateParser().parseTemplate(templateBody);
	}
}

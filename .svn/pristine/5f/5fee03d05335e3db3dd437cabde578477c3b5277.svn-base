package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

public class BeanShellScriptWriter extends AbstractComponentWriter
{
	@Override
	protected boolean isComponent(StartElementToken startElement)
	{
		return TokenUtils.hasAttribute(startElement, getTemplate(), "t_type", "BeanShellScript");
	}	
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String script = TokenUtils.getAttributeValue(startElement, getTemplate(), "value");
		String type = TokenUtils.getAttributeValue(startElement, getTemplate(), "languageType");
		
		if (!StringUtils.isBlank(script))
		{
			script = StringEscapeUtils.unescapeHtml(script);
			
			if(StringUtil.isEmpty(type)||type.equalsIgnoreCase("javascript")){//动态javascript脚本
			  script = StringEscapeUtils.escapeJava(script);
			  getMarkupWriter().writeRaw("<%" + String.format("((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\")", TemplateConstants.ScriptEngineAttribute, script) + ";%>");
			}else if(type.equalsIgnoreCase("jsp"))//JSP脚本 需自加<% %> 可支持JSP标签
				getMarkupWriter().writeRaw(script);

		}
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}
}

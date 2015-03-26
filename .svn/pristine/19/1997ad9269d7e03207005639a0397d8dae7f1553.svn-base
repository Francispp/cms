package com.cyberway.cms.internal.template.token;

import java.util.ArrayList;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.domain.Template;
import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.utils.BodyBuilder;
import com.cyberway.core.utils.StringUtil;

public class FormFieldWriter extends ComponentWriter
{
	public FormFieldWriter(String componentType, String elementName)
	{
		super (componentType, elementName);
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		boolean result = super.isAllowAttribute(token);
		
		if (result)
		{
			result = !ObjectUtils.equals(token.getName(), TemplateConstants.ValueScriptAttribute) &&
						!ObjectUtils.equals(token.getName(), TemplateConstants.HiddenScriptAttribute) &&
						!ObjectUtils.equals(token.getName(), TemplateConstants.PrivilegeScriptAttribute) &&
						!ObjectUtils.equals(token.getName(), TemplateConstants.ReadOnlyPrivilegeScriptField) &&
						!ObjectUtils.equals(token.getName(), TemplateConstants.hiddenPrivilegeScriptField) &&
						!ObjectUtils.equals(token.getName(), TemplateConstants.ValidateScriptTypeAttribute) &&
						!ObjectUtils.equals(token.getName(), TemplateConstants.ValidateScriptAttribute) &&
						!ObjectUtils.equals(token.getName(), TemplateConstants.SystemValidateAttribute);
		}
		
		return result;
	}
	
	@Override
	protected void writeAttribute(AttributeToken attribute)
	{
		if (StringUtils.equalsIgnoreCase(attribute.getName(), "_name"))
		{
			if ((ObjectUtils.equals(getTemplate().getType(), Template.TYPE_FORM) || 
				ObjectUtils.equals(getTemplate().getType(), Template.TYPE_DETAILS)) &&
				!ObjectUtils.toString(attribute.getValue()).startsWith("domain."))
			{
				getMarkupWriter().attributes("name", "domain." + attribute.getValue());
			}
			else
			{
				getMarkupWriter().attributes("name", attribute.getValue());
			}
		}
		else
		{
			if (!StringUtils.equalsIgnoreCase(attribute.getName(), "_style"))
			  super.writeAttribute(attribute);
		}
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{
		return false;
	}
	
	
	@Override
	protected void writeComponentEnd()
	{
		String valueScript = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), TemplateConstants.ValueScriptAttribute);
		//String hiddenScript = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), TemplateConstants.HiddenScriptAttribute);
		String validateScript = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), TemplateConstants.ValidateScriptAttribute);
		String validateScriptType = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), TemplateConstants.ValidateScriptTypeAttribute);
		String systemValidate = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), TemplateConstants.SystemValidateAttribute);
		String fieldValue = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "fieldValue");
		//设置样式
		String style = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_style");
		
		
		if (ObjectUtils.equals(validateScriptType, "0"))
		{
			if (!StringUtils.isBlank(systemValidate))
			{
				writeSystemValidateAttribute(systemValidate);
			}
			
			super.writeComponentEnd();
		}
		else
		{
			super.writeComponentEnd();
			
			if (!StringUtils.isBlank(validateScript))
			{
				writeValidateScript(validateScript);
			}
		}
		
		if (!StringUtils.isBlank(valueScript))
		{
			writeValueScript(valueScript);
		}
        
	/*	if (!StringUtils.isBlank(hiddenScript))
		{
			writeHiddenScript(hiddenScript);
		}*/
		if(fieldValue != null && !StringUtils.isBlank(fieldValue))
		{
			
			writeCheckbox(fieldValue);
		}
		//增加样式控制div结束符
		if(!StringUtil.isEmpty(style)){
			  getMarkupWriter().writeRaw("</div>");
		}
		getComponentIdStack().pop();
	}
	
	protected void writeSystemValidateAttribute (String systemValidate)
	{
		ArrayList<String> rules = new ArrayList<String> ();
		for (String rule : StringUtils.split(systemValidate, "|"))
		{
			if (!StringUtils.isBlank(rule))
			{
				rules.add(rule);
			}
		}
		
		getMarkupWriter().attributes("cssClass", StringUtils.join(rules.iterator(), "|"));
	}
	
	protected void writeValidateScript (String validateScript)
	{
		validateScript = StringEscapeUtils.unescapeHtml(validateScript);
		BodyBuilder script = new BodyBuilder ();
		
		script.addln("$(\"%s\").onmouseout =function ()", getComponentIdStack().peek());
		script.begin();
		script.addln(validateScript);
		script.end();
		
		getMarkupWriter().element("script", "type", "text/javascript");
		getMarkupWriter().writeRaw(script.toString());
		getMarkupWriter().end();
	}
	
	protected void writeValueScript (String valueScript)
	{
		valueScript = StringEscapeUtils.unescapeHtml(valueScript);
		valueScript = StringEscapeUtils.escapeJava(valueScript);
		
		BodyBuilder script = new BodyBuilder ();
		//script.addln("if (!defined ($(\"%s\").value)) {", getComponentIdStack().peek());
		script.addln("if ($(\"%s\").value != undefined) {", getComponentIdStack().peek());
		script.addln("$(\"%s\").value = \"%s\";", 
				getComponentIdStack().peek(), 
				"<%=" + String.format("ObjectUtils.toString (((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\"))", TemplateConstants.ScriptEngineAttribute, valueScript) + "%>");
		script.addln("}");
		getMarkupWriter().element("script", "type", "text/javascript");
		getMarkupWriter().writeRaw(script.toString());
		getMarkupWriter().end();
	}
	
	protected void writeHiddenScript (String hiddenScript)
	{
		hiddenScript = StringEscapeUtils.unescapeHtml(hiddenScript);
		hiddenScript = StringEscapeUtils.escapeJava(hiddenScript);
		
		getMarkupWriter().writeRaw("<%" + String.format("((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\");", TemplateConstants.ScriptEngineAttribute, hiddenScript) + "%>");
	}
	
	protected void writeCheckbox(String fieldValue)
	{
		
	}
	
}

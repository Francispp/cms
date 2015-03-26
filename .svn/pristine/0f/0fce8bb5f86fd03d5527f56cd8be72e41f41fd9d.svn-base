package com.cyberway.cms.internal.template.token;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;

public class EmailWriter extends ComponentWriter
{
	public EmailWriter()
	{
		super ("Email", "img");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		if (StringUtils.equalsIgnoreCase(TokenUtils.getAttributeValue(startElement, getTemplate(), "direct"), "true"))
		{
			Map<String, String> map = new HashMap<String, String> ();
			map.put("documentId", "${domain.id}");
			map.put("to", StringUtils.defaultString(TokenUtils.getAttributeValue(startElement, getTemplate(), "to"), StringUtils.EMPTY));
			map.put("cc", StringUtils.defaultString(TokenUtils.getAttributeValue(startElement, getTemplate(), "cc"), StringUtils.EMPTY));
			map.put("subject", StringUtils.defaultString(TokenUtils.getAttributeValue(startElement, getTemplate(), "subject"), StringUtils.EMPTY));
			map.put("body", StringUtils.defaultString(TokenUtils.getAttributeValue(startElement, getTemplate(), "body"), StringUtils.EMPTY));
			map.put("address", StringUtils.defaultString(TokenUtils.getAttributeValue(startElement, getTemplate(), "address"), StringUtils.EMPTY));
			
			
			String queryString = StringUtils.join(CollectionUtils.collect(map.entrySet(), new Transformer ()
			{
				public Object transform(Object obj)
				{
					Entry<String, String> pair = (Entry<String, String>)obj;
					
					return String.format("%s=%s", pair.getKey(), pair.getValue());
				}
			}).iterator(), "&");
			
			//getMarkupWriter().element("a", "href", "#", "onclick", String.format("new Ajax('${ctx}/cms/email!edit.action?%s', {method: 'post'}).request();", queryString));
			getMarkupWriter().element("a", "href", "#", "onclick", String.format("new Ajax.Request('${ctx}/cms/email!send.action',{method: 'get', parameters: '%s', onComplete: showResponse});", queryString));
		}
		else
		{
			getMarkupWriter().element("a", "href", "#", "onclick", "window.showModalDialog ('${ctx}/cms/email!edit.action?documentId=${domain.id}', null, 'dialogWidth:470px;DialogHeight=330px;scroll:no;status:no')");
		}
		
		super.writeComponentStart(startElement);
	}
	
	@Override
	protected void writeComponentEnd()
	{
		String style = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), TemplateConstants.StyleAttribute);
		String icon = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "iconList");
		if(StringUtils.isNotBlank(icon))
		{
			getMarkupWriter().attributes("style", style, "src", "/images/"+icon);
		}
		else
		{
		getMarkupWriter().attributes("style", style, "src", "${ctx}/images/common/email.gif");
		}

		super.writeComponentEnd();
		
		getMarkupWriter().end();
	}
	
	protected void writeHiddenScript (String hiddenScript)
	{
		hiddenScript = StringEscapeUtils.unescapeHtml(hiddenScript);
		hiddenScript = StringEscapeUtils.escapeJava(hiddenScript);
		
		getMarkupWriter().writeRaw("<%" + String.format("((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\");", TemplateConstants.ScriptEngineAttribute, hiddenScript) + "%>");
	}
}

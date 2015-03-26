package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.common.utils.BodyBuilder;
import com.cyberway.core.utils.StringUtil;

public class CheckboxWriter extends FormFieldWriter
{
	public CheckboxWriter()
	{
		super ("Checkbox", "ww:hidden");
		//super ("Checkbox", "ww:checkbox");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		boolean result = super.isAllowAttribute(token);
		
		if (result)
		{
			result = !ObjectUtils.equals(token.getName(), "type");
		}
		
		if(result && token.getName().indexOf("fieldValue")>=0)
			return false;
		return result;
	}
	
	@Override
	protected void writeValueScript(String valueScript)
	{
		valueScript = StringEscapeUtils.unescapeHtml(valueScript);
		valueScript = StringEscapeUtils.escapeJava(valueScript);
		
		BodyBuilder script = new BodyBuilder ();
		script.addln("window.addEvent (\"domready\", function ()");
		script.begin();
		script.addln("$(\"%s_CK\").checked = \"%s\";", 
				getComponentIdStack().peek(), 
				"<%=" + String.format("ObjectUtils.toString (((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\"))", TemplateConstants.ScriptEngineAttribute, valueScript) + "%>");
		script.end();
		script.add(");");
		
		getMarkupWriter().element("script", "type", "text/javascript");
		getMarkupWriter().writeRaw(script.toString());
		getMarkupWriter().end();
	}
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}		
		getComponentIdStack().push(id);
		//String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		//String type = TokenUtils.getAttributeValue(startElement, getTemplate(), "type");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		//String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		

		
		//增加样式控制
		if(!StringUtil.isEmpty(style)){
			  getMarkupWriter().writeRaw("<div class=\""+style+"\">");
			}
		
				
		//<input type="checkbox" name="domain.fieldString5" value="1" id="Checkbox_3451541888"/>
		//getMarkupWriter().writeRaw("<input type='checkbox' name='"+name+"_CK' value='"+fieldValue+"' id='"+name+"_CK' onchange=\"changeCheckBox(this,'"+name+"','"+fieldValueNo+"')\" />");
		//getMarkupWriter().element("input", "type", "checkbox","onchange", "changeCheckBox(this,'"+getComponentIdStack().peek()+"')","name",getComponentIdStack().peek()+"_CK","id",getComponentIdStack().peek()+"_CK");
		super.writeComponentStart(startElement);
		}
	@Override
	protected void writeComponentEnd()
	{
		String id = getComponentIdStack().peek();
		String name = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_name");
		String fieldValue = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "fieldValue");
		String fieldValueNo = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "fieldValueNo");
		if(StringUtil.isEmpty(fieldValueNo))
			fieldValueNo="";		
		if(!name.startsWith("domain.")){
			name = "domain."+name;
		}
		super.writeComponentEnd();
		getMarkupWriter().writeRaw("<input type='checkbox' name='"+id+"_CK' value='"+fieldValue+"' id='"+id+"_CK' onchange=\"changeCheckBox(this,'"+id+"','"+fieldValueNo+"')\" />");

		StringBuffer sb=new StringBuffer();
		sb.append("setcheckbox('"+id+"_CK"+"','<ww:property value=\""+name+"\"/>','"+fieldValue+"');\n");
		sb.append("if($('"+id+"').value == 'on'){$('"+id+"').value ='"+fieldValueNo+"';};\n");
		getMarkupWriter().element("script", "type", "text/javascript");
		getComponentIdStack().peek();
		getMarkupWriter().writeRaw(sb.toString());
		getMarkupWriter().end ();
	}	
	protected void writeCheckbox(String fieldValue)
	{
		
	}
}

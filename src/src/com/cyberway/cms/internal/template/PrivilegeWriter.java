package com.cyberway.cms.internal.template;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.common.service.MarkupWriter;

public class PrivilegeWriter {
	/**
	 * @author lan
	 * 生成权限控制脚本
	 * @param runing
	 * @param startElement
	 * @param template
	 * @param markupWriter
	 */
	public static void writePrivilegeScript (String runing,StartElementToken startElement,Template template,MarkupWriter markupWriter)
	{
		String readOnlyPrivilegeScriptField = TokenUtils.getAttributeValue(startElement, template, TemplateConstants.ReadOnlyPrivilegeScriptField);
		String hiddenPrivilegeScriptField = TokenUtils.getAttributeValue(startElement, template, TemplateConstants.hiddenPrivilegeScriptField);
		String privilegeScript = getPrivilegeScript();
		//String privilegeScript = TokenUtils.getAttributeValue(startElement, template, TemplateConstants.PrivilegeScriptAttribute);
		String name = TokenUtils.getAttributeValue(startElement, template, TemplateConstants._InputName);
	if(StringUtils.isNotEmpty(readOnlyPrivilegeScriptField) || StringUtils.isNotEmpty(hiddenPrivilegeScriptField))
	{
		if(StringUtils.isEmpty(readOnlyPrivilegeScriptField))
		{
			readOnlyPrivilegeScriptField = "true";
		}
		if(StringUtils.isEmpty(hiddenPrivilegeScriptField))
		{
			hiddenPrivilegeScriptField = "true";
		}
		if (!StringUtils.isBlank(privilegeScript))
		{
		String str ="";
		readOnlyPrivilegeScriptField = StringEscapeUtils.unescapeHtml(readOnlyPrivilegeScriptField);
		readOnlyPrivilegeScriptField = StringEscapeUtils.escapeJava(readOnlyPrivilegeScriptField);
		hiddenPrivilegeScriptField = StringEscapeUtils.unescapeHtml(hiddenPrivilegeScriptField);
		hiddenPrivilegeScriptField = StringEscapeUtils.escapeJava(hiddenPrivilegeScriptField);
		
		privilegeScript = privilegeScript.replace(TemplateConstants.ReplaceReadOnly, String.format("scriptEngine.eval (\"%s\")", readOnlyPrivilegeScriptField));
		privilegeScript = privilegeScript.replace(TemplateConstants.ReplaceHidden, String.format("scriptEngine.eval (\"%s\")", hiddenPrivilegeScriptField));
		if(runing.equals(TemplateConstants.TagEnd))
		{
		   str += toCreateXMLString(privilegeScript,TemplateConstants.TagReadonly,runing);
		   str += toCreateXMLString(privilegeScript,TemplateConstants.TagHidden,runing);
		   if(name != null && !StringUtils.isEmpty(name))
		   {
			   str = str.replace(TemplateConstants.ReplaceName,String.format("scriptEngine.eval (\"%s\");", "var name = '"+name+"';$HTML.print($WEB.getProperty(name));"));
				//str = str.replace(TemplateConstants.ReplaceName, name);
		   }
		   else
		   {
			   str = str.replace(TemplateConstants.ReplaceName, ""); 
		   }
		}
		else
		{
			str += toCreateXMLString(privilegeScript,TemplateConstants.TagHidden,runing);
			str += toCreateXMLString(privilegeScript,TemplateConstants.TagReadonly,runing);
		}
		markupWriter.writeRaw("<%" + String.format("%s", str) + "%>");
		
		
		}
	  }
	}
	//原有权限模式
	/*public static void writePrivilegeScript (String runing,StartElementToken startElement,Template template,MarkupWriter markupWriter)
	{
		String privilegeScript = TokenUtils.getAttributeValue(startElement, template, TemplateConstants.PrivilegeScriptAttribute);
		String name = TokenUtils.getAttributeValue(startElement, template, TemplateConstants.InputName);
		if (!StringUtils.isBlank(privilegeScript))
		{
		String str ="";
		privilegeScript = StringEscapeUtils.unescapeHtml(privilegeScript);
		privilegeScript = StringEscapeUtils.escapeJava(privilegeScript);
		if(runing.equals(TemplateConstants.TagEnd))
		{
		   str += toCreateXMLString(privilegeScript,TemplateConstants.TagReadonly,runing);
		   str += toCreateXMLString(privilegeScript,TemplateConstants.TagHidden,runing);
		}
		else
		{
			str += toCreateXMLString(privilegeScript,TemplateConstants.TagHidden,runing);
			str += toCreateXMLString(privilegeScript,TemplateConstants.TagReadonly,runing);
		}
		if(name != null && !StringUtils.isEmpty(name))
		str = str.replace(TemplateConstants.ReplaceName, name);
		str = StringEscapeUtils.unescapeJava(str);
		markupWriter.writeRaw("<%" + String.format("scriptEngine.eval (\"%s\");", str) + "%>");
		}
		}*/
	
	//创建XML
	public static String toCreateXMLString(String str, String element,String run) {
		String rtn = "";
		try{
			
		  Document document = DocumentHelper.parseText(str);
		 
		  Element root=document.getRootElement();
		  Element book=root.element(element);
		  if(book != null)
		  {
		  Element tagStart=root.element(element).element(run);
		  if(tagStart != null)
		  {
			  rtn = tagStart.getStringValue();
		  }
		  }
		}	
		catch (DocumentException t)
		{
		 t.printStackTrace ();
		}
		return rtn;
	}
	
	public static String getPrivilegeScript()
	{
		String str = "";
		str +="<root>" +
				"<readonly>" +
				"<start>if(((Boolean)@ReadOnly).booleanValue()){</start>" +
				"<end>}else{@NAME}</end>" +
				"</readonly>" +
				"<hidden>" +
				"<start>if(((Boolean)@Hidden).booleanValue()){</start>" +
				"<end>}</end>" +
				"</hidden>" +
				"</root>";
		return str;
	}

}

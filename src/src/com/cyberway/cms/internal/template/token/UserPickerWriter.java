package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.service.ScriptService;

public class UserPickerWriter extends AbstractComponentWriter
{
	private ScriptService _scriptService;
	private String _popupWindow;
	private String _textFieldId;
	private String _hiddenFieldId;
	private String _buttonId;
	
	public String getPopupWindow()
	{
		return _popupWindow;
	}

	public void setPopupWindow(String popupWindow)
	{
		_popupWindow = popupWindow;
	}

	public ScriptService getScriptService()
	{
		return _scriptService;
	}

	public void setScriptService(ScriptService scriptService)
	{
		_scriptService = scriptService;
	}

	@Override
	protected boolean isComponent(StartElementToken startElement)
	{
		return TokenUtils.hasAttribute(startElement, 
				getTemplate(),
				TemplateConstants.ComponentTypeAttribute, 
				"UserPicker");
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{
		return false;
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return !ObjectUtils.equals(token.getName(), TemplateConstants.ComponentTypeAttribute) && 
					!StringUtils.equalsIgnoreCase(token.getName(), "value") &&
					!StringUtils.equalsIgnoreCase(token.getName(), "id");
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		_textFieldId = getIdAllocator().allocate("textField");
		_hiddenFieldId = getIdAllocator().allocate("hiddenField");
		_buttonId = getIdAllocator().allocate("button");
		
		getMarkupWriter().element("input", "type", "hidden", "id", _hiddenFieldId);
		getMarkupWriter().end();
		getMarkupWriter().element("input", "type", "text", "id", _textFieldId);
	}
	
	@Override
	protected void writeComponentEnd()
	{
		super.writeComponentEnd();
		
//		getMarkupWriter().element("button", "id", _buttonId);
//		getMarkupWriter().write("选择");
//		getMarkupWriter().end();
//		
//		String valueScript = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), TemplateConstants.ValueScriptAttribute);
//		
//		BodyBuilder script = new BodyBuilder ();
//		if (!StringUtils.isBlank(valueScript))
//		{
//			script.addln("$(\"%s\").addEvent (\"onpropertychange\", function ()", _hiddenFieldId);
//			script.begin();
//			script.addln("if (event.propertyName == \"value\")");
//			script.begin();
//			script.addln("\t\t$(\"%s\").value = $(\"%s\").value ? userService.get ($(\"%s\").value).name : \"\";", _textFieldId, _hiddenFieldId, _hiddenFieldId);
//			script.end();
//			script.end();
//			script.add(");");
//			script.addln("$(\"%s\").value = %s;", _hiddenFieldId, ScriptUtils.toExpression(valueScript));
//			
//			MarkupWriterUtils.writeJavascript(script.toString(), getMarkupWriter());
//			
//			script.clear();
//		}
//		
//		script.addln("$(\"%s\").addEvent (\"onclick\", function ()", _buttonId);
//		script.end();
//		script.addln("\t$(\"%s\").value = window.showModalDialog (\"%s\");", getPopupWindow());
//		script.end();
//		script.add(");");
	}
}

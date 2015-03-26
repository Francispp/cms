package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import ognl.Ognl;

import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;


public class OperationWriter extends ComponentWriter
{
	public OperationWriter()
	{
		super ("Operation", "");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token) 
	{
		return false;
	}

	@Override
	protected void writeComponentStart(StartElementToken startElement,Object object1,Object object2) 
	{

		String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		String buttonScriptTypeField = TokenUtils.getAttributeValue(startElement, getTemplate(), "buttonScriptTypeField");
		try{
			Object obj = null;
			if(StringUtils.isNotBlank(buttonScriptTypeField) && buttonScriptTypeField.equals("1"))
				obj = Ognl.getValue(name, object2);
			else
				obj = Ognl.getValue(name, object1);
				getMarkupWriter().writeRaw(obj.toString());
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected void writeComponentEnd(Object object1,Object object2) 
	{		
		//super.writeComponentEnd(object);	
	}
}

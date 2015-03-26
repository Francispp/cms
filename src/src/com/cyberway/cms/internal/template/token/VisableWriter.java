package com.cyberway.cms.internal.template.token;


import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.common.utils.BodyBuilder;

public class VisableWriter extends FormFieldWriter
{
	public VisableWriter() 
	{
		super ("Visable", "");
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String list = TokenUtils.getAttributeValue(startElement, getTemplate(), "list");
		String target = TokenUtils.getAttributeValue(startElement, getTemplate(), "target");
		BodyBuilder jsp = new BodyBuilder ();
		jsp.begin();
		jsp.addln("java.util.ArrayList<String> colNameList = new java.util.ArrayList<String>();");
		for(String colName:list.split(",")){
			jsp.addln("colNameList.add(\""+colName+"\");");
		}
		jsp.addln("boolean isVisabled = false;");
		jsp.addln("for(String colName:colNameList){");
		jsp.addln("Object colValue = request.getAttribute(\"domain.\"+colName);");
		jsp.addln("if(colValue != null && !\"\".equals(colValue.toString().trim())){");
		jsp.addln("isVisabled = true;");
		jsp.addln("break;");
		jsp.addln("}");
		jsp.addln("}");
		jsp.addln("request.setAttribute(\""+target+"\",isVisabled);");
		jsp.end();
		getMarkupWriter().writeRaw("<%"+jsp.toString()+"%>");
	}

	
	@Override
	protected void writeComponentEnd()
	{
	}
}

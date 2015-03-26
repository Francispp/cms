package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.objects.Loginer;

public class UserWriter extends ComponentWriter{
	public UserWriter()
	{
		super("User","div");
	}
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		String _id = TokenUtils.getAttributeValue(startElement, getTemplate(), "_id");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String title = TokenUtils.getAttributeValue(startElement, getTemplate(), "title");
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(_id))
		{
		getComponentIdStack().push(id);
		StringBuffer sb=new StringBuffer();
		String divid = getComponentIdStack().peek();
		
		getMarkupWriter().element("div","class",style);
		getMarkupWriter().writeRaw("<table border=0><tr><td class='sel'>");
		getMarkupWriter().element("ww:hidden", "id", divid,"name","domain."+name);
		getMarkupWriter().end ();
		getMarkupWriter().element("ww:textfield", "id", "name"+divid, "name","domain."+_id,"readonly","true");
		getMarkupWriter().end ();
		getMarkupWriter().writeRaw("</td><td class='sel_but'>");
		if(Constants.LOGON_USER.equals(Loginer.USER_LOCAL))
		{
			sb.append("<input type='button' name='select' id='select' value='选择' onclick='selectUserTreeOrSearch(myform."+divid+",myform.name"+divid+",true,null,null,null,true)' /></td></tr></table>");
		}
		else
		{
		sb.append("<input type='button' name='select' id='select' value='选择' onclick='selectUser(myform."+divid+",myform.name"+divid+")' /></td></tr></table>");
		}
		getMarkupWriter().writeRaw(sb.toString());
		}
	}
	
	@Override
	protected void writeComponentEnd()
	{
		super.writeComponentEnd();
	}


}

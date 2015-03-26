package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class MessageWriter extends ComponentWriter{
	public MessageWriter()
	{
		super("Message","div");
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
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String pageSize = TokenUtils.getAttributeValue(startElement, getTemplate(), "listSize");
		String deleteMessage = TokenUtils.getAttributeValue(startElement, getTemplate(), "deleteMessage");
		String displayContent = TokenUtils.getAttributeValue(startElement, getTemplate(), "displayContent");
		String delete_choose = TokenUtils.getAttributeValue(startElement, getTemplate(), "delete_choose");
		
		if(pageSize == null && StringUtils.isEmpty(pageSize))
		{
			pageSize = "";
		}
		if(name == null)
		{
			name = "";
		}
		if(delete_choose == null)
		{
			delete_choose = "";
		}
		if(deleteMessage == null && StringUtils.isEmpty(deleteMessage))
		{
			deleteMessage = "false";
		}
		
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		String forwordURL = "/base/message.action?docid=${domain.id}&pageSize="+pageSize+"&deleteMessage="+deleteMessage+"&displayContent="+displayContent+"&delete_choose="+delete_choose+"&message_style="+style;
		getComponentIdStack().push(id);
		StringBuffer sb=new StringBuffer();
		String divid = getComponentIdStack().peek();
		getMarkupWriter().element("div","id",divid,"width","100%");
		 sb.append("<iframe width='100%' height='1200' ");
		 sb.append("src='"+forwordURL+"' ");//后缀为html
		 sb.append(" marginwidth=0 marginheight=0 frameborder=0 scrolling=no id=frsame onload=javascript:dyniframesizeformessage(this.id)>");
		 sb.append("</iframe>");
		getMarkupWriter().writeRaw(sb.toString());
		
		
	}
	
	@Override
	protected void writeComponentEnd()
	{
		super.writeComponentEnd();
	

	}


}

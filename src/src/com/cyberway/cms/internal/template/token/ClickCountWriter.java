package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

/**
 * @author caiw
 * 点击率标签的过滤器
 *
 */
public class ClickCountWriter extends ComponentWriter
{
	public ClickCountWriter()
	{
		super ("Clickcount", "span");
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
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		
		getComponentIdStack().push(id);
		getMarkupWriter().element(getElementName(), "id", getComponentIdStack().peek());
		//设置样式
		String style = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_style");
		if(!StringUtil.isEmpty(style))
		 getMarkupWriter().attributes("class",style);
	}
	
	@Override
	protected void writeComponentEnd() 
	{
		//获得名称
		String clickType=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "clickType");
		StringBuffer sb=new StringBuffer();
		StringBuffer pre=new StringBuffer();
		 
		pre.append("var objId; ");
		if(clickType.equals("1")){
			pre.append("objId = $REQUEST.getParameter('siteId');");
			pre.append("if(objId == null){"); 
			pre.append("var http = $REQUEST.getServerName();"); 
			pre.append("objId = SITESERVICE.getSiteManagerService().getSite(http,0).getOid();}");  
		} else if(clickType.equals("2")){
			pre.append("objId = $REQUEST.getParameter('channelId');");
			pre.append("if(objId == null && $REQUEST.getParameter('id') != null){"); 
			pre.append("objId = $REQUEST.getAttribute('channel').getId();"); 
			pre.append("}");  
		}else if(clickType.equals("4")){
			pre.append("objId = $REQUEST.getParameter('channelId');");
			pre.append("if(objId == null && $REQUEST.getParameter('id') != null){"); 
			pre.append("objId = $REQUEST.getAttribute('channel').getId();"); 
			pre.append("}");
		}else if(clickType.equals("5")){
			pre.append("objId = $REQUEST.getParameter('siteId');");
			pre.append("if(objId == null){"); 
			pre.append("var http = $REQUEST.getServerName();"); 
			pre.append("objId = SITESERVICE.getSiteManagerService().getSite(http,0).getOid();}");  
		}else{
			pre.append("objId = $REQUEST.getParameter('id');");
		} 
		
		sb.append( pre.toString());
		sb.append("$HTML.print(SITESERVICE.getClickCount(TOOLS.toLong(objId),"+clickType+"));");

	    getMarkupWriter().writeRaw("<%scriptEngine.eval (\""+sb.toString()+"\");%>\n");
		super.writeComponentEnd();

		
	}
	 
	protected void writeComponentEndbak() 
	{
		//获得名称
		String clickType=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "clickType");
		StringBuffer sb=new StringBuffer();
		
		String docidstr="$REQUEST.getParameter('id')"; 
		String siteidstr="$REQUEST.getParameter('siteId')"; 
		String channelidstr="$REQUEST.getParameter('channelId')"; 
		
		sb.append("$HTML.print(SITESERVICE.getClickCount(TOOLS.toLong("+siteidstr+"),TOOLS.toLong("+channelidstr+"),TOOLS.toLong("+docidstr+"),"+clickType+"));");

	    getMarkupWriter().writeRaw("<%scriptEngine.eval (\""+sb.toString()+"\");%>\n");
		super.writeComponentEnd();

		
	}
}

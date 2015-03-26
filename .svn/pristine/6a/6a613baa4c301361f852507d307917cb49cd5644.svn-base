package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class NavigationWriter extends ComponentWriter{
	public NavigationWriter()
	{
		super("Navigation","div");
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
		String navigationField = TokenUtils.getAttributeValue(startElement, getTemplate(), "navigationField");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String direct = TokenUtils.getAttributeValue(startElement, getTemplate(), "direct");
		String levelTarget = TokenUtils.getAttributeValue(startElement, getTemplate(), "levelTargetList"); 
		if(StringUtils.isEmpty(direct) || !direct.equals("true"))
		{
			direct ="false";
		}
		String title = TokenUtils.getAttributeValue(startElement, getTemplate(), "title");
		
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		 
		getComponentIdStack().push(id);
		String navigation = "com.cyberway.cms.exposed.ChannelServiceJsUtil serveice = new com.cyberway.cms.exposed.ChannelServiceJsUtil(); ";
		navigation += "DocumentCommonService documentCommonService = (DocumentCommonService)ServiceLocator.getBean (\"documentCommonService\");";
		navigation += "ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean (\"channelManagerService\");";
		navigation += "serveice.setDocumentCommonService(documentCommonService); \n";
		navigation += "serveice.setChannelManagerService(channelManagerService); \n";
		if(navigationField !=null && !StringUtils.isEmpty(navigationField))
		{
			if(navigationField.equals("current"))
			{
				navigation += "out.println(serveice.getChannel(request.getParameter(\"channelId\"),request.getParameter(\"id\")).getName());";
			}
			else{
				navigation += "out.println(serveice.getSuperChannels(request.getParameter(\"channelId\"),request.getParameter(\"id\"),"+Boolean.parseBoolean(direct)+",\""+levelTarget+"\"));";
			}
		}
		getMarkupWriter().writeRaw("<p class = \""+style+"\"><% {"+navigation+" } %></p>\n");

	}
	
	@Override
	protected void writeComponentEnd()
	{
		
	}

}

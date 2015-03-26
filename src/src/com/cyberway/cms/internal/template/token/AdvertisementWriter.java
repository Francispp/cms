package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class AdvertisementWriter extends ComponentWriter{
	public AdvertisementWriter()
	{
		super("Advertisement","");
	}
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String imgType = TokenUtils.getAttributeValue(startElement, getTemplate(), "buttonScriptTypeField");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String title = TokenUtils.getAttributeValue(startElement, getTemplate(), "title");
		String jumpURL = TokenUtils.getAttributeValue(startElement, getTemplate(), "jumpURL");
		String showType = TokenUtils.getAttributeValue(startElement, getTemplate(), "showType");
		String iconList = TokenUtils.getAttributeValue(startElement, getTemplate(), "iconList");
		
		StringBuilder sb=new StringBuilder();
		
		//sb.append("<img src='\\images\\"+iconList+"'/>");
		//sb.append("\n<table>\n<tr>\n<td>A</td>\n<td>B</td>\n<td>C</td>\n<td>D</td>\n</tr>\n<tr>\n<td>A</td>\n<td>B</td>\n<td>C</td>\n<td>D</td>\n</tr>\n<tr>\n<td>A</td>\n<td>B</td>\n<td>C</td>\n<td>D</td>\n</tr>\n<a href=\"test_view2.jsp\"></a>\n</table>");
		sb.append("\n<script src=\"${ctx}/common/cms_js/advertisement.js\"  type=\"text/javascript\"></script>");
		
		sb.append("\n<script>");
		
		sb.append("\nadvertisement_init('"+imgType+"','"+showType+"','\\images\\\\"+iconList+"','"+jumpURL+"');");
		
		
		sb.append("\n</script>");
	
		
		if(StringUtils.isBlank(style))
		{
			style ="";
		}
		if(StringUtils.isBlank(jumpURL))
		{
			jumpURL ="";
		}
		
		getMarkupWriter().writeRaw(sb.toString());
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}

}

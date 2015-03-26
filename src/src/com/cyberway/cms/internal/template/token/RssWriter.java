package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

public class RssWriter extends ComponentWriter{
	
	public RssWriter()
	{
		super("Rss","");
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
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String onclick = TokenUtils.getAttributeValue(startElement, getTemplate(), "_onclick");
		String loginpath = TokenUtils.getAttributeValue(startElement, getTemplate(), "loginpath");
		String optionFileValueList = TokenUtils.getAttributeValue(startElement, getTemplate(), "optionFileValueList");
		String optionFileTypeList = TokenUtils.getAttributeValue(startElement, getTemplate(), "optionFileTypeList");
		String siteIdField=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "siteIdField");
		String portalid=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "portalid");

		
		
		
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		if(StringUtils.isBlank(style))
		{
			style ="";
		}
		if(StringUtils.isBlank(onclick))
		{
			onclick ="";
		}
		if(StringUtils.isBlank(loginpath))
		{
			loginpath ="";
		}
		
		if(StringUtils.isBlank(optionFileValueList))
		{
			optionFileValueList ="";
		}
		if(StringUtils.isBlank(optionFileTypeList))
		{
			optionFileTypeList ="";
		}
		if(StringUtils.isBlank(siteIdField))
		{
			siteIdField ="0";
		}
		if(StringUtils.isBlank(portalid))
		{
			portalid ="0";
		}
		getComponentIdStack().push(id);
		String[] vs = null;
		String[] ns = null;
		
		StringBuffer sb=new StringBuffer();
		if (!StringUtil.isEmpty(optionFileValueList))
			vs =StringUtil.split(optionFileValueList, ",");
			
		if (!StringUtil.isEmpty(optionFileTypeList))
			ns = StringUtil.split(optionFileTypeList, ",");
		
		

		sb.append("<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>");
		sb.append("<script type='text/javascript' src='${ctx}/dwr/util.js'></script>");
		sb.append("<script type='text/javascript' src='${ctx}/common/cms_js/rss.js'></script>");
		sb.append("<script src='${ctx}/dwr/interface/rssManagerService.js'></script>");
		sb.append("<script type='text/javascript' src='/common/datepicker/WdatePicker.js' defer='defer'></script>");
		sb.append("<ww:if test=\"${empty session['loginer'] || session['loginer'].loginType != 2}\">");
		sb.append("<script language='javascript'>");
		sb.append("alert('请先登陆！');");
		sb.append("location.href='"+loginpath+"'");
		sb.append("</script>");
		sb.append("</ww:if><ww:elseif test=\"${session['loginer'].loginType == 2}\">");
		 


		sb.append("<div>");
		sb.append("<table border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tr><td>定阅栏目：</td>");
		sb.append("<td height='50'><select name='RsId' id='RsId'>");
		if(vs != null && ns != null && vs.length == ns.length)
		{
		for(int i = 0;i<vs.length;i++)
		{
			sb.append("<option value='"+vs[i]+"'>"+ns[i]+"</option>");
		}
		}
		
		sb.append("</select></td>");
		sb.append("<td>&nbsp;定阅开始时间：</td>");
		sb.append("<td><ww:textfield name='startDate' cssClass='WdatePickerCss' onfocus=\"WdatePicker({vel:this,lang:'zh-cn',dateFmt:'yyyy-M-d',skin:'whyGreen'})\"/></td>");
		sb.append("<td>&nbsp;定阅结束时间：</td>");
		sb.append("<td><ww:textfield name='endDate' cssClass='WdatePickerCss' onfocus=\"WdatePicker({vel:this,lang:'zh-cn',dateFmt:'yyyy-M-d',skin:'whyGreen'})\"/></td>");
		sb.append("<td>&nbsp;<button onclick='addRSS();'>添加定阅</button></td>");
		sb.append("</tr></table>");
		sb.append("<div id='RSS'></div>");
		
sb.append("</div>");
sb.append("<script language='javascript'>");
sb.append("initRss(${loginer.userid});");

		sb.append("</script>");
		sb.append("</ww:elseif>");
		
		getMarkupWriter().writeRaw(sb.toString());
		
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}

}

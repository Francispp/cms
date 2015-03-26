package com.cyberway.cms.internal.template.token;

import net.sf.json.JSONArray;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.utils.BodyBuilder;
import com.cyberway.core.utils.StringUtil;

public class HistoryWriter extends ComponentWriter
{
	public HistoryWriter()
	{
		super ("History", "table");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String style = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), TemplateConstants.StyleAttribute);
		if(StringUtil.isEmpty(style))
			style="history_default";
		super.writeComponentStart(startElement);
		 
		getMarkupWriter().getElement().forceAttributes("cellpadding", "3", "cellspacing", "1", "width", "100%", "class", style+"_"+"dataGrid");
	}
	
	@Override
	protected void writeComponentEnd()
	{
		JSONArray displayFields = JSONArray.fromObject(StringEscapeUtils.unescapeHtml(TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "displayFields")));
		getMarkupWriter().element("tr", "class", "header");
		for (int index = 0; index < displayFields.size(); index++)
		{
			
			//
			if (ObjectUtils.equals(displayFields.getString(index), "username"))
			{
				getMarkupWriter().element("td", "class", "td_username");
				getMarkupWriter().write("用户名");				
			}
			else if (ObjectUtils.equals(displayFields.getString(index), "department"))
			{
				getMarkupWriter().element("td", "class", "td_department");
				getMarkupWriter().write("部门");
			}
			else if (ObjectUtils.equals(displayFields.getString(index), "time"))
			{
				getMarkupWriter().element("td", "class", "td_time");
				getMarkupWriter().write("时间");
			}
			else if (ObjectUtils.equals(displayFields.getString(index), "operation"))
			{
				getMarkupWriter().element("td", "class", "td_operation");
				getMarkupWriter().write("操作");
			}
			
			getMarkupWriter().end();
		}
		getMarkupWriter().end();
		
		BodyBuilder jsp = new BodyBuilder ();
		jsp.begin(); 
		jsp.addln("LogManagerService logManagerService = (LogManagerService)ServiceLocator.getBean (\"logManagerService\");");
		jsp.addln("if (StringUtils.isNotBlank (request.getParameter (\"id\"))) {");
		jsp.addln("request.setAttribute (\"_data\", logManagerService.listById (request.getParameter (\"pageIndex\")==null?1:Integer.valueOf(request.getParameter (\"pageIndex\")),10,Long.valueOf (request.getParameter (\"id\")), \"%s\"));", Constants.LOG_TARGET_TYPE_DOCUMENT);
		jsp.addln("} else { request.setAttribute (\"_data\", new com.cyberway.core.dao.support.Page ()); }");
		jsp.end();
		getMarkupWriter().writeRaw("<%" + jsp.toString() + "%>");
		getMarkupWriter().element("ww:iterator", "value", "#request._data.result", "status", "rowstatus");
		getMarkupWriter().element("ww:set", "name", "row", "value", "'row'");
		getMarkupWriter().end();
		getMarkupWriter().element("ww:set", "name", "rowAlt", "value", "'row-alt'");
		getMarkupWriter().end();
		getMarkupWriter().element("tr", "class", "<ww:property value='#rowstatus.odd ? #row : #rowAlt' />");
		boolean alt = false;
		for (int index = 0; index < displayFields.size(); index++)
		{
			String fieldName = displayFields.getString(index);
			
			getMarkupWriter().element("td", "class", alt ? "cell-alt" : "cell");	
			if (ObjectUtils.equals(fieldName, "username"))
			{
				getMarkupWriter().element("ww:property", "value", "operator");
				getMarkupWriter().end();
			}
			else if (ObjectUtils.equals(fieldName, "department"))
			{
				getMarkupWriter().element("ww:property", "value", "operatorDepartment");
				getMarkupWriter().end();
			}
			else if (ObjectUtils.equals(fieldName, "time"))
			{
				getMarkupWriter().element("ww:property", "value", "@org.apache.commons.lang.time.DateFormatUtils@format (time, 'yyyy-MM-dd HH:mm:ss')");
				getMarkupWriter().end();
			}
			else if (ObjectUtils.equals(fieldName, "operation"))
			{
				getMarkupWriter().element("ww:property", "value", "action");
			}
			
			getMarkupWriter().end();
			
			alt = !alt;
		}
		
		getMarkupWriter().end();
		getMarkupWriter().end();
		getMarkupWriter().end();
		
		getMarkupWriter().element("cms:tablePager", "style","tablePager",
				"pageIndex", "#request._data.CurrentPageNo",
				"pageSize", "#request._data.pageSize",
				"recordCount", "#request._data.totalCount");
		
		getMarkupWriter().end();
	}
}

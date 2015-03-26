package com.cyberway.cms.internal.template.token;

import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.ecside.table.limit.FilterSet;
import org.ecside.table.limit.Sort;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.cms.template.token.TokenType;
import com.cyberway.common.html.dom.Element;
import com.cyberway.common.utils.BodyBuilder;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.staticer.util.TagGatherUtils;

public class ListWriter extends ComponentWriter
{
	public static class Limit implements org.ecside.table.limit.Limit
	{
		private Integer _pageIndex;
		private Integer _pageSize;
		private Integer _recordCount;
		private String _orderBy;
		private boolean _ascending = true;
		
		public Limit(Integer pageIndex, Integer pageSize, Integer recordCount, String orderBy, boolean ascending)
		{
			_pageIndex = pageIndex;
			_pageSize = pageSize;
			_recordCount = recordCount;
			_orderBy = orderBy;
			_ascending = ascending;
		}

		public int getCurrentRowsDisplayed()
		{
			return _pageSize;
		}

		public FilterSet getFilterSet()
		{
			return null;
		}

		public int getPage()
		{
			return _pageIndex;
		}

		public int getRowEnd()
		{
			return 0;
		}

		public int getRowStart()
		{
			return 0;
		}

		public Sort getSort()
		{
			return new Sort (_orderBy, _orderBy, _ascending ? "asc" : "desc");
		}

		public int getTotalRows()
		{
			return _recordCount;
		}

		public boolean isCleared()
		{
			return false;
		}

		public boolean isExported()
		{
			return false;
		}

		public boolean isFiltered()
		{
			return false;
		}

		public boolean isSorted()
		{
			return false;
		}

		public void setRowAttributes(int i, int j)
		{
		}
	}
	
	public ListWriter()
	{
		super ("List", "table");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return !StringUtils.equalsIgnoreCase(token.getName(), "field");
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{		
		//设置样式
		String style = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_style");
		String css_pix="list_";
		if(!StringUtil.isEmpty(style)){
			css_pix=style;
		}
		
		getMarkupWriter().element("input", "type", "hidden", "id", "orderBy", "name", "orderBy");
		getMarkupWriter().end();
		
		
		super.writeComponentStart(startElement);	
		//border="0" cellPadding="0" cellSpacing="0"
		getMarkupWriter().getElement().forceAttributes("border", "0","cellpadding", "0", "cellspacing", "0", "width", "100%", "class", css_pix+"dataGrid");
		getMarkupWriter().writeRaw("\n<!-- ");
		getMarkupWriter().element("div");
	}
	
	@Override
	protected void writeComponentEnd()
	{ 
		getMarkupWriter().end();
		getMarkupWriter().writeRaw(" -->\n");
		StartElementToken bodyStartElement = TokenUtils.getStartElement(getComponentStack().peek(), TokenUtils.getEndElement(getComponentStack().peek(), getTemplate()), "tbody", getTemplate());
		StartElementToken trStartElement = TokenUtils.getStartElement(bodyStartElement, TokenUtils.getEndElement(bodyStartElement, getTemplate()), "tr", getTemplate());
		JSONArray displayFields = new JSONArray ();
		for (int index = getTemplate().getTokens().indexOf(trStartElement); index < getTemplate().getTokens().indexOf(TokenUtils.getEndElement(trStartElement, getTemplate())); index++)
		{
			if (ObjectUtils.equals(getTemplate().getTokens().get(index).getTokenType(), TokenType.StartElement))
			{
				StartElementToken startElement = (StartElementToken)getTemplate().getTokens().get(index);
				if (ObjectUtils.equals(startElement.getName(), "td"))
				{
					String str = StringEscapeUtils.unescapeHtml(TokenUtils.getAttributeValue(startElement, getTemplate(), "field"));
					displayFields.add(JSONObject.fromObject(str));
				} 
			}
		}
		String channel = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "channel");
		String infotype = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "infotype");//显示信息类型数据
		String pageSize = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "pageSize");
		if(StringUtils.isBlank(pageSize))
			pageSize="10";
		String where = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "where");
		if(where==null)
			where="";
		if(!StringUtil.isEmpty(where)){
			where = StringEscapeUtils.unescapeHtml(where);
			where = StringEscapeUtils.escapeJava(where);
		}
		String newsImage = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "newsImage");
		String isNewsImagePlace = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "isNewsImagePlace");//显示最新图标位置
		String newsCondition = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "newsCondition");
		String dynaCondition= TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "dynaCondition");//高级动态条件
		String orderBy = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "orderBy");
		if (StringUtils.isBlank(orderBy))
		{
			orderBy = "id";
		}
		String sortOrder = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "sortOrder");
		if (StringUtils.isBlank(sortOrder))
		{
			sortOrder = "descending";
		}
		String pagination = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "pagination");
		String showHeader = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "showHeader");
		String showSearchBar = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "showSearchBar");
		//设置样式
		String style = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_style");
		String css_pix="list_";
		if(!StringUtil.isEmpty(style)){
			css_pix=style;
		}
		if (StringUtils.equalsIgnoreCase(showSearchBar, "true"))
		{
			getMarkupWriter().element("tr", "class", "header");
			Iterator<JSONObject> iterator = displayFields.iterator();
			while (iterator.hasNext())
			{
				JSONObject json = iterator.next();
			    
				getMarkupWriter().element("td");
				getMarkupWriter().element("input", "type", "text", 
						"id", String.format("_%s", json.getString("property")), 
						"name", String.format("_%s", json.getString("property")),
						"onkeydown", String.format("if (event.keyCode == 13) {%s ();}", getComponentIdStack().peek()),
						"value", "<%=ObjectUtils.toString (request.getParameter (\"_" + json.getString("property") + "\"))%>");
				getMarkupWriter().end();
				getMarkupWriter().end();
			}
			getMarkupWriter().end();
		}
		
		if (StringUtils.equalsIgnoreCase(showHeader, "true"))
		{
			getMarkupWriter().element("tr", "class", "header"); 
			Iterator<JSONObject> iterator = displayFields.iterator();
			while (iterator.hasNext())
			{
				JSONObject json = iterator.next();
				String title=json.getString("title");
				if(!StringUtil.isEmpty(title) && title.startsWith("<td")){//从table td开始，自定义表头
					//处理单击事件
					title=StringUtil.replace(title, "CUSTOMonclick=", "onclick=");
					getMarkupWriter().writeRaw(title);
				}else{
				getMarkupWriter().element("td");
				getMarkupWriter().element("a", "href", "javascript:"+String.format("sortList ('%s', 'orderBy');", json.getString("property")));
				getMarkupWriter().write(json.getString("title"));
				getMarkupWriter().end();
				getMarkupWriter().end();
				}
			}
			getMarkupWriter().end();
		}
		
		BodyBuilder jsp = new BodyBuilder ();
		jsp.begin(); 
		//jsp.addln("int pageIndex = StringUtils.isBlank (request.getParameter (\"pageIndex\")) ? 1 : Integer.valueOf (request.getParameter (\"pageIndex\"));");
		jsp.addln("String pageIndex = request.getParameter (\"pageIndex\");");
		jsp.addln("if(pageIndex == null || pageIndex.length() < 1) { pageIndex = \"1\"; }");
		jsp.addln(" String orderBy = request.getParameter (\"orderBy\");");
		jsp.addln("if(orderBy == null || orderBy.length() < 1) { orderBy = \"%s\"; }",orderBy.split(",")[0]);  
		jsp.addln(" boolean descend=%s;",StringUtils.equalsIgnoreCase("descending",sortOrder.split(",")[0]));
		jsp.addln(" if(request.getParameter(\"descending\")!=null) descend=Boolean.parseBoolean(request.getParameter(\"descending\"));");
		
		jsp.addln("Limit limit = new Limit (Integer.valueOf(pageIndex), Integer.valueOf(\"%s\"), null, orderBy, !descend);", pageSize);//, StringUtils.equalsIgnoreCase(sortOrder, "descending")
		jsp.addln("DocumentCommonService documentCommonService = (DocumentCommonService)ServiceLocator.getBean (\"documentCommonService\");");
		jsp.addln("ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean (\"channelManagerService\");");
		jsp.addln("Channel channel =null; ");
		if(StringUtil.isEmpty(channel))
			jsp.addln(" channel = channelManagerService.getChannelFromCache (Long.valueOf (request.getParameter (\"%s\")));", "channelId");
		else
			jsp.addln(" channel = channelManagerService.getChannelFromCache (Long.valueOf (\"%s\"));", channel);
		jsp.addln("CriteriaSetup criteriaSetup = new CriteriaSetup ();");
		jsp.addln(" List<org.hibernate.criterion.Order> inOrders = new ArrayList<org.hibernate.criterion.Order>();");
		if(!StringUtil.isEmpty(orderBy)){
			String [] inOrders =  orderBy.split(",");
			String [] inSorts =  sortOrder.split(","); 
			if(inSorts.length >= inOrders.length)
			for (int i = 0; i<inOrders.length; i++) {
				if(StringUtils.equalsIgnoreCase(inSorts[i], "descending"))
					jsp.addln("inOrders.add(org.hibernate.criterion.Order.desc(\""+inOrders[i]+"\"));");
				else
					jsp.addln("inOrders.add(org.hibernate.criterion.Order.asc(\""+inOrders[i]+"\"));"); 
			}
		}
		jsp.addln("criteriaSetup.setInOrders(inOrders);");
		jsp.addln("ArrayList criterions = new ArrayList ();");
		Iterator<JSONObject> iterator = displayFields.iterator();
		while (iterator.hasNext())
		{
			JSONObject json = (JSONObject)iterator.next();
			//若存在property,才输出，否则
			if(json.containsKey("property")){					
			jsp.addln("if (StringUtils.isNotBlank (request.getParameter (\"_%s\"))){", json.getString("property"));
			jsp.addln("criterions.add (Restrictions.like (\"%s\", \"%s\"+request.getParameter (\"_%s\")+\"%s\"));", json.getString("property"),"%", json.getString("property"),"%");
			jsp.addln("}");
			}
		}
		jsp.addln("criteriaSetup.setAddCriterions(criterions);");
		jsp.addln("String where = \"%s\";",where);
		//增加高级动态条件
		if(!StringUtil.isEmpty(dynaCondition)){
			dynaCondition = StringEscapeUtils.unescapeHtml(dynaCondition);
			dynaCondition = StringEscapeUtils.escapeJava(dynaCondition);			
		  if(StringUtil.isEmpty(where)){
			 jsp.addln("where = (String)(((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\"));", TemplateConstants.ScriptEngineAttribute, dynaCondition);
		  }else{//原条件不为空
			  jsp.addln("where += \" and (\"+ (String)(((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\"))+\")\";", TemplateConstants.ScriptEngineAttribute, dynaCondition);
		  }
		}
		
		if(!StringUtil.isEmpty(infotype) && infotype.equals("2"))
		 jsp.addln("Page datas = documentCommonService.findByPublishPageSiteDocs (limit, criteriaSetup, where, null,channel);");
		else if(!StringUtil.isEmpty(infotype) && infotype.equals("1")) 
		 jsp.addln("Page datas = documentCommonService.findChildByPublishPage (limit, criteriaSetup, channel, where);");
		else
		 jsp.addln("Page datas = documentCommonService.findByPublishPage (limit, criteriaSetup, channel, where);");
		
		
		
		jsp.addln("request.setAttribute (\"_data\",datas);"); 
		jsp.addln("request.setAttribute (\"_data_size\",datas.getTotalCount());"); 
		jsp.end();
		getMarkupWriter().writeRaw("<%" + jsp.toString() + "%>");
		getMarkupWriter().element("ww:iterator", "value", "#request._data.result", "status", "rowstatus");
		String isViewOnly = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "isViewOnly");
		if(StringUtils.isNotEmpty(isViewOnly) && isViewOnly.equals("1"))
		{
			StringBuffer str = new StringBuffer();
			str.append("<ww:if test=\"#request._data.totalCount == 1\">");
			str.append("<c:redirect url=\"docInfo!view.action?id=${id}&channelId=${channelId}\"></c:redirect>");
			str.append("</ww:if>");
			getMarkupWriter().writeRaw(str.toString());
		}
		
		getMarkupWriter().element("ww:set", "name", "row", "value", "'row'");
		getMarkupWriter().end();
		getMarkupWriter().element("ww:set", "name", "rowAlt", "value", "'row-alt'");
		getMarkupWriter().end();
		getMarkupWriter().element("ww:if","test","#rowstatus.first==true");
		getMarkupWriter().element("ww:hidden", "value","${id}","id","firstdocid");
		getMarkupWriter().end();
		getMarkupWriter().end();
		
		getMarkupWriter().element("tr", "class", "<ww:property value='#rowstatus.odd ? #row : #rowAlt' />");
		iterator = displayFields.iterator();
		boolean alt = false;
		while (iterator.hasNext())
		{
			JSONObject json = iterator.next();
			//自定义内容，且已增加td部分
			if (ObjectUtils.equals(json.opt("type"), "custom")&& !StringUtil.isEmpty(json.getString("custom"))
					&& json.getString("custom").startsWith("<td")){				
				getMarkupWriter().writeRaw(json.getString("custom"));
			}else{
			Integer maxLength = json.optInt("maxLength");
			getMarkupWriter().element("td", "class", alt ? "cell-alt" : "cell");
			if (ObjectUtils.equals(json.opt("type"), "link"))
			{ 
				getMarkupWriter().element("a", "href", json.getString("path"));
				if (StringUtils.equalsIgnoreCase(json.optString("linkOpenInNewWindow"), "true"))
				{
					getMarkupWriter().attributes("target", "_blank");
				}
				writeText(json.getString("property"), maxLength,(json.get("formatType") != null)?json.getString("formatType"):null,json.getString("type"));
				
				getMarkupWriter().end();
			}
			else if (ObjectUtils.equals(json.opt("type"),  "news"))
			{
				if (!StringUtils.isBlank(newsImage) && !StringUtils.isBlank(newsCondition))
				{
					jsp = new BodyBuilder ();
					//jsp.addln("((ScriptEngine)request.getAttribute (\"%s\")).put (\"$ITEM\", null);", TemplateConstants.ScriptEngineAttribute);
					//jsp.addln("if (ObjectUtils.equals (((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\"), Boolean.TRUE)) {", TemplateConstants.ScriptEngineAttribute, newsCondition);
					//.booleanValue()
					newsCondition = StringEscapeUtils.unescapeHtml(newsCondition);
					newsCondition = StringEscapeUtils.escapeJava(newsCondition);
					jsp.addln("if (((Boolean)(((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\"))).booleanValue()) {", TemplateConstants.ScriptEngineAttribute, newsCondition);
					//显示图标在内容前
				if(StringUtil.isEmpty(isNewsImagePlace)||!isNewsImagePlace.equals("1")){
					getMarkupWriter().writeRaw("<%" + jsp.toString() + "%>"); 
					getMarkupWriter().element("img", "src", newsImage);
					getMarkupWriter().end();
					getMarkupWriter().writeRaw("<%}%>");
				}
				}
				getMarkupWriter().element("a", "href", json.getString("path"));
				if (StringUtils.equalsIgnoreCase(json.optString("linkOpenInNewWindow"), "true"))
				{
					getMarkupWriter().attributes("target", "_blank");
				}
				writeText(json.getString("property"), maxLength,(json.get("formatType") != null)?json.getString("formatType"):null,json.getString("type"));
				getMarkupWriter().end();
				
				if (!StringUtils.isBlank(newsImage) && !StringUtils.isBlank(newsCondition))
				{
/*					jsp = new BodyBuilder ();
					//jsp.addln("((ScriptEngine)request.getAttribute (\"%s\")).put (\"$ITEM\", null);", TemplateConstants.ScriptEngineAttribute);
					//jsp.addln("if (ObjectUtils.equals (((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\"), Boolean.TRUE)) {", TemplateConstants.ScriptEngineAttribute, newsCondition);
					//.booleanValue()
					newsCondition = StringEscapeUtils.unescapeHtml(newsCondition);
					newsCondition = StringEscapeUtils.escapeJava(newsCondition);
					jsp.addln("if (((Boolean)(((ScriptEngine)request.getAttribute (\"%s\")).eval (\"%s\"))).booleanValue()) {", TemplateConstants.ScriptEngineAttribute, newsCondition);
*/					//显示图标在内容前
				 if(!StringUtil.isEmpty(isNewsImagePlace)&&isNewsImagePlace.equals("1")){
					getMarkupWriter().writeRaw("<%" + jsp.toString() + "%>"); 
					getMarkupWriter().element("img", "src", newsImage);
					getMarkupWriter().end();
					getMarkupWriter().writeRaw("<%}%>");
				}
				}
			}
			else if (ObjectUtils.equals(json.opt("type"), "custom"))
			{
				getMarkupWriter().writeRaw(json.getString("custom"));
			}
			else
			{
				writeText(json.getString("property"), maxLength,(json.get("formatType") != null)?json.getString("formatType"):null,json.getString("type"));
			}
			getMarkupWriter().end();
			
			alt = !alt;
			}
		}
		
		getMarkupWriter().end();
		getMarkupWriter().end();
		getMarkupWriter().end(); 
		 
		
		if (StringUtils.equalsIgnoreCase(pagination, "true"))
		{
			getMarkupWriter().element("cms:tablePager", 
					"pageIndex", "#request._data.currentPageNo",
					"pageSize", "#request._data.pageSize",
					"recordCount", "#request._data.totalCount");
			getMarkupWriter().end();
		
			/*List列表页面采集*/
			getMarkupWriter().writeRaw(TagGatherUtils.getListTag());
		}
		
		if (StringUtils.equalsIgnoreCase(showSearchBar, "true"))
		{  
			//取得头元素
			Element headE = getMarkupWriter().getDocument().getRootElement().find("head");
			//创建元素
			Element scriptE = headE.element("script", "type", "text/javascript");
			scriptE.raw(String.format("function %s () {", getComponentIdStack().peek()));
			scriptE.raw("var url = window.location.href;");
			
			iterator = displayFields.iterator();
			while (iterator.hasNext())
			{
				JSONObject json = iterator.next();
				scriptE.raw (String.format("url = replaceUrl (url, \"%s\", document.getElementById (\"%s\").value);\n", "_" + json.getString("property"), "_" + json.getString("property")));
			}
			
			scriptE.raw("window.location.href = url;}");
		}
		
	
	}
	
	private void writeText (String expression, Integer maxLength,String formatType,String type)
	{
		if(type != null && type.equals("date") &&  StringUtils.isNotEmpty(formatType))
		{
			StringBuffer sb=new StringBuffer();
			sb.append("<ww:text name='{0, date, "+formatType+"}'><ww:param name='value' value='"+expression+"'/></ww:text>");
			getMarkupWriter().writeRaw(sb.toString());
			
		}
		else
		{
		if (maxLength != null && maxLength > 4)
		{
		 getMarkupWriter().element("ww:property", "value", String.format("@org.apache.commons.lang.StringUtils@abbreviate(@org.apache.commons.lang.ObjectUtils@toString (%s), %d)", expression, maxLength));
		}
		else
		{
			getMarkupWriter().element("ww:property", "value", expression);
		}
		getMarkupWriter().end();
		}
	}
	@Override
	protected void writeAttribute(AttributeToken attribute)
	{
		if (!ObjectUtils.equals(attribute.getName(), "field"))
		{
			super.writeAttribute(attribute);
		}
	}


}

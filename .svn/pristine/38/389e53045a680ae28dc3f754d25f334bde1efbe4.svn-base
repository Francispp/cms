package com.cyberway.cms.internal.template.token;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.cms.template.token.TokenType;
import com.cyberway.common.utils.BodyBuilder;
import com.cyberway.core.utils.StringUtil;

public class SearchWriter extends ComponentWriter
{
	public SearchWriter()
	{
		super ("Search", "table");
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
		String showList=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "showList");
		if("ul".equals(showList)){
			String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
			if (StringUtils.isBlank(id))
			{
				id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
			}
			getComponentIdStack().push(id);
			getMarkupWriter().element("UL", "id", getComponentIdStack().peek(),"class","sp_search_list");
		}else{
			super.writeComponentStart(startElement);
		}
	}
	
	@Override
	protected void writeComponentEnd()
	{
		String showList=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "showList");
		String pageSize = StringUtils.defaultString(TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "pageSize"), "10");
		String siteId = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "site");
		String published = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "published");
		String showHeader = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "showHeader");
		
		String texthtml = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "texthtml");
		String page = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "page");
		String channelId = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "channelId");
		//String condition = "site:" + siteId;
		String condition = "";
		if (!StringUtils.isBlank(published))
		{
			if (Boolean.valueOf(published))
			{
				condition = condition + String.format(" AND issued:%s ", 5);
			}
		}
		BodyBuilder jsp = new BodyBuilder ();
		jsp.begin(); 
		jsp.addln("SearchService searchService = (SearchService)ServiceLocator.getBean (\"documentSearchService\");");
		jsp.addln("String condition = StringUtils.isBlank (request.getParameter (\"condition\")) ? \"\" :  request.getParameter (\"condition\");"); 
		jsp.addln("String ssiteId = \"%s\";",siteId);
		jsp.addln("if(request.getParameter (\"siteId\") != null && request.getParameter (\"siteId\").length() >0) { ssiteId = request.getParameter (\"siteId\"); }");
		jsp.addln("else{  SiteManagerService siteService=(SiteManagerService)ServiceLocator.getBean(\"siteManagerService\");");		
		jsp.addln("com.cyberway.cms.domain.CmsSite  tempsite=siteService.getSite(request.getServerName(),request.getServerPort());"); 
		jsp.addln("if( tempsite != null && tempsite.getOid() != null) ssiteId = tempsite.getOid().toString();");  
		jsp.addln("}"); 
		jsp.addln("if(condition == null || condition.length() < 1){ condition=\"\";}");
		if(StringUtils.isBlank(channelId)){
			jsp.addln("PagedCollection result = new PagedCollectionImpl (searchService.search (ssiteId,\"site:\"+ssiteId+\"%s\",condition));",condition);
		}else{
			jsp.addln("PagedCollection result = new PagedCollectionImpl (searchService.search (ssiteId,\"site:\"+ssiteId+\"%s\",condition,%sL));",condition,channelId);
		}
		jsp.addln("result.setPageSize (%s);", pageSize);
		jsp.addln("String pageIndex = request.getParameter (\"pageIndex\");");
		jsp.addln("if(pageIndex == null || pageIndex.length() < 1) { pageIndex = \"1\"; }");
		jsp.addln("result.setCurrentPage (Integer.parseInt(pageIndex));");
		jsp.addln("request.setAttribute (\"_data\", result);");
		jsp.end();
		
		if("default".equals(showList)){
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
			getMarkupWriter().writeRaw("<%" + jsp.toString() + "%>");
			if (StringUtils.equalsIgnoreCase(showHeader, "true"))
			{
			   getMarkupWriter().element("tr", "class", "header");
			   for (int index = 0; index < displayFields.size(); index++)
			   {
				String title=displayFields.getJSONObject(index).getString("title");
				if(!StringUtil.isEmpty(title) && title.startsWith("<td")){//从table td开始，自定义表头 
					//处理单击事件
					title=StringUtil.replace(title, "CUSTOMonclick=", "onclick=");
					getMarkupWriter().writeRaw(title);
				}else{
				getMarkupWriter().element("td");
				getMarkupWriter().write(displayFields.getJSONObject(index).getString("title"));
				getMarkupWriter().end();
				}
			   }
			   getMarkupWriter().end();
			}
			getMarkupWriter().element("ww:iterator", "value", "#request._data.iterator()", "status", "rowstatus");
			getMarkupWriter().element("ww:set", "name", "row", "value", "'row'");
			getMarkupWriter().end();
			getMarkupWriter().element("ww:set", "name", "rowAlt", "value", "'row-alt'");
			getMarkupWriter().end();
			getMarkupWriter().element("tr", "class", "<ww:property value='#rowstatus.odd ? #row : #rowAlt' />");
			boolean alt = false;
			
			for (int index = 0; index < displayFields.size(); index++)
			{
				JSONObject json = displayFields.getJSONObject(index);
				//自定义内容，且已增加td部分
				if (ObjectUtils.equals(json.opt("type"), "custom")&& !StringUtil.isEmpty(json.getString("custom"))
						&& json.getString("custom").trim().startsWith("<td")){
					String custom=json.getString("custom");
					if(!StringUtil.isEmpty(custom) && custom.indexOf("${")>0 && custom.indexOf("}")>0 ){
						custom=StringUtil.replace(custom, "${", "<ww:property value=\"get ('");
						custom=StringUtil.replace(custom, "}", "')\"></ww:property>");
					}
					
					getMarkupWriter().writeRaw(custom);
				}else{
					
				Integer maxLength = json.optInt("maxLength");
				getMarkupWriter().element("td", "class", alt ? "cell-alt" : "cell");
				if (ObjectUtils.equals(json.opt("type"), "link"))
				{ 
					String path=json.getString("path");
					if(!StringUtil.isEmpty(path) && path.indexOf("${id}")>0)
						path=StringUtil.replace(path, "${id}", "<ww:property value=\"get ('id')\"></ww:property>");
						//path.replaceAll("${id}", "<ww:property value=\"get ('id')\"></ww:property>");
					getMarkupWriter().element("a", "href", path);
					if (StringUtils.equalsIgnoreCase(json.optString("linkOpenInNewWindow"), "true"))
					{
						getMarkupWriter().attributes("target", "_blank");
					}
					writeText(json.getString("property"), maxLength);
					getMarkupWriter().end();
				}
				else if (ObjectUtils.equals(json.opt("type"), "custom"))
				{
					//<ww:property value="get ('timeCreated')"></ww:property>
					String custom=json.getString("custom");
					if(!StringUtil.isEmpty(custom) && custom.indexOf("${")>0 && custom.indexOf("}")>0 ){
						custom=StringUtil.replace(custom, "${", "<ww:property value=\"get ('");
						custom=StringUtil.replace(custom, "}", "')\"></ww:property>");
					}
						//custom.replaceAll("${id}", "<ww:property value=\"get ('id')\"></ww:property>");
					getMarkupWriter().writeRaw(custom);
				}
				else
				{
					writeText(json.getString("property"), maxLength);
				}
				getMarkupWriter().end();
				
				alt = !alt;
			}
			}
			getMarkupWriter().end();
			getMarkupWriter().end();
		}else if("custom".equals(showList) || "ul".equals(showList)){
			getMarkupWriter().writeRaw("<%" + jsp.toString() + "%>");
			if("ul".equals(showList)){
				getMarkupWriter().writeRaw("<div class=\"sp_search_match\"><ww:property value=\"#request._data.virtualCount\"/>个符合搜索结果</div>");
			}
			getMarkupWriter().element("ww:iterator", "value", "#request._data.iterator()", "status", "rowstatus");
			if(!StringUtil.isEmpty(texthtml)){
				getMarkupWriter().writeRaw(StringUtil.EncodingHTML(texthtml));	
			}
			getMarkupWriter().end();
			getMarkupWriter().end();
		}
		
		if(!StringUtil.isEmpty(page)){
			page = StringEscapeUtils.unescapeHtml(page);
			page = page.replace("_data", "#request._data");
			getMarkupWriter().writeRaw(page);
		}else{
			getMarkupWriter().element("cms:tablePager", "style","tablePager",
					"pageIndex", "#request._data.currentPage",
					"pageSize", "#request._data.pageSize",
					"recordCount", "#request._data.virtualCount");
			getMarkupWriter().end();
		}   
	}
	
	private void writeText (String expression, Integer maxLength)
	{
		if (maxLength != null && maxLength > 4)
		{
			
			
			getMarkupWriter().element("ww:property", "value", String.format("@org.apache.commons.lang.StringUtils@abbreviate(@org.apache.commons.lang.ObjectUtils@toString (get ('%s')), %d)", expression, maxLength));
		}
		else
		{
			getMarkupWriter().element("ww:property", "value", String.format("get ('%s')", expression));
		}
		getMarkupWriter().end();
	}
}

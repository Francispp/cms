package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils; 

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.common.utils.BodyBuilder;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.staticer.util.TagGatherUtils;

public class HomelistWriter extends ComponentWriter{
	public HomelistWriter()
	{
		super("Homelist","");
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
		String channel = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "channel");
		String channelPath = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "channelPath");
		String pageSize = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "pageSize");
		String where = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "where");
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
		String texthtml = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "texthtml");
		String pagination = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "pagination");
		String titleHead = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "titleHead");
		String titleEnd = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "titleEnd");
		String infotype = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "infotype");//显示信息类型数据
		String page = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "page");
		String dynaCondition= TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "dynaCondition");//高级动态条件
		
		
		if(!StringUtil.isEmpty(texthtml)){
			texthtml = StringEscapeUtils.unescapeHtml(texthtml);
		}
		titleHead = !StringUtil.isEmpty(titleHead)? StringEscapeUtils.unescapeHtml(titleHead) : "";
		
		if(StringUtils.isBlank(pageSize))
			pageSize="10";
		
		if(where==null)
			where="";
		if(!StringUtil.isEmpty(where)){
			where = StringEscapeUtils.unescapeHtml(where);
			where = StringEscapeUtils.escapeJava(where);
		} 
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		if(!StringUtil.isEmpty(titleEnd)){
			titleEnd = titleEnd.replace("_data.", "#request._data"+id+".");
			titleEnd = StringEscapeUtils.unescapeHtml(titleEnd);
		}else{
			titleEnd = "";
		}
		getComponentIdStack().push(id);
		BodyBuilder jsp = new BodyBuilder ();
		jsp.begin(); 
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
		else{
			if(!StringUtil.isEmpty(channelPath))
				jsp.addln("channel = channelManagerService.getChannelBySitehttpAndPath(request.getServerName(),\"%s\");",channelPath);
			jsp.addln("if(channel == null)");
			jsp.addln(" channel = channelManagerService.getChannelFromCache (Long.valueOf (\"%s\"));", channel);
		}
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
			
		
		jsp.addln("request.setAttribute (\"_data"+id+"\",datas);");
		jsp.end();
		getMarkupWriter().writeRaw(titleHead);
		getMarkupWriter().writeRaw("<%" + jsp.toString() + "%>");
		getMarkupWriter().element("ww:iterator", "value", "#request._data"+id+".result", "status", "rowstatus");
		getMarkupWriter().writeRaw(texthtml);
		
		getMarkupWriter().end();
		getMarkupWriter().writeRaw(titleEnd);
		
		if (StringUtils.equalsIgnoreCase(pagination, "true"))
		{
			if(StringUtils.isNotBlank(page))
			{
				page = StringEscapeUtils.unescapeHtml(page);
				page = page.replace("_data", "#request._data"+id);
				getMarkupWriter().writeRaw(page);
			}
			else
			{
			getMarkupWriter().element("cms:tablePager", 
					"pageIndex", "#request._data"+id+".currentPageNo",
					"pageSize", "#request._data"+id+".pageSize",
					"recordCount", "#request._data"+id+".totalCount");
			getMarkupWriter().end();
			}
			/*首页列表页面采集*/
			getMarkupWriter().writeRaw(TagGatherUtils.getIndexListTag(id));
		}
		
		
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}

}

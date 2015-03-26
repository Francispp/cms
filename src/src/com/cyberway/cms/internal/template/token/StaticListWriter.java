package com.cyberway.cms.internal.template.token;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ognl.Ognl;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentFactory;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.tags.components.DocumentIterator.Limit;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.xml.domain.XmlData;
import com.cyberway.cms.xml.service.XmlDataManagerService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class StaticListWriter extends ComponentWriter{
	
	public StaticListWriter()
	{
		super("StaticList","");
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
		String keyID = TokenUtils.getAttributeValue(startElement, getTemplate(), "keyID");
		String channel = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "channel");
		String pageSize = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "pageSize");
		String where = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "where");
		String orderBy = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "orderBy");
		String sortOrder = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "sortOrder");
		String texthtml = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "texthtml");
		String pagehtml = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "pagehtml");
		String pagination = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "pagination");
		String tableView = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "tableView");
		String infotype = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "infotype");//显示信息类型数据
		String page = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "page");
		String displayFieldSelect= TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "displayFieldSelect");
		if(!StringUtil.isEmpty(texthtml)){
			texthtml = StringEscapeUtils.unescapeHtml(texthtml);
		}
		if(!StringUtil.isEmpty(pagehtml)){
			pagehtml = StringEscapeUtils.unescapeHtml(pagehtml);
		}
		
		if(StringUtils.isBlank(pageSize))
			pageSize="10";
		
		if(where==null)
			where="";
		if(!StringUtil.isEmpty(where)){
			where = StringEscapeUtils.unescapeHtml(where);
			where = StringEscapeUtils.escapeJava(where);
		}
		
		if(!StringUtil.isEmpty(displayFieldSelect)){
			displayFieldSelect = StringEscapeUtils.unescapeHtml(displayFieldSelect);
			
		}
		JSONArray array = JSONArray.fromObject("["+displayFieldSelect+"]");
		
		if (StringUtils.isBlank(orderBy))
		{
			orderBy = "id";
		}
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		if(StringUtils.isEmpty("keyID"))
		{
			keyID = id;
		}
		getComponentIdStack().push(id);
		String fileName = keyID+".xml";
		StringBuffer sb=new StringBuffer();
		try{
		Document document = DOMDocumentFactory.getInstance().createDocument();
		Element root = document.addElement("channel");
		DocumentCommonService documentCommonService = (DocumentCommonService)ServiceLocator.getBean ("documentCommonService");
		ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean ("channelManagerService");
		XmlDataManagerService xmlDataManagerService = (XmlDataManagerService)ServiceLocator.getBean ("xmlDataManagerService");
		Limit limit = new Limit (1, 9999, null, orderBy, StringUtils.equalsIgnoreCase(sortOrder, "descending"));
		CriteriaSetup criteriaSetup = new CriteriaSetup ();
		ArrayList criterions = new ArrayList ();
		criteriaSetup.setAddCriterions(criterions);
		Page datas;
		Channel chn = channelManagerService.getChannelFromCache(Long.valueOf(channel));
		if(!StringUtil.isEmpty(infotype) && infotype.equals("1")) 
			 datas = documentCommonService.findChildByPublishPage(limit, criteriaSetup, chn, where);
		else
			 datas = documentCommonService.findByPublishPage (limit, criteriaSetup, chn, where);
		Collection<BaseDocument> docs = (Collection)datas.getResult();
		for(BaseDocument baseDoc : docs)
		{
			Element recorder = root.addElement("recorder");
			recorder.addElement("id").setText(baseDoc.getId().toString());
			for(int i = 0; i < array.size(); i++)
			{
				JSONObject jsonObject = (JSONObject) array.get(i);
				Object obj = Ognl.getValue(jsonObject.getString("property"),baseDoc);
				recorder.addElement(jsonObject.getString("property")).setText(obj != null ? obj.toString() : StringUtils.EMPTY);
			}
		}
		xmlDataManagerService.writeXML(fileName, document);
		if(Constants.IS_REALPATH)
		{
			sb.append("<XML id='"+keyID+"' src='"+Constants.ABSOLUTE_PATH+Constants.XML_PATH+fileName+"'></XML>");
		}
		else{
			sb.append("<XML id='"+keyID+"' src='"+Constants.XML_PATH+fileName+"'></XML>");
		}
		XmlData xmlData;
		if(xmlDataManagerService.findBy("xmlID", keyID).size() > 0)
		{
			xmlData = xmlDataManagerService.findBy("xmlID", keyID).get(0);
		}
		else	
		{
		xmlData = XmlData.class.newInstance();
		Ognl.setValue("xmlID", xmlData, keyID);
		}
		Ognl.setValue("filePath", xmlData, fileName);
		Ognl.setValue("condition", xmlData, where);
		Ognl.setValue("channelId", xmlData, channel);
		Ognl.setValue("orderBy", xmlData, orderBy);
		Ognl.setValue("sortOrder", xmlData, sortOrder);
		Ognl.setValue("infotype", xmlData, infotype);
		Ognl.setValue("jsonObject", xmlData, array.toString());
		xmlDataManagerService.saveOrUpdate(xmlData);
		
		if (StringUtils.equalsIgnoreCase(tableView, "true"))
		{
			sb.append("<table width=\"100%\" id='XML#"+keyID+"'  border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  DATASRC='#"+keyID+"' DATAPAGESIZE="+pageSize+">");
			sb.append("<tr>");
			for(int i = 0; i < array.size(); i++)
			{
				JSONObject obj = (JSONObject) array.get(i);
				sb.append("<td><span DATAFLD=\""+obj.getString("property")+"\"></span></td>");
			}
			sb.append("</tr>");
			sb.append("</table>");
			if(StringUtils.equalsIgnoreCase(pagination, "true"))
			{
				sb.append("<table><tr>");
				sb.append("<td><input type=\"button\" value=\"首页\" onClick=\"$('XML#"+keyID+"').firstPage();\"></td>");
				sb.append("<td><input type=\"button\" value=\"上一页\" onClick=\"$('XML#"+keyID+"').previousPage();\"></td>");
				sb.append("<td><input type=\"button\" value=\"下一页\" onClick=\"$('XML#"+keyID+"').nextPage();\"></td>");
				sb.append("<td><input type=\"button\" value=\"最后一页\" onClick=\"$('XML#"+keyID+"').lastPage();\"></td>");
				sb.append("</tr></table>");
			}
		}
		else
		{
			texthtml = texthtml.replace("#xmldso", "#"+keyID);
			sb.append(texthtml);
			if(StringUtils.equalsIgnoreCase(pagination, "true"))
			{
			pagehtml = pagehtml.replace("#xmldso", "#"+keyID);
			sb.append(pagehtml);
			}
		}
		
		getMarkupWriter().writeRaw(sb.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}

}

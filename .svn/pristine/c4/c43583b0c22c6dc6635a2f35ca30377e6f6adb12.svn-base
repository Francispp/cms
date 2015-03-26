package com.cyberway.common.quartz.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ognl.Ognl;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.tags.components.DocumentIterator.Limit;
import com.cyberway.cms.xml.domain.XmlData;
import com.cyberway.cms.xml.service.XmlDataManagerService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class UpdateXmlJob implements StatefulJob{
	public UpdateXmlJob(){
		
	}
	 public void execute(JobExecutionContext context)throws JobExecutionException {
	 XmlDataManagerService xmlDataManagerService = (XmlDataManagerService) ServiceLocator.getBean("xmlDataManagerService");
	 DocumentCommonService documentCommonService = (DocumentCommonService)ServiceLocator.getBean ("documentCommonService");
	 ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean ("channelManagerService");
	 Document document = DOMDocumentFactory.getInstance().createDocument();
	 Element root = document.addElement("channel");
	 List<XmlData> xmlDatas = xmlDataManagerService.getAll();
	 for(XmlData xmlData : xmlDatas)
	 {
		 try{
		 Limit limit = new Limit (1, 9999, null, xmlData.getOrderBy(), StringUtils.equalsIgnoreCase(xmlData.getSortOrder(), "descending"));
		 CriteriaSetup criteriaSetup = new CriteriaSetup ();
			ArrayList criterions = new ArrayList ();
			criteriaSetup.setAddCriterions(criterions);
			Page datas;
			Channel chn = channelManagerService.getChannelFromCache(Long.valueOf(xmlData.getChannelId()));
			if(!StringUtil.isEmpty(xmlData.getInfotype()) && xmlData.getInfotype().equals("1")) 
				 datas = documentCommonService.findChildByPublishPage(limit, criteriaSetup, chn, xmlData.getCondition());
			else
				 datas = documentCommonService.findByPublishPage (limit, criteriaSetup, chn, xmlData.getCondition());
			Collection<BaseDocument> docs = (Collection)datas.getResult();
			for(BaseDocument baseDoc : docs)
			{
				Element recorder = root.addElement("recorder");
				recorder.addElement("id").setText(baseDoc.getId().toString());
				JSONArray array = JSONArray.fromObject(xmlData.getJsonObject());
				for(int i = 0; i < array.size(); i++)
				{
					JSONObject jsonObject = (JSONObject) array.get(i);
					Object obj = Ognl.getValue(jsonObject.getString("property"),baseDoc);
					recorder.addElement(jsonObject.getString("property")).setText(obj != null ? obj.toString() : StringUtils.EMPTY);
				}
			}
			xmlDataManagerService.writeXML(xmlData.getFilePath(), document);
		 }catch(Exception e){e.printStackTrace();}
	 }
	
	 }

}

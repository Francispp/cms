package com.cyberway.crawl.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import ognl.Ognl;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.form.domain.CoreFormField;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.form.service.CoreFormService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;



public class FormXMLFileProcessor {

	/**
	 * 读取已提取的XML文件，将数据填充到数据库中。
	 * @param file
	 * @param loginer
	 * @throws Exception
	 */
  private void traverse(File file,Loginer loginer) throws Exception {
	FileInputStream inputStream = new FileInputStream(file.getAbsoluteFile());
	SAXReader reader = new SAXReader();
	Document document = reader.read(inputStream);
	CoreFormService formService = (CoreFormService)ServiceLocator.getBean("coreFormService");
	DocumentCommonService documentCommonService = (DocumentCommonService) ServiceLocator.getBean("documentCommonService");
	Element root = document.getRootElement();
	Long formId = Long.getLong(root.element(SettingHandler.XML_ELEMENT_META).element(SettingHandler.XML_ELEMENT_FORM).getText());
	Long channelId = Long.getLong(root.element(SettingHandler.XML_ELEMENT_META).element(SettingHandler.XML_ELEMENT_CHANNEL).getText());
	CoreForm coreForm = formService.get(formId);
	if(coreForm != null)
	documentCommonService.setEntityObject(coreForm.getClass());
	List<CoreFormField> formFields = coreForm.getFormFields();
	Iterable<Element> crawlDatas = root.elements(SettingHandler.XML_ELEMENT_DATAS);
	for(Element crawlData : crawlDatas)
	{  BaseDocument doc = BaseDocument.class.newInstance();
	  Ognl.setValue("id", doc, documentCommonService.getSequence());
	  Ognl.setValue("channel.id", doc, channelId);
	  Ognl.setValue("authorId", doc, loginer.getUserid());
	  Ognl.setValue("authorCname", doc, loginer.getUsername());
		Iterable<Element> datas = crawlData.elements(SettingHandler.XML_ELEMENT_DATA);
		for(Element data : datas)
		{
			Ognl.setValue(data.attributeValue(SettingHandler.XML_ATTRIBUTE_NAME), doc, data.getText());	
		}
		documentCommonService.saveOrUpdate(doc);
	}
	
}
}

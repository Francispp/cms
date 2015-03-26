package com.cyberway.crawl.extractor;



import java.io.File;
import java.io.FileInputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ExtractorXMLSetting {
	private Document document;
	    
	    /**
	     * 初始化 extracts.xml
	     * @param extractFile
	     * @throws Exception
	     */
	    public ExtractorXMLSetting(Document document)throws Exception
	    {
	    	this.document = document;
	    }
	    public Document getExtractDocument() throws Exception
	    {
	    	return document;
	    }
	    
	    public Document getdocument(File file) throws Exception
	    {
	    	FileInputStream inputStream = new FileInputStream(file.getAbsoluteFile());
	    	SAXReader reader = new SAXReader();
	    	Document xmlDoc = reader.read(inputStream);
	    	inputStream.close();
	    	return xmlDoc;
	    }
       
	    public Iterable<Element> getSettingIterable(Element element, String tagName)
	    {
	    	Iterable<Element> elements = element.elements(tagName);
	    	
	    	return elements;
	    }
	    public String getSettingText(Element element,String tagName)
	    {
	    	String text = element.element(tagName).getText();
	    	
	    	return text;
	    }
	    public String getSettingAttribute(Element element,String attributeName)
	    {
	    	String value = element.attributeValue(attributeName);
	    	
	    	return value;	
	    }


	    
	   
}

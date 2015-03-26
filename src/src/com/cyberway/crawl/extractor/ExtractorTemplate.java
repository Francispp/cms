package com.cyberway.crawl.extractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;

import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.crawl.jobs.service.JobsManagerService;
import com.cyberway.crawl.util.SettingHandler;



public class ExtractorTemplate extends Extractor{

	/** 
	 * 提取条件Document
	 */
	private Document xmlDOC;
	
	/**
	 * 初始化数据
	 * @param xmlDOC 提取条件Document
	 * @param outputPath 输出数据XML的文件目录
	 * @param mirrorDir 抓取的镜像文件目录
	 * @param imageDir 抓取后的图片存放目录
	 */
    public void initialize(Document xmlDOC, String outputPath,String mirrorDir,String imageDir) {
		 this.xmlDOC = xmlDOC;
		 super.setOutputPath(outputPath);
		 super.setMirrorDir(mirrorDir);
		 super.setImageDir(imageDir);
	    }
    
    /**
     * 启动提取任务
     * @throws Exception
     */
    public void startExtract()throws Exception
    {
    	ExtractorXMLSetting setting = new ExtractorXMLSetting(xmlDOC);
		File  file = new File(this.getOutputPath()+ "/"+setting.getSettingText(setting.getExtractDocument().getRootElement().element(SettingHandler.XML_ELEMENT_META), SettingHandler.XML_ELEMENT_JOBUID) + SettingHandler.settingsFilenameSuffix);
		FileOutputStream outputStream = new FileOutputStream(file);
		OutputFormat format = new OutputFormat();
		format.setIndent(true);
		format.setNewlines(true);
		XMLWriter writer = new XMLWriter(outputStream, format);
		Document document = DOMDocumentFactory.getInstance().createDocument();
		Element root = document.addElement(SettingHandler.XML_ROOT_ELEMENT);
		Element meta = root.addElement(SettingHandler.XML_ELEMENT_META);
		Element jobID = meta.addElement(SettingHandler.XML_ELEMENT_JOBID);
		jobID.addText(setting.getExtractDocument().getRootElement().element(SettingHandler.XML_ELEMENT_META).element(SettingHandler.XML_ELEMENT_JOBID).getText());
		//Element form = meta.addElement(SettingHandler.XML_ELEMENT_FORM);
		//form.addText(setting.getExtractDocument().getRootElement().element(SettingHandler.XML_ELEMENT_META).element(SettingHandler.XML_ELEMENT_FORM).getText());
		//Element channel = meta.addElement(SettingHandler.XML_ELEMENT_CHANNEL);
		//channel.addText(setting.getExtractDocument().getRootElement().element(SettingHandler.XML_ELEMENT_META).element(SettingHandler.XML_ELEMENT_CHANNEL).getText());
		traverse(this, new File(getMirrorDir()),root);
		writer.write(document);
		
		try{
			if (outputStream != null)
				outputStream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		JobsManagerService jobsManagerService = (JobsManagerService)ServiceLocator.getBean("jobsManagerService");
		jobsManagerService.traverse(document);
    }
    
    /**
     * 实现父类的抽象方法，用于执行正则表达式提取数据。
     */
	public void extract(Element root) throws Exception{
		ExtractorXMLSetting setting = new ExtractorXMLSetting(xmlDOC);
		Element datas = root.addElement(SettingHandler.XML_ELEMENT_DATAS);
		for(Element element : (Iterable<Element>)setting.getSettingIterable(setting.getExtractDocument().getRootElement().element(SettingHandler.XML_ELEMENT_FILTERS), SettingHandler.XML_ELEMENT_FILTER))
		{			
				NodeFilter attributes_filter =new AndFilter(new TagNameFilter(setting.getSettingText(element, SettingHandler.XML_ELEMENT_TAGNAME)),
						new HasAttributeFilter(setting.getSettingAttribute(element.element(SettingHandler.XML_ELEMENT_ATTRIBUTE), SettingHandler.XML_ATTRIBUTE_NAME),setting.getSettingText(element, SettingHandler.XML_ELEMENT_ATTRIBUTE)));
				NodeList nodes = this.getParser().parse(attributes_filter);
				//System.out.println(setting.getSettingText(element, SettingHandler.XML_ELEMENT_TAGNAME)+nodes.size());
				for(int i=0;i<nodes.size();i++)
				{
					for(Element match : (Iterable<Element>)setting.getSettingIterable(element.element(SettingHandler.XML_ELEMENT_MATCHS), SettingHandler.XML_ELEMENT_MATCH))
					{
						//若提取的是图片类型
					if(match.attributeValue(SettingHandler.XML_ATTRIBUTE_FIELDTYPE).equals(SettingHandler.IMG))
					{
						ImageTag node = (ImageTag) nodes.elementAt(i);
						String image_url = node.getAttribute("SRC");
						String fileType = image_url.substring(image_url.lastIndexOf(".") + 1);
						String new_iamge_file = com.cyberway.crawl.util.StringUtils.encodePassword(image_url, HASH_ALGORITHM)+ "." + fileType;
						image_url = StringUtils.replace(image_url, "+", " ");
						//System.out.println(image_url+new_iamge_file);
						copyImage(image_url, new_iamge_file);
						Element data = datas.addElement(SettingHandler.XML_ELEMENT_DATA);
						data.addAttribute(SettingHandler.XML_ATTRIBUTE_NAME, match.attributeValue(SettingHandler.XML_ATTRIBUTE_FIELD));
						data.addText(new_iamge_file);
					}
					else{//其它类型
						TagNode node =(TagNode)nodes.elementAt(i);
						String result = getProp(match.getText(),node.toHtml(), 1);
						
						if(StringUtils.isNotEmpty(result))
						{
						Element data = datas.addElement(SettingHandler.XML_ELEMENT_DATA);
						data.addAttribute(SettingHandler.XML_ATTRIBUTE_KEYFIELD, match.attributeValue(SettingHandler.XML_ATTRIBUTE_KEYFIELD));
						data.addAttribute(SettingHandler.XML_ATTRIBUTE_FIELD, match.attributeValue(SettingHandler.XML_ATTRIBUTE_FIELD));
						data.addText(result);
						}
					}
					
					}
					this.getParser().reset();
				}
				
		}
		
		}
		
	public static void main(String[] agrs) throws Exception {
		//ExtractorTemplate ex = new ExtractorTemplate();	
		//ex.initialize(new File("D:/Project/CMS3/extracts/extracts.xml"), "D:/Project/CMS3/extracts/", "D:/Project/CMS3/jobs/u-house.com.cn-20090213022257500/mirror/", "D://");
		//ex.startExtract();
/*		ExtractorTemplate ex = new ExtractorTemplate();	
		ex.setExtractFile(new File("D:/Project/CMS3/extracts/extracts.xml"));
		ex.setOutputPath("D:/Project/CMS3/extracts/");*/
		//traverse(ex, new File("D:\\test.html"));
	}

}

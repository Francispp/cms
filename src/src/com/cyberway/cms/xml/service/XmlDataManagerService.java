package com.cyberway.cms.xml.service;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.cyberway.cms.Constants;
import com.cyberway.cms.xml.domain.XmlData;
import com.cyberway.core.dao.HibernateEntityDao;

public class XmlDataManagerService extends HibernateEntityDao<XmlData>{
	public String writeXML(String fileName,Document document) throws Exception
	{
		File file;
		if(Constants.IS_REALPATH)
		{
		 file = new File(Constants.ABSOLUTE_PATH+Constants.XML_PATH+fileName);
		}
		else{
			file = new File(ServletActionContext.getServletContext().getRealPath(Constants.XML_PATH+fileName));
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		OutputFormat format = new OutputFormat();
		format.setIndent(true);
		format.setNewlines(true);
		XMLWriter writer = new XMLWriter(outputStream, format);
		writer.write(document);
		return "";
	}

}

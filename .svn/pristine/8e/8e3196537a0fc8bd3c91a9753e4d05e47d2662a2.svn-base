package com.cyberway.common.utils;

import java.io.File;

import javax.servlet.ServletContext;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.web.context.ServletContextAware;

public class ServletContextDocumentFactory extends AbstractFactoryBean implements ServletContextAware
{
	private ServletContext _servletContext;
	private String _path;

	public void setServletContext(ServletContext servletContext)
	{
		_servletContext = servletContext;
	}

	public String getPath()
	{
		return _path;
	}

	public void setPath(String path)
	{
		_path = path;
	}
	
	@Override
	protected Object createInstance() throws Exception
	{
		return new SAXReader ().read(new File (_servletContext.getRealPath(getPath())));
	}
	
	public Class getObjectType()
	{
		return Document.class;
	}
}

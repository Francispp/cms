package com.cyberway.common.utils;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.web.context.ServletContextAware;

import com.cyberway.cms.Constants;

public class ServletContextFileFactory extends AbstractFactoryBean implements ServletContextAware
{
	private ServletContext _servletContext;
	private String _path;
	private boolean _isRealPath;//指定path是否为绝对路径(真实路径) 
	public boolean getIsRealPath(){
		return _isRealPath;
	}
	public void setIsRealPath(boolean isRealPath){
		_isRealPath=isRealPath;
	}

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
		//模板路径，使用是否为相对路径设置方法
		if(Constants.IS_REALPATH && getPath().equals("/templates/"))
		{
			return new File (Constants.ABSOLUTE_PATH+getPath());
		}
		else{
			if(_isRealPath)//相对路径，直接创建
			   return new File (getPath());
			else
			   return new File (_servletContext.getRealPath(getPath()));//在当前web运行下
		}
		
	}
	
	public Class getObjectType()
	{
		return File.class;
	}
}

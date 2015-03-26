package com.cyberway.common.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.cyberway.cms.Constants;

public class DynamicPageService implements InitializingBean, ServletContextAware
{
	class PageCacheKey 
	{
		private String _body;
		private String _encoding;
		
		public String getBody()
		{
			return _body;
		}
		
		public void setBody(String body)
		{
			_body = body;
		}
		
		public String getEncoding()
		{
			return _encoding;
		}
		
		public void setEncoding(String encoding)
		{
			_encoding = encoding;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof PageCacheKey == false) 
			{
			     return false;
		   }
		   if (this == obj) 
		   {
		     return true;
		   }

		   PageCacheKey key = (PageCacheKey)obj;
		   
		   return new EqualsBuilder().append(getBody(), key.getBody()).append(getEncoding(), key.getEncoding()).isEquals();
		}
		
		@Override
		public int hashCode()
		{
			return new HashCodeBuilder ().append(getBody()).append(getEncoding()).toHashCode();
		}
	}
	
	public static final String JSP_EXTENSIONS = ".jsp";
	public static final String DEFAULT_PAGE_ENCODING = "UTF-8";
	public static final String HTML_EXTENSIONS = ".html";
	private int _seed = 0;
	private File _storeDir;
	private ServletContext _servletContext;
	private Map<PageCacheKey, String> _cached = new HashMap<PageCacheKey, String> ();
	
	public File getStoreDir()
	{
		return _storeDir;
	}

	public void setStoreDir(File storeDir)
	{
		_storeDir = storeDir;
	}

	public ServletContext getServletContext()
	{
		return _servletContext;
	}

	public void setServletContext(ServletContext servletContext)
	{
		_servletContext = servletContext;
	}

	public String getPage(String body)
	{
		return getPage (body, DEFAULT_PAGE_ENCODING);
	}

	public synchronized String getPage (String body, String encoding)
	{
		Validate.notNull(body);
		Validate.notNull(encoding);
		
		PageCacheKey key = new PageCacheKey ();
		key.setBody(body);
		key.setEncoding(encoding);
		
		if (!_cached.containsKey(key))
		{
			File file = null;
			
			do
			{
				_seed++;
				String path = FilenameUtils.concat(getStoreDir().getAbsolutePath(), String.valueOf(_seed) + JSP_EXTENSIONS);
				file = new File (path);
			}
			while (file.exists());
			
			try
			{
				FileUtils.writeStringToFile(file, body, encoding);
			}
			catch (Exception e)
			{
				throw new RuntimeException (e);
			}
			String virtualPath=null;
			//若使用绝对地址
			if(Constants.IS_REALPATH){
			 virtualPath = file.getAbsolutePath().substring(Constants.ABSOLUTE_PATH.length());
			}else
			 virtualPath = file.getAbsolutePath().substring(getServletContext().getRealPath("/").length());
			virtualPath = "/" + FilenameUtils.separatorsToUnix(virtualPath);
			_cached.put(key, virtualPath);
		} 
		
		return _cached.get(key);
	}
	
	public synchronized String getPage(String body, String encoding, File file)
	{
		Validate.notNull(body);
		Validate.notNull(encoding);
		Validate.notNull(file);
		
		PageCacheKey key = new PageCacheKey ();
		key.setBody(body);
		key.setEncoding(encoding);
		
		try
		{
			FileUtils.writeStringToFile(file, body, encoding);
		}
		catch (IOException ex)
		{
			throw new RuntimeException (ex);
		}
		String virtualPath=null;
		//若使用绝对地址
		if(Constants.IS_REALPATH){
		 virtualPath = file.getAbsolutePath().substring(Constants.ABSOLUTE_PATH.length());
		}else
		 virtualPath = file.getAbsolutePath().substring(getServletContext().getRealPath("/").length());
		virtualPath = "/" + FilenameUtils.separatorsToUnix(virtualPath);
		
		return virtualPath;
	} 

	public void afterPropertiesSet() throws Exception
	{
		/*String realPath=null;
		if(Constants.IS_REALPATH){
			realPath=Constants.ABSOLUTE_PATH;
		}else
			realPath=getServletContext().getRealPath("/");
		Validate.isTrue(getStoreDir().getAbsolutePath().startsWith(realPath));*/
		Validate.isTrue(getStoreDir().getAbsolutePath().startsWith(getServletContext().getRealPath("/")));
		
		if (!getStoreDir().exists())
		{
			getStoreDir().mkdirs();
		}
		else
		{
			for (File file : (Collection<File>)FileUtils.listFiles(getStoreDir(), new SuffixFileFilter (JSP_EXTENSIONS), null))
			{
				file.delete();
			}
		}
	}
}

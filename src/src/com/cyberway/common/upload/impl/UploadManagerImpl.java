package com.cyberway.common.upload.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.web.context.ServletContextAware;

import com.cyberway.common.upload.UploadManager;

public class UploadManagerImpl implements UploadManager, ServletContextAware
{
	private String _uploadPath = "/upload/";
	private ServletContext _servletContext;
	private int _fileNameLength = 10;

	public String getUploadPath()
	{
		return _uploadPath;
	}

	public void setUploadPath(String uploadPath)
	{	
		_uploadPath = uploadPath;
	}

	public int getFileNameLength()
	{
		return _fileNameLength;
	}

	public void setFileNameLength(int fileNameLength)
	{
		_fileNameLength = fileNameLength;
	}

	public ServletContext getServletContext()
	{
		return _servletContext;
	}

	public void setServletContext(ServletContext servletContext)
	{
		_servletContext = servletContext;
	}
	
	public String upload (InputStream inputStream, String rawFileName)
	{
		return upload (getUploadPath(), inputStream, rawFileName);
	}

	public String upload(String path, InputStream inputStream, String rawFileName)
	{
		Validate.notNull(inputStream);
		Validate.notNull(path);

		String fileName = null;
		FileOutputStream outputStream = null;
		String extension = rawFileName == null ? StringUtils.EMPTY : FilenameUtils.getExtension(rawFileName);
		if (!StringUtils.isEmpty(extension))
		{
			extension = "." + extension;
		}
		
		try
		{
			File file = new File (getServletContext().getRealPath(path));
			if (!file.exists())
			{
				FileUtils.forceMkdir(file);
			}

			do
			{
				fileName = RandomStringUtils.randomNumeric(getFileNameLength()) + extension;
				file = new File (FilenameUtils.concat(file.getPath(), fileName));
			}
			while (file.exists());

			file.createNewFile();
			outputStream = new FileOutputStream (file.getPath());
			IOUtils.copy(inputStream, outputStream);
			
			return path + fileName;
		}
		catch (Exception e)
		{
			throw new RuntimeException (e);
		}
		finally
		{
			IOUtils.closeQuietly(outputStream);
		}
	}
	
	public String upload(String path, InputStream inputStream)
	{
		return upload (path, inputStream, null);
	}
	
	public String upload(InputStream inputStream)
	{
		return upload(inputStream, null);
	}
}
package com.cyberway.common.webFilter.filters;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class DownloadFilter implements HandlerFilter
{
	private File _storeDir;
	private Map<String, String> _headers = new HashMap<String, String> ();
	private boolean _overwrite;
	private int _timeout;
	private Log _log = LogFactory.getLog(DownloadFilter.class);
	
	public File getStoreDir()
	{
		return _storeDir;
	}

	public void setStoreDir(File storeDir)
	{
		_storeDir = storeDir;
	}

	public Map<String, String> getHeaders()
	{
		return _headers;
	}

	public void setHeaders(Map<String, String> headers)
	{
		_headers = headers;
	}

	public boolean isOverwrite()
	{
		return _overwrite;
	}

	public void setOverwrite(boolean overwrite)
	{
		_overwrite = overwrite;
	}

	public int getTimeout()
	{
		return _timeout;
	}

	public void setTimeout(int timeout)
	{
		_timeout = timeout;
	}

	public void process(Object source, Handler handler)
	{
		if (source != null)
		{
			if (!getStoreDir().exists())
			{
				getStoreDir().mkdirs();
			}
			
			HttpMethod method = null;
			String url = ObjectUtils.toString(source);
			String fileName = FilenameUtils.concat(getStoreDir().getAbsolutePath(), FilenameUtils.getName(url)); 
			
			try
			{
				HttpClient client = new HttpClient ();
				client.getHttpConnectionManager().getParams().setConnectionTimeout(getTimeout());
				method = new GetMethod (url);
				
				if (!MapUtils.isEmpty(getHeaders()))
				{
					for (Entry<String, String> pair : getHeaders().entrySet())
					{
						method.addRequestHeader(new Header (pair.getKey(), pair.getValue()));
					}
				}
				client.executeMethod(method);
				
				File file = new File (fileName);
				if (!file.exists() || isOverwrite())
				{
					FileUtils.writeByteArrayToFile (file, IOUtils.toByteArray(method.getResponseBodyAsStream()));
				
					_log.info("successed download from '" + url + "' to '" + file.getAbsolutePath() + "'");
				}
			}
			catch (Exception e)
			{
				_log.error("cann't download from '" + url + "' to '" + fileName + "'", e);
			}
			finally
			{
				if (method != null)
				{
					method.releaseConnection();
				}
			}
		}
		
		handler.process(source);
	}
}

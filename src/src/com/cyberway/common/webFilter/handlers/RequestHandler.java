package com.cyberway.common.webFilter.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;

public class RequestHandler extends AbstractHandler
{
	private Map<String, String> _headers = new HashMap<String, String> ();
	private int _timeout = 30;
	
	public Map<String, String> getHeaders()
	{
		return _headers;
	}

	public void setHeaders(Map<String, String> headers)
	{
		_headers = headers;
	}

	public int getTimeout()
	{
		return _timeout;
	}

	public void setTimeout(int timeout)
	{
		_timeout = timeout;
	}

	@Override
	public void process(Object source)
	{
		Validate.notNull(source);
		
		HttpMethod method = null;
		
		try
		{
			String url = ObjectUtils.toString(source);
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
			
			super.process(method.getResponseBodyAsString()); 
		}
		catch (Exception e)
		{
			throw new RuntimeException (e);
		}
		finally
		{
			if (method != null)
			{
				method.releaseConnection();
			}
		} 
		super.process(source);
	}
}

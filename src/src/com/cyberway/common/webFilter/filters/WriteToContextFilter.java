package com.cyberway.common.webFilter.filters;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class WriteToContextFilter implements HandlerFilter
{
	private String _key;
	
	public String getKey()
	{
		return _key;
	}

	public void setKey(String key)
	{
		_key = key;
	}

	public void process(Object source, Handler handler)
	{
		handler.getContext().put(getKey(), source);
	}
}

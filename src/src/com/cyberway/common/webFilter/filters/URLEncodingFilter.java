package com.cyberway.common.webFilter.filters;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.ObjectUtils;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class URLEncodingFilter implements HandlerFilter
{
	public void process(Object source, Handler handler)
	{
		try
		{	
			if (source != null)
			{
				String url = ObjectUtils.toString(source);
				StringBuilder builder = new StringBuilder ();
				URLCodec codec = new URLCodec ();
				for (int index = 0; index < url.length(); index++)
				{
					if (!CharUtils.isAscii(url.charAt(index)))
					{
						builder.append(codec.encode(Character.toString(url.charAt(index))));
					}
					else
					{
						builder.append(url.charAt(index));
					}
				}
				
				source = builder.toString();
			}
			
			handler.process(source);
		}
		catch (Exception e)
		{
			throw new RuntimeException (e);
		}
	}
}

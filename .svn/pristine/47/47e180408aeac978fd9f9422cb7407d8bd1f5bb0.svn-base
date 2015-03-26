package com.cyberway.common.webFilter.filters;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class StringEscapeFilter implements HandlerFilter
{
	public void process(Object source, Handler handler)
	{
		if (source != null)
		{
			source = StringEscapeUtils.escapeJava(ObjectUtils.toString(source));
		}
		
		handler.process(source);
	}
}

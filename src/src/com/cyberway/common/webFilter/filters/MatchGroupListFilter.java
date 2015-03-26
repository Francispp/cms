package com.cyberway.common.webFilter.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.lang.Validate;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class MatchGroupListFilter implements HandlerFilter
{
	private int _index = 1;
	
	public int getIndex()
	{
		return _index;
	}

	public void setIndex(int index)
	{
		_index = index;
	}

	public void process(Object source, Handler handler)
	{
		Validate.notNull(source);
		
		Matcher matcher = (Matcher)source;
		List<String> target = new ArrayList<String> ();
		while (matcher.find())
		{
			target.add(matcher.group(getIndex()));
		}
		
		handler.process(target);
	}
}

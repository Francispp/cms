package com.cyberway.common.webFilter.filters;

import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class MatcherFilter implements HandlerFilter
{
	private String _regex;
	private int _flag = Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE;
	
	public int getFlag()
	{
		return _flag;
	}
	
	public void setFlag(int flag)
	{
		_flag = flag;
	}
	
	public String getRegex()
	{
		return _regex;
	}
	
	public void setRegex(String regex)
	{
		_regex = regex;
	}
	
	public void process(Object source, Handler handler)
	{
		if (source != null)
		{
			Validate.notNull(getRegex());
			
			handler.process(Pattern.compile(getRegex(), getFlag()).matcher(ObjectUtils.toString(source)));
		}
		else
		{
			handler.process(source);
		}
	}
}

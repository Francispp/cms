package com.cyberway.common.webFilter.filters;

import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class ReplaceFilter implements HandlerFilter
{
	private String _regex;
    private String _replacement;
    private int _flag = Pattern.DOTALL | Pattern.MULTILINE;
    
	public String getRegex()
	{
		return _regex;
	}

	public void setRegex(String regex)
	{
		_regex = regex;
	}

	public String getReplacement()
	{
		return _replacement;
	}

	public void setReplacement(String replacement)
	{
		_replacement = replacement;
	}

	public void process(Object source, Handler handler)
	{
		if (source != null && getRegex() != null && getReplacement() != null)
		{
			source = Pattern.compile(getRegex(), _flag).matcher(ObjectUtils.toString(source)).replaceAll(getReplacement());
		}
		
		handler.process(source);
	}
}

package com.cyberway.common.webFilter.filters;

import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class StringReplaceFilter implements HandlerFilter
{
	private String _regex;
    private String _replacement;
    private int _flag = Pattern.DOTALL | Pattern.MULTILINE;
    
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
		if (source != null)
		{
			Validate.notNull(getRegex());
			Validate.notNull(getReplacement());
			
			source = Pattern.compile(getRegex(), getFlag()).matcher(ObjectUtils.toString(source)).replaceAll(getReplacement());
		}
		
		handler.process(source);
	}
}

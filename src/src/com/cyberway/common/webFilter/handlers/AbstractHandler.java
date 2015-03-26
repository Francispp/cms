package com.cyberway.common.webFilter.handlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class AbstractHandler implements Handler
{
	private List<HandlerFilter> _filters;
	private int _current = -1;
	private Map<String, Object> _context = new LinkedHashMap<String, Object> ();
	
	public List<HandlerFilter> getFilters()
	{
		return _filters;
	}

	public void setFilters(List<HandlerFilter> filters)
	{
		_filters = filters;
	}
	
	public Map<String, Object> getContext()
	{
		return _context;
	}
	
	public void process(Object source)
	{
		_current++;
		
		if (!CollectionUtils.isEmpty(getFilters()) && _current < getFilters().size())
		{
			getFilters().get(_current).process (source, this);
		}
		
		_current = -1;
	}
}	

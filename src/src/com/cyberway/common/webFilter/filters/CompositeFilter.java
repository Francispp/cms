package com.cyberway.common.webFilter.filters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class CompositeFilter implements HandlerFilter
{
	private List<HandlerFilter> _filters = new ArrayList<HandlerFilter> ();
	private int _current = -1;
	
	public List<HandlerFilter> getFilters()
	{
		return _filters;
	}

	public void setFilters(List<HandlerFilter> filters)
	{
		_filters = filters;
	}

	public void process(Object source, Handler transformer)
	{
		if (!CollectionUtils.isEmpty(getFilters()))
		{
			_current++;
			
			for (HandlerFilter filter : getFilters())
			{
				filter.process(source, transformer);
				_current--;
			}
		}
	}
}

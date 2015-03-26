package com.cyberway.common.webFilter.filters;

import com.cyberway.common.webFilter.Handler;
import com.cyberway.common.webFilter.HandlerFilter;

public class DispatchFilter implements HandlerFilter
{
	private Handler _target;

	public Handler getTarget()
	{
		return _target;
	}

	public void setTarget(Handler target)
	{
		_target = target;
	}
	
	public void process(Object source, Handler handler)
	{
//		ListCoercion coercion = new ListCoercion ();
//		coercion.setNullToEmpty(true);
//		
//		for (Object item : coercion.coerce(source))
//		{
//			getTarget().process(item);
//		}
		
		handler.process(source);
	}
}

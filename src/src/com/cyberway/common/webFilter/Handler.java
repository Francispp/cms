package com.cyberway.common.webFilter;

import java.util.Map;

public interface Handler
{
	Map<String, Object> getContext ();
	void process (Object source);
}

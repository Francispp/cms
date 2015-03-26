package com.cyberway.common.internal;

import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.InitializingBean;

import com.cyberway.common.service.ScriptService;

public class ScriptServiceImpl implements ScriptService, InitializingBean
{
	private ScriptEngineManager _engineManager;
	private Map<String, Object> _defaultContext;
	private String _defaultEngine = "javascript";
	
	public Map<String, Object> getDefaultContext()
	{
		return _defaultContext;
	}

	public void setDefaultContext(Map<String, Object> defaultContext)
	{
		_defaultContext = defaultContext;
	}
	
	public String getDefaultEngine()
	{
		return _defaultEngine;
	}

	public void setDefaultEngine(String defaultEngine)
	{
		_defaultEngine = defaultEngine;
	}

	protected ScriptEngineManager getEngineManager ()
	{
		return _engineManager;
	}

	public ScriptEngine getEngine ()
	{
		return getEngine(getDefaultEngine());
	}
	
	public ScriptEngine getEngine (String name)
	{
		return getEngineManager().getEngineByName(name);
	}

	public void afterPropertiesSet() throws Exception 
	{
		_engineManager = new ScriptEngineManager ();
		
		if (!MapUtils.isEmpty(getDefaultContext()))
		{
			for (Entry<String, Object> pair : getDefaultContext().entrySet())
			{
				_engineManager.put(pair.getKey(), pair.getValue());
			}
		}
	}
}

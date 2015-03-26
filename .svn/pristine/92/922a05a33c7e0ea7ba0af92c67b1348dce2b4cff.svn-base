package com.cyberway.staticer.gather;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.cyberway.staticer.Configuration;

/**
 * 变量采集器<br />
 * 组件可以使用该对象方便的采集变量的值范围
 * @author helfen
 *
 */
public class VariableCollector
{
	private HttpServletResponse _response;
	
	public VariableCollector(HttpServletResponse response)
	{
		_response = response;
	}
	
	/**
	 * 采集指定变量的值范围
	 * @param name
	 * @param values
	 */
	public void collect (String name, String...values)
	{
		try 
		{
			_response.getWriter().write(String.format("%s%s=%s%s", 
					Configuration.headerVarPrefix(), 
					name, 
					JSONArray.fromObject(values).toString(),
					Configuration.headerVarSuffix()));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 当前请求是否为采集变量请求
	 * @param request
	 * @return
	 */
	public static boolean isGatherPhase (HttpServletRequest request)
	{
		return request.getHeader(Configuration.headerGahterPhase()) != null;
	}
}

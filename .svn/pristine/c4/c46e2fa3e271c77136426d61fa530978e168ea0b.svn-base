package com.cyberway.staticer.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

/**
 * 常用帮助类
 * @author helfen
 *
 */
public class StaticerUtils
{
	/**
	 * 把URL地址转换成URI对象
	 * @param url
	 * @return
	 */
	public static URI toURI (String url)
	{
		try
		{
			return new URI(url);
		}
		catch (URISyntaxException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 解释查询字符串为 Map，其中key为参数名,value为参数值
	 * @param queryString
	 * @return
	 */
	public static Map<String, String> parseQuery (String queryString)
	{
		Map<String, String> parameters = new HashMap<String, String> ();
		
		if (!StringUtils.isBlank(queryString))
		{
			for (String pairString : StringUtils.split(queryString, "&"))
			{	
				if (pairString.contains("="))
				{
					String[] keyValue = pairString.split("=");
					String value = keyValue.length==1 ? "" : keyValue[1];
					parameters.put(keyValue[0], value);
				}
				else
				{
					parameters.put(pairString, StringUtils.EMPTY);
				}
			}
		}
		
		return parameters;
	}
	
	/**
	 * 把 parameters 转换成查询字符串
	 * @param parameters
	 * @return
	 */
	public static String makeQueryString (Map<String, String> parameters)
	{
		StringBuilder queryString = new StringBuilder();
		
		if (parameters != null)
		{
			for (Entry<String, String> pair : parameters.entrySet())
			{
				if (queryString.length() > 0)
				{
					queryString.append("&");
				}
				
				queryString.append(pair.getKey()).append("=").append(pair.getValue());
			}
		}
		
		return queryString.toString();
	}
	
	/**
	 * 在 baseURL 的基础上合并参数 parameters
	 * @param baseURL
	 * @param parameters
	 * @return
	 */
	public static URI mergeQuery (String baseURL, Map<String, String> parameters)
	{
		URI uri = toURI(baseURL);
		
		try
		{
			Map<String, String> allParameters = parseQuery(uri.getQuery());
			if (parameters != null)
			{
				allParameters.putAll(parameters);
			}
			
			return new URI ("http", null, uri.getHost(), uri.getPort(), uri.getPath(), makeQueryString(allParameters), null);
		}
		catch (URISyntaxException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 格式化采集异常信息
	 * @param url
	 * @param role
	 * @return
	 */
	public static String formatExceptionMessage (String url, String role)
	{
		return String.format("采集页面 '%s' 时发生错误，采集使用角色为 '%s'", url, role);
	}	
}

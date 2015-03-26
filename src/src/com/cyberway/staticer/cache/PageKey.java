package com.cyberway.staticer.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 该对象表示一个请求缓存键
 * @author helfen
 *
 */
public class PageKey implements Serializable
{
	private MultiKey _key;
	
	public PageKey(String host, String path, String role)
	{
		this (host, path, MapUtils.EMPTY_MAP, role);
	}
	
	public PageKey(String host, String path, Map<String, String> parameters, String role)
	{
		parameters = (Map<String, String>) ObjectUtils.defaultIfNull(parameters, MapUtils.EMPTY_MAP);
		
		_key = new MultiKey(host, path, orderedMap(parameters), role); 
	}

	/**
	 * 取得主机域名
	 * @return
	 */
	public String getHost ()
	{
		return (String)_key.getKey(0);
	}
	
	/**
	 * 取得请求服务路径
	 * @return
	 */
	public String getPath()
	{
		return (String)_key.getKey(1);
	}
	
	/**
	 * 取得请求参数
	 * @return
	 */
	public Map<String, String> getParameters()
	{
		return (Map<String, String>)_key.getKey(2);
	}
	
	/**
	 * 取得本次请求的用户所属角色
	 * @return
	 */
	public String getRole()
	{
		return (String)_key.getKey(3);
	}
	
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(_key.hashCode()).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) { return false; }
		
		PageKey rhs = (PageKey)obj;

		return new EqualsBuilder().append(_key, rhs._key).isEquals();
	}
	
	private static Map<String, String> orderedMap (Map<String, String> map)
	{
		Map<String, String> orderedMap = new ListOrderedMap();
		
		List<String> keyList = new ArrayList<String>(map.keySet());
		Collections.sort(keyList);
		
		for (String key : keyList)
		{
			orderedMap.put(key, map.get(key));
		}
		
		return orderedMap;
	}
}

package com.cyberway.staticer.gather;

import java.util.Map;


/**
 * 该接口表示一个页面发布器
 * @author helfen
 *
 */
public interface PagePublisher
{
	/**
	 * 发布指定的URL成静态页面（使用变量采集组合的方式）
	 * @param url
	 * @param roleT
	 */
	void publish (String url, String role);
	
	/**
	 * 指定变量的组成，结合 baseURL 批量发布页面
	 * @param baseURL
	 * @param variables
	 * @param role
	 */
	void publish (String baseURL, Map<String, String[]> variables, String role);
	
	/**
	 * 指定变量的组成，结合 baseURL 批量发布页面
	 * @param baseURL
	 * @param variables
	 * @param role
	 */
	void publish (String baseURL, Map<String, String[]> variables, String role, boolean gatherNested);
}

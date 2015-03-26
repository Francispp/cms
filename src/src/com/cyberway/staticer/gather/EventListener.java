package com.cyberway.staticer.gather;

/**
 * 该接口表示一个页面采集事件监听器
 * @author helfen
 *
 */
public interface EventListener
{
	/**
	 * 在成功采集完一个页面后
	 * @param url
	 * @param role
	 */
	void onSuccessed (String url, String role);
	
	/**
	 * 在采集页面失败时
	 * @param url
	 * @param role
	 */
	void onFailed (String url, String role);
}

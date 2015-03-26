package com.cyberway.staticer.cache;

/**
 * 该接口表示一个页面缓存事件监听器
 * @author helfen
 *
 */
public interface EventListener
{
	/**
	 * 在保存后发面
	 * @param key
	 * @param pageData
	 */
	void afterSave (PageKey key, byte[] pageData);
	
	void afterRemove (PageKey key);
	
	/*void afterflvSave(PageKey key,byte[] pageData);
	
	void afterflvRemove(PageKey key) ;*/
}

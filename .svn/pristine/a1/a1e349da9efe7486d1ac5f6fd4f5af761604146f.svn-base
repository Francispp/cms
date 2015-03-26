package com.cyberway.staticer.cache;

import java.util.Set;


/**
 * 该接口表示一个页面缓存
 * @author helfen
 *
 */
public interface PageCache
{
	/**
	 * 取得所有已经缓存的页面
	 * @return
	 */
	Set<PageKey> keySet ();
	
	/**
	 * 缓存中是否有该页面
	 * @param key
	 * @return
	 */
	boolean contains (PageKey key);
	
	/**
	 * 取得页面的缓存值（静态HTML文件的数据）
	 * @param key
	 * @return
	 */
	byte[] get (PageKey key);
	
	/**
	 * 把静态HTML文件的数据放到缓存中
	 * @param key
	 * @param pageStream
	 */
	void put (PageKey key, byte[] pageData);
	
	/**
	 * 删除页面缓存
	 * @param key
	 */
	void remove (PageKey key);
}

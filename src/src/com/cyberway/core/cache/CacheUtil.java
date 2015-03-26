package com.cyberway.core.cache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.Assert;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * @author caiw
 * 
 */
public class CacheUtil {
	private Log logger = LogFactory.getLog(getClass());

	private Cache localCache;

	String cacheServerList;

	String cacheServerWeights;

	boolean cacheCluster = false;

	int initialConnections = 10;

	int minSpareConnections = 5;

	int maxSpareConnections = 50;

	long maxIdleTime = 1000 * 60 * 30; // 30 minutes

	long maxBusyTime = 1000 * 60 * 5; // 5 minutes

	long maintThreadSleep = 1000 * 5; // 5 seconds

	int socketTimeOut = 1000 * 3; // 3 seconds to block on reads

	int socketConnectTO = 1000 * 3; // 3 seconds to block on initial
									// connections. If 0, then will use blocking
									// connect (default)

	boolean failover = false; // turn off auto-failover in event of server
								// down

	boolean nagleAlg = false; // turn off Nagle's algorithm on all sockets in
								// pool

	MemCachedClient mc;

	public CacheUtil(){
		mc = new MemCachedClient();
		mc.setCompressEnable(false);
	}
	/**
	 * 放入
	 * @param key
	 * @param obj
	 */
	public void put(String key, Object obj) {
		Assert.hasText(key);
		Assert.notNull(obj);
		Assert.notNull(localCache);
		if (this.cacheCluster) {
			mc.set(key, obj);
		} else {
			Element element = new Element(key, (Serializable) obj);
			localCache.put(element);
		}
	}
	/**
	 * 删除
	 * @param key
	 */
	public void remove(String key){
		Assert.hasText(key);
		Assert.notNull(localCache);
		if (this.cacheCluster) {
			mc.delete(key);
		}else{
			localCache.remove(key);
		}
	}
	/**
	 * 获得
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		Assert.hasText(key);
		Assert.notNull(localCache);
		Object rt = null;
		if (this.cacheCluster) {
			rt = mc.get(key);
		} else {
			Element element = null;
			try {
	            element = localCache.get(key);
	        } catch (CacheException cacheException) {
	            throw new DataRetrievalFailureException("Cache failure: "
	                    + cacheException.getMessage());
	        }
			if(element != null)
				rt = element.getValue();
		}
		return rt;
	}
	/**
	 * 判断是否存在
	 * @param key
	 * @return
	 */
	public boolean exist(String key){
		Assert.hasText(key);
		Assert.notNull(localCache);
		if (this.cacheCluster) {
			return mc.keyExists(key);
		}else{
			return this.localCache.isKeyInCache(key);
		}
	}
	private void init() {
		if (this.cacheCluster) {
			String[] serverlist = cacheServerList.split(",");
			Integer[] weights = this.split(cacheServerWeights);
			// initialize the pool for memcache servers
			SockIOPool pool = SockIOPool.getInstance();
			pool.setServers(serverlist);
			pool.setWeights(weights);
			pool.setInitConn(initialConnections);
			pool.setMinConn(minSpareConnections);
			pool.setMaxConn(maxSpareConnections);
			pool.setMaxIdle(maxIdleTime);
			pool.setMaxBusyTime(maxBusyTime);
			pool.setMaintSleep(maintThreadSleep);
			pool.setSocketTO(socketTimeOut);
			pool.setSocketConnectTO(socketConnectTO);
			pool.setNagle(nagleAlg);
			pool.setHashingAlg(SockIOPool.NEW_COMPAT_HASH);
			pool.initialize();
			logger.info("初始化memcached pool!");
		}
	}

	private void destory() {
		if (this.cacheCluster) {
			SockIOPool.getInstance().shutDown();
		}
	}

	private Integer[] split(String Source) {
		int iCount, iPos;
		String sTemp;
		Integer[] aSplit = null, t = null; // aSplit结果返回 t临时的
		sTemp = Source;
		iCount = 0;
		for (;;) {
			iPos = sTemp.indexOf(',');
			if (iPos < 0) // 直到没有分割的字符串，就退出
				break;
			else {
				if (iCount > 0)
					t = aSplit; // 第一次，不用拷贝数组
				iCount++;
				aSplit = new Integer[iCount]; // 新的数组，
				if (iCount > 1) { // 不是第一次，拷贝数组
					for (int i = 0; i < t.length; i++)
						aSplit[i] = t[i];
				}
				aSplit[iCount - 1] = Integer.parseInt(sTemp.substring(0, iPos));
				sTemp = sTemp.substring(iPos + 1); // 取余下的字符串
			}
		}
		return aSplit;
	}

	public void setCacheServerList(String cacheServerList) {
		this.cacheServerList = cacheServerList;
	}

	public void setCacheServerWeights(String cacheServerWeights) {
		this.cacheServerWeights = cacheServerWeights;
	}

	public void setCacheCluster(boolean cacheCluster) {
		this.cacheCluster = cacheCluster;
	}

	public void setLocalCache(Cache localCache) {
		this.localCache = localCache;
	}
}

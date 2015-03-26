package com.cyberway.core.cache.hibernate;

import java.util.Map;

import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.Timestamper;

import com.danga.MemCached.MemCachedClient;

/**
 * Memcached plugins for Hibernate
 * 
 * @author caiw
 * 
 */
public class MemCache implements Cache {
	private static final Log log = LogFactory.getLog(MemCache.class);

	private static final int SIXTY_THOUSAND_MS = 60000;

	MemCachedClient mc;
	String regionName;

	/**
	 * Creates a new Hibernate pluggable cache based on a cache name. <p/>
	 * 
	 * @param resourceCache The underlying EhCache instance to use.
	 */
	public MemCache(String regionName) {
		mc = new MemCachedClient();
		mc.setCompressEnable(false);
		this.regionName = regionName;
	}

	/**
	 * Gets a value of an element which matches the given key.
	 * 
	 * @param key
	 *            the key of the element to return.
	 * @return The value placed into the cache with an earlier put, or null if
	 *         not found or expired
	 * @throws CacheException
	 */
	public Object get(Object key) throws CacheException {
		if (log.isDebugEnabled()) {
			log.debug("key: " + key);
		}
		if (key == null) {
			return null;
		} else {
			Object rt = mc.get(key.toString());
			if (rt == null) {
				if (log.isDebugEnabled()) {
					log.debug("Element for " + key + " is null");
				}
				return null;
			} else {
				return rt;
			}
		}

	}

	public Object read(Object key) throws CacheException {
		return get(key);
	}

	/**
	 * Puts an object into the cache.
	 * 
	 * @param key
	 *            a key
	 * @param value
	 *            a value
	 * @throws CacheException
	 *             if the {@link CacheManager} is shutdown or another
	 *             {@link Exception} occurs.
	 */
	public void update(Object key, Object value) throws CacheException {
		put(key, value);
	}

	/**
	 * Puts an object into the cache.
	 * 
	 * @param key
	 *            a key
	 * @param value
	 *            a value
	 * @throws CacheException
	 *             if the {@link CacheManager} is shutdown or another
	 *             {@link Exception} occurs.
	 */
	public void put(Object key, Object value) throws CacheException {
		mc.set(key.toString(), value);
	}

	/**
	 * Removes the element which matches the key. <p/> If no element matches,
	 * nothing is removed and no Exception is thrown.
	 * 
	 * @param key
	 *            the key of the element to remove
	 * @throws CacheException
	 */
	public void remove(Object key) throws CacheException {
		mc.delete(key.toString());
	}

	/**
	 * Remove all elements in the cache, but leave the cache in a useable state.
	 * 
	 * @throws CacheException
	 */
	public void clear() throws CacheException {
		log.warn("cann't clear all items in memcached!");
		throw new CacheException("cann't clear all items in memcached!");
	}

	/**
	 * Remove the cache and make it unuseable.
	 * 
	 * @throws CacheException
	 */
	public void destroy() throws CacheException {

	}

	/**
	 * Calls to this method should perform there own synchronization. It is
	 * provided for distributed caches. Because EHCache is not distributed this
	 * method does nothing.
	 */
	public void lock(Object key) throws CacheException {
	}

	/**
	 * Calls to this method should perform there own synchronization. It is
	 * provided for distributed caches. Because EHCache is not distributed this
	 * method does nothing.
	 */
	public void unlock(Object key) throws CacheException {
	}

	/**
	 * Gets the next timestamp;
	 */
	public long nextTimestamp() {
		return Timestamper.next();
	}

	/**
	 * Returns the lock timeout for this cache.
	 */
	public int getTimeout() {
		// 60 second lock timeout
		return Timestamper.ONE_MS * SIXTY_THOUSAND_MS;
	}

	public String getRegionName() {
		return this.regionName;
	}

	/**
	 * Warning: This method can be very expensive to run. Allow approximately 1
	 * second per 1MB of entries. Running this method could create liveness
	 * problems because the object lock is held for a long period <p/>
	 * 
	 * @return the approximate size of memory ehcache is using for the
	 *         MemoryStore for this cache
	 */
	public long getSizeInMemory() {
		log.warn("cann't getSizeInMemory in memcached!");
		throw new CacheException("cann't getSizeInMemory in memcached!");
	}

	public long getElementCountInMemory() {
		log.warn("cann't getElementCountInMemory in memcached!");
		throw new CacheException("cann't getElementCountInMemory in memcached!");
	}

	public long getElementCountOnDisk() {
		log.warn("cann't getElementCountOnDisk in memcached!");
		throw new CacheException("cann't getElementCountOnDisk in memcached!");
	}

	public Map toMap() {
		log.warn("cann't toMap in memcached!");
		throw new CacheException("cann't toMap in memcached!");
	}

	public String toString() {
		return "MemCached(" + getRegionName() + ')';
	}

}
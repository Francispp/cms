package com.cyberway.core.cache.hibernate;

import java.util.Properties;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;
import org.hibernate.cache.Timestamper;

/**
 * cache provider for Hibernate
 * @author caiw
 *
 */
public class MemcachedProvider implements CacheProvider {

	public Cache buildCache(String name, Properties properties) throws CacheException {
		return new MemCache(name);
	}

	public boolean isMinimalPutsEnabledByDefault() {
		return false;
	}

	public long nextTimestamp() {
		return Timestamper.next();
	}

	public void start(Properties properties) throws CacheException {
	}

	public void stop() {
	}

}

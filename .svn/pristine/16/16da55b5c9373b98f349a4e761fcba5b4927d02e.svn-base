package com.cyberway.core.acegi.cache;

import java.util.List;

import com.cyberway.core.acegi.resource.ResourceDetails;


/**
 * 为 {@link org.springside.components.acegi.resource.CoreResource} 实例提供缓存.
 * @author cac
 *
 */
public interface ResourceCache {

	public ResourceDetails getResourceFromCache(String resString);
	
	public void putResourceInCache(ResourceDetails resourceDetails);
	
	public void removeResourceFromCache(String resString);
	
	public List getAllResources();

	public void removeAllResources();
	
}
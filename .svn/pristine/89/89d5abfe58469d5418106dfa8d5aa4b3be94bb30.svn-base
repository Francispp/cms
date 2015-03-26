package com.cyberway.cms.cache.domain;

import java.io.Serializable;

/**
 * com.cyberway.cms.cache.domain.CmsCache
 *
 * @author Janice Yang
 *
 * @createTime 2012-2-3 下午2:47:38 
 *
 * @Description:
 *
 */
public class CmsCache implements Serializable {
	/**
	 * 主键
	 */
	private Long	id;
	
	/**
	 * 缓存名称
	 */
	private String cacheName;

	/**
	 * 该缓存管理类定义在spring中的id
	 */
	private String	manageClassPath;

	/**
	 * 初始化改缓存的方法名
	 */
	private String	initMethodName;
	
	/**
	 * 获取所有缓存的方法名
	 */
	private String getAllMethodName;

	/**
	 * 获取单个缓存的方法名
	 */
	private String	getMethodName;
	
	/**
	 * 清除掉所有缓存的方法名
	 */
	private String	removeAllMethodName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getManageClassPath() {
		return manageClassPath;
	}

	public void setManageClassPath(String manageClassPath) {
		this.manageClassPath = manageClassPath;
	}

	public String getInitMethodName() {
		return initMethodName;
	}

	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}

	public String getRemoveAllMethodName() {
		return removeAllMethodName;
	}

	public void setRemoveAllMethodName(String removeAllMethodName) {
		this.removeAllMethodName = removeAllMethodName;
	}

	public String getGetMethodName() {
		return getMethodName;
	}

	public void setGetMethodName(String getMethodName) {
		this.getMethodName = getMethodName;
	}

	public String getCacheName() {
    	return cacheName;
    }

	public void setCacheName(String cacheName) {
    	this.cacheName = cacheName;
    }

	public String getGetAllMethodName() {
    	return getAllMethodName;
    }

	public void setGetAllMethodName(String getAllMethodName) {
    	this.getAllMethodName = getAllMethodName;
    }
}

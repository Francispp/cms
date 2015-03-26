package com.cyberway.cms.domain;

import java.util.Date;

/**
 * 页面采集记录
 * 
 * @author Frank
 *
 */
public class PagePublisherLog {
	private Long id;
	private String baseURL;
	private String ids;
	private String role;
	private Long urlKey;
	private Long successCount;
	private Long failCount;
	private Date time;
	
	public PagePublisherLog() {}
	
	public PagePublisherLog(String baseURL, String ids, String role, Long urlKey, Long successCount, Long failCount, Date time) {
	    super();
	    this.baseURL = baseURL;
	    this.ids = ids;
	    this.role = role;
	    this.urlKey = urlKey;
	    this.successCount = successCount;
	    this.failCount = failCount;
	    this.time = time;
    }

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getUrlKey() {
		return urlKey;
	}

	public void setUrlKey(Long urlKey) {
		this.urlKey = urlKey;
	}

	public Long getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
	}

	public Long getFailCount() {
		return failCount;
	}

	public void setFailCount(Long failCount) {
		this.failCount = failCount;
	}

}

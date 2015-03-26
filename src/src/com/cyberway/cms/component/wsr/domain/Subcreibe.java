package com.cyberway.cms.component.wsr.domain;

// default package

import java.util.Date;

/**
 * WebUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Subcreibe implements java.io.Serializable {
	
	private Long id;    //编号
	private Long userId;    //用户编号
	private String userName;    //用户名
	private String userEmail;    //邮箱地址
	private String siteName;    //站点名称
	private Long siteId;    //站点编号
	private Long channelId;    //频道编号
	private String channelName;    //频道名称
	private String remoteAddr;    //请求地址
	private Date subcreibeDate;    //订阅时间
	private Date lastSubcreibeDate;    //最后订阅时间 
	private Long locked;    //订阅状态
	private Long approved;    //审核状态
	

	/** default constructor */
	public Subcreibe() {
	}


	public Long getApproved() {
		return approved;
	}


	public void setApproved(Long approved) {
		this.approved = approved;
	}


	public Long getChannelId() {
		return channelId;
	}


	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}


	public String getChannelName() {
		return channelName;
	}


	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
 


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	} 

	public Long getLocked() {
		return locked;
	}


	public void setLocked(Long locked) {
		this.locked = locked;
	}


	public String getRemoteAddr() {
		return remoteAddr;
	}


	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}


	public Long getSiteId() {
		return siteId;
	}


	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}


	public String getSiteName() {
		return siteName;
	}


	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}


	


	public Date getSubcreibeDate() {
		return subcreibeDate;
	}


	public void setSubcreibeDate(Date subcreibeDate) {
		this.subcreibeDate = subcreibeDate;
	}


	public Date getLastSubcreibeDate() {
		return lastSubcreibeDate;
	}


	public void setLastSubcreibeDate(Date lastSubcreibeDate) {
		this.lastSubcreibeDate = lastSubcreibeDate;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
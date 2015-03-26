package com.cyberway.cms.domain;

import java.io.Serializable;
import java.util.Date;

public class CmsDistributionLog implements Serializable
{
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * ftp服务器id
	 */
	private Long ftpId;
	
	/**
	 * 分发资源名称
	 */
	private String name;
	/**
	 * 分发资源本地路径
	 */
	private String path;
	/**
	 * 资源ftp路径
	 */
	private String ftpPath;
	
	/**
	 * 最后上传时间
	 */
	private Date lastTime;
	
	/**
	 * 分发状态
	 */
	private String status;
	/**
	 * 分发类型
	 */
	private String type;
	
	private String typeName;
	/**
	 * 站点id
	 */
	private Long siteId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
			this.status = status;	
	}
	public String getFtpPath() {
		return ftpPath;
	}
	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getFtpId() {
    	return ftpId;
    }
	public void setFtpId(Long ftpId) {
    	this.ftpId = ftpId;
    }
}

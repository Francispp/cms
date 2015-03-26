package com.cyberway.cms.component.docShare.domain;

import java.io.Serializable;
import java.util.Date;

public class DocShareRecord implements Serializable{
	private Long oid; 
	private String userName;
	private Long userId;
	private Date updateTime;  //最后操作时间

	private Long baseSiteId;//源站点id
	private String baseSiteName;//源站名
	private Long baseChannelId;//源频道id
	private String baseChannelName;//源频道名
	private Long baseDocId;//源文档ID
	private Long targetSiteId;//目标站点id
	private String targetSiteName;//目标站点名
	private Long targetChannelId;//目标频道id
	private String targetChannelName;//目标频道名
	private Long targetDocId;//目标文档id
	private int isDefault=0;//是否默认
	private Long relationId;
	public Long getRelationId() {
		return relationId;
	}
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	public Long getBaseChannelId() {
		return baseChannelId;
	}
	public void setBaseChannelId(Long baseChannelId) {
		this.baseChannelId = baseChannelId;
	}
	public String getBaseChannelName() {
		return baseChannelName;
	}
	public void setBaseChannelName(String baseChannelName) {
		this.baseChannelName = baseChannelName;
	}
	public Long getBaseSiteId() {
		return baseSiteId;
	}
	public void setBaseSiteId(Long baseSiteId) {
		this.baseSiteId = baseSiteId;
	}
	public String getBaseSiteName() {
		return baseSiteName;
	}
	public void setBaseSiteName(String baseSiteName) {
		this.baseSiteName = baseSiteName;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Long getTargetChannelId() {
		return targetChannelId;
	}
	public void setTargetChannelId(Long targetChannelId) {
		this.targetChannelId = targetChannelId;
	}
	public String getTargetChannelName() {
		return targetChannelName;
	}
	public void setTargetChannelName(String targetChannelName) {
		this.targetChannelName = targetChannelName;
	}
	public Long getTargetSiteId() {
		return targetSiteId;
	}
	public void setTargetSiteId(Long targetSiteId) {
		this.targetSiteId = targetSiteId;
	}
	public String getTargetSiteName() {
		return targetSiteName;
	}
	public void setTargetSiteName(String targetSiteName) {
		this.targetSiteName = targetSiteName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public Long getBaseDocId() {
		return baseDocId;
	}
	public void setBaseDocId(Long baseDocId) {
		this.baseDocId = baseDocId;
	}
	public Long getTargetDocId() {
		return targetDocId;
	}
	public void setTargetDocId(Long targetDocId) {
		this.targetDocId = targetDocId;
	}
	
}

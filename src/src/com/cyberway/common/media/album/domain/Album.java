package com.cyberway.common.media.album.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.cyberway.common.media.video.domain.Video;

public class Album {
	private Long id;

	/**
	 * 站点id
	 */
	private Long siteId;

	/**
	 * 视频专辑标题
	 */
	private String title;

	/**
	 * 视频专辑描述
	 */
	private String discribe;

	/**
	 * 封面图片路径
	 */
	private String imagePath;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人编号
	 */
	private Long modifUserId;
	/**
	 * 修改人名称
	 */
	private String modifUserName;

	/**
	 * 最后修改时间
	 */
	private Date lastModifTime = new Date();

	/**
	 * 父节点
	 */
	private Long pid;
	/**
	 * 媒体类型
	 */
	private String mediaType;// video doc img avdio
	/**
	 * 是否添加水印
	 */
	private Long waterMark = 0l;
	//是否为默认专辑 0 否 1是
	private Long isdefault=0l;
	
	public Long getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Long isdefault) {
		this.isdefault = isdefault;
	}

	public Long getWaterMark() {
		return waterMark;
	}

	public void setWaterMark(Long waterMark) {
		this.waterMark = waterMark;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscribe() {
		return discribe;
	}

	public void setDiscribe(String discribe) {
		this.discribe = discribe;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifUserId() {
		return modifUserId;
	}

	public void setModifUserId(Long modifUserId) {
		this.modifUserId = modifUserId;
	}

	public String getModifUserName() {
		return modifUserName;
	}

	public void setModifUserName(String modifUserName) {
		this.modifUserName = modifUserName;
	}

	public Date getLastModifTime() {
		return lastModifTime;
	}

	public void setLastModifTime(Date lastModifTime) {
		this.lastModifTime = lastModifTime;
	}

}

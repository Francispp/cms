package com.cyberway.common.media.audio.domain;

import java.util.Date;

import com.cyberway.cms.Constants;
import com.cyberway.common.login.edu.Des;
import com.cyberway.core.utils.StringUtil;

public class Audio {
	private Long id;
	/**
	 * 所属站点id
	 */
	private Long siteId;

	/**
	 * 音频标题
	 */
	private String title;

	/**
	 * 音频描述
	 */
	private String discribe;

	/**
	 * 音频相对路径
	 */
	private String filePath;

	/**
	 * 音频名称
	 */
	private String fileName;

	/**
	 * 音频封面
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
	 * 评论统计
	 */
	private Long commentCount = 0l;
	/**
	 * 播放次数
	 */
	private Long playCount = 0l;
	/**
	 * 浏览数
	 */
	private Long viewCount = 0l;
	/**
	 * 所属专辑id
	 */
	private Long albumId;

	/**
	 * 是否置顶的标志:0为否,1为是
	 */
	private Boolean topFlag = false;

	/**
	 * 文件格式、方便将来做视频格式转换
	 */
	private String format;
	/**
	 * 是否分发
	 */
	private Long issue = 0l;

	private String serverURL;//服务器的路径
	private String fullFilePath;
	public String getServerURL() {
		serverURL=Constants.MEDIAFILE_PATH;
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	public String getFullFilePath() {
		fullFilePath=filePath;
		return fullFilePath;
	}

	public void setFullFilePath(String fullFilePath) {
		this.fullFilePath = fullFilePath;
	}
	
	public Long getIssue() {
		return issue;
	}

	public void setIssue(Long issue) {
		this.issue = issue;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Long getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Long playCount) {
		this.playCount = playCount;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public Boolean getTopFlag() {
		return topFlag;
	}

	public void setTopFlag(Boolean topFlag) {
		this.topFlag = topFlag;
	}

	public String getFormat() {
		String path = this.getFilePath();
		if (StringUtil.isEmpty(path)) {
			path = this.getFilePath();
		}
		if (!StringUtil.isEmpty(path) && path.indexOf(".") > 0) {
			format = path.substring(path.lastIndexOf(".") + 1, path.length());
		} else {
			format = "未知格式";
		}
		return format.toLowerCase();
	}

	public void setFormat(String format) {
		this.format = format;
	}


}

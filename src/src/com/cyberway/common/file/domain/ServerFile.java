package com.cyberway.common.file.domain;

import java.io.Serializable;
import java.util.Date;

public class ServerFile implements Serializable {
	private static final long serialVersionUID = -8245121244419574286L;
	
	public static int UPLOADER_TYPE_INNER = 1;
	public static int UPLOADER_TYPE_WEB = 2;

	private Long id;
	private String channelId;
	private String docId;
	private String sourceName;
	private String path;
	private String url;
	private Date updateTime;
	private String fileType;
	private String fieldName;
	private String fileExt;
	private Boolean hasSend;
	private Long uploaderId;
	private Integer uploaderType;

	public Integer getUploaderType() {
		return uploaderType;
	}

	public void setUploaderType(Integer uploaderType) {
		this.uploaderType = uploaderType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public Boolean getHasSend() {
		return hasSend;
	}

	public void setHasSend(Boolean hasSend) {
		this.hasSend = hasSend;
	}

	public Long getUploaderId() {
		return uploaderId;
	}

	public void setUploaderId(Long uploaderId) {
		this.uploaderId = uploaderId;
	}
}

package com.cyberway.cms.domain;

import java.io.Serializable;
import java.util.Date;

public class Template implements Serializable
{
	public static String[] TYPE_NAMES={"通用","表单","细览","概览","后台概览","首页","wap通用","wap首页","wap概览","wap细览"};
	public final static int TYPE_ANY = 0;
	public final static int TYPE_FORM = 1;
	public final static int TYPE_DETAILS = 2;
	public final static int TYPE_SUMMARY = 3;
	public final static int TYPE_ADMIN_SUMMARY = 4;
	public final static int TYPE_INDEX = 5;
	
	public final static int TYPE_ANY_WAP=6;
	public final static int TYPE_INDEX_WAP=7;
	public final static int TYPE_SUMMARY_WAP=8;
	public final static int TYPE_DETAILS_WAP=9;
	
	
	
	private Long id;
	private String name;
	private String description;
	private String body;
	private Boolean issued=true;
	private Boolean saveToDesigner = false;
	private Date timeCreated;
	private Date lastModified;
	private Integer type = TYPE_ANY;
	private Long site_id;
	private Long channel_id;
	private com.cyberway.cms.form.domain.CoreForm form;
	private String beforsavescript;
	private String aftersavescript;
	
	/**
	 * 是否静态采集(只针对"首页","细览","概览"模板)
	 */
	private Boolean isPublishStatic = true;
	
	
	
	
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Boolean getIssued() {
		return issued;
	}
	public void setIssued(Boolean issued) {
		this.issued = issued;
	}
	public Boolean getSaveToDesigner() {
		return saveToDesigner;
	}
	public void setSaveToDesigner(Boolean saveToDesigner) {
		this.saveToDesigner = saveToDesigner;
	}
	public Date getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypeName(){
		if(type==null)
			type=TYPE_ANY;
		return TYPE_NAMES[type];
	}
	public com.cyberway.cms.form.domain.CoreForm getForm() {
		return form;
	}
	public void setForm(com.cyberway.cms.form.domain.CoreForm form) {
		this.form = form;
	}
	public String getAftersavescript() {
		return aftersavescript;
	}
	public void setAftersavescript(String aftersavescript) {
		this.aftersavescript = aftersavescript;
	}
	public String getBeforsavescript() {
		return beforsavescript;
	}
	public void setBeforsavescript(String beforsavescript) {
		this.beforsavescript = beforsavescript;
	}
	public Long getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Long channel_id) {
		this.channel_id = channel_id;
	}
	public Long getSite_id() {
		return site_id;
	}
	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}
	public Boolean getIsPublishStatic() {
    	return isPublishStatic;
    }
	public void setIsPublishStatic(Boolean isPublishStatic) {
    	this.isPublishStatic = isPublishStatic;
    }
	
	
	
}

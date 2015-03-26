package com.cyberway.common.comtemplate.domain;

import java.io.Serializable;
import java.util.Date;

import com.cyberway.cms.domain.Template;
import com.cyberway.common.temLibrary.domain.TemLibrary;

public class ComTemplate implements Serializable{
	
	public String [] TYPE_NAMES={"通用","表单","细览","概览","后台概览","首页","wap通用","wap首页","wap概览","wap细览"};
	/*
	 * 数字代表字符串中的“通用”。。。。
	 */
	public final static int TYPE_ANY = 0;				    //共用模板
	public final static int TYPE_FORM = 1;                 //表单模板
	public final static int TYPE_DETAILS = 2;			   //细缆模板
	public final static int TYPE_SUMMARY = 3;			  //概览模板
	public final static int TYPE_ADMIN_SUMMARY = 4;		  //后台概览模板
	public final static int TYPE_INDEX = 5;				  //首页模板
	
	public final static int TYPE_ANY_WAP=6;				  //WAP共用模板
	public final static int TYPE_INDEX_WAP=7;			  //WAP首页模板
	public final static int TYPE_SUMMARY_WAP=8;			  //概览模板WAP
	public final static int TYPE_DETAILS_WAP=9;			  //细缆模板WAP
	
	
	
	private Long id;     			//序号
	private String name;   			//名称
	private String description;
	private String body;
	private Boolean issued=true;    //发布
	private Boolean saveToDesigner = false;
	private Date timeCreated;       //创建时间
	private Date lastModified; 	    //最后修改时间
	private Integer type = TYPE_ANY;   //模板类型
	private TemLibrary temLibrary;     //模板库ID
	private Long temLibraryId;
	private com.cyberway.cms.form.domain.CoreForm form;   
	private String beforsavescript;    //脚本保存前执行
	private String aftersavescript;	   //脚本保存后执行
	private String content;
	private ComTemplate _parent;
	private ComTemplate _adminSummaryComTemplate;
	private ComTemplate _detailsComTemplate;
	private ComTemplate _formComTemplate;
	private ComTemplate _summaryComTemplate;

	
	
	/**
	 * 是否静态采集(只针对"首页","细览","概览"模板)
	 */
	private Boolean isPublishStatic = true;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public TemLibrary getTemLibrary() {
		return temLibrary;
	}
	public void setTemLibrary(TemLibrary temLibrary) {
		this.temLibrary = temLibrary;
	}
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
	public Boolean getIsPublishStatic() {
    	return isPublishStatic;
    }
	public void setIsPublishStatic(Boolean isPublishStatic) {
    	this.isPublishStatic = isPublishStatic;
    }
	public Long getTemLibraryId() {
		return temLibraryId;
	}
	public void setTemLibraryId(Long temLibraryId) {
		this.temLibraryId = temLibraryId;
	}
	public ComTemplate getParent() {
		return _parent;
	}
	public void setParent(ComTemplate parent) {
		this._parent = parent;
	}
	public ComTemplate getAdminSummaryComTemplate() {
		return _adminSummaryComTemplate;
	}
	public void setAdminSummaryComTemplate(ComTemplate adminSummaryComTemplate) {
		this._adminSummaryComTemplate = adminSummaryComTemplate;
	}
	public ComTemplate getDetailsComTemplate() {
		return _detailsComTemplate;
	}
	public void setDetailsComTemplate(ComTemplate detailsComTemplate) {
		this._detailsComTemplate = detailsComTemplate;
	}
	public ComTemplate getFormComTemplate() {
		return _formComTemplate;
	}
	public void setFormComTemplate(ComTemplate formComTemplate) {
		this._formComTemplate = formComTemplate;
	}
	public ComTemplate getSummaryComTemplate() {
		return _summaryComTemplate;
	}
	public void setSummaryComTemplate(ComTemplate summaryComTemplate) {
		this._summaryComTemplate = summaryComTemplate;
	}

	
	
	
	
}


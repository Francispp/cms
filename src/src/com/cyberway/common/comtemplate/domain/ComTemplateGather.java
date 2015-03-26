package com.cyberway.common.comtemplate.domain;

import java.util.Date;

public class ComTemplateGather implements java.io.Serializable{
	private Long oid;
	private Long temLibraryId;
	private Long comtemplateId;
	private Long includeTemLibraryId;   //引用模板库ID
	private String includeTemLibraryNmae;
	private Integer comtemplateType;   
	private Date timeCreated = new Date();
	
	
	
	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}
	
	public Long getTemLibraryId(){
		return temLibraryId;
	}
	
	public void setTemLibraryId(Long temLibraryId){
		this.temLibraryId = temLibraryId;
	}
	
	public Long getComTemplateId(){
		return comtemplateId;
	}
	
	public void setComTemplateId(Long comtemplateId){
		this.comtemplateId = comtemplateId;
	}
	
	public Long getIncludeTemLibraryId(){
		return includeTemLibraryId;
	}
	
	public void setIncludeTemLibraryId(Long includetemLibraryId){
		this.includeTemLibraryId = includetemLibraryId;
	}
	
	public Integer getComTemplateType(){
		return comtemplateType;
	}
	
	public void setComTemplateType(Integer comtemplateType){
		this.comtemplateType = comtemplateType;
	}
	
	public String getIncludeTemLibraryName(){
		return includeTemLibraryNmae;
	}
	
	public void setIncludeTemLibraryName(String includeTemLibraryNmae){
		this.includeTemLibraryNmae = includeTemLibraryNmae;
	}
	
	
	
	
	

}

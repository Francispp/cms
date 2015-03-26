package com.cyberway.cms.permission.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CmsResource implements Serializable {
	private Long oid;
	private String resourceName;
	private String resourceCode;
	private Integer objectType;
	private int orderNo;
	private Integer levelIsView;
	private int levelNo=0;
	private String remark;
	private List<CmsPermResource> permResources =new ArrayList<CmsPermResource>();
	
	
	public Integer getObjectType() {
		return objectType;
	}
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	//用于显示中文内容
	public String getObjectTypeName(){
		return com.cyberway.cms.permission.service.CmsResourceService.ObjectResourceTypes[objectType];
	}
	public List<CmsPermResource> getPermResources() {
		return permResources;
	}
	public void setPermResources(List<CmsPermResource> permResources) {
		this.permResources = permResources;
	}
	//用于显示中文名称
	public String getLevelIsViewName(){
		if(levelIsView==1)
			return "是";
		else
			return "否";
	}
	public Integer getLevelIsView() {
		return levelIsView;
	}
	public void setLevelIsView(Integer levelIsView) {
		this.levelIsView = levelIsView;
	}
	public int getLevelNo() {
		return levelNo;
	}
	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}
	
}

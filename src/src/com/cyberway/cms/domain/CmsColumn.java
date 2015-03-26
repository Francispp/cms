package com.cyberway.cms.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CmsColumn implements Serializable
{ 
	private Long id;
	private String name;
	private CmsColumn parent;
	private CmsSite site;
	private String path;
	private String remark;
	private String columnCode;
	private int state=1;
	private int sortOrder = 0;
	private Date timeCreated = new Date ();
	private List<CmsColumn> subColumn = new ArrayList<CmsColumn> ();
	

	public String getColumnCode() {
		return columnCode;
	}
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
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
	public CmsColumn getParent() {
		return parent;
	}
	public void setParent(CmsColumn parent) {
		this.parent = parent;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public CmsSite getSite() {
		return site;
	}
	public void setSite(CmsSite site) {
		this.site = site;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	public List<CmsColumn> getSubColumn() {
		return subColumn;
	}
	public void setSubColumn(List<CmsColumn> subColumn) {
		this.subColumn = subColumn;
	}
	

}

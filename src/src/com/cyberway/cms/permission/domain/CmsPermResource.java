package com.cyberway.cms.permission.domain;

import java.io.Serializable;

public class CmsPermResource implements Serializable {
	private Long oid;
	private String resourceCode;
	private CmsResource resource;
	private CmsPermission permission;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public CmsPermission getPermission() {
		return permission;
	}
	public void setPermission(CmsPermission permission) {
		this.permission = permission;
	}
	public CmsResource getResource() {
		return resource;
	}
	public void setResource(CmsResource resource) {
		this.resource = resource;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	

}

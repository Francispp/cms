package com.cyberway.common.domain;

import java.io.Serializable;

public class CoreADUserRole implements Serializable {
	private Long oid;

	private Long userid;

	private com.cyberway.common.domain.CoreRole coreRole = new CoreRole();

	public CoreADUserRole() {
	}

	public CoreADUserRole(Long userid,
			com.cyberway.common.domain.CoreRole coreRole) {
		this.userid = userid;
		this.coreRole = coreRole;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public com.cyberway.common.domain.CoreRole getCoreRole() {
		return coreRole;
	}

	public void setCoreRole(com.cyberway.common.domain.CoreRole coreRole) {
		this.coreRole = coreRole;
	}
}

package com.cyberway.cms.component.webuser.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cyberway.common.domain.CoreRole;


/** @author Hibernate CodeGenerator */
public class WebUserRole implements Serializable {

    /** identifier field */
    private Long oid;

    /** persistent field */
    private WebUser webUser=new WebUser();

    /** persistent field */
    private CoreRole coreRole=new CoreRole();

    /** full constructor */
    public WebUserRole(WebUser coreUser, com.cyberway.common.domain.CoreRole coreRole) {
        this.webUser = coreUser;
        this.coreRole = coreRole;
    }

    /** default constructor */
    public WebUserRole() {
    }

    public Long getOid() {
        return this.oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }




    public String toString() {
        return new ToStringBuilder(this)
            .append("oid", getOid())
            .toString();
    }

	public com.cyberway.common.domain.CoreRole getCoreRole() {
		return coreRole;
	}

	public void setCoreRole(com.cyberway.common.domain.CoreRole coreRole) {
		this.coreRole = coreRole;
	}

	public WebUser getWebUser() {
		return webUser;
	}

	public void setWebUser(WebUser webUser) {
		this.webUser = webUser;
	}

}

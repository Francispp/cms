package com.cyberway.common.domain;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CoreUserRole implements Serializable {

    /** identifier field */
    private Long oid;

    /** persistent field */
    private com.cyberway.common.domain.CoreUser coreUser=new CoreUser();

    /** persistent field */
    private com.cyberway.common.domain.CoreRole coreRole=new CoreRole();

    /** full constructor */
    public CoreUserRole(com.cyberway.common.domain.CoreUser coreUser, com.cyberway.common.domain.CoreRole coreRole) {
        this.coreUser = coreUser;
        this.coreRole = coreRole;
    }

    /** default constructor */
    public CoreUserRole() {
    }

    public Long getOid() {
        return this.oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public com.cyberway.common.domain.CoreUser getCoreUser() {
        return this.coreUser;
    }

    public void setCoreUser(com.cyberway.common.domain.CoreUser coreUser) {
        this.coreUser = coreUser;
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

}

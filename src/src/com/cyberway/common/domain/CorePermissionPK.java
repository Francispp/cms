package com.cyberway.common.domain;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CorePermissionPK implements Serializable {

    /** identifier field */
    private Long targettype;

    /** identifier field */
    private Long objectid;

    /** identifier field */
    private Long resourceid;

    /** full constructor */
    public CorePermissionPK(Long targettype, Long objectid, Long resourceid) {
        this.targettype = targettype;
        this.objectid = objectid;
        this.resourceid = resourceid;
    }

    /** default constructor */
    public CorePermissionPK() {
    }

    public Long getTargettype() {
        return this.targettype;
    }

    public void setTargettype(Long targettype) {
        this.targettype = targettype;
    }

    public Long getObjectid() {
        return this.objectid;
    }

    public void setObjectid(Long objectid) {
        this.objectid = objectid;
    }

    public Long getResourceid() {
        return this.resourceid;
    }

    public void setResourceid(Long resourceid) {
        this.resourceid = resourceid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("targettype", getTargettype())
            .append("objectid", getObjectid())
            .append("resourceid", getResourceid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof CorePermissionPK) ) return false;
        CorePermissionPK castOther = (CorePermissionPK) other;
        return new EqualsBuilder()
            .append(this.getTargettype(), castOther.getTargettype())
            .append(this.getObjectid(), castOther.getObjectid())
            .append(this.getResourceid(), castOther.getResourceid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTargettype())
            .append(getObjectid())
            .append(getResourceid())
            .toHashCode();
    }

}

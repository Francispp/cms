package com.cyberway.common.domain;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CorePermission implements Serializable {

    /** identifier field */
    private com.cyberway.common.domain.CorePermissionPK comp_id;

    /** nullable persistent field */
    private com.cyberway.common.domain.CoreResource coreResource;

    /** full constructor */
    public CorePermission(com.cyberway.common.domain.CorePermissionPK comp_id, com.cyberway.common.domain.CoreResource coreResource) {
        this.comp_id = comp_id;
        this.coreResource = coreResource;
    }

    /** default constructor */
    public CorePermission() {
    }

    /** minimal constructor */
    public CorePermission(com.cyberway.common.domain.CorePermissionPK comp_id) {
        this.comp_id = comp_id;
    }

    public com.cyberway.common.domain.CorePermissionPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.cyberway.common.domain.CorePermissionPK comp_id) {
        this.comp_id = comp_id;
    }

    public com.cyberway.common.domain.CoreResource getCoreResource() {
        return this.coreResource;
    }

    public void setCoreResource(com.cyberway.common.domain.CoreResource coreResource) {
        this.coreResource = coreResource;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof CorePermission) ) return false;
        CorePermission castOther = (CorePermission) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}

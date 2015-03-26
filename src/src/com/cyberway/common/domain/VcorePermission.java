package com.cyberway.common.domain;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class VcorePermission implements Serializable {

    /** identifier field */
    private Long targettype;

    /** identifier field */
    private String typename;

    /** identifier field */
    private Long objectid;

    /** identifier field */
    private Long resourceid;

    /** identifier field */
    private String objectname;

    /** identifier field */
    private String resourcename;

    /** identifier field */
    private String resourcekey;

    /** full constructor */
    public VcorePermission(Long targettype, String typename, Long objectid, Long resourceid, String objectname, String resourcename, String resourcekey) {
        this.targettype = targettype;
        this.typename = typename;
        this.objectid = objectid;
        this.resourceid = resourceid;
        this.objectname = objectname;
        this.resourcename = resourcename;
        this.resourcekey = resourcekey;
    }

    /** default constructor */
    public VcorePermission() {
    }

    public Long getTargettype() {
        return this.targettype;
    }

    public void setTargettype(Long targettype) {
        this.targettype = targettype;
    }

    public String getTypename() {
        return this.typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
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

    public String getObjectname() {
        return this.objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    public String getResourcename() {
        return this.resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public String getResourcekey() {
        return this.resourcekey;
    }

    public void setResourcekey(String resourcekey) {
        this.resourcekey = resourcekey;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("targettype", getTargettype())
            .append("typename", getTypename())
            .append("objectid", getObjectid())
            .append("resourceid", getResourceid())
            .append("objectname", getObjectname())
            .append("resourcename", getResourcename())
            .append("resourcekey", getResourcekey())
            .toString();
    }

}

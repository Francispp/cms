package com.cyberway.common.domain;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CoreResource implements Serializable {

    /** identifier field */
    private Long resourceid;

    /** nullable persistent field */
    private String resourcename;

    /** nullable persistent field */
    private String resourcetype;

    /** nullable persistent field */
    private String resourcestring;

    /** nullable persistent field */
    private Long orderno; 

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String resourcekey;
       
    /** persistent field */
    private Set corePermissions;    

    
    private Set vcorePermissions;
    
    private int viewtype;//显示类型 

    /** full constructor */
    public CoreResource(String resourcename, String resourcetype, String resourcestring, Long orderno, String remark, String resourcekey, Set corePermissions) {
        this.resourcename = resourcename;
        this.resourcetype = resourcetype;
        this.resourcestring = resourcestring;
        this.orderno = orderno;
        this.remark = remark;
        this.resourcekey = resourcekey;
        this.corePermissions = corePermissions;
    }

    /** default constructor */
    public CoreResource() {
    }

    /** minimal constructor */
    public CoreResource(Set corePermissions) {
        this.corePermissions = corePermissions;
    }

    public Long getResourceid() {
        return this.resourceid;
    }

    public void setResourceid(Long resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcename() {
        return this.resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public String getResourcetype() {
        return this.resourcetype;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
    }


    public Long getOrderno() {
        return this.orderno;
    }

    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }

 

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResourcekey() {
        return this.resourcekey;
    }

    public void setResourcekey(String resourcekey) {
        this.resourcekey = resourcekey;
    }

    public Set getCorePermissions() {
        return this.corePermissions;
    }

    public void setCorePermissions(Set corePermissions) {
        this.corePermissions = corePermissions;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("resourceid", getResourceid())
            .toString();
    }

	public int getViewtype() {
		return viewtype;
	}

	public void setViewtype(int viewtype) {
		this.viewtype = viewtype;
	}

	public String getResourcestring() {
		return resourcestring;
	}

	public void setResourcestring(String resourcestring) {
		this.resourcestring = resourcestring;
	}


	public Set getVcorePermissions() {
		return vcorePermissions;
	}

	public void setVcorePermissions(Set vcorePermissions) {
		this.vcorePermissions = vcorePermissions;
	}

}

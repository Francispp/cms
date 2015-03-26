package com.cyberway.common.domain;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CoreDept implements Serializable {

    /** identifier field */
    private Long deptid;

    /** nullable persistent field */
    private Long pdeptid;

    /** nullable persistent field */
    private String deptname;

    /** nullable persistent field */
    private String depttype;

    /** nullable persistent field */
    private String remark;

    /** persistent field */
    private Set coreUsers;

    private CoreOrg coreOrg;

    /** persistent field */
    private Set subDepts;
    /** nullable persistent field */
    private Long state;
    //
    private String pdeptname;
    /** full constructor */
    public CoreDept(Long pdeptid, String deptname, String depttype, String remark, Set coreUsers) {
        this.pdeptid = pdeptid;
        this.deptname = deptname;
        this.depttype = depttype;
        this.remark = remark;
        this.coreUsers = coreUsers;

    }

    /** default constructor */
    public CoreDept() {
    }

    /** minimal constructor */
    public CoreDept(Set coreUsers) {
        this.coreUsers = coreUsers;
    }

    public Long getDeptid() {
        return this.deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public Long getPdeptid() {
        return this.pdeptid;
    }

    public void setPdeptid(Long pdeptid) {
        this.pdeptid = pdeptid;
    }

    public String getDeptname() {
        return this.deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getDepttype() {
        return this.depttype;
    }

    public void setDepttype(String depttype) {
        this.depttype = depttype;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set getCoreUsers() {
        return this.coreUsers;
    }

    public void setCoreUsers(Set coreUsers) {
        this.coreUsers = coreUsers;
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("deptid", getDeptid())
            .toString();
    }

	public String getPdeptname() {
		return pdeptname;
	}

	public void setPdeptname(String pdeptname) {
		this.pdeptname = pdeptname;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Set getSubDepts() {
		return subDepts;
	}

	public void setSubDepts(Set subDepts) {
		this.subDepts = subDepts;
	}

	public CoreOrg getCoreOrg() {
		return coreOrg;
	}

	public void setCoreOrg(CoreOrg coreOrg) {
		this.coreOrg = coreOrg;
	}


}

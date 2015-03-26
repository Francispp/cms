package com.cyberway.core.objects;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CoreFlow implements Serializable {

    /** identifier field */
    private Long fid;

    /** nullable persistent field */
    private String flowname;

    /** nullable persistent field */
    private String flowid;

    /** nullable persistent field */
    private Date createtime;

    /** nullable persistent field */
    private String createname;

    /** nullable persistent field */
    private Long createid;

    /** nullable persistent field */
    private String modulename;

    /** nullable persistent field */
    private String flowVersion;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Long state;

    //增加的业务对象
    private String activityname;
    private String activityid;
    private String flowState;
    /** full constructor */
    public CoreFlow(String flowname, String flowid, Date createtime, String createname, Long createid, String modulename, String flowVersion, String remark) {
        this.flowname = flowname;
        this.flowid = flowid;
        this.createtime = createtime;
        this.createname = createname;
        this.createid = createid;
        this.modulename = modulename;
        this.flowVersion = flowVersion;
        this.remark = remark;
    }

    /** default constructor */
    public CoreFlow() {
    }


    public String getFlowname() {
        return this.flowname;
    }

    public void setFlowname(String flowname) {
        this.flowname = flowname;
    }

    public String getFlowid() {
        return this.flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreatename() {
        return this.createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    public Long getCreateid() {
        return this.createid;
    }

    public void setCreateid(Long createid) {
        this.createid = createid;
    }

    public String getModulename() {
        return this.modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getFlowVersion() {
        return this.flowVersion;
    }

    public void setFlowVersion(String flowVersion) {
        this.flowVersion = flowVersion;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("fid", getFid())
            .toString();
    }

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public String getFlowState() {
		return flowState;
	}

	public void setFlowState(String flowState) {
		this.flowState = flowState;
	}

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}


}

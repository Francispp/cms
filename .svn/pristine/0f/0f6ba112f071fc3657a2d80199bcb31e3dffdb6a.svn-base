package com.cyberway.common.domain;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CoreSystemLog implements Serializable {

    /** identifier field */
    private Long oid;

    /** nullable persistent field */
    private Date accesstime;

    /** nullable persistent field */
    private String ipaddress;

    /** nullable persistent field */
    private String accessurl;

    /** nullable persistent field */
    private Long accessid;

    /** nullable persistent field */
    private String accessname;

    /** nullable persistent field */
    private Long type;

    /** nullable persistent field */
    private String optname;
    
    /** nullable persistent field */
    private String pcode;
    
    /** nullable persistent field */
    private String remark;

    /** full constructor */
    public CoreSystemLog(Date accesstime, String ipaddress, String accessurl, Long accessid, String accessname, String remark) {
        this.accesstime = accesstime;
        this.ipaddress = ipaddress;
        this.accessurl = accessurl;
        this.accessid = accessid;
        this.accessname = accessname;
        this.remark = remark;
    }

    /** default constructor */
    public CoreSystemLog() {
    }

    public Long getOid() {
        return this.oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Date getAccesstime() {
        return this.accesstime;
    }

    public void setAccesstime(Date accesstime) {
        this.accesstime = accesstime;
    }

    public String getIpaddress() {
        return this.ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getAccessurl() {
        return this.accessurl;
    }

    public void setAccessurl(String accessurl) {
        this.accessurl = accessurl;
    }

    public Long getAccessid() {
        return this.accessid;
    }

    public void setAccessid(Long accessid) {
        this.accessid = accessid;
    }

    public String getAccessname() {
        return this.accessname;
    }

    public void setAccessname(String accessname) {
        this.accessname = accessname;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("oid", getOid())
            .toString();
    }

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getOptname() {
		return optname;
	}

	public void setOptname(String optname) {
		this.optname = optname;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

}

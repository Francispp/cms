package com.cyberway.common.domain;

import java.util.HashSet;
import java.util.Set;


/**
 * CoreOrg generated by MyEclipse - Hibernate Tools
 */

public class CoreOrg  implements java.io.Serializable {


    // Fields    
	private Long oid;
	
	 private Long porgid;
     private String orgCode;
     private String orgName;
     private String orgInfo;
     /** nullable persistent field */
     private String inco;
     
     /** nullable persistent field */
     private String openInco;
     
     /** nullable persistent field */
     private String closeInco;     
     private Set depts = new HashSet(0);
     private Set roles = new HashSet(0);
     private Set subOrgs = new HashSet(0);


     private String porgName;
    // Constructors

    /** default constructor */
    public CoreOrg() {
    }
	/** minimal constructor */
    public CoreOrg(Long oid) {
        this.oid = oid;
    }
	/** minimal constructor */
 
   
  

    public Set getDepts() {
        return this.depts;
    }
    
    public void setDepts(Set coreDepts) {
        this.depts = coreDepts;
    }

    public Set getRoles() {
        return this.roles;
    }
    
    public void setRoles(Set coreRoleses) {
        this.roles = coreRoleses;
    }

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getCloseInco() {
		return closeInco;
	}

	public void setCloseInco(String closeInco) {
		this.closeInco = closeInco;
	}

	public String getInco() {
		return inco;
	}

	public void setInco(String inco) {
		this.inco = inco;
	}

	public String getOpenInco() {
		return openInco;
	}

	public void setOpenInco(String openInco) {
		this.openInco = openInco;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgInfo() {
		return orgInfo;
	}
	public void setOrgInfo(String orgInfo) {
		this.orgInfo = orgInfo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getPorgid() {
		return porgid;
	}
	public void setPorgid(Long porgid) {
		this.porgid = porgid;
	}
	public Set getSubOrgs() {
		return subOrgs;
	}
	public void setSubOrgs(Set subOrgs) {
		this.subOrgs = subOrgs;
	}
	public String getPorgName() {
		return porgName;
	}
	public void setPorgName(String porgName) {
		this.porgName = porgName;
	}
   








}
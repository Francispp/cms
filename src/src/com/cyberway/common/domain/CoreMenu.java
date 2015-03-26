package com.cyberway.common.domain;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CoreMenu implements Serializable {

    /** identifier field */
    private Long oid;

    /** nullable persistent field */
    private String menuname;
    
    /** nullable persistent field */
    private String menucode;
    
    /** nullable persistent field */
    private Long pmid;   
    
    /** nullable persistent field */
    private Long orderno; 
    
    /** nullable persistent field */
    private String inco;
    
    /** nullable persistent field */
    private String openInco;
    
    /** nullable persistent field */
    private String closeInco;
    
    /** nullable persistent field */
    private String url;
    
    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Long state;
    
    private Long portalid;
    

    /** persistent field */
    private Set subMenus;

    private String pmname;
    /** default constructor */
    public CoreMenu() {
    }

	public String getInco() {
		return inco;
	}

	public void setInco(String inco) {
		this.inco = inco;
	}

	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Long getOrderno() {
		return orderno;
	}

	public void setOrderno(Long orderno) {
		this.orderno = orderno;
	}

	public Long getPmid() {
		return pmid;
	}

	public void setPmid(Long pmid) {
		this.pmid = pmid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Set subMenus) {
		this.subMenus = subMenus;
	}

	public String getPmname() {
		return pmname;
	}

	public void setPmname(String pmname) {
		this.pmname = pmname;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getOpenInco() {
		return openInco;
	}

	public void setOpenInco(String openInco) {
		this.openInco = openInco;
	}

	public String getCloseInco() {
		return closeInco;
	}

	public void setCloseInco(String closeInco) {
		this.closeInco = closeInco;
	}

	public Long getPortalid() {
		return portalid;
	}

	public void setPortalid(Long portalid) {
		this.portalid = portalid;
	}

}

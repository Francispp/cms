package com.cyberway.core.web.domain;

import java.io.Serializable;
import java.util.Date;

import com.cyberway.core.dao.BaseDomain;

/**
 * @hibernate.class table="CMS_LOGINLOG"
 * @author Administrator
 * 
 */
public class LoginLog extends BaseDomain {

	
	private Long oid;
	private String remoteIpAddress; //用户ip地址
	private Boolean loginSuccess;// 是否成功
	private String errorMessage; // 登录的站点
    private Long  orgid;//当前用户的机构id
    private String orgName;//机构名称
    private String orgCode;//机构编码
    private String portalid;//机构编码
    
	/**
	 * 登录者userid
	 */
	private Long userid;

	/**
	 * 登录者用户名
	 */
	private String loginid;


	/**
	 * 登录者部门id
	 */
	private Long deptcode;

	/**
	 * 登录者部门名称
	 */
	private String deptname;
	/**
	 * 登录者中文名
	 */
	private String username;
	/**
	 * 登录时间
	 */
	private Date loginTime;
	/**
	 * 登录时间
	 */
	private Date logoutTime;

	// 用户类型（内部用户，外部用户）
	private String userType;
	
	
	
	
	

	/**
	 * @hibernate.id
	 * generator-class = "native"
	 * @return
	 */
	public Long getOid() {
		return oid;
	}





	/**
	 * @hibernate.property
	 * @return
	 */
	public Boolean getLoginSuccess() {
		return loginSuccess;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public Long getUserid() {
		return userid;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public String getLoginid() {
		return loginid;
	}


	/**
	 * @hibernate.property
	 * @return
	 */
	public Long getDeptcode() {
		return deptcode;
	}

	public String getDeptname() {
		return deptname;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @hibernate.property type = "java.sql.Timestamp"
	 * @return
	 */
	public Date getLoginTime() {
		return loginTime;
	}
	/**
	 * @hibernate.property type = "java.sql.Timestamp"
	 * @return
	 */
	public Date getLogoutTime() {
		return logoutTime;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public String getRemoteIpAddress() {
		return remoteIpAddress;
	}
	
	/**
	 * @hibernate.property
	 * @return
	 */
	public Long getOrgid() {
		return orgid;
	}


	/**
	 * @hibernate.property
	 * @return
	 */
	public String getOrgName() {
		return orgName;
	}


	/**
	 * @hibernate.property
	 * @return
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public String getPortalid() {
		return portalid;
	}
	
	
	
	
	
	
	public void setOid(Long oid) {
		this.oid = oid;
	}


	public void setLoginSuccess(Boolean loginSuccess) {
		this.loginSuccess = loginSuccess;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public void setDeptcode(Long deptcode) {
		this.deptcode = deptcode;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setRemoteIpAddress(String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}


	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}





	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}





	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}





	public void setPortalid(String portalid) {
		this.portalid = portalid;
	}


	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	
	
}

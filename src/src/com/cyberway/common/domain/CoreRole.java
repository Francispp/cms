package com.cyberway.common.domain;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CoreRole implements Serializable {

	
	/** identifier field */
	private Long	oid;

	/** nullable persistent field */
	private String	rolename;

	/** nullable persistent field */
	private String	rolecode;
	/**
	 * 身份类型
	 */
	private String	roleIdentity;

	/**
	 * 业绩水平字符串
	 */
	private String	roleGrade;

	/**
	 * 身份类型_业绩水平的组合:各个组合之间用字符"&"分隔.例如:身份类型1_业绩水平1_业绩水平2&身份类型2_业绩水平1
	 */
	private String	identityGradeString;
	
	/**
	 * 身份类型_业绩水平组合的中文显示
	 */
	private String	identityGradeText;

	/**
	 * 角色类型:"0"代表"普通角色";"1"代表"LDAP角色";"2"代表"外部角色"
	 */
	private Long	roleType;

	private Long	gradeId;

	private String	relationContent;
	private String	managerUserIds;
	private String	managerUserNames;

	/** nullable persistent field */
	private String	remark;

	/** persistent field */
	private Set	    coreUserRole;
	private Set	    coreADUserRole;

	/** nullable persistent field */
	private Long	state;

	private CoreOrg	coreOrg	= new CoreOrg();
	
	private Long siteId;
	
	/**
	 * 与site站点保持一致
	 */
	private Long portal_id=1L;

	/** full constructor */
	public CoreRole(String rolename, String remark, Set coreUserRole) {
		this.rolename = rolename;
		this.remark = remark;
		this.coreUserRole = coreUserRole;
	}

	public CoreRole(String rolename, String remark, Set coreUserRole, Set coreADUserRole) {
		this.rolename = rolename;
		this.remark = remark;
		this.coreUserRole = coreUserRole;
		this.coreADUserRole = coreADUserRole;
	}

	/** default constructor */
	public CoreRole() {
	}

	/** minimal constructor */
	public CoreRole(Set coreUserRole) {
		this.coreUserRole = coreUserRole;
	}

	public String toString() {
		return new ToStringBuilder(this).append("oid", getOid()).toString();
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Set getCoreUserRole() {
		return coreUserRole;
	}

	public Set getCoreADUserRole() {
		return coreADUserRole;
	}

	public void setCoreUserRole(Set coreUserRole) {
		this.coreUserRole = coreUserRole;
	}

	public void setCoreADUserRole(Set coreADUserRole) {
		this.coreADUserRole = coreADUserRole;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRelationContent() {
		return relationContent;
	}

	public void setRelationContent(String relationContent) {
		this.relationContent = relationContent;
	}

	public Long getRoleType() {
		return roleType;
	}

	public void setRoleType(Long roleType) {
		this.roleType = roleType;
	}

	public String getManagerUserIds() {
		return managerUserIds;
	}

	public void setManagerUserIds(String managerUserIds) {
		this.managerUserIds = managerUserIds;
	}

	public String getManagerUserNames() {
		return managerUserNames;
	}

	public void setManagerUserNames(String managerUserNames) {
		this.managerUserNames = managerUserNames;
	}

	public CoreOrg getCoreOrg() {
		return coreOrg;
	}

	public void setCoreOrg(CoreOrg coreOrg) {
		this.coreOrg = coreOrg;
	}

	

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getRoleGrade() {
		return roleGrade;
	}

	public void setRoleGrade(String roleGrade) {
		this.roleGrade = roleGrade;
	}

	public String getIdentityGradeString() {
		return identityGradeString;
	}

	public void setIdentityGradeString(String identityGradeString) {
		this.identityGradeString = identityGradeString;
	}

	public String getIdentityGradeText() {
    	return identityGradeText;
    }

	public void setIdentityGradeText(String identityGradeText) {
    	this.identityGradeText = identityGradeText;
    }
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getRoleIdentity() {
		return roleIdentity;
	}

	public void setRoleIdentity(String roleIdentity) {
		this.roleIdentity = roleIdentity;
	}

	public Long getPortal_id() {
		return portal_id;
	}

	public void setPortal_id(Long portal_id) {
		this.portal_id = portal_id;
	}

}

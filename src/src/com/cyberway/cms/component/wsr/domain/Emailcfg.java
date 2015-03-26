package com.cyberway.cms.component.wsr.domain;

// default package

import java.util.Date;

/**
 * WebUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Emailcfg implements java.io.Serializable {
	
	private Long id;    //编号
	private Long siteId;    //站点编号
	private String siteName;    //站点名称
	private String mailServer;	//邮件服务器
	private String fromMail;		//默认发送人
	private String frompwd;		//发送人密码
	private boolean needvalidate;		//发送人密码
	private String subject;		//邮件标题
	private Date createTime;	//创建时间
	private String describe;		//备注
	private String formName;		//表单名称

	/** default constructor */
	public Emailcfg() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	} 

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getFrompwd() {
		return frompwd;
	}

	public void setFrompwd(String frompwd) {
		this.frompwd = frompwd;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
 

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public boolean isNeedvalidate() {
		return needvalidate;
	}

	public void setNeedvalidate(boolean needvalidate) {
		this.needvalidate = needvalidate;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	
}
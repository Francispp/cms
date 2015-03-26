package com.cyberway.common.domain;

import java.io.Serializable;

/**
 * ftp服务器配置管理
 * 
 * @author taoz
 * 
 */
public class CoreSiteDistribution implements Serializable {
	// id
	private Long	id;
	// siteId
	private Long	siteId;
	// ftp服务器ip
	private String	ftpIp;
	// ftp端口
	private Integer	port;

	// 是ftp 1还是sftp 2
	private String	isFtp;

	// 用户名
	private String	userName;
	// 密码
	private String	passWord;
	// ftp目录位置
	private String	ftpPath;
	// 是否被用到 1表示被选择 null和 0表示没被选择
	private String	checked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getIsFtp() {
		return isFtp;
	}

	public void setIsFtp(String isFtp) {
		this.isFtp = isFtp;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

}

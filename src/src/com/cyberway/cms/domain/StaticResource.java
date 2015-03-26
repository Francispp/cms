package com.cyberway.cms.domain;

import java.io.File;
import java.io.Serializable;
import java.util.Date;



public class StaticResource implements Serializable {

	private long id;

	/*资源类型*/
	private String type;

	/*资源名称*/
	private String name;

	/*资源路径*/
	private String serverpath;

	/*上传人*/
	private String uploadman;

	/*上传时间*/
	private Date uploadtime;

	/*站点ID*/
	private long siteid;

	/*频道ID*/
	private long channelid;

	/*资源描述*/
	private String description;

	/*资源归属*/
	private String owner;

	/*频道名称*/
	private String _channelname;

	/*站点名称*/
	private String _site;
	
	private File _file;
	
	private Date timeDeleted;
	
	private Integer status = 1;
	
	private String _filecontent;

	


	public File get_file() {
		return _file;
	}

	public void set_file(File _file) {
		this._file = _file;
	}

	/** full constructor */
	public StaticResource(String type, String name, String serverpath,
			String uploadman, Date uploadtime, long siteid,long channelid,
			String description,String owner) {
		 this.type = type;
		 this.name = name;
		 this.serverpath = serverpath;
		 this.uploadman = uploadman;
		 this.uploadtime = uploadtime;
		 this.siteid = siteid;
		 this.channelid = channelid;
		 this.description = description;
		 this.owner = owner;

	}
	
	 /** default constructor */
	public StaticResource()
	{
		
	}

	public String get_channelname() {
		return _channelname;
	}

	public void set_channelname(String _channelname) {
		this._channelname = _channelname;
	}

	public String get_site() {
		return _site;
	}

	public void set_site(String _site) {
		this._site = _site;
	}



	public long getChannelid() {
		return channelid;
	}

	public void setChannelid(long channelid) {
		this.channelid = channelid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getServerpath() {
		return serverpath;
	}

	public void setServerpath(String serverpath) {
		this.serverpath = serverpath;
	}

	public long getSiteid() {
		return siteid;
	}

	public void setSiteid(long siteid) {
		this.siteid = siteid;
	}


	public String getUploadman() {
		return uploadman;
	}

	public void setUploadman(String uploadman) {
		this.uploadman = uploadman;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getTimeDeleted() {
		return timeDeleted;
	}

	public void setTimeDeleted(Date timeDeleted) {
		this.timeDeleted = timeDeleted;
	}

	public String get_filecontent() {
		return _filecontent;
	}

	public void set_filecontent(String _filecontent) {
		this._filecontent = _filecontent;
	}

}

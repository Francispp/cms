package com.cyberway.common.message.domain;

import java.io.Serializable;
import java.util.Date;

import com.cyberway.common.domain.CoreUser;

public class Message implements Serializable{
	private long id;

	private long docid;

	private int score;
	
	private String title;

	private String content;
	
	private String face;
	
	//private String productInfo;
	
	private String ipInfo;
	
	private Date createtime;
	
	private CoreUser user;

	public Message(){
		
	}
   public Message(long id,long docid,String title,String content,String face,Date createtime,CoreUser user){
	   this.id = id;
	   this.docid = docid;
	   this.title = title;
	   this.content = content;
	   this.face = face;
	   this.createtime = createtime;
	   this.user = user;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public long getDocid() {
		return docid;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CoreUser getUser() {
		return user;
	}

	public void setUser(CoreUser user) {
		this.user = user;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getIpInfo() {
		return ipInfo;
	}
	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}
}

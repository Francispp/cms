package com.cyberway.cms.component.leaveword.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class LeaveWord implements Serializable{
	private long oid;
	private long siteId;
	private String siteName;
	private String email;
	private String removeIp;
	private String userName;
	private long userId;
	private Date leaveTime;
	private String title;
	private String link;
	private String content;
	private long status; 
	private Set<AnswerLeaveWord> answers = new TreeSet<AnswerLeaveWord>(new Comparator<AnswerLeaveWord>(){

		public int compare(AnswerLeaveWord answer1, AnswerLeaveWord answer2) {
			return answer1.getAnswerTime().toString().compareTo(answer2.getAnswerTime().toString());
		}});
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public String getRemoveIp() {
		return removeIp;
	}
	public void setRemoveIp(String removeIp) {
		this.removeIp = removeIp;
	}
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	} 
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Set<AnswerLeaveWord> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<AnswerLeaveWord> answers) {
		this.answers = answers;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}  
	
	
	
}

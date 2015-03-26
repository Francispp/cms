package com.cyberway.cms.survey.domain;

import java.util.Date;

/**
 * Question entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class QuestionReply implements java.io.Serializable {

	// Fields
	// 防止在规定的时间内重复提交
	private Long oid;

	private Long questionId; // 问题编号

	private Long bdate; // 提交时间

	private String bip; // 访问IP
	
	/**
	 * 区分系统用户(S-U-0001)、内部(I-S-0001)、外部(E-U-0001)用户
	 */
	private String answerer;

	private Date createTime = new Date();

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getBdate() {
		return bdate;
	}

	public void setBdate(Long bdate) {
		this.bdate = bdate;
	}

	public String getBip() {
		return bip;
	}

	public void setBip(String bip) {
		this.bip = bip;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getAnswerer() {
		return answerer;
	}

	public void setAnswerer(String answerer) {
		this.answerer = answerer;
	}

}
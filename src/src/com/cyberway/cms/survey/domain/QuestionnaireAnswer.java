package com.cyberway.cms.survey.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Question entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class QuestionnaireAnswer implements java.io.Serializable {

	// Fields

	private Long id;
	private Long questionnaireId;
	private Long userId;
	private Date submitTime;
	
	private Set<QuestionAnswer> questionAns = new HashSet<QuestionAnswer>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getQuestionnaireId() {
		return questionnaireId;
	}
	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Set<QuestionAnswer> getQuestionAns() {
		return questionAns;
	}
	public void setQuestionAns(Set<QuestionAnswer> questionAns) {
		this.questionAns = questionAns;
	}
	
	
	

}
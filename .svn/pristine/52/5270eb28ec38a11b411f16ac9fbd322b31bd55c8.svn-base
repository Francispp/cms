package com.cyberway.cms.survey.domain;


/**
 * SelectOption entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SelectOption implements java.io.Serializable {

	// Fields

	private Long id;
	private String content;
	private Integer point;
	private Integer odr=100;//序号
	private Long question_id;//所属问题
	
	private Integer totalTickets = 0;
	
	public SelectOption() {
	}
	
	public SelectOption(String content, Integer odr) {
		this.content = content;
		this.odr = odr;
	}

	public SelectOption(Long id, String content, Integer odr) {
		super();
		this.id = id;
		this.content = content;
		this.odr = odr;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getOdr() {
		return odr;
	}

	public void setOdr(Integer odr) {
		this.odr = odr;
	}

	public Integer getTotalTickets() {
		if(totalTickets == null)
			return 0;
		return totalTickets;
	}

	public void setTotalTickets(Integer totalTickets) {
		this.totalTickets = totalTickets;
	}

	public Long getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(Long question_id) {
		this.question_id = question_id;
	}

}
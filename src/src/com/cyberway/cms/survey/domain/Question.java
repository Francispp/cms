package com.cyberway.cms.survey.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Question entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Question implements java.io.Serializable {

	// Fields

	private Long id;
	private Long questionnaireId;
	private String content;
	private Date createDate;
	private Integer point;
	private String type;  //0:'短答',1:'单选',2:'多选',3:'单选短答',4:'多选短答',5:'自由长答'
	private Integer optDirect=1;//选项排列：  纵向排列　  横向排列 
	private Integer required=0;//其它设置：  必答  非必答 
	private Integer odr=100;//序号
	private String remark;
	private String validationRegex;//其它答案的验证规则

	private List<SelectOption> selectOptions = new ArrayList<SelectOption>(0);
	
	
//	private Questionnaire questionnaire;

	// Constructors

	/** default constructor */
	public Question() {
	}


	// Property accessors

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


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public void removeSelectOption(SelectOption selectoption){
		getSelectOptions().remove(selectoption );
	}
		
	public SelectOption getSelectOptionById(Long optid){
		for(SelectOption opt : getSelectOptions()){
			if(opt.getId().equals(optid)) return opt;
		}
		return null;
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Question)
			return false;
		
		if(this.id == ((Question)obj).id)
			return true;
		else 
			return false;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public Integer getOptDirect() {
		return optDirect;
	}

	public void setOptDirect(Integer optDirect) {
		this.optDirect = optDirect;
	}

	public Integer getOdr() {
		return odr;
	}

	public void setOdr(Integer odr) {
		this.odr = odr;
	}

	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public List<SelectOption> getSelectOptions() {
		return selectOptions;
	}

	public void setSelectOptions(List<SelectOption> selectOptions) {
		this.selectOptions = selectOptions;
	}


	public String getValidationRegex() {
		return validationRegex;
	}


	public void setValidationRegex(String validationRegex) {
		this.validationRegex = validationRegex;
	}
	
}
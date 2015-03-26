package com.cyberway.crawl.extractor.domain;

import java.io.Serializable;

import com.cyberway.cms.form.domain.CoreFormField;
import com.cyberway.crawl.jobs.object.Jobs;
import com.cyberway.crawl.regular.domain.Regular;

public class Extract implements Serializable{
	private Long oid;
	private String tagName;
	private String attributeName;
	private String attributeValue;
	private CoreFormField formField;
	private String fieldType;

	private Jobs job;
	private Regular regular;
	public Regular getRegular() {
		return regular;
	}
	public void setRegular(Regular regular) {
		this.regular = regular;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public Jobs getJob() {
		return job;
	}
	public void setJob(Jobs job) {
		this.job = job;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public CoreFormField getFormField() {
		return formField;
	}
	public void setFormField(CoreFormField formField) {
		this.formField = formField;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
}

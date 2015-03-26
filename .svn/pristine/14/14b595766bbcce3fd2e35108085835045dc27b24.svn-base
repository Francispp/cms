package com.cyberway.cms.domain;

import java.io.Serializable;

public class CmsShareDocument implements Serializable{
	private Long oid;
	private Long docid;
	private Integer isCopy = 1;
	private CmsBaseDocument document= new CmsBaseDocument();
	public CmsShareDocument() {
	}

	public CmsShareDocument(Long docid,
			CmsBaseDocument document) {
		this.docid = docid;
		this.document = document;
	}
	public Long getDocid() {
		return docid;
	}
	public void setDocid(Long docid) {
		this.docid = docid;
	}
	public CmsBaseDocument getDocument() {
		return document;
	}
	public void setDocument(CmsBaseDocument document) {
		this.document = document;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Integer getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(Integer isCopy) {
		this.isCopy = isCopy;
	}

}

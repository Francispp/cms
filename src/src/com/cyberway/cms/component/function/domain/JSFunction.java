package com.cyberway.cms.component.function.domain;

import java.io.Serializable;

public class JSFunction implements Serializable{
	private long oid;
	private String type ;
	private String functionName;
	private String code;
	private String describe;
	private String remark;
	
	public JSFunction() {
	}
	
	public JSFunction(long oid, String type, String functionName, String describe, String remark, String code) {
		this.oid = oid;
		this.type = type;
		this.functionName = functionName;
		this.describe = describe;
		this.remark = remark;
		this.code = code;
	}
	
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}

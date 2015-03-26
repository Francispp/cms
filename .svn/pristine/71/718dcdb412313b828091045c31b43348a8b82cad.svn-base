package com.cyberway.cms.form.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CoreForm implements Serializable {
  private Long oid;
  private String formCode;
  private String formName;
  private int formType=0;//表示表单类型(基本<0>、动态<1>)
  private String pojoName;
  private String remark; 
  private List<CoreFormField> formFields=new ArrayList();
  private boolean isCreateField=false;//是否生新生成字段 不保存到数据库中
  
public String getFormCode() {
	return formCode;
}
public void setFormCode(String formCode) {
	this.formCode = formCode;
}
public List getFormFields() {
	return formFields;
}
public void setFormFields(List formFields) {
	this.formFields = formFields;
}
public String getFormName() {
	return formName;
}
public void setFormName(String formName) {
	this.formName = formName;
}
public int getFormType() {
	return formType;
}
public void setFormType(int formType) {
	this.formType = formType;
}
public Long getOid() {
	return oid;
}
public void setOid(Long oid) {
	this.oid = oid;
}
public String getPojoName() {
	return pojoName;
}
public void setPojoName(String pojoName) {
	this.pojoName = pojoName;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public boolean getIsCreateField() {
	return isCreateField;
}
public void setIsCreateField(boolean isCreateField) {
	this.isCreateField = isCreateField;
}
}

package com.cyberway.cms.form.domain;

import java.util.Date;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;

import com.cyberway.cms.form.object.BaseDocument;

/**
 * 业务表单2
 * @author caiw
 *
 */
@SuppressWarnings("serial")
public class BizDocument2 extends BaseDocument{  
	
	//业务字段
	@Field
	private String body;
	@Field
	private String classString;
	@Field
	private String fieldString1;
	@Field
	private String fieldString2;
	@Field
	private String fieldString3;
	@Field
	private String fieldString4;
	@Field
	private String	fieldString5;
	@Field
	private String	fieldString6;
	@Field
	private String	fieldString7;
	@Field
	private String	fieldString8;
	@Field
	private String	fieldString9;
	@Field
	private String	fieldString10;
	@Field
	private String	fieldString11;
	@Field
	private String	fieldString12;
	@Field
	private String	fieldString13;
	@Field
	private String	fieldString14;
	@Field
	private String	fieldString15;
	

	@Field
	private Long fieldLong1;
	@Field
	private Long fieldLong2;
	@Field
	private Long fieldLong3;
	@Field
	private Long fieldLong4;
	@Field
	private Long fieldLong5; 
	
	
	
	@Field
	private Float fieldFloat1;
	@Field
	private Float fieldFloat2;
	
	@Field
	private Date fieldDate1;
	@Field
	private Date fieldDate2;
	@Field
	private Date fieldDate3;
	@Field
	private Date fieldDate4;
	@Field
	private Date fieldDate5; 
 
	
	@Field(index=Index.UN_TOKENIZED)
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	 
	public String getClassString() {
		return classString;
	}
	public void setClassString(String classString) {
		this.classString = classString;
	}
	public Date getFieldDate1() {
		return fieldDate1;
	}
	public void setFieldDate1(Date fieldDate1) {
		this.fieldDate1 = fieldDate1;
	}
	public Date getFieldDate2() {
		return fieldDate2;
	}
	public void setFieldDate2(Date fieldDate2) {
		this.fieldDate2 = fieldDate2;
	}
	public Float getFieldFloat1() {
		return fieldFloat1;
	}
	public void setFieldFloat1(Float fieldFloat1) {
		this.fieldFloat1 = fieldFloat1;
	}
	public Float getFieldFloat2() {
		return fieldFloat2;
	}
	public void setFieldFloat2(Float fieldFloat2) {
		this.fieldFloat2 = fieldFloat2;
	}
	public Long getFieldLong1() {
		return fieldLong1;
	}
	public void setFieldLong1(Long fieldLong1) {
		this.fieldLong1 = fieldLong1;
	}
	public Long getFieldLong2() {
		return fieldLong2;
	}
	public void setFieldLong2(Long fieldLong2) {
		this.fieldLong2 = fieldLong2;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString1() {
		return fieldString1;
	}
	public void setFieldString1(String fieldString1) {
		this.fieldString1 = fieldString1;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString2() {
		return fieldString2;
	}
	public void setFieldString2(String fieldString2) {
		this.fieldString2 = fieldString2;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString3() {
		return fieldString3;
	}
	public void setFieldString3(String fieldString3) {
		this.fieldString3 = fieldString3;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString4() {
		return fieldString4;
	}
	
	
	public void setFieldString4(String fieldString4) {
		this.fieldString4 = fieldString4;
	}
 
 
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	public Date getTimeIssued() {
		return timeIssued;
	}
	public void setTimeIssued(Date timeIssued) {
		this.timeIssued = timeIssued;
	}
	public Date getTimeLastUpdated() {
		return timeLastUpdated;
	}
	public void setTimeLastUpdated(Date timeLastUpdated) {
		this.timeLastUpdated = timeLastUpdated;
	}
	 
	public Date getFieldDate3() {
		return fieldDate3;
	}
	public void setFieldDate3(Date fieldDate3) {
		this.fieldDate3 = fieldDate3;
	}
	public Date getFieldDate4() {
		return fieldDate4;
	}
	public void setFieldDate4(Date fieldDate4) {
		this.fieldDate4 = fieldDate4;
	}
	public Date getFieldDate5() {
		return fieldDate5;
	}
	public void setFieldDate5(Date fieldDate5) {
		this.fieldDate5 = fieldDate5;
	}
	public Long getFieldLong3() {
		return fieldLong3;
	}
	public void setFieldLong3(Long fieldLong3) {
		this.fieldLong3 = fieldLong3;
	}
	public Long getFieldLong4() {
		return fieldLong4;
	}
	public void setFieldLong4(Long fieldLong4) {
		this.fieldLong4 = fieldLong4;
	}
	public Long getFieldLong5() {
		return fieldLong5;
	}
	public void setFieldLong5(Long fieldLong5) {
		this.fieldLong5 = fieldLong5;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString10() {
		return fieldString10;
	}
	public void setFieldString10(String fieldString10) {
		this.fieldString10 = fieldString10;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString11() {
		return fieldString11;
	}
	public void setFieldString11(String fieldString11) {
		this.fieldString11 = fieldString11;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString12() {
		return fieldString12;
	}
	public void setFieldString12(String fieldString12) {
		this.fieldString12 = fieldString12;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString13() {
		return fieldString13;
	}
	public void setFieldString13(String fieldString13) {
		this.fieldString13 = fieldString13;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString14() {
		return fieldString14;
	}
	public void setFieldString14(String fieldString14) {
		this.fieldString14 = fieldString14;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString15() {
		return fieldString15;
	}
	public void setFieldString15(String fieldString15) {
		this.fieldString15 = fieldString15;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString5() {
		return fieldString5;
	}
	public void setFieldString5(String fieldString5) {
		this.fieldString5 = fieldString5;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString6() {
		return fieldString6;
	}
	public void setFieldString6(String fieldString6) {
		this.fieldString6 = fieldString6;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString7() {
		return fieldString7;
	}
	public void setFieldString7(String fieldString7) {
		this.fieldString7 = fieldString7;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString8() {
		return fieldString8;
	}
	public void setFieldString8(String fieldString8) {
		this.fieldString8 = fieldString8;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getFieldString9() {
		return fieldString9;
	}
	public void setFieldString9(String fieldString9) {
		this.fieldString9 = fieldString9;
	}
	
	
	
	
}

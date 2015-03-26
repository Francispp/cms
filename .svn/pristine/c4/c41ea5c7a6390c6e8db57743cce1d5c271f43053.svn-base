package com.cyberway.cms.form.domain;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;

import com.cyberway.cms.form.object.BaseDocument;

/**
 * 基本新闻信息表单(继承信息表单基类)
 * @author caiw
 *
 */
public class BasicDocument extends BaseDocument {
	@Field
	private Long id;
	
	protected String title;//标题	
	
	@Field
	private String body;
	//private Date timeIssued = new Date ();  
	
	@Field
	private String authorCname;
	
	@Field
	private String fieldString1;
	
	@Field
	private String fieldString2;
	
	@Field
	private String fieldString3;
	
	@Field
	private String fieldString4;
	
	@Field
	private String fieldString5;
	
	@Field
	private String fieldString6; 
	
	@Field
	private String fieldString7; 
	
	@Field
	private String fieldString8;
	
	@Field
	private String fieldString9;
	
	@Field
	private String fieldString10;
	
	@Field
	private String fieldString11;
	
	@Field
	private String fieldString12;
	
	@Field
	private String fieldString13;
	
	private Long orderNumber;
	
	@Field
	private Long fieldLong1;
	
	@Field
	private Long fieldLong2;
	
	@Field
	private Float fieldFloat1;
	
	@Field
	private Float fieldFloat2;
	
	@Field
	private Date fieldDate1;
	
	@Field
	private Date fieldDate2;
	
	@Field
	private String subBody;
	
	protected int issued;//文档的状态，草稿，正审，待发，已否，已返，已发
	
	@Override
	public String getStateName() {
		return DOCUMENT_STATE[issued];//取得文档状态
	}
	
	protected Long docType;
	
	@Override
	public String getTypeName() {
		return DOCUMENT_TYPE[docType == null ? 0 : docType.intValue()];//取得文档类型
	}
	
	@Field(index=Index.UN_TOKENIZED)


	public Long getOrderNumber() {
		return orderNumber;
	}

	public Long getDocType() {
		return docType;
	}

	public void setDocType(Long docType) {
		this.docType = docType;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public int getIssued() {
		return issued;
	}

	public void setIssued(int issued) {
		this.issued = issued;
	}
	
	
	@Field(index=Index.TOKENIZED)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getFieldString9() {
		return fieldString9;
	}

	public void setFieldString9(String fieldString9) {
		this.fieldString9 = fieldString9;
	}

	public String getFieldString10() {
		return fieldString10;
	}

	public void setFieldString10(String fieldString10) {
		this.fieldString10 = fieldString10;
	}

	public String getFieldString11() {
		return fieldString11;
	}

	public void setFieldString11(String fieldString11) {
		this.fieldString11 = fieldString11;
	}

	public String getFieldString12() {
		return fieldString12;
	}

	public void setFieldString12(String fieldString12) {
		this.fieldString12 = fieldString12;
	}

	public String getFieldString13() {
		return fieldString13;
	}

	public void setFieldString13(String fieldString13) {
		this.fieldString13 = fieldString13;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public String getSubBody() {
		Pattern p_html; 
        Matcher m_html;
		p_html = Pattern.compile("<[^>]+>",Pattern.CASE_INSENSITIVE); 
		subBody=body;
		if(StringUtils.isNotBlank(subBody)){
			m_html = p_html.matcher(subBody); 
			subBody = m_html.replaceAll("");
			subBody = subBody.replaceAll("&nbsp;", "").replaceAll("&amp;nbsp;", "");
	        if(subBody!=null && subBody.length()>50){
	        	subBody=subBody.substring(0, 50)+"...";
	        }
	        return subBody;	
		}
		return "";
        
	}

	@Field(index=Index.UN_TOKENIZED)
	public String getAuthorCname() {
		return authorCname;
	}
	public void setAuthorCname(String authorCname) {
		this.authorCname = authorCname;
	}
	
	@Field(index=Index.TOKENIZED)
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
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
	@Field(index=Index.TOKENIZED)
	public String getFieldString1() {
		return fieldString1;
	}
	public void setFieldString1(String fieldString1) {
		this.fieldString1 = fieldString1;
	}
	@Field(index=Index.TOKENIZED)
	public String getFieldString2() {
		return fieldString2;
	}
	public void setFieldString2(String fieldString2) {
		this.fieldString2 = fieldString2;
	}
	@Field(index=Index.TOKENIZED)
	public String getFieldString3() {
		return fieldString3;
	}
	public void setFieldString3(String fieldString3) {
		this.fieldString3 = fieldString3;
	}
	@Field(index=Index.TOKENIZED)
	public String getFieldString4() {
		return fieldString4;
	}
	public void setFieldString4(String fieldString4) {
		this.fieldString4 = fieldString4;
	}
	@Field(index=Index.TOKENIZED)
	public String getFieldString5() {
		return fieldString5;
	}
	public void setFieldString5(String fieldString5) {
		this.fieldString5 = fieldString5;
	} 
	
	@Field(index=Index.TOKENIZED)
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTimeLastUpdated() {
		return timeLastUpdated;
	}
	public void setTimeLastUpdated(Date timeLastUpdated) {
		this.timeLastUpdated = timeLastUpdated;
	}
	
}

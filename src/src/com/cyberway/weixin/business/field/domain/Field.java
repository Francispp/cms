package com.cyberway.weixin.business.field.domain;

import java.util.Date;

/**
 * 
 * @com.cyberway.weixin.field.domain.Field
 * TODO : 外勤
 * @author xiachao
 * @createDate：2015年3月9日
 */
public class Field {
	
	/**
	 * 
	 *外勤id
	 */
	private Long id;
	/**
	 * userId
	 */
	private String userId;
	/**
	 * user名
	 */
	private String userName;
	/**
	 * 外出事由（100字以内）
	 */
	private String purpose;
	/**
	 * 事由创建时间
	 */
	private Date createTime;
	/**
	 * 外出开始时间
	 */
	private Date startDate;
	/**
	 * 外出结束时间
	 */
	private Date endDate;
	/**
	 * 签到时间
	 */
	private Date signInTime;
	/**
	 * 签到地点
	 */
	private String signInPlace;
	/**
	 * 签到照片
	 */
	private String signInPic;
	/**
	 * 签到文本（140字以内）
	 */
	private String signInTxt;
	/**
	 * 签退时间
	 */
	private Date signOutTime;
	/**
	 * 签退地点
	 */
	private String signOutPlace;
	/**
	 * 签退照片
	 */
	private String signOutPic;
	/**
	 * 签退文本（100字以内）
	 */
	private String signOutTxt;
	/**
	 * 外勤地点
	 */
	private String fieldPlace;
	/**
	 * 外出类型:1为外出；2为其他；
	 */
	private int fieldType;
	/**
	 * 签到类型：1为签到/签退；2为仅签到；
	 */
	private int signType;
	/**
	 * 签到状态:0为未签到；1为已签到；2为已签退；
	 */
	private int signStatus;
	/**
	 * 事由
	 */
	private String cause;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getSignInTime() {
		return signInTime;
	}
	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}
	public String getSignInPlace() {
		return signInPlace;
	}
	public void setSignInPlace(String signInPlace) {
		this.signInPlace = signInPlace;
	}
	public String getSignInPic() {
		return signInPic;
	}
	public void setSignInPic(String signInPic) {
		this.signInPic = signInPic;
	}
	public String getSignInTxt() {
		return signInTxt;
	}
	public void setSignInTxt(String signInTxt) {
		this.signInTxt = signInTxt;
	}
	public Date getSignOutTime() {
		return signOutTime;
	}
	public void setSignOutTime(Date signOutTime) {
		this.signOutTime = signOutTime;
	}
	public String getSignOutPlace() {
		return signOutPlace;
	}
	public void setSignOutPlace(String signOutPlace) {
		this.signOutPlace = signOutPlace;
	}
	public String getSignOutPic() {
		return signOutPic;
	}
	public void setSignOutPic(String signOutPic) {
		this.signOutPic = signOutPic;
	}
	public String getSignOutTxt() {
		return signOutTxt;
	}
	public void setSignOutTxt(String signOutTxt) {
		this.signOutTxt = signOutTxt;
	}
	public String getFieldPlace() {
		return fieldPlace;
	}
	public void setFieldPlace(String fieldPlace) {
		this.fieldPlace = fieldPlace;
	}
	public int getFieldType() {
		return fieldType;
	}
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}
	public int getSignType() {
		return signType;
	}
	public void setSignType(int signType) {
		this.signType = signType;
	}
	public int getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(int signStatus) {
		this.signStatus = signStatus;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	
}


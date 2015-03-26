package com.cyberway.weixin.business.attendance.domain;


public class FieldRecord {
	 
	private Long id;
	/*
	 * 用户id
	 */
	private String userId;
	/*
	 * 用户名
	 */
	private String userName;
	/*
	 * 外勤事由
	 */
	private String reason;
	/*
	 * 签到时间
	 */
	private String signinTime;
	/*
	 * 签退时间
	 */
	private String signOutTime;
	/*
	 * 考勤状态
	 */
	private String state;
	/*
	 * 外出人
	 */
	private String fieldPerson;
	/*
	 * 开始时间
	 */
	private String startTime;
	/*
	 * 结束时间
	 */
	private String endTime;
	/*
	 * 外出地点
	 */
	private String fieldPlace;
	/*
	 * 外出类型
	 */
	private String fieldType;
	/*
	 * 备注
	 */
	private String note;
	
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSigninTime() {
		return signinTime;
	}
	public void setSigninTime(String signinTime) {
		this.signinTime = signinTime;
	}
	public String getSignOutTime() {
		return signOutTime;
	}
	public void setSignOutTime(String signOutTime) {
		this.signOutTime = signOutTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFieldPerson() {
		return fieldPerson;
	}
	public void setFieldPerson(String fieldPerson) {
		this.fieldPerson = fieldPerson;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getFieldPlace() {
		return fieldPlace;
	}
	public void setFieldPlace(String fieldPlace) {
		this.fieldPlace = fieldPlace;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	
	
	

}

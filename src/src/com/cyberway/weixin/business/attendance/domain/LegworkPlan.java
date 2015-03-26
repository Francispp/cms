package com.cyberway.weixin.business.attendance.domain;

import java.util.Date;
/**
 * 
 * @com.cyberway.weixin.business.attendance.domain.LegworkPlan
 * TODO :
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年3月23日 上午10:51:22
 */
public class LegworkPlan {
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
	 * 外出开始时间
	 */
	private Date startDate;
	/**
	 * 外出结束时间
	 */
	private Date endDate;
	/**
	 * 外勤地点
	 */
	private String fieldPlace;
	/**
	 * 外出类型:0为外出；1为其他；
	 */
	private int fieldType;
	/**
	 * 签到类型：0为签到/签退；1为仅签到；
	 */
	private int signType;
	/**
	 * 事由
	 */
	private String cause;
	/**
	 * 计划创建时间
	 */
	private Date createDate;
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
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}

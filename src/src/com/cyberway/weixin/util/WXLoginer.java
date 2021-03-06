package com.cyberway.weixin.util;

import java.io.Serializable;

public class WXLoginer implements Serializable{

	/**  
	* serialVersionUID:TODO（用一句话描述这个变量表示什么）
	*/  
	private static final long serialVersionUID = -6513570365636702903L;

	/**
	 * 用户id
	 */
    private String userid;

	/**
	 * 成员名称 
	 */
    private String name;
    /**
     * 职位信息
     */
    private String position;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 微信号
     */
    private String weixinid;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 关注状态: 1=已关注，-1=已冻结，0=未关注 
     */
    private Integer status;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    

}

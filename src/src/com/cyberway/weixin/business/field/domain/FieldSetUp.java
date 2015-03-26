package com.cyberway.weixin.business.field.domain;

/**
 * 外勤设置
 * @author Administrator
 *
 */
public class FieldSetUp {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 签到拍照设置：0为可以不拍照签到；1为必须拍照签到
	 */
	private int signInSet;
	/**
	 * 签到拍照设置：0为可以不拍照签退；1为必须拍照签退
	 */
	private int signOutSet;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getSignInSet() {
		return signInSet;
	}
	public void setSignInSet(int signInSet) {
		this.signInSet = signInSet;
	}
	public int getSignOutSet() {
		return signOutSet;
	}
	public void setSignOutSet(int signOutSet) {
		this.signOutSet = signOutSet;
	}


}
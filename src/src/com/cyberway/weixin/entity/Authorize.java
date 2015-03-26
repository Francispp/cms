package com.cyberway.weixin.entity;

public class Authorize {
	//临时授权码
	private String  authCode;
	 // 凭证有效时间，单位：秒  
    private int expiresIn;
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
    
    
}

package com.cyberway.weixin.entity;

import java.util.Date;

/**
 * 
 * @com.cyberway.weixin.entity.AccessToken
 * TODO :微信通用接口凭证 
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015-1-27 上午09:54:19
 */
public class AccessToken {
	// 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  
    private int expiresIn;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
    
}

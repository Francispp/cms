package com.cyberway.weixin.entity;

public class JSSDK {

	private String ticket; 
	// 凭证有效时间，单位：秒  
    private int expiresIn;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}

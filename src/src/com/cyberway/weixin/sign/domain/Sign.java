package com.cyberway.weixin.sign.domain;

import java.io.Serializable;

public class Sign implements Serializable{

	/*
	 * 考勤地点实体
	 */
	
	/*
	 * 地点ID
	 */
	private Long id;
	
	/*
	 * 地点名称
	 */
	private String address;
	
	/*
	 * 详细地址
	 */
	private String detailaddress;
	
	/*
	 * 经度
	 */
	private Long longitude;
	
	/*
	 * 纬度
	 */
	private Long latitude;
	
	/*
	 * 考勤范围
	 */
	private String range;

	
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetailaddress() {
		return detailaddress;
	}

	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}
	
	
}

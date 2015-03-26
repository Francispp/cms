package com.cyberway.common.domain;

import java.io.Serializable;

/**
 * 地区：省市县选择
 * @author Dicky
 * 2013-12-4 16:35:58
 */
public class CoreArea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9071281247239860708L;

	/**
	 * 天气预报ID
	 * 来源于中国气象局
	 */
	private String id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 身份证编码
	 */
	private String code;
	
	/**
	 * 电话区号
	 */
	private String area_code;
	
	/**
	 * 邮政编码
	 */
	private String zip_code;
	
	/**
	 * 详细地址
	 */
	private String detail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}

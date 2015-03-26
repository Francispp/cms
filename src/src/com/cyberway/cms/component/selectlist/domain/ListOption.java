package com.cyberway.cms.component.selectlist.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @hibernate.class table="CMS_LISTOPTION"
 * @author Administrator
 * 
 */
public class ListOption implements Serializable {

	private Long	oid;
	private Long	listTitleId;
	private String	key;
	private String	value;
	private Integer	pos;	          // 排序

	private Date	lastUpdateTime;
	private String	lastUpdateUser;
	private Long	lastUpdateUserid;

	public ListOption() {
		super();
	}

	public ListOption(String key, String value, Integer pos, Date lastUpdateTime, String lastUpdateUser, Long lastUpdateUserid) {
		super();
		this.key = key;
		this.value = value;
		this.pos = pos;
		this.lastUpdateTime = lastUpdateTime;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateUserid = lastUpdateUserid;
	}

	public ListOption(String key, String value, Integer pos) {
		super();
		this.key = key;
		this.value = value;
		this.pos = pos;
	}

	/**
	 * @hibernate.id generator-class = "native"
	 * @return
	 */
	public Long getOid() {
		return oid;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public Long getListTitleId() {
		return listTitleId;
	}

	/**
	 * @hibernate.property column="optkey"
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @hibernate.property column="optvalue"
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @hibernate.property type="java.sql.Timestamp"
	 * @return
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public Long getLastUpdateUserid() {
		return lastUpdateUserid;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public Integer getPos() {
		return pos;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}

	public void setListTitleId(Long listTitleId) {
		this.listTitleId = listTitleId;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

}

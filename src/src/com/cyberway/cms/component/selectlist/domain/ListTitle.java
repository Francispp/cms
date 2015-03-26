package com.cyberway.cms.component.selectlist.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @hibernate.class table="CMS_LISTTITLE"
 * @author Administrator
 * 
 */
public class ListTitle implements Serializable {

	private Long	        oid;
	private String	        title;
	private String	        type;	    // 分类
	private String	        key;	    // 页面中根据key取得列表 唯一
	private String	        remark;

	private Set<ListOption>	options	= new TreeSet<ListOption>(new Comparator<ListOption>() {

		                                public int compare(ListOption o1, ListOption o2) {
			                                return o1.getPos() - o2.getPos();
		                                }
	                                });

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
	public String getTitle() {
		return title;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @hibernate.property column="t_key" unique="true"
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @hibernate.property column="t_type"
	 * @return
	 */
	public String getType() {
		return type;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @hibernate.set order-by="pos" cascade="save-update" inverse="false"
	 * @hibernate.collection-key column="listTitleId"
	 * @hibernate.collection-one-to-many 
	 *                                   class="com.cyberway.cms.component.selectlist.domain.ListOption"
	 * @return
	 */
	public Set<ListOption> getOptions() {
		return options;
	}

	public void setOptions(Set<ListOption> options) {
		this.options = options;
	}

}

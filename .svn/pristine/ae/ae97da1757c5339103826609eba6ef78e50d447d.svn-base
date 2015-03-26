package com.cyberway.common.menu.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * com.cyberway.common.menu.domain.MenuResource
 * 
 * @author Janice Yang
 * 
 * @createTime 2012-2-24 上午11:55:39
 * 
 * @Description:系统后台菜单资源
 * 
 */
public class MenuResource {
	private Long	id;

	/**
	 * 该菜单所属的父菜单id
	 */
	private Long	pid;

	/**
	 * 菜单代码
	 */
	private String	menuCode;

	/**
	 * 菜单名称
	 */
	private String	menuName;
	
	/**
	 * 是否显示:"0"为不显示,"1"为显示
	 */
	private Integer isView = 1;
	
	/**
	 * 级别:"0"代表系统级别,"1"代表站点级别,"2"代表开放
	 */
	private Integer grade = 2;

	/**
	 * 菜单执行的url
	 */
	private String	url;

	/**
	 * 顶级菜单的第二个执行路径
	 */
	private String	subUrl;
	
	/**
	 * 顶级菜单的第三个路径
	 */
	private String threedUrl;

	/**
	 * 图片路径
	 */
	private String	          icoPath;

	/**
	 * 菜单排序号
	 */
	private int	              orderNo;

	/**
	 * 子菜单集合
	 */
	private Set<MenuResource>	subMenuResources	= new HashSet<MenuResource>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<MenuResource> getSubMenuResources() {
		return subMenuResources;
	}

	public void setSubMenuResources(Set<MenuResource> subMenuResources) {
		this.subMenuResources = subMenuResources;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getIcoPath() {
		return icoPath;
	}

	public void setIcoPath(String icoPath) {
		this.icoPath = icoPath;
	}
	
	public String getSubUrl() {
		return subUrl;
	}

	public void setSubUrl(String subUrl) {
		this.subUrl = subUrl;
	}

	public String getThreedUrl() {
    	return threedUrl;
    }

	public void setThreedUrl(String threedUrl) {
    	this.threedUrl = threedUrl;
    }

	public Integer getIsView() {
    	return isView;
    }

	public void setIsView(Integer isView) {
    	this.isView = isView;
    }

	public Integer getGrade() {
    	return grade;
    }

	public void setGrade(Integer grade) {
    	this.grade = grade;
    }

}

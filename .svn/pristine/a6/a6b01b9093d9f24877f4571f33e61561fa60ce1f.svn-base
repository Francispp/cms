package com.cyberway.common.xtree;

import java.io.Serializable;

public class DefineTree implements Serializable {

	/**
	 * Required. 树实体对象
	 */
	private Class pojoTree;
	/**
	 * Required. 节点属性名称
	 */
	private String nodePropertyName;
	/**
	 * Required. 子节点属性名称
	 */
	private String subNodeSetPropertyName;
	
	/**
	 * 节点ID
	 */
	private Long nodeId;
	
	/**
	 * 父节点ID 
	 */
	private Long parentNodeId;
	
	
	/**
	 * Optional. The source for the xml file to load when expanded.
	 */
	private String src;

	/**
	 * Optional. The action (uri) associated with the tree item.
	 */
	private String action;

	/**
	 * Optional. The icon image to use for this item. In case this item is a
	 * folder this will be used when the folder is closed.
	 */
	private String icon;

	/**
	 * Optional. The icon image to use for this item when it is opened. This
	 * is only used for folder items that are opened/expanded.
	 */
	private String openIcon;
	//节点的鼠标事件
	private String nodeOnMouseup;
	private String radio;//是否带单选框
	private String checkBox;//是否带多选框
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Class getPojoTree() {
		return pojoTree;
	}
	public void setPojoTree(Class pojoTree) {
		this.pojoTree = pojoTree;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getSubNodeSetPropertyName() {
		return subNodeSetPropertyName;
	}
	public void setSubNodeSetPropertyName(String subTreePropertyName) {
		this.subNodeSetPropertyName = subTreePropertyName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getOpenIcon() {
		return openIcon;
	}
	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}
	public String getNodePropertyName() {
		return nodePropertyName;
	}
	public void setNodePropertyName(String nodePropertyName) {
		this.nodePropertyName = nodePropertyName;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public Long getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(Long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public String getNodeOnMouseup() {
		return nodeOnMouseup;
	}
	public void setNodeOnMouseup(String nodeOnMouseup) {
		this.nodeOnMouseup = nodeOnMouseup;
	}
	public String getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
}

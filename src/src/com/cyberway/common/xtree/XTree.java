package com.cyberway.common.xtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cyberway.core.utils.StringUtil;

/**
 * 树对象
 * 
 * @author caiw
 * 
 */
public class XTree implements Serializable {
	
	final static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
	final static String TREE_ROOT = "tree";
	
	private List<Node> listNode =null;
	
	@SuppressWarnings("unchecked")
	public XTree(){
		listNode = new ArrayList();
	}
	/**
	 * 得到描述树的XML格式串 
	 * 
	 * <?xml version="1.0"?> 
	 * <!DOCTYPE tree SYSTEM "tree.dtd">
	 *  <tree>
	 *   <tree text="Loaded Item 1" action="href://webfx.eae.net" />
	 *   <tree text="Loaded Item 2">
	 *     <tree text="Loaded Item 2.1" action="javascript:alert(2.1)" />
	 *   </tree>
	 *   <tree text="Load &quot;tree1.xml&quot;" src="tree1.xml" />
	 *  </tree>
	 * 
	 * @return
	 */
	public String getXmlTree() {

		StringBuffer xmlTree = new StringBuffer();
		//xmlTree.append(XML_HEAD);
		xmlTree.append(" <").append(TREE_ROOT).append(">");
		if(listNode != null){
			for(int i=0;i<listNode.size();i++){
				Node node = listNode.get(i);
				xmlTree.append(node.getXMLTreeByNode());
			}
		}
		xmlTree.append("</").append(TREE_ROOT).append(">");
		return xmlTree.toString();
	}
	
	/**
	 * 初始化树节点
	 * @param node
	 */
	public Node initNode(){
		return new Node();
	}
	
	/**
	 * 增加树节点
	 * @param node
	 */
	public void addNode(Node node){
		if(listNode != null && !listNode.contains(node)){
			listNode.add(node);
		}
	}
	
	/**
	 * 树节点对象
	 * 
	 * @author caiw
	 * 
	 */
	public class Node implements Serializable {
		
		final static String NODE_DESC = "tree";
		
		/**
		 * 子节点列表
		 */
		private List<Node> subNodeList ;
		
		/**
		 * Required. The text label for the tree item.
		 */
		private String text;

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
		
		/**
		 * Optional. If target="_new" then new window open .
		 */
		private String target;
		//节点的鼠标事件
		private String onMouseup;	
		private String radio;//是否带单选框
		private String checkBox;//是否带多选框		
		/**
		 * 得到当前节点及其子节点的XML描述
		 * @return
		 */
		public String getXMLTreeByNode(){
			StringBuffer xmlTree = new StringBuffer();
			GenXMLByRecursionNode(this,xmlTree);
			return xmlTree.toString();
		}
		

		/**
		 * 得到描述当前节点的XML格式串 
		 * 
		 * <?xml version="1.0"?>		 * 
		 * <!DOCTYPE tree SYSTEM "tree.dtd">
		 *  <tree>
		 *   <tree text="Loaded Item 1" action="href://webfx.eae.net" />
		 *   <tree text="Loaded Item 2">
		 *     <tree text="Loaded Item 2.1" action="javascript:alert(2.1)" />
		 *   </tree>
		 *   <tree text="Load &quot;tree1.xml&quot;" src="tree1.xml" />
		 *  </tree>
		 * 
		 * @return
		 */
		private String getXmlNode() {
			StringBuffer xmlTree = new StringBuffer();
			xmlTree.append("<").append(NODE_DESC);
			if (!StringUtil.isEmpty(text)) {
				xmlTree.append(" text=\"").append(text).append("\"");
			}
			if (!StringUtil.isEmpty(src)) {
				String encodeString /*= UtilString.toUtf8String(src)*/;
				encodeString = StringUtil.HTMLEncoding(src);
				xmlTree.append(" src=\"").append(encodeString).append("\"");
			}
			if (!StringUtil.isEmpty(action)) {
				String encodeString /*= UtilString.toUtf8String(action)*/;
				encodeString = StringUtil.HTMLEncoding(action);				
				xmlTree.append(" action=\"").append(encodeString).append("\"");
			}
			if (!StringUtil.isEmpty(onMouseup)) {
				String encodeString /*= UtilString.toUtf8String(action)*/;
				encodeString = StringUtil.HTMLEncoding(onMouseup);	
				System.out.println("onMouseup:"+onMouseup);
				xmlTree.append(" onMouseup=\"").append(encodeString).append("\"");
			}			
			if (!StringUtil.isEmpty(icon)) {
				xmlTree.append(" icon=\"").append(icon).append("\"");
			}
			if (!StringUtil.isEmpty(openIcon)) {
				xmlTree.append(" openIcon=\"").append(openIcon).append("\"");
			}
			if (!StringUtil.isEmpty(target)) {
				xmlTree.append(" target=\"").append(target).append("\"");
			}
			if (!StringUtil.isEmpty(radio)) {
				xmlTree.append(" radio=\"").append(radio).append("\"");
			}
			if (!StringUtil.isEmpty(checkBox)) {
				xmlTree.append(" checkBox=\"").append(checkBox).append("\"");
			}			
			xmlTree.append(">");
			return xmlTree.toString();
		}
		
		/**
		 * 递归得到节点及子节点的XML描述
		 * @param node
		 * @param xmlTree
		 */
		private void GenXMLByRecursionNode(Node node,StringBuffer xmlTree){
			xmlTree.append(node.getXmlNode());
			List<Node> _subNodeList = node.getSubNodeList();
			if(_subNodeList!=null && _subNodeList.size()>0){
				Iterator itaSubNodeList=_subNodeList.iterator();
				while(itaSubNodeList.hasNext()){
					Node _node = (Node) itaSubNodeList.next();
					//Log.debug("\n GenXMLByRecursionNode:"+_node.text);
					GenXMLByRecursionNode(_node,xmlTree);
				}
			}
			xmlTree.append("</").append(NODE_DESC).append(">");
			return;
		}
		
		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
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

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public List<Node> getSubNodeList() {
			return subNodeList;
		}

		public void setSubNodeList(List<Node> subNodeList) {
			this.subNodeList = subNodeList;
		}


		public String getTarget() {
			return target;
		}


		public void setTarget(String target) {
			this.target = target;
		}


		public String getOnMouseup() {
			return onMouseup;
		}


		public void setOnMouseup(String onMouseup) {
			this.onMouseup = onMouseup;
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

	public List<Node> getListNode() {
		return listNode;
	}

	public void setListNode(List<Node> listNode) {
		this.listNode = listNode;
	}
}

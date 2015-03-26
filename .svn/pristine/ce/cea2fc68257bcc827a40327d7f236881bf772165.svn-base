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
public class DHtmlXTree implements Serializable {
	
	final static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
	final static String TREE_ROOT = "tree";
	
	private List<Node> listNode =null;
	private String treeId="0";
	private String defaultIm0;
	private String defaultIm1;
	private String defaultIm2;
	private boolean isDynamicLoad=false;//动态装载
	
	
	@SuppressWarnings("unchecked")
	public DHtmlXTree(){
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
	public String getDHtmlXmlTree() {

		StringBuffer xmlTree = new StringBuffer();
		xmlTree.append(XML_HEAD);
		//xmlTree.append(XML_HEAD);
		xmlTree.append("\n<").append(TREE_ROOT).append(" id=\""+treeId+"\">");
		if(listNode != null){
			for(int i=0;i<listNode.size();i++){
				Node node = listNode.get(i);
				xmlTree.append(node.getXMLTreeByNode());
			}
		}
		xmlTree.append("\n</").append(TREE_ROOT).append(">");
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
		
		final static String NODE_DESC = "item";
		
		public Node(){
			
		}
		/**
		 * 子节点列表
		 */
		private List<Node> subNodeList ;
		/**
		 * Required. The id for the tree item.
		 */		
		private String id;
		
		
		/**
		 * 是否打开此节点
		 */
		private Boolean		open		= new Boolean(false);
		/**
		 * 是否已经勾选
		 */
		private Boolean		checked		= new Boolean(false);
		
		private Boolean checkBox = new Boolean(false);
		/**
		 * Required. The text label for the tree item.
		 */
		private String text;

		/**
		 * Optional. The action (uri) associated with the tree item.
		 */
		private String action;

		private String child;
		private String im0;
		private String im1;
		private String im2;	
		
		/**
		 * Optional. If target="_new" then new window open .
		 */
		
		
		
		private String target;
		//节点的鼠标事件
		private String onMouseup;	
		private String radio;//是否带单选框
		
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
			xmlTree.append("\n<").append(NODE_DESC);
			if (!StringUtil.isEmpty(text)) {
				xmlTree.append(" text=\"").append(text).append("\"");
			}
			if (!StringUtil.isEmpty(id)) {
				xmlTree.append(" id=\"").append(id).append("\"");
			}			
/*			if (!StringUtil.isEmpty(action)) {
				String encodeString = UtilString.toUtf8String(action);
				encodeString = StringUtil.HTMLEncoding(action);				
				xmlTree.append(" action=\"").append(encodeString).append("\"");
			}*/
/*			if (!StringUtil.isEmpty(onMouseup)) {
				String encodeString = UtilString.toUtf8String(action);
				encodeString = StringUtil.HTMLEncoding(onMouseup);	
				System.out.println("onMouseup:"+onMouseup);
				xmlTree.append(" onMouseup=\"").append(encodeString).append("\"");
			}*/	
			if (StringUtil.isEmpty(im0))
				im0=getDefaultIm0();
			if (StringUtil.isEmpty(im1))
				im1=getDefaultIm1();
			if (StringUtil.isEmpty(im2))
				im2=getDefaultIm2();
			
			if (!StringUtil.isEmpty(child)) {
				xmlTree.append(" child=\"").append(child).append("\"");
			}			
			if (!StringUtil.isEmpty(im0)) {
				xmlTree.append(" im0=\"").append(im0).append("\"");
			}				
			if (!StringUtil.isEmpty(im1)) {
				xmlTree.append(" im1=\"").append(im1).append("\"");
			}
			if (!StringUtil.isEmpty(im2)) {
				xmlTree.append(" im2=\"").append(im2).append("\"");
			}
			/*			if (!StringUtil.isEmpty(target)) {
			xmlTree.append(" target=\"").append(target).append("\"");
		}*/
			/*if (!StringUtil.isEmpty(radio)) {
			xmlTree.append(" radio=\"").append(radio).append("\"");
		}*/	
			
			if (getCheckBox()) {
				xmlTree.append(" checkBox=\"1\"");
			}	
			if (getOpen()) {
				xmlTree.append(" open=\"1\"");
			}
			if (getChecked()) {
				xmlTree.append(" checked=\"1\"");
			}
			xmlTree.append(">");
			if (!StringUtil.isEmpty(action)) {
				String encodeString = StringUtil.toUtf8String(action);
				encodeString = StringUtil.HTMLEncoding(action);				
				xmlTree.append("\n<userdata name=\"url\">").append(encodeString).append("</userdata>");
			}					
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
			xmlTree.append("\n</").append(NODE_DESC).append(">");
			return;
		}
		
		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
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


		public Boolean getCheckBox() {
			return checkBox;
		}


		public void setCheckBox(Boolean checkBox) {
			this.checkBox = checkBox;
		}


		public String getRadio() {
			return radio;
		}


		public void setRadio(String radio) {
			this.radio = radio;
		}


		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getIm0() {
			return im0;
		}


		public void setIm0(String im0) {
			this.im0 = im0;
		}


		public String getIm1() {
			return im1;
		}


		public void setIm1(String im1) {
			this.im1 = im1;
		}


		public String getIm2() {
			return im2;
		}


		public void setIm2(String im2) {
			this.im2 = im2;
		}


		public String getChild() {
			return child;
		}


		public void setChild(String child) {
			this.child = child;
		}
		
		public Boolean getOpen() {
			return open;
		}

		public void setOpen(Boolean open) {
			this.open = open;
		}
		
		public void setChecked(Boolean checked) {
			this.checked = checked;
		}
		
		public Boolean getChecked() {
			return checked;
		}
	}

	public List<Node> getListNode() {
		return listNode;
	}

	public void setListNode(List<Node> listNode) {
		this.listNode = listNode;
	}
	
	public static void main(String[] args) {
		DHtmlXTree	dx=new DHtmlXTree();
		Node nd=dx.initNode();
		nd.setId("1");
		nd.setText("text");
		nd.setAction("user.action");
		List subnds=new ArrayList();
		subnds.add(getNode("2","text2","test.action"));
		nd.setSubNodeList(subnds);
		dx.addNode(nd);
		System.out.println(dx.getDHtmlXmlTree());
	}
	private static Node getNode(String id ,String text,String url){
		DHtmlXTree	dx=new DHtmlXTree();
		Node nd=dx.initNode();
		nd.setId(id);
		nd.setText(text);
		nd.setAction(url);
		return nd;
	}
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public String getDefaultIm0() {
		return defaultIm0;
	}
	public void setDefaultIm0(String defaultIm0) {
		this.defaultIm0 = defaultIm0;
	}
	public String getDefaultIm1() {
		return defaultIm1;
	}
	public void setDefaultIm1(String defaultIm1) {
		this.defaultIm1 = defaultIm1;
	}
	public String getDefaultIm2() {
		return defaultIm2;
	}
	public void setDefaultIm2(String defaultIm2) {
		this.defaultIm2 = defaultIm2;
	}
	public boolean getIsDynamicLoad() {
		return isDynamicLoad;
	}
	public void setIsDynamicLoad(boolean isDynamicLoad) {
		this.isDynamicLoad = isDynamicLoad;
	}
	
	
}

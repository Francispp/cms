	<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
</head>
<link href="${sytlePath}/style.css" rel="stylesheet" type="text/css"/>

<body style="background:#F1F1F1;">
<div id="menu_tree_4" ></div>
</body>
 <script language="javascript">
	//初始化树
       function initTree(treeBox,xmlUrl){
	 tree=new dhtmlXTreeObject(treeBox,"100%","100%",0);
	 //enable Drag&Drop
	 tree.enableDragAndDrop(0);
	 //set my Drag&Drop handler
	 //tree.setDragHandler(myDragHandler); 
	 tree.loadXML(xmlUrl);
	 tree.setOnClickHandler(doOnClickTree);
	 return tree;
	}
 //树上单击事件
       function doOnClickTree(nodeId){
	var myUrl = tree.getUserData(nodeId,"url");
	if(nodeId=='logout')
	parent.logout();
	 //confirmMessage( '您确定退出系统么?','提示', parent.logout);
	 //parent.Ext.MessageBox.confirm('提示', '您确定退出系统么?', parent.logout);
	else{
	if(myUrl==null||myUrl=='')
	return ;
	parent.setMainFrameUrl(myUrl);	
	}
	}		
	initTree('menu_tree_4','${ctx}/portals/cms3/menuxml.jsp');
</script>
</html>
 
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="类别树" />
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<%@ include file="/common/menu/menu.inc"%>
<script src="${ctx}/common/core_js/core.js"></script>

<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
 
<script type="text/javascript">
	//点击树节点执行的方法
	function onclickItem(value) {
		var myUrl='';
		if(value.indexOf('R_') != -1) {
			myUrl='';
		}else{
		myUrl='${ctx}/base/comtemplate!comTabbar_Select.action?temLibraryId='+value;
		}
		top.setMainFrameUrl(myUrl);
	}

	function myDragHandler(idFrom, idTo) {
	}

	var treeSelectId = "";
	function OnClick() {
		closeAllMenu();
	}
	function onMenuClick(id) {
		alert("Menu item " + id + " was clicked");
	}
</script>
<ww:head/>
</head>

<body  class="tree-bg">
<div id="application-tree"></div> 

	
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
	tree=new dhtmlXTreeObject("application-tree","100%","100%",'P_');
	tree.enableDragAndDrop(1);
	tree.setDragHandler(myDragHandler);
	tree.loadXML("${ctx}/base/temLibrary!xml.action");
	tree.setOnClickHandler(onclickItem);
</script>
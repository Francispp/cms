<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="类别树" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		if(isNaN(value)){
			if(value.indexOf('R_') != -1) {
				myUrl='${ctx}/base/commontype!listCommonType.action?parentID='+value;
			}else{
		   		myUrl='${ctx}/base/commontype!listCommonType.action?parentID='+value;
			}
			top.setMainFrameUrl(myUrl);
		}else{
			myUrl='${ctx}/base/commontype!listCommonType.action?parentID='+value;
			top.setMainFrameUrl(myUrl);
		}
	}
	//点击树节点执行的方法
// 	function onclickItem(value) {
//// 	function onclickItem(value) {
// 		var myUrl='';
// 		if(isNaN(value)){
// 			if(value.indexOf('R_') != -1) {
// 				myUrl='${ctx}/base/commontype!listCommonType.action';
// 			}else{
// 		   		myUrl='${ctx}/base/commoninfo!list.action?coreCommonTypeId='+value;
// 			}
// 			top.setMainFrameUrl(myUrl);
// 		}else{
// 			myUrl='${ctx}/base/commoninfo!list.action?coreCommonTypeId='+value;
// 			top.setMainFrameUrl(myUrl);
// 		}
// 	}
// 	}

	//拖动时，事件
	function myDragHandler(idFrom, idTo) {
		//alert(document.getElementById('application-tree1').in);
		if (isNaN(idFrom)) {
			alert('当前选择的对象不能移动！');
			return false;
		}

		var rt = true;
		var obj = new Array;
		obj[0] = "" + idFrom;
		if (!isNaN(idTo)) {//若idTo为P_开头，是树节点id
			obj[1] = "" + idTo;
			obj[2] = "";//
		} else {
			obj[1] = "";
			obj[2] = "" + idTo.substring(2);//
		}
		ExecuteService("if(reply.getResult()=='true'){}else{ alert('操作失败!');location.reload();}", 'commonTypeService', 'setParentType', obj);

		return rt;
	}

	var treeSelectId = "";
	/*************************
	 * 右键菜单
	 * script代码
	 *************************/
	document.onclick = OnClick;
	//关闭所有右击菜单
	function closeAllMenu() {
		HideAll("ItemTreeMenu", 0);
		HideAll("RootTreeMenu", 0);
	}
	//单击事件
	function OnClick() {
		closeAllMenu();
	}

	//新增
	function addTreeItem(pid) {
		 top.setMainFrameUrl('/base/commontype!listBySite.action?parentid=' + pid);
		//parent.frames['main_frame'].location.href = '/base/commontype!listBySite.action?parentid=' + pid;
	}

	//新增
	function addRootTreeItem(siteId) {
		/**
		if(isNaN(siteId)){
			var pid=siteId.substring(2);
			parent.frames['main_frame'].location.href="commontype!listCommonType.action?siteid="+pid;
		}
		 */
		 top.setMainFrameUrl("/base/commontype!listCommonType.action");
	}

	//删除
	function removeTreeItem(selectid) {
		if (confirm('您确定要删除选择的记录吗？')) {
			parent.location.href = "menu!delete.action?keys=" + selectid;
		}
	}
	//刷新
	function refreshMenu() {
		location.reload()
	}
	var cut_copy_menu_code = "";//选择的编号
	var flagCutOrCpy = "";//选择的操作

	//在树上的右击事件
	function myRightClickHandler(nodeid) {
		treeSelectId = '' + nodeid;
		if (isNaN(nodeid))
			OnMouseUpExt('RootTreeMenu');
		else
			OnMouseUpExt('ItemTreeMenu');
	}

	function onMenuClick(id) {
		alert("Menu item " + id + " was clicked");
	}
</script>
<ww:head/>
</head>

<%--

<body oncontextmenu="window.event.returnValue=false" style="height:100%" >
 --%>
<body  class="tree-bg" >
<div id="application-tree${cmsSite.oid}"></div> 

	
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
	tree${cmsSite.oid}=new dhtmlXTreeObject("application-tree${cmsSite.oid}","100%","100%",'P_${cmsSite.oid}');
	 //enable Drag&Drop
	tree${cmsSite.oid}.enableDragAndDrop(1);
	 //set my Drag&Drop handler
	tree${cmsSite.oid}.setDragHandler(myDragHandler);
	tree${cmsSite.oid}.loadXML("${ctx}/base/commontype!xml.action?siteid=${siteid}");
	tree${cmsSite.oid}.setOnClickHandler(onclickItem);
	tree${cmsSite.oid}.setOnRightClickHandler(myRightClickHandler);
  
	//ItemTreeMenu right menu
	var ItemTreeMenu = new RightMenu("ItemTreeMenu");
	//ItemTreeMenu.AddItem("update11","","","子类别管理 ","ItemTreeMenu","javascript:addTreeItem(treeSelectId);");
	ItemTreeMenu.AddItem("update121","","","删除类别 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
	ItemTreeMenu.AddItem("update10","","","刷新类别 ","ItemTreeMenu","javascript:refreshMenu();");   
	document.writeln(ItemTreeMenu.GetMenu());  
	   
	var RootTreeMenu = new RightMenu("RootTreeMenu");
	RootTreeMenu.AddItem("update2001","","","类别管理 ","RootTreeMenu","javascript:addRootTreeItem(treeSelectId);"); 
	RootTreeMenu.AddItem("update102","","","刷新类别 ","RootTreeMenu","javascript:refreshMenu();");
	document.writeln(RootTreeMenu.GetMenu());    
  
   </script>
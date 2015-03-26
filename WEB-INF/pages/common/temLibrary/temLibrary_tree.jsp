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
		if(value.indexOf('R_') != -1) {
			myUrl='';
		}else{
		myUrl='${ctx}/base/comtemplate!comTabbar.action?temLibraryId='+value;
		}
		top.setMainFrameUrl(myUrl);
	}

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
		ExecuteService("if(reply.getResult()=='true'){}else{ alert('操作失败!');location.reload();}", 'temLibraryService', 'setParentType', obj);

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
		global_ab.fn.popWindow.addPopWindow("base/temLibrary!editCommonType.action?parentid="+pid, "500px", "175px", false);
		location.reload();
	}

	//删除
	function removeTreeItem(selectid) {
		if (confirm('您确定要删除选择的记录吗？')) {
			top.setMainFrameUrl("/base/temLibrary!deleteCommonType.action?keys=" + selectid);
			top.setMainFrameUrl("");
		}
		location.reload();
	}

	//编辑
	function editTreeItem(selectid) {
		global_ab.fn.popWindow.addPopWindow("/base/temLibrary!editCommonType.action?id=" + selectid ,"500px", "175px", false);
		location.reload();
	}
	//刷新
	function refreshMenu() {
		location.reload();
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
	tree.setOnRightClickHandler(myRightClickHandler);
  
	var ItemTreeMenu = new RightMenu("ItemTreeMenu");
	ItemTreeMenu.AddItem("update123","","","新增类别 ","ItemTreeMenu","javascript:addTreeItem(treeSelectId);");
	ItemTreeMenu.AddItem("update122","","","编辑类别 ","ItemTreeMenu","javascript:editTreeItem(treeSelectId);");
	ItemTreeMenu.AddItem("update121","","","删除类别 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
	ItemTreeMenu.AddItem("update10","","","刷新类别 ","ItemTreeMenu","javascript:refreshMenu();");   
	document.writeln(ItemTreeMenu.GetMenu());  
	   
	var RootTreeMenu = new RightMenu("RootTreeMenu");
	RootTreeMenu.AddItem("update128","","","新增类别 ","RootTreeMenu","javascript:addTreeItem(treeSelectId);");
	RootTreeMenu.AddItem("update102","","","刷新类别 ","RootTreeMenu","javascript:refreshMenu();");
	document.writeln(RootTreeMenu.GetMenu());    
  
   </script>
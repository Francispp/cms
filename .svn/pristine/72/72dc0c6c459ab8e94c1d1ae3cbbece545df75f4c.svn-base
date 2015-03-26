<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/menu/menu.inc"%>
<script src="${ctx}/common/core_js/core.js"></script>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script type="text/javascript"> 


//点击树节点执行的方法
function onclickItem(value) {
    if(value == "role_list")
    	{
    	   parent.frames['main_frame'].location.href='/base/role.action';
    	}
    else
	parent.frames['main_frame'].location.href='/base/role!roleDetail.action?id='+value;
}
function editRole(value){
	global_ab.fn.popWindow.addPopWindow('base/role!add.action?id=',"600px","420px",false);
}

var treeSelectId="";
/*************************
* 右键菜单
* script代码
*************************/
document.onclick=OnClick;
//关闭所有右击菜单
function closeAllMenu() {
  HideAll("RootTreeMenu",0);
  HideAll("ItemTreeMenu",0);

}
//单击事件
function OnClick(){
closeAllMenu();
} 


//在树上的右击事件
function myRightClickHandler(nodeid){
}
//刷新
function refreshMenu(){
	location.reload()
}
function myDragHandler(){
	
}


</script>
</head>
<body style="background:#F1F1F1;" onmouseup="OnMouseUp('RootTreeMenu');" oncontextmenu="window.event.returnValue=false">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<c:if test="${not empty session.ActionError}">
  <script>alertMessage("${session.ActionError}");</script>
  <% session.removeAttribute("ActionError"); %>
</c:if>
<table align="center" width="100%"  hight="100%" border="0">
     <tr>
            <td width="100%" align="left" hight="100%"> 
 <div id="siteTree_Root"></div> 
 <br>
            </td>
    </tr>               
</table> 
</body>
</html>
<script language="javascript">

<!--
  
  treeRoot = new dhtmlXTreeObject('siteTree_Root','100%','100%','S_Root');
  treeRoot.enableDragAndDrop(1);
  treeRoot.setOnClickHandler(onclickItem);
  treeRoot.setDragHandler(myDragHandler);
  treeRoot.insertNewChild('S_Root','role_list',"角色列表",0,0,0,0,"");
  treeRoot.loadXML("${ctx}/base/role!xml.action");

  var RootTreeMenu = new RightMenu("RootTreeMenu");
  RootTreeMenu.AddItem("update105","","","新建角色","RootTreeMenu","javascript:editRole(treeSelectId);");
  RootTreeMenu.AddItem("update10","","","刷新角色","RootTreeMenu","javascript:refreshMenu();");
  document.writeln(RootTreeMenu.GetMenu()); 
  
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");
   document.writeln(ItemTreeMenu.GetMenu());     
-->
</script>

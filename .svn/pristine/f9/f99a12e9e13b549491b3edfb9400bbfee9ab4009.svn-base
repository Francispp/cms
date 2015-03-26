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
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script type="text/javascript"> 


//点击树节点执行的方法
function onclickItem(value) {

 if(isNaN(value)){
	 if(value=='album_list'){
		 //跳转到专辑列表
		 return ;
	 }else if(value=='default_album'){
		 myUrl="${ctx}/base/${albumType}!listByAlbum.action?albumType=${albumType}&isdefault=1";
	 }else{		 
		 myUrl="${ctx}/base/${albumType}!listByAlbum.action?albumType=${albumType}&albumId="+pid;	 
	 }
 }else{
	myUrl="${ctx}/base/${albumType}!listByAlbum.action?albumType=${albumType}&albumId="+value;
 }
 parent.frames['main_frame'].location.href=myUrl
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
  HideAll("DefaultTreeMenu",0);
}
//单击事件
function OnClick(){
closeAllMenu();
} 

//删除album
function removealbumItem(selectid){
	if(confirm('您确定要删除选择的记录吗？')){
		if(isNaN(selectid)){
			selectid=selectid.substring(2);
		}
		 location.href="${ctx}/base/album!delete.action?keys="+selectid+"&albumType=${albumType}";
	}
}

//在树上的右击事件
function myRightClickHandler(nodeid){
treeSelectId=''+nodeid;
var albumid='';
if(nodeid=='album_list'){
	OnMouseUpExt('RootTreeMenu'); 
}else if(nodeid=='default_album'){
	OnMouseUpExt('DefaultTreeMenu');
}else if(isNaN(nodeid)){
	if(nodeid && nodeid!=null && nodeid!=''){
		albumid=nodeid.substring(2);
		OnMouseUpExt('RootTreeMenu'+albumid); 
	}	
}else  
OnMouseUpExt('ItemTreeMenu');
}
//刷新
function refreshMenu(){
	location.href="${ctx}/base/album!tree.action?albumType=${albumType}";
}



//编辑专辑
function editAlbum(value,edit){
var palbumid="";
if(value=='album_list'){
	palbumid='';  
}else if(value=='default_album'){
	palbumid='';  
}else if(isNaN(value)){
	palbumid=value.substring(2);     
 }else{
	 palbumid=value;     
 }
 global_ab.fn.popWindow.addPopWindow("${ctx}/base/album!edit.action?pid="+palbumid+"&albumType=${albumType}","320px","262px",false);
 
 
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
 <div id="albumTree_Root"></div>
 <br>
            </td>
    </tr>               
</table> 
</body>
</html>
<script language="javascript">
function myDragHandler(){
	
}
<!--
 
treeRoot = new dhtmlXTreeObject('albumTree_Root','100%','100%','S_Root');
treeRoot.enableDragAndDrop(1);
treeRoot.setOnClickHandler(onclickItem);
treeRoot.setDragHandler(myDragHandler);
treeRoot.setOnRightClickHandler(myRightClickHandler);
treeRoot.insertNewChild('S_Root','album_list',"专辑列表",0,0,0,0,"");
treeRoot.loadXML("${ctx}/base/album!xml.action?albumType=${albumType}");
//treeRoot.setXMLAutoLoading("${ctx}/base/album!itemAlbum.action");
treeRoot.insertNewChild('album_list','default_album',"默认专辑",0,0,0,0,"");

var DefaultTreeMenu = new RightMenu("DefaultTreeMenu");
DefaultTreeMenu.AddItem("update10","","","刷新专辑","DefaultTreeMenu","javascript:refreshMenu();");
document.writeln(DefaultTreeMenu.GetMenu()); 

  var RootTreeMenu = new RightMenu("RootTreeMenu");
  RootTreeMenu.AddItem("update105","","","新建专辑","RootTreeMenu","javascript:editAlbum(treeSelectId);");
  RootTreeMenu.AddItem("update10","","","刷新专辑","RootTreeMenu","javascript:refreshMenu();");
  document.writeln(RootTreeMenu.GetMenu()); 
  
  
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");
   //ItemTreeMenu.AddItem("update201","","","新建专辑 ","ItemTreeMenu","javascript:editAlbum(treeSelectId);"); 
   ItemTreeMenu.AddItem("update202","","","编辑专辑","ItemTreeMenu","javascript:editAlbum(treeSelectId,1);");
   ItemTreeMenu.AddItem("update203","","","删除专辑","ItemTreeMenu","javascript:removealbumItem(treeSelectId);");
   ItemTreeMenu.AddItem("update10","","","刷新专辑 ","ItemTreeMenu","javascript:refreshMenu();");   
   document.writeln(ItemTreeMenu.GetMenu());     
-->
</script>
<c:if test="${not empty actionErrors}">
	    <c:forEach var="err" items="${actionErrors}">
	        <script> alert("专辑中有内容不能删除,请先清除专辑中的内容");</script>
        </c:forEach> 
	</c:if>

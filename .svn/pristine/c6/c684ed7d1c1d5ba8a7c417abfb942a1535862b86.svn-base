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
  var myUrl='';
   if(isNaN(value)){
     var pid=value.substring(2);    
     myUrl='${ctx}/cms/site!tabbar.action?id='+pid;
    
     top.setMainFrameUrl(myUrl);
   }else{
     myUrl='${ctx}/cms/column!edit.action?id='+value;
     parent.setMainFrameUrl(myUrl);
   }
}
//拖动时，事件
function myDragHandler(idFrom,idTo){
 if(isNaN(idFrom)){
  alert('当前选择的对象不能移动！');
  return false;
 }
  
 var rt=true;
 var obj=new Array;
 obj[0]= ""+idFrom;
  if(!isNaN(idTo)){//若idTo为P_开头，是树节点id
    obj[1]= ""+idTo;
    obj[2]= "";//
  }else{
    obj[1]= "";
    obj[2]=""+idTo.substring(2);//
    
    }
 ExecuteService("if(reply.getResult()=='true'){}else{ alert('操作失败!');location.reload();}",'columnManagerService','setColumnPM',obj);
 
 return rt;
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

//新增频道
function addColumn(value){

  var siteid="";
  var pchannelid="";
  if(isNaN(value)){
     siteid=value.substring(2);     
   }else{
     pchannelid=value;     
   }
  var path="${ctx}/cms/column!add.action?siteid="+siteid+"&pcolumnid="+pchannelid;
  parent.setMainFrameUrl(path);

}
//删除
function removeTreeItem(selectid){
 if(confirm('您确定要删除选择的记录吗？')){
  location.href="${ctx}/cms/column!chuck.action?keys="+selectid;
  }
}
//在树上的右击事件
function myRightClickHandler(nodeid){
  
 treeSelectId=''+nodeid;
 if(isNaN(nodeid))
  OnMouseUpExt('RootTreeMenu'); 
 else  
  OnMouseUpExt('ItemTreeMenu');
}
//刷新
function refreshMenu(){
 location.reload()
}
function recycleBin(value)
{
var pid=value.substring(2);
window.parent.parent.frames['main_frame'].location.href='${ctx}/cms/site!recycle.action?siteid='+pid;
}

</script>
</head>
<body style="background:#F1F1F1;" onmouseup="OnMouseUp('RootTreeMenu');" oncontextmenu="window.event.returnValue=false">
<table align="center" width="100%"  hight="100%" border="0">
     <tr>
            <td width="100%" align="left" hight="100%"> 
 <c:forEach var="site" items="${sites}" varStatus="status"> 
  <div id="siteTree_${site.oid}"></div> 
 </c:forEach>
            </td>
    </tr>               
</table> 
</body>
</html>
<script language="javascript">

 
<!--		
  <c:forEach var="site" items="${sites}" varStatus="status">  
  tree${site.oid}=new dhtmlXTreeObject("siteTree_${site.oid}","100%","100%",'S_${site.oid}');
   //enable Drag&Drop
  tree${site.oid}.enableDragAndDrop(1);
   //set my Drag&Drop handler
  tree${site.oid}.setDragHandler(myDragHandler);
  tree${site.oid}.loadXML("${ctx}/cms/column!xml.action?id=${site.oid}");
  tree${site.oid}.setOnClickHandler(onclickItem);
 
  tree${site.oid}.setOnRightClickHandler(myRightClickHandler);
  tree${site.oid}.setXMLAutoLoading("${ctx}/cms/column!columnxml.action?id=${site.oid}");
  </c:forEach>  
  //ItemTreeMenu right menu
  var RootTreeMenu = new RightMenu("RootTreeMenu");
  RootTreeMenu.AddItem("update202","","","新建栏目 ","RootTreeMenu","javascript:addColumn(treeSelectId);");   
  RootTreeMenu.AddItem("update102","","","刷新栏目","RootTreeMenu","javascript:refreshMenu();");
  RootTreeMenu.AddItem("update103","","","回收站","RootTreeMenu","javascript:recycleBin(treeSelectId);");
  document.writeln(RootTreeMenu.GetMenu());   
 
 //ItemTreeMenu right menu
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");
   ItemTreeMenu.AddItem("update2001","","","新建栏目 ","ItemTreeMenu","javascript:addColumn(treeSelectId);"); 
   ItemTreeMenu.AddItem("update121","","","删除栏目 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
   ItemTreeMenu.AddItem("update123","","","复制栏目 ","ItemTreeMenu","javascript:clkColumnTree(treeSelectId,0);");
   ItemTreeMenu.AddItem("update124","","","粘贴栏目 ","ItemTreeMenu","javascript:pasteColumnItem(treeSelectId);");
   ItemTreeMenu.AddItem("update10","","","刷新栏目 ","ItemTreeMenu","javascript:refreshMenu();");   
   document.writeln(ItemTreeMenu.GetMenu());     
-->
</script>

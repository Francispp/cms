<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="菜单树" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<%@ include file="/common/menu/menu.inc"%>
<script src="${ctx}/common/core_js/core.js"></script>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<script type="text/javascript"> 
//点击树节点执行的方法
function onclickItem(value) {
  if(!isNaN(value))
   parent.onClickTreeNode(value);
}
  
//拖动时，事件
function myDragHandler(idFrom,idTo){
//alert(document.getElementById('application-tree1').in);
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
 ExecuteService("if(reply.getResult()=='true'){}else{ alert('操作失败!');location.reload();}",'menuManagerService','setMenuPM',obj);
 
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
    HideAll("ItemTreeMenu",0);
    HideAll("RootTreeMenu",0);
  }
 //单击事件
function OnClick(){
  closeAllMenu();
 } 

//新增
function addTreeItem(pid){

 parent.frames['_content'].location.href='menu!edit.action?pmenuid='+pid+'&portalid=';
}
//新增
function addRootTreeItem(portalId){
 if(isNaN(portalId)){
  var pid=portalId.substring(2);
  parent.frames['_content'].location.href="menu!edit.action?pmenuid=&portalid="+pid;
 }
}
//删除
function removeTreeItem(selectid){
 if(confirm('您确定要删除选择的记录吗？')){
  parent.location.href="menu!delete.action?keys="+selectid;
  }
}
//刷新
function refreshMenu(){
 location.reload()
}
var cut_copy_menu_code="";//选择的编号
var flagCutOrCpy="";//选择的操作
//复制、剪切
function clkMenuTree(code,flag){
	cut_copy_menu_code=code;
	if(flag==1)
		flagCutOrCpy='cut';
	else
		flagCutOrCpy='copy';	
}
//粘贴
function pasteTreeItem(code){

var obj=new Array;
obj[0]= ''+cut_copy_menu_code;
obj[1]= ''+code;
obj[2]= '';
//若检测是根节点，则为portalid
if(isNaN(code)){//若idTo为P_开头
 obj[1]='';
 obj[2]=''+code.substring(2)
}

var methodName='copyMenuTo';
  if(cut_copy_menu_code==null||cut_copy_menu_code==''){
   alert('请选择要进行的操作!');
   return ;
   }
   ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else alert('操作失败!');",'menuManagerService',methodName,obj);
   flagCutOrCpy="";
   cut_copy_menu_code="";   
}

//在树上的右击事件
function myRightClickHandler(nodeid){
  
 treeSelectId=''+nodeid;
 if(isNaN(nodeid))
  OnMouseUpExt('RootTreeMenu'); 
 else  
  OnMouseUpExt('ItemTreeMenu');
} 
		
function onMenuClick(id){
	alert("Menu item "+id+" was clicked");
}        
</script>
<ww:head/>
</head>
<body oncontextmenu="window.event.returnValue=false" style="height:100%" >
 <div id="inside_left_center_content" style="height:100%">

            <p style="background:url(${default_imagepath}/menu_bg3.jpg) no-repeat center top; height:26px; line-height:26px;color:#fff;font-size:14px;font-weight:bold"><c:out value="${title}"/></p>         
            <div>
<table align="center" width="100%"  hight="100%" border="0">
     <tr>
     <td width="5%">&nbsp;</td>
            <td width="95%" align="left" hight="100%"> 
<c:forEach var="portal" items="${session.loginer.portals}" varStatus="status"> 
 <div id="application-tree${portal.portalid}"></div> 
</c:forEach> 
            </td>
    </tr>               
</table>
  </div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
		
  <c:forEach var="portal" items="${session.loginer.portals}" varStatus="status">  
  tree${portal.portalid}=new dhtmlXTreeObject("application-tree${portal.portalid}","100%","100%",'P_${portal.portalid}');
   //enable Drag&Drop
  tree${portal.portalid}.enableDragAndDrop(1);
   //set my Drag&Drop handler
  tree${portal.portalid}.setDragHandler(myDragHandler);
  tree${portal.portalid}.loadXML("${ctx}/base/menu!xml.action?portalid=${portal.portalid}");
  tree${portal.portalid}.setOnClickHandler(onclickItem);
  tree${portal.portalid}.setOnRightClickHandler(myRightClickHandler);
  </c:forEach>  
 
  
//ItemTreeMenu right menu
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");
   ItemTreeMenu.AddItem("update11","","","新增子菜单 ","ItemTreeMenu","javascript:addTreeItem(treeSelectId);");
   //ItemTreeMenu.AddItem("update122","","","新增同级菜单 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,1);");
   ItemTreeMenu.AddItem("update121","","","删除菜单 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
   ItemTreeMenu.AddItem("update123","","","复制菜单 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,0);");
   ItemTreeMenu.AddItem("update124","","","粘贴菜单 ","ItemTreeMenu","javascript:pasteTreeItem(treeSelectId);");
   ItemTreeMenu.AddItem("update10","","","刷新菜单 ","ItemTreeMenu","javascript:refreshMenu();");   
   document.writeln(ItemTreeMenu.GetMenu());  
   
  var RootTreeMenu = new RightMenu("RootTreeMenu");
  RootTreeMenu.AddItem("update2001","","","新增菜单 ","RootTreeMenu","javascript:addRootTreeItem(treeSelectId);"); 
  RootTreeMenu.AddItem("update202","","","粘贴菜单 ","RootTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  RootTreeMenu.AddItem("update102","","","刷新菜单 ","RootTreeMenu","javascript:refreshMenu();");
  document.writeln(RootTreeMenu.GetMenu());    
   </script>
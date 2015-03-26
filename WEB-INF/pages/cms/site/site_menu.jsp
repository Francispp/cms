<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
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
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript"> 

  //点击树节点执行的方法
function onclickItem(value) {
 <c:forEach var="site" items="${sites}" varStatus="status">
  <cms:CmsAuth resCode="CMS_SITE_VIEW" objectId="${site.oid}" objectType="1"> 
   var siId${site.oid}=tree${site.oid}.getSelectedItemId();
   //清除其他树上已选中的项
   if(siId${site.oid}!=null && siId${site.oid}!='' && value!=siId${site.oid}){
    tree${site.oid}.clearSelectedItems();
   }
  </cms:CmsAuth>
  </c:forEach> 
  var myUrl='';
   if(isNaN(value)){
     var pid=value.substring(2);
     if(value.indexOf('Y_') != -1)
       {
        myUrl='${ctx}/cms/recycle.action?objectId='+pid;
        
       }
     else
     {
     //myUrl='${ctx}/cms/site!tabbar.action?id='+pid;//栏目管理中的站点管理不需要点
   
     }
     if(myUrl!=''){
     	top.setMainFrameUrl(myUrl);
     }
   }else{
     //myUrl='${ctx}/cms/channel!tabbar.action?id='+value;
     myUrl='${ctx}/cms/channel!edit.action?id='+value;
     top.setMainFrameUrl(myUrl);
   }
}
//拖动时，事件
function myDragHandler(idFrom,idTo){
 if(isNaN(idFrom)){
  alert('当前选择的对象不能移动！');
  return false;
 }
 if(!confirm('您确认要移动此栏目吗？'))
  return false;   
 var rt=true;
 var obj=new Array;
 obj[0]= ""+idFrom;
 obj[3]= '{userinfo}';
  if(!isNaN(idTo)){//若idTo为P_开头，是树节点id
    obj[1]= ""+idTo;
    obj[2]= "";//
  }else{
    obj[1]= "";
    obj[2]=""+idTo.substring(2);//
    
    }
 ExecuteService("if(reply.getResult()=='true'){}else{ alert('操作失败!');location.reload();}",'channelManagerService','setChannelPM',obj);
 
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
  <c:forEach var="site" items="${sites}" varStatus="status">
  <cms:CmsAuth resCode="CMS_SITE_VIEW" objectId="${site.oid}" objectType="1"> 
   HideAll("RootTreeMenu${site.oid}",0);
  </cms:CmsAuth>
  </c:forEach> 
  }
 //单击事件
function OnClick(){
  closeAllMenu();
 } 

//新增站点
function addSite(){
 window.parent.parent.frames['main_frame'].location.href='${ctx}/cms/site!tabbar.action';
}

//文档访问统计
function visitCount(siteId){
 window.parent.parent.frames['main_frame'].location.href='${ctx}/base/visit!frame.action?siteId='+siteId;
}
//新增频道
function addChanel(value){
  var siteid="";
  var pchannelid="";
  if(isNaN(value)){
     siteid=value.substring(2);     
   }else{
     pchannelid=value;     
   }
  
 // window.parent.parent.frames['main_frame'].location.href="${ctx}/cms/channel!tabbar.action?siteid="+siteid+"&pchannelid="+pchannelid;
  window.parent.parent.frames['main_frame'].location.href="${ctx}/cms/channel!edit.action?siteid="+siteid+"&pchannelid="+pchannelid;
}
//删除channel
function removeTreeItem(selectid){
 if(confirm('您确定要删除选择的记录吗？')){
  location.href="${ctx}/cms/channel!chuck.action?keys="+selectid;
  top.setMainFrameUrl("");
  }
}
//删除site
function removeSiteItem(selectid){
 if(confirm('您确定要删除选择的记录吗？')){
  location.href="${ctx}/cms/site!chuck.action?keys="+selectid.substring(2);
  }
}

//在树上的右击事件
function myRightClickHandler(nodeid){
 treeSelectId=''+nodeid;
 var siteid='';
 if(isNaN(nodeid)){
  if(nodeid && nodeid!=null && nodeid!='')
   siteid=nodeid.substring(2);
  OnMouseUpExt('RootTreeMenu'+siteid); 
 }else  
  OnMouseUpExt('ItemTreeMenu');
}
//刷新
function refreshMenu(){
 location.reload()
}

//站点角色管理
function siteRoleManager(objectid)
{
  var oid="";
  if(isNaN(objectid)){
     oid=objectid.substring(2);     
   }else
  return ;
 window.parent.parent.frames['main_frame'].location.href='${ctx}/cms/role!extlist.action?objectId='+oid;

}
//过期文档
function overdueDoc(objectid){
  var oid="";
  if(isNaN(objectid)){
     oid=objectid.substring(2);     
   }else
  return ;
 window.parent.parent.frames['main_frame'].location.href='${ctx}/cms/document!overdueDoc.action?siteId='+oid;
}
//共享文档
function shareDoc(objectid){
  var oid="";
  if(isNaN(objectid)){
     oid=objectid.substring(2);     
   }else
  return ;
 window.parent.parent.frames['main_frame'].location.href='${ctx}/cms/document!shareDocList.action?siteId='+oid;
}

function commonType(objectid){
  var oid="";
  if(isNaN(objectid)){
     oid=objectid.substring(2);     
   }else
  return ;
 window.parent.parent.frames['main_frame'].location.href='${ctx}/base/commontype!frame.action?siteid='+oid;
}

var cut_copy_menu_code="";//选择的编号
var flagCutOrCpy="";//选择的操作
//复制、剪切
function clkChanelTree(code,flag){
   if(isNaN(code)){
     cut_copy_menu_code=code.substring(2);     
   }else{
     cut_copy_menu_code=code;     
   }
	if(flag==1)
		flagCutOrCpy='cut';
	else
		flagCutOrCpy='copy';	
}

//粘贴
function pasteChanelItem(code,choose){
var obj=new Array;
obj[0]= ''+cut_copy_menu_code;
obj[1]= ''+code;
obj[2]= '';
obj[3]= '{userinfo}';
//若检测是根节点，则为portalid
if(isNaN(code)){//若idTo为P_开头
 obj[1]='';
 obj[2]=''+code.substring(2)
}

if(choose==1)
var methodName='copySiteTo';
else
var methodName='copyChannelTo';
  if(cut_copy_menu_code==null||cut_copy_menu_code==''){
   alert('请先选择要操作的对象!');
   return ;
   }
   if(choose==1)
    ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else alert('操作失败,请选择要粘贴的站点！');",'siteManagerService',methodName,obj);
   else
   ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else alert('操作失败!');",'channelManagerService',methodName,obj);
   flagCutOrCpy="";
   cut_copy_menu_code="";   
}

function recycleBin(value)
{
var pid=value.substring(2);
window.parent.parent.frames['main_frame'].location.href='${ctx}/cms/site!recycle.action?siteid='+pid;
}
//导入站点
function importSite()
{
var title="选择导入的文件";
actionURL="${ctx}/cms/site!importSiteData.action";
uploadXML(title,actionURL);
location.reload();
}

 function exportSite(value)
 {
 value=value.substring(2);
  location.href="${ctx}/cms/site!exportSiteData.action?id="+value;
 }
  function exportSiteData(value)
 {
 value=value.substring(2);
  location.href="${ctx}/cms/site!exportSiteInfo.action?id="+value;
 }
 
 //导入频道
 function importChannel(value){
 //导入时可能会是指定频道，现只处理了站点
     value=value.substring(2);
	 var title="选择导入的文件";
	 actionURL="${ctx}/cms/channel!importChannelData.action?id="+value;
	 uploadXML(title,actionURL);
	 }
	  //导入频道
 function importParentChannel(value){
	 var title="选择导入的文件";
	 actionURL="${ctx}/cms/channel!importParentChannelData.action?id="+value;
	 uploadXML(title,actionURL);
	 }
 //导出频道
   function exportChannel(value){
	  location.href="channel!exportChannelData.action?id="+value;
	 }

//权限设置
function setpermission(type,objectid){
  var oid="";
  if(isNaN(objectid)){
     oid=objectid.substring(2);     
   }else
   oid=objectid;
 window.parent.parent.frames['main_frame'].location.href='${ctx}/cms/permission!perm.action?objectId='+oid+'&type='+type;
}       
</script>
</head>
<body style="background:#F1F1F1;" class="tree-bg" onmouseup="OnMouseUp('RootTreeMenu');" oncontextmenu="window.event.returnValue=false">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<c:if test="${not empty session.ActionError}">
  <script>alertMessage("${session.ActionError}");</script>
  <% session.removeAttribute("ActionError"); %>
</c:if>
 <c:forEach var="site" items="${sites}" varStatus="status"> 
  <div id="siteTree_${site.oid}"></div> 
 </c:forEach>
</body>
</html>
<script language="javascript">

<!--		
  <c:forEach var="site" items="${sites}" varStatus="status">
  <cms:CmsAuth resCode="CMS_SITE_VIEW" objectId="${site.oid}" objectType="1">  
  tree${site.oid}=new dhtmlXTreeObject("siteTree_${site.oid}","100%","100%",'S_${site.oid}');
   //enable Drag&Drop
  tree${site.oid}.enableDragAndDrop(1);
   //set my Drag&Drop handler
  tree${site.oid}.setDragHandler(myDragHandler);
  tree${site.oid}.loadXML("${ctx}/cms/site!xml.action?id=${site.oid}&siteName="+encodeURI(encodeURI("栏目列表")));
  tree${site.oid}.setOnClickHandler(onclickItem);
 
  tree${site.oid}.setOnRightClickHandler(myRightClickHandler);
  tree${site.oid}.setXMLAutoLoading("${ctx}/cms/site!channelxml.action?id=${site.oid}");
  
   //RootTreeMenu right menu
  var RootTreeMenu${site.oid} = new RightMenu("RootTreeMenu${site.oid}");
  <c:if test="${session.loginer.isAdminUser}">
  //-- RootTreeMenu${site.oid}.AddItem("update202_${site.oid}","","","新建站点 ","RootTreeMenu${site.oid}","javascript:addSite();");
  //-- RootTreeMenu${site.oid}.AddItem("update204_${site.oid}","","","复制站点 ","RootTreeMenu${site.oid}","javascript:clkChanelTree(treeSelectId,0);");
  //-- RootTreeMenu${site.oid}.AddItem("update205_${site.oid}","","","粘贴站点 ","RootTreeMenu${site.oid}","javascript:pasteChanelItem(treeSelectId,1);");
  //-- RootTreeMenu${site.oid}.AddItem("update109_${site.oid}","","","导出站点结构","RootTreeMenu${site.oid}","javascript:exportSite(treeSelectId);");
 //--  RootTreeMenu${site.oid}.AddItem("update111_${site.oid}","","","导出站点数据","RootTreeMenu${site.oid}","javascript:exportSiteData(treeSelectId);");
  //-- RootTreeMenu${site.oid}.AddItem("update222_${site.oid}","","","访问文档统计","RootTreeMenu${site.oid}","javascript:visitCount(${site.oid});");
  </c:if>
  <cms:CmsAuth resCode="CMS_CHANNEL_ADD" objectId="${site.oid}" objectType="1">
  RootTreeMenu${site.oid}.AddItem("update203_${site.oid}","","","新建频道 ","RootTreeMenu${site.oid}","javascript:addChanel(treeSelectId);");   
  RootTreeMenu${site.oid}.AddItem("update206_${site.oid}","","","粘贴频道 ","RootTreeMenu${site.oid}","javascript:pasteChanelItem(treeSelectId,0);");
  //--RootTreeMenu${site.oid}.AddItem("update110_${site.oid}","","","导入频道","RootTreeMenu${site.oid}","javascript:importChannel(treeSelectId);");
  </cms:CmsAuth>
  <cms:CmsAuth resCode="CMS_SITE_DELETE" objectId="${site.oid}" objectType="1">
 //-- RootTreeMenu${site.oid}.AddItem("update207_${site.oid}","","","删除站点 ","RootTreeMenu${site.oid}","javascript:removeSiteItem(treeSelectId);");
  //RootTreeMenu${site.oid}.AddItem("update2012_${site.oid}","","","站点权限 ","RootTreeMenu${site.oid}","javascript:setpermission(1,treeSelectId);");
  </cms:CmsAuth>
  <cms:CmsAuth resCode="CMS_SITE_ROLE_MANAGER" objectId="${site.oid}" objectType="1">
  //--RootTreeMenu${site.oid}.AddItem("update104_${site.oid}","","","角色管理","RootTreeMenu${site.oid}","javascript:siteRoleManager(treeSelectId);");
  </cms:CmsAuth>
  <cms:CmsAuth resCode="CMS_SITE_RECYCLE_MANAGER" objectId="${site.oid}" objectType="1">
  //--RootTreeMenu${site.oid}.AddItem("update103_${site.oid}","","","回收站","RootTreeMenu${site.oid}","javascript:recycleBin(treeSelectId);");
  </cms:CmsAuth>
  <cms:CmsAuth resCode="CMS_SITE_OVERDUE_DOC" objectId="${site.oid}" objectType="1">
 //-- RootTreeMenu${site.oid}.AddItem("update108_${site.oid}","","","过期文档","RootTreeMenu${site.oid}","javascript:overdueDoc(treeSelectId);");
 //-- RootTreeMenu${site.oid}.AddItem("update119_${site.oid}","","","共享文档","RootTreeMenu${site.oid}","javascript:shareDoc(treeSelectId);");
  </cms:CmsAuth>
 
  <cms:CmsAuth resCode="CMS_SITE_TYPE" objectId="${site.oid}" objectType="1">
  //-- RootTreeMenu${site.oid}.AddItem("update120_${site.oid}","","","类别管理","RootTreeMenu${site.oid}","javascript:commonType(treeSelectId);");
  </cms:CmsAuth>
  RootTreeMenu${site.oid}.AddItem("update102_${site.oid}","","","刷新站点","RootTreeMenu${site.oid}","javascript:refreshMenu();");
  
  document.writeln(RootTreeMenu${site.oid}.GetMenu());   
  </cms:CmsAuth>
  </c:forEach> 
  //RootTreeMenu right menu
  var RootTreeMenu = new RightMenu("RootTreeMenu");
  <c:if test="${session.loginer.isAdminUser}">
 //--  RootTreeMenu.AddItem("update202","","","新建站点 ","RootTreeMenu","javascript:addSite();");
   //RootTreeMenu.AddItem("update204","","","复制站点 ","RootTreeMenu","javascript:clkChanelTree(treeSelectId,0);");
 //--  RootTreeMenu.AddItem("update205","","","粘贴站点 ","RootTreeMenu","javascript:pasteChanelItem(treeSelectId,1);");
 //--  RootTreeMenu.AddItem("update104","","","导入站点","RootTreeMenu","javascript:importSite();");
  </c:if>     
   //RootTreeMenu.AddItem("update203","","","新建频道 ","RootTreeMenu","javascript:addChanel(treeSelectId);");
   //RootTreeMenu.AddItem("update206","","","粘贴频道 ","RootTreeMenu","javascript:pasteChanelItem(treeSelectId,0);");
   
   
   //RootTreeMenu.AddItem("update207","","","删除站点 ","RootTreeMenu","javascript:removeSiteItem(treeSelectId);");
   //RootTreeMenu.AddItem("update2012","","","站点权限 ","RootTreeMenu","javascript:setpermission(1,treeSelectId);");
   
  //RootTreeMenu.AddItem("update104","","","角色管理","RootTreeMenu","javascript:siteRoleManager(treeSelectId);");
  <c:if test="${session.loginer.isAdminUser}">
  //--RootTreeMenu.AddItem("update103","","","回收站","RootTreeMenu","javascript:recycleBin(treeSelectId);");
  </c:if>  
   
  //RootTreeMenu.AddItem("update108","","","过期文档","RootTreeMenu","javascript:overdueDoc(treeSelectId);");
  RootTreeMenu.AddItem("update102","","","刷新站点","RootTreeMenu","javascript:refreshMenu();");
  
  document.writeln(RootTreeMenu.GetMenu()); 
 //ItemTreeMenu right menu
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");
  <cms:CmsAuth resCode="CMS_CHANNEL_ADD" objectId="${loginer.siteId}" objectType="1">
   ItemTreeMenu.AddItem("update2001","","","新建频道 ","ItemTreeMenu","javascript:addChanel(treeSelectId);"); 
   </cms:CmsAuth>
   <cms:CmsAuth resCode="CMS_CHANNEL_DELETE" objectId="${loginer.siteId}" objectType="1">
   ItemTreeMenu.AddItem("update121","","","删除频道 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
   </cms:CmsAuth>
   <cms:CmsAuth resCode="CMS_CHANNEL_ADD" objectId="${loginer.siteId}" objectType="1">
   ItemTreeMenu.AddItem("update123","","","复制频道 ","ItemTreeMenu","javascript:clkChanelTree(treeSelectId,0);");
   ItemTreeMenu.AddItem("update124","","","粘贴频道 ","ItemTreeMenu","javascript:pasteChanelItem(treeSelectId,0);");
   </cms:CmsAuth>
   <c:if test="${session.loginer.isAdminUser}">
   //--　ItemTreeMenu.AddItem("update128","","","导入频道 ","ItemTreeMenu","javascript:importParentChannel(treeSelectId);");   
   //-- ItemTreeMenu.AddItem("update125","","","导出频道 ","ItemTreeMenu","javascript:exportChannel(treeSelectId);");
   </c:if> 
   //ItemTreeMenu.AddItem("update125","","","频道权限 ","ItemTreeMenu","javascript:setpermission(2,treeSelectId);");
   ItemTreeMenu.AddItem("update10","","","刷新站点 ","ItemTreeMenu","javascript:refreshMenu();");   
   document.writeln(ItemTreeMenu.GetMenu());     
-->



</script>

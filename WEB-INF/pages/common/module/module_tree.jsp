<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="模块树" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dtree.css" type="text/css">
<%@ include file="/common/cxtree.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/js.inc"%>

<script src="${ctx}/common/dtree/dtree.js"></script>
<script src="${ctx}/common/dtree/respagetree.js"></script>
<script src="${ctx}/common/core_js/core.js"></script>
<script type="text/javascript">
//新增
function addTreeItem(pid){
 parent.frames['_content'].location.href="module!edit.action?pmoCode="+pid;
}
//新增
function addRootTreeItem(pcode){
 parent.frames['_content'].location.href="module!edit.action?pmoCode=&pcode="+pcode;
}
//删除
function removeTreeItem(selectid){
 parent.location.href="module!delete.action?keys="+selectid;
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
if(code==null || code=='undefined' || code=="")
 code='';
 
var obj=new Array;
obj[0]= cut_copy_menu_code;
obj[1]= code;
obj[2]=0;//0-复制，1-剪切

var methodName='pasteResourceTo';
  if(flagCutOrCpy==null||flagCutOrCpy==''){
   alert('请选择要进行的操作!');
   return ;
   }
if(flagCutOrCpy=='cut'){
 obj[2]= 1;
}     
   ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else alert('操作失败!');",'moduleManagerService',methodName,obj);
   flagCutOrCpy="";
   cut_copy_menu_code="";	
}
function goBack(){
 location.href='module.action';
}
function selectOne(value) {
  window.returnValue = value;
  window.close();
}
//点击树节点执行的方法
function onclickItem(value) {
   parent.onClickTreeNode(value);
}

//onclick select tree item id
var treeSelectId="";
/*************************
 * 右键菜单
 * script代码
 *************************/

document.onclick=OnClick;
//document.onmouseup=OnClick;
//关闭所有右击菜单
function closeAllMenu() {
    HideAll("ItemTreeMenu",0);
    HideAll("RootTreeMenu",0);
    HideAll("ResourceTreeMenu",0);
  }
//单击事件
function OnClick(){
  closeAllMenu();
 }  
//ItemTreeMenu right menu
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");
  <cms:auth resCode="SYS_MODULE_ADD"> 
  ItemTreeMenu.AddItem("update11","","","新增模块 ","ItemTreeMenu","javascript:addTreeItem(treeSelectId);");
  </cms:auth>
  <cms:auth resCode="SYS_MODULE_DELETE">
  ItemTreeMenu.AddItem("update121","","","删除模块 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
  </cms:auth>
  <cms:auth resCode="SYS_MODULE_MODI">
  ItemTreeMenu.AddItem("update122","","","剪切模块 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,1);");
  ItemTreeMenu.AddItem("update123","","","复制模块 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,0);");
  ItemTreeMenu.AddItem("update124","","","粘贴模块 ","ItemTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  </cms:auth>
  ItemTreeMenu.AddItem("update125","","","新增资源 ","ItemTreeMenu","javascript:addResource(treeSelectId);");
  ItemTreeMenu.AddItem("update10","","","刷新菜单 ","ItemTreeMenu","javascript:refreshMenu();");  
  document.writeln(ItemTreeMenu.GetMenu()); 
  
  var RootTreeMenu = new RightMenu("RootTreeMenu");
  <cms:auth resCode="SYS_MODULE_ADD"> 
  RootTreeMenu.AddItem("update2001","","","新增模块 ","RootTreeMenu","javascript:addRootTreeItem(treeSelectId);"); 
  RootTreeMenu.AddItem("update202","","","粘贴模块 ","RootTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  </cms:auth>
  RootTreeMenu.AddItem("update102","","","刷新菜单 ","RootTreeMenu","javascript:refreshMenu();");
  document.writeln(RootTreeMenu.GetMenu());  
  
  var ResourceTreeMenu = new RightMenu("ResourceTreeMenu");
    ResourceTreeMenu.AddItem("update8001","","","删除资源 ","ResourceTreeMenu","javascript:deleteResource(treeSelectId);"); 
　　document.writeln(ResourceTreeMenu.GetMenu()); 
  //定义资源类型的图标
  
  var ResourceIco="${ctx}/common/xloadtree/images/iconText.gif";
  function onclickItemByResource(oid){
   parent.frames['_content'].location.href="resource!edit.action?id="+oid;
  }
  //增加资源
  function addResource(moid){
   parent.frames['_content'].location.href="resource!edit.action?moid="+moid;
  }
  //删除资源
  function deleteResource(oid){
	var msg = "确定删除该资源吗？";
	parent.Ext.MessageBox.confirm('提示', msg, function(btn){
	if(btn=='yes'){
         parent.frames['_content'].location.href="resource!delete.action?keys="+oid;
         parent.location.reload();
	}
	});    
  }
</script>
<ww:head/>
</head>
<body  style="margin:0px;padding:0px;" oncontextmenu="window.event.returnValue=false">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="2"></td>
	</tr>
	<tr>
		<td >
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="模块树"/></div>
		</div>
		</div>
		</div>
		</td>
	</tr>     
   	<tr>
		<td valign="top"  height="100%">  
<!-- 页面主要内容 -->
<div id="list_scroll_content" align=justify style="padding-left: 3px; width: 98%;">                 
      <script  language="javascript"> 
      <c:forEach var="portal" items="${requestScope.portals}" varStatus="status">
      </c:forEach>
       document.write("<hr>");
       var T_cyberCMS=new WebFXTree('系统管理', '',null, null, null,null,'false','cybercms',null,null,"javascript:treeSelectId='cybercms';OnMouseUp('RootTreeMenu');");
       document.write(T_cyberCMS);
       
      
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.pmoCode==null}" >
                  var T${item.oid}= new WebFXTreeItem('${item.moName}', 'javascript:onclickItem(${item.oid})', null, null, null,null,'false','${item.oid}',null,null,"javascript:treeSelectId='${item.oid}';OnMouseUp('ItemTreeMenu');");
                  </c:when>
                  <c:otherwise>
                  var T${item.oid}= new WebFXTreeItem('${item.moName}', 'javascript:onclickItem(${item.oid})', null, null, null,null,'false','${item.oid}',null,null,"javascript:treeSelectId='${item.oid}';OnMouseUp('ItemTreeMenu');");
                  </c:otherwise>
                 </c:choose>
        <c:forEach var="resource" items="${item.coreResources}" varStatus="rstatus">
         var Tr${resource.resourceid}= new WebFXTreeItem('${resource.resourcename}', 'javascript:onclickItemByResource(${resource.resourceid})', null, ResourceIco,null,null,'false','${resource.resourceid}',null,null,"javascript:treeSelectId='${resource.resourceid}';OnMouseUp('ResourceTreeMenu');");
         T${item.oid}.add(Tr${resource.resourceid});  
        </c:forEach>         
                </c:forEach>
               
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.pmoCode==null}" > 
                
                	 T_cyberCMS.add(T${item.oid});             
                  </c:when>
                  <c:otherwise>
                  if(T${item.pmoCode}!=null)
                   T${item.pmoCode}.add(T${item.oid});
                  </c:otherwise>
                 </c:choose>
                </c:forEach>         
      </script>       

</div>
  </td></tr>
   </table> 
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

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
  
  ItemTreeMenu.AddItem("update125","","","新增资源 ","ItemTreeMenu","javascript:addResource(treeSelectId);");
  ItemTreeMenu.AddItem("update10","","","刷新菜单 ","ItemTreeMenu","javascript:refreshMenu();");  
  document.writeln(ItemTreeMenu.GetMenu()); 
  
  var RootTreeMenu = new RightMenu("RootTreeMenu");
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
       var T_cyberCMS=new WebFXTree('系统权限管理', '',null, null, null,null,'false','cybercms',null,null,"javascript:treeSelectId='cybercms';OnMouseUp('RootTreeMenu');");
       document.write(T_cyberCMS);
       
      
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               
                  var T${item.id}= new WebFXTreeItem('${item.menuName}', 'javascript:onclickItem(${item.id})', null, null, null,null,'false','${item.id}',null,null,"javascript:treeSelectId='${item.id}';OnMouseUp('ItemTreeMenu');");
                
        <c:forEach var="resource" items="${requestScope.resources}" varStatus="rstatus">
         var Tr${resource.resourceid}= new WebFXTreeItem('${resource.resourcename}', 'javascript:onclickItemByResource(${resource.resourceid})', null, ResourceIco,null,null,'false','${resource.resourceid}',null,null,"javascript:treeSelectId='${resource.resourceid}';OnMouseUp('ResourceTreeMenu');");
         <c:if test="${item.id==resource.resourceid}">
         T${item.id}.add(Tr${resource.resourceid});  
         </c:if>
        </c:forEach>         
                </c:forEach>
               
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
                	 T_cyberCMS.add(T${item.id});             
                </c:forEach>         
      </script>       

</div>
  </td></tr>
   </table> 
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

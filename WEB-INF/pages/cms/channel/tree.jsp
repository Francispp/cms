<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="部门树" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dtree.css" type="text/css">
<%@ include file="/common/cxtree.inc"%>

<script src="${ctx}/common/dtree/dtree.js"></script>
<script src="${ctx}/common/dtree/respagetree.js"></script>
<script src="${ctx}/common/core_js/core.js"></script>
<script type="text/javascript">
//新增
function addTreeItem(pid){
 parent.frames['_content'].location.href="channel!add.action?pid="+pid;
}
//新增
function addRootTreeItem(portalId){
 parent.frames['_content'].location.href="channel!edit.action?pid=&portalId="+portalId;
}
//删除
function removeTreeItem(selectid){
 if(confirm('您确定要删除选择的记录吗？')){
  parent.location.href="channel!delete.action?keys="+selectid;
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
if(code==null || code=='undefined' || code=="")
 code='';
 
var obj=new Array;
obj[0]= cut_copy_menu_code;
obj[1]= code;
obj[2]=0;//0-复制，1-剪切

var methodName='pasteDeptTo';
  if(flagCutOrCpy==null||flagCutOrCpy==''){
   alert('请选择要进行的操作!');
   return ;
   }
if(flagCutOrCpy=='cut'){
 obj[2]= 1;
}     
   ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else alert('操作失败!');",'channelManagerService',methodName,obj);
   flagCutOrCpy="";
   cut_copy_menu_code="";	
}
function goBack(){
 location.href='channel!list.action';
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
    //HideAll("RootTreeMenu",0);
  }
//单击事件
function OnClick(){
  closeAllMenu();
 }  
//ItemTreeMenu right menu
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");
  <ww:if test="#session.loginer.haveThePermission(\"SYS_DEPT_MANAGE_ADD\")">
   ItemTreeMenu.AddItem("update11","","","新增频道 ","ItemTreeMenu","javascript:addTreeItem(treeSelectId);");
  </ww:if>
  <ww:if test="#session.loginer.haveThePermission(\"SYS_DEPT_MANAGE_DEL\")"> 
   ItemTreeMenu.AddItem("update121","","","删除频道 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
  </ww:if>
  ItemTreeMenu.AddItem("update122","","","剪切频道 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,1);");
  ItemTreeMenu.AddItem("update123","","","复制频道 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,0);");
  ItemTreeMenu.AddItem("update124","","","粘贴频道 ","ItemTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  ItemTreeMenu.AddItem("update10","","","刷新菜单 ","ItemTreeMenu","javascript:refreshMenu();");   
  document.writeln(ItemTreeMenu.GetMenu()); 
  
  var RootTreeMenu = new RightMenu("RootTreeMenu");
  RootTreeMenu.AddItem("update2001","","","新增频道 ","RootTreeMenu","javascript:addRootTreeItem(treeSelectId);"); 
  RootTreeMenu.AddItem("update202","","","粘贴频道 ","RootTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  RootTreeMenu.AddItem("update102","","","刷新菜单 ","RootTreeMenu","javascript:refreshMenu();");
  document.writeln(RootTreeMenu.GetMenu());    
</script>
<ww:head/>
</head>
<body  oncontextmenu="window.event.returnValue=false" nowrap topmargin="0" leftmargin="0" bgcolor="#F4F4F0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<!--div id="titleDiv"><c:out value="${title}"/></div-->
<!-- 操作功能按钮条 -->
<!-- div id="operationDivNoBorder">
          
 <button id="backbutton" onclick="goBack()" class="button">返回</button>
</div-->
<!-- 页面主要内容 -->
<div id="page-content-no-border">
<table align="center" width="100%" border="1">
     <tr>
            <td width="80%" align="left" >            
      <script  language="javascript"> 
      var rightMenu=null;//style=1,出现右击管理菜单
     <c:forEach var="portal" items="${requestScope.portals}" varStatus="status">
       document.write("<hr>");
       var Tree${portal.portalCode}=new WebFXTree('${portal.portalName}', '',null, null, null,null,'false','${portal.oid}',null,null,"javascript:treeSelectId='${portal.oid}';OnMouseUp('RootTreeMenu');");
       document.write(Tree${portal.portalCode});       
      </c:forEach>     
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
             <c:if test="${pageStyle==1}">
               rightMenu="javascript:treeSelectId='${item.id}';OnMouseUp('ItemTreeMenu');"
             </c:if>
               <c:choose>
                 <c:when test="${item.parent ==null}" > 
                   var T${item.deptid} = new WebFXTreeItem('${item.name}', 'javascript:onclickItem(${item.id})', null, null, null,null,'false','${item.id}',null,null,rightMenu);                 
                  </c:when>
                  <c:otherwise>
                   var T${item.deptid} = new WebFXTreeItem('${item.name}', 'javascript:onclickItem(${item.tid})', null, null, null,null,'false','${item.id}',null,null,rightMenu);
                  </c:otherwise>
                 </c:choose>
                </c:forEach>
               
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.parent==null}" >
                  Tree${item.corePortal.portalCode}.add(T${item.id});              
                  </c:when>
                  <c:otherwise>
                   T${item.pdeptid}.add(T${item.id});
                  </c:otherwise>
                 </c:choose>
                </c:forEach>         
      </script>       
            </td>
    </tr>               
</table>
</div>
<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>

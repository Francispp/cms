<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="资源树" />

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
 parent.frames['_content'].location.href="resource!edit.action?presourceid="+pid;
}
//新增
function addRootTreeItem(pcode){
 parent.frames['_content'].location.href="resource!edit.action?presourceid=&pcode="+pcode;
}
//删除
function removeTreeItem(selectid){
 parent.location.href="resource!delete.action?keys="+selectid;
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
   ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else alert('操作失败!');",'resourceManagerService',methodName,obj);
   flagCutOrCpy="";
   cut_copy_menu_code="";	
}
function goBack(){
 location.href='resource.action';
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
  }
//单击事件
function OnClick(){
  closeAllMenu();
 }  
//ItemTreeMenu right menu
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");
  ItemTreeMenu.AddItem("update11","","","新增资源 ","ItemTreeMenu","javascript:addTreeItem(treeSelectId);");
  ItemTreeMenu.AddItem("update121","","","删除资源 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
  ItemTreeMenu.AddItem("update122","","","剪切资源 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,1);");
  ItemTreeMenu.AddItem("update123","","","复制资源 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,0);");
  ItemTreeMenu.AddItem("update124","","","粘贴资源 ","ItemTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  ItemTreeMenu.AddItem("update10","","","刷新菜单 ","ItemTreeMenu","javascript:refreshMenu();"); 
  document.writeln(ItemTreeMenu.GetMenu()); 
  
  var RootTreeMenu = new RightMenu("RootTreeMenu");
  RootTreeMenu.AddItem("update2001","","","新增资源 ","RootTreeMenu","javascript:addRootTreeItem(treeSelectId);"); 
  RootTreeMenu.AddItem("update202","","","粘贴资源 ","RootTreeMenu","javascript:pasteTreeItem(treeSelectId);");
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
        资源树                
      <script  language="javascript"> 
      <c:forEach var="portal" items="${requestScope.portals}" varStatus="status">
       document.write("<hr>");
       var T${portal.portalcode}=new WebFXTree('${portal.cname}', '',null, null, null,null,'false','${portal.portalcode}',null,null,"javascript:treeSelectId='${portal.portalcode}';OnMouseUp('RootTreeMenu');");
       document.write(T${portal.portalcode});
       
      </c:forEach>
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.precourceid==null}" >
                  //var T${item.recourceid}=new WebFXTree('${item.resourcename}', 'javascript:onclickItem(${item.recourceid})',null, null, null,null,'false','${item.recourceid}',null,null,"javascript:treeSelectId='${item.recourceid}';OnMouseUp('ItemTreeMenu');");
                  //document.write(T${item.recourceid});
                  var T${item.recourceid}= new WebFXTreeItem('${item.resourcename}', 'javascript:onclickItem(${item.recourceid})', null, null, null,null,'false','${item.recourceid}',null,null,"javascript:treeSelectId='${item.recourceid}';OnMouseUp('ItemTreeMenu');");
                  </c:when>
                  <c:otherwise>
                  var T${item.recourceid}= new WebFXTreeItem('${item.resourcename}', 'javascript:onclickItem(${item.recourceid})', null, null, null,null,'false','${item.recourceid}',null,null,"javascript:treeSelectId='${item.recourceid}';OnMouseUp('ItemTreeMenu');");
                  </c:otherwise>
                 </c:choose>
                </c:forEach>
               
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.precourceid==null}" > 
                 if(T${item.pcode}!=null)
                  T${item.pcode}.add(T${item.recourceid});             
                  </c:when>
                  <c:otherwise>
                  if(T${item.precourceid}!=null)
                   T${item.precourceid}.add(T${item.recourceid});
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

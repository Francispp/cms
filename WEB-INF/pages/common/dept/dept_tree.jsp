<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<c:set var="title" value="部门树" />
<html >
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>
<%@ include file="/common/cxtree.inc"%>
<%@ include file="/common/buffalo.inc"%>

<script src="${ctx}/common/core_js/core.js"></script>
<script type="text/javascript">
//新增
function addTreeItem(pid){
 parent.frames['_content'].location.href="dept!add.action?pdeptid="+pid;
}
//新增
function addRootTreeItem(orgId){
   if(isNaN(orgId))
     orgId=orgId.substring(2);
 parent.frames['_content'].location.href="dept!edit.action?pdeptid=&orgId="+orgId;
}
//删除
function removeTreeItem(selectid){
 if(confirm('您确定要删除选择的记录吗？')){
  parent.location.href="dept!delete.action?keys="+selectid;
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
   ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else alert('操作失败!');",'deptManagerService',methodName,obj);
   flagCutOrCpy="";
   cut_copy_menu_code="";	
}
function goBack(){
 location.href='dept.action';
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
  <cms:auth resCode="SYS_DEPT_ADD"> 
   ItemTreeMenu.AddItem("update11","","","新增部门 ","ItemTreeMenu","javascript:addTreeItem(treeSelectId);");
  </cms:auth>
  <cms:auth resCode="SYS_DEPT_DELETE"> 
   ItemTreeMenu.AddItem("update121","","","删除部门 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
  </cms:auth>
  <cms:auth resCode="SYS_DEPT_MODI">
  ItemTreeMenu.AddItem("update122","","","剪切部门 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,1);");
  ItemTreeMenu.AddItem("update123","","","复制部门 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,0);");
  ItemTreeMenu.AddItem("update124","","","粘贴部门 ","ItemTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  </cms:auth>
  ItemTreeMenu.AddItem("update10","","","刷新菜单 ","ItemTreeMenu","javascript:refreshMenu();");   
  document.writeln(ItemTreeMenu.GetMenu()); 
  
  var RootTreeMenu = new RightMenu("RootTreeMenu");
  <cms:auth resCode="SYS_DEPT_ADD"> 
  RootTreeMenu.AddItem("update2001","","","新增部门 ","RootTreeMenu","javascript:addRootTreeItem(treeSelectId);"); 
  RootTreeMenu.AddItem("update202","","","粘贴部门 ","RootTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  </cms:auth>
  RootTreeMenu.AddItem("update102","","","刷新菜单 ","RootTreeMenu","javascript:refreshMenu();");
  document.writeln(RootTreeMenu.GetMenu());    
</script>
<ww:head/>
</head>
<body oncontextmenu="window.event.returnValue=false" style="margin: 0px; text-align:left; ">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td bgcolor="#ffffff" height="2"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><a href="javascript:parent.changeViewStyle();" onFocus="this.blur()"><c:out value="${title}"/></a></div>
		</div>
		</div>
		</div>
		</td>
	</tr>
   </table>
   <div style="text-align: left; padding-left: 20px;">
      <script  language="javascript"> 
      var rightMenu=null;//style=1,出现右击管理菜单
     <c:forEach var="org" items="${requestScope.orgs}" varStatus="status">
       document.write("<br>");
       var Tree${org.orgCode}=new WebFXTree('${org.orgName}', '',null, null, null,null,'false','${org.oid}',null,null,"javascript:treeSelectId='P_${org.oid}';OnMouseUp('RootTreeMenu');");
       document.write(Tree${org.orgCode});       
      </c:forEach>     
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
             <c:if test="${pageStyle==1}">
               rightMenu="javascript:treeSelectId='${item.deptid}';OnMouseUp('ItemTreeMenu');"
             </c:if>
               <c:choose>
                 <c:when test="${item.pdeptid==null}" > 
                   var T${item.deptid} = new WebFXTreeItem('${item.deptname}', 'javascript:onclickItem(${item.deptid})', null, null, null,null,'false','${item.deptid}',null,null,rightMenu);                 
                  </c:when>
                  <c:otherwise>
                   var T${item.deptid} = new WebFXTreeItem('${item.deptname}', 'javascript:onclickItem(${item.deptid})', null, null, null,null,'false','${item.deptid}',null,null,rightMenu);
                  </c:otherwise>
                 </c:choose>
                </c:forEach>
               
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.pdeptid==null}" >
                  Tree${item.coreOrg.orgCode}.add(T${item.deptid});              
                  </c:when>
                  <c:otherwise>
                   T${item.pdeptid}.add(T${item.deptid});
                  </c:otherwise>
                 </c:choose>
                </c:forEach>         
      </script>                   
 </div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

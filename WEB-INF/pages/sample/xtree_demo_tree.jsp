<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="部门动态Demo树" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/cxtree.inc"%>
<script type="text/javascript" src="${ctx}/common/xloadtree/extend_xloadtree.js"></script>

<script type="text/javascript">
//open window style
var winstyle="status:0;dialogHeight:365px;dialogWidth:380px;"
//新增
function addTreeItem(pid){
	var bolRet=window.showModalDialog("category!edit.do?pareid="+pid,"",winstyle);
	if(bolRet) refreshMenu();
}
//修改
function editTreeItem(oid){
	var bolRet=window.showModalDialog("category!edit.do?cateid="+oid,"",winstyle);
	if(bolRet) refreshMenu();
}
//新增
function addRootTreeItem(pid){
	addTreeItem(pid);
 //parent.frames['_content'].location.href="resource!edit.action?presourceid=&pcode="+pcode;
}
//删除
function removeTreeItem(selectid){
 //parent.location.href="resource!delete.action?keys="+selectid;
 if(confirm('确定删除该类别？'))
    CategoryManager.removeCategory(selectid,drawDetail);
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
	//CategoryManager.savePasteCategoryTo(params,drawDetail);
function drawDetail(result){
	if(result){
	  alert('操作成功!');
	  location.reload();
	 }else
	  alert('操作失败!');
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
  //CategoryManager.savePasteCategoryTo(obj[0]+'',obj[1]+'',obj[2],drawDetail);   
   //ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else alert('操作失败!');",'resourceManagerService',methodName,obj);
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
  ItemTreeMenu.AddItem("update11","","","新增部门 ","ItemTreeMenu","javascript:addTreeItem(treeSelectId);");
  ItemTreeMenu.AddItem("update120","","","修改部门 ","ItemTreeMenu","javascript:editTreeItem(treeSelectId);");
  ItemTreeMenu.AddItem("update121","","","删除部门 ","ItemTreeMenu","javascript:removeTreeItem(treeSelectId);");
  ItemTreeMenu.AddItem("update122","","","剪切部门 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,1);");
  ItemTreeMenu.AddItem("update123","","","复制部门 ","ItemTreeMenu","javascript:clkMenuTree(treeSelectId,0);");
  ItemTreeMenu.AddItem("update124","","","粘贴部门 ","ItemTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  ItemTreeMenu.AddItem("update10","","","刷新菜单 ","ItemTreeMenu","javascript:refreshMenu();"); 
  document.writeln(ItemTreeMenu.GetMenu()); 
  
  var RootTreeMenu = new RightMenu("RootTreeMenu");
  RootTreeMenu.AddItem("update2001","","","新增部门 ","RootTreeMenu","javascript:addRootTreeItem(treeSelectId);"); 
  RootTreeMenu.AddItem("update202","","","粘贴部门 ","RootTreeMenu","javascript:pasteTreeItem(treeSelectId);");
  RootTreeMenu.AddItem("update102","","","刷新菜单 ","RootTreeMenu","javascript:refreshMenu();");
  document.writeln(RootTreeMenu.GetMenu());
  
  //根节点单击url
  var rootUrl="javascript:";
  //节点单击url
  var itemUrl= rootUrl;  
</script>
<ww:head/>
</head>
<body  oncontextmenu="window.event.returnValue=false" nowrap topmargin="0" leftmargin="0" bgcolor="#F4F4F0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<!--div id="titleDiv"><c:out value="${title}"/></div-->
<!-- 操作功能按钮条 -->
<!-- 页面主要内容 -->
<div id="page-content-no-border">
<table align="center" width="100%" border="1">
     <tr>
            <td width="80%" align="left" >
        ${title}               
      <script  language="javascript"> 

      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.pdeptid==null}" >
                   var T${item.deptid}=new WebFXLoadTree('${item.deptname}','<ww:url value="xtreeDemo!loadXtree.action?pareid="/>${item.deptid}',rootUrl,null,null,null,null,null,'${item.deptid}',null,null,"javascript:treeSelectId='${item.deptid}';OnMouseUp('RootTreeMenu');");	
                   document.write(T${item.deptid});
                  var rootid=${item.deptid};
                  </c:when>
                  <c:otherwise>
                  </c:otherwise>
                 </c:choose>
                </c:forEach>        
      </script>       
            </td>
    </tr>               
</table>
</div>
<!-- page foot -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

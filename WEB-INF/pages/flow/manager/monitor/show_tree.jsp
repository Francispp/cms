<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="引擎环境" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/cxtree.inc"%>
<%@ include file="/common/buffalo.inc"%>

<script type="text/javascript">

function selectOne(value) {
  window.returnValue = value;
  window.close();
}
//点击树节点执行的方法
function onclickItem(value) {
   parent.onClickTreeNode(value);
}
//点击树节点(包节点上)执行的方法
function onclickPackage(value) {
 //parent.document.frames['_content'].location.href="processMonitor!list.action?packageId="+value;
   parent.onClickTreeNode(value);
}
//点击树节点(流程节点上)执行的方法
function onclickWorkflow(value) {
    //parent.document.frames['_content'].location.href="processMonitor!list.action?packageId="+value;
     parent.onClickTreeNode(value);
}
</script>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<!--tr>
		<td bgcolor="#ffffff" height="1"></td>
	</tr-->
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><p><c:out value="${title}"/></p></div>
		</div>
		</div>
		</div>
		</td>
	</tr>
<tr><td>   
<script  language="javascript"> 
       var T_package=new WebFXTree('所有流程的包', "javascript:onclickPackage('')",null, null, null,null,'false','',null,null);
       document.write(T_package);
      <c:forEach var="item" items="${requestScope.packages}" varStatus="status">
        var T${item.key}= new WebFXTreeItem('${item.key}', "javascript:onclickPackage('${item.key}')", null, null, null,null,'false','${item.key}',null,null,"javascript:treeSelectId='${item.key}';//OnMouseUp('ItemTreeMenu');");
         T_package.add(T${item.key});
         <c:forEach var="workflow" items="${item.value}" varStatus="index">
           var T${workflow.id}= new WebFXTreeItem('${workflow.name}', "javascript:onclickWorkflow('${item.key}:${workflow.id}')", null, null, null,null,'false','${workflow.id}',null,null,"javascript:treeSelectId='${workflow.id}';OnMouseUp('RootTreeMenu');");
            T${item.key}.add(T${workflow.id});  
         </c:forEach>       
      </c:forEach>        
      </script>                          
</td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

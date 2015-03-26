<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="栏目管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<script type="text/javascript">
//点击树节点执行的方法
function onClickTreeNode(value) {
  document.frames['_content'].location.href="column!edit.action?id="+value;
}
//设置内容窗口显示内容
function setMainFrameUrl(path){
 document.frames['_content'].location.href=path;
}
var viewstyle=0;//显示方式，若为1表示列表，0表示树结构
function changeViewStyle(){
  location.href="column.action?pageStyle=1";
  //viewstyle=1;   
}
</script>
</head>
<body>
<!-- 页面标题 -->
<div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
  </div>
</div>
 <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="194" height="100%" valign="top" >
   <iframe src="${ctx}/cms/column!tree.action?pageStyle=1" llowtransparency="yes" id="menu" name="menu" scrolling="no" width="100%" height="486" frameborder="0"></iframe>
   </td>
   <td width="78%" valign="top">
   <iframe src="" llowtransparency="yes" id="_content" name="_content" scrolling="no" width="100%" height="486" frameborder="0"></iframe>
   </td>
</tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

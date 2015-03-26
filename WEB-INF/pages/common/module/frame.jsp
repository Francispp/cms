<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%> 
<c:set var="title" value="模块管理" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<%@ include file="/common/js.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<style type=text/css>
body { 
  background-color: #FFFFFF;
  scrollbar-face-color: #FFFFFF;
  scrollbar-shadow-color: #FFFFFF;
  scrollbar-highlight-color: #FFFFFF; 
  scrollbar-3dlight-color: #FFFFFF;
  scrollbar-darkshadow-color: #FFFFFF;
  scrollbar-track-color: #FFFFFF;
  scrollbar-arrow-color: #FFFFFF
}
.navigation {border-right: black 1px solid; margin: 5px 0px 0px 5px; background-color: #f7f7f7}
</style>
<script type="text/javascript">
//点击树节点执行的方法
function onClickTreeNode(value) {
  document.frames['_content'].location.href="module!edit.action?id="+value;
}
var viewstyle=0;//显示方式，若为1表示列表，0表示树结构
function changeViewStyle(){
  location.href="module.action?pageStyle=1";
  //viewstyle=1;   
}
</script>
<ww:head/>
</head>
<body style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 
<div style="text-align:right;width:100px;float:right;"> <button id="backbutton" onclick="changeViewStyle()" class="button">列表显示</button>&nbsp;</div>
 -->

<!-- 页面标题 -->
<!--table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td bgcolor="#ffffff" height="2"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}"/></div>
		</div>
		</div>
		</div>
		</td>
	</tr>
   </table-->
   
<div id="page-content-no-border">
<!-- 页面主要内容 -->
 <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="180" height="100%" valign="top" >
  <iframe allowtransparency="yes" scrolling="no" width="100%" height="500" src="${ctx}/base/module!tree.action" frameborder="0" name="menu"  id="menu"></iframe>

   </td>
   <td valign="top">
   <iframe allowtransparency="yes" height="500" width="100%" scrolling="auto" src="" frameborder="0" name="_content"></iframe>
   </td>
</tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

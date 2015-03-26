<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="流程监控管理" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<script type="text/javascript">
//点击树节点执行的方法
function onClickTreeNode(value) {
  document.frames['_content'].location.href="processMonitor!"+executeMethod+".action?packageId="+value;
  
}
var viewstyle=0;//显示方式，若为1表示列表，0表示树结构
function changeViewStyle(){
  //location.href="resource.action?style=1";
  //viewstyle=1;   
}
var executeMethod="list";//list 显示流程列表，assigns显示任务列表
//流程监控
function processMonitor(){
 document.frames['_content'].location.href="";
 executeMethod="list";
}
//任务监控
function assignsMonitor(){
 document.frames['_content'].location.href="";
 executeMethod="assigns";
}
</script>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<!--tr>
		<td bgcolor="#ffffff" height="1"></td>
	</tr-->
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt">${title}</div>
		</div>
		</div>
		</div>
		</td>
	</tr>
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<!--tr>
				<td height="1"></td>
			</tr-->
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
		<li id="button1"><a href="javascript:processMonitor();">流程监控</a></li>
		<li id="button1"><a href="javascript:assignsMonitor();">任务监控</a></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
<tr><td>
<!-- 页面主要内容 -->
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="200" height="100%" valign="top" >
   <iframe src="${ctx}/flow/processMonitor!showtree.action" llowtransparency="yes" id="menu" name="menu" scrolling="auto" width="100%" height="466" frameborder="0"></iframe>
   </td>
   <td width="78%" valign="top">
   <iframe src="" llowtransparency="yes" id="_content" name="_content" scrolling="auto" width="100%" height="436" frameborder="0"></iframe>
   </td>
</tr>
</table>
</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

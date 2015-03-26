<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="流程参与者管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
</head>
<script>
function onChangePackge(){
var obj=new Array;
obj[0]='{userinfo}';
obj[1]=document.myform.packageId.value;
updateSelect(document.myform.participantId,'flowManagerService','getAllParticipants',obj);
document.frames['participantFrame'].location.replace('${ctx}/flow/participantManager!list.action?packageId='+document.myform.packageId.value);
}
function onChangeParticipant(){
document.frames['participantFrame'].location.replace('${ctx}/flow/participantManager!list.action?packageId='+document.myform.packageId.value+"&participantId="+document.myform.participantId.value);
}
</script>
<body topmargin="0" leftmargin="0" topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<form id="myform" name="myform" onsubmit="return true;" action="" method="POST">
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
		<td valign="top" height=20>

<input type="hidden" name="userId" value="">
<input type="hidden" name="userName" value="">
<table width="100%" border="0">
<tr>
<td align="right"><b>&nbsp;&nbsp;包:</b></td><td><ww:select name="packageId" list="packages" required="true" emptyOption="false" onchange="onChangePackge(); "/></td>
<td align="right"><b>&nbsp;&nbsp;参入者:</b></td><td><select name="participantId"  onchange="onChangeParticipant(); "></select></td>
</tr>
</table>
		</td>
	</tr>	
	<tr>
		<td valign="top">
<!-- 页面主要内容 -->
<iframe src='${ctx}/flow/participantManager!list.action' frameborder=0 width=100% height=420 name="participantFrame"> </iframe>
		</td>
	</tr>	
</table>
  </form>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
<script>
onChangePackge();
</script>
</body>
</html>
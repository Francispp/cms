<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<ww:head/>
<head>
<title></title>
<script>
var DeleteHintWord='<ww:property value="getText(\"RESOURCE.HINTINFO.DELETEHINTWORD\")"/>';
var UploadFileNameIsNull='<ww:property value="getText(\"UploadFileNameIsNull\")"/>';
var LetSelectRecordWord='<ww:property value="getText(\"RESOURCE.HINTINFO.LETSELECTRECORDWORD\")"/>';
var UploadFileExtendNameIsNull='<ww:property value="getText(\"UploadFileExtendNameIsNull\")"/>';
//view date
function ext_popUpCalendar(ctl,ctl2, format,timeformat){
 if(ctl!=null&&ctl.readOnly!=null&&ctl.readOnly==true){
 return ;
 }
  popUpCalendar(ctl,ctl2, format,timeformat);
}
function onChangePackge(){
var obj=new Array;
obj[0]=document.myform.packageId.value;
updateSelect(document.myform.participantId,'com.jacal.workflow.services.WorkFlowManagerService','getAllParticipants',obj);
document.frames['participantFrame'].location.replace('${ctx}/flow/participantManager!list.action?packageId='+document.myform.packageId.value);
}
function onChangeParticipant(){
document.frames['participantFrame'].location.replace('${ctx}/flow/participantManager!list.action?packageId='+document.myform.packageId.value+"&participantId="+document.myform.participantId.value);
}
function check(){
	var val;
	var cha;
    val=document.myform.elements['uploadFiles'].value;
	if(val=='')
	{
		alert(UploadFileNameIsNull);
		return;
	}
	else if(val.lastIndexOf(".")<=0)
	{
		alert(UploadFileExtendNameIsNull);
		return;
	}
	document.myform.submit();
}
</script>
</head>
<body topmargin="0" leftmargin="0" topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<form id="myform" name="myform" onsubmit="return true;" action="xpdlFileManager!upload.action" method="POST" enctype="multipart/form-data">

<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<!--tr>
		<td bgcolor="#ffffff" height="1"></td>
	</tr-->
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><ww:property value="getText('manager_title')"/></div>
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
<input type="hidden" name="userId" value="">
<input type="hidden" name="userName" value="">
<table width="100%" border="0">
<tr>
<td align="right"><b>&nbsp;&nbsp;<ww:property value="getText('LetSelectFile')"/></b></td><td><input type="file" name="uploadFiles" size="30"></td>
 <td>
 <ul>
  <li id="button"><a href="javascript:check();"><ww:property value="getText('UploadButten')"/></a></li>
  <li id="button"><a href="javascript:document.frames['xpdlFileFrame'].deletesFiles();"><ww:property value="getText('RESOURCE.COMMON.DEL')"/></a></li>
  </ul>
  </tr></table>

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
	
<!-- 页面主要内容 -->
			
</td></tr>
</table>
<iframe src='${ctx}/flow/xpdlFileManager!list.action' frameborder=0 width=99% height=500 name="xpdlFileFrame"> </iframe>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
  </form>
</body>
</html>
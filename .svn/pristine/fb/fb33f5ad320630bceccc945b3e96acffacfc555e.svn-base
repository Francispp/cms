<%@ page contentType="text/html; charset=UTF-8"%>
<%
if(session.getAttribute(com.cyberway.core.objects.Loginer.LOGININFO_SESSION)==null){
%>
<script>
window.returnValue="timeout";
window.close();
</script>
<%
}
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
function init() {
	window.onbeforeunload = beforclose;
	var arg = window.dialogArguments;
	if (arg != null){
		alert(arg);
		var actionUrl = "";
		if(arg.actionUrl){
			actionUrl = arg.actionUrl;
		} else {
			actionUrl = "${ctx}/uploadFile!upload.action?file.channelId="+arg.channelId+"&file.docId="+arg.docId+"&file.fileType="+arg.fileType+"&file.fieldName="+arg.fieldName;
		}
		if(actionUrl){
			document.getElementById("uploadForm").action = actionUrl;
			return;
		}
	}
	window.returnValue="errorarg";
	window.close();
}

function closeTips(){
	document.all.tips.style.display="none";
}

function ev_add() {
	var uploadValue = document.getElementById("upload").value;
	if (!uploadValue) {
		alert("请选择附件！");
		return;
	}
	var fpath = document.uploadForm._file.value;
	var findex = fpath.lastIndexOf(".");
	if(findex == -1){
		alert("附件必须要有后缀!");
		return;
	}
	tips.style.display='';
	frm.style.display='none';
	document.uploadForm.submit();
}
</script>
</head>
<body onload="init()" bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<div id="tips" style="display:none">
		<table width=100% height=100% align=center cellpadding=0 cellspacing=0 border=0 >
			<tr>
				<td align=center valign=middle><img src="${ctx}/images/common/wait.gif"/></td>
			</tr>
		</table>
	</div>
	<div id="frm">
		<form id="uploadForm" name="uploadForm" enctype="multipart/form-data" method="post" action="${ctx}/cms/attachment!uploadFile.action?isPic=${isPic}&name=${name}&fpath=${fpath}&docid=${docid}&channelid=${channelid}&siteid=${siteid}&maxSize=${maxSize}" >
			<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
				<tr height="50%">
					<td width="20%"><span style="font-size:12px">上传文件:</span></td>
					<td width="50%" align="center"><input type="file" name="upload" id="upload"/></td>
					<td width="30%" align="left"><input type="button" name="btnAdd" value="上传" class="bt" onClick="ev_add()"/> &nbsp;&nbsp;<input type="button" class="btcss" onClick="doEmpty()" value="关闭"/> </td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="附件上传" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>导入文件</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<script language="JavaScript">
<!--
function ft(){
	window.event.returnValue = false;
}
function ev_add() {
  if (document.uploadForm._file.value=="") {
    alert("请选择附件！");
    return;
  }
  var rt = "${ftype}";
  var fpath = document.uploadForm._file.value;
  var findex = fpath.lastIndexOf(".");
  if(findex == -1)
  {
  alert("附件必须要有后缀!");
  return;
  }
  if(findex != -1 && rt.length > 0 && rt != "undefined")
  {
  fpath = fpath.substring(findex,fpath.length); 
  if(rt.indexOf(fpath) == -1)
   {
   alert("只允许上传："+rt);
   return;
   }
  }
  tips.style.display='';
  frm.style.display='none';
  document.uploadForm.submit();
  //window.close();
}

// -->
</script> 
</head>
<body>
<div id="tips" style="display:none">
  <table width=100% height=100% align=center cellpadding=0 cellspacing=0 border=0 >
    <tr>
      <td align=center valign=middle><img src="${ctx}/images/common/wait.gif"/></td>
    </tr>
  </table>
</div>
<br/>
<div id="frm">
<form name="uploadForm" enctype="multipart/form-data" method="post" action="/component/jsfunction!importData.action?name=${name}&fpath=${fpath}&docid=${docid}&maxSize=${maxSize}" >
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr height="50%">
	<td colspan="3"><!-- &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btcss" onclick="doExit()" value="取消">&nbsp;&nbsp; --></td>
  </tr>
  <tr height="50%">
    <td width="20%"><span style="font-size:12px">导入文件:</span></td>
    <td width="50%" align="center"><input class='input-text' type="file" name="_file" onKeyDown="ft()"/> </td>
    <td width="30%" align="left"><input type="button" name="btnAdd" value="上传" class="bt" onClick="ev_add()"/> &nbsp;&nbsp;<input type="button" class="btcss" onClick="doEmpty()" value="关闭"/> </td>
  </tr>
</table>
</form>
</div>
</body>
</html>
<script language="JavaScript">
<!--

 var actionURL = "${actionURL}";
 var siteId = "${siteId}";
 if(actionURL.length > 0 && actionURL != "undefined")
 {
 if(siteId.length > 0 && siteId != "undefined")
 {
 document.uploadForm.action = actionURL+"&siteId=${siteId}";
 }
 else
 {
  document.uploadForm.action = actionURL;
 }

 }
 
// -->
</script> 

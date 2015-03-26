<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ww" uri="/webwork" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ec" uri="ecside" %>
<%@ taglib prefix="fck" uri="http://fckeditor.net/tags-fckeditor" %>
<%@ taglib prefix="cms" uri="cms" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<%
if(session.getAttribute("loginer")==null){
	%>
	<script>
	
	window.returnValue="timeout";
	window.close();
	
	</script>
  
  <% 
 }else{	
  if(session.getAttribute("portalcode")==null){
   session.setAttribute("portalcode","cms3");
  }		
 }
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="portalcode" value="${session.portalcode}" />
<c:set var="style" value="/${session.LOCALE_STYLE}" />
<c:if test="${empty session.LOCALE_STYLE or session.LOCALE_STYLE eq 'index'}">
 <c:set var="style" value="" />
</c:if>
<c:set var="sytlePath" value="${ctx}/styles/${portalcode}${style}" />
<c:set var="default_style" value="${sytlePath}/style.css" />
<c:set var="default_imagepath" value="${ctx}/images/${portalcode}${style}" />
<%@ include file="/common/js.inc"%>
<c:set var="title" value="附件上传" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>上传文件</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<script language="JavaScript">
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
  fpath = fpath.substring(findex+1,fpath.length);
  if(rt.indexOf(fpath.toLowerCase()) == -1)
   {
   alert("只允许上传："+rt);
   return;
   }
  }
  tips.style.display='';
  frm.style.display='none';
 
	document.uploadForm.submit();
}

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
<div id="frm">
<form name="uploadForm" enctype="multipart/form-data" method="post" action="${ctx}/cms/attachment!uploadFile.action?isPic=${isPic}&name=${name}&fpath=${fpath}&docid=${docid}&channelid=${channelid}&siteid=${siteid}&maxSize=${maxSize}" >
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr height="50%">
    <td width="20%"><span style="font-size:12px">上传文件:</span></td>
    <td width="50%" align="center"><input class='input-text' type="file" name="_file" onKeyDown="ft()"/> </td>
    <td width="30%" align="left"><input type="button" name="btnAdd" value="上传" class="bt" onClick="ev_add()"/> &nbsp;&nbsp;<input type="button" class="btcss" onClick="doEmpty()" value="关闭"/> </td>
  </tr>
</table>
</form>
</div>
</body>
</html>
<script language="JavaScript">

 var actionURL = "${actionURL}";
 var siteId = "${loginer.siteId}";
 if(actionURL.length > 0 && actionURL != "undefined")
 {
  document.uploadForm.action = actionURL;
 }
 
</script> 

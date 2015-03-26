<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc" %>

<html>
<head>
<title>附件</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body >
	<c:if test="${not empty actionMessages}">
        <c:forEach var="msg" items="${actionMessages}">
            <script>alert("${msg}");</script>
        </c:forEach>    
	</c:if>
<script language="JavaScript">
if("${count}" != "" && "${count}" != "null")
alert("共成功导入${count}条数据");
if("${xml}" == "maxSize")
{
alert("只允许上传小于${maxSize}M的文件");
window.close();
}
else
{
if(window.parent != undefined && window.parent.document.myform != undefined && window.parent.document.myform.txtUrl != undefined)
{
window.parent.document.myform.txtUrl.value = '${xml}';
alert("发送成功！");
}
else
{
  window.top.returnValue = '${xml}';
  window.top.close();
  }
}
</script>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/js.inc"%>

<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body >
<script language="JavaScript">
<!--
if("${xml}" == "maxSize")
{
	alert("只允许上传小于${maxSize}M的文件");
window.close();
}else if("${xml}" == "uploadError"){
	alert('上传失败');
	window.close();
}
else
{
if(window.parent != undefined && window.parent.document.myform != undefined && window.parent.document.myform.txtUrl != undefined)
{
window.parent.document.myform.txtUrl.value = '${mediaid}';

}
else
{
  window.returnValue = '${xml}';
  window.close();
  }
}
// -->
</script>
</body>
</html>

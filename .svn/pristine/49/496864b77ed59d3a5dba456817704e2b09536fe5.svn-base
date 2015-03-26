<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<c:set var="title" value="Sample test" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<!--link href="${ctx}/common/default.css" rel="stylesheet" type="text/css"-->
	<link href="${ctx}/common/table/extremecomponents.css" type="text/css" rel="stylesheet">
<%@ include file="/common/js.inc"%>
<ww:head/>
</head>

<body>
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%>
</DIV>
<!-- 页面主要内容 -->
<div id="page-content-no-border">
<a href="test!language.action?language=cn">中文</a>  <a href="test!language.action?language=en">英文</a> <a href="test!language.action?language=tw">繁体</a>
<br>
<form method="post" action="user!save.action">
<ww:text  name="test"/>
</form>
</div>


</body>

</html>

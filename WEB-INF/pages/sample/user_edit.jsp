<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<c:set var="title" value="Sample user 编辑" />

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
<form method="post" action="user!save.action">

<ww:hidden  name="domain.userId" id="userId"/>
<table align="center" width="800" height="600" border="1">
<tr>
<td width="400" valign="top">
<div>
	<br>
用户名：<ww:textfield  name="domain.loginId" id="loginId" maxlength="10" />
移动电话：<ww:textfield  name="domain.mobilephone" id="mobilephone" maxlength="10" />
电话：<ww:textfield  name="domain.phone" id="phone" maxlength="10" />
备注：<ww:textfield  name="domain.mark" id="mark" maxlength="10" />
<ww:submit value="保存"></ww:submit>
	</div>
</td>
</tr>
</table>
</form>
</div>


</body>

</html>

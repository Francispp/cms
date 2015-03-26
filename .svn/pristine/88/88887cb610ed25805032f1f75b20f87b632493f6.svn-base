<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<c:set var="title" value="Sample user 编辑" />

<html>
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
<form method="post" action="dept!save.action">

<ww:hidden  name="domain.userId" id="userId"/>
<table align="center" width="800" height="600" border="1">
<tr>
<td width="400" valign="top">
<div>
	<br>
部门ＩＤ：<ww:textfield  name="domain.deptid" id="deptid" maxlength="10" />
部门名：<ww:textfield  name="domain.deptname" id="deptname" maxlength="10" />
类型：<ww:textfield  name="domain.depttype" id="depttype" maxlength="10" />
备注：<ww:textfield  name="domain.remark" id="remark" maxlength="10" />
<ww:submit value="保存"></ww:submit>
	</div>
	${domain.subDepts}
</td>
</tr>
</table>
</form>
</div>


</body>

</html>

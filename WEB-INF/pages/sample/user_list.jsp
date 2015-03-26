<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<c:set var="title" value="Sample user 列表" />
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
<div class="search_field">
	<form method="post" action="${ctx}//sample/user!search.action">
		<div class="search_form">
			<p>Search books: <input type="text" name="queryString" class="search" value='${queryString}'/>
				<input type="submit" value="Search" class="submit"/>
				<a class="grey" href="#">Advanced</a>
			</p>
		</div>
	</form>
</div>
<!-- 页面主要内容 -->
<div id="page-content-no-border">
	<h1>用户列表</h1>
	<ec:table items="users" var="user"
			  action="${pageContext.request.contextPath}/sample/user.action">
		<ec:exportXls fileName="UserList.xls" tooltip="导出 Excel"/>
		<ec:row>
			<ec:column property="rowcount" cell="rowCount" sortable="false" title="序号" width="60"/>
			
			<ec:column property="loginId" title="用户名"/>
			<ec:column property="email" title="邮箱"/>
			<ec:column property="mobilephone" title="移动电话"/>
			<ec:column property="phone" title="电话"/>
			<ec:column property="mark" title="备注"/>			
			<ec:column property="null" title="修改" sortable="false" width="40" viewsAllowed="html">
				<a href="user!edit.action?id=${user.userId}">修改</a>
			</ec:column>
			<ec:column property="null" title="删除" sortable="false" width="40" viewsAllowed="html">
				<a href="user!delete.action?id=${user.userId}">删除</a>
			</ec:column>
		</ec:row>
	</ec:table>
</div>

<div>
	<button id="addbutton" onclick="location.href='user!edit.action'" class="button">Add</button>
</div>

</body>

</html>

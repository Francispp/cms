<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/weixin.inc"%>
<html>
<head>
<title>签退</title>
 <link href="/weixin/css/index.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript">
	$(function() {
		$(".sub_btn").click(function() {
			$("#myForm").submit();
		});
	});
</script>
<style type="text/css">
</style>
</head>
<body>
	<h1>签退成功</h1>
	<span>签退时间</span>
	<ww:date name="domain.signOutTime" format="yyyy-MM-dd HH:mm:ss" />
<form id="myForm" action="/weixin/attendance!doSave.action?type=0" method="post">
<ww:hidden name="domain.id"></ww:hidden>
<ww:hidden name="domain.score" value="100"></ww:hidden>
<input type="button" class="sub_btn" value="提交">
</form>
</body>
</html>

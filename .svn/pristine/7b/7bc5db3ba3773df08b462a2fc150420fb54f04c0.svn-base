<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/weixin.inc"%>
<html>
<head>
<title>签退</title>
 <link href="/weixin/css/index.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript">
$(document).ready(function(){
	var signInTime = '<ww:property value="domain.signInTime" />';
	var signOutTime = '<ww:property value="domain.signOutTime" />';
	var date=signInTime.getTime()-signOutTime.getTime(); //时间差的毫秒数
	$("#runing").html(date);
});
</script>
<style type="text/css">
</style>
</head>
<body>
	<h1>签退成功</h1>
	<span>签到时间</span><ww:date name="domain.signInTime" format="yyyy-MM-dd HH:mm:ss" />
	<span>签退时间</span><ww:date name="domain.signOutTime" format="yyyy-MM-dd HH:mm:ss" />
	<span>工作时长</span><div id="runing"></div>
	<span>签退人</span><ww:property value="domain.userName"/>
	<span>签到分数</span><ww:property value="domain.score"/>
</body>
</html>

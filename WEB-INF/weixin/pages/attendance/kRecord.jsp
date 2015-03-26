<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/common/js.inc"%> --%>
<%-- <%@ include file="/common/weixin.inc"%> --%>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<html>
<head>
<title>考勤记录</title>
<!-- 在写移动端的网站的时候， 一定要写一个meta的name为viewport的属性， 因为该属性代表着网站页面的自适应。 代表着网站为驱动设备的宽度。 -->
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<link rel="stylesheet" href="/common/jquery/mobile/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" href="/common/jquery/mobile/jquery.mobile.datepicker.css" />
<link rel="stylesheet" href="/common/jquery/mobile/jquery.mobile.datepicker.theme.css" />
<script src="/common/jquery/mobile/jquery-1.11.1.min.js"></script>
<script src="/common/jquery/mobile/jquery.mobile-1.4.5.min.js"></script>
<script src="/common/jquery/mobile/jquery.mobile.datepicker.js"></script>
<script src="/common/jquery/mobile/jquery.ui.datepicker.js"></script>
<script language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>

<script type="text/javascript">
$(document).ready(function(){
	var signinTxt = '<ww:property value="attendance.signinTxt" />';
	$("#txt").html(replace_em(signinTxt));
});
function replace_em(str) {
	str = str.replace(/\</g, '<;');
	str = str.replace(/\>/g, '>;');
	str = str.replace(/\n/g, '<;br/>;');
	str = str.replace(/\[em_([0-9]*)\]/g,'<img src="/weixin/images/face/$1.gif" border="0" />');
	return str;
}
</script>
<style type="text/css">
</style>
</head>
<body>
<!-- <h1>考勤记录页面制作中....</h1> -->
<input type="text" data-role="date" data-inline="true" name="signTime">
<span>签到:</span><ww:date name="attendance.signinTime" format="yyyy-MM-dd HH:mm:ss" /><br/>
<span>签退:</span><ww:date name="attendance.signOutTime" format="yyyy-MM-dd HH:mm:ss" /><br/>
<span>工作时长:</span><br/>
<span>签到说说:</span><div id="txt"></div><br/>
<span>签退心情:</span><ww:property value="attendance.score"/>
</body>
</html>

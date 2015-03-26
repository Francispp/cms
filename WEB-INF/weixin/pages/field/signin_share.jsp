<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/weixin.inc"%>
<html>
<head>
<title>签到</title>
<!-- 在写移动端的网站的时候， 一定要写一个meta的name为viewport的属性， 因为该属性代表着网站页面的自适应。 代表着网站为驱动设备的宽度。 -->
<meta name="viewport" content="width=device-width, initial-scale=1"> 
 <link href="/weixin/css/index.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript">
$(document).ready(function(){
	var signinTxt = '<ww:property value="domain.signInTxt" />';
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
	<h1>签到成功</h1>
	<span>签到时间：</span>
	<ww:date name="domain.signInTime" format="yyyy-MM-dd HH:mm:ss" /><br/>
	<span>签到人：</span><ww:property value="domain.userName"/><br/>
	<span>签到说说：</span><div id="txt"></div><br/>
	<span>签到地点：</span><ww:property value="domain.signInPlace" /><br/>
	<span>签到图片：</span><img src='<ww:property value="domain.signInPic" />'/>
</body>
</html>

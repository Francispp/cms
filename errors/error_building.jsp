<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站正在建设中</title>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
</head>

<body class="login_ab">
<div class="problem_ab">
    <h1 class="problem-title_ab">
        <img src="${default_imagepath}/pic_problem_error.jpg">
    </h1>
    <div class="problem-content_ab">
        <p class="e blue">The website is currently under construction</p>
    </div>
    <a id="winClose" onClick="javascript:global_ab.fn.popWindow.removePopWindow(false);" class="problem-back_ab1"></a>
	<div class="problem-buildBg_ab"></div>
    <a href="mailto:<%= com.cyberway.cms.Constants.DEFAULT_SENDER %>?subject=dear administrator" class="problem-link_ab">联系管理员</a>
</div>
</body>
</html>
<script type="text/javascript">
var win = window.top.document.getElementById("newPopWindow_win_ab");
if (win == null || win == 'undefined') {
	document.getElementById("winClose").style.display = 'none';
}
</script>
<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ page import="org.acegisecurity.context.SecurityContextHolder" %>
<%@ page import="org.acegisecurity.Authentication" %>
<%@ page import="org.acegisecurity.ui.AccessDeniedHandlerImpl" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>错误页面</title>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
</head>

<body class="login_ab">
<div class="problem_ab">
    <h1 class="problem-title_ab">
        <img src="${default_imagepath}/pic_problem_error.jpg">
    </h1>
    <div class="problem-content_ab">
        <p class="c red">您没有权限访问当前页！</p>
        <p class="e red">You do not have permission to access this page</p>
        <div style="display:none;">
        <%= request.getAttribute(AccessDeniedHandlerImpl.ACEGI_SECURITY_ACCESS_DENIED_EXCEPTION_KEY)%>
        <%Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) { %>
			Authentication object as a String: <%= auth.toString() %>
		<%}%>
		</div>
    </div>
	<a id="winClose" onClick="javascript:global_ab.fn.popWindow.removePopWindow(false);" class="problem-back_ab1"></a>
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
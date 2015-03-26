<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>页面已过期</title>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
</head>

<body class="login_ab">
	<%
if(session.getAttribute("loginer")==null){
	%>
	<script>
window.top.location.href='${ctx}/login.action';
</script>
	<%
  //response.sendRedirect(request.getContextPath()+"/login.action"); 		
 }else{	
  if(session.getAttribute("portalcode")==null){
   session.setAttribute("portalcode","cybercms");
  }		
 }
%>
<div class="problem_ab">
    <h1 class="problem-title_ab">
        <img src="${default_imagepath}/pic_problem_error.jpg">
    </h1>
    <div class="problem-content_ab">
        <p class="c red">提示信息</p>
        <p class="e red">抱歉！当前页面已过期，请重新从易联网首页登录。</p>
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
<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ page import="org.apache.log4j.Logger" %>
 <!DOCTYPE HTML>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>异常提示</title>
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

    <%
	  //Exception exception=null;
	  if (exception != null){ //from JSP
		//Exception from JSP didn't log yet ,should log it here.
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
	  } else if (request.getAttribute("exception") != null) {//from Spring
		exception = (Exception) request.getAttribute("exception");
	  }else if (request.getAttribute("javax.servlet.error.exception") != null){ //from servlet
		exception = (Exception) request.getAttribute("javax.servlet.error.exception");                        
	  }
	  if (exception != null){
		  Logger.getLogger(this.getClass()).error(exception.getMessage(), exception);
	  }
    %>
    <div class="problem_ab">
      <h1 class="problem-title_ab"><img src="${default_imagepath}/pic_problem_error.jpg"></h1>
      <div class="problem-content_ab">
        <p class="c red">提示信息</p>
        <p class="e red"><%if (exception!=null){%><%=exception.getMessage()%><%}%></p>
        <div style="display:none;">
          <%if (exception!=null) {exception.printStackTrace(new java.io.PrintWriter(out));}%>
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
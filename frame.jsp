<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ec" uri="ecside" %>
<%@ taglib prefix="fck" uri="http://fckeditor.net/tags-fckeditor" %>
<%@ taglib prefix="cms" uri="cms" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<%
if(session.getAttribute("loginer")==null){
	//response.sendRedirect(request.getContextPath()+"/login.action"); 	
	%>
	<script>
	
	window.returnValue="timeout";
	//window.open("${ctx}/login.action");
	//alert(opener);
	//opener.location.href="/login.action";
	window.close();
	
	</script>
  
  <% 
 }else{	
  if(session.getAttribute("portalcode")==null){
   session.setAttribute("portalcode","cms3");
  }		
 }
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="portalcode" value="${session.portalcode}" />
<c:set var="style" value="/${session.LOCALE_STYLE}" />
<c:if test="${empty session.LOCALE_STYLE or session.LOCALE_STYLE eq 'index'}">
 <c:set var="style" value="" />
</c:if>
<c:set var="sytlePath" value="${ctx}/styles/${portalcode}${style}" />
<c:set var="default_style" value="${sytlePath}/style.css" />
<c:set var="default_imagepath" value="${ctx}/images/${portalcode}${style}" />
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript">
var isInFrame = true;

function init() {
	window.onbeforeunload = beforclose;
}
function beforclose() {
	if(window.returnValue!=null && window.returnValue == true && window.opener!=null ) {
		window.opener.document.forms[0].submit();
	}
}
function ev_onclose(){
	if (this.returnValue==true && this.opener!=null) {
		opener.location.reload();
	}
}
function closeTips(){
	document.all.tips.style.display="none";
}
</script>
</head>
<body  onload="init()" bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="tips">
  <table width=100% height=100% align=center cellpadding=0 cellspacing=0 border=0 >
    <tr>
      <td align=center valign=middle><img src="${ctx}/images/common/wait.gif"></td>
    </tr>
  </table>
</div>
<iframe align="center"  width="100%" height="100%" name="myitem" scrolling=auto frameborder=0 border=0 onload="document.all.tips.style.display='none'"></iframe>
</body>
<script language="javascript">
var arg = window.dialogArguments;
if (arg != null) {
	if(arg.url == null){
		myitem.location = arg;
	}
	else{
	myitem.location = arg.url;
	}
	if (arg.title != null){
		document.title = arg.title;
	}
	else if (window.location.search!=null) {
		var pos = location.search.lastIndexOf("title=") + 6;
		var title = location.search.substring(pos, location.search.length);
		document.title = title;
	}
}
else{
	if(window.location.search!=null && window.location.search!="") {
		var pos = location.search.lastIndexOf("&title=") + 7;
		var title = location.search.substring(pos, location.search.length);
		document.title = title;
		myitem.location = location.search.substring(1, location.search.length);
	}
}
</script>
</html>

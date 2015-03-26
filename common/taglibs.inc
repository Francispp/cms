<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ec" uri="ecside" %>
<%@ taglib prefix="fck" uri="http://fckeditor.net/tags-fckeditor" %>
<%@ taglib prefix="cms" uri="cms" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
   session.setAttribute("portalcode","cms3");
  }		
 }
%>


<c:set var="portalcode" value="${session.portalcode}" />
<c:set var="style" value="/${session.LOCALE_STYLE}" />
<c:if test="${empty session.LOCALE_STYLE or session.LOCALE_STYLE eq 'index'}">
 <c:set var="style" value="" />
</c:if>
<c:set var="sytlePath" value="${ctx}/styles/${portalcode}${style}" />
<c:set var="default_style" value="${sytlePath}/style.css" />
<c:set var="default_imagepath" value="${ctx}/images/${portalcode}${style}" />
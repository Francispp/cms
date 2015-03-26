<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ec" uri="ecside" %>
<%@ taglib prefix="fck" uri="http://fckeditor.net/tags-fckeditor" %>
<%@ taglib prefix="ww" uri="/webwork" %>
<%@ taglib prefix="cms" uri="cms" %>
<%@ taglib prefix="jodd" uri="http://www.springside.org.cn/jodd_form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
     <%
     if(request.getParameter("loginid")!=null){
     String loginno=request.getParameter("loginid");
     com.cyberway.common.service.BasicService service=(com.cyberway.common.service.BasicService)com.cyberway.core.utils.ServiceLocator.getBean("basicService");
     session.setAttribute("loginer",service.ebizlogon(loginno, "",""));
     response.sendRedirect(request.getContextPath()+"/");
     //long tparm=com.cyberway.core.utils.EncryptionHelper.getTimeParm(curr_date);       
     //String after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt(loginno,com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
     //after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
     //String after_tparm= com.cyberway.core.utils.EncryptionHelper.encrypt(String.valueOf(tparm),com.cyberway.core.utils.EncryptionHelper.PASS_PHRASE);     
     //after_tparm=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_tparm); 
      
      //response.sendRedirect(request.getContextPath()+"/ebizlogon.action?loginid="+after_encrypt+"&style="+after_tparm+"&fromUrl=");
     }
    
     %>
    
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="portalcode" value="kms" />
<c:set var="style" value="/${session.LOCALE_STYLE}" />
<c:if test="${empty session.LOCALE_STYLE or session.LOCALE_STYLE eq 'index'}">
 <c:set var="style" value="" />
</c:if>
<c:set var="sytlePath" value="${ctx}/styles/${portalcode}${style}" />
<c:set var="default_style" value="${sytlePath}/style.css" />
<c:set var="default_imagepath" value="${ctx}/images/${portalcode}${style}" />
<html>
<head>
<title>测试登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
<%@ include file="/common/js.inc"%>
</head>
<script type="text/javascript">
    function test(errorString, exception)
    {
        alert(errorString);
    }
    function login(){
      if(document.getElementById('loginid').value==''){
       alert('请输入用户名!');
       return ;       
       }
       location.href="login.jsp?loginid="+document.getElementById('loginid').value;
    }
</script>
<body>
<%
   String str="";//com.cyberway.core.utils.FileUtil.readFile("e:/kmsfile/test.txt");   
%>
<form name="_item" method="post" action="">
测试登录
<table width="89%" border="0">  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="14%">用户名：</td>
    <td width="58%"><input  name="loginid" type="text" size="12"/><a href="javascript:login();">登录</a></td>
    <td width="28%">&nbsp;</td>
  </tr>    
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>

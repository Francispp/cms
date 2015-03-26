<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="portalcode" value="cms3" />

<c:set var="sytlePath" value="${ctx}/styles/${portalcode}" />
<c:set var="default_style" value="${sytlePath}/style.css" />
<c:set var="default_imagepath" value="${ctx}/images/${portalcode}" />



<%@ include file="/common/meta.inc"%>
<title><ww:property value="getText('page.title')" /></title>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/dhtmlxMenu/menu.inc"%>
<script language="JavaScript" type="text/JavaScript">
function enterLogin(){
  if(typeof myform == 'undefined'){
	 myform = $('myform');
  }
  

  
  
 
  
  var event = arguments[0]||window.event;
  var currentKey = event.charCode||event.keyCode;
  if(currentKey==13){
      if(doSubmit){
          myform.submit();
      }
   }
  
//记住密码
  if($('RetentionPassword').checked){
   setCookie('j_username',$('j_username').value);
   setCookie('j_password',$('j_password').value);
   setCookie('RetentionPassword',true);
  }else{//清除记住的密码
   setCookie('j_username','');
   setCookie('j_password','');
   setCookie('RetentionPassword',false);  
  }
}
document.onkeypress=enterLogin;
		
function doSubmit(){
  if(myform.j_username.value == ""){
    alertMessage('<ww:property value="getText('HINTINFO.InputUserName')"/>');    
    $('j_username').focus();
    return false;
  }
  if(myform.j_password.value == ""){
	alertMessage('请输入密码!');    
	$('j_password').focus();
	return false;
  }
 
  //记住密码
  if($('RetentionPassword').checked){
   setCookie('j_username',$('j_username').value);
   setCookie('j_password',$('j_password').value);
   setCookie('RetentionPassword',true);
  }else{//清除记住的密码
   setCookie('j_username','');
   setCookie('j_password','');
   setCookie('RetentionPassword',false);  
  }
  return true;
}
function doReset(){
	myform.j_username.value = '';
	myform.j_password.value = '';
}
function doExit(){
	window.close();
}
function setLanguage(language,style){
 if(language!=null && language!='')
  setCookie('LOCALE_LANGUAGE',language);
 if(style!=null && style!='')
   setCookie('LOCALE_STYLE',style);
 location='login!language.action?language='+language+'&style='+style;
}
</script>
<ww:head />
</head>
<STYLE TYPE="text/css">
<!--
body {
overflow: auto;
clip:  rect(   );
background-color: #79afc1;
}
-->
</style>
<body style="text-align:center">
		
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	
	<tr><td style="height:60px;">&nbsp;</td></tr>
	
	<tr>
	<td>
	
						<table width="555" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="${default_imagepath}/login_06.jpg" width="100%"/></td>
							</tr>
							<tr>
								<td width="100%" valign="top"
									background="${default_imagepath}/login_08.jpg" align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<!--<td width="18%">&nbsp;</td>-->
										
										<td style="text-align:center">
										<div style="height:26px;"></div>
										
										<div id="login" style="text-align:center;width:100%">
										<form method="post" action="${ctx}/login!login.action"
											name="myform" style="text-align:center">
											<ww:hidden name="fromUrl" />
											<ww:hidden name="portalcode" value="cms3"/>

										<p style="text-align:center">

										
										<img src="${default_imagepath}/login_icon.gif"
											width="15" height="14" align="absmiddle" /> <ww:property
											value="getText('loginid')" /> <input name="j_username"
											type="text" class="formcss1" id="j_username" size="18"
											<c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if> />
										</p>
										<p><img src="${default_imagepath}/login_icon1.gif"
											width="15" height="12" align="absmiddle" /> <ww:property
											value="getText('password')" /> <input name="j_password"
											type="password" class="formcss1" style="width:133;" id="j_password" /></p>
										<p><input type="checkbox" name="RetentionPassword"
											value="checkbox" style="margin-left:68px;" id="RetentionPassword"/> <ww:property
											value="getText('RetentionPassword')" /></p>
											

										<p id="sub" style="text-align:center;">
										
											<a href="#"
												onclick="if (doSubmit()) myform.submit();"> <img
												src="${default_imagepath}/login_icon2.gif" width="50" height="18" border="0" />
											</a>

											<a href="#"> <img src="${default_imagepath}/login_icon3.gif"
												width="50" height="18" border="0" onclick="doReset()" /></a>
									</p>
										</form>
										</div>

										</td>
										
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td><img src="${default_imagepath}/login_09.jpg" width="100%"/></td>
							</tr>
						</table>
	</td></tr>
	
	<tr><td style="height:40px;">&nbsp;
<div id="login_message">
<c:if test="${not empty actionErrors}">
	        <c:forEach var="err" items="${actionErrors}">
	        <script>
	alertMessage("${err}");
	</script>
        	<!--<font color="red">${err}</font>--><br>
        </c:forEach> 
</c:if>
<c:if
	test="${not empty param.login_error}">
	
	<font color="red"> <ww:property value="getText('login_error')" /><BR>	
	<%=((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage()%>
	 </font>
</c:if></div>
<div id="login2">
</div>
</td></tr>
</table>
<%@ include file="/common/foot.inc"%>

</body>
</html>



<script>

  if($('j_username').value=='' && getCookie('RetentionPassword',false)){
    $('j_username').value=getCookie('j_username','');
    $('j_password').value=getCookie('j_password','');
    $('RetentionPassword').checked = getCookie('RetentionPassword',false);
    //alertMessage($('RetentionPassword').checked);
   }
   //myform.RetentionPassword.checked = getCookie('RetentionPassword',false);
   $('j_username').focus();

 //init context menu
 //aMenu=new dhtmlXContextMenuObject('120',0,dhtmlxMenuImPath);
 //aMenu.menu.setGfxPath(dhtmlxMenuImPath);		
 //aMenu.menu.loadXML("${ctx}/portals/cms/right_menu_xml.xml");				
 //aMenu.setContextMenuHandler(onButtonClick);
		
 //set context zones
 //aMenu.setContextZone("login_center",1);
 
 function onButtonClick(itemId,itemValue){
 //alert(itemId+":"+itemValue);
  if(itemId=='index')
   setLanguage('',itemId);
  if(itemId=='index1')
    setLanguage('',itemId);
 }	
</script>









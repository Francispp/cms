<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="portalcode" value="kms" />

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
  if(event.keyCode==13){
  if(doSubmit)
    myform.submit();
   }
  }
document.onkeypress=enterLogin;
		
function doSubmit(){
  if(myform.j_username.value == ""){
    alertMessage('<ww:property value="getText('HINTINFO.InputUserName')"/>');    
    $('j_username').focus();
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
	myform.loginid.value = '';
	myform.password.value = '';
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
<body>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" bgcolor="#aaaaaa">
	
	<tr><td style="height:80px;">&nbsp;</td></tr>
	
	<tr>
	<td>
	
						<table width="555" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="${default_imagepath}/login_06.jpg" width="100%"/></td>
							</tr>
							<tr>
								<td width="100%" valign="top"
									background="${default_imagepath}/login_08.jpg">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<!--<td width="18%">&nbsp;</td>-->
										
										<td>
										<div style="height:26px;"></div>
										
										<div id="login">
										<form method="post" action="${ctx}/j_acegi_security_check"
											name="myform"><ww:hidden name="fromUrl" />

										<p><img src="${default_imagepath}/login_icon.gif"
											width="15" height="14" align="absmiddle" /> <ww:property
											value="getText('loginid')" /> <input name="j_username"
											type="text" class="formcss1" size="18"
											<c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if> />
										</p>
										<p><img src="${default_imagepath}/login_icon1.gif"
											width="15" height="12" align="absmiddle" /> <ww:property
											value="getText('password')" /> <input name="j_password"
											type="password" class="formcss1" style="width:133;" /></p>
										<p><input type="checkbox" name="RetentionPassword"
											value="checkbox" style="margin-left:68px;" /> <ww:property
											value="getText('RetentionPassword')" /></p>

										<p id="sub" style="text-align:center;">
										
											<a href="#"
												onclick="if (doSubmit()) myform.submit();"> <img
												src="${default_imagepath}/login_icon2.gif" width="50" height="18" border="0" />
											</a>

											<a href="#"> <img src="${default_imagepath}/login_icon3.gif"
												width="66" height="18" border="0" /></a>
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
	
	<tr><td style="height:90px;">&nbsp;
	

<div id="login_message"><c:if
	test="${not empty param.login_error}">
	<font color="red"> <ww:property value="getText('login_error')" /><BR>
	<BR>
	<ww:property value="getText('FailReason')" /> <%=((AuthenticationException) session
									.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY))
									.getMessage()%> </font>
</c:if></div>
<div id="login2"><!--Copyright by Cyberway Compucomm Co.,Ltd. All Rights Reserved.2007　　有任何疑问，请联系<a href="#"><font color="ff0000">系统管理员</font></a><br /> -->
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

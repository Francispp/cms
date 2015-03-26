<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="portalcode" value="cms" />
<c:set var="style" value="/${session.LOCALE_STYLE}" />
<c:if test="${empty session.LOCALE_STYLE or session.LOCALE_STYLE eq 'index'}">
 <c:set var="style" value="" />
</c:if>
<c:set var="sytlePath" value="${ctx}/styles/${portalcode}${style}" />
<c:set var="default_style" value="${sytlePath}/style.css" />
<c:set var="default_imagepath" value="${ctx}/images/${portalcode}${style}" />
<%@ include file="/common/meta.inc"%>
<title><ww:property value="getText('page.title')"/></title>
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
<ww:head/>
</head>
<body>
<div id="logintop"></div>
<div id="login_center">
<form method="post" action="${ctx}/j_acegi_security_check" name="myform">
    <ww:hidden name="fromUrl"/>
    <!--ww:hidden name="portalcode" value="frame"/-->
<div id="loginbg">
  <div id="loginleft">
    <div id="logo"></div>
    <div id="edition"><a href="javascript:setLanguage('zh_CN');"><img src="${default_imagepath}/login2.gif" width="60" height="22" border="0" /></a>
     <a href="javascript:setLanguage('zh_TW');"><img src="${default_imagepath}/login3.gif" width="59" height="22" border="0" /></a> 
     <a href="javascript:setLanguage('en_US');"><img src="${default_imagepath}/login4.gif" width="78" height="22" border="0" /></a></div>
  </div>
  <div id="login">
  <p><img src="${default_imagepath}/login5.gif" width="15" height="14" align="absmiddle" /> <ww:property value="getText('loginid')"/>
    <input name="j_username" type="text" class="formcss1" size="18" <c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if>/>
  </p>
  <p><img src="${default_imagepath}/login6.gif" width="15" height="12" align="absmiddle" /> <ww:property value="getText('password')"/> 
    <input name="j_password" type="password" class="formcss1" size="20" />
  </p>
  <p>
    <input type="checkbox" name="RetentionPassword" style="margin-left:15px;"/>
<ww:property value="getText('RetentionPassword')"/> </p>
    <div id="sub">
       <ul>
	      <li><a href="#" onclick="if (doSubmit()) myform.submit();"><ww:property value="getText('Login')"/></a></li>
		  <li><a href="#"><ww:property value="getText('Registration')"/></a></li>
		  <li id="one"><a href="#"><ww:property value="getText('ForgetPassword')"/></a></li>
	   </ul>
    </div>
      </div>
</div>
</form>
</div>
<div id="login_message">
<c:if test="${not empty param.login_error}">
 <font color="red"> <ww:property value="getText('login_error')"/><BR> <BR> <ww:property value="getText('FailReason')"/> <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %> </font>
 </c:if>
</div> 
<div id="login2"><!--Copyright by Cyberway Compucomm Co.,Ltd. All Rights Reserved.2007　　有任何疑问，请联系<a href="#"><font color="ff0000">系统管理员</font></a><br /> -->
</div>
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script>
  //检测当前窗口是否为最外层
  if(top.location.href!=location.href)
    top.location.href=location.href;
   //记住上次的语言环境
   var locale_language=getCookie('LOCALE_LANGUAGE','');
   var locale_style=getCookie('LOCALE_STYLE','');
   
   if((locale_language!='' && locale_language!='${session.LOCALE_LANGUAGE}')
   || (locale_style!='' && locale_style!='${session.LOCALE_STYLE}'))
    setLanguage(locale_language,locale_style);
   //if((locale_style!='' && locale_style!='${session.LOCALE_LANGUAGE}')))
    //setLanguage(locale_language,locale_style);    
//alertMessage($('RetentionPassword').checked);
  if($('j_username').value=='' && getCookie('RetentionPassword',false)){
    $('j_username').value=getCookie('j_username','');
    $('j_password').value=getCookie('j_password','');
    $('RetentionPassword').checked = getCookie('RetentionPassword',false);
    //alertMessage($('RetentionPassword').checked);
   }
   //myform.RetentionPassword.checked = getCookie('RetentionPassword',false);
   $('j_username').focus();

 //init context menu
 aMenu=new dhtmlXContextMenuObject('120',0,dhtmlxMenuImPath);
 aMenu.menu.setGfxPath(dhtmlxMenuImPath);		
 aMenu.menu.loadXML("${ctx}/portals/cms/right_menu_xml.xml");				
 aMenu.setContextMenuHandler(onButtonClick);
		
 //set context zones
 aMenu.setContextZone("login_center",1);
 
 function onButtonClick(itemId,itemValue){
 //alert(itemId+":"+itemValue);
  if(itemId=='index')
   setLanguage('',itemId);
  if(itemId=='index1')
    setLanguage('',itemId);
 }	
</script>
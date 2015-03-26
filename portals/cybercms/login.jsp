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
<c:set var="portalcode" value="cybercms" />

<c:set var="sytlePath" value="${ctx}/styles/${portalcode}" />
<c:set var="default_style" value="${sytlePath}/css.css" />
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

	
function doSubmit(){
	document.getElementById("siteUrlHttp").value=location.hostname;
    if(myform.j_username.value == ""){
      document.getElementById("err").innerHTML="<img src='${default_imagepath}/ico_001_warm.gif' class='ico ico-001' /><font color='red'>请输入用户名!</font>";
  	  document.getElementById("j_username_li").className="login-frame-con-field-inField_ab login-frame-con-field-inField-wrong_ab";    	 
      $('j_username').focus();
      return false;
  }
  if(myform.j_password.value == ""){
	  document.getElementById("err").innerHTML="<img src='${default_imagepath}/ico_001_warm.gif' class='ico ico-001' /><font color='red'>请输入密码!</font>";
  	  document.getElementById("j_password_li").className="login-frame-con-field-inField_ab login-frame-con-field-inField-wrong_ab";
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
body {
overflow: auto;
clip:  rect(   );
background-color: #79afc1;
}
</style>


<body class="login_ab">

<form method="post" action="${ctx}/login!login.action"
											name="myform" class="login-frame_ab">
											<ww:hidden name="fromUrl" />
											<ww:hidden name="portalcode" value="cybercms"/>
										   <ww:hidden name="siteUrlHttp" id="siteUrlHttp" ></ww:hidden>
	<div class="login-frame-con_ab">
    	<ul class="login-frame-con-field_ab">
        	<li class="login-frame-con-field-inField_ab"  id="j_username_li">
            	<span>账号</span>
                 <input name="j_username"
											type="text" class="formcss1" id="j_username" size="18"
											<c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if> />
                <img src="${default_imagepath}/pic_blank.gif" />
            </li>
            <!--当li中的class为"login-frame-con-field-inField_ab login-frame-con-field-inField-wrong_ab"时，li中的img元素会显示红色X-->
            <li class="login-frame-con-field-inField_ab" id="j_password_li">
            	<span>密码</span>
                <input name="j_password"  type="password" class="formcss1" style="width:133;" id="j_password" />
                <img src="${default_imagepath}/pic_blank.gif" />
            </li>
            <li class="login-frame-con-field-tip_ab" >
            	
                <span id="err"><c:if test="${not empty actionErrors}">
	        <c:forEach var="err" items="${actionErrors}">
	          <script>
	          var err="${err}";
	         
	          if(err=="用户不存在或用户名与密码不匹配！"||err=="账号或密码为空！"||err=="用户过期！"){
	        	  document.getElementById("j_username_li").className="login-frame-con-field-inField_ab login-frame-con-field-inField-wrong_ab";
	        	  document.getElementById("j_password_li").className="login-frame-con-field-inField_ab login-frame-con-field-inField-wrong_ab";
	          }else if(err=="密码错误！"){
	        	  document.getElementById("j_password_li").className="login-frame-con-field-inField_ab login-frame-con-field-inField-wrong_ab";
	          }
	
	</script><img src="${default_imagepath}/ico_001_warm.gif" class="ico ico-001" />
        	<font color="red">${err}</font><br>
        </c:forEach> 
</c:if>
<c:if
	test="${not empty param.login_error}">
	<img src="${default_imagepath}/ico_001_warm.gif" class="ico ico-001" />
	<font color="red"> <ww:property value="getText('login_error')" /><BR>	
	<%=((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage()%>
	 </font>
</c:if></span>
            </li>
            <li class="login-frame-con-field-psdField_ab">
            	 
                	<label><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    	<input type="checkbox" name="RetentionPassword"
											value="checkbox"  id="RetentionPassword"/><!--   <ww:property
											value="getText('RetentionPassword')" />-->
                    	<span>记住密码</span>
                    </label>
            </li>
            <li class="login-frame-con-field-item_ab">
            	<input id="loginSubmit" type="button" value="" href="#" onclick="if(doSubmit()){ myform.submit();}" class="login-frame-con-field-loginBtn_ab" />
            </li>
        </ul>
        <div class="login-frame-con-text_ab">版权所有 &copy; 1996-2014 Cyberway 赛百威信息科技</div>
    </div>
</form>
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript" src="script/global_ab.js"></script>
<script>

document.onkeypress=enterLogin;	

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
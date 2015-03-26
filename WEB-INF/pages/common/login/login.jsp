<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<html>
<head>
<title><ww:property value="getText('page.title')"/></title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/js.inc"%>
<script language="JavaScript" type="text/JavaScript">
		function enterLogin(){
			if(event.keyCode==13){
				myform.submit();
			}
		}
		document.onkeypress=enterLogin;
		
function doSubmit_bak(){
  if(myform.loginid.value == ""){
    alert("请输入用户名！");
    myform.loginid.focus();
    return false;
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
</script>
<ww:head/>
</head>
<body  bgcolor="#ffffff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<!-- 状态提示栏 -->
<!--DIV ID="message"><%@ include file="/common/messages.inc"%></DIV-->
<!-- 页面标题 -->
<!-- 操作功能按钮条 -->
<div id="operationDivNoBorder">
  <a href="login!language.action?language=cn"><ww:property value="getText('SYSTEM.CONFIG.LANGUAGE_DEFAULT')"/></a> 
  <a href="login!language.action?language=en"><ww:property value="getText('SYSTEM.CONFIG.LANGUAGE_ENGLISH')"/></a> 
  <a href="login!language.action?language=tw"><ww:property value="getText('SYSTEM.CONFIG.LANGUAGE_TW')"/></a>
</div>
<!-- 页面主要内容 -->
<div id="page-content-no-border">
<form method="post" action="${ctx}/j_acegi_security_check" name="myform">
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr> 
    <td height="277"  align="center" valign="middle" bgcolor="#FFFFFF" > 
      <table width="51%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="3"><img src="${default_imagepath}/login_box01_01.jpg" width="540" height="8"></td>
        </tr>
        <tr> 
          <td width="6"><img src="${default_imagepath}/login_box01_03.jpg" width="6" height="201"></td>
          <td width="523"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="43%"><img src="${default_imagepath}/login_box01_04.jpg" width="239" height="201"></td>
                <td width="57%" align="center" bgcolor="F3F3F3">
<table width="92%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="45"><img src="${default_imagepath}/login_box01_06.jpg" width="119" height="33"></td>
                    </tr>
                    <tr>
                      <td height="63"><table width="94%" border="0" cellpadding="2" cellspacing="2" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
                         <ww:hidden name="fromUrl"/>
                         <!--ww:hidden name="portalcode" value="frame"/-->
                          <tr><td width="100%" height="29"> 
                          <table  border="0" width="100%" bgcolor="#FFFFFF">
                          <tr>
                            <td width="40%" height="29" align="right" bgcolor="F3F3F3"><ww:label name="getText('portalname')"/></td>
                            <td width="60%" height="29" align="left" bgcolor="F3F3F3"> 
                            <select name="portalcode">
                            <option value="frame">frame</option>
                            <option value="test">test</option>
                            </select>
                            </td>
                            </tr>
                            </table></td>
                          </tr>
                          <tr><td width="100%" height="29"> 
                          <table  border="0" width="100%" bgcolor="#FFFFFF">                                                    
                          <tr>
                            <td width="40%" height="29" align="right" bgcolor="F3F3F3"><ww:label name="getText('loginid')"/></td>
                            <td width="60%" height="29" align="left" bgcolor="F3F3F3"> 
                            <input name="j_username" type="text" class="textbox" size="18" <c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if>></td>
                            </tr>
                            </table></td>
                          </tr>
                          <tr><td width="100%" height="29"> 
                          <table  border="0" width="100%" bgcolor="#FFFFFF">
                          <tr> 
                           <td width="40%"  height="29" align="right" bgcolor="F3F3F3"><ww:label name="getText('password')"/></td>
                            <td  width="60%"height="29" align="left" bgcolor="F3F3F3">
                            <input name="j_password" type="password" class="textbox" size="18" onKeyPress="enterLogin();"></td>
                         </tr>
                         </table></td>
                          </tr>
              <tr><td width="100%" height="29"> 
                          <table  border="0" width="100%" bgcolor="#FFFFFF">
                          <tr> 
                           <td width="40%"  height="29" align="right" bgcolor="F3F3F3"><input type="checkbox" name="gd">记住密码</td>
                            <td  width="60%"height="29" align="center" bgcolor="F3F3F3">忘记密码</td>
                         </tr>
                         </table></td>
                          </tr>                          
                          <tr> 
                            <td height="44" align="center" bgcolor="F3F3F3"> 
                              <table width="117" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="58">
				    											<table width="37%"  border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width="9" align="right"><img src="${default_imagepath}/12.gif" width="9" height="40"></td>
                                        <td width="40"  align="center" class="bg5"> 
            				   											<input type="submit"  value="<ww:property value="getText('RESOURCE.COMMON.LOGIN')"/>">
            														</td>
                                        <td width="66"><img src="${default_imagepath}/3.gif" width="9" height="40" border="0"></td>
                                      </tr>
                                    </table></td>
                                  <td width="8" align="center"></td>
                                  <td width="67" align="center">
				 													<table width="19%"  border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width="9" align="right"><img src="${default_imagepath}/1.gif" width="9" height="40"></td>
                                        <td width="64"  align="center" class="bg5"> 
            																<input type="button" name="BReset" class="bt-login" onClick="doReset();" value="<ww:property value="getText('RESOURCE.COMMON.RESET')"/>">
																				</td>
                                        <td width="90"><img src="${default_imagepath}/3.gif" width="9" height="40" border="0"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
       								 <td align="center" colspan="3"><font color="red">
 							<c:if test="${not empty param.login_error}">
								<font color="red"> 登录失败,请重新登录.<BR> <BR> 失败原因: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %> </font>
							</c:if>      								 
    <c:if test="${not empty actionMessages}">
      <c:forEach var="msg" items="${actionMessages}">${msg}</c:forEach>
    </c:if>
   <c:if test="${not empty actionErrors}">
    <c:forEach var="err" items="${actionErrors}">${err}</c:forEach>
   </c:if>
</font></td>
      							</tr>
                  </table>
                </td>
              </tr>
            </table></td>
          <td width="11" align="right"><img src="${default_imagepath}/login_box01_08.jpg" width="11" height="201"></td>
        </tr>
        <tr> 
          <td colspan="3"><img src="${default_imagepath}/login_box01_10.jpg" width="540" height="14"></td>
        </tr>
      </table>
      
      <br>
      <ww:property value="getText('page.foot.label')"/><a href="<ww:property value="getText('SYSTEM.CONFIG.ADMIN_EMAIL')"/>"><ww:property value="getText('RESOURCE.COMMON.SYSTEMADMIN')"/></a>
      </td>
  </tr>
  <tr> 
    <td height="2" class="bg2"></td>
  </tr>
  <tr> 
    <td height="78" align="center" class="bg3">&nbsp;</td>
  </tr>
</table>
</form>
</div>
<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>
<script>
   myform.j_username.focus();
</script>
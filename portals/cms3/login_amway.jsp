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
<c:set var="portalcode" value="kms" />
<c:set var="sytlePath" value="${ctx}/styles/${portalcode}" />
<c:set var="default_style" value="${sytlePath}/portal_login.css" />
<c:set var="default_imagepath" value="${ctx}/images/${portalcode}" />
<%@ include file="/common/meta.inc"%>
<title>KMS</title>
<link href="${default_style}" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/dhtmlxMenu/menu.inc"%>
<script type="text/javascript" language="Javascript" src="http://amwaychinaep.accl.gcr-intranet.msd/wps/menu/menu_service.js"></script>
<script type="text/javascript" language="Javascript">var menuService_AppletCodebase ="http://amwaychinaep.accl.gcr-intranet.msd/wps/menu";</script>
<script language="JavaScript" type="text/JavaScript">
 if(top.location.href!=location.href)
    top.location.href=location.href;
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
    <body style="margin-left:0px" marginwidth="0" marginheight="0"  onLoad="if ( document.forms['wpsPageGroupSelectionForm'] != null ) document.forms['wpsPageGroupSelectionForm'].reset();" >       
	    <!--Table one contain a logo pic and a toolbar-->
	    <table width="1002" border="0" cellspacing="0" cellpadding="0" height="40" bgcolor="#FFFFFF" valign="middle" align="center">
	      <tr class="wpsToolBar">
	        <td width="13"></td>
	        <td width="540"><a href="http://amwaychinaep.accl.gcr-intranet.msd/wps/myportal/DefaultPortal/"><img src="${default_imagepath}/top_title_main.gif" width="186" height="30" border="0" /></a></td>
	        <td align="right">
<table border="0" cellspacing="0" cellpadding="0" height="29">
	<tr>
		<td class="wpsToolbarBannerBackground" width="0" valign="middle" nowrap ></td>
			<td><img src="${default_imagepath}/top_line_verticals.gif" width="1" height="23"></td>
			<td width="48" align="center" class="wpsToolBar" valign="middle" nowrap>
				<a href='http://amwaychinaep.accl.gcr-intranet.msd/wps/doc/zh/help/index.html' target="PortalHelpWindow" onClick="javascript: window.open('http://amwaychinaep.accl.gcr-intranet.msd/wps/doc/zh/help/index.html','PortalHelpWindow','resizable=yes,scrollbars=yes,menubar=no,toolbar=yes,status=no,width=800,height=600,screenX=10,screenY=10,top=10,left=10').focus();	return	false;"
                    class="public">&#24110;&#21161;</a>
			</td>
	</tr>
</table>
	       	</td>
	        <td width="13"></td>
	      </tr>
	    </table>
	   <!--Table two -->      
	    <table width="1002" height="12" border="0" cellspacing="0" cellpadding="0" valign="top" align="center">
		  <tr>
		    	<td width="13" height="4"></td>
        	      	<td width="540" height="4"></td>
		        <td><table width="436" height="4" border="0" cellspacing="0" cellpadding="0" valign="top">
		          <tr>
		            <td height="4" bgcolor="d4d4d4"></td>
		          </tr>
		        </table></td>
       			<td width="13" height="4"></td>
		  </tr>
		  <tr colspan="4" width="1002" height="8">
		  </tr>
            </table>
	   <!--Table three Start Flash-->
	    <table width="1002" height="145" border="0" cellspacing="0" cellpadding="0" align="center">
	       	  <tr>
	       		 <td height="6" bgcolor="#C8C8C8"></td>
	      	  </tr>
	     	   <tr>
	      		  <td height="2" bgcolor="#FFFFFF"></td>
	     	  </tr>
		  <tr>
		    <td><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="1002" height="148">
          <param name="movie" value="${default_imagepath}/topbanner.swf" />
          <param name="quality" value="high" />
          <embed src="${default_imagepath}/topbanner.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="1002" height="148"></embed>
        </object></td>
		  </tr>
		  <tr>
		        <td height="1" bgcolor="#FFFFFF"></td>
		  </tr>
	   </table>
	   <!-- End Flash-->	   	           
        <table width="1002" cellpadding="0" cellspacing="0" border="0" height="100%" align="center" bgcolor="#FFFFFF" align="center">
            <tr class="wpsToolBar">
                <td width="100%" height="2" valign="top" align="right" bgcolor="#ECEDEF">
                </td>
            </tr>
            <tr height="30">
                <td valign="top" colspan="2" background="${default_imagepath}/top_title_bg1.gif" style="background-repeat:repeat-x;background-attachment: fixed;background-color: #FFFFFF;background-position: top">
                </td>
            </tr> 
            <tr style="display:none">
            	<td align="left" valign="top">
			<tr style="display:none">
            <td nowrap="nowrap" align="left" valign="middle" class="wpsLinkBar">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr class="wpsToolBar" >
                            <td valign="middle" nowrap>&nbsp;<label for="usp_query">搜索：</label></td>
                            <td valign="middle" style="padding: 0px 4px 0px 4px;"><input class="wpsEditField" name="usp_query" type="text" size="12"/></td>
                            <td valign="middle" align="right"><input valign="middle" alt='搜索' src="${default_imagepath}/Search.gif" type="image" /></td>
                        </tr>
                    </table>
            </td>
            </tr>
				</td>
			</tr>                
            <tr>
                <td colspan="2" height="100%" valign="top">
                    <table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                            <td>   
                            </td>
                            <td width="5"><img width="5" src="${default_imagepath}/spacer.gif"/></td>                            
                            <td width="100%" height="100%" valign="top">
                                <a name="wpsMainContent"></a>
<table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td width="100%" valign="top">
<table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
	<tr height="100%">
		<td valign="top" >
<table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td width="100%" valign="top">
<a name="7_9_4S8"></a>

<table border="0" width="255" height="353" cellpadding="0" cellspacing="0" align="center" class="wpsPortletBody" style="background-attachment: fixed;background-color: #FFFFFF;background-image: url(http://amwaychinaep.accl.gcr-intranet.msd/wps/skins/html/DefaultPortalSkinForLogin2/left_pic_bottom.jpg);background-repeat: no-repeat;background-position: bottom;">
  <tr>
  <td width="100%" valign="top"><table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="6" align="center" valign="top"><img src="${default_imagepath}/public_1px_white.gif" width="238" height="6" /></td>
    </tr>   
    <tr>
      <td height="39" align="center" valign="top"><img src="${default_imagepath}/left_top.gif" width="238" height="39" /></td>
    </tr>  
	<tr height="27">
	   <td height="27"><table border="0" cellpadding="0" cellspacing="0" width="100%" height="27">
			 <tr height="27">
				<td height="27" width="28" nowrap align="left" valign="middle"><img src='${default_imagepath}/left_title_arrow.gif'></td>
				<!--Portlet Title-->
				<td nowrap class="copyright" height="27" width="100%" align="left" valign="middle" bgcolor="#A2CEE9" background="${default_imagepath}/left_title_bg_01.gif" style="background-repeat:repeat-x;background-position:middle">
					&nbsp;<strong><font color="#FFFFFF">&#30331;&#24405; Portlet</fonts></strong>
				</td>
				<!--Toolbar-->
				<td height="27" width="80" nowrap align="right" valign="middle" bgcolor="#A2CEE9" background="${default_imagepath}/left_title_bg_01.gif" style="background-repeat:repeat-x;background-position:middle"><table border="0" cellpadding="0" cellspacing="0">
					<tbody><tr>
					<td nowrap>&nbsp;</td>
					<td width="12" align="center">
						<a href="http://amwaychinaep.accl.gcr-intranet.msd/wps/portal/login/kcxml/04_Sj9SPykssy0xPLMnMz0vM0Y_QjzKLt4w39ncFSYGZfj76kTCxIH1vfV-P_NxU_QD9gtyIckdHRUUA7Vi81w!!/delta/base64xml/L0lJSklLVUpKb3BRIS9JSGpBQUF4QUFFUWtMS3JFaGtZIS80SVVHUllRdUlsYkNVYUEhLzZfOV8zT0UvN185XzRTOC83OTk!" target="portletHelpWindow" onClick="javascript: window.open('http://amwaychinaep.accl.gcr-intranet.msd/wps/portal/login/kcxml/04_Sj9SPykssy0xPLMnMz0vM0Y_QjzKLt4w39ncFSYGZfj76kTCxIH1vfV-P_NxU_QD9gtyIckdHRUUA7Vi81w!!/delta/base64xml/L0lJSklLVUpKb3BRIS9JSGpBQUF4QUFFUWtMS3JFaGtZIS80SVVHUllRdUlsYkNVYUEhLzZfOV8zT0UvN185XzRTOC83OTk!','portletHelpWindow','resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=450,height=260,screenX=200,screenY=200,top=200,left=200').focus();	return	false;">
					   	<img border="0" align="absmiddle" src='${default_imagepath}/title_help.gif' alt='帮助' title='帮助'></a>
					</td>				
					<td width="12" align="center">
						<a href='http://amwaychinaep.accl.gcr-intranet.msd/wps/portal/login/kcxml/04_Sj9SPykssy0xPLMnMz0vM0Y_QjzKLt4w39ncFSYGZfj76kTCxIH1vfV-P_NxU_QD9gtyIckdHRUUA7Vi81w!!/delta/base64xml/L0lDU0lLVUtVL0lGUkFBSWlNakNnZy80SkZpQ28wVEVKVG8vN185XzRTOC9u'><img border="0" align="absmiddle" src='${default_imagepath}/left_title_ico_a.gif' alt='最小化' title='最小化'></a>
					</td>					
					<td width="12" align="center">
					   	<a href='http://amwaychinaep.accl.gcr-intranet.msd/wps/portal/login/kcxml/04_Sj9SPykssy0xPLMnMz0vM0Y_QjzKLt4w39ncFSYGZfj76kTCxIH1vfV-P_NxU_QD9gtyIckdHRUUA7Vi81w!!/delta/base64xml/L0lDU0lLVW9wUUEhIS9JTlJBQUlpTWpFZ29RS2lnLzRKRmlDbzBURUpSby83XzlfNFM4L24!'><img border="0" align="absmiddle" src='${default_imagepath}/left_title_ico_b.gif' alt='最大化' title='最大化'></a>
					</td>
					</tr></tbody>
					</table>
				</td>
			</tr>
		</table>
	    </td>
	</tr>
          <tr>
            <td height="2"></td>
          </tr>
          <tr>
            <td height="1" background='${default_imagepath}/public_underline_01.gif'></td>
          </tr>
          <tr>
            <td height="11"></td>
          </tr>
          <!--Portlet body-->
	   <tr height="100%">
		  
		  <td dir="ltr" width="100%" valign="top">
<p>
 <form method="post" action="${ctx}/j_acegi_security_check" name="myform">
 <ww:hidden name="fromUrl" />
<table width="70%" cellspacing="2" cellpadding="0" align="left" border="0">
	<tr>
		<td>
    <table cellspacing="2" cellpadding="0" align="left" border="0">
       
            <tr>
                <td class="wpsEditText" align="left"><label for="userID">&#29992;&#25143;&#26631;&#35782;:</label></td>
            </tr>
            <tr>
    		<td class="wpsFieldText" align="left">
    		
    		<input autocomplete="off" dir="ltr" type="text" size="16"  name="j_username" <c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if>/>
    		
    		</td>
            </tr>
            <tr>
                <td class="wpsEditText" align="left"><label for="password">&#23494;&#30721;:</label></td>
            </tr>
            <tr>
                <td class="wpsFieldText" align="left"><input autocomplete="off" onKeydown='if(event.keyCode==13){if (doSubmit()) myform.submit();}'; dir="ltr" type="password" size="16" name="j_password"/></td>
            </tr>
	</table>
		</td>
		<td valign="bottom" width="100%">
                    <table border="0" cellpadding="0" cellspacing="4">
                        <tr>
                            <td nowrap>
                                <input class="wpsButtonText"  onclick="if (doSubmit()) myform.submit();" style="cursor:hand" type="button" border="0" align="absmiddle" name="PC_7_9_4S8__login" value='&#30331;&#24405;' />
                            </td>
                        </tr>
                    </table>
		</td>
	</tr>
</table>	
 </form>
		  </td>
	   </tr>
          <tr>
            <td height="6"><br/>&nbsp;&nbsp;&#35831;&#20351;&#29992;&#30331;&#24405;<span style="font-family: Arial, Helvetica, sans-serif">WINDOW ACCL</span>&#22495;&#30340;&#29992;&#25143;<br>&nbsp;&nbsp;&#21517;&#21644;&#23494;&#30721;,&#22914;&#26377;&#20219;&#20309;&#30097;&#38382;&#35831;&#33268;&#30005;&#30005;&#33041;&#37096;<br>&nbsp;&nbsp;&#28909;&#32447;&#12290;</td>
          </tr>
	</table></td>
	<td width="6">&nbsp;</td>
	<!--Display a streight line on the right-->
	<td width="1" valign="top" bgcolor="#C6C6C6"><img src='${default_imagepath}/public_1px_white.gif' width="1" height="2" /></td>
 	<td>&nbsp;&nbsp;</td>
      </tr>
</table>
<a name="skipportlet7_9_4S8"></a>
		</td>
	</tr>
</table>
		</td>
		<td valign="top" >
<table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td width="100%" valign="top">
<a name="7_9_7AF"></a>
<table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%" >
	<tr>
		<td valign="top">
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				
					<tr>
						<td height="3"><img alt="" border="0" width="1" height="1" src='http://amwaychinaep.accl.gcr-intranet.msd/wps/images/dot.gif'></td>
					<tr>
					<tr>
						<td dir="ltr" width="100%" height="100%" valign="top" >      
    <TABLE border="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%"><TR valign="top"><TD><table align="left" border="0" cellpadding="0" cellspacing="0"><TBODY><tr><td><table align="center" border="0" cellpadding="0" cellspacing="0"><TBODY><tr><td background="http://amwaychinaep.accl.gcr-intranet.msd/login/images/main_table_left_bg.gif" valign="top" width="39"><img height="95" src="${default_imagepath}/main_table_left.gif" width="39"></img></td><td valign="top" width="481"><table border="0" cellpadding="0" cellspacing="0" width="100%"><TBODY><tr><td><img height="72" src="${default_imagepath}/main_table_center_top.gif" width="481"></img></td></tr><tr><td bgcolor="#F4F5F8" valign="top"><table align="center" border="0" cellpadding="0" cellspacing="0" width="96%"><TBODY><tr><td height="10"></td></tr><tr><td><table align="center" border="0" cellpadding="0" cellspacing="0" height="220" width="98%"><TBODY><tr><td valign="top" width="128"><table align="center" border="0" cellpadding="0" cellspacing="0"><TBODY><tr><td height="4"></td></tr><tr><td><table border="0" cellpadding="2" cellspacing="1" class="border-gray"><TBODY><tr><td bgcolor="#FFFFFF"><img height="97" src="${default_imagepath}/main_pic_01.jpg" width="116"></img></td></tr><tr><td align="right" bgcolor="#FFFFFF" height="15"><a href="javascript:;"><img border="0" height="5" hspace="4" src="${default_imagepath}/main_arrow_right.gif" width="15"></img></a></td></tr></TBODY></table></td></tr></TBODY></table></td><td width="18">&nbsp;</td><td valign="top"><IFRAME frameborder="0" height="220" name="WebPage" scrolling="no" src="http://amwaychinaep.accl.gcr-intranet.msd/AmwayMiscApp/loginRedirect/redirectURL.jsp" width="100%"></IFRAME><script>
                            	WebPage.location.href = 'http://amwaychinaep.accl.gcr-intranet.msd/login/link1.html';
                            </script></td></tr></TBODY></table></td></tr></TBODY></table></td></tr></TBODY></table></td><td background="${default_imagepath}/main_table_right_bg.gif" valign="top" width="186"><img height="285" src="${default_imagepath}/main_table_right.jpg" width="186"></img></td></tr></TBODY></table></td></tr><tr><td><img border="0" height="55" src="${default_imagepath}/main_table_bottom2.jpg" usemap="#Map" width="532"></img></td></tr></TBODY></table></TD></TR></TABLE>
						</td>
					</tr>		
			</table>
		</td>
	</tr>
</table>
<a name="skipportlet7_9_7AF"></a>
		</td>
	</tr>
</table>
		</td>
	</tr>
</table>
		</td>
	</tr>
	<tr>
		<td width="100%" valign="top">
<table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
	<tr height="100%">
		<td valign="top" >
<a name="7_9_7AG"></a>
<table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%" >
	<tr>
		<td valign="top">

			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				
					<tr>
						<td height="3"><img alt="" border="0" width="1" height="1" src='http://amwaychinaep.accl.gcr-intranet.msd/wps/images/dot.gif'></td>
					<tr>
					<tr>
						<td dir="ltr" width="100%" height="100%" valign="top" >
							
</td>
</tr>
</table>
<script>

  if($('j_username').value=='' && getCookie('RetentionPassword',false)){
    $('j_username').value=getCookie('j_username','');
    $('j_password').value=getCookie('j_password','');
   // $('RetentionPassword').checked = getCookie('RetentionPassword',false);
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
   <c:if test="${not empty param.login_error}">
    alert("<ww:property value="getText('login_error')" />");
    </c:if>
</script>
    
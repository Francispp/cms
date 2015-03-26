<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs2.inc"%>
<c:set var="title" value="字段编辑" /> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<link href="${ctx}/styles/dtree.css" type="text/css" rel="stylesheet">
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/dtree/dtree.js"></script>
<script src="${ctx}/common/frame_js/select.js"></script>
<script language="JavaScript">
 var contextPath = '${ctx}';  
 
 function doReturn(){
 	
    var returnval ;
    var ereg = /\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/g; 
    
    var name =$F("username"); 
    if(name == ""){
    	alert("用户名称不能为空！");
        $("username").focus();
        return false; 
    }
    
    var email=$F("email");
     if(email == ""){
    	alert("邮件地址不能为空！");
        $("email").focus();
        return false; 
    }
   
    if(!ereg.test(email)){
        alert("请输入正确的邮件地址！");
        $("email").focus();
        return false;
    }
     
   $("myform").submit();
 }
 
 
 window.attachEvent("onload",function (){
 	var msg = "${msg}";
 	if(msg != "null" && msg != ""){
 		alert(msg);
 		if(msg.indexOf("成功") != -1)
 			window.close();
 	}
 });
</script>
</head>
<body style="margin: 10px;"> 
<form action="/component/subcreibe!save.action" id="myform" name="myform" method="post" style="margin: 0px;">
<ww:hidden name="channelId" id="channelId"></ww:hidden>
<ww:hidden name="username" id="username"></ww:hidden>
<table width="100%" border="1" align="left" cellpadding="0" cellspacing="10"> 
	<tr>
		<td width="25%" align="left" valign="top" class="inside_list">邮件地址：</td>
		<td width="60%" align="left"><ww:textfield  name="email" id="email" onkeypress="if(event.keyCode == 13) doReturn();"/></td>
		<td width="15%" align="left"><input type='button' name='btnReturn' value='确 定' class='bt-enter' onClick='doReturn();'></td>
	</tr>
</table>
</form>
</body>
</html> 
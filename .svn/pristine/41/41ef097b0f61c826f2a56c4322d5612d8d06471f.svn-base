
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="回复留言" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>
<link href="/styles/cms3/leaveWord.css" rel="stylesheet" type="text/css"></link>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script type="text/javascript">
function sendTo(){
 if($F("subject") == ""){
    alert("内容不能为空！");
    $("subject").focus();
   	return false;
 } 
  if($F("to") == ""){
    alert("内容不能为空！");
    $("to").focus();
   	return false;
 } 

 if($F("body") == ""){
    alert("内容不能为空！");
    $("body").focus();
   	return false;
 } 
 $("myform").submit();  
} 

function goBack(){
	window.location="/component/leaveword!list.action";
}
 
var msg = "${mailMsg}";
if(msg != "" && msg != "null")
	alert(msg); 

  
</script> 
<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0" style="margin:0px;padding:0px;" >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
<!-- style="background-color:#dfedef;" -->	
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr> 

	<!-- 页面主要内容 -->
	
	<tr><td style="padding-left:2px;padding-right:1px;">
<table width="500" border="0" align="center" style="margin-top:-3px;" bgcolor="#dfedef" height="100%">
	<tr>
		<td align="center" valign="top">
	 <table style="margin-top: -3px" border="0" width="500" bgcolor="#ffffff" align="center" height="100%">
            <tbody>
                <tr>
                    <td valign="top" align="center">  
                    <form method="post" name="myform" id="myform" action="/cms/email!sendTo.action">
                    	 <input type="hidden" name="jumUrl" value="/component/leaveword!sendEmail.action" />
						<table  cellspacing="0" cellpadding="0" class="mframe" >
						<tr> 
						  <td align="right"> <div class="fbart">标题：</div></td>
						  <td><div class="fbart"><input type="text" class="text" name="subject" id="subject"  maxlength="80" size="50" value="${domain.title}"></input></div></td> 
						</tr> 		
						<tr> 
						  <td align="right"> <div class="fbart">收件人：</div></td>
						  <td><div class="fbart"><input type="text"  name="to" id="to" class="text" maxlength="80" size="50" value="${domain.email}"></input></div></td> 
						</tr>
						<tr> 
						  <td colspan="2"> 	
							<textarea id="body" name="body" rows="5" style="width:99%;font-size:14px;"></textarea>
						  </td> </tr> 
						  <tr>
						  <td  align="right" colspan="2">
						  	<div class="fbarb">
						   	<ul>
							<li><a href="javascript:;" onclick="sendTo()" class="dr">回复</a></li>
							<li><a href="javascript:;" onclick="reset()" class="dr nob">清空</a></li>
							</ul> 
							</div> 
							</td></tr> 
						</table>  
                    </form>
               	 	</td>
				</tr>
			</tbody>
		</table> 
		</td>
	</tr>
</table>
</td></tr></table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%> 

</body>
</html>  
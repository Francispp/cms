<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="网上订阅" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/common/mootools.js" type="text/javascript"></script>
<script type="text/javascript">
function save(){ 

 if($F("userName") == ""){
 	alert("请输入用户名!");
 	$("userName").focus();
 	return false;
 }
 if($F("userEmail") == ""){
 	alert("请输入Email!");
 	$("userEmail").focus();
 	return false;
 }
 
 if($F("channel_Name") == ""){
 	alert("请选择频道!");
 	$("channel_Name").focus();
 	return false;
 }
 
 myform.submit();
} 
 
function goBack(){
	window.location = "/component/subcreibe!list.action";
}
function setSiteName(opt){
	$("siteName").value = opt.options[opt.selectedIndex].text; 
}

function selchn(){
	

	var retval = Json.evaluate(window.showModalDialog("/common/fckeditor/editor/plugins/cms/dialog/channelPicker.html", "13", "dialogWidth=400px;dialogHeight=400px;status=no;scroll=yes;resizable=yes"));
   	 
   	$('channel_Name').value = retval.text;
   	$('channel_Id').value  = retval.id;
  }
  
</script> 
<ww:head />
</head>
<body style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->

<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<!--style="background-color:#dfedef;"  --> 
	<tr><td>
	<table  width="100%" align="left"   border="0" cellspacing="0" cellpadding="0"  bgcolor="#ffffff">
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
	</table>
	</td>
	</tr>


	<!-- 操作栏 -->

	<tr>
		<td valign="top"  height="100%">
		<table  width="100%" align="left"   border="0" cellspacing="0" cellpadding="0"  bgcolor="#ffffff">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
				<c:if test="${isEdit==true}">
						<li id="button"><a href="javascript:save()">保存</a></li>
					</c:if>
					<li id="button"><a href="javascript:goBack()">返回</a></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<!-- 页面主要内容 -->
	<tr><td style="padding-left:2px;padding-right:1px;"> 
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#dfedef"> 
		<tr>
		<td align="center" valign="top">
		<form method="post" action="/component/subcreibe!saveOrUpdate.action" name="myform">
		<ww:hidden name="domain.id" id="id" />
		<ww:hidden name="domain.userId" id="userId" /> 
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="view_content_table">
			<tr>
				<td width="50%" align="center" class="view_content_td">
				<table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
					<tr>
						<td align="left" class="inside_list">用户名<span style="color:#F00">*</span>：</td>
						<td align="left"><ww:textfield name="domain.userName" id="userName"  cssClass="required max-length-40"/></td>
					</tr> 
					<tr>
						<td align="left" width="37%" class="inside_list">邮件地址<span style="color:#F00">*</span>：</td>
						<td align="left" width="63%"><ww:textfield name="domain.userEmail" id="userEmail"  cssClass="max-length-20"/></td>
					</tr> 
					<tr>
						<td align="left" class="inside_list">订阅时间：</td>
						<td align="left"><ww:datepicker name="domain.subcreibeDate" /></td>
					</tr> 
					<tr>
						<td align="left" class="inside_list">最后订阅时间：</td>
						<td align="left"><ww:datepicker name="domain.lastSubcreibeDate"  /></td>
					</tr>  
				</table>
				</td>
				<td align="center" class="view_content_td">
				<table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
                    <tr>
                      <td  width="37%" align="left" class="inside_list">是否正常(用户状态)：</td>
                      <td  width="63%" align="left"><ww:select name="domain.locked" list="yesno" listKey="key" listValue="value" /></td>
                    </tr>
					<tr>
						<td align="left" class="inside_list">所属站点：</td>
						<td>
						 <ww:hidden name="domain.siteName" id="siteName"></ww:hidden>
						 <ww:action name="site" namespace="/cms" id="siteAction"/>
                          <ww:select name="domain.siteId" list="#attr.siteAction.sites" listKey="oid" listValue="sitename" required="true"  onchange="setSiteName(this)"/>
                         </td> 
					</tr>
					<tr>
						<td align="left" class="inside_list">频道名称：</td>
						<td align="left"><table border="0" width="100%" cellpadding="0"><tr><td><ww:hidden name="domain.channelId" id="channel_Id"></ww:hidden><ww:textfield name="domain.channelName" id="channel_Name"  cssClass="max-length-40" /></td><td><input type="button" onclick="selchn()" value="选择"/></td></table></td>
					</tr>					
					 <tr>
						<td align="left" class="inside_list">是否审核通过：</td>
					 <td align="left"><ww:select name="domain.approved" list="yesno" listKey="key" listValue="value"/></td>
					</tr> 
				</table>
				</td>
			</tr>
		</table>
		</form>
		</td>
	</tr>	  
	</table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
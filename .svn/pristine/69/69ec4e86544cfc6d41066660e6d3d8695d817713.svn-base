<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="网上订阅收费频道信息" />
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

 if($F("channel_Name") == ""){
 	alert("请选择频道!");
 	$("channel_Name").focus();
 	return;
 }

 setSiteName($("siteId"));
 
 myform.submit();
} 
 
function goBack(){
	window.location = "/component/channelfee!list.action";
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
		<form method="post" action="/component/channelfee!saveOrUpdate.action" name="myform">
		<ww:hidden name="domain.id" id="id" /> 
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="view_content_table">
			<tr>
				<td width="50%" align="center" class="view_content_td">
				<table width="70%" border="0" align="center" cellpadding="0" cellspacing="10"> 
					<tr>
						<td align="left" class="inside_list" width="37%" >所属站点：</td>
						<td width="63%" >
						 <ww:hidden name="domain.siteName" id="siteName"></ww:hidden>
						 <ww:action name="site" namespace="/cms" id="siteAction"/>
                          <ww:select name="domain.siteId" list="#attr.siteAction.sites" id="siteId" listKey="oid" listValue="sitename" required="true"  onchange="setSiteName(this)"/>
                         </td> 
					</tr>
					<tr>
						<td align="left" class="inside_list">频道名称：</td>
						<td align="left"><table border="0" width="100%" cellpadding="0"><tr><td><ww:hidden name="domain.channelId" id="channel_Id"></ww:hidden><ww:textfield name="domain.channelName" id="channel_Name"/></td><td><input type="button" onclick="selchn()" value="选择"/></td></table></td>
					</tr>					
					 <tr>
						<td align="left" class="inside_list">是否收费：</td>
					 <td align="left"><ww:select name="domain.isfee" list="yesno" listKey="key" listValue="value"/></td>
					</tr> 
				</table> 
				</td>
				<td align="center" class="view_content_td" valign="top">
				<table width="70%" border="0" align="center" cellpadding="0" cellspacing="10"> 
					<tr>
						<td align="left" class="inside_list">创建时间：</td>
						<td align="left"><ww:datepicker name="domain.createdTime" /></td>
					</tr> 
				 	<tr>
						<td align="left" class="inside_list">费用：</td>
						<td align="left"><ww:textfield name="domain.fee" /></td>
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

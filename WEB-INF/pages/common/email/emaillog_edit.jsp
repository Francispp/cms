<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="邮件日志管理" />
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
<script type="text/javascript"> 
 function save(){
if(valid.validate())
 myform.submit();
}
function goBack(){
	window.location = "/base/emaillog!list.action";
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
		<form method="post" action="/base/emaillog!saveOrUpdate.action" name="myform">
		<ww:hidden name="domain.id" id="id" />
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="view_content_table">
			<tr>
				<td width="50%" align="center" class="view_content_td">
				<table width="70%" border="0" align="center" cellpadding="0" cellspacing="10"> 
					<tr>
						<td align="left" width="37%" class="inside_list">发送人<span style="color:#F00">*</span>：</td>
						<td align="left" width="63%"><ww:textfield name="domain.setFrom"cssClass="required"/></td>
					</tr> 
					<tr>
						<td align="left" class="inside_list">接收人：</td>
						<td align="left"><ww:textfield name="domain.to" cssClass="required max-length-40"/></td>
					</tr> 
					<tr>
						<td align="left" class="inside_list">抄送人：</td>
						<td align="left">  <ww:textfield name="domain.cc" id="cc"  cssClass="max-length-40"/></td>
					</tr> 
					<tr>
						<td align="left" class="inside_list">附件：</td>
						<td align="left"><ww:textfield name="domain.attach"  cssClass="max-length-40"/></td>
					</tr> 　
				</table>
				</td>
				<td align="center" class="view_content_td">
				<table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
					<tr>
						<td align="left" class="inside_list">邮件主题：</td>
					 <td  width="63%" align="left"><ww:textfield name="domain.emailSubject" cssClass="required max-length-40"/></td>
					</tr>
                    <tr>
                      <td  width="37%" align="left" class="inside_list">发送时间</td>
                      <td  width="63%" align="left"><ww:textfield name="domain.createtime" /></td>
                    </tr>
					<tr>
						<td align="left" class="inside_list">再次发送时间：</td>
					 <td  width="63%" align="left"><ww:textfield name="domain.reSendtime"/></td>
					</tr>
					<tr>
						<td align="left" class="inside_list">状态：</td>
					 <td  width="63%" align="left"><ww:select name="domain.status" cssClass="required max-length-20" list="yesno" listKey="key" listValue="value" /></td>
					</tr>
				</table>
				</td>
			</tr> 
			<tr>
				<td align="left" class="inside_list" colspan="2">
					<table width="85%" border="0" align="center" cellpadding="0" cellspacing="10"> 
					 <tr>
						<td align="left" class="inside_list" width="14%">邮件内容:</td>
						<td align="left">
							 <ww:textarea cols="80" rows="10" name="domain.content" cssClass="max-length-120"></ww:textarea>
						</td>
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
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});

<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>
</script>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="定时任务" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<%@ include file="/common/validation.inc"%>
<script type="text/javascript">
function save(){
if(valid.validate())
 myform.submit();
}
function goBack(){
 location.href='scheduler.action';
}
</script>
<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="background-color:#dfedef;">
	<tr>
		<td bgcolor="#ffffff" height="6"></td>
	</tr>
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

	<!-- 操作功能按钮条 -->
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
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
					<li id="button"><a href="javascript:save()">保存</a></li>
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
	<tr>
		<td align="center" valign="top">

		<div id="view_scroll_content" align=justify style="height:auto;">
		<form method="post" action="scheduler!saveOrUpdate.action"
			name="myform" enctype="multipart/form-data">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="view_content_table">
			<tr>
				<td width="50%" align="center" class="view_content_td">
				<table width="80%" border="0" align="center" cellpadding="0"
					cellspacing="10">
					<tr>
						<td width="23%" align="right" class="inside_list">工作名称：</td>
						<td width="77%" align="left"><ww:textfield
							name="domain.jobName" size="40" cssClass="required" /></td>
					</tr>
					<tr>
						<td align="right" class="inside_list">工作类：</td>
						<td align="left"><ww:textfield name="domain.jobClass"
							size="40" cssClass="required" /></td>
					</tr>
					<tr>
						<td align="right" class="inside_list">工作组：</td>
						<td align="left"><ww:textfield name="domain.jobGroup"
							size="40" cssClass="required" /></td>
					</tr>
					<tr>
						<td align="right" class="inside_list">触发名称：</td>
						<td align="left"><ww:textfield name="domain.trigger"
							size="40" cssClass="required" /></td>
					</tr>
					<tr>
						<td align="right" class="inside_list">执行表达式：</td>
						<td align="left"><ww:textfield name="domain.expression"
							size="40" cssClass="required" /></td>
					</tr>


				</table>
				</td>
			</tr>

		</table>
		</form>
		</div>

		</td>
	</tr>
</table>
<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
</script>

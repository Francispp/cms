<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="表达式创建" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/common/cms_js/util.js" type="text/javascript"></script>
<script type="text/javascript">
function save(){
if(valid.validate())
 myform.submit();
}
function goBack(){
 location.href='regular!list.action?pageStyle=<ww:property value="pageStyle" />&objectId=<ww:property value="objectId" />';
}
</script>
<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0" style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
<!--style="background-color:#dfedef;"  -->	
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

	<!-- 操作栏 -->
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
	
	<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#dfedef">
	
	<tr>
		<td align="center" valign="top">
		
		<form method="post" action="${ctx}/crawl/regular!saveOrUpdate.action" name="myform">
		<ww:hidden  name="domain.oid" id="domain.oid"/>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="view_content_table">
			<tr>
				<td width="50%" align="center" class="view_content_td">
				<table width="90%" border="0" align="center" cellpadding="0"
					cellspacing="10">
					<tr>
						<td width="23%" align="right" class="inside_list">名称 :&nbsp;&nbsp;</td>
						<td width="77%" align="left">
						<ww:textfield name="domain.title" cssClass="required" cssStyle="width: 440px"/>
						</td>
					</tr>
					<tr>
						<td width="21%" align="right" valign="top" class="inside_list"> 表达式:</td>
						<td width="79%" align="left">
						 <ww:textarea name="domain.content" cssStyle="width: 440px" rows="8" cssClass="required"/>
							</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</form></td></tr></table>
		</td>
	</tr>
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


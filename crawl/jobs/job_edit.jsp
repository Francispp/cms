<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="任务创建" />

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
 location.href='jobs!list.action?pageStyle=<ww:property value="pageStyle" />&objectId=<ww:property value="objectId" />';
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
		
		<form method="post" action="${ctx}/crawl/jobs!modules.action" name="myform">
		<ww:hidden  name="domain.oid" id="domain.oid"/>
		<ww:hidden  name="domain.profile" id="domain.profile"/>
        <ww:hidden  name="domain.jobUID" id="domain.jobUID"/>
        <ww:hidden  name="domain.recovery" id="domain.recovery"/>
        <ww:hidden  name="domain.status" id="domain.status"/>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="view_content_table">
			<tr>
				<td width="50%" align="center" class="view_content_td">
				<table width="90%" border="0" align="center" cellpadding="0"
					cellspacing="10">
					<tr>
						<td width="23%" align="right" class="inside_list">任务名称 :&nbsp;&nbsp;</td>
						<td width="77%" align="left">
						<ww:textfield name="domain.metaName" cssClass="required" cssStyle="width: 440px"/>
						</td>
					</tr>
					<tr>
						<td align="right" class="inside_list">频道选择：&nbsp;&nbsp;</td>
						<td align="left"><ww:hidden name="domain.channel.id"
							id="channelId" /> <input type="text" readonly="readonly" name="domain.channel.name" value="<ww:property value="domain.channel.name"/>"
							id="channelName" /> 
							<input type="button" name="button" onclick="selectChannelForJob();"value="选择频道"></td>
							
					</tr>
					
					<tr>
						<td align="right" class="inside_list">描述:&nbsp;&nbsp;</td>
						<td align="left">
						<ww:textfield name="domain.jobDescription" cssStyle="width: 440px"/> 
					</td>
					</tr>
					<tr>
						<td width="21%" align="right" valign="top" class="inside_list"> 抓取站点URL:&nbsp;&nbsp;</td>
						<td width="79%" align="left">
						 <ww:textarea name="domain.seeds" cssStyle="width: 440px" rows="8"/>
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
<script type="text/javascript">
function selectChannelForJob(){
var retval = window.showModalDialog("${ctx}/cms/channel!channelPicker.action", null, "dialogWidth=400px;dialogHeight=400px;status=no;scroll=yes;resizable=yes");
			var rt = new Array();
			rt= retval.split("@");
			$("channelId").value = rt[0];
			$("channelName").value = rt[1];
					}
</script>

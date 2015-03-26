
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="表单信息" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script type="text/javascript">
function save(){
if(valid.validate())
 myform.submit();
}
function goBack(){
 location.href='jsfunction.action';
}
function exportFields(){
 if('${domain.oid}'!=''){
   if(confirm('原字段会被清除，您确定要重新导入当前表单的字段吗？')){
    document.getElementById("isCreateField").value='true';
    myform.submit();      
  }
 }else
  save();
}
</script>
<script type="text/javascript">
Ext.onReady(function(){ 	
 	var myempclass = new Array();
	<ww:iterator status="sts" value="typeList" id="type">
		myempclass[${sts.index}] = ["<ww:property value='#type'/>"];
 	</ww:iterator>
 	//myempclass[0]=["test"];
 	//for(var i=0;i<empclassList.length;i++){
 	//	myempclass[i] = [empclassList[i]]; 		
 	//}
	var fs=new Ext.form.ComboBox({
            store: new Ext.data.SimpleStore({
                fields: ['type'],
                data : myempclass
            }),
            displayField:'type',
            triggerAction: 'all',
            mode:'local',
            selectOnFocus:true,
            width:135
        });
	fs.applyTo('domain.type');
});
</script>
<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0" style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
						<!-- li id="button3"><a href="javascript:exportFields()">导入</a></li-->
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
<table width="100%" border="0" align="center" style="margin-top:-3px;" bgcolor="#ffffff" height="100%">
	
	<tr>
		<td align="center" valign="top">


		<form method="post" action="jsfunction!saveOrUpdate.action" name="myform">
		<ww:hidden name="domain.oid" id="domain.oid" /> <ww:hidden
			name="domain.isCreateField" id="isCreateField" />
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="view_content_table" bgcolor="#dfedef">
			<tr>
				<td width="100%" align="center" class="view_content_td">
				<table width="90%" border="0" align="center" cellpadding="0"
					cellspacing="10">
					<tr>
						<td width="23%" align="left" class="inside_list">功能名称：</td>
						<td width="77%" align="left"><ww:textfield
							name="domain.functionName" cssClass="required max-length-40"/></td>
					</tr>
					<tr>
						<td align="left" class="inside_list">类&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
						<td align="left"><ww:textfield name="domain.type" cssClass="max-length-40"/></td>
					</tr>
					<tr>
						<td align="left" class="inside_list">功能说明：</td>
						<td align="left"><ww:textfield name="domain.describe" size="78" cssClass="required  max-length-240"/></td>
					</tr>

					<c:choose>
						<c:when test="${domain.oid!=null}">
							<tr>
								<td align="left" class="inside_list">J&nbsp;S&nbsp;脚本：</td>
								<td align="left"><ww:textarea
							name="domain.code" cols="60" rows="13" cssClass="max-length-1000"/></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td align="left" class="inside_list">JS脚本：</td>
								<td align="left"><ww:textarea
							name="domain.code" cols="60" rows="13"  cssClass="max-length-1000"/></td>
							</tr>
						</c:otherwise>
					</c:choose>
					
					<tr>
						<td width="21%" align="left" valign="top" class="inside_list">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
						<td width="79%" align="left"><ww:textarea
							name="domain.remark" cols="60" rows="4"  cssClass="max-length-240"/></td>
					</tr>
					
				</table>
				</td>
			</tr>

		</table>
		
		</td></form></tr></table>
		
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

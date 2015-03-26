
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="设置共享信息" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>

<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script type="text/javascript">
function save(){
if(myform.baseChannelId.value==''){
 alert('请选择源频道！');
 return ;
}
if(myform.targetSiteId.value==''){
 alert('请选择目标频道！');
 return ;
}  

if(myform.targetChannelId.value==myform.baseChannelId.value){
 alert('请选择源频道和目标频道不能相同！');
 return ;
}
if(valid.validate())
 myform.submit();
}
function delete1(id){ 
  window.location = "/component/docShareRelation!delete.action?id="+id;
}

function goBack(){
	window.location = "/component/docShareRelation!list.action";
}

 function issueItem(status,topicid,id){  
	 location.href='/component/docShareRelation!issueItem.action?id='+topicid+'&status='+status+"&keys="+id;
}
  function setNameValue(obj,tagetName){
    document.getElementById(tagetName).value=obj.options[obj.selectedIndex].text;
}
//检测是否选择站点
function checkSelectSite(siteId,fieldName){
  if(siteId && siteId!='')
   return true;
  else{
   alert('请先选择'+fieldName+'！');
   return false;
   }
}
</script> 
<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0" style="margin:0px;padding:0px;">
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
	
	<tr><td style="padding-left:2px;padding-right:1px;">
<table width="100%" border="0" align="center" style="margin-top:-3px;" bgcolor="#dfedef" height="100%">
	<tr>
		<td align="center" valign="top">
	 <form method="post" action="docShareRelation!saveOrUpdate.action" name="myform">
		<ww:hidden name="domain.oid" id="domain.oid" /> <ww:hidden
			name="domain.userId" id="userId" />
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="view_content_table" bgcolor="#dfedef">
			<tr>
				<td width="100%" align="center" class="view_content_td">
				<table width="90%" border="0" align="center" cellpadding="0"
					cellspacing="10">
					<tr>
						<td width="23%" align="right" class="inside_list">创建人：</td>
						<td width="77%" align="left">
								<ww:textfield name="domain.userName" id="userName" readonly="true"/>
								</td>
					</tr>
					<tr>
						<td width="23%" align="right" class="inside_list">最后修改日期：</td>
						<td width="77%" align="left">
								<ww:textfield name="domain.updateTime" id="updateTime" readonly="true"/>
								</td>
					</tr>
					<tr>
						<td width="23%" align="right" class="inside_list">源站名：</td>
						<td width="77%" align="left"><ww:hidden name="domain.baseSiteName" id="baseSiteName" />
								<ww:action name="site" namespace="/cms" id="siteAction"/>
                          <ww:select name="domain.baseSiteId" id="baseSiteId" onchange="setNameValue(this,'baseSiteName')" list="#attr.siteAction.sites" listKey="oid" listValue="sitename" required="true" headerKey="" headerValue="==请选择=="/>
							</td>
					</tr>
					<tr>
						<td align="right" class="inside_list">源频道名：</td>
						<td align="left">						
						
						<ww:hidden name="domain.baseChannelId" id="baseChannelId" />
						<table border=0  id="public">
          <tr>
          <td><ww:textfield name="domain.baseChannelName" id="baseChannelName"  readonly="true"/></td>
          <td><input type='button' class='srchbt' value='选择' onClick='if(checkSelectSite(myform.baseSiteId.value,"源站点名")) selectPublicChannelEx(myform.baseChannelId,myform.baseChannelName, "${ctx}/cms/site!selectChanelTree.action?siteid="+myform.baseSiteId.value)'>
             
           </td>
           </tr></table>
						</td>
					</tr>
					<tr>
						<td align="right" class="inside_list">目标站点名：</td>
						<td align="left">
						<ww:hidden name="domain.targetSiteName" id="targetSiteName" />
						<ww:select name="domain.targetSiteId" id="targetSiteId" onchange="setNameValue(this,'targetSiteName')" list="#attr.siteAction.sites" listKey="oid" listValue="sitename" required="true" headerKey="" headerValue="==请选择=="/>
						</td>
					</tr>				
					<tr>
						<td align="right" class="inside_list">目标频道名：</td>
						<td align="left"><ww:hidden name="domain.targetChannelId" id="targetChannelId" />
						<table border=0  id="public">
          <tr>
          <td><ww:textfield name="domain.targetChannelName" id="targetChannelName"  readonly="true"/></td>
          <td><input type='button' class='srchbt' value='选择' onClick='if(checkSelectSite(myform.targetSiteId.value,"目标站点名")) selectPublicChannelEx(myform.targetChannelId,myform.targetChannelName, "${ctx}/cms/site!selectChanelTree.action?siteid="+myform.targetSiteId.value)'>
             
           </td>
           </tr></table>
						</td>
					</tr>	
					<tr>
						<td align="right" class="inside_list">是否默认共享：</td>
						<td align="left"><ww:select name="domain.isDefault" list="trueOfFalseMap1"/></td>
					</tr>	
					<tr>
						<td align="right" class="inside_list">是否同步删除：</td>
						<td align="left"><ww:select name="domain.isDelete" list="trueOfFalseMap1"/></td>
					</tr>	
					
					<tr>
						<td width="21%" align="right" valign="top" class="inside_list">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
						<td width="79%" align="left"><ww:textarea
							name="domain.remark" cols="60" rows="4" cssClass="required max-length-200"/></td>
					</tr>
					
				</table>
				</td>
			</tr>

		</table>
		
		</td></form></tr></table>
		</td>
	</tr>
</table>
</td></tr></table>
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

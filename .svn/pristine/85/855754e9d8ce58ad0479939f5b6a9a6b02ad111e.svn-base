<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
</head>
<script>
function onChangeUser(_this){
 //
 //if(_this.value!=null && _this.value!='')
  //document.getElementById('ecs_t_user').value=_this.value;
  //ECSideUtil.updateEditCell(document.getElementById('ecs_t_user'));
  //ECSideUtil.reload('myTable');
  location.href="processMonitor!assigns.action?resourceId="+_this.value;
}
//任务操作
function setAssignOpt(opt){
 
}
//接收或取消接收任务
function acceptAssign(actId,userId,accepted){
   var obj=new Array;
   obj[0]=actId;
   obj[1]=userId;
   obj[2]=accepted+'';
 ExecuteService("if(reply.getResult()=='true'){alert('更新任务状态成功!');}else {alert('更新任务状态失败!');location.reload();}",'processMonitorService','setAssignAccept',obj);
}
</script>
<body leftmargin="0" topmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<!--tr>
		<td bgcolor="#ffffff" height="1"></td>
	</tr-->
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt">流程任务管理</div>
		</div>
		</div>
		</div>
		</td>
	</tr>
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
				<ul><ww:select name="resourceId" list="packages" required="true"  emptyOption="true" onchange="onChangeUser(this); "/></ul>
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
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">

			
			<tr>
				<td align="center" valign="top">
				
	<ec:table items="items"
	 tableId="${tableId}"
	 var="item"
	 action="${ctx}/flow/processMonitor!assigns.action"
	 generateScript="true" classic="true" listWidth="100%"
	 xlsFileName="list.xls" pdfFileName="list.pdf" 
	 resizeColWidth="true" filterable="true"
	 style="table-layout:fixed;"
	 >
		<ec:row rowId="rowid_${GLOBALROWCOUNT}">
			<ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" width="8%" sortable="false" filterable="false" style="text-align:center">
				<input type="checkbox" name="_selectitem" value="${item.activityId}" onclick='checkOne(allbox);'/> 
			</ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" filterable="false" width="8%" title="序号" style="text-align:center"/>
			<ec:column property="activityId" title="活动Id" sortable="true" filterable="true" style="text-align:center"  width="30%" ellipsis="true">
			</ec:column>
			<ec:column property="activityProcessId" title="流程Id" sortable="true" filterable="true" style="text-align:center" width="30%" ellipsis="true">			
			<a href='${ctx}/flow/processMonitor!view.action?processId=${item.activityProcessId}' target="_blank">${item.activityProcessId}</a>
			</ec:column>
			<!--ec:column property="activityProcessDefName" title="流程版本名" sortable="true" filterable="true" width="15%" style="text-align:center" /-->
			<ec:column property="resourceId" title="资源名" sortable="true" filterable="true" style="text-align:center" width="10%" editTemplate="ecs_t_select_user" >
			${packages[item.resourceId]}
			</ec:column>
			<ec:column property="isaccepted" title="接收" sortable="true" filterable="true" width="5%"  editTemplate="ecs_t_select_accept" style="text-align:center">
				<c:choose>
					<c:when test="${item.isaccepted==1}"><input type="checkbox" name="isaccepted" onChange="acceptAssign('${item.activityId}','${item.resourceId}',this.checked)" checked value="${item.activityId}"></c:when>
					<c:otherwise><input type="checkbox" name="isaccepted" onChange="acceptAssign('${item.activityId}','${item.resourceId}',this.checked)" value="${item.activityId}"></c:otherwise>
				</c:choose>
			</ec:column>
			<ec:column property="isaccepted" title=" " sortable="false" filterable="false" width="5%"  style="text-align:center">				
			 <a href="javascript:setAssignOpt('complete');">完成</a>			
			</ec:column>				
	 	</ec:row>
	</ec:table>
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols="" style="display:none">
  <input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
 </textarea> 
 <textarea id="ecs_t_select_user" rows="" cols="" style="display:none">
 <select id="ecs_t_user" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">
  <option ></option>
 <ww:iterator value="packages">  	
      <option value='<ww:property value="key"/>'><ww:property value="value"/></option>      
 </ww:iterator>
 </select>
  </textarea>
 <textarea id="ecs_t_select_accept" rows="" cols="" style="display:none"> 
 	<select id="ecs_t_accept" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">
		<option ></option>
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
 </textarea>		

</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

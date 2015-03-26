<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
<script language="javascript">
function addPPMap(isgroup){
 if(parent.document.myform.packageId.value==''){
  alert('请选择包ID!');
 return ;
}
if(parent.document.myform.participantId.value==''){
 alert('请选择参入者!');
 return ;
}
document.myform.userId.value='';
//var strurl='${ctx}/common/SelectPerson.action?width=400&height=300&input=userName&style=1&inputid=userId';
if(isgroup==1){
  //document.myform.userId.value='1';
  //strurl='${ctx}/common/SelectRole.action?width=400&height=300&input=userName&style=1&inputid=userId';
 selectRoleEx(document.myform.userId,document.myform.userName);
 }else
  selectUserEx(document.myform.userId,document.myform.userName);
if(document.myform.userId.value==''){
alert('请选择人员名称!');
return ;
}
var obj=new Array;
obj[0]=parent.document.myform.packageId.value;
obj[1]=parent.document.myform.participantId.value;
if(isgroup==1){
 obj[2]='true';
 obj[3]='R_'+document.myform.userId.value;
 //obj[3]=document.myform.userId.value;
}else{
 obj[2]='false';
 //obj[3]='U_'+document.myform.userId.value;
 obj[3]=document.myform.userId.value;
 }

//pageBuffalo = new Buffalo(endPoint,false);
ExecuteService("if(reply.getResult()=='true'){alert('映射操作成功!');window.location.reload();}else alert('映射操作失败!');",'flowManagerService','addParticipantMapping',obj);
}
function deleteId(){	
   var billIDs=getSelectedID();
   if(billIDs==""){
	   alert("请选择删除项!");
	   return ;
	}
	//alert(document.selectForm.checkDeptID);
	//alert(billIDs); 
var obj2=new Array;
obj2[0]=billIDs;
ExecuteService("if(reply.getResult()=='true'){alert('删除人员映射成功!');window.location.reload();}else alert('删除操作失败!');",'flowManagerService','deleteParticipantMap',obj2);	
}
</script>
<body leftmargin="0" topmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<!--tr>
				<td height="1"></td>
			</tr-->
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
 <form id="myform" name="myform" onsubmit="return true;" action="" method="POST">
 <input type="hidden" name="userId" value="">
 <input type="hidden" name="userName" value="">
 <ul>
  <li id="button1"><a href="javascript:addPPMap(1);"><ww:property value="getText('RESOURCE.COMMON.ADD')"/>角色</a></li>
  <li id="button1"><a href="javascript:addPPMap(0);"><ww:property value="getText('RESOURCE.COMMON.ADD')"/>人员</a></li>
  <li id="button"><a href="javascript:deleteId();"><ww:property value="getText('RESOURCE.COMMON.DEL')"/></a></li>  
  </ul>
 </form>
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
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			
			<tr>
				<td align="center" valign="top">
	<ec:table items="items"
	 tableId="${tableId}"
	 var="item"
	 action="${ctx}/flow/participantManager!list.action"
	 generateScript="true" classic="true" listWidth="100%"
	 xlsFileName="list.xls" pdfFileName="list.pdf" 
	 resizeColWidth="true" filterable="true"
	 >
		<ec:row recordKey="${item.packageId}" rowId="rowid_${GLOBALROWCOUNT}">
			<ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" width="8%" sortable="false" filterable="false" style="text-align:center">
				<input type="checkbox" name="_selectitem" value="${item.packageId};${item.processDefinitionId};${item.participantId};${item.username};${item.isGroupUser}" onclick='checkOne(allbox);'/> 
			</ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" filterable="false" width="8%" title="序号" style="text-align:center"/>
			<ec:column property="packgeName" title="包ID" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="processDefinitionId" title="流程定义ID" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="participantId" title="参与者ID" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="isGroupUser" title="是否角色" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="usercname" title="名称" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="username" title="映射ID" sortable="true" filterable="true" style="text-align:center"/>			
	 	</ec:row>
	</ec:table>
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols="" style="display:none">
  <input type="text" class="inputtext" value=""
  onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"name="" />
 </textarea>		
</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="title" value="Sample flow 列表" />
<script type="text/javascript">
var parameterUrl='&flowstate=<ww:property value="flowstate" />';
 function addItem(){
  location.href='flow!add.action?id='+parameterUrl;
 }
 function editItem(oid){//undefine
 if(oid)
  location.href='flow!edit.action?id='+oid+parameterUrl;
 else
  location.href='flow!edit.action?id='+parameterUrl;
 }
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 /*confirmMessage('您确定要删除选择的记录吗？','提示',function(btn){
  if(btn=='yes'){	
     location.href='flow!delete.action?keys='+ids+parameterUrl;
 }
 }); */   
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='flow!delete.action?keys='+ids+parameterUrl;
 }
 }
 </script>
<title>${title}</title>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<ww:head/>
</head>

<body>
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<tr>
		<td bgcolor="#ffffff" height="6"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}"/> ${flowstateString}</div>
		</div>
		</div>
		</div>
		</td>
	</tr>   
 <c:if test="${flowstate<3}">
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
				<ww:if test='#session.loginer.haveThePermission("SYS_DEPT_MANAGE_ADD")'>  
  <li id="button"><a href="javascript:editItem();">新增</a></li>
</ww:if>
 <ww:if test='#session.loginer.haveThePermission("SYS_DEPT_MANAGE_DEL")'>
  <li id="button"><a href="javascript:deleteItem();">删除</a></li>
 </ww:if> 		
 </div></ul>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr> 
</c:if>
<!-- 页面主要内容 -->
<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">
<div id="list_scroll_content" align=justify>
	       <ec:table items="items" var="domain"
	        listWidth="100%"
	        filterable="true"
	        sortable="true"
	        action="${ctx}/sample/flow.action">
		<ec:exportXls fileName="FlowList.xls" tooltip="导出 Excel"/>
		<ec:row>
		        <ec:column property="oid" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="false" width="8%" style="text-align:center">
            	         <input type="checkbox" name="_selectitem" value="${domain.oid};${domain.flowinfo.activityid}" onclick='checkOne(allbox);'/>
                        </ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" title="序号" filterable="false" width="60" style="text-align:center"/>
			
			<ec:column property="name" title="名称" style="text-align:center">
			<a href="flow!edit.action?id=${domain.oid}&activityid=${domain.flowinfo.activityid}&flowstate=${flowstate}">${domain.name}</a>
			</ec:column>
			<ec:column property="test" title="测试字段"/>
			<ec:column property="test2" title="测试字段2"/>
			<ec:column property="test3" title="测试字段3"/>
			<ec:column property="remark" title="备注"/>
			<c:if test="${flowstate<3}">
			<ec:column property="flowinfo.activityname" title="活动名称" style="text-align:center"/>
			</c:if>
			<c:if test="${flowstate<3}">			
			<ec:column property="null" title="修改" sortable="false" filterable="false" width="40" viewsAllowed="html">
				<a href="flow!edit.action?id=${domain.oid}&activityid=${domain.flowinfo.activityid}&flowstate=${flowstate}">修改</a>
			</ec:column>
			</c:if>	
		</ec:row>
	</ec:table>
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols="" style="display:none">
 <input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
	name="" />
 </textarea>	
</div>
</td></tr></table>
</body>

</html>

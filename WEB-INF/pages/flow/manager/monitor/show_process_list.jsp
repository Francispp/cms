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
<body leftmargin="0" topmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面主要内容 -->
<div id="view_scroll_content" align=justify>
	<ec:table items="items"
	 tableId="${tableId}"
	 var="item"
	 action="${ctx}/flow/processMonitor!list.action"
	 generateScript="true" classic="true" listWidth="100%"
	 xlsFileName="list.xls" pdfFileName="list.pdf" 
	 resizeColWidth="true" filterable="true"
	 style="table-layout:fixed;"
	 >
		<ec:row recordKey="${item.processId}" rowId="rowid_${GLOBALROWCOUNT}">
			<ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" width="8%" sortable="false" filterable="false" style="text-align:center">
				<input type="checkbox" name="_selectitem" value="${item.processId}" onclick='checkOne(allbox);'/> 
			</ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" filterable="false" width="8%" title="序号" style="text-align:center"/>
			<ec:column property="processId" title="流程id" sortable="true" filterable="true" style="text-align:center" ellipsis="true">
			<a href='${ctx}/workflow/webflow/index.jsp?flowdefmgrname=${item.processDefmgrName}.xpdl' target="_blank">${item.processId}</a>
			</ec:column>
			<ec:column property="processName" title="流程名" sortable="true" filterable="true" style="text-align:center" ellipsis="true">
			 <a href='${ctx}/flow/processMonitor!view.action?processId=${item.processId}' target="_blank">${item.processName}</a>
			</ec:column>
			<ec:column property="state" title="状态"  editTemplate="ecs_t_select_state" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="processDefmgrName" title="流程版本名" sortable="true" filterable="true" style="text-align:center" ellipsis="true">
			<a href='${ctx}/workflow/webflow/index.jsp?flowdefmgrname=${item.processDefmgrName}.xpdl' target="_blank">${item.processDefmgrName}</a>
			</ec:column>
	 	</ec:row>
	</ec:table>
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols="" style="display:none">
  <input type="text" class="inputtext" value=""
  onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"name="" />
 </textarea>
 <textarea id="ecs_t_select_state" rows="" cols="" style="display:none">
  <select id="select_state" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">
  <c:forEach var="state" items="${states}" varStatus="status">
  <option value="${state.key}">${state.value}</option>
  </c:forEach>			 
  </select>
 </textarea> 		
</div> 
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

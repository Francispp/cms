<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
</head>
<%@ include file="/common/ec/ec.inc"%>
<script>
function editItem(id,actid){
 var assignUrl="${ctx}/flow/assign!edit.action?id=";
 if(id!=null)
   assignUrl+=id;
 if(actid && actid!=null)
   assignUrl+="&activityid="+actid;
 location.href=assignUrl;  
}
</script>
<body leftmargin="0" topmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt">我的任务  ${flowstateString}</div>
		</div>
		</div>
		</div>
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
	 action="${ctx}/flow/assign.action"
	 generateScript="true" classic="true" listWidth="100%"
	 xlsFileName="list.xls" pdfFileName="list.pdf" 
	 resizeColWidth="true" filterable="true"  useAjax="false"
	 retrieveRowsCallback="limit" sortRowsCallback="limit"
	 >
		<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}" ondblclick="editItem('${item.oid}','${item.activityId}')">

			<ec:column property="rowcount" cell="rowCount" sortable="false" filterable="false" width="8%" title="序号" style="text-align:center"/>
			<ec:column property="bizname" title="任务名" sortable="true" filterable="true" width="20%" style="text-align:center"/>

			<ec:column property="activityId" title="活动ID" width="10%" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="activityProcessId" title="流程ID" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="createtime" title="申请时间" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="createname" title="申请人" sortable="true" filterable="true" style="text-align:center"/>
	 	</ec:row>
	</ec:table>
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols="" style="display:none">
  <input type="text" class="inputtext" value=""
  onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"name="" />
 </textarea>		
</td></tr></table>
</td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

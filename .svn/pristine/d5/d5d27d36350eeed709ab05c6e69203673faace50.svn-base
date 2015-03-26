<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="定时任务" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">			

 var parameterUrl="";
 //编辑
 function editItem(oid){
  //location.href='scheduler!edit.action?id='+oid+parameterUrl;
 }
 function addItem(){
  location.href='scheduler!add.action';
 }


 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='scheduler!delete.action?&keys='+ids;
 }
 }
 function pauseall(){
  location.href="scheduler.action?method=pauseall";
}

function resumeall(){
  location.href="scheduler.action?method=resumeall";
}
 function pause(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择任务！');
    return ;
    }
   if(confirm('您确定要暂停选择的任务吗？')){  
  location.href='scheduler!pause.action?keys='+ids;
  }
}
function resume(){
var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择任务！');
    return ;
    }
    if(confirm('您确定要启动选择的任务吗？')){  
  location.href='scheduler!resume.action?keys='+ids;
  }
}
</script>

</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
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
		<div id="titel1_txt"><c:out value="${title}" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>

	<!-- 操作功能按钮条 -->
	
	<tr>
		<td valign="top">
		<div id="operationDivNoBorder"><!-- 操作功能按钮条 -->
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
					<li id="button"><a href="javascript:pause()">暂停</a></li>
					<li id="button"><a href="javascript:resume()">启动</a></li>
					<li id="button"><a href="javascript:addItem();">新增</a></li>
					<li id="button"><a href="javascript:deleteItem();">删除</a></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table></div>
		</td>
	</tr>


<!-- 页面主要内容 -->
<!--<div id="list_scroll_content" align=justify>-->

<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">

<ec:table
	items="lsJobs" var="item" action="${ctx}/base/scheduler.action"
	updateAction="${ctx}/base/scheduler.action"
	deleteAction="${ctx}/base/scheduler.action"
	insertAction="${ctx}/base/scheduler.action" editable="false"
	batchUpdate="false" xlsFileName="站点信息.xls" pdfFileName="站点信息.pdf"
	csvFileName="站点信息.csv" minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.jobName}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="id"
			title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
			viewsDenied="xls" sortable="false" filterable="false" width="3%"
			style="text-align:left">
			<input type="checkbox" name="_selectitem"
				value="${item.jobName}@${item.jobGroup}"
				onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column property="_1" title="序号" width="3%" sortable="false"
			editable="false" filterable="false" value="${GLOBALROWCOUNT}"
			style="text-align:center" />
		<ec:column property="jobName"
			title="工作名称" width="30%" sortable="true" filterable="true"
			editTemplate="ecs_t_input" />
		<ec:column property="jobClass" title="工作类" width="25%" sortable="true"
			filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="jobGroup" title="工作组" width="10%" sortable="true"
			filterable="true" editTemplate="ecs_t_input" />

		<ec:column property="triggerName" title="触发名称" width="15%"
			filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="triggerState" title="状态" width="*%"
			filterable="true" editTemplate="ecs_t_input" />
	</ec:row>
</ec:table> 



<!-- 编辑和过滤所使用的 通用的文本框模板 --> 
<textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea>

</td></tr></table></td></tr>
</table>			
			<!--</div>

-->
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

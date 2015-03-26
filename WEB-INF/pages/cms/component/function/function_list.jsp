<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="组件管理列表" />
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
  location.href='jsfunction!edit.action?id='+oid+parameterUrl;
 }
 function deleteItem(){
  var ids=getSelectedID();
  if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='jsfunction!delete.action?keys='+ids;
 }
 }
 
	 //从excel文件中导入信息
	 function inputData(){
	 var title="选择导入的文件";
	 actionURL="${ctx}/component/jsfunction!toimportData.action?id=${channelId}&siteId=${siteId}";
	 //uploadXML(title,actionURL);

	wy = '80px';
  	wx = '500px';
  	//var rtn = showframe('导入Excel', "/component/jsfunction!toimportData.action?actionURL="+actionURL);
  	var returnvalue = window.showModalDialog(actionURL,'','font-size:9pt;dialogWidth:' + wx + ';dialogHeight:' + wy + ';status:no;scroll=no;');
	var mess="导入失败:同一类型下JS功能名称不能重复！ ("+returnvalue.length+")\n";
	for(var i in returnvalue)
	  mess += "  " + (returnvalue[i]);
	if(returnvalue.length>0)
	  alert(mess);
	else
	  //window.document.location.reload();
	  window.document.location = "${ctx}/component/jsfunction.action";
	}

	 //导出模板信息到excel中
	 function exportData(){
	     var ids=getSelectedID();
		 if(ids==null||ids=='')
		 {
		    alertMessage('请先选择记录！');
		    return ;
		}
		if (confirm("您确定要导出选择的组件吗?"))
		{
		location.href="jsfunction!exportData.action?keys="+ids;
		}
	  
	 }
</script>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt">${title}</div>
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
					<li id="button"><a href="javascript:editItem('');">新增</a></li>
					<li id="button"><a href="javascript:deleteItem();">删除</a></li>
					<li id="button"><a href="javascript:inputData();">导入</a></li>
					<li id="button"><a href="javascript:exportData();">导出</a></li>
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
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">

<ec:table items="items" var="item" action="${ctx}/component/jsfunction.action"
 editable="false"
	batchUpdate="false" xlsFileName="JS组件信息.xls" pdfFileName="JS组件信息.pdf"
	csvFileName="JS组件信息.csv" minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="oid" width="3%"
			title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
			style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.oid}"
				onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column property="_1" title="序号" width="3%" sortable="false"
			editable="false" filterable="false"
			value="<a href='#' onclick='editItem(${item.oid})'>${GLOBALROWCOUNT}</a>" tipTitle="点击查看详细信息"
			style="text-align:center" />
		<ec:column property="type" title="类型" width="20%" sortable="true"
			filterable="true" editTemplate="ecs_t_input"
			value="<a href='#' onclick='editItem(${item.oid})'>${item.type}</a>" />
		<ec:column property="functionName" title="功能" width="30%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" 
			value="<a href='#' onclick='editItem(${item.oid})'>${item.functionName}</a>"/>
		<ec:column property="describe" title="说明" width="20%"
			filterable="true" editTemplate="ecs_t_input"
			value="${item.describe}">
		</ec:column>
		<ec:column property="remark" title="备注" width="*%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" value="${item.remark}"/>

	</ec:row>
</ec:table>

<!-- 编辑和过滤所使用的 通用的文本框模板 -->
<textarea id="ecs_t_input" rows="" cols="" style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea>
			
</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

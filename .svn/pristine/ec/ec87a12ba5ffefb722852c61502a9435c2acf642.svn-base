<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="常用类型数据管理列表" />
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
  location.href='/cms/selectlist!edit.action?id='+oid+parameterUrl;
 }
 function deleteItem(){
  var ids=getSelectedID();
  if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='/cms/selectlist!delete.action?keys='+ids;
 }
 }
 


</script>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff"> 
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
				<div style="float: left;">
					 <strong class="font4">${title}</strong> 
				</div>
				<div style="float: left;">
				<ul>
					<li id="button"><a href="javascript:editItem('');">新增</a></li>
					<li id="button"><a href="javascript:deleteItem();">删除</a></li>
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
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">

<ec:table items="items" var="item" action="${ctx}/cms/selectlist!list.action"
 editable="false"
	batchUpdate="false" minColWidth="80" generateScript="true"
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
		<%--<ec:column property="type" title="类别" width="20%" sortable="true"
			filterable="true" editTemplate="ecs_t_input"
			value="<a href='#' onclick='editItem(${item.oid})'>${item.type}</a>" /> --%>
		<ec:column property="title" title="标题" width="20%" sortable="true"
			filterable="true" editTemplate="ecs_t_input"
			value="<a href='#' onclick='editItem(${item.oid})'>${item.title}</a>" />
		<ec:column property="key" title="关键字" width="20%" sortable="true"
			filterable="true" editTemplate="ecs_t_input"
			value="<a href='#' onclick='editItem(${item.oid})'>${item.key}</a>" />
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

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="数据导入列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">			
	 var parameterUrl="";
	 
	 function deleteItem(id)
	 {
	 	var ids=getSelectedID();
		 if(ids==null||ids=='')
		 {
		    alertMessage('请先选择记录！');
		    return ;
		}
		
		if (confirm("您确定要删除选择的记录吗?"))
		{
			location.href='dataLog!delete.action?keys='+ids+"&" + parameterUrl;
		}
	 }
	 
	 //从xml文件中导入信息
	 function inputData(){
	 var title="选择导入的文件";
	 actionURL="${ctx}/biz/dataLog!importData.action";
	 uploadXML(title,actionURL);
	 }
</script>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->

		
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
		<div id="titel">
		<div id="titel_left">
		<div id="titel_right">
		<div id="nav">
		<div>
				<ul>
					<li id="button"><a href="javascript:deleteItem();">删除</a></li>
					<li id="button"><a href="javascript:inputData();">导入</a></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		
		<!--
		</td>
	</tr>
</table>-->

<!-- 页面主要内容 -->

	<tr>
		<td>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" valign="top">
<ec:table
	items="items" var="item" action="${ctx}/biz/dataLog.action"
	editable="false" batchUpdate="false" xlsFileName="模板信息.xls"
	pdfFileName="模板信息.pdf" csvFileName="模板信息.csv" minColWidth="80"
	generateScript="true" classic="true" listWidth="100%"
	rowsDisplayed="10" tableId="${tableId}" showPrint="true"
	resizeColWidth="true" filterable="true" filterRowsCallback="limit"
	sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="id"
			title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />"
			viewsDenied="xls" sortable="false" filterable="false" width="5%"
			style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.oid}"
				onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column property="rowcount" cell="rowCount" sortable="false"
			width="8%" style="text-align:center" title="序号" />
			<ec:column property="keyField" title="关键字段" sortable="true"
			filterable="true"
			style="text-align:center" width="30%" />
		<ec:column property="channelName" title="栏目名称" sortable="true"
			filterable="true"
			style="text-align:center" width="20%" />
		<ec:column property="tableName" title="表名" sortable="true"
			filterable="true" style="text-align:center" width="10%"/>
		<ec:column property="createTime" title="导入时间" sortable="true"
			filterable="true" width="15%" />
	</ec:row>
</ec:table>

</td></tr></table></td></tr></table>

 <!-- 编辑和过滤所使用的 通用的文本框模板 --> 
 <textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

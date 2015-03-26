<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="已提取内容" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<c:set var="icon_16_actions" value="${ctx}/images/common" />
<script type="text/javascript">			

 var parameterUrl="&pageStyle=1";

 //删除
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='extractLog!delete.action?id=${id}&keys='+ids+parameterUrl;
 }
 }
  function extractContent(){
  location.href='extractLog!extractContent.action?id=${id}'+parameterUrl;
 }
</script>
<style type="text/css">
/*选择资源图标*/
.selectRes{
	/*background-image: url('${icon_16_actions}/tools-check-spelling.png');*/
	background-repeat: no-repeat; 
	background-position: center;
	text-align:center;
	cursor:hand;
}
</style>
</head>
<body nowrap topmargin="0" leftmargin="0">
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
						<li id="button1"><a href="javascript:extractContent();">提取内容</a></li>
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
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">

<ec:table
	items="items" var="item" action="${ctx}/crawl/extractLog!list.action?id=${id}"
	updateAction="${ctx}/crawl/extractLog!saveAjax.action"
	deleteAction="${ctx}/crawl/extractLog!deleteAjax.action"
	insertAction="${ctx}/crawl/extractLog!saveAjax.action" editable="false"
	batchUpdate="false" xlsFileName="角色信息.xls" useAjax="false" 
	csvFileName="角色信息.csv" minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit">
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="_0"
			title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
			viewsDenied="xls" sortable="false" filterable="false"
			editable="false" width="4%" style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.oid}"
				onclick='checkOne(allbox);'>
		</ec:column>
		<ec:column property="_1" title="序号" width="3%" sortable="false"
			editable="false" filterable="false"
			value="<a href='#' onclick='editItem(${item.oid})'>${GLOBALROWCOUNT}</a>"
			style="text-align:center" />
		<ec:column property="keyField" title="关键字段" width="30%" sortable="true"
			filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="channelName" style="text-align:center" title="所属频道" width="10%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
			<ec:column property="job.metaName" style="text-align:center" title="所属工作任务" width="15%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
			<ec:column property="createTime" style="text-align:center" title="提取时间" width="15%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
			<ec:column property="author" style="text-align:center" title="提取作者" width="*%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
	</ec:row>
</ec:table> <!-- 编辑和过滤所使用的 通用的文本框模板 --> <textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea>
</td></tr></table></td></tr>
</table>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

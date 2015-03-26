<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="网上订阅收费频道信息列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>

<script type="text/javascript">			
 var parameterUrl="";
 //编辑
 function editItem(id){
  location.href='/component/channelfee!edit.action?id='+id;
 }
 function deleteItem(id){
  var ids=getSelectedID();
  if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
  }
  if(confirm('您确定要删除选择的记录吗？')){
    location.href='/component/channelfee!delete.action?keys='+ids;
  }
 }
 
 //编辑
 function createItem(){
  location.href='/component/channelfee!edit.action';
 }
 
   
</script>
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
					<li id="button"><a href="javascript:createItem();">新增</a></li>
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
	items="items" var="item" action="${ctx}/component/channelfee!list.action"
	updateAction="${ctx}/component/channelfee!saveAjax.action"
	deleteAction="${ctx}/component/channelfee!deleteAjax.action"
	insertAction="${ctx}/component/channelfee!saveAjax.action" editable="false"
	batchUpdate="false" xlsFileName="留言信息.xls" minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	
	<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="reportid"  width="5%"
			title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
			style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.id}"
				onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column property="_1" title="序号" width="5%" sortable="false"
			editable="false" filterable="false"
			value="<a href='#' onclick='editItem(${item.id})'>${GLOBALROWCOUNT}</a>"
			style="text-align:center" />
		<ec:column property="siteName" title="站点名称" width="25%"  	sortable="true"  value="<a href='#' onclick='editItem(${item.id})'>${item.siteName}</a>" />   
		<ec:column property="channelName" title="频道名称" width="25%" sortable="true"   /> 
		<ec:column property="createdTime" title="创建时间" width="15%" 	sortable="true" />   
		<ec:column property="fee" title="费用" width="15%" 	sortable="true" />   
		<ec:column property="approved" title="是否收费" width="*%" 	sortable="true"  value="${item.isfee == 1 ? '是':'否'}" />
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

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="留言管理列表" />
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
 function editItem(oid){
  location.href='/component/leaveword!edit.action?id='+oid+parameterUrl;
 }
 function deleteItem(oid){
  var ids=getSelectedID();
  if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
  }
  if(confirm('您确定要删除选择的记录吗？')){
    location.href='/component/leaveword!deleteLeave.action?keys='+ids;
  }
 }
 
 //编辑
 function createItem(){
  location.href="/component/leaveword!create.action";
 }
 
  function issueItem(status){
	  var ids=getSelectedID();
	  if(ids==null||ids==''){
	    alert('请先选择记录！');
	    return ;
	  }
	  
	  var clew = "您确定要发布选择的记录吗？";
	  if(status == 0) 
	  	 clew = "您确定要取消发布选择的记录吗？";
	  
	  if(confirm(clew)) 
		 location.href='/component/leaveword!issue.action?keys='+ids+'&status='+status;
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
					<li id="button"><a href="javascript:issueItem(1);">发布</a></li>
					<li id="button1"><a href="javascript:issueItem(0);">取消发布</a></li>
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
	items="items" var="item" action="${ctx}/component/leaveword!list.action"
	updateAction="${ctx}/component/leaveword!saveAjax.action"
	deleteAction="${ctx}/component/leaveword!deleteAjax.action"
	insertAction="${ctx}/component/leaveword!saveAjax.action" editable="false"
	batchUpdate="false" xlsFileName="留言信息.xls" minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="reportid"  width="5%"
			title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
			style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.oid}"
				onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column property="_1" title="序号" width="5%" sortable="false"
			editable="false" filterable="false"
			value="<a href='#' onclick='editItem(${item.oid})'>${GLOBALROWCOUNT}</a>"
			style="text-align:center" />
		<ec:column property="siteName" title="站点名称" width="15%"  	sortable="true" 
			 value="<a href='#' onclick='editItem(${item.oid})'>${item.siteName}</a>"  />
		<ec:column property="title" title="标题" width="25%"  	sortable="true" 
			 value="<a href='#' onclick='editItem(${item.oid})'>${item.title}</a>"  />
		<ec:column property="userName" title="用户名称" width="13%" 	sortable="true"  />  
		<ec:column property="link" title="联系方式" width="13%" 	sortable="true"  />  
		<ec:column property="leaveTime" title="创建时间" width="13%" 	sortable="true"  />  
		<ec:column property="status" title="是否发布" width="*%" 	sortable="true"  />
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

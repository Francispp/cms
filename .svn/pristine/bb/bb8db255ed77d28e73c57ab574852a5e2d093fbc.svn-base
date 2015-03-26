<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="栏目匹配管理" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>


</head>
<script type="text/javascript">
var parameterUrl="";
//编辑
 function editItem(oid){
  location.href='field!list.action?bizTableId='+oid+parameterUrl;
 }
	  //删除
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='table!delete.action?keys='+ids+parameterUrl;
 }
 }
</script>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt">${title}</div>
		</div>
		</div>
		</div>
		</td>
	</tr>

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
<!--div id="list_scroll_content" align=justify-->

<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">

<ec:table
	items="items" var="item" action="${ctx}/biz/table.action"
	updateAction="${ctx}/biz/table!saveAjax.action"
	deleteAction="${ctx}/biz/table!deleteAjax.action"
	insertAction="${ctx}/biz/table!saveAjax.action" editable="true"
	batchUpdate="false" xlsFileName="info.xls" pdfFileName="info.pdf"
	csvFileName="info.csv" minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
	<ec:column property="_0"
			title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />"
			viewsDenied="xls" sortable="false" filterable="false"
			editable="false" width="4%" style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.oid}"
				onclick='checkOne(allbox);'>
		</ec:column>
		<ec:column property="_1" title="序号" width="3%" sortable="false"
			editable="false" filterable="false"
			value="<a href='#' onclick='editItem(${item.oid})'>${GLOBALROWCOUNT}</a>"
			style="text-align:center" />
			<ec:column property="tableName" title="表名称" width="40%" sortable="true"
			filterable="true" editTemplate="ecs_t_input" value="<a href='#' onclick='editItem(${item.oid})'>${item.tableName}</a>"/>
			
			<ec:column property="channel.id" title="对应栏目" width="40%" sortable="true" editable="true"
			filterable="true" value="${item.channel.name}" editTemplate="ecs_t_select_field" />
			
			
	</ec:row>
</ec:table> 


<!-- 编辑和过滤所使用的 通用的文本框模板 --> 

<textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea> 
                       <textarea id="ecs_t_select_field" rows="" cols=""
	style="display:none">
			<select id="select_field" name="select_field"
	onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">			  		 
			  	<option value=''></option>
			  	<c:forEach var="channel" items="${channels}" varStatus="status">
			  	<option value='${channel.id}'>${channel.name}</option>
 	                        </c:forEach>
			 </select>
                       </textarea>
                      

<!-- 新建记录所用模板 --> 

<textarea id="add_template"
	rows="" cols="" style="display:none">
&#160;
<tpsp />
<tpsp />
<input type="text" name="tableName" class="inputtext" value="" />
<tpsp />

<select id="channel.id" name="channel.id" style="width: 100%">		  		 
			  	<c:forEach var="channel" items="${channels}" varStatus="status">
			  	<option value='${channel.id}'>${channel.name}</option>
 	                        </c:forEach>
			 </select>
<tpsp />

&#160;
</textarea>

</td></tr></table></td></tr>
</table>
<!--/div-->

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

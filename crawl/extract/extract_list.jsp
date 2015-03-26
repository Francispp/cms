<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="抓取条件管理" />
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
  location.href='extract!edit.action?id='+oid+parameterUrl;
 }
  function keyFieldItem (id)
	 {
	       if (confirm("您确定要设置成关键字段吗?"))
	 	location.href="extract!setKeyField.action?jobId=${jobId}&id=" + id;
	 }
	 function cancelKeyFieldItem (id)
	 {
	       if (confirm("您确定要取消关键字段吗?"))
	 	location.href="extract!setKeyField.action?jobId=${jobId}&id=";
	 }
	  //删除
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='extract!delete.action?jobId=${jobId}&keys='+ids+parameterUrl;
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
	items="items" var="item" action="${ctx}/crawl/extract.action?jobId=${jobId}"
	updateAction="${ctx}/crawl/extract!saveAjax.action?jobId=${jobId}"
	deleteAction="${ctx}/crawl/extract!deleteAjax.action?jobId=${jobId}"
	insertAction="${ctx}/crawl/extract!saveAjax.action?jobId=${jobId}" editable="true"
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
			<ec:column property="job.oid" title="抓取任务" width="10%" sortable="true" editable="true"
			filterable="true" value="${item.job.metaName}" editTemplate="ecs_t_select_type" />
			<ec:column property="formField.oid" title="存储字段" width="10%" sortable="true" editable="true"
			filterable="true" value="${item.formField.fieldName}" editTemplate="ecs_t_select_field" />
			<ec:column property="fieldType" title="类型" width="10%" sortable="true" editable="true"
			filterable="true" value="${item.fieldType}" editTemplate="ecs_t_select_fieldType" />
		<ec:column property="tagName" title="标识" width="8%" sortable="true"
			filterable="true" editTemplate="ecs_t_input" />
			<ec:column property="attributeName" title="属性名" width="10%" sortable="true"
			filterable="true" editTemplate="ecs_t_input" />
			<ec:column property="attributeValue" title="属性值" width="10%" sortable="true"
			filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="regular.oid" value="${item.regular.title}" title="表达式" width="15%" sortable="true"
			filterable="true" editTemplate="ecs_t_select_regular"></ec:column>

			<ec:column property="keyFiledExtract" title="关键字段" sortable="false"
				filterable="false" style="text-align:center" width="8%">
				<c:choose>
					<c:when test="${item.oid == keyFiledExtractId}">
						<img alt='取消关键字段'
							onclick='cancelKeyFieldItem (${item.oid})'
							style='cursor: pointer'
							src='${ctx}/images/common/dialog-apply.png' />
					</c:when>
					<c:otherwise>
						<img alt='设置关键字段'
							onclick='keyFieldItem(${item.oid})'
							style='cursor: pointer'
							src='${ctx}/images/common/dialog-cancel.png' />
					</c:otherwise>
				</c:choose>
			</ec:column>
	</ec:row>
</ec:table> 


<!-- 编辑和过滤所使用的 通用的文本框模板 --> 

<textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea> <textarea id="ecs_t_select_type" rows="" cols=""
	style="display:none">
			<select id="select_type" name="select_type"
	onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">			  		 
			  	<option value=''></option>
			  	<c:forEach var="job" items="${jobs}" varStatus="status">
			  	<option value='${job.oid}'>${job.metaName}</option>
 	                        </c:forEach>
			 </select>
                       </textarea>
                       <textarea id="ecs_t_select_field" rows="" cols=""
	style="display:none">
			<select id="select_field" name="select_field"
	onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">			  		 
			  	<option value=''></option>
			  	<c:forEach var="formField" items="${formFields}" varStatus="status">
			  	<option value='${formField.oid}'>${formField.fieldName}</option>
 	                        </c:forEach>
			 </select>
                       </textarea>
                        <textarea id="ecs_t_select_regular" rows="" cols=""
	style="display:none">
			<select id="select_regular" name="select_regular"
	onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">			  		 
			  	<option value=''></option>
			  	<c:forEach var="regular" items="${regulars}" varStatus="status">
			  	<option value='${regular.oid}'>${regular.title}</option>
 	                        </c:forEach>
			 </select>
                       </textarea>
                       <textarea id="ecs_t_select_fieldType" rows="" cols=""
	style="display:none">
			<select id="select_fieldType" name="select_fieldType"
	onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">			  		 
			  	<option value=''></option>
			  	
			  	<option value='String'>String</option>
			  	<option value='IMG'>IMG</option>
			 </select>
			 
                       </textarea>
                        

<!-- 新建记录所用模板 --> 

<textarea id="add_template"
	rows="" cols="" style="display:none">
&#160;
<tpsp />
<tpsp />
<select id="job.oid" name="job.oid" style="width: 100%">		  		 
			  	
			  	<c:forEach var="job" items="${jobs}" varStatus="status">
			  	<option value='${job.oid}'>${job.metaName}</option>
 	                        </c:forEach>
			 </select>
			 
			 <tpsp />
<select id="formField.oid" name="formField.oid" style="width: 100%">		  		 
			  	
			  	<c:forEach var="formField" items="${formFields}" varStatus="status">
			  	<option value='${formField.oid}'>${formField.fieldName}</option>
 	                        </c:forEach>
			 </select>
			  <tpsp />
<select id="fieldType" name="fieldType" style="width: 100%">		  		 
			   <option value='String'>String</option>
			  	<option value='IMG'>IMG</option>
			 </select>
			 
			
<!--input type="text" name="type" class="inputtext" value="" /-->
<tpsp />
<input type="text" name="tagName" class="inputtext" value="" />
<tpsp />
<input type="text" name="attributeName" class="inputtext" value="" />
<tpsp />
<input type="text" name="attributeValue" class="inputtext" value="" />
<tpsp />
<select id="regular.oid" name="regular.oid" style="width: 100%">		  		 
			  	
			  	<c:forEach var="regular" items="${regulars}" varStatus="status">
			  	<option value='${regular.oid}'>${regular.title}</option>
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

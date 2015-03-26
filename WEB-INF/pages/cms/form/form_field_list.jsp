<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="表单字段列表" />
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
  location.href='form!edit.action?id='+oid+parameterUrl;
 }
</script>
</head>
<body style="background:#fff;margin:0px;padding:0px;width:99%;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

    <div class="content" style="margin:0px;width:100%">
      <div class="info-box" style="margin:0px;width:100%;">

	<ec:table items="items"
	          var="item" 
	          action="${ctx}/form/formField.action"
		  updateAction="${ctx}/form/formField!saveAjax.action"
		  deleteAction="${ctx}/form/formField!deleteAjax.action"
		  insertAction="${ctx}/form/formField!saveAjax.action"	          
		  editable="${isEdit}" batchUpdate="false" xlsFileName="字段信息.xls"
		  pdfFileName="字段信息.pdf" csvFileName="字段信息.csv" minColWidth="60"
		  generateScript="true" classic="true" listWidth="100%"
		  rowsDisplayed="10"  useAjax="false" 
		  tableId="${tableId}" 
		  showPrint="true"
		  resizeColWidth="true" filterable="true"
	          filterRowsCallback="limit"
	          sortRowsCallback="limit"
	          retrieveRowsCallback="limit"	  			  
		  >	          
		<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="_1" title="序号" width="3%" sortable="false" editable="false"
		 filterable="false" value="${GLOBALROWCOUNT}" style="text-align:center" />
		<ec:column property="fieldName" title="字段中文名" width="20%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="fieldCode" title="字段名" width="30%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="fieldType" title="字段类型" width="20%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />		
                	 
	 </ec:row>
	</ec:table>

			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display:none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
			</textarea>
			
			<!-- 新建记录所用模板 -->
			<textarea id="add_template" rows="" cols="" style="display:none">
&#160;
<tpsp />
<input type="text" name="fieldName" class="inputtext" value="" />
<input type="hidden" name="coreForm.oid" class="inputtext" value="${formid}" />
<tpsp />
<input type="text" name="fieldCode" class="inputtext" value="" />
<tpsp />
<input type="text" name="fieldType" class="inputtext" value="" />
<tpsp />
&#160;
</textarea>                       				
<!--/div-->

</div></div>
<!-- 页脚 -->

</body>
</html>

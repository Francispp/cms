<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="模板管理列表" />
<html>
<head>
<html xmlns="http://www.w3.org/1999/xhtml" >
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type='text/javascript' src='${ctx}/dwr/interface/omtemplateManagerService.js'></script>
<script src="${ctx}/common/cybercms_js/global_ab.js"
	type="text/javascript"></script>
<script type="text/javascript">			
 	 var parameterUrl="temLibraryId=${temLibraryId}&comTemplateType=${comTemplateType}&id=${item.id}";
	 function copy(i){
		 var message= document.getElementById("bodyValue_"+i).value;
		 window.returnValue = message;
		 window.close(); 
	 }
	
	 
</script>
</head>

<body style="overflow-x:hidden;overflow-y:hidden"  topmargin="0" leftmargin="0">

<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 	<div class="system-header"> -->
<!-- 		<div class="edit-header-lion"> -->
<!-- 			<ul id="lion-ul"> -->

<!-- 				<li class="fn-clear"></li> -->
				
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 	</div> -->


	<!-- 页面主要内容 -->

		<div class="content">
  <div class="info-box">
<ec:table style="table-layout:fixed;"
	items="items" var="item" action="${ctx}/base/comtemplate!Select_list.action"
	editable="false" batchUpdate="false" xlsFileName="模板信息.xls"
	 minColWidth="20"
	generateScript="true" classic="true" listWidth="100%" listHeight="100%"
	rowsDisplayed="10" tableId="${tableId}" showPrint="true"
	resizeColWidth="true" filterable="true" filterRowsCallback="limit"
	sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
	<ww:hidden	name="temLibraryId" />
	<ww:hidden	name="id" />
	<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="id" title="选择模板" viewsDenied="xls"
			sortable="false" filterable="false" width="3%" style="text-align:center">
			<input type="button" name="_selectitem" value="复制" onclick="copy('${item.id}');" />
			<input type="hidden" name="body" value="${item.body}" id="bodyValue_${item.id}" />
		</ec:column>
		<ec:column property="rowcount" cell="rowCount" sortable="false"
			width="3%" style="text-align:center" title="序号" />
		<ec:column property="name" title="名称" sortable="true"
			filterable="true"
			value="${item.name}"
			style="text-align:center" width="5%" />
	</ec:row>
</ec:table>

</div></div>

 <!-- 编辑和过滤所使用的 通用的文本框模板 --> 
 <textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea>
 <textarea id="ecs_t_select_IsNot" rows="" cols="" style="display:none">
			 <select id="select_ispublished" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">			  
			  <option value="true">是</option>	
			  <option value="false">否</option>			 
			 </select>
                       </textarea>	
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>

<textarea id="test" name="test" style="width:500px;height:600px;display:none;" ></textarea>
</body>
</html>

<script type="text/javascript">
</script>
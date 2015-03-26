<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="菜单管理列表" />
<html>
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
  location.href='menu!edit.action?id='+oid+parameterUrl;
 }
 
 function changeViewStyle(){
  location.href="menu.action?id="+parameterUrl;   
}
</script>
</head>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt">${title}</div>
	 </div>
  </div>
</div>
<!-- 操作栏 -->
<div id="nav">
 <div>
 <ul>
  <li id="button"><a href="javascript:changeViewStyle();">树型</a></li>
  </ul>
  </div>
</div> 
<!-- 页面主要内容 -->
<div id="list_scroll_content" align=justify>
	<ec:table items="items"
	          var="item" 
	          action="${ctx}/base/menu.action"
		  updateAction="${ctx}/base/menu!saveAjax.action"
		  deleteAction="${ctx}/base/menu!deleteAjax.action"
		  insertAction="${ctx}/base/menu!saveAjax.action"	          
		  editable="true" batchUpdate="false" xlsFileName="菜单信息.xls"
		  pdfFileName="菜单信息.pdf" csvFileName="菜单信息.csv" minColWidth="80"
		  generateScript="true" classic="true" listWidth="100%"
		  rowsDisplayed="10" tableId="${tableId}" showPrint="true"
		  resizeColWidth="true" filterable="true"
		  >	          
		<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="_1" title="序号" width="3%" sortable="false" editable="false"
		 filterable="false" value="<a href='#' onclick='editItem(${item.oid})'>${GLOBALROWCOUNT}</a>" style="text-align:center" />
		<ec:column property="menuname" title="菜单名" width="15%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="menucode" title="菜单编码" width="10%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="url" title="菜单地址" width="20%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" /> 
		<ec:column property="inco" title="图标" width="10%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" /> 	 
		<ec:column property="orderno" title="排序号" width="10%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" /> 
		<ec:column property="remark" title="菜单说明" width="*%"
		 filterable="true" editTemplate="ecs_t_input" />                		 
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

<input type="text" name="menuname" class="inputtext" value="" />
<tpsp />
<input type="text" name="menucode" class="inputtext" value="" />
<tpsp />
<input type="text" name="url" class="inputtext" value="" />
<tpsp />
<input type="text" name="inco" class="inputtext" value="" />
<tpsp />
<input type="text" name="orderno" class="inputtext" value="" />
<tpsp />
<input type="text" name="remark" class="inputtext" value="" />
<tpsp />
<input type="hidden" name="portalid" value="${session.loginer.portal.portalid}"/>
<tpsp />
&#160;
</textarea>                       				
</div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

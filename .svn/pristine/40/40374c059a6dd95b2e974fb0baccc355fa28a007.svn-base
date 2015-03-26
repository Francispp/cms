<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="门户信息管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<c:set var="icon_16_actions" value="${ctx}/images/icon/Nuvola/16/actions"/>
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
	
<!-- 页面主要内容 -->

<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">
	<ec:table items="items"
	          var="item" 
	          action="${ctx}/base/portal.action"
		  updateAction="${ctx}/base/portal!saveAjax.action"
		  deleteAction="${ctx}/base/portal!deleteAjax.action"
		  insertAction="${ctx}/base/portal!saveAjax.action"	          
		  editable="true" batchUpdate="false" xlsFileName="门户信息.xls"
		  pdfFileName="门户信息.pdf" csvFileName="门户信息.csv" minColWidth="80"
		  generateScript="true" classic="true" listWidth="100%"
		  rowsDisplayed="10" 
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
		<ec:column property="code" title="编码" width="20%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="name" title="名称" width="30%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="inco" title="图标" width="15%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="openInco" title="打开图标" width="15%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		 <ec:column property="closeInco" title="关闭图标" width="15%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />		 
		<ec:column property="remark" title="门户信息说明" width="*%"
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
<input type="text" name="code" class="inputtext" value="" />
<tpsp />
<input type="text" name="name" class="inputtext" value="" />
<tpsp />
<input type="text" name="inco" class="inputtext" value="" />
<tpsp />
<input type="text" name="openInco" class="inputtext" value="" />
<tpsp />
<input type="text" name="closeInco" class="inputtext" value="" />
<tpsp />
<input type="text" name="remark" class="inputtext" value="" />
<tpsp />
&#160;
</textarea>                       				

</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

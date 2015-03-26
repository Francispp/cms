<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="权限信息列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
</head>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<!--tr>
		<td bgcolor="#ffffff" height="1"></td>
	</tr-->
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
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">

			
			<tr>
				<td align="center" valign="top">
	<ec:table items="items"
	          var="item" 
	          action="${ctx}/base/permission!listPerm.action"	          
		  editable="false" batchUpdate="false" xlsFileName="权限信息.xls"
		  pdfFileName="权限信息.pdf" csvFileName="权限信息.csv" minColWidth="80"
		  generateScript="true" classic="true" listWidth="100%"
		  rowsDisplayed="10" tableId="${tableId}" showPrint="true"
		  resizeColWidth="true" filterable="true"
		  >	          
		<ec:exportXls fileName="exportExcel.xls" tooltip="导出 Excel"/>
		<ec:row  rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="resourceid" title="序号" width="3%" sortable="false"
		 filterable="false" value="${GLOBALROWCOUNT}" style="text-align:center" />
		<ec:column property="typename" title="对象类型" width="20%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="objectname" title="名称" width="30%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="resourcename" title="资源名称" width="15%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input"/>		 
		<ec:column property="resourcekey" title="资源代码" width="*%"
		 filterable="true" editTemplate="ecs_t_input" />
	 </ec:row>
	</ec:table>

			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display:none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
			</textarea>
			                      				

</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

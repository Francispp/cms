<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="站点访问文档列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/js.inc" %>
<%@ include file="/common/ec/ec.inc" %>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/dwr/interface/userManagerService.js" type="text/javascript"></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<!--tr>
		<td bgcolor="#ffffff" height="6"></td>
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

	<!--h1>站点访问文档列表</h1-->	
	<ec:table items="items" var="item"  action="${ctx}/base/visit!list.action"	
    editable="false"
	batchUpdate="false" xlsFileName="站点访问文档列表.xls"
	 minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false"
			  >
		<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
        	        <ec:column property="_1" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="true" editable="false" width="4%" style="text-align:center">
            	         <input type="checkbox" name="_selectitem" value="${item.oid}"  onclick='checkOne(allbox);'/>
                        </ec:column>		
			<ec:column property="_2" sortable="false" filterable="false" editTemplate="ecs_t_input" editable="false" title="序号" style="text-align:center" viewsAllowed="html" tipTitle="点击查看详细信息" value="<a href='#' onclick='editItem()'>${GLOBALROWCOUNT}</a>">						 
			</ec:column>			
			<ec:column property="siteName" title="站点名称aa"  editTemplate="ecs_t_input" sortable="true" filterable="false"  style="text-align:center"/>			
			<ec:column property="channelName" title="频道名称" editTemplate="ecs_t_input" sortable="true" filterable="false"  style="text-align:center"/>
			<ec:column property="title" title="文档名称"  editTemplate="ecs_t_input" sortable="true" filterable="false"  style="text-align:center"/>
			
			
			<ec:column property="visitNumber" title="访问量(人次)" editTemplate="ecs_t_input" sortable="true" filterable="false" editable="false" style="text-align:center" />
											
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
<script language="javascript">
 dyniframesizeforall("iframe_users");
 setParentFrameSize();
</script>
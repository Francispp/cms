<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="角色管理列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<c:set var="icon_16_actions" value="${ctx}/images/common" />
<script type="text/javascript">			
  //资源选择
 function selectRes(oid){ 
  //给角色赋权
  location.href="${ctx}/base/permission!tree.action?objectid="+oid+"&type=1";
  //Ext.get('application-info-iframe').dom.src="${ctx}/base/permission!tree.action?objectid="+oid+"&type=1";
  /*var permdialog;
  if(!permdialog){
                permdialog = new Ext.BasicDialog("perm_content", { 
                        modal:true,
                        width:400,
                        height:350,
                        shadow:true,
                        proxyDrag: true
                });
                permdialog.addKeyListener(27, permdialog.hide, permdialog);
     }
     permdialog.show();*/
 }
 var parameterUrl="&pageStyle=1";
 //编辑
 function editItem(oid){
  location.href='role!edit.action?id='+oid+parameterUrl;
 }
 //新增
  function addItem(){
  location.href='role!add.action?id='+parameterUrl;
 }
 //删除
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='role!delete.action?keys='+ids+parameterUrl;
  parent.parent.frames['switchToTree'].refreshMenu();
 }
 }
</script>
<style type="text/css">
/*选择资源图标*/
.selectRes{
	background-image: url('${icon_16_actions}/tools-check-spelling.png');
	background-repeat: no-repeat; 
	background-position: center;
	text-align:center;
	cursor:hand;
}
</style>
</head>
<body nowrap topmargin="0" leftmargin="0">
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
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">

<ec:table
	items="items" var="item" action="${ctx}/base/role!list.action"
	updateAction="${ctx}/base/role!saveAjax.action"
	deleteAction="${ctx}/base/role!deleteAjax.action"
	insertAction="${ctx}/base/role!saveAjax.action" editable="false"
	batchUpdate="false" xlsFileName="角色信息.xls" useAjax="false" 
	 minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit">
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="_0"
			title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
			viewsDenied="xls" sortable="false" filterable="false"
			editable="false" width="4%" style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.oid}"
				onclick='checkOne(allbox);'>
		</ec:column>
		<ec:column property="_1" title="序号" width="3%" sortable="false"
			editable="false" filterable="false"
			value="${GLOBALROWCOUNT}"
			style="text-align:center" />
		<ec:column property="rolename" title="角色名" width="20%" sortable="true"
			filterable="true" editTemplate="ecs_t_input"
			value="${item.rolename}" />
		<ec:column property="rolecode" title="角色编码" width="30%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<!--ec:column property="coreOrg.oid" title="组织名称" width="15%"
		 sortable="true" filterable="true" editTemplate="ecs_t_select_portal" value="${item.coreOrg.orgName}"-->
		<ec:column property="remark" title="角色说明" width="*%" filterable="true"
			editTemplate="ecs_t_input" />
		
	</ec:row>
</ec:table> 
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea> <textarea id="ecs_t_select_portal" rows="" cols=""
	style="display:none">
			 <select id="select_portal" onblur="ECSideUtil.updateEditCell(this)"
	style="width: 100%">
			  <c:forEach var="portal" items="${requestScope.portals}"
		varStatus="status">
			  <option value="${portal.portalid}">${portal.cname}</option>
			 </c:forEach>			 
			 </select>
                       </textarea>
</td></tr></table></td></tr>
</table>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

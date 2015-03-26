<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="模块管理列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/ec/ec.inc"%>
<%@ include file="/common/js.inc"%>
<script type="text/javascript">
var parameterUrl='&pageStyle=<ww:property value="pageStyle" />';
 function addItem(){
  location.href='module!add.action?id='+parameterUrl;
 }
 function editItem(oid){
 location.href='module!edit.action?id='+oid+parameterUrl;
 }
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='module!delete.action?keys='+ids+parameterUrl;
 }
 }
  function changeViewStyle(){
  location.href="module.action?pageStyle=0";   
}
</script>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
  </div>
</div>
<!-- 操作功能按钮条 -->
<div id="operationDivNoBorder">
 <button id="backbutton" onclick="changeViewStyle()" class="button">树型</button>
 <ww:if test='#session.loginer.haveThePermission("SYS_RESOURCE_MANAGE_ADD")'>
 <button id="addbutton" onclick="addItem()" class="button">新增</button>
  </ww:if>
 <ww:if test='#session.loginer.haveThePermission("SYS_RESOURCE_MANAGE_DEL")'>
 <button id="delbutton" onclick="deleteItem()" class="button">删除</button>
 </ww:if>
</div>
<!-- 页面主要内容 -->
<div id="page-content-no-border">
	<ec:table items="items"
	 tableId="${tableId}"
	 var="item"
	 action="${ctx}/base/module.action"
	 listWidth="100%"
	 classic="true"
	 filterable="true"
	 filterRowsCallback="limit"
	 sortRowsCallback="limit"
	 retrieveRowsCallback="limit"
	 >
		<ec:exportXls fileName="exportExcel.xls" tooltip="导出 Excel"/>
		<ec:row>
        	        <ec:column property="oid" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="false" width="8%" style="text-align:center">
            	         <input type="checkbox" name="_selectitem" value="${item.oid}" onclick='checkOne(allbox);'/>
                        </ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" title="序号"/>			
			<ec:column property="moName"  title="模块名称">
			 <a href="#" onclick="editItem(${item.oid})">${item.moName}</a>
			</ec:column>			
			<ec:column property="moCode" sortable="true" filterable="true" title="模块代码"/>
			<ec:column property="flowMgrname" title="流程版本名称"/>
			<ec:column property="flowCname" title="流程中文名称"/>
			<ec:column property="pcode" sortable="true" filterable="true" title="所属系统"/>
			<ec:column property="remark" title="备注"/>				
		</ec:row>
	</ec:table>
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols="" style="display:none">
 <input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
	name="" />
 </textarea>	
</div>
<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>

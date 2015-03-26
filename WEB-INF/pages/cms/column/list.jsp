<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="栏目列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">
var parameterUrl='&pageStyle=<ww:property value="pageStyle" />';
 function addItem(){
  location.href='column!add.action?id='+parameterUrl;
 }
 function editItem(oid){
 location.href='column!edit.action?id='+oid+parameterUrl;
 }
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='column!delete.action?keys='+ids+parameterUrl;
 }
 }
 function changeViewStyle(){
  location.href="column!list.action?pageStyle=0";   
}
</script>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<div id="titleDiv"><c:out value="${title}"/></div>
<!-- 操作功能按钮条 -->
<div id="operationDivNoBorder">
 <button id="backbutton" onclick="changeViewStyle()" class="button">树型</button>
 <ww:if test='#session.loginer.haveThePermission("SYS_DEPT_MANAGE_ADD")'>
  <button id="addbutton" onclick="addItem()" class="button">新增</button>
 </ww:if>
 <ww:if test='#session.loginer.haveThePermission("SYS_DEPT_MANAGE_DEL")'>
  <button id="delbutton" onclick="deleteItem()" class="button">删除</button>
 </ww:if>
</div>
<!-- 页面主要内容 -->
<div id="page-content-no-border">
	<ec:table items="items"
	 var="item"
	 action="${ctx}/base/channel!list.action"
	 listWidth="100%"
	 >
		<ec:exportXls fileName="exportExcel.xls" tooltip="导出 Excel"/>
		<ec:row>
        	        <ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="false" width="8%" style="text-align:center">
            	         <input type="checkbox" name="_selectitem" value="${item.id}" onclick='checkOne(allbox);'/>
                        </ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" title="序号"/>
			<ec:column property="name" title="名称">
			 <a href="#" onclick="editItem(${item.id})">${item.name}</a>
			</ec:column>
			<ec:column property="columnCode" title="栏目编码"/>			
			<ec:column property="sortOrder" title="创建时间"/>
			<ec:column property="timeCreated" title="创建时间"/>
			<ec:column property="state" title="状态"/>
		</ec:row>
	</ec:table>
 <!-- 编辑和过滤所使用的 通用的文本框模板 -->
  <textarea id="ecs_t_input" rows="" cols="" style="display:none">
  <input type="text" class="inputtext" value=""
   onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"name="" />
  </textarea>	
</div>
<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>

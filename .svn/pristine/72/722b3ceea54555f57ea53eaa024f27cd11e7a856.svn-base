<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="信息管理列表" />
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
  location.href='document!add.action?id='+parameterUrl;
 }
 function editItem(oid){
 location.href='document!edit.action?id='+oid+parameterUrl;
 }
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='document!delete.action?keys='+ids+parameterUrl;
 }
 }
 function changeViewStyle(){
  location.href="document!list.action?pageStyle=0";   
}
</script>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td bgcolor="#ffffff" height="6"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
<div id="titleDiv"><c:out value="${title}"/></div>
</div></div></div></td></tr>

<!-- 操作功能按钮条 -->

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

<div id="operationDivNoBorder">
 <button id="backbutton" onclick="changeViewStyle()" class="button">树型</button>
 <ww:if test='#session.loginer.haveThePermission("SYS_DEPT_MANAGE_ADD")'>
  <button id="addbutton" onclick="addItem()" class="button">新增</button>
 </ww:if>
 <ww:if test='#session.loginer.haveThePermission("SYS_DEPT_MANAGE_DEL")'>
  <button id="delbutton" onclick="deleteItem()" class="button">删除</button>
 </ww:if>
</div>

</div></div></div></div></td></tr></table></td></tr>

<!-- 页面主要内容 -->

<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">

			
			<tr>
				<td align="center" valign="top">

<div id="page-content-no-border">
	<ec:table items="items"
	 var="item"
	 action="${ctx}/base/document!list.action"
	 listWidth="100%"
	 >
		<ec:exportXls fileName="exportExcel.xls" tooltip="导出 Excel"/>
		<ec:row>
        	        <ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="false" width="4%" style="text-align:center">
            	         <input type="checkbox" name="_selectitem" value="${item.id}" onclick='checkOne(allbox);'/>
                        </ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" title="序号"/>
			<ec:column property="title" title="标题">
			 <a href="#" onclick="editItem(${item.id})">${item.title}</a>
			</ec:column>
			<ec:column property="channel.name" title="所属频道"/>
			<ec:column property="issued" title="发布"/>
			<ec:column property="author.name" title="作者"/>
			<ec:column property="timeCreated" title="创建时间"/>
		</ec:row>
	</ec:table>
</div>

</td></tr></table></td></tr>

</table>

<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>

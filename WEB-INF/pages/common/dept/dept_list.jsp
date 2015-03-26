<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="title" value="部门管理列表" />
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">
var parameterUrl='&pageStyle=<ww:property value="pageStyle" />';
 function addItem(){
  location.href='dept!add.action?id='+parameterUrl;
 }
 function editItem(oid){
 location.href='dept!edit.action?id='+oid+parameterUrl;
 }
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 confirmMessage('您确定要删除选择的记录吗？','提示',function(btn){
  if(btn=='yes'){	
     location.href='dept!delete.action?keys='+ids+parameterUrl;
 }
 });    
 //if(confirm('您确定要删除选择的记录吗？')){
 // location.href='dept!delete.action?keys='+ids+parameterUrl;
 //}
 }
 function changeViewStyle(){
  location.href="dept.action?pageStyle=0";   
}
</script>
<ww:head />
</head>
<body>
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<table width="99%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff" style="padding-left:3px;">
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
	<tr>
		<td valign="top" style="padding-left:5px;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<!--tr>
				<td height="1"></td>
			</tr-->
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
					<ul>
				  <li id="button"><a href="javascript:changeViewStyle();">树型</a></li>
					<ww:if test='#session.loginer.haveThePermission("SYS_DEPT_MANAGE_ADD")'>  
					  <li id="button"><a href="javascript:editItem();">新增</a></li>
					</ww:if>
					 <ww:if test='#session.loginer.haveThePermission("SYS_DEPT_MANAGE_DEL")'>
					  <li id="button"><a href="javascript:deleteItem();">删除</a></li>
					 </ww:if>
				  </ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
<!-- 页面主要内容 -->
			
<tr><td>
<table width="99%" border="0" cellpadding="0" cellspacing="0"> 
			
			<tr>
				<td style="padding-left:0px;" valign="top">
				
				<ec:table items="items"
	 tableId="${tableId}"
	 var="item"
	 action="${ctx}/base/dept.action"
	 listWidth="100%"
	 filterRowsCallback="limit"
	 sortRowsCallback="limit"
	 retrieveRowsCallback="limit"
	 >
		<ec:exportXls fileName="exportExcel.xls" tooltip="导出 Excel"/>
		<ec:row>
        	        <ec:column property="deptid" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="false" width="8%" style="text-align:center">
            	         <input type="checkbox" name="_selectitem" value="${item.deptid}" onclick='checkOne(allbox);'/>
                        </ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" title="序号" width="8%"  style="text-align:center" />			
			<ec:column property="deptname" title="部门名称">
			 <a href="#" onclick="editItem(${item.deptid})">${item.deptname}</a>
			</ec:column>			
			<ec:column property="depttype" title="类型"/>
			<ec:column property="remark" title="备注"/>				
		</ec:row>
	</ec:table> 

</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

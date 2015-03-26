<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="用户列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>

<script src="${ctx}/dwr/interface/userManagerService.js" type="text/javascript"></script>
<script type="text/javascript">
var parameterUrl='&isEdit=false&pageStyle=<ww:property value="pageStyle" />&deptid=<ww:property value="deptid"/>';
 function addItem(){
  location.href='user!add.action?id='+parameterUrl;
 }
 function editItem(oid){
 location.href='user!edit.action?id='+oid+parameterUrl;
 }
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='user!delete.action?keys='+ids+parameterUrl;
 }
 }
  function changeViewStyle(){
  location.href="user!frame.action?id="+parameterUrl;   
}
function changeStatus(oid,status){
	var msg = "确定禁用该用户么？";
	if(status==1)
	 msg = "确定激活该用户么？";
	Ext.MessageBox.confirm('提示', msg, function(btn){
	if(btn=='yes'){
         userManagerService.setUserStatus(oid,status);
         window.location.reload();
	}
	});
}
</script>
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

	<!--h1>用户管理列表</h1-->	
	<ec:table items="items" var="item"
			  action="${ctx}/base/user!list.action"			  		  
			  editable="false"			  
			  batchUpdate="false"
			  generateScript="true"
			  filterable="true"
			  useAjax="false"
			  doPreload="false"
			  xlsFileName="用户列表.xls"
			  pdfFileName="用户列表.pdf"
			  csvFileName="用户列表.csv"
			  showPrint="true"  
			  listWidth="100%"
			  classic="true"
			  tableId="${tableId}"
			  >
		<ec:row recordKey="${item.userid}" rowId="rowid_${GLOBALROWCOUNT}">
        	        <ec:column property="_1" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="false" editable="false" width="4%" style="text-align:center">
            	         <input type="checkbox" name="_selectitem" value="${item.userid}"  onclick='checkOne(allbox);'/>
                        </ec:column>		
			<ec:column property="_2" sortable="false" filterable="false" editable="false" title="序号" style="text-align:center" viewsAllowed="html" tipTitle="点击查看详细信息" value="<a href='#' onclick='editItem(${item.userid})'>${GLOBALROWCOUNT}</a>">						 
			</ec:column>			
			<ec:column property="loginid" title="登录帐号" sortable="true" filterable="true" editable="false" style="text-align:center"/>			
			<ec:column property="ename" title="英文名" sortable="true" filterable="true" editable="false" style="text-align:center"/>
			<ec:column property="username" title="中文名" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="deptname" title="部门名称" sortable="true" filterable="true" editTemplate="ecs_t_input" style="text-align:center"/>
			<ec:column property="hotmail" title="电子邮件" sortable="true" filterable="true"  editTemplate="ecs_t_input" style="text-align:center"/>
			<ec:column property="officephone" title="IP电话" ellipsis="true" style="text-align:center" />
			<ec:column property="state" title="状态"  sortable="true" filterable="true" editable="false" editTemplate="ecs_t_status" style="text-align: center">
				<c:choose>
					<c:when test="${item.state==1}"><img  style="cursor: pointer" src="${ctx}/images/common/dialog-apply.png"/></c:when>
					<c:otherwise><img  style="cursor: pointer" src="${ctx}/images/common/dialog-cancel.png"/></c:otherwise>
				</c:choose>
			</ec:column>									
		</ec:row>
	</ec:table>
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols="" style="display:none">
 <input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
	name="" />
 </textarea>
 <textarea id="ecs_t_sex" rows="" cols="" style="display:none">
	<select id="ecs_t_sex" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">
		<option selected="selected"></option>
		<option value="男">男</option>
		<option value="女">女</option>
	</select>
</textarea>				
 <textarea id="ecs_t_status" rows="" cols="" style="display:none">
	<select id="ecs_t_status" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">
		<option selected="selected"></option>
		<option value="1">有效</option>
		<option value="0">无效</option>
	</select>
</textarea>
<tpsp />
&#160;             			

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
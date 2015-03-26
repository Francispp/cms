<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="流程版本管理" />
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script language="javascript">
function updateVersion(){
var pkgid=document.myform.packageId.value;
var xfn=document.myform.xpdlFileName.value;
//pkgid==null||pkgid==""||
if(xfn==null||xfn==""){
 alert('请选择包和流程文件名！');
 return ;
 }
 var strurl="${ctx}/flow/versionManager!updateVersion.action?xpdlName="+xfn+"&packageId="+pkgid;
 window.location=strurl;
}
function uninstallVersion(){
var pkgid=getSelectedID();
if(pkgid==null||pkgid==''){
   alert('请选择相应的流程版本！');
   return ;
   }
 var strurl="${ctx}/flow/versionManager!uninstallVersion.action?packageId="+pkgid;
 window.location=strurl;
}
</script>
<body leftmargin="0" topmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<!--tr>
		<td bgcolor="#ffffff" height="1"></td>
	</tr-->
	<form id="myform" name="myform" onsubmit="return true;" action="" method="POST">
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
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--tr>
				<td height="1"></td>
			</tr-->
			<tr>
				<td>
<input type="hidden" name="userId" value="">
<input type="hidden" name="userName" value="">
<table width="100%" border="0">
<tr>
<td align="right"><b>&nbsp;&nbsp;包ID:</b></td><td><ww:select name="packageId" list="packages" required="true" emptyOption="true"/></td>
<td align="right"><b>&nbsp;&nbsp;流程文件名:</b></td><td><ww:select name="xpdlFileName" list="xpdlFileNames" required="true" emptyOption="true"/></td>
 <td>
 <ul>
  <li id="button"><a href="javascript:updateVersion();">更新</a></li>
  <li id="button"><a href="javascript:uninstallVersion();">卸载</a></li>
  </ul>
  </tr></table>

				</td>
			</tr>
		</table>
		</td>
	</tr>
  </form>
<!-- 页面主要内容 -->
			
<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">			
			<tr>
				<td align="center" valign="top">
	<ec:table items="items"
	 tableId="${tableId}"
	 var="item"
	 action="${ctx}/flow/versionManager.action"
	 generateScript="true" classic="true" listWidth="100%"
	 xlsFileName="list.xls" pdfFileName="list.pdf" 
	 resizeColWidth="true" filterable="true"
	 >
		<ec:row recordKey="${item.packageId}" rowId="rowid_${GLOBALROWCOUNT}">
			<ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" width="8%" sortable="false" filterable="false" style="text-align:center">
				<input type="checkbox" name="_selectitem" value="${item.packageId};${item.version}" onclick='checkOne(allbox);'/> 
			</ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" filterable="false" width="8%" title="序号" style="text-align:center"/>
			<ec:column property="packageId" title="包ID" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="packageName" title="包名" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="version" title="版本" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="countPDefinitions" title="流程个数" sortable="true" filterable="true" style="text-align:center"/>			
	 	</ec:row>
	</ec:table>
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols="" style="display:none">
  <input type="text" class="inputtext" value=""
  onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"name="" />
 </textarea>		

</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%> 	
</body>
</html>

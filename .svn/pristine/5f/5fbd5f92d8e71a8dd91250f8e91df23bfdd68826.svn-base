<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="流程版本管理列表" />
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script language="javascript">
function edit(actid){
var pid=document.getElementById("processId").value;
var strurl="${ctx}/flow/flowTrack!edit.action?processId="+pid+"&activityId="+actid;
var winstyle='center=0;status=0;resizable=0;dialogWidth=400px; dialogHeight=250px;dialogLeft=200px;dialogTop=100px';
var belrt=window.showModalDialog(strurl,window,winstyle);
if(belrt)
   location.reload();
//alert(actid);
}
function deletesFiles(){
   var billIDs=getSelectedID();
   if(billIDs==""){
	   alert("请选择删除项!");
	   return false;
	}
var strurl="${ctx}/flow/xpdlFileManager!deletes.action?xpdlName="+billIDs;
window.location=strurl;
}
</script>
<body leftmargin="0" topmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面主要内容 -->
<div id="list_scroll_content" align=justify>
	<ec:table items="items"
	 tableId="${tableId}"
	 var="item"
	 action="${ctx}/flow/versionManager!list.action"
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
</div>	
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

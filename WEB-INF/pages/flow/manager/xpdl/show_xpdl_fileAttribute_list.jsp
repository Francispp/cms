<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script language="javascript">
var LetSelectRecordWord='<ww:property value="getText(\"RESOURCE.HINTINFO.LETSELECTRECORDWORD\")"/>';
var DeleteHintWord='<ww:property value="getText(\"RESOURCE.HINTINFO.DELETEHINTWORD\")"/>';

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
   if(billIDs==null||billIDs==""){
	   alert(LetSelectRecordWord);
	   return ;
	}
if(confirm(DeleteHintWord)){ 	
 var strurl="${ctx}/flow/xpdlFileManager!deletes.action?xpdlName="+billIDs;
 var pmessage=parent.document.getElementById("actionMessages");
 if(pmessage!=null&&pmessage.style!=null)
    pmessage.style.display = "none";
 window.location=strurl;
 }
}
</script>
<body leftmargin="0" topmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<!--div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt"></div>
	 </div>
  </div>
  <input type="button" value=<ww:property value="getText('RESOURCE.COMMON.DEL')"/>文件  onclick="deletesFiles()" class="input_button"/>
</div-->

<!-- 页面主要内容 -->
<div id="view_scroll_content" align=justify>
	<ec:table items="items"
	 tableId="${tableId}"
	 var="item"
	 action="${ctx}/flow/xpdlFileManager!list.action"
	 generateScript="true" classic="true" listWidth="100%"
	 xlsFileName="list.xls" pdfFileName="list.pdf" 
	 resizeColWidth="true" filterable="true"
	 >
		<ec:row recordKey="${item.packageId}" rowId="rowid_${GLOBALROWCOUNT}">
			<ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" width="8%" sortable="false" filterable="false" style="text-align:center">
				<input type="checkbox" name="_selectitem" value="${item.packageId}" onclick='checkOne(allbox);'/> 
			</ec:column>
			<ec:column property="rowcount" cell="rowCount" sortable="false" filterable="false" width="8%" title="序号" style="text-align:center"/>
			<ec:column property="packageId" title="文件名" sortable="true" filterable="true" style="text-align:center">
			<a href='${ctx}/workflow/webflow/index.jsp?xpdlfilename=${item.packageId}' target="_parent">${item.packageId}</a>
			</ec:column>
			<ec:column property="fileSize" title="文件大小(bytes)" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="fileUploadTime" title="上传时间" sortable="true" filterable="true" style="text-align:center"/>
			<ec:column property="packageId" title="下载" sortable="true" filterable="true" style="text-align:center">
			<a href='${ctx}/flow/xpdlFileManager!download.action?xpdlName=${item.packageId}' target="_blank">下载</a>
			</ec:column>
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

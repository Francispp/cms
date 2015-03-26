<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="静态资源列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">			

 var parameterUrl="";
 //编辑
 function editItem(oid){
  location.href='staticResource!edit.action?id='+oid+parameterUrl;
 }
 function addItem(){
  location.href='staticResource!edit.action?siteid=${siteid}';
 }
 function importZip(){
 var title="选择导入的文件";
 actionURL="${ctx}/cms/staticResource!importZipChannelRes.action?chnid=${chnid}";
 uploadXML(title,actionURL);
 }
 function exportZip(){
  location.href='staticResource!exportZip.action?siteid=${siteid}';
 }
 function multiUpload(){
  location.href='staticResource!multiUploadChannelRes.action?siteid=${siteid}&chnid=${chnid}';
 }

 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='staticResource!deleteChannelRes.action?siteid=${siteid}&chnid=${chnid}&keys='+ids+parameterUrl;
 }
 }
 
 function multiDistribution(){
	
		if(confirm('确定要分发全部静态资源吗？')){	
		  var url = 'staticResource!distribution.action';
		  new Ajax.Request(url, { method:'post', parameters: {siteid:${siteid},chnid:${chnid}, flag:0},
				onSuccess: function(transport){alert('批量分发成功！');}
		  });
		}
     
 }

 function selectDistribution(){
	 var ids=getSelectedID();
	 if(ids==null||ids==''){
	    alert('请先选择记录！');
	    return ;
	   }
	if(confirm('确定要分发这些静态资源吗？')){	
			var url = 'staticResource!selectDistribution.action';
			  new Ajax.Request(url, { method:'post', parameters: {siteid:${siteid}, keys:ids ,flag:0},
					onSuccess: function(transport){alert('选择分发成功！');}
			  });
	}
 
}
</script>

</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<!--<div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
  </div>
</div>

-->
<!-- 操作功能按钮条 -->
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	bgcolor="#FFFFFF">
	<tr>
		<td height="1"></td>
	</tr>
	<tr>
		<td>
		<div id="titel">
		<div id="titel_left">
		<div id="titel_right">
		<div id="nav">
		<div>
		
		
<div id="operationDivNoBorder">

 <!-- 操作功能按钮条 -->

 <ul>
  <cms:CmsAuth resCode="CMS_TEMPLATE_MANAGER" objectId="${siteid}" objectType="1">
  <li id="button1"><a href="javascript:selectDistribution()">选择分发</a></li>
   <li id="button1"><a href="javascript:multiDistribution()">批量分发</a></li>
  <li id="button1"><a href="javascript:multiUpload()">批量录入</a></li>  
  <li id="button"><a href="javascript:importZip()">导入</a></li>  
   <li id="button"><a href="javascript:exportZip()">导出</a></li>  
  <li id="button"><a href="javascript:deleteItem()">删除</a></li>  
  </cms:CmsAuth> 
  </ul>
  </div>

		</div>
		</div>
		</div>
		</div>
		</div>
		</td>
	</tr>
<!-- 页面主要内容 -->

	<tr>
		<td>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" valign="top">

<div id="list_scroll_content" align=justify>
	<ec:table items="items"
	          var="item" 
	          action="${ctx}/cms/staticResource.action"
		  updateAction="${ctx}/cms/staticResource!saveAjax.action"
		  deleteAction="${ctx}/cms/staticResource!deleteAjax.action"
		  insertAction="${ctx}/cms/staticResource!saveAjax.action"	          
		  editable="false" batchUpdate="false" xlsFileName="站点信息.xls"
		  pdfFileName="站点信息.pdf" csvFileName="站点信息.csv" minColWidth="80"
		  generateScript="true" classic="true" listWidth="100%"
		  rowsDisplayed="10" 
		  tableId="${tableId}" 
		  showPrint="true"
		  resizeColWidth="true" filterable="true"
	          filterRowsCallback="limit"
	          sortRowsCallback="limit"
	          retrieveRowsCallback="limit" useAjax="false"		  			  
		  >	          
		<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
		 <ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="false" width="8%" style="text-align:left">
            	         <input type="checkbox" name="_selectitem" value="${item.id}" onclick='checkOne(allbox);'/>
                        </ec:column>
		<ec:column property="_1" title="序号" width="3%" sortable="false" editable="false"
		 filterable="false" value="${GLOBALROWCOUNT}" style="text-align:center" />
		<ec:column property="name" value="<a href='javascript:editItem(${item.id});'>${item.name}</a>" title="静态资源名称" width="30%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="type" title="静态类型" width="20%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="uploadtime" title="上传时间" width="10%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />		 
				 		 
 		<ec:column property="description" title="备注" width="*%"
		 filterable="true" editTemplate="ecs_t_input" />              	 
	 </ec:row>
	</ec:table>

			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display:none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
			</textarea>
				                       				
</div>
	 
	 
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

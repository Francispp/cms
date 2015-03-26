<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="采编信息列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script src="${ctx}/common/cms_js/cms.js" type="text/javascript"></script>
<script type="text/javascript">			

 var parameterUrl="";
 //编辑


 function cleanUp(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要清空选择的记录吗？')){
  location.href='/cms/document!cleanup.action?siteid=${siteid}&keys='+ids+parameterUrl;
 }
 }
 function revivification(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要还原选择的记录吗？')){
  location.href='${ctx}/cms/document!revivification.action?siteid=${siteid}&keys='+ids+parameterUrl;
 }
 
 }
 var refresh = "${refresh}";
 if(refresh == "true")
 {
 window.parent.parent.document.frames['menu_frame'].document.frames['site_frame'].location.reload();
 }
 
 var channelId='';
 
 //点击查看，进入查看模板
 function editItem(oid,actid)
  {  	
  	var urlStr=null;
 	if (oid && oid > 0)
 	{
 		urlStr=ctx+"/cms/docInfo!view.action?id=" + oid + "&channelId="+channelId;
 	} 	
 	 location=urlStr;
  }
</script>

</head>
<body nowrap topmargin="0" leftmargin="0" >
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
<div id="nav">
 <div>
	<ul>
		<li id="button1"><a href="javascript:cleanUp();">清空</a></li>
		<li id="button1"><a href="javascript:revivification();">还原</a></li>
	</ul>
 </div>
</div> 
<!-- 页面主要内容 -->
<div id="list_scroll_content" align=justify style="height:320px;">
	<ec:table items="items"
	          var="item" 
	          action="${ctx}/cms/document!recycle.action"
		  updateAction="${ctx}/cms/document!saveAjax.action"
		  deleteAction="${ctx}/cms/document!deleteAjax.action"
		  insertAction="${ctx}/cms/document!saveAjax.action"	          
		  editable="false" batchUpdate="false" xlsFileName="站点信息.xls"
		  pdfFileName="站点信息.pdf" csvFileName="站点信息.csv" minColWidth="80"
		  generateScript="true" classic="true" listWidth="100%"
		  rowsDisplayed="10" 
		  tableId="${tableId}" 
		  showPrint="true"
		  resizeColWidth="true" filterable="true"
	          filterRowsCallback="limit"
	          sortRowsCallback="limit"
	          retrieveRowsCallback="limit"		  			  
		  >	          
		<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
		 <ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="false" width="8%" style="text-align:left">
            	         <input type="checkbox" name="_selectitem" value="${item.id}" onclick='checkOne(allbox);'/>
                        </ec:column>
		<ec:column property="rowcount" cell="rowCount" sortable="false" title="序号"/>
		<ec:column property="title" title="标题">
			 <a href="javascript:channelId='${item.channel.id}';editItem('${item.id}');">${item.title}</a>
			</ec:column>
			<ec:column property="timeCreated" title="创建时间"/>
			<!--ec:column property="timeDeleted" title="删除时间"/-->
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
<%@ include file="/common/foot.inc"%>
</body>
</html>

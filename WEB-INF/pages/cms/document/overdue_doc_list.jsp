<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="过期文档列表" />
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
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='/cms/document!overdueDoc.action?siteId=${siteId}&optName=cleanup&keys='+ids+parameterUrl;
 }
 }
 //还原过期文档
 function unOverdue(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要还原选择的记录吗？')){
  location.href='${ctx}/cms/document!adminUnoverdue.action?siteId=${siteId}&keys='+ids+"&returnMethod=adminOverdue"+parameterUrl;
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
<%@ include file="/common/messages.inc"%>
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
	 <div id="titel1_txt"><c:out value="${title}"/></div>
	 </div>
  </div>
</div></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
        <td height="1"></td>
      </tr>
      <tr>
        <td>
<div id="titel2">
  <div id="titel2_left">
     <div id="titel2_right">
       <div id="nav2">
         <div>
           <ul>
           <cms:CmsAuth resCode="CMS_SITE_OVERDUE_DOC" objectId="${siteId}" objectType="1">
             	<li id="button2"><a href="javascript:cleanUp();">删除</a></li>
		<li id="button2"><a href="javascript:unOverdue();">还原</a></li>
	  </cms:CmsAuth>	
           </ul>
         </div>
       </div>
      </div>
  </div>
</div></td>
      </tr>
      <tr>
        <td align="center" valign="top">        
 <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">
<!-- 页面主要内容 -->
	<ec:table items="items"
	          var="item" 
	          action="${ctx}/cms/document!overdueDoc.action"
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
	</td></tr></table>
	</td></tr></table>

			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display:none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
			</textarea>
				                       				

	 
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

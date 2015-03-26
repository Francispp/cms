<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<c:set var="title" value="静态资源回收站列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
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
  location.href='/cms/staticResource!cleanup.action?siteid=${siteid}&keys='+ids+parameterUrl;
 }
 }
 function revivification(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要还原选择的记录吗？')){
  location.href='${ctx}/cms/staticResource!revivification.action?siteid=${siteid}&keys='+ids+parameterUrl;
 }
 
 }
 var refresh = "${refresh}";
 if(refresh == "true")
 {
 window.parent.parent.document.frames['menu_frame'].document.frames['site_frame'].location.reload();
 }
</script>

</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<!--div id="titel">
	<div id="titel_left">
	<div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
	</div>
</div-->
<!-- 操作功能按钮条 -->
<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">

		
		
									 
  
      <li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
    
        <a class="artEdit-btn-in_ab" href="javascript:cleanUp();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>清空</span>
         </a>
        
    </li>
   
    <li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
   
          <a class="artEdit-btn-in_ab" href="javascript:revivification();">
          <img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" />
           
            <span>还原</span>
         </a>
        
    </li>
   
    <li class="fn-clear"></li>
</ul>
  </div>
 
</div>


	<div class="content">
  <div class="info-box">
				<div id="list_scroll_content" align=justify style="height:200px;">
				<ec:table items="items" var="item"
					action="${ctx}/cms/staticResource!recycle.action"
					updateAction="${ctx}/cms/staticResource!saveAjax.action"
					deleteAction="${ctx}/cms/staticResource!deleteAjax.action"
					insertAction="${ctx}/cms/staticResource!saveAjax.action"
					editable="false" batchUpdate="false" xlsFileName="站点信息.xls"
					pdfFileName="站点信息.pdf" csvFileName="站点信息.csv" minColWidth="80"
					generateScript="true" classic="true" listWidth="100%"
					rowsDisplayed="10" tableId="${tableId}" showPrint="true"
					resizeColWidth="true" filterable="true" filterRowsCallback="limit"
					sortRowsCallback="limit" retrieveRowsCallback="limit">
					<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
						<ec:column property="id"
							title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
							viewsDenied="xls" sortable="false" filterable="false" width="8%"
							style="text-align:left">
							<input type="checkbox" name="_selectitem" value="${item.id}"
								onclick='checkOne(allbox);' />
						</ec:column>
						<ec:column property="rowcount" cell="rowCount" sortable="false"
							title="序号" />
						<ec:column property="name" title="名称">${item.name}</ec:column>
						<ec:column property="timeCreated" title="创建时间" />
						<ec:column property="timeDeleted" title="删除时间" />
					</ec:row>
				</ec:table> <!-- 编辑和过滤所使用的 通用的文本框模板 --> <textarea id="ecs_t_input" rows=""
					cols="" style="display:none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
			</textarea></div>
				</div></div>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

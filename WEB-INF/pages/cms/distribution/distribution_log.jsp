<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="分发记录列表" />
<html>
<head>
<title>${title}</title>

<%@ include file="/common/meta.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>

<ww:head/>
<script>
var parameterUrl="&pageStyle=1";
//删除
function deleteItem(){
	 var siteId=document.getElementById("siteId").value;
	 var dtype=document.getElementById("dtype").value;
	var ids=getSelectedID();
	if(ids==null||ids==''){
	   alertMessage('请先选择记录！');
	   return ;
	   }
	if(confirm('您确定要删除选择的记录吗？')){
	 location.href='distributionLog!delete.action?keys='+ids+'&siteId='+siteId+"&dtype="+dtype+"&myTable_s_lastTime=desc&myTable_s_status=desc";
	}
}
//返回
function goBack(){
	 var siteId=document.getElementById("siteId").value;
	 location.href='siteDistribution.action?pageStyle=<ww:property value="pageStyle"/>&siteId='+siteId;
}
//清空
function deleteAll(){
	var siteId=document.getElementById("siteId").value;
	var dtype=document.getElementById("dtype").value;
	if(confirm('您确定要清空记录吗？')){
	 location.href='distributionLog!deleteAll.action?pageStyle=<ww:property value="pageStyle"/>&siteId='+siteId+"&dtype="+dtype;
	}
}
//分发
function rdistribution(){
	
	var ids=getSelectedID();
	var siteId=document.getElementById("siteId").value;
	var dtype=document.getElementById("dtype").value;
	if(ids==null||ids==''){
	  alertMessage('请先选择记录！');
	  return ;
	  }
	if(confirm('您确定要选择的记录吗？')){
		 var obj=new Array;
		  obj[0]= ids+""; 
		  obj[1]= siteId+""; 
		  ExecuteService("if(reply.getResult()=='true'){alert('分发成功!');location.href='distributionLog.action?siteId="+siteId+"&dtype="+dtype+"';}else{ alert('分发失败!');}",
		  			 'distributionLogService',
		  			 'rdistribution',
		  			 obj);
	}
 }
</script>
</head>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<table cellpadding="0" cellspacing="0" width="100%"><tr><td width="100%">
<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
    <li class="artEdit-btn_ab artEdit-btn-w4letters_ab"">

        <a class="artEdit-btn-in_ab" href="javascript:rdistribution();">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>重新分发</span>
         </a>
    </li>
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab"">
        <a class="artEdit-btn-in_ab" href="javascript:deleteAll();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>清空</span>
         </a>
  
    </li>

      <li class="artEdit-btn_ab artEdit-btn-w2letters_ab"">
        <a class="artEdit-btn-in_ab" href="javascript:deleteItem();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>删除</span>
         </a>
  
    </li>
    
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab"">
        <a class="artEdit-btn-in_ab" href="javascript:goBack();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>返回</span>
         </a>
  
    </li>
     
    <li class="fn-clear"></li>
</ul>
  </div>
 
</div>

	<div class="content">
  <div class="info-box">
  

<ww:hidden name="siteId" value="%{siteId}"></ww:hidden>
<ww:hidden name="dtype" value="%{dtype}"></ww:hidden>			
			
				<ec:table items="items" var="item" 
				action="${ctx}/cms/distributionLog.action"
					batchUpdate="false" xlsFileName="站点分发记录列表.xls"  minColWidth="80" generateScript="true"
					classic="true" listWidth="100%" rowsDisplayed="10"
					tableId="${tableId}" showPrint="true" resizeColWidth="true"
					filterable="true" filterRowsCallback="limit"
					sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
					<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
					<ec:column property="id"
							title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
							viewsDenied="xls" sortable="false" filterable="false" width="3%"
							style="text-align:center">
							<input type="checkbox" name="_selectitem" value="${item.id}"
								onclick='checkOne(allbox);' />
						</ec:column>
						<ec:column property="_1" title="序号" width="3%" sortable="false" editable="false"
		 filterable="false" value="${GLOBALROWCOUNT}" style="text-align:center" />
						<ec:column property="type"  title="分发类型" value="${item.typeName}" sortable="true"  style="text-align:center" editTemplate="ecs_t_input" >
						
						</ec:column>
						<ec:column property="name" title="分发资源名称"  editTemplate="ecs_t_input" />
					<ec:column property="ftpPath" title="分发资源ftp路径" editTemplate="ecs_t_input"  />
					<ec:column property="lastTime" title="最后上传时间" sortable="true" editTemplate="ecs_t_input" />
					<ec:column property="status" title="分发状态" sortable="true" style="text-align:center" editTemplate="ecs_t_input" >
					</ec:column>
					</ec:row>
				</ec:table> 
				<!-- 编辑和过滤所使用的 通用的文本框模板 --> <textarea id="ecs_t_input" rows=""
					cols="" style="display:none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
			</textarea>
</div></div>
</td></tr></table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>

</body>
</html>
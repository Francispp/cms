<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="站点管理列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>

<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<c:set var="icon_16_actions" value="${ctx}/images/icon/Nuvola/16/actions"/>
<script type='text/javascript' src='/dwr/interface/SiteManagerService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>

<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">			

 var parameterUrl="";
 //编辑
 function editSiteItem(){
	var ids=getSelectedID();
		 if(ids==null||ids==''){
		    alertMessage('请先选择记录！');
		    return ;
		  }
		 if(ids.split(",").length>1){
			 alertMessage('只能选择一个站点进行编辑，请重新先选择记录！');
			 return ;
		}
	global_ab.fn.popWindow.addPopWindow('cms/site!edit.action?id='+ids+parameterUrl, '600px', '430px', false);
  //  location.href='site!edit.action?id='+oid+parameterUrl;
 }
 
 function editItems(oid){
		global_ab.fn.popWindow.addPopWindow('cms/site!edit.action?id='+oid+parameterUrl, '600px', '430px', false);
		  
 }
 
 
 function sitePublic(){
	 
	 var ids=getSelectedID();
	 if(ids==null||ids==''){
		alertMessage('请先选择记录！');
	    return ;  
	}
	if(ids.split(",").length>1){
		alertMessage('只能选择一个站点进行动态发布，请重新先选择记录！');
		return ;
	}
	  var obj=new Array;
	  obj[0]= ids;
	  if(confirm('您确定要重新生成当前站点下所有模板吗？')){
	   ExecuteService("if(reply.getResult()=='true'){}else{ alert('操作失败!');}",'templateService','sitePublish',obj);
	  }
	}
 
 function addSiteItem(){
	  global_ab.fn.popWindow.addPopWindow("cms/site!edit.action?id="+parameterUrl, '600px', '430px', false);
	  
	 
	 // location.href='site!edit.action?id='+parameterUrl;
 }
 
//删除site
 function removeSiteItem(){
	
	 var ids=getSelectedID();
	 if(ids==null||ids==''){
			alertMessage('请先选择记录！');
			return ;
	 }
	 if(ids.split(",").length>1){
		   alertMessage('只能选择一个站点进行删除，请重新先选择记录！');
		   return ;
	}
   if(confirm('您确定要删除选择的记录吗？')){
  		 location.href="${ctx}/cms/site!chuckto.action?keys="+ids;
   }
 }
 

</script>
<style type="text/css">
/*选择资源图标*/
.selectRes{
	background-image: url('${icon_16_actions}/tools-check-spelling.png');
	background-repeat: no-repeat; 
	background-position: center;
	text-align:center;
	cursor:hand;
}
</style>
</head>
<script>

function loadpage(){
	 	  SiteManagerService.getListCmsSite(1,function(data){  
	 		  window.top.document.getElementById("currentsite").options.length = 0;//初始化select
	 		  window.top.document.getElementById("currentsite").selectedIndex = 0;//默认选中第一个
    		  var siteId="${loginer.siteId}";
    		  if(siteId=="0"){
    			 	window.top.document.getElementById("currentsite").options.add(new Option("cms内容管理系统","0"));
    			 	window.top.document.getElementById("currentsite").options.length = 1;//初始化select
    		  }
         	  for(var i=0;i<data.length;i++){
         			 window.top.document.getElementById("currentsite").options.add(new Option(data[i].sitename, data[i].oid));
         	  }
         	  
         	  window.top.document.getElementById("currentsite").value=siteId;
         	  if(siteId!="0"){
         		var  currentsite = window.top.document.getElementById("currentsite");
         		currentsite.options.add(new Option("cms内容管理系统", "0"));
         	  }
	      });

}
</script>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
   <cms:CmsAuth resCode="CMS_SITE_ADD" objectId="${loginer.siteId}" objectType="1">
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
    
        <a class="artEdit-btn-in_ab" href="javascript:addSiteItem();">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>新建</span>
         </a>
        
    </li>
  </cms:CmsAuth>
   <cms:CmsAuth resCode="CMS_SITE_MODI" objectId="${loginer.siteId}" objectType="1">
      <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
    
        <a class="artEdit-btn-in_ab" href="javascript:editSiteItem();">
            <img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" />
            <span>编辑</span>
         </a>
        
    </li>
    </cms:CmsAuth>
     <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="${ctx}/cms/site!list.action">
            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
    <cms:CmsAuth resCode="CMS_SITE_DELETE" objectId="${loginer.siteId}" objectType="1">
   
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
   
          <a class="artEdit-btn-in_ab" href="javascript:removeSiteItem();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>删除</span>
         </a>
        
    </li>
    </cms:CmsAuth>
     <cms:CmsAuth resCode="CMS_SITE_DYNAMIC_PUBLISHING" objectId="${loginer.siteId }" objectType="1">
    
   
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
   
          <a class="artEdit-btn-in_ab" href="javascript:sitePublic();">
            <img src="${default_imagepath}/ico_006_world.gif" class="ico_ab ico-006_ab" />
            <span>动态发布</span>
         </a>
        
    </li>
   </cms:CmsAuth>
    <li class="fn-clear"></li>
</ul>
  </div>
 
</div>


	<div class="content">
  <div class="info-box">	

<!-- 页面标题 -->

<!-- 页面主要内容 -->
<div id="page-content-no-border">
	<ec:table items="items"
	          var="item" 
	          action="${ctx}/cms/site!list.action"
		  updateAction="${ctx}/cms/site!saveAjax.action"
		  deleteAction="${ctx}/cms/site!deleteAjax.action"
		  insertAction="${ctx}/cms/site!saveAjax.action"	          
		  editable="false" batchUpdate="false" xlsFileName="站点信息.xls"
		   minColWidth="80"
		  generateScript="true" classic="true" listWidth="100%"
		  rowsDisplayed="10" 
		  tableId="${tableId}" 
		  showPrint="true"
		  resizeColWidth="true" filterable="true"
	          filterRowsCallback="limit"
	          sortRowsCallback="limit"
	          retrieveRowsCallback="limit"	useAjax="false"	  			  
		  >	          
		<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="_1"
							title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />"
							viewsDenied="xls" sortable="false" filterable="false"
							editable="false" width="4%" style="text-align:center">
							<input type="checkbox" name="_selectitem" value="${item.oid}"
								onclick='checkOne(allbox);' />
						</ec:column>
		<ec:column property="_2" title="序号" width="3%" sortable="false" editable="false"
		 filterable="false" value="${GLOBALROWCOUNT}" style="text-align:center" />
		 
		
		<ec:column property="sitename" title="站点名称" width="20%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input"  >
		 <a href="#" onclick="editItems(${item.oid})">${item.sitename}</a>
		 </ec:column>
		<ec:column property="sitehttp" title="站点HTTP" width="25%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input">
			<c:choose> 
		    	<c:when test="${fn:length(item.sitehttp) > 10}">
		    		<span title='${item.sitehttp }'>
					<c:out value="${fn:substring(item.sitehttp, 0, 20)}..." />
					</span>
		    	</c:when> 
				<c:otherwise>
					<c:out value="${item.sitehttp}" /> 
			    </c:otherwise> 
			</c:choose>
		</ec:column>

		 
		<ec:column property="siteport" title="端口" width="10%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />		 
		
		<ec:column property="ispublished" title="是否发布" width="8%"
		 sortable="true" filterable="true" editTemplate="ecs_t_select_IsNot" >
				<c:choose>
					<c:when test="${item.ispublished==1}">是</c:when>
					<c:otherwise>否</c:otherwise>
				</c:choose>
		</ec:column>		 		 
 		<ec:column property="remark" title="备注" width="*%"
		 filterable="true" editTemplate="ecs_t_input" >         
			<c:choose> 
		    	<c:when test="${fn:length(item.remark) > 10}"> 
		    		<span title='${item.remark }'>
					<c:out value="${fn:substring(item.remark, 0, 20)}..." /> 
					</span>
		    	</c:when> 
				<c:otherwise> 
					<c:out value="${item.remark}" /> 
			    </c:otherwise> 
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
			<textarea id="ecs_t_select_IsNot" rows="" cols="" style="display:none">
			 <select id="select_ispublished" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">			  
			  <option value="1">是</option>	
			  <option value="0">否</option>			 
			 </select>
                       </textarea>			
			<!-- 新建记录所用模板 -->
			<textarea id="add_template" rows="" cols="" style="display:none">
&#160;
<tpsp />
<input type="text" name="rolename" class="inputtext" value="" />
<tpsp />
<input type="text" name="rolecode" class="inputtext" value="" />
<input type="text" name="remark" class="inputtext" value="" />
<tpsp />
&#160;
</textarea>                       				
</div>

<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</div>
</div>
<script>
</script>
<c:if test="${not empty actionMessages}">
	<c:forEach var="err" items="${actionMessages}">
		<script>loadpage();</script>
	</c:forEach> 
</c:if>
</body>
</html>
<script type="text/javascript">
</script>

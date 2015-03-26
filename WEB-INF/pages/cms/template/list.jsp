<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="模板管理列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script src="${ctx}/dwr/interface/templateManagerService.js" type="text/javascript"></script>
<script src="${ctx}/common/cybercms_js/global_ab.js"
	type="text/javascript"></script>
<script type="text/javascript">			
	 var parameterUrl="siteId=${siteId}&channelId=${channelId}&templateType=${templateType}"
	 function insertItem(id)
	 {
	 	 var will= window.open("template!edit.action?" + parameterUrl);
	 	// alert("sss");
	 	/// location.href="${ctx}/cms/template!setDefault.action?id=48353&siteId=446&channelId=48301&templateType=1";
	 	//alert("sss");
	 	/*
	 	var returnstatus=document.getElementById("returnstatus").value;
	 		 if(returnstatus!=""){
	 		 		alert("站点创建成功1");
	 		 		location.reload();
	 		 }else{
	 			    alert("站点创建成功2");
	 		 		location.reload();
	 		 }
	 	*/
	 	
	 }
	 function refreshs(){
		 location.reload();
	 }
	 function deleteItem(id)
	 {
	 	 var idstrs=getSelectedID();
		 if(idstrs==null||idstrs=='')
		 {
		    alertMessage('请先选择记录！');
		    return ;
		}
		var idstr=idstrs.split(",");
		
		var ids="";
		for(var i=0;i<idstr.length;i++){
			var idstrfor=idstr[i].split('|');
			ids+=idstrfor[0];
			if((idstr.length-1)!=i){
				ids+=",";
			}
		}
		if (confirm("您确定要删除选择的记录吗?"))
		{
			location.href='template!delete.action?keys='+ids+"&" + parameterUrl;
		}
	 }
	 
	 function defaultItem (siteid, channel, id, templateType)
	 {
	       if (confirm("您确定要设置成默认模板吗?"))
	 	location.href="template!setDefault.action?id=" + id + "&siteId=" + siteid + "&channelId=" + channel + "&templateType=" + templateType;
	 }
	 function cancelDefaultItem (siteid, channel, id, templateType)
	 {
	       if (confirm("您确定要取消默认模板吗?"))
	 	location.href="template!setDefault.action?id=&siteId=" + siteid + "&channelId=" + channel + "&templateType=" + templateType;
	 }
	 function editItem(id)
	 {
		    document.getElementById("returnstatus").value="";
		 	window.open('template!edit.action?id='+ id + "&" + parameterUrl);
	 }
	 //从xml文件中导入信息
	 function inputData(){
	 var title="选择导入的文件";
	 actionURL="${ctx}/cms/template!importData.action?id=${channelId}&siteId=${siteId}";
	 uploadXML(title,actionURL);
	 }
	 //导出模板信息到xml中
	 function exportData(){
		 var idstrs=getSelectedID();
		 if(idstrs==null||idstrs=='')
		 {
		    alertMessage('请先选择记录！');
		    return ;
		}
		var idstr=idstrs.split(",");
		
		var ids="";
		for(var i=0;i<idstr.length;i++){
			var idstrfor=idstr[i].split('|');
			ids+=idstrfor[0];
			if((idstr.length-1)!=i){
				ids+=",";
			}
		}
		if (confirm("您确定要导出选择的模板吗?"))
		{
		location.href="template!exportData.action?keys="+ids+"&" + parameterUrl;
		}
	  
	 }
	 
	 
	 //设置后台概览页面
	 function generateAdminSummary (){
			//save ();
			var ids=getSelectedID();
			var idstr=ids.split(",");
			 if(ids==null||ids=='')
			 {
			    alertMessage('请先选择记录！');
			    return ;
			}
			 if(idstr.length>1){
				 alertMessage('只能选择一个模板设置后台概览模板，请先选择记录！');
				 return ;
				 
			 }
			var forids=ids.split("|");
			var formTemplateId=forids[0];
			var channelId=forids[2];
			window.open ("${ctx}/cms/template!edit.action?formTemplateId="+formTemplateId+"&channelId="+channelId+"&templateType=4");
	}
	 
	 
	 //设置引用
	 function setInclude(){
			 var ids=getSelectedID();
			 var idstr=ids.split(",");
			 if(ids==null||ids=='')
			 {
			    alertMessage('请先选择记录！');
			    return ;
			 }
			 if(idstr.length>1){
				 alertMessage('只能选择一个模板进行设置引用，请先选择记录！');
				 return ;
				 
			 }
			 var forids=ids.split("|");
				var templateId=forids[0];
				var siteId=forids[1];
				var channelId=forids[2];
				var templateType=forids[3];
				
			var url ="templateGather!list.action?templateId="+templateId+"&channelId="+channelId+"&templateType="+templateType+"&siteId="+siteId;
			window.open(url,'','resizable=yes,scrollbars=yes,menubar=no,toolbar=yes,status=no,width=600px,height=500px');
	 }
	 

	   
	//设置是否静态采集
	function cancelPublishStatic (siteid, channel, id, templateType, isPublishStatic){
		location.href="template!setIsPublishStatic.action?id=" + id + "&isPublishStatic=" + isPublishStatic + "&siteId=" + siteid + "&channelId=" + channel + "&templateType=" + templateType;
	}
</script>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
				<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
  
				 <li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
	       			 <a class="artEdit-btn-in_ab" href="javascript:insertItem();">
	           			 <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
	           			 <input type="hidden" name="returnstatus" id="returnstatus">
	            		<span>新增</span>
	         		</a>
    			</li>
    			<li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
          			<a class="artEdit-btn-in_ab" href="javascript:deleteItem();">
            			<img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
           			   <span>删除</span>
         			</a>
    			</li>
    			
    			 <li class="artEdit-btn_ab artEdit-btn-w3letters_ab" style="display:none;">
    			 		<a class="artEdit-btn-in_ab" href="javascript:inputData();">
    			 		<img class="ico_ab ico-020_ab" alt="" src="${default_imagepath}/ico_020_upcomingWork.gif" />
    			  		<span>导入</span> 
    			 </a></li>
    			
    			
    			
    			<li class="artEdit-btn_ab artEdit-btn-w3letters_ab" style="display:none">
    					<a class="artEdit-btn-in_ab" href="javascript:exportData();">
    					<img class="ico_ab ico-020_ab" alt="" src="${default_imagepath}/ico_020_upcomingWork1.gif" />
    					<span>导出</span>
    			 </a></li>
    			 
    			
    			 <li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
    					<a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
    					 <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
    					<span>刷新</span>
    			 </a></li>
           
    	<c:if test="${templateType==1}">
					
					<li class="artEdit-btn_ab">
        	<a class="artEdit-btn-in_ab" href="javascript:generateAdminSummary();">
        		<img src="${default_imagepath}/ico_012_zoom.gif" class="ico_ab ico-012_ab" />
                <span>生成后台概览</span>
             </a>
         </li>
		</c:if>
    	
    
 		
            <li class="artEdit-btn_ab">
        			<a class="artEdit-btn-in_ab" href="javascript:setInclude ()">
        				<img src="${default_imagepath}/ico_011_tag2.gif" class="ico_ab ico-011_ab" />
                		<span>设置引用</span>
             		</a>
        	</li>
    <li class="fn-clear"></li>
</ul>
  </div>
 
</div>


<!-- 页面主要内容 -->

		<div class="content">
  <div class="info-box">
<ec:table
	items="items" var="item" action="${ctx}/cms/template.action"
	editable="false" batchUpdate="false" xlsFileName="模板信息.xls"
	 minColWidth="80"
	generateScript="true" classic="true" listWidth="100%"
	rowsDisplayed="10" tableId="${tableId}" showPrint="true"
	resizeColWidth="true" filterable="true" filterRowsCallback="limit"
	sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" viewsDenied="xls"
			sortable="false" filterable="false" width="5%" style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.id}|${loginer.siteId}|${channelId}|${item.type}" onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column property="rowcount" cell="rowCount" sortable="false"
			width="8%" style="text-align:center" title="序号" />
		<ec:column property="name" title="名称" sortable="true"
			filterable="true"
			value="<a href='javascript:editItem(${item.id});'>${item.name}</a>"
			style="text-align:center" width="30%" />
		<ec:column property="issued" title="发布" sortable="true"
			filterable="false" style="text-align:center" editTemplate="ecs_t_select_IsNot" />
		<ec:column property="timeCreated" title="创建时间" sortable="true"
			filterable="true" width="15%" />
		<ec:column property="lastModified" title="最后修改时间" sortable="true"
			filterable="true" width="15%" />
		<c:if test="${(siteId!=null||channelId!=null) && (templateType == 2 || templateType == 3 || templateType == 5||templateType == 7 || templateType == 8 || templateType == 9)}">
			<ec:column property="isPublishStatic" title="是否静态采集" sortable="false"
				filterable="false" style="text-align:center" width="8%">
				<c:choose>
					<c:when test="${item.isPublishStatic}">
						<img alt="取消发布为静态文件"
							onclick="cancelPublishStatic('${siteId}','${channelId}', ${item.id}, ${item.type}, false)"
							style="cursor: pointer"
							src="${ctx}/images/common/dialog-apply.png" />
					</c:when>
					<c:otherwise>
						<img alt="设置发布为静态文件"
							onclick="cancelPublishStatic('${siteId}','${channelId}', ${item.id}, ${item.type}, true)"
							style="cursor: pointer"
							src="${ctx}/images/common/dialog-cancel.png" />
					</c:otherwise>
				</c:choose>
			</ec:column>
		</c:if>
		<c:if test="${(siteId!=null||channelId!=null)&& templateType>0}">
			<ec:column property="channel" title="默认" sortable="false"
				filterable="false" style="text-align:center" width="8%">
				<c:choose>
					<c:when test="${item.id==defaultTemplateId}">
						<img alt="" src="${ctx}/images/common/dialog-apply.png" />
						<!-- img alt="取消默认模板"
							onclick="cancelDefaultItem ('${siteId}','${channelId}', ${item.id}, ${item.type})"
							style="cursor: pointer"
							src="${ctx}/images/common/dialog-apply.png" /-->
					</c:when>
					<c:otherwise>
						<img alt="设置默认模板"
							onclick="defaultItem ('${siteId}','${channelId}', ${item.id}, ${item.type})"
							style="cursor: pointer"
							src="${ctx}/images/common/dialog-cancel.png" />
					</c:otherwise>
				</c:choose>
			</ec:column>
		</c:if>
	</ec:row>
</ec:table>

</div></div>

 <!-- 编辑和过滤所使用的 通用的文本框模板 --> 
 <textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea>
 <textarea id="ecs_t_select_IsNot" rows="" cols="" style="display:none">
			 <select id="select_ispublished" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%">			  
			  <option value="true">是</option>	
			  <option value="false">否</option>			 
			 </select>
                       </textarea>	
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>

<textarea id="test" name="test" style="width:500px;height:600px;display:none;" ></textarea>
</body>
</html>

<script type="text/javascript">
<!--
function setParentFrameWidthSize(){
	try{
 if(parent!=null&&parent.document.getElementById("a_tabbar")!=null)
  {
	 var iframes = parent.document.getElementsByTagName("iframe");
	 	
	  var bHeight = iframes[0].contentWindow.document.body.scrollHeight;
	  var dHeight = iframes[0].contentWindow.document.documentElement.scrollHeight;
	  var height = Math.max(bHeight, dHeight);
	 if(height > 500)
		 {
		 iframes[0].style.height=height;
	     parent.document.all.a_tabbar.style.height= height+"px";
	     parent.document.all.dhx_tabcontent_zone.style.height= height+10+"px";
		 }
	 else
		 iframes[0].style.height="95%"; 
	 
	//document.all.test.value = parent.document.all.a_tabbar.innerHTML
	// alert(parent.document.all.a_tabbar.innerHTML);
    

   setTimeout("top.scrollTo(0,0)",1);
  }
   }catch(E){}
}
//setParentFrameWidthSize();
//-->
</script>
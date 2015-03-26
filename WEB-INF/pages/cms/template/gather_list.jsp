<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<c:set var="title" value="引用信息列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>

<script type="text/javascript">			
	
	function saveGather(){
		var ids=document.getElementById("gatherChannel");
		var id;
		var templateId;
		var templateName;
		for(var i=0;i<ids.length;i++){
			if(ids[i].selected==true){
				id=ids[i].value;
				break;
			}
		}
		if(id!=null&&id!=''&&id.indexOf("|")>0){
			templateId=id.substring(0,id.indexOf("|"));
			templateName=id.substring(id.indexOf("|")+1,id.length);
		}
		
	
var url="${ctx}/cms/templateGather!save.action";
var param="?templateId=${templateId}&channelId=${channelId}&templateType=${templateType}&includeChannelId="+templateId+"&siteId=${siteId}";
location.href=url+param;
	}
	
	function deleteGather(){
		var ids=document.getElementsByName("_selectitem");
		var keys="";
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked==true){
				keys+=ids[i].value+",";
			}
		}
		if(keys==''){
			alert("请选择删除选项");
			return ;
		}
var url="${ctx}/cms/templateGather!delete.action";
location.href=url+"?templateId=${templateId}&channelId=${channelId}&templateType=${templateType}&siteId=${siteId}&keys="+keys;
		 
	}
	
</script>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
 	<li class="artEdit-edit_ab artEdit-btn-w2letters_ab" >
       <ww:action name="templateGather!gathertree" namespace="/cms" id="channels">
 			<ww:param name="siteId">${siteId}</ww:param>
	   </ww:action>
                     
       <select id="gatherChannel">
           <ww:iterator id="itemg" value="#attr.channels.items" status="sts">
              <option value="${itemg.id }|${itemg.name}">${itemg.name}</option>
           </ww:iterator>
       </select>
    </li>
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
        <a class="artEdit-btn-in_ab" href="javascript:saveGather();">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>新增</span>
         </a>
    </li>
     
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
          <a class="artEdit-btn-in_ab" href="javascript:deleteGather();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>删除</span>
         </a>
    </li>
    <li class="fn-clear"></li>
</ul>
  </div>
 
</div>


	<div class="content">
  <div class="info-box">	
		
		
			
<ec:table
	items="items" var="item" action="${ctx}/cms/templateGather!list.action"
	editable="false" batchUpdate="false" xlsFileName="引用信息.xls"
	 minColWidth="80" generateScript="true" classic="true" listWidth="100%"
	rowsDisplayed="10" tableId="${tableId}" showPrint="true"
	resizeColWidth="true" filterable="true" filterRowsCallback="limit"
	sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" viewsDenied="xls"
			sortable="false" filterable="false" width="5%" style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.oid}" onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column property="includeChannelName" title="频道名称" width="15%" />
		<ec:column property="timeCreated" title="创建时间" width="15%" />
		
	
	</ec:row>
</ec:table>

</div></div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

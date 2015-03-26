<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<c:set var="title" value="角色管理列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<c:set var="icon_16_actions" value="${ctx}/images/common" />
<script type='text/javascript' src='/dwr/interface/CmsPermissionService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type="text/javascript">	

//查看角色信息
function viewItem(value) {
	parent.frames['main_frame'].location.href='/cms/cmsrole!roleDetail.action?rolestatus=site&id='+value;
}
  //资源选择
 function selectRes(oid){ 
  //给角色赋权
   global_ab.fn.popWindow.addPopWindow('${ctx}/base/permission!tree.action?objectid='+oid+'&type=1',"385px","410px",false);
 }
 var parameterUrl="&pageStyle=1&objectId=<ww:property value="objectId" />";

 function editItem(){
     var keys = "";
     keys = getSelectedID();
     if (keys == null || keys == '') {
       alert('请先选择记录！');
       return;
     }
     var ids = keys.split(",");
     if (ids.length > 1) {
       alert("一次只能编辑一条数据!");
       return;
     }
     global_ab.fn.popWindow.addPopWindow("/cms/cmsrole!edit.action?rolestatus=site&id=" + ids[0], "600px", "420px", false);
   }
 
	//新增
	function addItem(){
		global_ab.fn.popWindow.addPopWindow('/cms/cmsrole!add.action?rolestatus=site&id='+parameterUrl,"600px","420px",false);
		//location.href='role!add.action?id='+parameterUrl;
	}

  	//删除
  	function deleteItem() {
  		var keys = getSelectedID();
  		if (keys == null || keys == '') {
  			alert('请先选择记录！');
  			return;
  		}
  		CmsPermissionService.dwrCoreRoleValidateHaveCmsPermission(keys,
  			function(data){
  				if (confirm(data + " 确定删除所选记录吗?")) {
  					location.href='/cms/cmsrole!delete.action?rolestatus=site&keys='+keys+parameterUrl;
  				}
  			}
  		); 
  	}	
 
	//刷新
	function refreshMenu(){
		location.reload()
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
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
    
        <a class="artEdit-btn-in_ab" href="javascript:addItem();">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>新增</span>
         </a>
       
					
    </li>
      <li class="artEdit-btn_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="editItem();"><img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" /><span>编辑</span></a>
          </li>
   
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
   
          <a class="artEdit-btn-in_ab" href="javascript:deleteItem();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>删除</span>
         </a>
      
    </li>
     <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
    <li class="fn-clear"></li>
</ul>
  </div>
 
</div>


	<div class="content">
  <div class="info-box">	
					
				<ec:table items="items"
					var="item" action="${ctx}/cms/cmsrole!extlist.action"
					updateAction="${ctx}/cms/cmsrole!saveAjax.action"
					deleteAction="${ctx}/cms/cmsrole!deleteAjax.action"
					insertAction="${ctx}/cms/cmsrole!saveAjax.action" editable="false"
					batchUpdate="false" xlsFileName="角色信息.xls"  minColWidth="80" generateScript="true"
					classic="true" listWidth="100%" rowsDisplayed="10"
					tableId="${tableId}" showPrint="true" resizeColWidth="true"
					filterable="true" filterRowsCallback="limit"
					sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">

					<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
						<ec:column property="_0"
							title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
							viewsDenied="xls" sortable="false" filterable="false"
							editable="false" width="8%" style="text-align:center">
							<input type="checkbox" name="_selectitem" value="${item.oid}"
								onclick='checkOne(allbox);' />
						</ec:column>
						<ec:column property="_1" title="序号" width="3%" sortable="false"
			editable="false" filterable="false"
			value="<a href='#' onclick='viewItem(${item.oid})'>${GLOBALROWCOUNT}</a>"
			style="text-align:center" />
						<ec:column property="rolename" title="角色名" width="20%" sortable="true"
			filterable="true" editTemplate="ecs_t_input"
			value="<a href='#' onclick='viewItem(${item.oid})'>${item.rolename}</a>" />
						<ec:column property="managerUserNames" title="角色管理员" width="20%"
							sortable="true" filterable="true" editTemplate="ecs_t_input" />
						<ec:column property="roleType" title="角色类型" width="10%"
							sortable="true" filterable="true" editTemplate="ecs_t_input">
							<c:choose>
								<c:when test="${item.roleType==1}">LDAP角色</c:when>
								<c:otherwise>普通角色</c:otherwise>
							</c:choose>
						</ec:column>
						<ec:column property="remark" title="角色说明" width="*%"
							filterable="true" editTemplate="ecs_t_input" />
						
					</ec:row>
				</ec:table> <!-- 编辑和过滤所使用的 通用的文本框模板 --> <textarea id="ecs_t_input" rows=""
					cols="" style="display:none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
			</textarea> <textarea id="ecs_t_select_portal" rows="" cols=""
					style="display:none">
			 <select id="select_portal" onblur="ECSideUtil.updateEditCell(this)"
					style="width: 100%">
			  <c:forEach var="portal" items="${requestScope.portals}"
						varStatus="status">
			  <option value="${portal.portalid}">${portal.cname}</option>
			 </c:forEach>			 
			 </select>
                       </textarea> <!-- 新建记录所用模板 --> <textarea id="add_template"
					rows="" cols="" style="display:none">
&#160;
<tpsp />
<input type="text" name="rolename" class="inputtext" value="" />
<tpsp />
<input type="text" name="rolecode" class="inputtext" value="" />
<tpsp />
 <select id="corePortal.oid" style="width: 100%" name="corePortal.oid">
  <c:forEach var="portal" items="${requestScope.portals}"
						varStatus="status">
   <option value="${portal.portalid}">${portal.cname}</option>
   </c:forEach>			 
 </select>
<tpsp />
<input type="text" name="remark" class="inputtext" value="" />
<tpsp />
&#160;
</textarea>
				<div id="perm_content" style="display:none;"><iframe
					id="application-info-iframe" src="" width="100%" height="350"
					style="border:0px none;"></iframe></div>

			</div></div>
<!-- 页脚 -->
 <c:if test="${not empty actionMessages}">
   <c:forEach var="err" items="${actionMessages}">
	<script>
	      var err="${err}";
	      if(err=='删除成功！'){
	   		window.location.href="${ctx}/cms/cmsrole!extlist.action";
	      }
	 </script>
 </c:forEach>  
</c:if>
<%@ include file="/common/foot.inc"%>


</body>
</html>

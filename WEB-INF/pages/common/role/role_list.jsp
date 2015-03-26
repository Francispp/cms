<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="title" value="角色管理列表" />

<head>
<title>${title}</title>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc" %>
<c:set var="icon_16_actions" value="${ctx}/images/common" />
<script type="text/javascript">			

function viewItem(value) {
	parent.frames['main_frame'].location.href='/base/role!roleDetail.action?rolestatus=system&id='+value;
}
  //资源选择
 function selectRes(oid){ 
  //给角色赋权
  //location.href="${ctx}/base/permission!tree.action?objectid="+oid+"&type=1";
  global_ab.fn.popWindow.addPopWindow("${ctx}/base/permission!tree.action?objectid="+oid+"&type=1","385px","408px",false);
  //Ext.get('application-info-iframe').dom.src="${ctx}/base/permission!tree.action?objectid="+oid+"&type=1";
  /*var permdialog;
  if(!permdialog){
                permdialog = new Ext.BasicDialog("perm_content", { 
                        modal:true,
                        width:400,
                        height:350,
                        shadow:true,
                        proxyDrag: true
                });
                permdialog.addKeyListener(27, permdialog.hide, permdialog);
     }
     permdialog.show();*/
 }

 var parameterUrl="&pageStyle=1";

 
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
     global_ab.fn.popWindow.addPopWindow("base/role!edit.action?rolestatus=system&id=" + ids[0], "600px", "420px", false);
   }
 
 //新增角色
  function addItem(){
	 global_ab.fn.popWindow.addPopWindow('base/role!add.action?rolestatus=system&id='+parameterUrl,"600px","420px",false);
  }
 //删除
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='role!delete.action?rolestatus=system&keys='+ids+parameterUrl;
  parent.parent.frames['switchToTree'].refreshMenu();
 }
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
<body style="margin:0px;padding:0px;width:100%">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>



<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
    <cms:auth resCode="SYS_ROLE_MAN">
        <a class="artEdit-btn-in_ab" href="javascript:addItem();">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>新建</span>
         </a>
        
    </li>
    
     <li class="artEdit-btn_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="editItem();"><img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" /><span>编辑</span></a>
          </li>
     </cms:auth>
     <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
   <cms:auth resCode="SYS_ROLE_MAN">
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">

        <a class="artEdit-btn-in_ab" href="javascript:deleteItem();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>删除</span>
         </a>

    </li>
   </cms:auth>
    <li class="fn-clear"></li>
</ul>
  </div>
 
</div>

	<div class="content">
  <div class="info-box">

<ec:table  
 items="items"
 var="item"
 action="${ctx}/base/role.action" 
 editable="false" 
 batchUpdate="false"
 xlsFileName="角色信息.xls"  
 minColWidth="80" 
 generateScript="true"
 classic="true" 
 listWidth="100%" 
 rowsDisplayed="10" 
 tableId="${tableId}" 
 showPrint="true" 
 resizeColWidth="true"
 filterable="true" 
 filterRowsCallback="limit" 
 sortRowsCallback="limit" 
 retrieveRowsCallback="limit" 
 useAjax="false">
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="_0"
			title="&nbsp;<input type='checkbox' name='allbox' onclick='checkAll(this);' />&nbsp;"
			viewsDenied="xls" sortable="false" filterable="false"
			editable="false" width="4%" style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.oid}"
				onclick='checkOne(allbox);'>
		</ec:column>
		<ec:column property="_1" title="序号" width="3%" sortable="false" editable="false" filterable="false" value="<a href='#' onclick='viewItem(${item.oid})'>${GLOBALROWCOUNT}</a>"
			style="text-align:center" />
		<ec:column property="rolename" title="角色名" width="20%" value="<a href='#' onclick='viewItem(${item.oid})'>${item.rolename}</a>"  sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="rolecode" title="角色编码" width="30%"	sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<!--ec:column property="coreOrg.oid" title="组织名称" width="15%"
		 sortable="true" filterable="true" editTemplate="ecs_t_select_portal" value="${item.coreOrg.orgName}"-->
		<ec:column property="remark" title="角色说明" width="*%"  sortable="true" filterable="true" editTemplate="ecs_t_input"  />
		<ec:column property="_3" title="赋权" width="5%" filterable="false"	editable="false" value="" styleClass="selectRes" onclick="selectRes('${item.oid}');" />
	</ec:row>
</ec:table> 






<!-- 编辑和过滤所使用的 通用的文本框模板 -->
 <textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
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
 <select id="coreOrg.oid" style="width: 100%" name="coreOrg.oid">
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
</div></div>
<c:if test="${not empty actionMessages}">
   <c:forEach var="err" items="${actionMessages}">
	<script>
	      var err="${err}";
	      if(err=='删除成功！'){
	   		window.location.href="${ctx}/base/role.action";
	      }
	 </script>
 </c:forEach>  
</c:if>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

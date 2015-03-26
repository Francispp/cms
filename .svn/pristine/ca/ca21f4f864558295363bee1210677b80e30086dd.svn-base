<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="资源管理列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">
var parameterUrl='&pageStyle=<ww:property value="pageStyle" />';
 function addItem(){
	 
	  global_ab.fn.popWindow.addPopWindow('base/resource!add.action?id='+parameterUrl,"600px","327px",false);
	  
 // location.href='resource!add.action?id='+parameterUrl;
 }
 function editItem(){
	 var ids=getSelectedID();
	 if(ids==null||ids==''){
	    alert('请先选择记录！');
	    return ;
	    }
	 if(ids.split(",").length>1){
		alert("只能选择一条记录进行编辑，请重新选择！");
	 	return;
	 }
	 global_ab.fn.popWindow.addPopWindow('base/resource!edit.action?id='+ids+parameterUrl,"600px","327px",false);
 //location.href='resource!edit.action?id='+oid+parameterUrl;
 }
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='resource!delete.action?keys='+ids+parameterUrl;
 }
 }

 //刷新
   function refreshMenu() {
   	location.reload()
   }
</script>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
  <ww:if test='#session.loginer.haveThePermission("CMS_BASE_PERMRESOURE_MAN")'>
    <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
        <a class="artEdit-btn-in_ab" href="javascript:addItem();">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>新建</span>
         </a>
    </li>
  
     <li class="artEdit-btn_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="editItem();"><img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" /><span>编辑</span></a>
          </li>
            </ww:if>
           <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
    <ww:if test='#session.loginer.haveThePermission("CMS_BASE_PERMRESOURE_MAN")'>
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
        <a class="artEdit-btn-in_ab" href="javascript:deleteItem();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>删除</span>
         </a>
    </li>
    </ww:if>
    <li class="fn-clear"></li>
</ul>
  </div>
 
</div>
	<div class="content">
  <div class="info-box">
<ec:table items="items" var="item" action="${ctx}/base/resource.action" editable="false"
	batchUpdate="false" xlsFileName="系统权限资源信息.xls"
	 minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.resourceid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="resourceid"
			title="&nbsp<input type='checkbox' name='allbox' onclick='checkAll(this);' />&nbsp"
			style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.resourceid}"
				onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column property="_1" title="序号" width="3%" sortable="false"
			editable="false" filterable="false"
			value="${GLOBALROWCOUNT}"
			style="text-align:center" />
		<ec:column property="resourcename" title="资源名称" width="20%" sortable="true"
			filterable="true" editTemplate="ecs_t_input"/>
		<ec:column property="resourcekey" title="权限代码" width="30%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="resourcetype" title="类型" width="20%"
			filterable="true" editTemplate="ecs_t_input">
		</ec:column>
		<ec:column property="resourcestring" title="动作" width="*%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
		

	</ec:row>
</ec:table>
<!-- 编辑和过滤所使用的 通用的文本框模板 -->
<textarea id="ecs_t_input" rows="" cols="" style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea>
<!-- 新建记录所用模板 -->
<textarea id="add_template" rows="" cols="" style="display:none">
&#160;
<tpsp />
<input type="text" name="resourcename" class="inputtext" value="" />
<tpsp />
<input type="text" name="resourcekey" class="inputtext" value="" />
<tpsp />
<input type="text" name="resourcetype" class="inputtext" value="" />
<tpsp />
<input type="text" name="resourcestring" class="inputtext" value="" />
<tpsp />
&#160;
</textarea>

</div></div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

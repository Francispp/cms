<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="常用信息管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
</head>
<script type="text/javascript">
	var parameterUrl="";
	//编辑
	function editItems(oid){
		global_ab.fn.popWindow.addPopWindow('base/commoninfo!edit.action?coreCommonTypeId=${coreCommonTypeId}&id='+oid+parameterUrl, 
				'350px', '420px', false);
	}
	
	function add(){
		global_ab.fn.popWindow.addPopWindow('base/commoninfo!edit.action?coreCommonTypeId=${coreCommonTypeId}'+parameterUrl,
				'350px', '420px', false);
	}
 
	function edit(){
		var ids=getSelectedID();
		if(ids==null||ids==''){
			alertMessage('请先选择记录！');
		    return ;
		}
		if(ids.split(",").length>1){
			 alertMessage('只能选择一个站点进行编辑，请重新先选择记录！');
			 return ;
		}
		global_ab.fn.popWindow.addPopWindow('base/commoninfo!edit.action?coreCommonTypeId=${coreCommonTypeId}&id='+ids+parameterUrl, 
				'350px', '420px', false);
	}
  
	function del(){
		var ids=getSelectedID();
		if(ids==null||ids==''){
	    	alertMessage('请先选择记录！');
	    	return ;
		}
		if(confirm('您确定要删除选择的记录吗？')){
			location.href='commoninfo!delete.action?coreCommonTypeId=${coreCommonTypeId}&keys='+ids;
		}
		//global_ab.fn.popWindow.addPopWindow('base/commoninfo!delete.action?keys='+ids+parameterUrl, '350px', '430px', false);
	}
	//刷新
	function refreshMenu() {
		location.reload()
	}
</script>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<div class="system-header">
	<div class="edit-header-lion">
	    <ul id="lion-ul">
		    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
		        <a class="artEdit-btn-in_ab" href="javascript:add();">
		            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
		            <span>新建</span>
		         </a>
		    </li>
		      <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
		        <a class="artEdit-btn-in_ab" href="javascript:edit();">
		            <img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" />
		            <span>编辑</span>
		         </a>
		    </li>
		     <li class="artEdit-btn_ab">
		        <a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
		            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
		            <span>刷新</span>
		         </a>
		    </li>
		    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
		          <a class="artEdit-btn-in_ab" href="javascript:del();">
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
			items="items" var="item" action="${ctx}/base/commoninfo!list.action" 
			editable="false" batchUpdate="false" xlsFileName="常用信息.xls" 
			minColWidth="80" generateScript="true" classic="true"
			listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
			showPrint="true" resizeColWidth="true" filterable="true"
			filterRowsCallback="limit" sortRowsCallback="limit"
			retrieveRowsCallback="limit" useAjax="false">
			<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
				<ec:column property="_1"
					title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />"
					viewsDenied="xls" sortable="false" filterable="false"
					editable="false" width="4%" style="text-align:center">
					<input type="checkbox" name="_selectitem" value="${item.id}" />
				</ec:column>
				<ec:column property="sortOrder" title="序号" width="3%" sortable="true" filterable="true"  editTemplate="ecs_t_input" style="text-align:center" value="<a href='#' onclick='editItems(${item.id})'>${item.sortOrder}</a>"/>
				<ec:column property="code" title="信息代码" width="30%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
				<ec:column property="content" title="信息内容" width="15%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
				<ec:column property="coreCommonType.keyword" title="类型" width="20%" sortable="true" editable="false" filterable="false" editTemplate="ecs_t_select_type" />
			</ec:row>
		</ec:table> 
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display:none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
				name="" />
		</textarea>
	</div>
</div>
<!--/div-->
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

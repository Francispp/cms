<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><ww:property value="getText('page.title')" /></title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">
<!--
var parameterUrl='&pageStyle=<ww:property value="pageStyle" />';
 function addItem(){
  global_ab.fn.popWindow.addPopWindow('/base/dept!adminAdd.action?id='+parameterUrl,"320px","350px",false);
 }
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='/base/dept!adminDelete.action?keys='+ids+parameterUrl;
 }
 }

function editItem(){
	var keys="";
	keys=getSelectedID();
	if(keys==null||keys==''){
		alert('请先选择记录！');
		return ;
	}
	var ids = keys.split(",");
	if(ids.length > 1){
		alert("一次只能编辑一条数据!");
		return;
	}
	global_ab.fn.popWindow.addPopWindow('/base/dept!adminEdit.action?id='+ids[0]+parameterUrl,"320px","350px",false);
}

function editItemById(id){
	global_ab.fn.popWindow.addPopWindow('/base/dept!adminEdit.action?id='+id+parameterUrl,"320px","350px",false);
}

//刷新
function refreshMenu() {
	location.reload()
}
-->
</script>
<ww:head />
</head>
<body>
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<c:if test="${hiddenHeard==null}">
<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
  <cms:auth resCode="SYS_USER_MAN">
  
    <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
        <a class="artEdit-btn-in_ab" href="javascript:addItem();">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>新建</span>
         </a>
    </li>
  	 <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
              <a class="artEdit-btn-in_ab" href="#" onclick="editItem()">
                  <img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" />
                  <span>编辑</span>
               </a>
          </li></cms:auth>
           <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
    <cms:auth resCode="SYS_USER_MAN">
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

</c:if>
	<div class="content">
  <div class="info-box">			
				<ec:table items="items"
					var="item" action="${ctx}/base/dept!adminList.action"
					updateAction="${ctx}/base/dept!saveAjax.action"
					insertAction="${ctx}/base/dept!saveAjax.action"
					deleteAction="${ctx}/base/dept!deleteAjax.action" editable="false"
					batchUpdate="false" generateScript="true" filterable="true"
					useAjax="false" doPreload="false" xlsFileName="用户列表.xls"
					 showPrint="true" rowsDisplayed="10"
					listWidth="100%" classic="true" tableId="${tableId}"
					filterRowsCallback="limit" sortRowsCallback="limit"
					retrieveRowsCallback="limit">
					<ec:row recordKey="${item.deptid}" rowId="rowid_${GLOBALROWCOUNT}">
						<ec:column property="_1"
							title="&nbsp;<input type='checkbox' name='allbox' onclick='checkAll(this);' />&nbsp;"
							viewsDenied="xls" sortable="false" filterable="false"
							editable="false" width="4%" style="text-align:center">
							<input type="checkbox" name="_selectitem" value="${item.deptid}"
								onclick='checkOne(allbox);' />
						</ec:column>
						<ec:column property="_2" sortable="false" filterable="false"
							editable="false" title="序号" style="text-align:center"
							viewsAllowed="html" value="${GLOBALROWCOUNT}" />
						<ec:column property="deptname" title="部门名称" style="text-align:center">
							<a href="#" onclick="editItemById(${item.deptid})">${item.deptname}</a>
						</ec:column>
						<ec:column property="depttype" title="类型" style="text-align:center"/>
						<ec:column property="remark" title="备注" style="text-align:center"/>
					</ec:row>
				</ec:table>

				
<!-- 编辑和过滤所使用的 通用的文本框模板 --> 

<textarea id="ecs_t_input" rows=""
					cols="" style="display:none">
 <input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
 </textarea> <textarea id="ecs_t_status" rows="" cols="" style="display:none">
	<select id="ecs_t_status" onblur="ECSideUtil.updateEditCell(this)"
					style="width: 100%">
		<option selected="selected"></option>
		<option value="1">有效</option>
		<option value="0">无效</option>
	</select>
</textarea> 


<!-- 新建记录所用模板 -->
 <textarea id="add_template" rows="" cols=""
					style="display:none">
&#160;
<tpsp />
&#160;
<tpsp />
<input type="text" name="loginname" class="inputtext" value="" />
<tpsp />
<input type="text" name="empcode" class="inputtext" value="" />
<tpsp />
<input type="text" name="name" class="inputtext" value="" />
<tpsp />
&#160;
<tpsp />
	<select id="state" style="width: 100%">
		<option selected="selected"></option>
		<option value="1">有效</option>
		<option value="0">无效</option>
	</select>
</textarea>
</div>
</div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

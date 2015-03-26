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
<script src="${ctx}/dwr/interface/userManagerService.js"
	type="text/javascript"></script>
<script type="text/javascript">
<!--
var parameterUrl='&pageStyle=<ww:property value="pageStyle" />&deptid=<ww:property value="deptid"/>';
 function addItem(){
  global_ab.fn.popWindow.addPopWindow('base/user!add.action?id='+parameterUrl,"700px","500px",false);
 }
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='user!delete.action?keys='+ids+parameterUrl;
 }
 }
  function changeViewStyle(){
  location.href="user!frame.action?id="+parameterUrl;   
}
function changeStatus(oid,status){
	var msg = "确定禁用该用户么？";
	if(status==1)
	 msg = "确定激活该用户么？";	
	parent.confirmMessage(msg,'提示',function(btn){
	if(btn=='yes'){	
         userManagerService.setUserStatus(oid,status);
         window.location.reload();
	}
	});
}
//删除用户
function removeUser(){
	 var ids=getSelectedID();
	 if(ids==null||ids==''){
	    alertMessage('请先选择记录！');
	    return ;
	    }
	 if(confirm('您确定要移除选择的记录吗？')){
	  return ids;
	 }
}
//从角色中移除用户
function removeUsersFromRole(){
	return getSelectedID();
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
	global_ab.fn.popWindow.addPopWindow('base/user!edit.action?id='+ids[0]+parameterUrl,"700px","500px",false);
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
					var="item" action="${ctx}/base/user.action"
					updateAction="${ctx}/base/user!saveAjax.action"
					insertAction="${ctx}/base/user!saveAjax.action"
					deleteAction="${ctx}/base/user!deleteAjax.action" editable="false"
					batchUpdate="false" generateScript="true" filterable="true"
					useAjax="false" doPreload="false" xlsFileName="用户列表.xls"
					 showPrint="true"
					listWidth="100%" classic="true" tableId="${tableId}"
					filterRowsCallback="limit" sortRowsCallback="limit"
					retrieveRowsCallback="limit">
					<ec:row recordKey="${item.userid}" rowId="rowid_${GLOBALROWCOUNT}">
						<ec:column property="_1"
							title="&nbsp;<input type='checkbox' name='allbox' onclick='checkAll(this);' />&nbsp;"
							viewsDenied="xls" sortable="false" filterable="false"
							editable="false" width="4%" style="text-align:center">
							<input type="checkbox" name="_selectitem" value="${item.userid}"
								onclick='checkOne(allbox);' />
						</ec:column>
						<ec:column property="_2" sortable="false" filterable="false"
							editable="false" title="序号" style="text-align:center"
							viewsAllowed="html" tipTitle="点击查看详细信息"
							value="${GLOBALROWCOUNT}">

						</ec:column>
						<ec:column property="loginid"  title="登录帐号" sortable="true"
							filterable="true" editable="false" style="text-align:center" value="${item.loginid}" />
						<ec:column property="ename" title="英文名" sortable="true"  filterable="true" editable="false" style="text-align:center" />
						<ec:column property="username" title="中文名" sortable="true"  filterable="true" style="text-align:center" />
						<ec:column property="deptname" title="部门名称" sortable="true"  filterable="true"  editTemplate="ecs_t_input"
							style="text-align:center" />
						<ec:column property="hotmail" title="电子邮件" sortable="true"  filterable="true"  editTemplate="ecs_t_input"
							style="text-align:center" />
						
						<ec:column property="state" title="状态" sortable="true"
							filterable="true" editable="false" editTemplate="ecs_t_status"
							style="text-align: center">
							<c:choose>
								<c:when test="${item.state==1}">
									<img  onclick="//changeStatus(${item.userid},0)"
										src="${ctx}/images/common/dialog-apply.png" />
								</c:when>
								<c:otherwise>
									<img  onclick="//changeStatus(${item.userid},1)"
										src="${ctx}/images/common/dialog-cancel.png" />
								</c:otherwise>
							</c:choose>
						</ec:column>
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
<input type="text" name="loginid" class="inputtext" value="" />
<tpsp />
<input type="text" name="empcode" class="inputtext" value="" />
<tpsp />
<input type="text" name="username" class="inputtext" value="" />
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

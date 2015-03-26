
				
				   <%@ page contentType="text/html; charset=UTF-8"%>
				   <%@ include file="/common/taglibs.inc"%>
				   <%@ include file="/common/buffalo.inc"%>
					<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
						<head>
							<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
							<title>${objectTypeName}权限设置</title>
							<ww:head />
							<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
							<%@ include file="/common/js.inc"%>
							<%@ include file="/common/ec/ec.inc"%>
							<script type="text/javascript" src="${ctx}/common/frame_js/select.js"></script>
							<script type="text/javascript">
								function saveGird(buttonObj,formid){
								 if(!confirm(ECSideMessage.UPDATE_CONFIRM)){
								  return;
								 }
								 var ecsideObj=ECSideUtil.getGridObj(formid);
								 var form=ecsideObj.ECForm;
								 var url=form.getAttribute("updateAction");	
								 var rows=ECSideUtil.getUpdatedRows(formid);
								 ecsideObj.forUpdateRows=rows;	
								 for (var i=0;i<rows.length;i++){	
								 var pars=ECSideUtil.getRowCellsMap(ecsideObj.forUpdateRows[i],formid);
								 ECSideUtil.doAjaxUpdate(url,pars,ecsideObj.updateCallBack,formid);
								 }
								 }
								 ECSideUtil.updateCellContent=function(cellObj,elementObj){	
								  var editType=elementObj.tagName.toLowerCase();
								 if (editType=="input"){
								 var type=elementObj.type.toLowerCase();
								 if (type=='checkbox' || type=='radio'){
								 editType=type;
								 }
								 }		
								 var value=elementObj.value;
								 if (editType=="input"){
								  cellObj.innerHTML=elementObj.value;
								 }else if (editType=="select"){
								  value=elementObj.options[elementObj.selectedIndex].value;
								  cellObj.innerHTML=elementObj.options[elementObj.selectedIndex].text;
								 }else if (editType=="checkbox" || editType=="radio"){
								 //cellObj.innerHTML=elementObj.nextSibling.nodeValue;
								  value=''+elementObj.checked;			
								 }
								  cellObj.setAttribute("cellValue",ECSideUtil.trimString(value));		
								 };	
								 function insertObject(roleType){						 
								  if(roleType==1)
								  {
								   <ww:if test="%{isSearchUsers}">
								     selectUser(document.myform.userId,document.myform.userName,true);
								     </ww:if>
								     <ww:else>
								    selectUserEx(document.myform.userId,document.myform.userName);
								    </ww:else>
								    }
								  if(roleType==2)
								  {
								  <ww:if test="%{isSearchUsers}">
								     selectRole(document.myform.userId,document.myform.userName,true);
								      </ww:if>
								     <ww:else>
								    selectRoleEx(document.myform.userId,document.myform.userName);
								     </ww:else>		
								    }						  															  								    
								  if(roleType==3)
								    selectDeptEx(document.myform.userId,document.myform.userName);
								  var obj=new Array;
								  obj[0]=roleType;
								  obj[1]=document.myform.userId.value;
								  obj[2]=document.myform.userName.value;
								  obj[3]=${objectType};
 								  obj[4]='${objectId}';
 								  obj[5]=${type};	
 								  if(obj[1]!=null && obj[1]!='')							 
								   ExecuteService("if(reply.getResult()=='true'){alert('增加权限成功!');window.location.reload();}else alert('增加权限失败!');",'cmsPermissionService','insertPermission',obj);								 
								 }
								 function deleteObject(id){
								  var obj=new Array;
								  obj[0]=id;
								   ExecuteService("if(reply.getResult()=='true'){alert('删除权限成功!');ECSideUtil.reload('myTable');}else alert('删除权限失败!');",'cmsPermissionService','deletePermission',obj);								 
								 }
								function changeIsInheritPerm(){
								 if(${type}!=${objectType} || ${objectType}<1)
								  alert('不能设置继承父系的权限项!');
								 var hitMessage='将继承父系的权限吗？';
								  var obj=new Array;
								  obj[0]=${objectType};
 								  obj[1]='${objectId}';
 								  obj[2]=1;
								 if(document.getElementById('isInheritPerm').checked){
								   obj[2]=1;
								   hitMessage='设置'+hitMessage;
								 }else{
								   obj[2]=0;
								   hitMessage='设置不'+hitMessage;
								 }
								 if(confirm(hitMessage)){
        							  ExecuteService("if(reply.getResult()=='true'){}else alert('保存失败!');",'cmsPermissionService','updateIsInheritPerm',obj); 	
 								  }
								 }								 								 							 
							</script>
						</head>
						<body topmargin="0" leftmargin="0">
							<%@ include file="/common/messages.inc"%>
							<div id="titel">
							  <div id="titel_left">
								 <div id="titel_right">
								 <div id="titel_txt">${objectTypeName}权限设置</div>
								 </div>
							  </div>
							</div>
							<div id="nav">
								<div>
									 <ul>
									 <c:if test="${type>1 && type==objectType}">
									 <li><input type='checkbox' name='isInheritPerm' id='isInheritPerm' value='1' onclick='changeIsInheritPerm();' <c:if test="${isInheritPerm==1}"> checked</c:if>/>将继承父系的权限</li>
									 <li></li>
									 </c:if> 									 
										<li id="button1"><a href="javascript:insertObject(1);">增加用户</a></li>
										<li id="button1"><a href="javascript:insertObject(2);">增加角色</a></li>
										<li id="button1"><a href="javascript:insertObject(3);">增加部门</a></li>
										<li id="button1"><a href="javascript:saveGird(this, 'myTable');">保存</a></li>
									 </ul>
								</div>
							</div> 
							<div id="list_scroll_content" align=justify>
								<ec:table items="items"
									var="item" 
									action="${ctx}/cms/permission.action"
									updateAction="${ctx}/cms/permission!saveAjax.action"
									sortable="false"
									editable="true"
									batchUpdate="false" 
									minColWidth="80"
									generateScript="true" classic="true" listWidth="90%"
									rowsDisplayed="10" 
									tableId="${tableId}" 
									showPrint="true" 
									resizeColWidth="true" 
									filterable="false"
									filterRowsCallback="limit"
									sortRowsCallback="limit"
									retrieveRowsCallback="limit"
									toolbarLocation="none">
									<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
									<ec:column property="oid" editable="false" title="<input type='checkbox' name='allbox' onclick=checkAllEx('_selectitem') />全选" viewsDenied="xls" width="8%" style="text-align:center">
            	      			     <input type="checkbox" name="_selectitem" value="${item.oid}" onclick=checkOneEx(allbox,'_selectitem') />
                        			</ec:column>
                        			<ec:column property="_1" title="删除" editable="false" style="text-align:center" width="8%"><a href="javascript:deleteObject('${item.oid}');">删除</a></ec:column>
                        			<ec:column property="roleName" editable="false" title="角色/用户" width="12%" />                        			
					
			<c:if test="${objectType!=type}">
<ec:column property="CMS_TEMPLATE_VIEW" title="浏览模板" editable="false" filterable="false" />
</c:if>
<ec:column property="CMS_TEMPLATE_ADD" title="新增模板" editable="false" filterable="false" />
<ec:column property="CMS_TEMPLATE_MODI" title="修改模板" editable="false" filterable="false" />
<c:if test="${objectType!=type}">
<ec:column property="CMS_TEMPLATE_DELETE" title="删除模板" editable="false" filterable="false" />
</c:if>

				
									</ec:row>
								</ec:table>	 
							</div>
							<textarea id="ecs_t_input" rows="" cols="" style="display:none">
								<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"name="" />
							</textarea>
							<form id="myform" name="myform" onsubmit="return true;" action="" method="POST">
							 <input type="hidden" name="userId" value="">
							 <input type="hidden" name="userName" value="">
							</form>								
							<%@ include file="/common/foot.inc"%>
						</body>
					</html>	
					
			
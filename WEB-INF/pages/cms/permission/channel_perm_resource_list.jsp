
				
				   <%@ page contentType="text/html; charset=UTF-8"%>
				   <%@ include file="/common/cyber_taglibs.inc"%>
				   <%@ include file="/common/buffalo.inc"%>
					<html>
						<head>
							<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
							<title>${objectTypeName}权限设置</title>
							<ww:head />
							<link href="${default_style}" rel="stylesheet" type="text/css">
							<%@ include file="/common/js.inc"%>
							<%@ include file="/common/ec/ec.inc"%>
							<script type="text/javascript" src="${ctx}/common/frame_js/select.js"></script>
							<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
							<script src="${ctx}/common/cms_js/util.js" type="text/javascript"></script>
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
								 var str = "增加权限失败!";
								 for (var i=0;i<rows.length;i++){	
								 var pars=ECSideUtil.getRowCellsMap(ecsideObj.forUpdateRows[i],formid);
								 ECSideUtil.doAjaxUpdate(url,pars,ecsideObj.updateCallBack,formid);
								 }
								 if ( rows.length>0)  alert('保存权限成功！');
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
								  //cellObj.innerHTML=elementObj.options[elementObj.selectedIndex].text;
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
								      str ="添加用户失败，列表已存在该用户！";
								     </ww:if>
								     <ww:else>
								    selectUserEx(document.myform.userId,document.myform.userName);
								    </ww:else>
								    }
								  if(roleType==2)
								  {
								  <ww:if test="%{isSearchUsers}">
								     selectRole(document.myform.userId,document.myform.userName,true);
								      str ="添加角色失败，列表已存在该角色！";
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
								   ExecuteService("if(reply.getResult()=='true'){window.location.reload();}else alert(str);",'cmsPermissionService','insertPermission',obj);								 
								 }
								 function deleteObject(id){
								  var obj=new Array;
								  obj[0]=id;
								  var hitMessage='您确定要删除选择的记录吗？';
								  if(confirm(hitMessage))
								   ExecuteService("if(reply.getResult()=='true'){alert('删除权限成功!');location.reload();}else alert('删除权限失败!');",'cmsPermissionService','deletePermission',obj);								 
								 }
								function changeIsInheritPerm(){
								//if(${type}!=${objectType} || ${objectType}<1)
								 if(${objectType}<1)
								 {
								  alert('不能设置继承父系的权限项!');
								  return false;
								  }
								 var hitMessage='将继承父系的权限吗？';
								  var obj=new Array;
								  obj[0]=${objectType};
 								  obj[1]='${objectId}';
 								  obj[2]=1;
 								  obj[3]=${type};
								 if(document.getElementById('isInheritPerm').checked){
								   obj[2]=1;
								   hitMessage='设置'+hitMessage;
								 }else{
								   obj[2]=0;
								   hitMessage='设置不'+hitMessage;
								 }
								 var flag=document.getElementById('isInheritPerm').checked;
								 if(confirm(hitMessage)){
        							  ExecuteService("if(reply.getResult()=='true'){alert('保存成功!');}else alert('保存失败!');",'cmsPermissionService','updateIsInheritPerm',obj); 	
 								  }else{
	 									  if(flag==false){
	 										 document.getElementById('isInheritPerm').checked=true;
	 									  }else{
	 										 document.getElementById('isInheritPerm').checked=false;
	 									  }
	 								  }
								 }
								 //选择或取消全部权限选择项
								 function checkAllPermession(_this){
								  var chkID = document.all.tags('input');
								  allchecked=true;
								  for (i=0; i<chkID.length; i++){
								   if (chkID(i).type=="checkbox" && chkID(i).value==_this.value){
								  	chkID(i).checked=_this.checked;
								  	ECSideUtil.updateEditCell(chkID(i));
								    }
								   }
								 }
								function checkOnePermession(_this){
								  var chkID = document.all.tags('input');
								  allchecked=true;
								  var chkall=null;
								  for (i=0; i<chkID.length; i++){
								  if (chkID(i).type=="checkbox" && chkID(i).checked==false && chkID(i).value==_this.value && chkID(i).name!='_selectitem'){	 
								 	allchecked=false;
								  }
								  if (chkID(i).type=="checkbox" && chkID(i).name=='_selectitem' && chkID(i).value==_this.value)
								   chkall=chkID(i);
								  }	
								  if(!chkall || chkall==null)  return ;
								  if (allchecked==false) {chkall.checked=false;}else{ chkall.checked=true;}								  
								  }
			                	  //角色权限
								  var RoleTypePerms=new Array;
								  <c:forEach var="roleType" items="${roleTypePerms}" varStatus="status">
								   RoleTypePerms[${status.index}]='${roleType}';
								  </c:forEach> 
								  //设置角色类别权限
								  function setRoleTypePermessions(_this){ 
								  var chkID = document.all.tags('input');
								  allchecked=true;
								  if(_this.value<RoleTypePerms.length){
								   ECSideUtil.updateEditCell(_this);
								  for (i=0; i<chkID.length; i++){								  
								   if (chkID(i).type=="checkbox" && chkID(i).value==_this.id){
								       //若名称在范围内，选中
								       if(RoleTypePerms[_this.value]!=null && (RoleTypePerms[_this.value]=='ALL'||RoleTypePerms[_this.value].indexOf(chkID(i).name+',')>=0)){
								  	chkID(i).checked=true;
								  	ECSideUtil.updateEditCell(chkID(i));
								  	}else{//不选中
								  	 chkID(i).checked=false;
								  	 ECSideUtil.updateEditCell(chkID(i));
								  	}
								    }
								   }
								   }								   
								  }									   								  								 								 							 
							</script>
						</head>
						<body topmargin="0" leftmargin="0">
							<%@ include file="/common/messages.inc"%>
							<table cellpadding="0" cellspacing="0" width="100%"><tr><td width="100%">
											<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
		 <c:if test="${type>1 && type==objectType}"> </c:if>
		  <c:if test="${type>1}"> 
									 <li class="artEdit-input_ab"><input type='checkbox' name='isInheritPerm' id='isInheritPerm' value='1' onclick='changeIsInheritPerm();' <c:if test="${isInheritPerm==1}"> checked</c:if>/>将继承父系的权限</li>
									 <li></li>
									 </c:if> 									 
									 
    <li class="artEdit-btn_ab artEdit-btn-w5letters_ab">
    
        <a class="artEdit-btn-in_ab" href="javascript:insertObject(1);">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>增加用户</span>
         </a>
        
    </li>
      <li class="artEdit-btn_ab artEdit-btn-w5letters_ab">
    
        <a class="artEdit-btn-in_ab" href="javascript:insertObject(2);">
            <img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" />
            <span>增加角色</span>
         </a>
        
    </li>
    <!--li id="button1"><a href="javascript:insertObject(3);">增加部门</a></li-->
	 <li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
             <a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
              <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
             <span>刷新</span> 
             </a>
             </li>									
    <li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
   
          <a class="artEdit-btn-in_ab" href="javascript:saveGird(this, 'myTable');">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>保存</span>
         </a>
        
    </li>
   
    <li class="fn-clear"></li>
</ul>
  </div>
 
</div>


	<div class="content">
  <div class="info-box">
								<ec:table items="items"
									var="item" 
									action="${ctx}/cms/permission.action"
									updateAction="${ctx}/cms/permission!saveAjax.action"
									sortable="false"
									editable="false"
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
									retrieveRowsCallback="limit" useAjax="false"
									>
									<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
									<ec:column property="oid" editable="false" title="全选" viewsDenied="xls" width="8%" style="text-align:center">
            	      			     <input type="checkbox" name="_selectitem" value="${item.oid}" onclick="checkAllPermession(this);" />
                        			</ec:column>
                        			
                        			<ec:column property="roleName" editable="false" title="角色/用户"  />
                        			<ec:column property="managerName" editable="false" title="权限类别"  />                         			
					
			<ec:column property="CMS_DOCUMENT_PERMISSION_MANAGER" title="文档权限管理" editable="false" filterable="false" />
<ec:column property="CMS_CHANNEL_VIEW" title="浏览频道" editable="false" filterable="false" />
<ec:column property="CMS_CHANNEL_ADD" title="新增频道" editable="false" filterable="false" />
<ec:column property="CMS_CHANNEL_MODI" title="修改频道" editable="false" filterable="false" />
<ec:column property="CMS_CHANNEL_DELETE" title="删除频道" editable="false" filterable="false" />
<ec:column property="CMS_TEMPLATE_MANAGER" title="模板管理" editable="false" filterable="false" />
<ec:column property="CMS_PERMISSION_MANAGER" title="权限管理" editable="false" filterable="false" />

				
				                    <ec:column property="_1" title="操作" editable="false" style="text-align:center" width="8%"><a href="javascript:deleteObject('${item.oid}');">删除</a></ec:column>
									</ec:row>
								</ec:table>	 
							</div></div>
							<textarea id="ecs_t_input" rows="" cols="" style="display:none">
								<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"name="" />
							</textarea>
							<form id="myform" name="myform" onsubmit="return true;" action="" method="POST">
							 <input type="hidden" name="userId" value="">
							 <input type="hidden" name="userName" value="">
							</form>	
							</td></tr></table>								
							<%@ include file="/common/foot.inc"%>
							<script type="text/javascript">
<!--
function setParentFrameWidthSize(){
	try{
 if(parent!=null&&parent.document.getElementById("a_tabbar")!=null)
  {
  
   
     var bWidth = document.body.scrollWidth;
     var dWidth = document.documentElement.scrollWidth;
     var width = Math.max(bWidth, dWidth);
  
   if(width > 500)
   {
    	var iframes = parent.document.getElementsByTagName("iframe");
    	iframes[0].style.width=width;
    	//iframes[0].style.height="98%";
    	
     }
     var iframes = parent.document.getElementsByTagName("iframe");
	 	
	  var bHeight = iframes[0].contentWindow.document.body.scrollHeight;
	  var dHeight = iframes[0].contentWindow.document.documentElement.scrollHeight;
	  var height = Math.max(bHeight, dHeight);
	   if(height > 500)
		 {
		 iframes[0].style.height=height;
	     parent.document.all.a_tabbar.style.height= height+"px";
	     parent.document.all.dhx_tabcontent_zone.style.height= height+20+"px";
		 }
	 else
		 iframes[0].style.height="98%"; 
    

   setTimeout("top.scrollTo(0,0)",1);
  }
   }catch(E){}
}
setParentFrameWidthSize();
//-->
</script>
						</body>
					</html>	
					
			
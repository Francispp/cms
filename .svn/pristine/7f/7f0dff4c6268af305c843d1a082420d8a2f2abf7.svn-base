<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="title" value="角色管理列表" />

<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<c:set var="icon_16_actions" value="${ctx}/${default_imagepath}/common" />


</head>

<body style="background:#fff;width:100%">
<div class="system-header">
  <div class="edit-header-lion">
   <ul id="lion-ul">
                  <cms:auth resCode="SYS_ROLE_MAN">  
                    <li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="javascript:editItem('${domain.oid }');"> <img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" /> <span>编辑</span> </a> </li>
                    <li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="javascript:deleteItem('${domain.oid }');"> <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" /> <span>删除</span> </a> </li>
                    </cms:auth>
                    <cms:auth resCode="SYS_PERMISSION_SET">  
                    <c:if test="${rolestatus!='site' }">
                    <li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="javascript:selectRes('${domain.oid }');"> <img src="${default_imagepath}/ico_022_clippedID.gif" class="ico_ab ico-017_ab" /> <span>赋权</span> </a> </li>
                   </c:if>
                    </cms:auth><li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="javascript:goBack('${rolestatus}');"> <img src="${default_imagepath}/ico_018_shortkey.gif" class="ico_ab ico-017_ab" /> <span>返回</span> </a> </li>  
                                     
                    <li class="fn-clear"></li>
                  </ul>
  </div>
 
</div>

	<div class="content" style="margin:0px;">
  <div class="info-box" style="margin:0px;">
  
  
   <div class="balic-table-bj-a" style="width:100%;">
                  <div class="balic-box" style="width:100%;">
                    <div class="balic-box-table">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="role-table">
                        <tr>
                          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="role-table-tetle">
                              <tr>
                                <td width="100%" class="role-table-telet-right">角色基本信息</td>
                              </tr>
                            </table></td>
                        </tr>
                        <tr>
                          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="role-con">
                              <tr class="role-con-link" >
                                <td width="20%" class="role-left-text">角色类型</td>
                                <td class="role-right-text">
									 <ww:iterator value="trueOfFalseMap1" id="temp1">
									 	<ww:if test="%{domain.roleType==temp1.key}">
									 		${temp1.value }
									 	</ww:if>
									 </ww:iterator>
								</td>
                                <td class="role-left-text">角色代码</td>
                                <td class="role-right-text">${domain.rolecode}
                               
							 </td>
                              </tr>
                             
                              <tr class="role-con-link" >
                                <td class="role-left-text">角色名称</td>
                                <td class="role-right-text">${domain.rolename}</td>
                                <td width="20%" class="role-left-text">角色管理员</td>
                                <td class="role-right-text"><span></span>${domain.managerUserNames }</td>
                              </tr>
                               
                               <tr class="role-con-link" >
                                <td class="role-left-text">状态</td>
                                <td class="role-right-text">
 									<ww:iterator value="trueOfFalseMap2" id="temp2">
									 	<ww:if test="%{domain.state==temp2.key}">
									 		${temp2.value }
									 	</ww:if>
									 </ww:iterator>
								</td>
                                <td width="20%" class="role-left-text">备注</td>
                                <td class="role-right-text"><span></span>${domain.remark}</td>
                              </tr>
                              
                              
                            </table></td>
                        </tr>
                        <tr>
                          <td valign="top"><div> <img src="${default_imagepath}/info-table-left.gif" class="myTab-foot-left_ab" /> <img src="${default_imagepath}/info-table-right.gif" class="myTab-foot-right_ab" /> </div></td>
                        </tr>
                      </table>
                      <ul id="lion-ul-b">
                        <li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="#" onclick="addUser()"> <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" /> <span>添加用户到角色</span> </a> </li>
                        <li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="javascript:removeUser();"> <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" /> <span>从角色中删除用户</span> </a> </li>
                        <li class="fn-clear"></li>
                      </ul>
                    </div>
                  </div>
                </div>
  
  
    <div class="balic-table-bj-b">
                  <div class="balic-box" style="width:100%;">
                  
                  <iframe id="iframe_users"  allowtransparency="yes" scrolling="auto"src="${ctx}/base/user!listAD.action?roleid=${domain.oid}" width="100%" height="200" style="border:0px none;" frameborder="0"></iframe>
					
              		</div>
              </div>
  
  
  </div></div>
  
  



</body>
</html>
<script type="text/javascript">			
  //资源选择
 function selectRes(oid){ 
  //给角色赋权
  global_ab.fn.popWindow.addPopWindow("${ctx}/base/permission!tree.action?objectid="+oid+"&type=1","385px","408px",false);
 }
 var parameterUrl="&pageStyle=1";
 //编辑
 function editItem(oid){
	 var rolestauts="${rolestatus}";
	 if(rolestauts=='site'){
		global_ab.fn.popWindow.addPopWindow('${ctx}/cms/cmsrole!edit.action?rolestatus=${rolestatus}&id='+oid+parameterUrl,"600px","420px",false);
	 }else{
	 	global_ab.fn.popWindow.addPopWindow('${ctx}/base/role!edit.action?rolestatus=${rolestatus}&id='+oid+parameterUrl,"600px","420px",false);
	 }
}
 //新增角色
  function addItem(){
	 global_ab.fn.popWindow.addPopWindow('base/role!add.action?id='+parameterUrl,"600px","420px",false);
  }
 //删除
 /*function deleteItem(){
	 var ids=getSelectedID();
	 if(ids==null||ids==''){
	    alertMessage('请先选择记录！');
	    return ;
	    }
	 if(confirm('您确定要删除选择的记录吗？')){
	    location.href='base/role!delete.action?keys='+ids+parameterUrl;
	 }
 }*/
</script>
<script type="text/javascript">
//<![CDATA[

//global_ab.fn.table_fn(3);


 //删除
 function deleteItem(ids){
	 if(confirm('您确定要删除选择的记录吗？')){
	   var rolestauts="${rolestatus}";
	   if(rolestauts=='site'){
		   location.href='${ctx}/cms/cmsrole!delete.action?rolestatus=${rolestatus}&keys='+ids+parameterUrl;
	   }else{
	  		location.href='${ctx}/base/role!delete.action?rolestatus=${rolestatus}&keys='+ids+parameterUrl;
	   }
	 }
 }
//添加角色去用户
function addUser(){
	selectUser('','','');

}

function selectUser(id,text,isOne)
{
 if(isOne == undefined)
 {
 	isOne=false;
}
 
 var rtn = window.showModalDialog("${ctx}/base/user!selectUser.action?selected=&selectedname=&isOne="+isOne,null,"dialogWidth:490px;dialogHeight:510px;");
 if(rtn == undefined) 
 {
   return;
 }
 rtn=rtn.split(";");
  id="";
  text="";
  i=0;
 while(i<rtn.length-1)
 {
   id+=rtn[i]+";";
   i++;
   text+=rtn[i]+";";
   i++;
 }
	var obj=new Array;
	obj[0]='${domain.oid}';
	obj[1]=''+id;
	ExecuteService("if(reply.getResult()=='true'){alert('增加用户操作成功!');document.frames['iframe_users'].location.reload();window.close();}else alert('增加用户操作失败!');",'roleManagerService','insertUsersToRole',obj);
}

 //从角色中移除用户
 function removeUser(){
	 var uids=document.frames['iframe_users'].removeUsersFromRole();
	 if(uids==''){
	 　alert('请选择需要从当前角色中删除的用户!');
	 　return ;
	 }else{
		 if(confirm('您确定要删除选择的记录吗？')){
			 var obj=new Array;
				obj[0]='${domain.oid}';
				obj[1]=''+uids;
				<ww:if test="%{isLocalUser}">
				ExecuteService("if(reply.getResult()=='true'){alert('删除人员操作成功!');document.frames['iframe_users'].location.reload();}else alert('删除人员操作失败!');",'roleManagerService','removeUsersFromRole',obj);
				</ww:if>
				<ww:else>
				ExecuteService("if(reply.getResult()=='true'){alert('删除人员操作成功!');document.frames['iframe_users'].location.reload();}else alert('删除人员操作失败!');",'roleManagerService','removeUsersAndADUserFromRole',obj);
				</ww:else>
			}
	 }
 }
 function goBack(status){
	 if(status=='site'){
		 location.href='${ctx}/cms/cmsrole!extlist.action';
	 }else{
		 location.href='${ctx}/base/role.action';
	 }
}
</script>

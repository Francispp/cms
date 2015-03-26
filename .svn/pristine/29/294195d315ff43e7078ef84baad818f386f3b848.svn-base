<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="用户管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/common/cms_js/util.js" type="text/javascript"></script>
<script src="${ctx}/dwr/interface/userManagerService.js"
	type="text/javascript"></script>
	
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-con-input-item_ab .lab-pw-tit_ab {
	width:100px;
	display:block;
	float:left;
	text-align:right;
	height:22px;
	line-height:22px;
	color:#333;
}
.textarea-hb{
	border:1px #999 solid;
	width:90%;
	margin:7px 0;
}
.textField-w150_ab {
	width:150px;
	_width:150px;
}
.textField-w100_ab {
	width:100px;
	_width:100px;
}
.pw-w650_ab{
	width:600px;
	height:470px;
	
}
</style>

<script type="text/javascript">
function save(){
 if(valid.validate()){
	 try{
		 //初始化的时候无用户选择
	 	document.getElementById('keys').value=getRoleIds();
	 }catch(e){
		 
	 }
	 myform.submit();
 }
 else
  dyniframesizeforall("main_frame");
}
function goBack(){
 location.href='user.action?pageStyle=<ww:property value="pageStyle" />&deptid=<ww:property value="deptid" />';
}
function InitUserPassword(oid){
	var msg = "确定要初始化用户密码吗？";	
	confirmMessage(msg,'提示',function(btn){
	if(btn=='yes'){	
         userManagerService.modifyUsersPassword(oid,'');
         alertMessage('初始化密码成功！');
	}
	});
}
function addRole(){
 //selectUserTreeOrSearch(document.getElementById('userids'),document.getElementById('usernames'),true,null,null,null,${isLocalUser})
  //selectRole(document.myform.groupIds,document.myform.groupNames,true);
  <ww:if test="%{isSearchUsers}">
								     selectRole(document.myform.groupIds,document.myform.groupNames,true);
								      </ww:if>
								     <ww:else>
								    selectRoleEx(document.myform.groupIds,document.myform.groupNames);
								     </ww:else>		
 if(document.myform.groupIds.value==null || document.myform.groupIds.value==''){
 alert('请选择角色!');
 return ;
 }
var obj=new Array;
obj[1]='${domain.userid}';
obj[0]=''+document.myform.groupIds.value;
document.myform.groupIds.value='';
ExecuteService("if(reply.getResult()=='true'){alert('增加角色操作成功!');document.frames['iframe_users'].location.reload();}else alert('增加角色操作失败!');",'roleManagerService','insertUsersToRole',obj);
}
//从当前角色中删除选择的用户
function deleteRole(){
 var uids=document.frames['iframe_users'].getSelectedID();
 if(uids==''){
 　alert('请选择需要删除的当前用户所属角色!');
 　return ;
 }
 
var obj=new Array;
obj[1]='${domain.userid}';
obj[0]=''+uids;
<ww:if test="%{isLocalUser}">
ExecuteService("if(reply.getResult()=='true'){alert('删除角色操作成功!');document.frames['iframe_users'].location.reload();}else alert('删除角色操作失败!');",'roleManagerService','removeUsersFromRole',obj);
</ww:if>
<ww:else>
ExecuteService("if(reply.getResult()=='true'){alert('删除角色操作成功!');document.frames['iframe_users'].location.reload();}else alert('删除角色操作失败!');",'roleManagerService','removeUsersAndADUserFromRole',obj);
</ww:else>
}
</script>
</head>
<body class="pw_ab" style="overflow:hidden;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<form class="pw-borderOut_ab pw-w650_ab" method="post" action="user!saveOrUpdate.action" name="myform">
		<ww:hidden name="domain.userid" id="userid" /> 
			<ww:hidden name="deptid" id="deptid" />
			<ww:hidden name="pageStyle" id="pageStyle" /> 
			<ww:hidden name="groupNames" id="groupNames" /> 
			<input type="hidden" name="groupIds"	value="" id="groupIds" /> 
			<ww:hidden	name="domain.password" />
			<ww:hidden name="domain.coreDept.deptid" id="coreDept.deptid" />
		<ww:hidden name="keys" id="keys" /> 
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>新建用户</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:450px;overflow-y:auto;">
        	<ul class="pw-con-input_ab pw-con-input-ex_ab">
            	<li class="pw-con-input-item_ab">
                	<h2 class="pw-con-input-item-tit_ab">基本信息</h2>
                </li>

                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>登录帐号：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
                  		<c:if test="${domain.userid!=null}">
						<ww:textfield name="domain.loginid" cssClass="required " readonly="true"  />
						</c:if>
						<c:if test="${domain.userid==null}">
						 <ww:textfield name="domain.loginid" cssClass="required max-length-24 textField_ab textField-w150_ab" />
						</c:if>
                    </label>
                </li>
                
                 <li class="pw-con-input-item_ab" style="">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>所属部门：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                       <ww:select name="domain.coreDept.deptid" id="coreDept.deptid" list="coreDepts" listKey="deptid"  listValue="deptname" cssClass="textField_ab textField-w150_ab"/>
                       
                  	
                    </label>
                </li>
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>员工编号：</span>
                        </span> 
                  	<ww:textfield name="domain.empcode" cssClass=" max-length-12 textField_ab textField-w150_ab" />
                    </label>
                </li>

                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>电子邮件：</span>
                        	
                        </span> 
                  	<ww:textfield name="domain.hotmail" cssClass=" max-length-30 textField_ab textField-w150_ab" />
                    </label>
                </li>
                 
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>移动电话：</span>
                        </span> 
                 	 <ww:textfield name="domain.mobilephone" cssClass=" max-length-13 textField_ab textField-w150_ab"/>
                    </label>
                </li>
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>英文名：</span>
                        	
                        </span>
                      
                  	 <ww:textfield name="domain.ename" cssClass="  max-length-16 textField_ab textField-w150_ab" />
                    </label>
                </li>
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>中文名：</span>
                        <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
</span> 
                  	<ww:textfield name="domain.username" cssClass="required max-length-24 textField_ab textField-w150_ab"/>
                         </label>
                </li>
               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>性别：</span>
                        </span> 
                  	<ww:select name="domain.sex" list="trueOfFalseMap1" cssClass="textField_ab textField-w150_ab"/>
                    </label>
                </li>
                <li class="pw-con-input-item_ab">
                  <label class="lab-pw_ab">
                     <span class="lab-pw-tit_ab">
                        	<span>状态：</span>
                        </span> 
                       <ww:select name="domain.state" list="trueOfFalseMap2" id="state" cssClass="textField_ab textField-w150_ab"/>
                    </label>
                </li>
            </ul>
            
            
            <ul class="pw-con-input_ab pw-con-input-ex_ab pw-con-input-rx_ab">
            	<li class="pw-con-input-item_ab">
                	<h2 class="pw-con-input-item-tit_ab">所属角色</h2>
                </li>
            	<li class="pw-con-input-item_ab">
                	<div class="pw-con-input-item-plu_ab">
						<iframe id="iframe_users"  src="${ctx}/base/role!roleTree.action?userId=${domain.userid}" width="100%" height="100%" style="border:0px none;"></iframe>
                    </div>
                </li>
            </ul>
            
            <div class="fn-clear"></div>
            <div class="pw-con-btns_ab">
			
				<input type="button" class="pwSubmit_ab" value="" onclick="javascript:save();"/>		
            	<input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
		 		
					<ww:if test="%{domain.userid!=null}&&#session.loginer.haveThePermission(\"SYS_USER_MANAGE_RESETPASSWORD\")">
						<input type="button" class="" value="初始密码" onclick="javascript:InitUserPassword('${domain.userid}')"/>
					</ww:if>
            </div>
        </div>
    </div>
</form>
<c:if test="${not empty actionMessages}">
	<c:forEach var="err" items="${actionMessages}">
		<script>
			parent.parent.parent.frames['main_frame'].refreshMenu();
	  		global_ab.fn.popWindow.removePopWindow(false);
		</script>
	</c:forEach> 
</c:if>

<script type="text/javascript">
global_ab.init.popwindowPage_fn();

(function()
{
	var closeBtn = document.getElementById("pwClose_ab");
	closeBtn.onclick = function()
	{
		global_ab.fn.popWindow.removePopWindow(false);
	}
})();

function getRoleIds(){
	var roles=window.frames["iframe_users"].getSelectIds();
	if(roles!=null&&roles.startsWith("roleCode")){
		roles=roles.substring(9);
	}
	return roles;

}
</script>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});

<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>
</script>
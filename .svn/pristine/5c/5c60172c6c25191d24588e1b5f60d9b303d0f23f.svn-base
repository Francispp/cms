<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="角色管理" />
<!DOCTYPE HTML>
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/common/cms_js/util.js" type="text/javascript"></script>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-w617_ab {
	width:600px;
	height:400px;
	
}
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
	width:100%;
	margin:7px 0;
}
.select-w150_ab {
	width:150px;
	_width:150px;
}
.textField-w70_ab{
width:90px;
}
.textField-w30_ab{
width:60px;
}
.textField-w150_ab {
	width:150px;
}
.select-w100_ab {
	width:80px;
	height:100px;
}
.pw-con-input-item-plu_ab {
	border:1px solid #999;
	width:200px;
	height:290px;
#height:290px;
}
.pw-con-input-rx_ab {
	width:200px;
	padding-left:30px;
}

</style>

<script type="text/javascript">
function save(){
	if(valid.validate()){
		var str = "";
		var roleString = "";
		//=[{"xlid":"cxh","xldigitid":123456,"topscore":2000,"topplaytime":"2009-08-20"},{"xlid":"zd","xldigitid":123456,"topscore":1500,"topplaytime":"2009-11-20"}]
		for(var i = 0; i < $("CMS2List").length; i++){
			if(i == 0){
				str = $("CMS2List").options[i].value;
				roleString = $("CMS2List").options[i].text;
			}else{
				str += "&" + $("CMS2List").options[i].value;
				roleString += "&" + $("CMS2List").options[i].text;
			}
		}
		document.getElementById("roleCore").value = str;
		document.getElementById("roleString").value = roleString;
		myform.submit();
	}
}

function init(){
	var roleType = '${domain.roleType}' + "";
	if(roleType == 2){
		var identityGradeMap = "${identityGradeMap}" + "";
		if(identityGradeMap != ""){
			var str = identityGradeMap.split("&");
			DWRUtil.removeAllOptions("CMS2List");
		    for (var i=0; i<str.length; i++)
		    {
		    	AddComboOption($("CMS2List"), str[i + 1], str[i]);
		    	++i;
		    }
		}
	}
}

function goBack(){
 location.href='role.action?pageStyle=<ww:property value="pageStyle" />&objectId=<ww:property value="objectId" />';
}
function addUser(){
 //selectUserTreeOrSearch(document.getElementById('userids'),document.getElementById('usernames'),true,null,null,null,${isLocalUser})
 <ww:if test="%{isSearchUsers}">
 selectUser(document.getElementById('userids'),document.getElementById('usernames'),true);
								      </ww:if>
								     <ww:else>
								     selectUserEx(document.getElementById('userids'),document.getElementById('usernames'));
								     </ww:else>		
 if(document.myform.userids.value==null || document.myform.userids.value==''){
 alert('请选择用户名!');
 return ;
 }
var obj=new Array;
obj[0]='${domain.oid}';
obj[1]=''+document.myform.userids.value;
document.myform.userids.value='';
ExecuteService("if(reply.getResult()=='true'){alert('增加人员操作成功!');document.frames['iframe_users'].location.reload();}else alert('增加人员操作失败!');",'roleManagerService','insertUsersToRole',obj);
}
//从当前角色中删除选择的用户
function deleteUser(){
 var uids=document.frames['iframe_users'].getSelectedID();
 if(uids==''){
 　alert('请选择需要从当前角色中删除的用户!');
 　return ;
 }
 
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
//设置角色类型
function setRoleType(_this){
 if(_this==1){//ldap角色
   document.getElementById('LDAP_TR').style.display="";
   document.getElementById('roleIdentity_1').style.display="none";
   document.getElementById('roleIdentity_1_1').style.display="none";
   document.getElementById('roleIdentity_2').style.display="none";
   //document.frames['iframe_users'].style.display="none";
 }else{
    document.getElementById('LDAP_TR').style.display="none";
    //document.frames['iframe_users'].style.display="";
 }
 
 if(_this==2){//外部角色
	   //document.getElementById("domain.rolecode").disabled="disabled";
	   document.getElementById('roleIdentity_1').style.display="block";
	   document.getElementById('roleIdentity_1_1').style.display="block";
	   document.getElementById('roleIdentity_2').style.display="block";
	   document.getElementById('LDAP_TR').style.display="none"
	   
	 }else{
		 document.getElementById("domain.rolecode").disabled="";
	    document.getElementById('roleIdentity_1').style.display="none";
	    document.getElementById('roleIdentity_1_1').style.display="none";
	    document.getElementById('roleIdentity_2').style.display="none";
	 }
}

</script>
</head>
<body class="pw_ab" onload="init();" style="overflow:hidden;padding:0px;margin:0px;border:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<form class="pw-borderOut_ab pw-w617_ab" method="post" action="${ctx}/cms/role!saveOrUpdate.action" name="myform">
		<ww:hidden name="pageStyle" id="pageStyle" /> 
		<ww:hidden name="objectId" id="objectId" />
		<ww:hidden name="domain.oid" id="domain.oid" />
		<ww:hidden name="domain.coreOrg.oid" id="domain.coreOrg.oid" />
		<ww:hidden name="roleCore" id="roleCore" />
		<ww:hidden name="roleString" id="roleString" />
		<input type="hidden" name="userids" value="" id="userids" />
		<input type="hidden" name="usernames" value="" id="usernames" />
		
	<div class="pw-borderIn_ab" style="height:418px;">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>新建角色</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:380px;">
        	<ul class="pw-con-input_ab pw-con-input-ex_ab">
            
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>角色名称：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                       <ww:textfield name="domain.rolename"
							cssClass="required textField_ab textField-w150_ab"/>
                    </label>
                </li>
              <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>角色管理员：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                      <ww:hidden name="domain.managerUserIds" id="managerUserIds" />
					  <input type="text" readonly="readonly" name="domain.managerUserNames" value="<ww:property value="domain.managerUserNames" />" id="managerUserNames" class="textField_ab textField-w70_ab"/>
				      <input type="button" name="button" onclick="selectUserTreeOrSearch(document.getElementById('managerUserIds'),document.getElementById('managerUserNames'),true,null,null,null,${isLocalUser})" value="选择用户" style="margin-left:2px;" class="textField_ab textField-w30_ab">
                    </label>
                </li>
                
               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>角色类型：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                       <ww:select name="domain.roleType" id="domain.roleType" list="trueOfFalseMap1"
							onchange="setRoleType(this.value)" cssClass="required select_ab select-w150_ab"/> 
                    </label>
                </li>
               
               <li class="pw-con-input-item_ab" id="roleIdentity_1">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>身份类型：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                     <ww:select id="selectRoleIdentity" name="domain.roleIdentity" list="#request.coreIdentityList" listKey="iid" listValue="iname" cssClass="select_ab select-w150_ab"/>
                    </label>
                </li>
                
                 <li class="pw-con-input-item_ab" id="roleIdentity_2">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>业绩水平：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                        <span class="lab-pw-tit_ab_c">
                      <ww:select cssStyle="vertical-align:middle;WIDTH:150px;height:130px" id="gradeId" name="gradeId" list="#request.coreGradeList" listKey="gcode" listValue="gname" multiple="true" size="6" cssClass="select_ab select-w150_ab"/>
                   		   
                      	</span>
                      
                   
                    </label>
                </li>
               <li class="pw-con-input-item_ab" id="LDAP_TR" style="position:absolute;width:600px;">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>SQL：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                     <ww:textarea
							name="domain.relationContent"  cssClass="textarea-hb select-w150_ab" rows="6" cssStyle="width:400px;height:100px;"/>
							 </label>
                </li>
                
               
                
               
            </ul>
            
            
            <ul class="pw-con-input_ab pw-con-input-ex_ab" style="height:264px;">
            
             <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>角色代码：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                       <ww:textfield
							name="domain.rolecode" cssClass="required textField_ab textField-w150_ab" />
                    </label>
                </li>
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>状态：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                     <ww:select name="domain.state"
							list="trueOfFalseMap2" cssClass="select_ab select-w150_ab"/>
							 </label>
                </li>
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>备注：</span>
                        </span>
                         <ww:textfield
							name="domain.remark" cssClass="textField_ab textField-w150_ab" />
                     
                    </label>
                </li>
                 <li class="pw-con-input-item_ab" id="roleIdentity_1_1">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab" style="float:left;">
                        	<span style="float:left;"><br/><input onClick="addRole();" type="button" style="WIDTH:50px" value=">"/><br/>
		    						<input onClick="deleteRole();" type="button" style="WIDTH:50px" value="<"/><br/>
		    						<input type="button" value=">>" onClick="addAllRole()" style="WIDTH:50px"/><br/>
		    						<input type="button" value="<<" onClick="deleteAllRole()" style="WIDTH:50px"/>	   </span>
                        </span>
                        <select id="CMS2List" style="vertical-align:middle;WIDTH:150px;height:150px" size="10" name="CMS2List"></select>
                     
                    </label>
                </li>
            
            </ul>
            
            <div class="fn-clear"></div>
            <div class="pw-con-btns_ab">
            	<input type="button" class="pwSubmit_ab" value="" onclick="javascript:save()"/>
              	<input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
            </div>
       
       
        </div>
    </div>
</form>
<script type="text/javascript">
//<![CDATA[
global_ab.init.popwindowPage_fn();

(function()
{
	var closeBtn = document.getElementById("pwClose_ab");
	closeBtn.onclick = function()
	{
		global_ab.fn.popWindow.removePopWindow(false);
	}
})();

//]]>
</script>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
document.getElementById('domain.coreOrg.oid').value='<ww:property value="domain.coreOrg.oid" />';
<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>
setRoleType(document.getElementById("domain.roleType").value);

function addRole(){
	var flag = true;
	if($("gradeId").selectedIndex == -1){
		alert("请选择业绩水平!");
	}else{
		var roleIdentityValue = $("selectRoleIdentity").options[$("selectRoleIdentity").selectedIndex].value; 
	   	var roleIdentityText = $("selectRoleIdentity").options[$("selectRoleIdentity").selectedIndex].text;
	   	var optionValue = "";
	   	var optionText = "";
		for(var i = 0; i < $("gradeId").length; i++){
			if($("gradeId").options[i].selected){
				optionValue = roleIdentityValue + "_" + $("gradeId").options[i].value;
				optionText = roleIdentityText + "_" + $("gradeId").options[i].text;
				for(var j = 0; j < $("CMS2List").length; j++){
					if($("CMS2List").options[j].value == optionValue){
						alert("已经添加了'" + optionText + "'.!" );
						flag = false;
						break;
					}
				}
				if(flag){
					AddComboOption($("CMS2List"), optionText, optionValue);
					$("CMS2List").selectedIndex = $("CMS2List").options.length - 1;
				}
				flag = true;
			}
		}
	}
}

function addAllRole(){
	var flag = true;
	var roleIdentityValue = $("selectRoleIdentity").options[$("selectRoleIdentity").selectedIndex].value; 
   	var roleIdentityText = $("selectRoleIdentity").options[$("selectRoleIdentity").selectedIndex].text;
   	var optionValue = "";
   	var optionText = "";
	for(var i = 0; i < $("gradeId").length; i++){
		optionValue = roleIdentityValue + "_" + $("gradeId").options[i].value;
		optionText = roleIdentityText + "_" + $("gradeId").options[i].text;
		for(var j = 0; j < $("CMS2List").length; j++){
			if($("CMS2List").options[j].value == optionValue){
				alert("已经添加了'" + optionText + "'.!" );
				flag = false;
				break;
			}
		}
		if(flag){
			AddComboOption($("CMS2List"), optionText, optionValue);
			$("CMS2List").selectedIndex = $("CMS2List").options.length - 1;
		}
		flag = true;
	}
}

function AddComboOption(combo, optionText, optionValue, documentObject, index){
	var option;
	
	if (documentObject)
		option = documentObject.createElement("OPTION") ;
	else
		option = document.createElement("OPTION") ;

	if (index != null)
		combo.options.add(option, index);
	else
		combo.options.add(option);
	option.innerHTML = optionText.length > 0 ? HTMLEncode(optionText) : '&nbsp;';
	option.value = optionValue ;

	return option;
}

function HTMLEncode( text ){
	if ( !text )
		return '' ;

	text = text.replace( /&/g, '&amp;' ) ;
	text = text.replace( /</g, '&lt;' ) ;
	text = text.replace( />/g, '&gt;' ) ;

	return text ;
}

function deleteRole(){
	//获取INDEX
	var selectedindex = $("CMS2List").selectedIndex;
	
	$A($("CMS2List").options).each (function (option)
	{
		if (option.selected)
		{
			$("CMS2List").removeChild (option);
		}
	}.bind (this));
		
	//自动选中上一份  
	if(selectedindex > 0)
	   	$("CMS2List").selectedIndex = selectedindex-1;
	else if($("CMS2List").length > 0)
	    $("CMS2List").selectedIndex = 0; 
}

function deleteAllRole(){
	$A($("CMS2List").options).each (function (option)
	{
		$("CMS2List").removeChild (option);
	}.bind (this));
}
</script>
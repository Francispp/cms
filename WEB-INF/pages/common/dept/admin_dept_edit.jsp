<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.cyberway.core.utils.ServiceLocator"%>
<%@ page import="com.cyberway.common.area.service.AreaService"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="用户管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<script src="${ctx}/common/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/common/cms_js/util.js" type="text/javascript"></script>
	
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
		jQuery.ajax({
            type: "POST",
            url: '/base/dept!adminSaveOrUpdate.action',//提交的URL
            data: jQuery('#myform').serialize(), // 要提交的表单,必须使用name属性
            async: false,
            dataType: 'json',
            success: function (data) {
            	if(data.success){
                	alert(data.msg);
					parent.parent.parent.frames['main_frame'].refreshMenu();
			  		global_ab.fn.popWindow.removePopWindow(false);
            	}else{
                	alert(data.msg);
            	}
            },
            error: function (request) {
                alert("保存失败");
            }
        });
		//myform.submit();
	}
	else
		dyniframesizeforall("main_frame");
}
function goBack(){
 location.href='dept!adminList.action?pageStyle=<ww:property value="pageStyle" />&isInternal=<ww:property value="isInternal" />';
}

function updateKey(provinceId,cityId)
{
	var selectKey = document.getElementById(provinceId);
	var selectValue =selectKey.options[selectKey.selectedIndex].value;
	var changeKey = document.getElementById(cityId);
	areaService.getCities(selectValue,
		function(data){
			changeKey.options.length=1;
			DWRUtil.addOptions(changeKey,data,"id","name");
		}
	);
}
</script>
</head>
<body class="pw_ab" style="overflow:hidden;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<form id="myform" class="pw-borderOut_ab pw-w350_ab" method="post" action="webuser!adminSaveOrUpdate.action" name="myform">
	<ww:hidden name="domain.deptid" id="deptid" />
	<ww:hidden name="domain.pdeptid" id="pdeptid" />
    <c:if test="${domain.deptid!=null}">
		<ww:hidden name="domain.state" id="state" />
	</c:if>
    <c:if test="${domain.deptid==null}">
		<ww:hidden name="domain.state" id="state" value="0" />
	</c:if>
	<ww:hidden name="domain.pdeptname" id="pdeptname" />
	<ww:hidden name="pageStyle" id="pageStyle" />
	<ww:hidden name="keys" id="keys" />
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>新建\编辑部门</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:300px;overflow-y:auto;">
        	<ul class="pw-con-input_ab pw-con-input-ex_ab">
            	<li class="pw-con-input-item_ab">
                	<h2 class="pw-con-input-item-tit_ab">基本信息</h2>
                </li>

                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>部门名称：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
						<ww:textfield name="domain.deptname" cssClass="in_Text width01 required max-length-24 textField_ab textField-w150_ab" />
                    </label>
                </li>

				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>部门类型：</span>
						</span> 
                  		<ww:textfield name="domain.depttype" cssClass="in_Text width01 max-length-30 textField_ab textField-w150_ab"/>
                    </label>
                </li>

                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>备注：</span>
                        </span> 
                  		<ww:textarea name="domain.remark" cols="30" rows="3" cssClass="max-length-120 in_Text width01"/>
                    </label>
                </li>

            <div class="fn-clear"></div>
            <div class="pw-con-btns_ab">
			
				<input type="button" class="pwSubmit_ab" value="" onclick="javascript:save();"/>		
            	<input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
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

function getRoleIds(){
	var roles=window.frames["iframe_users"].getSelectIds();
	if(roles!=null&&roles.startsWith("roleCode")){
		roles=roles.substring(9);
	}
	return roles;

}
//]]>
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
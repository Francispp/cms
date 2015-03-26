<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="角色管理" />
<!DOCTYPE HTML>
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<script type='text/javascript' src='/dwr/interface/gradeManagerService.js'></script>
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
.pw-w417_ab {
	width:500px;
}
.pw-con-input_ab {
	width:420px;
	margin:0 auto;
}
</style>
<script type="text/javascript">
function save(){
  if (valid.validate()){
  	var gcode = document.getElementById("domain.gcode").value;
	if(gcode.indexOf("_") != -1 || gcode.indexOf("&") != -1){
		alert("级别代码不能包含符号:'_'和'&'!");
		return;
	}
	myform.submit();
  }
}

</script>
</head>
<body class="pw_ab">
<%@ include file="/common/messages.inc"%>
<form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/base/grade!saveOrUpdate.action">
	<ww:hidden name="domain.gid" id="domain.gid" />
	<ww:hidden name="domain.siteId" id="domain.siteId" />
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span><ww:if test="domain.gid!=null">编辑级别</ww:if><ww:else>新建级别</ww:else></span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:250px;overflow-y:auto;">
        	<ul class="pw-con-input_ab">
            	<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">级别代码：</span>
                    	<ww:textfield id="domain.gcode" name="domain.gcode" cssClass="textField_ab textField-w191_ab required max-length-20"></ww:textfield>
                    </label>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">级别名称：</span>
                    	<ww:textfield id="domain.gname" name="domain.gname" cssClass="textField_ab textField-w191_ab required max-length-40"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">级别等级：</span>
                    	<ww:textfield id="domain.gnumber" name="domain.gnumber" cssClass="textField_ab textField-w191_ab validate-number max-value-10000"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">备注：</span>
                    	<ww:textfield id="domain.gremark" name="domain.gremark" cssClass="textField_ab textField-w191_ab max-length-120"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
            </ul>
            <div class="pw-con-btns_ab">
            	<input type="button" class="pwSubmit_ab" value="" onclick="save();"/>
                <input type="button" class="pwCancel_ab" value="" onclick="global_ab.fn.popWindow.removePopWindow(false);" />
            </div>
        </div>
    </div>
</form>
 <c:if test="${not empty actionMessages}">
  <c:forEach var="err" items="${actionMessages}">
	   <script>global_ab.fn.popWindow.removePopWindow(false);</script>
  </c:forEach> 
</c:if>
</body>
</html>
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

var valid = new Validation('myform',{immediate:true});
//]]>
</script>

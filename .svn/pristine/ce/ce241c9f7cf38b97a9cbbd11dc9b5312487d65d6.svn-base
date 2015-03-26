<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<!DOCTYPE HTML>
<html>
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-con-input-item_ab .lab-pw-tit_ab {
	width:80px;
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
  	myform.submit();
  }
}

</script>
</head>
<body class="pw_ab" style="overflow:hidden;">
<%@ include file="/common/messages.inc"%>
<form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/base/ftpService!saveOrUpdate.action">
	<ww:hidden name="domain.id" id="domain.id" />
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span><ww:if test="${domain.id}">编辑</ww:if><ww:else>新建</ww:else></span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:260px;">
        	<ul class="pw-con-input_ab">
            	<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">服务器ip：</span>
                    	<ww:textfield id="domain.ftpIp" name="domain.ftpIp" cssClass="textField_ab textField-w191_ab required max-length-24"></ww:textfield>
                    </label>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">服务器端口：</span>
                    	<ww:textfield id="domain.port" name="domain.port" cssClass="textField_ab textField-w191_ab required validate-integer max-value-100000"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab  ico-003_ab" />
                    	<span class="lab-pw-tit_ab">服务器类型：</span>
						<ww:select name="domain.isFtp" list="trueOfFalseMap1" cssClass="select_ab select-w193_ab" />
                    </label>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">用户名：</span>
                    	<ww:textfield id="domain.userName" name="domain.userName" cssClass="textField_ab textField-w191_ab max-length-20"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">密码：</span>
                    	<ww:password id="domain.passWord" name="domain.passWord" show="true" cssClass="textField_ab textField-w191_ab max-length-20"></ww:password>
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

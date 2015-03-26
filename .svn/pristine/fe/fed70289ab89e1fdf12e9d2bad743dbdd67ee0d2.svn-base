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
<script type='text/javascript' src='/dwr/interface/identityManagerService.js'></script>
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
  	var icode = document.getElementById("domain.icode").value;
	if(icode.indexOf("_") != -1 || icode.indexOf("&") != -1){
		alert("身份代码不能包含符号:'_'和'&'!");
		return;
	}
	myform.submit();
  }
}

</script>
</head>
<body class="pw_ab">
<%@ include file="/common/messages.inc"%>
<form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/base/identity!saveOrUpdate.action">
	<ww:hidden name="domain.iid" id="domain.iid" />
<ww:hidden name="domain.siteId" id="domain.siteId" />
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span><ww:if test="${domain.iid}">编辑身份</ww:if><ww:else>新建身份</ww:else></span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:210px;">
        	<ul class="pw-con-input_ab">
            	<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">身份代码：</span>
                    	<ww:textfield id="domain.icode" name="domain.icode" cssClass="textField_ab textField-w191_ab required max-length-20"></ww:textfield>
                    </label>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">身份名称：</span>
                    	<ww:textfield id="domain.iname" name="domain.iname" cssClass="textField_ab textField-w191_ab required max-length-24"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">排序：</span>
                    	<ww:textfield id="domain.orderno" name="domain.orderno" cssClass="textField_ab textField-w191_ab validate-number max-value-50"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">备注：</span>
                    	<ww:textfield id="domain.iremark" name="domain.iremark" cssClass="textField_ab textField-w191_ab max-length-60"></ww:textfield>
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
	   <script>window.top.document.all.main_frame.src="${ctx}/base/identity.action";global_ab.fn.popWindow.removePopWindow(false);</script>
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

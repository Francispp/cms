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
<script type='text/javascript' src='/dwr/interface/menuResourceService.js'></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-con-input-item_ab .lab-pw-tit_ab {
	width:170px;
	display:block;
	float:left;
	text-align:right;
	height:22px;
	line-height:22px;
	color:#333;
}
.pw-w417_ab {
	width:580px;
}
.pw-con-input_ab {
	width:500px;
	margin:0 auto;
}
</style>
<script type="text/javascript">
function save(){
  if (valid.validate()) {
	myform.submit();
  }
}
</script>
</head>
<body class="pw_ab">
<%@ include file="/common/messages.inc"%>
<form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/cms/cmsCache!saveOrUpdate.action">
	<ww:hidden name="domain.id" id="domain.id" />
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span><ww:if test="%{domain.id!=null}">编辑缓存</ww:if><ww:else>新建缓存</ww:else></span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:270px;overflow-y:auto;">
        	<ul class="pw-con-input_ab">
            	<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">缓存名称：</span>
                    	<ww:textfield id="domain.cacheName" name="domain.cacheName" cssClass=" textField_ab textField-w191_ab required max-length-40"></ww:textfield>
                    </label>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">缓存管理类的bean的id：</span>
                    	<ww:textfield id="domain.manageClassPath" name="domain.manageClassPath" cssClass="textField_ab textField-w191_ab required max-length-40"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">初始化缓存的方法名：</span>
                    	<ww:textfield id="domain.initMethodName" name="domain.initMethodName" cssClass="textField_ab textField-w191_ab required max-length-40"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">清除掉所有缓存的方法名：</span>
                    	<ww:textfield id="domain.removeAllMethodName" name="domain.removeAllMethodName" cssClass="textField_ab textField-w191_ab required max-length-40"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">获取单个缓存的方法名：</span>
                    	<ww:textfield id="domain.getMethodName" name="domain.getMethodName" cssClass="textField_ab textField-w191_ab required max-length-40"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">获取所有缓存的方法名：</span>
                    	<ww:textfield id="domain.getAllMethodName" name="domain.getAllMethodName" cssClass="textField_ab textField-w191_ab required max-length-40"></ww:textfield>
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
</body>
 <c:if test="${not empty actionMessages}">
  <c:forEach var="err" items="${actionMessages}">
	   <script>global_ab.fn.popWindow.removePopWindow(false);</script>
  </c:forEach> 
</c:if>
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
	};
})();

var valid = new Validation('myform',{immediate:true});
//]]>
</script>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="邮件模板管理" />
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
	height:600px;
	
}
</style>

<script type="text/javascript">
function save(){
 if(valid.validate()){
	 myform.submit();
 }
 else{
	dyniframesizeforall("main_frame");
 }
}

</script>
</head>
<body class="pw_ab" style="overflow:hidden;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<form class="pw-borderOut_ab pw-w650_ab" method="post" action="/base/email!sentHr.action" name="myform">
	<ww:hidden name="pageStyle" id="pageStyle" /> 
			
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>写邮件</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="overflow-y:auto;height:560px;">
        	<ul class="pw-con-input_ab pw-con-input-ex_ab" style="width:550px;">
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>收件人：</span>
                        </span> 
                  		<ww:textfield name="toEmail" cssClass="textField_ab textField-w191_ab required"></ww:textfield>
                    </label>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>抄送：</span>
                        </span> 
                  		<ww:textfield name="ccEmail" cssClass="textField_ab textField-w191_ab"></ww:textfield>
                    </label>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>邮件标题：</span>
                        </span> 
                  		<ww:textfield name="domain.templatename" cssClass="textField_ab textField-w191_ab required max-length-100"></ww:textfield>
                    </label>
                </li>
                
            </ul>
            
          <div class=" artEdit-frame-pluInBg_ab" style="border:none;padding: 5 20px;width:540px;height:350px;" >
    	<div class="artEdit-frame-pluIn_ab" style="width:530px;height:350px;">
<ww:textarea id="bodyField" name="domain.templatecontent" cssClass="max-length-240"/>
        		
        </div>
            </div>
            <div class="fn-clear"></div>
            <div class="pw-con-btns_ab">
			
				<input type="button" class="pwSubmit_ab" value="" onclick="javascript:save();"/>		
            	<input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
		 		
					
					
            </div>
       
       
        
    </div>
</form>


<c:if test="${not empty actionMessages}">
	<c:forEach var="err" items="${actionMessages}">
		<script>
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

var fckEditor = new FCKeditor("domain.templatecontent", "100%", "350px", "Email");
fckEditor.Config["FullPage"]=false;
fckEditor.BasePath = "<%=request.getContextPath ()%>/common/fckeditor/";
fckEditor.ReplaceTextarea();
</script>
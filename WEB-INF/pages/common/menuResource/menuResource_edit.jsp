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
  	var pid = document.getElementById("domain_pid").value;
	var icoPath = document.getElementById("domain_icoPath").value;
	if (pid != "" && icoPath == "") {
		var img = document.getElementById("lab-pw-ico_ab").src;
  		img = img.substring(img.lastIndexOf("/") + 1, img.length);
		document.getElementById("domain_icoPath").value = img;
	};
	
  	var menuCode = document.getElementById("domain_menuCode").value;
	var id = ${domain.id} + "";
	myform.submit();
  /**	menuResourceService.menuCodeIsUnique(id, menuCode, {
    	callback:
		function (args) {
			if (args == 0) {
				alert("菜单代码重复,必须唯一!");
				document.getElementById("domain_menuCode").focus();
				return;	
			}
  			
		},
    	errorHandler:
		function(mess){}
	});
  **/
  }
}
</script>
</head>
<body class="pw_ab" style="overflow:hidden;">
<%@ include file="/common/messages.inc"%>
<form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/base/menuResource!saveOrUpdate.action">
	<ww:hidden name="domain.id" id="domain_id" />
	<ww:hidden name="domain.icoPath" id="domain_icoPath" />
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span><ww:if test="domain.id!=null">编辑菜单</ww:if><ww:else>新建菜单</ww:else></span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:390px;overflow-y:auto;">
        	<ul class="pw-con-input_ab">
            	<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">菜单代码：</span>
                    	<ww:textfield id="domain_menuCode" name="domain.menuCode" cssClass="textField_ab textField-w191_ab required max-length-32"></ww:textfield>
                    </label>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">菜单名称：</span>
                    	<ww:textfield id="domain_menuName" name="domain.menuName" cssClass="textField_ab textField-w191_ab required max-length-16"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">所属父菜单：</span>
                    	<ww:select list="topMenuResource" name="domain.pid" listKey="id" listValue="menuName" cssClass="select_ab select-w193_ab" onchange="isParentChange(this.value);" headerKey="" headerValue="" ></ww:select>	
                    </label>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">权限级别：</span>
                    	<ww:select list="trueOfFalseMap2" name="domain.grade" cssClass="select_ab select-w193_ab"></ww:select>	
                    </label>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">是否显示：</span>
                    	<ww:select list="trueOfFalseMap1" name="domain.isView" cssClass="select_ab select-w193_ab"></ww:select>	
                    </label>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">一级URL：</span>
                    	<ww:textfield id="domain_url" name="domain.url" cssClass="textField_ab textField-w191_ab max-length-100"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">二级URL：</span>
                    	<ww:textfield id="domain_subUrl" name="domain.subUrl" cssClass="textField_ab textField-w191_ab max-length-100"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li><li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">三级URL：</span>
                    	<ww:textfield id="domain_threedUrl" name="domain.threedUrl" cssClass="textField_ab textField-w191_ab max-length-100"></ww:textfield>
                    </label>
                    <div class="fn-clear"></div>
                </li>
                <li class="pw-con-input-item_ab" id="li_ico">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">菜单图标：</span>
						<ww:if test="%{domain.icoPath ==null or domain.icoPath==''}">
							<img src="${default_imagepath}/ico_004_floppy.gif" class="ico_ab ico-004_ab" style="cursor:pointer;" id="lab-pw-ico_ab"/>
						</ww:if>
						<ww:else>
							<img src="${default_imagepath}/${domain.icoPath}" class="ico_ab ico-004_ab" style="cursor:pointer;" id="lab-pw-ico_ab"/>
						</ww:else>
                        <span class="lab-pw-tip_ab">（点击选择图标）</span>
                    </label>
                    <div class="fn-clear"></div>
                </li>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">排序号：</span>
                    	<ww:textfield id="domain_orderNo" name="domain.orderNo" cssClass="textField_ab textField-w191_ab validate-number"></ww:textfield>
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
var icoPath;
//<![CDATA[
global_ab.init.popwindowPage_fn();
(function()
{
	var closeBtn = document.getElementById("pwClose_ab");
	var imgBtn = document.getElementById("lab-pw-ico_ab");
	closeBtn.onclick = function()
	{
		global_ab.fn.popWindow.removePopWindow(false);
	};
	imgBtn.onclick = function()
	{
		icoPath = showModalDialog("${ctx}/base/menuResource!selectIco.action",window,"dialogWidth:620px;dialogHeight:530px;scroll=yes;help:no;status:yes;center:true");
		if(icoPath != "" && icoPath != undefined){
			document.getElementById("domain_icoPath").value = icoPath;	
			imgBtn.src="${default_imagepath}/" + icoPath;
		}
	};	
})();

function isParentChange (args) {
	if (args == "") {
		document.getElementById("li_ico").style.display="none";	
		//document.getElementById("li_ico_bluck").style.display="block";	
	}else{
		document.getElementById("li_ico").style.display="block";
		//document.getElementById("li_ico_bluck").style.display="none";	
	}
	
}

var valid = new Validation('myform',{immediate:true});
if(${domain.pid} + "" == ""){
	document.getElementById("li_ico").style.display="none";
	//document.getElementById("li_ico_bluck").style.display="block";	
}else{
	document.getElementById("li_ico").style.display="block";
	//document.getElementById("li_ico_bluck").style.display="none";	
}
//]]>
</script>

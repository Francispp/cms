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
<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/siteDistributionService.js'></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-con-input-item_ab .lab-pw-tit_ab {
	width:70px;
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
.textField_ab1 {
	height:60px;
	border:1px solid #999;
	text-indent:5px;
	color:#333;
}
</style>
<script type="text/javascript">
  function save(){
  	if (valid.validate()) {
  		if (valid.validate()){
  		  	var dtype = document.getElementById("domain.dtype").value;
  			var id = ${domain.id} + "";
  		  	siteDistributionService.resTypeIsUnique(id, ${siteId}, dtype, {
  		    	callback:
  				function (args) {
  					if (args == 0) {
  						alert("该分发类型已经存在,不可以重复添加!");
  						document.getElementById("domain.dtype").focus();
  						return;	
  					}
  		  			var keys = "";
          			keys = getSelectedID();
          			document.getElementById("keys").value = keys;
  					myform.submit();
  				},
  		    	errorHandler:
  				function(mess){}
  			});
  		  }
  	}   
  }
</script>
</head>
<body class="pw_ab">
<%@ include file="/common/messages.inc"%>
<form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/cms/siteDistribution!saveOrUpdate.action">
	<ww:hidden name="domain.id" id="domain.id" />
	<ww:hidden name="keys" id="keys" />
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span><ww:if test="${domain.id}">编辑</ww:if><ww:else>新建</ww:else></span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:298px;overflow-y:auto">
        	<ul class="pw-con-input_ab" >
            	<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">分发类型：</span>
						<ww:action name="selectlist!selectList" namespace="/cms" id="ins_Type"><ww:param name="key">site_resource_type</ww:param></ww:action>
   			 			<ww:select list="#attr.ins_Type.selectList.options" name="domain.dtype" listKey="key" listValue="value" cssClass="textField_ab textField-w191_ab">
   			 			</ww:select>
                    </label>
                </li>
				<ww:iterator value="ftpList" status="rowstatus">
					<li class="pw-con-input-item_ab">
	                    <label class="lab-pw_ab">
	                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
	                    	<span class="lab-pw-tit_ab"><ww:if test="#rowstatus.index == 0">分发服务器:</ww:if></span>
							<ww:if test="checked == 1">
								<input type="checkbox" name="_selectitem" value="<ww:property value = 'id'/>" class="checkbox_ab" checked="checked" /><span><ww:property value = "ftpIp"/></span>
							</ww:if>
							<ww:else>
								<input type="checkbox" name="_selectitem" value="<ww:property value = 'id'/>" class="checkbox_ab" /><span><ww:property value = "ftpIp"/></span>
							</ww:else>
	                    </label>
	                    <div class="fn-clear"></div>
	                </li>		
				</ww:iterator>
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab" />
                    	<span class="lab-pw-tit_ab">备注：</span>
						<ww:textfield id="domain.remark" name="domain.remark" cssClass="textField_ab textField-w191_ab max-length-120"></ww:textfield>
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
	   <script>window.top.document.all.main_frame.src="${ctx}/cms/siteDistribution.action";global_ab.fn.popWindow.removePopWindow(false);</script>
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
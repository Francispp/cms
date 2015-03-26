<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="常用信息管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
	function save(){
		var valid = new Validation('myform',{immediate:true});
		if(valid.validate()){
			myform.submit();
		}
	}
	function goBack(){
		location.href='commoninfo.action?pageStyle=<ww:property value="pageStyle" />';
	}
</script>
<script type="text/javascript"> 
</script>

<style type="text/css">
.pw-w317_ab {
	width:350px;
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
	width:90%;
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
<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0" class="pw_ab" >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<form method="post" action="commoninfo!saveOrUpdate.action" name="myform" class="pw-borderOut_ab pw-w317_ab">
<ww:hidden name="pageStyle" id="pageStyle" /> 
<ww:hidden name="domain.id" id="domain.id" />
<!-- 页面主要内容 -->
<div class="pw-borderIn_ab" style="height:418px;">
	<div class="pw-head_ab">
		<div class="pw-head-tit_ab">
			<img src="/images/cybercms/ico_013_plus.gif" class="ico_ab ico-013_ab" />
			<span><ww:if test="${domain.id}">编辑常用信息</ww:if><ww:else>新建常用信息</ww:else></span>
		</div>
		<img src="/images/cybercms/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
	</div>
	<div class="pw-con_ab" style="height:380px;">
		<ul class="pw-con-input_ab pw-con-input-ex_ab">
            
			<li class="pw-con-input-item_ab">
				<label class="lab-pw_ab">
					<span class="lab-pw-tit_ab">
						<span>类型：</span>
					</span>
					<ww:hidden name="coreCommonTypeId" id="coreCommonTypeId" value="%{domain.coreCommonType.id}"/>
					${domain.coreCommonType.keyword}
			    </label>
			</li>
       		<li class="pw-con-input-item_ab">
           		<label class="lab-pw_ab">
          			<span class="lab-pw-tit_ab">
              			<span>排序：</span>
              		</span>
              		<ww:textfield name="domain.sortOrder" cssClass="required  max-length-20 textField_ab textField-w150_ab" size="40"  />
				</label> 
			</li>
			<li class="pw-con-input-item_ab">
           		<label class="lab-pw_ab">
          			<span class="lab-pw-tit_ab">
              			<span>信息代码：</span>
              		</span>
              		<ww:textfield name="domain.code" cssClass="required  max-length-20 textField_ab textField-w150_ab" size="40"  />
				</label> 
			</li>
       		<li class="pw-con-input-item_ab">
           		<label class="lab-pw_ab">
          			<span class="lab-pw-tit_ab">
              			<span>信息内容：</span>
              		</span>
              		<ww:textarea name="domain.content" cssClass="textarea-hb select-w150_ab   max-length-40" rows="6" cssStyle="height:150px;" /> 
				</label> 
			</li>
		</ul>
		<div class="fn-clear"></div>
		<div class="pw-con-btns_ab">
			<c:if test="${isEdit==true}">
				<input type="button" class="pwSubmit_ab" value="" onclick="javascript:save()"/>
			</c:if>
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
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
	<c:if test="${isEdit!=true}">
		setElementsDisabled(true);
	</c:if>
</script>
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

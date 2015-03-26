<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="用户导入" />
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
.pw-w500_ab{
	width:450px;
	height:370px;
}
</style>

<script type="text/javascript">
function closeWin(){
	parent.parent.parent.frames['main_frame'].refreshMenu();
	global_ab.fn.popWindow.removePopWindow(false);
}
</script>
</head>
<body class="pw_ab" style="overflow:hidden;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<form class="pw-borderOut_ab pw-w500_ab">
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>导入员工结果</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div style="text-align:center;" class="pw-con_ab" style="height:350px;overflow-y:auto;">
            <div>成功导入<ww:property value="#request.successCount" />条数据</div>
			<div><span style="color:red;"><ww:property value="#request.errMsg" /></span></div>
			<ww:if test="#request.failRows.size>0">
            <div>导入失败数据:</div>
			<table border="1" cellpadding="0" cellspacing="0" style="padding-left:20px;">
				<tr>
					<th>行号</th>
					<th>失败原因</th>
				</tr>
				<ww:iterator value="#request.failRows" status="itStatus">
				<tr>
					<td><ww:property/></td>
					<td><ww:property value="#request.failReasons[#itStatus.index]"/></td>
				</tr>
				</ww:iterator>
			</table>
			</ww:if>
            <div class="fn-clear"></div>
            <div class="pw-con-btns_ab">
            	<input type="button" class="pwSubmit_ab" value="" onclick="javascript:closeWin();"/>
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
		closeWin();
	}
})();
//]]>
</script>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
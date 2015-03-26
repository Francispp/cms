<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>

<c:set var="title" value="批量录入" />
<!DOCTYPE HTML>
<html>
<head>
<title>${title}</title>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<%@ include file="/common/MultiUpload.inc"%>
<script type="text/javascript">
var upload1;

		window.onload = function() {
           upload1 = new SWFUpload({
				// Backend Settings
				upload_url: "staticResource!uploads.action?siteid=${domain.siteid}",	// Relative to the SWF file (or you can use absolute paths)
				post_params: {"PHPSESSID" : "uosr56igt5a3l5sgrvd6eqee21"},

				// File Upload Settings
				file_size_limit : "102400",	// 100MB
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : "1000",
				file_queue_limit : "0",
				file_post_name : "_file",
				

				// Event Handler Settings (all my handlers are in the Handler.js file)
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				queue_complete_handler : queueComplete,
				// Button Settings
				button_image_url : "${ctx}/images/common/XPButtonUploadText_61x22.png",	// Relative to the SWF file
				button_placeholder_id : "spanButtonPlaceholder1",
				button_width: 70,
				button_height: 19,
				
				// Flash Settings
				flash_url : "${ctx}/common/MultiUpload/swfupload/swfupload.swf",
				

				custom_settings : {
					progressTarget : "fsUploadProgress1",
					cancelButtonId : "btnCancel1"
				},
				
				// Debug Settings
				debug: false
			});
	     }

</script>

</head>
<body class="pw_ab">
	
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<form id="form1" class="pw-borderOut_ab pw-w417_ab" action="staticResource!uploads.action?siteid=${domain.siteid}" method="post" enctype="multipart/form-data">
<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>批量录入</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
          <div class="pw-con_ab" style="height:300px;overflow:scroll;">
          <div style="text-align:left;margin-left:20px;"><SPAN id=spanButtonPlaceholder1></SPAN></div><input id="btnCancel1" type="button" value="取消上传" onclick="cancelQueue(upload1);" disabled="disabled" style="font-size: 8pt;color:#333;display:none;" />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<!-- 操作栏 -->

	
	<!-- 页面主要内容 -->
	
	<tr><td align="center" valign="top">
 <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" >

   <tr>
     <td align="center" >&nbsp;</td>
     </tr>
   <tr>
     <td align="center">	
	

			<table width="80%">
				<tr valign="top">
					<td width="80%">
					 <DIV class="fieldset flash" id=fsUploadProgress1></DIV>
					</td>
					
				</tr>
			</table>
	
</td></tr></table>	
</td></tr></table></div></div></form>
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

//]]>
</script>


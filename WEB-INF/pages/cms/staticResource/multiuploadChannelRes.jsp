<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="批量录入" />

<html>
<head>
<title>${title}</title>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/MultiUpload.inc"%>
<script type="text/javascript">
var upload1;

		window.onload = function() {
           upload1 = new SWFUpload({
				// Backend Settings
				upload_url: "staticResource!uploadChannelRes.action?siteid=${domain.siteid}&chnid=${domain.channelid}",	// Relative to the SWF file (or you can use absolute paths)
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
<body  nowrap topmargin="0" leftmargin="0" style="background-color:#F1F1F1;margin:0px;padding:0px;">
	
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="background-color:#dfedef;">
	<!-- 操作栏 -->

	<tr>
		<td valign="top"  height="100%">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
				  <c:if test="${isEdit==true}"> 
				  <li><SPAN id=spanButtonPlaceholder1></SPAN></li>
				  </c:if>
				    <li><input id="btnCancel1" type="button" value="取消上传" onclick="cancelQueue(upload1);" disabled="disabled" style="font-size: 8pt;color:#333" /></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<!-- 页面主要内容 -->
	
	<tr><td align="center" valign="top">
 <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#dfedef;">

   <tr>
     <td align="center" >&nbsp;</td>
     </tr>
   <tr>
     <td align="center">	
	<form id="form1" action="staticResource!uploadChannelRes.action?siteid=${domain.siteid}&chnid=${domain.channelid}" method="post" enctype="multipart/form-data">

			<table width="80%">
				<tr valign="top">
					<td width="80%">
					 <DIV class="fieldset flash" id=fsUploadProgress1></DIV>
					</td>
					
				</tr>
			</table>
	</form>
</td></tr></table>	
</body>
</html>


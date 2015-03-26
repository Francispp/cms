<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="批量录入" />

<html>
<head> 
<title>${title}</title>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/common/MultiUpload.inc"%>
<script type="text/javascript">
var upload1;

		window.onload = function() {
			upload1 = new SWFUpload({
				// Backend Settings
				upload_url: "staticResource!uploads.action?siteid=${domain.siteid}",	// Relative to the SWF file (or you can use absolute paths)
				//post_params: {"PHPSESSID" : "<?php echo session_id(); ?>"}, //设置提交参数.

				// File Upload Settings
				file_size_limit : "102400",	// 100MB
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : "1000",
				file_queue_limit : "0",

				// Event Handler Settings (all my handlers are in the Handler.js file)
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_complete_handler : uploadComplete,
				file_complete_handler : fileComplete,

				// Flash Settings
				flash_url : "${ctx}/common/MultiUpload/swfupload/swfupload.swf",	// Relative to this file (or you can use absolute paths)

				// UI Settings
				ui_container_id : "flashUI1",
				degraded_container_id : "degradedUI1",

				// Debug Settings
				debug: false
			});
			upload1.customSettings.progressTarget = "fsUploadProgress1";	// Add an additional setting that will later be used by the handler.
			upload1.customSettings.cancelButtonId = "btnCancel1";			// Add an additional setting that will later be used by the handler.
					// Add an additional setting that will later be used by the handler.

	     }

</script>
</head>
<body  nowrap topmargin="0" leftmargin="0" style="background-color:#F1F1F1;margin:0px;padding:0px;">
	
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
  </div>
</div>

<!-- 操作功能按钮条 -->
<div id="nav">
 <div>
 <ul>
  <c:if test="${isEdit==true}"> 
  <li><input type="button" value="加载文件" onclick="upload1.selectFiles()" style="font-size: 8pt;color:#333" /></li>
  </c:if>

								
  <li><input id="btnCancel1" type="button" value="取消上传" onclick="cancelQueue(upload1);" disabled="disabled" style="font-size: 8pt;color:#333" /></li>

  </ul>
  </div>
</div>
	<form id="form1" action="staticResource!uploads.action?siteid=${domain.siteid}" method="post" enctype="multipart/form-data">

		<div class="content" width="80%" >
			<table width="80%">
				<tr valign="top">
					<td width="80%" style="display:none;">
						<div id="flashUI1" style="display: none;widht:80%;">
							
							<div class="flash" id="fsUploadProgress1" style="widht:80%;background:#fff;"></div>
							<div>
								
							</div>
						</div>
						<div id="degradedUI1">
							<div class="flash" id="fsUploadProgress1">
								 <input type="file" name="anyfile2" />
							</div>
							<div>
								<input type="submit" value="Submit Files" />
							</div>
						</div>
					</td>
					
				</tr>
			</table>
		</div>
	</form>
</body>
</html>


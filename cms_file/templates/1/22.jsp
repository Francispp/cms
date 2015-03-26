
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<!DOCTYPE html>
			<html>
	<%@ include file="/common/template/head.inc"%><head>
		<title>信息表单</title>
		<%@ include file="/common/cyber_taglibs.inc"%>
		<script src="${ctx}/common/cybercms_js/global_ab.js" type="text/javascript"><!--
 --></script>
		<link href="/styles/cybercms/css.css" rel="stylesheet" type="text/css"></link>
		<style type="text/css">
			body{background:url('/images/cybercms/bg_artEdit_main.gif');}
			.artEdit-frame-inField_ab{padding:20px 20px;}
		</style>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" /></head>
<body class="artEdit_ab">
	<%@ include file="/common/messages.inc" %>
	<form action="${ctx}/cms/document!adminSaveOrUpdate.action?id=${id}&channelId=${channelId}" class="artEdit-frame_ab" id="myform" method="post" name="myform">
		<ww:hidden id="id" name="domain.id"></ww:hidden>
		<div class="artEdit-frame-header_ab"><img alt="cms" class="artEdit-logo_ab" height="30" src="/images/cybercms/pic_artEdit_logo.jpg" width="204"></img>&nbsp; <img alt="cms" class="artEdit-imgShadow_ab" height="10" src="/images/cybercms/pic_artEdit_shadow.jpg" width="962"></img></div>
		<div class="artEdit-frame-border_ab artEdit-frame-tit_ab">文章编辑<img alt="" class="artEdit-frame-close_ab" id="windowClose_ab" src="/images/cybercms/btn_close.gif"></img></div>
		<ul class="artEdit-frame-border_ab artEdit-frame-editField_ab">
			<li class="artEdit-frame-blankForIe67"><c:if test="isEdit"></c:if></li>
			<li class="artEdit-btn_ab"><a class="artEdit-btn-in_ab" href="javascript:save();"><img alt="" class="ico_ab ico-004_ab" src="/images/cybercms/ico_004_floppy.gif"></img> <span>保存草稿</span> </a></li>
			<li class="artEdit-btn_ab"><a class="artEdit-btn-in_ab" href="javascript:PublicAndRefreshView();"><img alt="" class="ico_ab ico-004_ab" src="/images/cybercms/ico_004_floppy.gif"></img> <span>直接发布</span> </a></li>
			<li class="artEdit-btn_ab"><a class="artEdit-btn-in_ab" href="javascript:goBack()"><img alt="" class="ico_ab ico-009_ab" src="/images/cybercms/ico_009_order1.gif"></img> <span>返回</span> </a></li>
			<li class="fn-clear"></li>
		</ul>
		<div class="artEdit-frame-border_ab artEdit-frame-blank_ab"><img alt="" height="6" src="/images/cybercms/pic_artEdit_lines.gif" width="100%"></img></div>
		<ul class="artEdit-frame-border_ab artEdit-frame-inField_ab" style="padding-left: 0px; padding-right: 0px">
			<div class="info-box">
				<table border="0" cellPadding="0" cellSpacing="0" class="myTab_ab">
					<tbody>
						<tr>
							<td align="right" height="30" width="150">标题<span style="color: #f00">*</span>：</td>
							<td colSpan="3"><ww:textfield cssClass="textField_ab" cssStyle="width: 400px" id="TextField_4568834444" name="domain.title"></ww:textfield></td>
						</tr>
						<tr>
							<td align="right" height="30" width="150"><span style="color: #f00"><font color="#000000">内容</font>*</span>：</td>
							<td align="right" colSpan="3" height="30" width="150"></td>
						</tr>
						<tr>
							<td colSpan="4"><ww:textarea cssClass="max-length-null" id="Html_8766110375" name="domain.body"></ww:textarea><script type="text/javascript"></script><%com.ckeditor.CKEditorConfig ckConfig_Html_8766110375 = new com.ckeditor.CKEditorConfig();ckConfig_Html_8766110375.addConfigValue("filebrowserImageUploadUrl", pageContext.getAttribute("ctx")+"uploadFile!uploadCK.action?file.fileType=image&file.docId="+request.getAttribute("id")+"&file.channelId="+request.getAttribute("channelId")+"&file.fieldName=domain.body");ckConfig_Html_8766110375.addConfigValue("filebrowserFlashUploadUrl", pageContext.getAttribute("ctx")+"uploadFile!uploadCK.action?file.fileType=flash&file.docId="+request.getAttribute("id")+"&file.channelId="+request.getAttribute("channelId")+"&file.fieldName=domain.body");%><ckeditor:replace basePath="/common/ckeditor/" config="<%=ckConfig_Html_8766110375%>" replace="domain.body"></ckeditor:replace></td>
						</tr>
						<tr>
							<td align="right" height="30" width="150">附件：</td>
							<td colSpan="3"><div class="attachment_default"> <s:if test='isEdit'><div class = "but_img" onclick="newAttachment(window.Upload_5738136169,'','','${domain.id}','${domain.site.oid}','','attachs','50');"></div></s:if>
<div id="Upload_5738136169"></div></div><script type="text/javascript"> <s:if test='isEdit'> viewAttachment(window.Upload_5738136169,'','${domain.id}','attachs');
</s:if><s:else> viewAttachmentForDetailsTemplate(window.Upload_5738136169,'','${domain.id}','attachs');
</s:else></script></td>
						</tr>
						<tr>
							<td align="left" height="30">发布人：</td>
							<td><ww:if test="domain.authorCname!=null&&domain.authorCname.toString()!=''"><cms:property escape="false" formatType="yyyy-MM-dd" isDate="false" isOffecOcx="false" value="domain.authorCname"></cms:property></ww:if></td>
							<td align="left">发布日期：</td>
							<td><ww:if test="domain.timeCreated!=null&&domain.timeCreated.toString()!=''"><cms:property escape="false" formatType="yyyy-MM-dd" isDate="true" isOffecOcx="false" value="domain.timeCreated"></cms:property></ww:if></td>
						</tr>
						<tr>
							<td colSpan="4" vAlign="top">
								<table border="0" cellPadding="0" cellSpacing="0" width="100%">
									<tbody>
										<tr>
											<td align="left">修改记录：</td>
										</tr>
										<tr>
											<td vAlign="top">
												<table cellpadding="3" cellspacing="1" class="history_dataGrid" id="History_4157122763" width="100%"><tr class="header"><td class="td_username">&#29992;&#25143;&#21517;</td><td class="td_time">&#26102;&#38388;</td></tr><%{
  LogManagerService logManagerService = (LogManagerService)ServiceLocator.getBean ("logManagerService");
  if (StringUtils.isNotBlank (request.getParameter ("id"))) {
  request.setAttribute ("_data", logManagerService.listById (request.getParameter ("pageIndex")==null?1:Integer.valueOf(request.getParameter ("pageIndex")),10,Long.valueOf (request.getParameter ("id")), "document"));
  } else { request.setAttribute ("_data", new com.cyberway.core.dao.support.Page ()); }
}
%><ww:iterator status="rowstatus" value="#request._data.result"><ww:set name="row" value="'row'"></ww:set><ww:set name="rowAlt" value="'row-alt'"></ww:set><tr class="<ww:property value='#rowstatus.odd ? #row : #rowAlt' />"><td class="cell"><ww:property value="operator"></ww:property></td><td class="cell-alt"><ww:property value="@org.apache.commons.lang.time.DateFormatUtils@format (time, 'yyyy-MM-dd HH:mm:ss')"></ww:property></td></tr></ww:iterator></table><cms:tablePager pageIndex="#request._data.CurrentPageNo" pageSize="#request._data.pageSize" recordCount="#request._data.totalCount" style="tablePager"></cms:tablePager>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</ul>
		<div class="artEdit-frame-rights_ab">Copyright by Cyberway information Technology Co.,Ltd.All Rights Reserved.2014-2015 designed by cyberway</div>
		<%@ include file="/common/foot.inc"%>
	</form>
	<script type="text/javascript"><!--
 var valid = new Validation('myform',{immediate:true});
		setElementsDisabled(!${isEdit});
		function whetherClose(){
			var domainid="${domain.id}";
			var isCloseWindow="${isCloseWindow}";
			if(domainid!=""&&isCloseWindow!=""){
				try {
					window.opener.ECSideUtil.reload('myTable');
				}catch(e){
					window.close();
				}
				window.close();
			}
		}
		whetherClose();
 --></script>
	<script type="text/javascript"><!--
 global_ab.init.btnAct_forIe6("li");
		global_ab.init.btnAct_forIe6("div");
		
		(function(){
			document.getElementById("windowClose_ab").onclick = function(){
				window.close();
			}
		})();
 --></script>
</body>
</html>
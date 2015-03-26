<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<html>
	<%@ include file="/common/template/head.inc"%><head>
		<title>信息表单</title>
		<%@ include file="/common/cyber_taglibs.inc"%><script
			src="${ctx}/common/cybercms_js/global_ab.js" type="text/javascript"><!--
 --></script>
		<link href="/styles/cybercms/css.css" rel="stylesheet" type="text/css"></link>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
	</head>
	<body class="artEdit_ab">
		<style type="text/css">
body {
	background: url('/images/cybercms/bg_artEdit_main.gif');
}
.artEdit-frame-inField_ab {
	padding: 20px 20px;
}
</style>
		<%@ include file="/common/messages.inc"%>
		<form
			action="${ctx}/cms/document!adminSaveOrUpdate.action"
			class="artEdit-frame_ab" method="post" name="myform">
			<ww:hidden id="id" name="domain.id"></ww:hidden>
			<div class="artEdit-frame-header_ab">
				<img alt="cms" class="artEdit-logo_ab" height="30"
					src="/images/cybercms/pic_artEdit_logo.jpg" width="204"></img>
				&nbsp;
				<img alt="cms" class="artEdit-imgShadow_ab" height="10"
					src="/images/cybercms/pic_artEdit_shadow.jpg" width="962"></img>
			</div>
			<div class="artEdit-frame-border_ab artEdit-frame-tit_ab">
				评论管理
				<img alt="" class="artEdit-frame-close_ab" id="windowClose_ab"
					src="/images/cybercms/btn_close.gif"></img>
			</div>
			<ul class="artEdit-frame-border_ab artEdit-frame-editField_ab">
				<li class="artEdit-frame-blankForIe67">
					<c:if test="${isEdit}"></c:if>
				</li>
				<!--<li style="margin-left:870" class="artEdit-btn_ab artEdit-btn-w3letters_ab">
				<a class="artEdit-btn-in_ab" href="javascript:location='/base/message!list.action';">
				<img alt="" class="ico_ab ico-017_ab" src="/images/cybercms/ico_017_trashFull.gif"></img> 
				<span>返回</span> </a></li>
				-->
			<li class="fn-clear"></li>
			</ul>
			<div class="artEdit-frame-border_ab artEdit-frame-blank_ab">
				<img alt="" height="6" src="/images/cybercms/pic_artEdit_lines.gif"
					width="100%"></img>
			</div>
			<ul class="artEdit-frame-border_ab artEdit-frame-inField_ab"
				style="padding-left: 0px; padding-right: 0px">
				<div class="info-box">
					<table border="0" cellPadding="0" cellSpacing="0" class="myTab_ab"
						width="95%">
						<tbody>
							<tr>
								<td align="right" height="30" width="150">
									<font color="#333333">评论人</font>：
								</td>
								<td colSpan="3">
									${domain.face }&nbsp;
								</td>
							</tr>
							<tr>
								<td align="right" height="30" width="150">
									<font color="#333333">评论信息</font>：
								</td>
								<td colSpan="3">
									${domain.content }&nbsp;
								</td>
							</tr>
							<tr>
								<td align="right" height="30" width="150">
									评论时间：
								</td>
								<td colSpan="3">
									<ww:date name="domain.createtime" format="yyyy-MM-dd hh:mm:ss"/>&nbsp;
								</td>
							</tr>
							<tr>
								<td align="right" height="30" width="150">
									<font color="#333333">IP地址</font>：
								</td>
								<td colSpan="3">
									${domain.ipInfo }&nbsp;
								</td>
							</tr>
							<tr>
								<td align="right" height="30" width="150">
									<font color="#000000">产品信息</font>：
								</td>
								<td colSpan="3">
									${domain.productInfo }&nbsp;
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</ul>
			<div class="artEdit-frame-rights_ab">
				版权所有 &copy; 1996-2014 Cyberway 赛百威信息科技
			</div>
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

 (function()
 {
 	document.getElementById("windowClose_ab").onclick = function()
 	{
 		window.close();
 	}
 })();
 --></script>
	</body>
</html>
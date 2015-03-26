<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<!doctype html>
<html>
	<head>
		<title>初始信息</title>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/css/base.css" rel="stylesheet" />
		<link href="/css/konka.company.css" rel="stylesheet" />
		<link href="/css/personar.css" rel="stylesheet" />
		<link rel="stylesheet" href="/css/hr.jobs.css" />
<style type="text/css">
.codeRound {
	color: #06F;
	font-size: 12px;
	vertical-align: top;
	display: inline-block;
	margin-top: 5px;
	cursor: pointer;
}
.codeRound:hover {
	color: #F00;
}
.mr_red_prompt {
	font-size: 12px;
	color: #F00 !important;
	margin-left: 8px;
	vertical-align: top;
	height: 22px;
	margin-top: 6px;
	display: inline-block;
}
.ps_reset_int .errorMessage{
			font-size: 12px;
			color: #F00;
			margin-left: 80px;
			vertical-align: top;
			height: 22px;
			margin-top: 2px;
			display: block;
			width: 340px;
			text-align: left;
		}
	.sr_up_pic{width:160px;height:160px;}
	.hrCommain{float:none;}
	.mrCommain, .srCommain{float:none;}
</style>
		<script src="/common/cms_js/util.js" type="text/javascript"></script>
		<script type="text/javascript" src="/common/jquery/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="/common/jquery/jquery.form.min.js"></script>
		<script src="/scripts/hr.page.js" type="text/javascript"></script>
		<script src="/dwr/util.js" type="text/javascript"></script>
		<script src="/dwr/engine.js" type="text/javascript"></script>
		<script src="/dwr/interface/areaService.js" type="text/javascript"></script>
		<script language="javascript" src="/common/datepicker/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="/scripts/konka.company.js"></script>
		<script type="text/javascript" src="/common/validation/prototype.js"></script>
		<script src="/scripts/validation.js" type="text/javascript"></script>
		<script src="/common/cms_js/cms.js" type="text/javascript" language="javascript"></script>
		<script src="/common/attachment/attachment.js" type="text/javascript" language="javascript"></script>
		<script src="/common/attachment/xml.js" type="text/javascript" language="javascript"></script>
		<script>
			function editResume(mode){
				jQuery(".ps_tabCur").removeClass("ps_tabCur");
				jQuery("#resumeLink").addClass("ps_tabCur");
				var obj = {};
				if(mode==1){
					obj.channelId=136;
					obj.templateId=375;
				}else{
					obj.channelId=135;
					obj.templateId=290;
				}
				gotoResume(obj);
			}
		</script>
		<c:if test="${success != null}">
			<script type="text/javascript">
				alert('${success}');
			</script>
		</c:if>
	</head>
	<body class="">
		<div class="kanKaBox">
			<%@ include file="/cms_file/templates/1/company_header_include.jsp"%>
			<div class="content">
				<div class="personalBox">
					<%@ include file="personLeft.jsp"%>
					<div class="ps_con">
						<ul class="ps_tab">
							<li>
								<a href="#">个人资料</a>
							</li>
						</ul>
						<div class="ps_conInfo">
							<form id="pwdForm" name="pwdForm" action="/webuser!saveInitInfo.action" method="post">
								<div class="clear"></div>
								<p class="ps_resetPas">
									个人信息补完
								</p>
								<div class="ps_resetCon">
									<div class="ps_reset_int">
										<label>
											<span>邮箱：</span>
											<input name="domain.email" id="domain_email" class="rePase_text required validate-email" value="${domain.email}"/>
											<ww:fielderror><ww:param>domain.email</ww:param></ww:fielderror>
										</label>
									</div>
									<div class="ps_reset_int">
										<label>
											<span>手机：</span>
											<input name="domain.mobilephone" id="domain_mobilephone" class="rePase_text required validate-mobile-phone" value="${domain.mobilephone}"/>
											<ww:fielderror><ww:param>domain.mobilephone</ww:param></ww:fielderror>
										</label>
									</div>
									<div class="ps_reset_int">
										<label>
											<span>原密码：</span>
											<input type="password" name="domain.loginpwd" id="domain_loginpwd" class="rePase_text required" maxlength="20"/>
											<ww:fielderror><ww:param>domain.loginpwd</ww:param></ww:fielderror>
										</label>
									</div>
									<div class="ps_reset_int">
										<label>
											<span>新密码：</span>
											<input type="password" id="newLoginpwd" name="newLoginpwd" class="rePase_text required min-length-6 validate-not-equals-domain_loginpwd" maxlength="20"/>
											<ww:fielderror><ww:param>newLoginpwd</ww:param></ww:fielderror>
											<!--<span class="ps_pasPromt">密码强度：低，建议提高密码复杂度。</span>-->
										</label>
									</div>
									<div class="ps_reset_int">
										<label>
											<span>确认新密码：</span>
											<input type="password" class="rePase_text required validate-equals-newLoginpwd" maxlength="20"/>
										</label>
									</div>
									<div class="ps_reset_int">
										<label>
											<span>验证码：</span>
											<input type="text" id="validateCode" name="validateCode" class="rePase_text required" />
											<span><img id="validatePic" src="/validateCode.jsp" style="margin-top: -8px;" alt="">
											</span>
											<a onclick="javascript:document.getElementById('validatePic').src='/validateCode.jsp?_='+new Date().toString()"
												class="codeRound">看不清？</a>
											<ww:fielderror ><ww:param>validateCode</ww:param></ww:fielderror>
										</label>
									</div>
									<input type="button" onclick="savePwd()"
										style="margin-left: 80px; background: url(/images/fp_sure_btn.png) no-repeat; width: 126px; height: 36px; border: none; color: #fff; font-size: 16px;"
										value="修改" class="setPassBtn" />
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="wihte_03"></div>
			<%@ include file="/cms_file/templates/1/footer_include.jsp"%>
		</div>
	</body>
		<script type="text/javascript">
			var valid = new Validation('pwdForm', {immediate :true});
			function savePwd() {
				if (valid.validate())
					document.pwdForm.submit();
			}
		</script>
</html>

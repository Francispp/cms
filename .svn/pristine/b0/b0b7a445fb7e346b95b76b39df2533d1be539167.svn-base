<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<link href="/css/base.css" rel="stylesheet" />
		<link href="/css/layout.css" rel="stylesheet" />
		<title>密码找回</title>
		<script type="text/javascript" src="/common/validation/prototype.js"></script>
		<script type="text/javascript" src="/scripts/validation.js"></script>
		<script type="text/javascript">
<c:if test="${success != null}">
	alert('${success}');
	window.location.href="/webuser!login.action";
</c:if>
</script>
		<style type="text/css">
.mr_red_prompt {
	font-size: 12px;
	color: #F00 !important;
	margin-left: 6px;
	vertical-align: top;
	height: 22px;
	margin-top: 2px;
	display: inline-block;
}

.login_int .errorMessage,#advice-validate-email-userEmail {
	font-size: 12px;
	color: #F00 !important;
	margin-left: 80px;
	vertical-align: top;
	height: 22px;
	margin-top: 2px;
	display: block;
	width: 340px;
	text-align: left;
}
</style>
	</head>
	<body>
		<div class="kanKaBox loginPage">
			<div class="content loginCon">
				<div class="loginTitel">
					<p class="passCred">
						<a href="/"><img src="/images/ico_group_logo.png" alt="logo" />
						</a>
					</p>
				</div>
				<form id="formToSubmit" name="formToSubmit"
					action="/webuser!findPwdByEmail.action" method="post">
					<div class="loginBox">
						<div class="re_top_bg"></div>
						<div class="re_mid_bg fd_mid_bg">
							<p class="find_title">
								密码找回
							</p>
							<div class="re_leftPic">
								<img src="/images/re_left_pic.jpg" alt="" />
							</div>
							<div class="fp_Conbox">
								<p class="ps_findDetaid">
									请输入你注册的邮箱找回密码
								</p>
								<div class="login_int">
									<label>
										<span>邮箱：</span>
										<input type="text" name="domain.email" id="domain_email"
											value="${domain.email }"
											class="reUser_text required validate-email" theme="simple" />
										<ww:fielderror>
											<ww:param>domain.email</ww:param>
										</ww:fielderror>
									</label>
								</div>
								<div class="login_int">
									<label>
										<span>验证码：</span>
										<input type="text" id="validateCode" name="validateCode"
											class="recode_text required" />
										<span><img id="validatePic" src="/validateCode.jsp"
												style="margin-top: -8px;" alt="">
										</span>
										<a
											onclick="javascript:document.getElementById('validatePic').src='/validateCode.jsp?_='+new Date().toString()"
											class="codeRound">看不清？</a>
										<ww:fielderror>
											<ww:param>validateCode</ww:param>
										</ww:fielderror>
									</label>
								</div>
								<div class="login_int">
									<input type="button" onclick="submitForm()" class="find_btn"
										value="提交" />
								</div>
							</div>
						</div>
						<div class="re_bot_bg"></div>
					</div>
				</form>
				<div class="wihte_01"></div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		var valid = new Validation('formToSubmit', {immediate :true});
		function submitForm() {
			if (valid.validate()) {
				document.formToSubmit.submit();
			}
		}
	</script>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ include file="/common/template/public.inc"%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/css/base.css" rel="stylesheet" />
		<link href="/css/layout.css" rel="stylesheet" />
		<link href="/css/hr.jobs.css" rel="stylesheet" />
		<script type="text/javascript" src="/common/validation/prototype.js"></script>
		<script src="/scripts/validation.js" type="text/javascript"></script>
		<title>康佳集团</title>
	</head>
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
		#advice-validate-mobile-phone-domain_mobilephone,.login_int .errorMessage,#advice-validate-email-domain_email{
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
	</style>
	<body>
		<div class="kanKaBox loginPage">
			<div class="content loginCon">
				<div class="loginTitel">
					<p class="passCred">
						<a href="/"><img src="/images/ico_group_logo.png" alt="logo" /></a>
					</p>
				</div>
				<div class="loginBox">
					<div class="re_top_bg"></div>
					<div class="re_mid_bg">
						<p class="wel_regin">
							欢迎注册康佳通行证
						</p>
						<form id="registerForm" name="registerForm" method="post"
							action="/webuser!registerSave.action">
							<div class="re_leftPic">
								<img src="/images/re_left_pic.jpg" alt="" />
							</div>
							<div class="re_Conbox">
								<div class="login_int">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>登录账号：</span>
										<input id='loginname' name='domain.loginname'  type="text" value="${domain.loginname }" class='reUser_text required max-length-16 validate-alphanum' />
										<ww:fielderror><ww:param>domain.loginname</ww:param></ww:fielderror>
									</label>
								</div>
								<div class="login_int">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>邮箱：</span>
										<input id="domain_email" name="domain.email" type="text" value="${domain.email }" class="reUser_text required validate-email" />
										<ww:fielderror><ww:param>domain.email</ww:param></ww:fielderror>
									</label>
								</div>
								<div class="login_int">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>手机号码：</span>
										<input type="text" id="domain_mobilephone" name="domain.mobilephone" value="${domain.mobilephone }" class="reUser_text required validate-mobile-phone" />
									</label>
								</div>
								<div class="login_int login_sex">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>性别：</span>
										<input type="radio" checked="checked" value="男" name="domain.sex" />
										&nbsp;男&nbsp;
										<input type="radio" value="女" name="domain.sex" />
										&nbsp;女
									</label>
								</div>
								<div class="login_int">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>密码：</span>
										<input id='loginpwd' type="password" name='domain.loginpwd' class='reUser_text required min-length-6' />
										<p class="re_prompt">
											最少6个字符，建议使用数字及字母的组合
										</p>
									</label>
								</div>
								<div class="login_int">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>确认密码：</span>
										<input id="loginpwdCheck" type="password" class="reUser_text required validate-equals-loginpwd" />
									</label>
								</div>
								<div class="login_int">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>验证码：</span>
										<input type="text" id="validateCode" name="validateCode" class="recode_text required" />
										<span><img id="validatePic" src="/validateCode.jsp" style="margin-top: -8px;" alt="">
										</span>
										<a onclick="javascript:document.getElementById('validatePic').src='/validateCode.jsp?_='+new Date().toString()"
											class="codeRound">看不清？</a>
										<ww:fielderror><ww:param>validateCode</ww:param></ww:fielderror>
									</label>
								</div>
								<div class="login_ask">
									<p class="re_ask_p">
										您是否购买过康佳产品？
									</p>
									<label class="reask">
										<input type="radio" value="多次购买过" name="reask" />
										&nbsp;多次购买过&nbsp;
									</label>
									<label class="reask">
										<input type="radio" value="一次购买过多件" name="reask" />
										&nbsp;一次购买过多件
									</label>
									<label class="reask">
										<input type="radio" value="买过一件" name="reask" />
										&nbsp;买过一件
									</label>
									<br/>
									<label class="reask">
										<input type="radio" value="没有买过" name="reask" />
										&nbsp;没有买过
									</label>
									<label class="reask re_noeeBy">
										<input type="radio" value="买过不满意" name="reask" />
										&nbsp;买过不满意
									</label>
								</div>
								<label class="re_agreed">
									<input checked="checked" type="checkbox" id="regist_check"/>
									&nbsp;我接受<a href="/zctk">《康佳注册用户服务条款》</a>
								</label>
								<div class="login_int">
									<input onclick="register()" type="button" class="regist_btn" value="同意以上协议，马上注册" />
								</div>
							</div>
						</form>
					</div>
					<div class="re_bot_bg"></div>
				</div>
				<div class="wihte_01"></div>
			</div>
			<%@ include file="/cms_file/templates/1/footer_include.jsp"%>
		</div>
	</body>
	<script type="text/javascript">
		var valid = new Validation('registerForm', {immediate :true});
		function register() {
			if (valid.validate()) {
				if ($('regist_check').checked != true) {
					alert('请先接受康佳注册用户服务条款!');
					return;
				}
				document.registerForm.submit();
			}
		}
	</script>
</html>

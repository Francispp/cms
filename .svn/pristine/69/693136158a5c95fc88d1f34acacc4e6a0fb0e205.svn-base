<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<!doctype html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/css/base.css" rel="stylesheet" />
		<link href="/css/layout.css" rel="stylesheet" />
		<title>修改密码</title>
		<script type="text/javascript" src="/common/validation/prototype.js"></script>
		<script src="/scripts/validation.js" type="text/javascript"></script>
		<c:if test="${success != null}">
		<script type="text/javascript">
			alert('${success}');
			window.location.href = "/webuser!login.action";
		</script>
		</c:if>
	</head>
	<style type="text/css">
	.mr_red_prompt {
		font-size: 12px;
		color: #F00 !important;
		margin-left: 8px;
		vertical-align: top;
		height: 22px;
		margin-top: 6px;
		display: inline-block;
	}
	.login_int .errorMessage{
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
					<div class="re_mid_bg" style="height:500px;">
						<p class="wel_regin">
							重置密码
						</p>
						<form id="registerForm" name="registerForm" method="post" action="/webuser!resetpwd.action">
							<input type="hidden" name="domain.oid" value="${oid != null ? oid : domain.oid}">
							<input type="hidden" name="emailResetpwd" value="true">
							<div class="re_leftPic">
								<img src="/images/re_left_pic.jpg" alt="" />
							</div>
							<div class="re_Conbox">
								<div class="login_int">
									<label>&nbsp;</label>
								</div>
								<div class="login_int">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>密码：</span>
										<input id='newLoginpwd' type="password" name='newLoginpwd' class='reUser_text required min-length-6' />
										<p class="re_prompt">
											最少6个字符，建议使用数字及字母的组合
										</p>
									</label>
								</div>
								<div class="login_int">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>确认密码：</span>
										<input id="loginpwdCheck" type="password" class="reUser_text required validate-equals-newLoginpwd" />
									</label>
								</div>
								<div class="login_int">
									<label>
										<span><i class="lg_redStar">*&nbsp;</i>验证码：</span>
										<input type="text" id="validateCode" name="validateCode" class="recode_text required" />
										<span><img id="validatePic" src="/validateCode.jsp" style="margin-top: -8px;" alt="">
										</span>
										<a onclick="javascript:document.getElementById('validatePic').src='/validateCode.jsp?_='+new Date().toString()" class="codeRound">看不清？</a>
										<ww:fielderror><ww:param>validateCode</ww:param></ww:fielderror>
									</label>
								</div>
								<div class="login_int">
									<input onclick="resetpwd()" type="button" class="regist_btn" value="确认" />
								</div>
							</div>
						</form>
					</div>
					<div class="re_bot_bg"></div>
				</div>
				<div class="wihte_01"></div>
			</div>
		</div>
	</body>
<script type="text/javascript">
	var valid = null;
	function resetpwd(){
		if(valid == null)
			valid = new Validation('registerForm',{immediate:true});
		if(valid.validate()){
			document.registerForm.submit();
		}
	}
</script>
</html>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<!doctype html>
<html>
	<head>
		<title>登录</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<link href="/css/base.css" rel="stylesheet" />
		<link href="/css/layout.css" rel="stylesheet" />
		<script type="text/javascript" src="/common/cms_js/util.js"></script>
		<script type="text/javascript" src="/common/validation/prototype.js"></script>
		<script src="/scripts/validation.js" type="text/javascript"></script>
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
			.login_int .errorMessage{
			font-size: 12px;
			color: #F00;
			margin-left: 5px;
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
						<a href="/"><img src="/images/ico_group_logo.png" alt="logo" /></a>
						<span>通行证</span>
					</p>
				</div>
				<div class="loginBox">
					<div class="lg_leftPic">
						<img src="/images/lg_left_pic.jpg" alt="" />
					</div>
					<form id="loginForm" name="loginForm" action="/webuser!login.action" method="post">
						<input type="hidden" name="location" value="${!empty param.fromUrl ? param.fromUrl : '/cms/index.action?siteId=1&templateId=6'}"/>
						<div class="lg_list">
							<p class="wel_login">
								欢迎登录康佳通行证
							</p>
							<div class="login_int">
								<label>
									<p>登录名：</p>
									<input id='loginname' name='domain.loginname' value="${domain.loginname}" class='lgUser_text required' />
									<ww:fielderror><ww:param>domain.loginname</ww:param></ww:fielderror>
								</label>
							</div>
							<div class="login_int">
								<label>
									<p>
										<a href="/webuser!findPwdByEmail.action" class="lg_forget">忘记密码</a>密码：
									</p>
									<input id='loginpwd' type="password" name='domain.loginpwd' class='lgPass_text required' />
									<ww:fielderror><ww:param>domain.loginpwd</ww:param></ww:fielderror>
								</label>
								<div class="lg_check">
									<input type="checkbox" id="storePwd" name="storePwd"/>&nbsp;两周内免登录
								</div>
							</div>
							<div class="login_int">
								<input onclick="loginSubmit()" type="button" class="lg_btn" value="登录" />
								<a class="lgNewname" href="/webuser!registerSave.action" target="_blank">注册新账号</a>
							</div>
							<div class="lg_bg_line"></div>
							<div class="login_coop" style="display:none;">
								<p>
									使用合作网站账户登录康佳：
								</p>
								<ul class="coopList">
									<li>
										<a href="#" class="lgsina">新浪微薄</a>
									</li>
									<li>
										<a href="#" class="lgren">人人网</a>
									</li>
									<li>
										<a href="#" class="lgkx">开心网</a>
									</li>
									<li>
										<a href="#" class="lgqq">QQ</a>
									</li>
								</ul>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="wihte_01"></div>
			<%@ include file="/cms_file/templates/1/footer_include.jsp"%>
		</div>
		</div>
	</body>
<script type="text/javascript">
document.onkeypress = function(){
	var event = arguments[0]||window.event;
	var currentKey = event.charCode||event.keyCode;
  	if(currentKey==13){
		loginSubmit();
	}
}
var valid = new Validation('loginForm', {immediate :true});
function loginSubmit(){
	if(valid.validate()){
		var fromUrl = escape('${param.fromUrl}');
		$('loginForm').action += '?fromUrl=' + fromUrl;
		$('loginForm').submit();
		writeCookie('outerLoginname',$('loginname').value, 2*7*24);
	}
}
$('loginname').value = getCookie('outerLoginname');
var pc_ = getCookie('webuserLoginname');
$('storePwd').checked = (pc_!=''&&pc_!=null);
</script>
</html>
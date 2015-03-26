<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<!doctype html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/css/base.css" rel="stylesheet" />
    <link href="/css/layout.css" rel="stylesheet" />
    <link href="/css/prsoner.gy.css" rel="stylesheet" type="text/css" />
    <title>注册成功</title>
<c:if test="${webuserLoginname != null}">
	<script type="text/javascript">
	document.cookie = 'webuserLoginname=' + escape('${webuserLoginname}') + ';path=/';
	document.cookie = 'webuserLoginid=' + escape('${webuserLoginid}') + ';path=/';
	<c:if test="${!empty param.fromUrl}">
	window.location.href = 'param.fromUrl';
	</c:if>
	</script>
	</c:if>
	<c:if test="${webuser == null}">
	<script type="text/javascript">
		window.location.href = "/webuser!login.action?fromUrl=" + escape(window.location);
	</script>
</c:if>
</head>
<body>
    <div class="kanKaBox loginPage">
        <div class="content loginCon">
            <div class="loginTitel">
                <p class="passCred"><a href="/"><img src="/images/ico_group_logo.png" alt="logo" /></a></p>
            </div>
            <div class="loginBox">
                <div class="re_top_bg">
                </div>
                <div class="re_mid_bg">
                    <p class="wel_regin">
                        欢迎注册康佳通行证</p>
                    <div class="re_leftPic"><img src="/images/re_left_pic.jpg" alt="" /></div>
                    <div class="re_Conbox registerInfo">
                        <p class="red font01">
                            恭喜您，注册成功！</p>
                        <p class="">
                            欢迎进入<a class="red" href="/webuser!personal.action">个人中心</a>完善您的资料，从而获得更多的信息和服务</p>
                        <p class="font02 black">
                            现在您可以从这里开始：</p>
                        <p>
                            <a href="/webuser!toSetInfo.action?id=${webuser != null ? webuser.oid : ''}" class="red blockW">&gt;完善个人资料</a><span>完善您的个人资料，从而获得更多更好的服务</span></p>
                        <p>
                            <a href="/cms/index.action?siteId=1&templateId=1" class="red blockW">&gt;回到首页</a><span>浏览康佳官网，了解详细产品信息，体验贴心服务</span></p>
                        <p>
                            <a href="/sh_zpzw" class="red blockW">&gt;查看招聘信息</a><span>更多康佳人的梦想和机会，加入康佳在家庭</span></p>
                        <p>
                            <a href="/sq" class="red blockW">&gt;进入康佳社区</a><span>加入社区讨论，了解最新最前沿资讯</span></p>
                        <p>
                            <a href="/dmzfw" class="red blockW">&gt;服务与支持</a><span>为您提供便捷服务，我们会在第一时间给您反馈</span></p>
                    </div>
                </div>
                <div class="re_bot_bg">
                </div>
            </div>
            <div class="wihte_01"></div>
			<%@ include file="/cms_file/templates/1/footer_include.jsp"%>
        </div>
    </div>
</body>
</html>

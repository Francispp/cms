<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="/css/base.css" rel="stylesheet"/>
	<link href="/css/konka.company.css" rel="stylesheet"/>
	<link href="/css/personar.css" rel="stylesheet"/>
	<link rel="stylesheet" href="/css/hr.jobs.css" />
	<title>个人中心 | 个人信息</title>
	<script src="/common/cms_js/util.js" type="text/javascript"></script>
	<script type="text/javascript" src="/common/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="/common/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="/scripts/konka.company.js"></script>
	<script type="text/javascript" src="/scripts/hr.page.js"></script>
	<script src="/dwr/util.js" type="text/javascript"></script>
	<script src="/dwr/engine.js" type="text/javascript"></script>
	<script src="/dwr/interface/areaService.js" type="text/javascript"></script>
	<script type="text/javascript" src="/common/datepicker/WdatePicker.js" language="javascript"></script>
	<script src="/common/validation/prototype.js" type="text/javascript" language="javascript"></script>
	<script src="/scripts/validation.js" type="text/javascript" language="javascript"></script>
	<script src="/common/cms_js/cms.js" type="text/javascript" language="javascript"></script>
	<script src="/common/attachment/attachment.js" type="text/javascript" language="javascript"></script>
	<script src="/common/attachment/xml.js" type="text/javascript" language="javascript"></script>
	<style type="text/css">
	.sr_up_pic{width:160px;height:160px;}
	.hrCommain{float:none;}
	.mrCommain, .srCommain{float:none;}
	</style>
</head>
<body>
<div class="kanKaBox">
	<%@ include file="/cms_file/templates/1/company_header_include.jsp"%>
    <div class="content">
        <div class="personalBox">
			<%@ include file="personLeft.jsp"%>
         	<div class="ps_con">
            	<ul class="ps_tab">
                	<li><a class="ps_tabCur" href="/webuser!personal.action" class="">个人资料</a></li>
                    <li><a href="/webuser!setpwd.action">修改密码</a></li>
					<ww:if test="showResumeLi">
                    <li>
						<a id="resumeLink" href="#">个人简历</a>
						<div class="resumeList">
							<p class="mlist"><a href="/webuser!campusLog.action">校园简历</a></p>
							<p class="mlist"><a href="/webuser!socialLog.action">社会简历</a></p>
						</div>
					</li>
					</ww:if>
					<ww:else>
                    <li>
						<a id="resumeLink" href="#">申请记录</a>
						<div class="resumeList">
							<p class="mlist"><a href="/webuser!tutorLog.action">导师申请</a></p>
							<p class="mlist"><a href="/webuser!courseLog.action">课程申请</a></p>
						</div>
					</li>
					</ww:else>
                </ul>
                <div class="ps_conInfo">
                	<div class="clear"></div>
                	<p class="if_resetPas">您的个人基本信息</p>
                    <div class="ps_resetCon">
                    <table border="0" cellpadding="0" cellspacing="0" class="infonTable">
                        	<tr>
                            	<td class="basicTd">登录名 ：</td>
                                <td>${webuser.loginname }</td>
                            </tr>
							<tr>
                            	<td class="basicTd">姓名 ：</td>
                                <td>${webuser.name }</td>
                            </tr>
                            <tr>
                            	<td class="basicTd">邮箱 ：</td>
                                <td>${webuser.email }</td>
                            </tr>
                            <tr>
                            	<td class="basicTd">手机 ：</td>
                                <td>${webuser.mobilephone }</td>
                            </tr>
                            <tr>
                            	<td class="basicTd">QQ号 ：</td>
                                <td>${webuser.qq }</td>
                            </tr>
                            <tr>
                            	<td class="basicTd">微信 ：</td>
                                <td>${webuser.weixinhao }</td>
                            </tr>
                            <tr>
                            	<td class="basicTd">地址 ：</td>
                                <td>${webuser.address }</td>
                            </tr>
                            <tr>
                            	<td colspan="2" class="insetFont"><a href="/webuser!toSetInfo.action?id=${webuser != null ? webuser.oid : ''}">修改个人资料</a></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
 	<div class="clear"></div> 
	<div class="wihte_03"></div>
	<%@ include file="/cms_file/templates/1/footer_include.jsp"%>
    </div>   
</div>
</body>
</html>

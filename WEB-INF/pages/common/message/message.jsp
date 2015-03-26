<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ec" uri="/WEB-INF/tld/ecside.tld"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tld/cms.tld"%>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>
<c:set var="title" value="留言" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<style type="text/css">
ul {
	list-style: none;
}

.fl {
	float: left;
}

.fr {
	float: right;
}

.clear {
	clear: both;
}

.star_1 {
	width: 79px;
	height: 20px;
	vertical-align: top;
	display: inline-block;
}

.whiteBg {
	background: #000;
	opacity: .5;
	filter: alpha(opacity =     50);
	position: absolute;
	top: 0px;
	left: 0px;
	display: none;
	z-index: 995;
}

.product_commentsuser {
	position: relative;
	width: 905px;
	margin: 0 auto;
	height: 100%;
}

.scoreTitle {
	width: 70px;
	height: 28px;
	font-size: 16px;
	color: #444;
	line-height: 25px;
	margin-left: 47px;
}

.all_comment {
	position: absolute;
	right: 15px;
	font-size: 12px;
	top: 17px;
	right: 36px;
}

.pro_score {
	height: 50px;
	padding-top: 60px;
	padding-bottom: 20px;
	border-bottom: 1px solid #ccc;
}

.scroeStar {
	width: 136px;
}

.scroeStar li {
	float: left;
	width: 27px;
	height: 28px;
}

.starnor {
	background: url(/images/icon_star_nor.png) no-repeat;
}

.starcur {
	background: url(/images/icon_star_cur.png) no-repeat;
}

.pro_interest {
	font-size: 12px;
	margin-right: 40px;
}

.comentBtn {
	background: url(/images/btn_commont.png) no-repeat;
	width: 138px;
	height: 28px;
	display: inline-block;
	vertical-align: middle;
	text-align: center;
	color: #00aae6;
	line-height: 25px;
	margin-left: 5px;
	cursor: pointer;
}

.star_1 {
	width: 79px;
	height: 20px;
	vertical-align: top;
}

.useComcon {
	width: 848px;
	margin: 0 auto;
}

.userComList li {
	padding-bottom: 30px;
	border-bottom: 1px solid #ccc;
	margin-top: 38px;
}

.userInfo {
	width: 820px;
}

.aboutuser {
	padding-bottom: 5px;
	border-bottom: 1px solid #ccc;
	font-size: 12px;
	color: #666;
}

.userNumber {
	width: 20px;
	height: 20px;
	font-size: 14px;
	color: #444;
	line-height: 20px;
}

.userName {
	font-size: 14px;
	color: #444;
	margin-right: 12px;
}

.comTime {
	float: right;
}

.userEvaluate {
	margin-top: 5px;
	font-size: 12px;
	color: #444;
}

.evainfo,.markinfo {
	font-size: 12px;
	color: #666;
}

.userScore {
	margin-top: 10px;
	line-height: 25px;
}

.userCombot {
	border-top: 1px solid #ccc;
	margin-top: 50px;
	width: 870px;
	margin-left: 15px;
	position: relative;
	margin-bottom: 55px;
}

.comsatr {
	height: 28px;
	vertical-align: middle;
	margin-bottom: 15px;
}

.comenFont {
	display: inline-block;
	height: 28px;
	vertical-align: middle;
	line-height: 28px;
	color: #fff;
}

.starlist {
	display: inline-block;
	width: 200px;
	height: 28px;
	vertical-align: middle;
}

.starlist li {
	float: left;
	height: 28px;
	width: 27px;
	background: url("/images/icon_star_nor.png") no-repeat scroll 0 0;
	cursor: pointer;
}

.inputner {
	width: 500px;
}

.comentArea {
	width: 496px;
	height: 150px;
	border: 1px solid #CCC;
	padding-left: 3px;
	padding-top: 3px;
	margin: 0 auto;
}

.comentBox {
	display: block;
	position: fixed;
	top: 150px;
	left: 50%;
	width: 500px;
	margin-left: -250px;
	display: none;
	z-index: 996;
}

.comentchange {
	width: 500px;
	text-align: center;
	margin-top: 20px;
}

.OkBtn,.delBtn {
	width: 40px;
	height: 25px;
	cursor: pointer;
}

.OkBtn {
	margin-right: 25px;
}

.starlist li.starcur {
	background: url(/images/icon_star_cur.png) no-repeat;
}

.star_1,.star_2,.star_3,.star_4,.star_5 {
	width: 79px;
	height: 20px;
	vertical-align: top;
	display: inline-block;
}

.star_1 {
	background: url(/images/icon_all_star.png) 0 -88px;
}

.star_2 {
	background: url(/images/icon_all_star.png) 0 -67px;
}

.star_3 {
	background: url(/images/icon_all_star.png) 0 -46px;
}

.star_4 {
	background: url(/images/icon_all_star.png) 0 -24px;
}

.star_5 {
	background: url(/images/icon_all_star.png) 0 0px;
}

.bigStar_1,.bigStar_2,.bigStar_3,.bigStar_4,.bigStar_5 {
	width: 134px;
	height: 28px;
	vertical-align: top;
	display: inline-block;
}

.bigStar_1 {
	background: url(/images/icon_bigall_star.png) 0 0px;
}

.bigStar_2 {
	background: url(/images/icon_bigall_star.png) 0 -28px;
}

.bigStar_3 {
	background: url(/images/icon_bigall_star.png) 0 -58px;
}

.bigStar_4 {
	background: url(/images/icon_bigall_star.png) 0 -88px;
}

.bigStar_5 {
	background: url(/images/icon_bigall_star.png) 0 -118px;
}
.firstBtn,.lastBtn,.preBtn,.nextBtn,.pagesItem{color:#606060;display:inline-block; background:#fff; border:1px solid #ccc;height:20px;}
.firstBtn:hover,.lastBtn:hover,.preBtn:hover,.nextBtn:hover,.pagesItem:hover,.pageCur{background:#da251d;color:#fff;}
.firstBtn,.lastBtn{width:40px;}
.preBtn,.nextBtn{width:52px;}
</style>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/TablePager.css" />
		<title>${title}</title>
		<script src="/common/cms_js/util.js" type="text/javascript"></script>
		<xml id="myXML"></xml>
	<head>
		<link href="css/base.css" rel="stylesheet"></link>
	</head>
	<body>
		<!-- 状态提示栏 -->
		<form id="messageForm" action="">
		<div class="product_commentsuser">
			<ww:hidden name="domain.docid" value="%{docid}" />
			<ww:hidden name="docid"/>
			<ww:hidden name="pageSize"/>
			<p class="all_comment">
				共${_data.totalCount}条评论
			</p>
			<div class="pro_score">
				<span class="scoreTitle fl">总评分：</span>
				<span class="bigStar_${_data.totalCount == '0' ? 5 : average }"></span>
				<p class="pro_interest fr">
					拥有此产品，或对产品感兴趣？
					<b><a style="color: blue;background:none; width:100px;" class="comentBtn">我要发表评论</a></b>
				</p>
			</div>
			<div class="useComcon">
				<ul class="userComList">
					<ww:iterator value="#request._data.result" status="result">
						<li class="usercomDetail">
							<p class="userNumber fl">
								${result.index + 1}.
							</p>
							<div class="userInfo fl">
								<p class="aboutuser">
									<span class="userName">用户：${face} </span>(使用1个月内)
									<span class="comTime"><ww:date name="createtime"
											format="yyyy-MM-dd hh:mm" /> </span>
								</p>
								<div class="userEvaluate">
									<p class="userScore">
										评分：
										<span class="star_${score}"></span>
									</p>

									<p class="userScore">
										评论：
										<span class="evainfo">${content}</span>
									</p>
									<!--<p class="userScore">
										标签：
										<span class="markinfo"><ww:property value="title"
												escape="true"></ww:property> </span>
									</p>-->
								</div>
							</div>
							<div class="clear"></div>
						</li>
					</ww:iterator>
				</ul>
			</div>
			<div class="userCombot">
				<p class="all_comment">
					共${_data.totalCount}条评论
				</p>
			</div>
			<center>
				<cms:tablePager pageIndex="#request._data.currentPageNo"
					pageSize="#request._data.pageSize"
					recordCount="#request._data.totalCount"></cms:tablePager>
			</center>
		</div>

		<!-- 页脚 -->
		<%@ include file="/common/foot.inc"%>
		<div id="pinglun" class="whiteBg"></div>
		<c:if test="${webuser != null}">
			<div class="comentBox">
					<div class="comsatr">
						<span class="comenFont">评分：</span>
						<ul class="starlist" id="starlist">
							<li class=""></li>
							<li class=""></li>
							<li class=""></li>
							<li class=""></li>
							<li class=""></li>
						</ul>
						`
						<input type="hidden" id="channelId" name="channelId"/>
						<input type="hidden" id="domain_score" name="domain.score" value="" />
					</div>
					<div class="inputner">
						<span class="comenFont">评论：</span>
						<textarea id="domain_content" name="domain.content"
							class="comentArea"></textarea>
					</div>
					<div class="comentchange">
						<input type="button" id="submitFormbt" onclick="submitForm()" class="OkBtn"
							value="发布">
						<input type="button" class="delBtn" value="取消">
					</div>
			</div>
		</c:if>
</form>
		<script type="text/javascript" src="/common/jquery/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="/common/jquery/jquery.form.min.js"></script>
		<script type="text/javascript" ><!--
	function submitForm() {
		if ($("#domain_score").val() == '') {
			alert('请评分')
			return;
		}
		if ($("#domain_content").val() == '') {
			alert('评论内容不能为空')
			$("#domain_content").focus();
			return;
		}
		$("#submitFormbt").attr('disabled',"disabled");
		var fromUrl = parent.location.href;
		var ci = fromUrl.indexOf('channelId')+10;
		$('#channelId').val(fromUrl.substring(ci,fromUrl.indexOf('&',ci)))
		$('#messageForm').ajaxSubmit({
				cache:false,
				type:"post",
				url:'/base/message!saveOrUpdate.action',
				success:function(rs){window.location.reload();
									$('#frsame').css({height:600+"px"})
				}
		});
	}

	var _bol = false
	$(".comentBtn").live("click", function() {
		<c:if test="${webuser != null}">
		var _h = $(document).outerHeight(true);
		var _w = $(document).outerWidth(true);
		$(".comentBox").show()
		$(".whiteBg").css( {
			width :_w + "px",
			height :_h + "px"
		})
		$(".whiteBg").show()
		</c:if>
		<c:if test="${webuser == null}">
		var fromUrl = parent.location.href;
		fromUrl = fromUrl.substring(fromUrl.indexOf('/cms/docInfo!view.action'));
		fromUrl = escape(fromUrl)
		window.parent.location.href = "/webuser!login.action?fromUrl=" + fromUrl;
		</c:if>
	})
	$(".delBtn").live("click", function() {
		$(".comentBox").hide()
		$(".whiteBg").hide()
	})

	var _star = $(".starlist li")
	$(_star).each( function(st) {
		var _temp = $(_star[st])
		$(_temp).live("mouseenter", function() {
			_bol = false
			for ( var i = 0; i <= st; i++) {
				$(_star).eq(i).addClass("starcur")
			}
		}).mouseleave( function() {
			if (_bol == false) {
				for ( var i = 0; i <= st; i++) {
					$(_star).eq(i).removeClass("starcur")
				}
			}
		}).click( function() {
			_bol = true
			for ( var i = 0; i <= st; i++) {
				$(_star).eq(i).addClass("starcur")
			}
			$("#domain_score").val(i)
		})
	});
</script>
<script>
$().ready(function(){
	//var o = $('#frsame').height + '<>' + $('.whiteBg')[0].height
///alert(document.getElementById('frsame'))
});
window.onload = function(){
	//alert(document.getElementById('frsame'))
}
</script>
	</body>
</html>

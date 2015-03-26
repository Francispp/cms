<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/js.inc"%>
<html>
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script type="text/javascript">
	function save() {
		var valid = new Validation('myform', {
			immediate : true
		});
		if (valid.validate())
			myform.submit();
	}

	function closed() {
		window.returnValue = 'true';
		window.close();

	}
</script>

<style type="text/css">
.pw-con-input-item_ab .lab-pw-tit_ab {
	width: 100px;
	display: block;
	float: left;
	text-align: right;
	height: 22px;
	line-height: 22px;
	color: #333;
}

.textarea-hb {
	border: 1px #999 solid;
	width: 90%;
	margin: 7px 0;
}

.pw-con-input-item-plu_ab {
	border: 1px solid #999;
	width: 200px;
	height: 200px; #
	height: 200px;
}

.pw-w350_ab {
	width: 350px;
}
</style>
</head>
<body class="pw_ab">
	<!-- 状态提示栏 -->
	<%@ include file="/common/messages.inc"%>
	<!-- 页面标题 -->


	<form class="pw-borderOut_ab pw-w350_ab" method="post"
		action="${ctx}/base/audio!edit.action" name="myform">

		<div class="pw-borderIn_ab">
			<div class="pw-head_ab">
				<div class="pw-head-tit_ab">
					<img src="${default_imagepath}/ico_013_plus.gif"
						class="ico_ab ico-013_ab" /> <span>播放音频</span>
				</div>
				<img src="${default_imagepath}/pic_popwindow_close.jpg"
					class="pwClose_ab" id="pwClose_ab" />
			</div>
			<div class="pw-con_ab">
				<ul class="pw-con-input_ab pw-con-input-ex_ab">
					<li class="pw-con-input-item_ab"><label class="lab-pw_ab">
<span id="audioBroadcast">
<EMBED src='${mediaFilePath }${ domain.filePath}' loop='-1'
								width=290 height=50 type=audio/x-pn-realaudio-plugin
								controls='all' />
								</span>
							 </label></li>


				</ul>


				<div class="fn-clear"></div>
				<div class="pw-con-btns_ab"></div>


			</div>
		</div>
	</form>
	<script type="text/javascript">
		//         
		global_ab.init.popwindowPage_fn();

		(function() {
			var closeBtn = document.getElementById("pwClose_ab");
			closeBtn.onclick = function() {
				document.getElementById('audioBroadcast').innerHTML='';
				global_ab.fn.popWindow.removePopWindow(false);
			}
		})();

		//
	</script>

	<!-- 页脚 -->
	<%@ include file="/common/foot.inc"%>
</body>
</html>

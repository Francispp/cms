<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/weixin.inc"%>
<html>
<head>
<title>签到</title>
<!-- 在写移动端的网站的时候， 一定要写一个meta的name为viewport的属性， 因为该属性代表着网站页面的自适应。 代表着网站为驱动设备的宽度。 -->
<meta name="viewport" content="width=device-width, initial-scale=1"> 
 <link href="/weixin/css/index.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript">
wx.error(function(res){
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    if(res.errMsg=="config:invalid signature"){
    	 $.ajax({
		   url:"/weixin/attendance!ajaxloadConfig.action",
		   type: "POST",
		   data:{
			   "type":"signErr"
		   },
		   async:false,
		   success:function(data){
			   alert(data);
			   wx.config({
				    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: '${resultMap.appId}', // 必填，企业号的唯一标识，此处填写企业号corpid
				    timestamp: '${resultMap.timestamp}', // 必填，生成签名的时间戳
				    nonceStr: '${resultMap.nonceStr}', // 必填，生成签名的随机串
				    signature: '${resultMap.timestamp}',// 必填，签名，见附录1
				    jsApiList: '${resultMap.jsApiList}' // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
		   }
	   });
    }
});


wx.ready(function(){
	$('.emotion').qqFace({     
		assign:'signinTxt', //给输入框赋值         
		path:'/weixin/images/face/'    
		//表情图片存放的路径 
	});     
}); 
${config}
$(function() {
	$(".uploadPic").click(function() {
		wx.chooseImage({
			success : function(res) {
				var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
				var imgDiv = $("#showimages");
				for(var i=0;i<localIds.length;i++){
					if(i>0){
						alert("只能上传一张图片");
					}else{
						imgDiv.append('<img src="'+localIds[i]+'"/>');
						$("#pic").val(localIds[i]);
					}
				}
			}
		});
	});
});
$(function() {
	$(".sub_btn").click(function() {
		$("#myForm").submit();
	});
});
</script>
<style type="text/css">
</style>
</head>
<body>
	<h1>签到成功</h1>
	<span>签到时间</span>
	<ww:date name="domain.signinTime" format="yyyy-MM-dd HH:mm:ss" />
<form id="myForm" action="/weixin/attendance!doSave.action?type=1" method="post">
<ww:hidden name="domain.id"></ww:hidden>
		<div id="showimages"></div>
		<div class="comment">
			<div class="com_form">
				<textarea class="input" id="signinTxt" name="domain.signinTxt"></textarea>
				<p>
					<span class="emotion">表情</span> 
					<input type="button" class="uploadPic" value="上传照片">
					<input type="hidden" name="domain.signInPic" id="pic"/>
					<input type="button" class="sub_btn" value="提交">
				</p>
			</div>
		</div>
</form>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>外勤签到/退</title>
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<script src="${ctx}/common/jquery/jquery-1.4.2.js" type="text/javascript"></script>
<script language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CUvdkSR2lHwp7r1rje07xFmH"></script>

<script type="text/javascript">
function showTime(){
	var now=new Date();
	var year=now.getFullYear();
	var month=now.getMonth()+1;
	var day=now.getDate();
	var hours=now.getHours();
	var minutes=now.getMinutes();
	var seconds=now.getSeconds();
	document.getElementById("showDate").innerHTML=""+year+"-"+month+"-"+day+"- "+hours+":"+minutes+":"+seconds+"";
	setTimeout(showTime,1000);//一秒刷新一次显示时间
}
${config}
wx.ready(function(){
       var map = new BMap.Map("map");   
       map.centerAndZoom(new BMap.Point(113.443456, 23.168507), 19); //初始化地图，设置地图中心和级别。
       map.setCurrentCity("广州");       						//地图显示的城市
       map.enableScrollWheelZoom(true);
       var center = map.getCenter();   
       var myGeo = new BMap.Geocoder(); //创建地理编码实例
       myGeo.getLocation(new BMap.Point(center.lng,center.lat),function (result){
       	if(result){
       		$("#address").val(result.address);
       		
       	}
       });
});

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
<form id="myForm" action="/weixin/field!doSave.action?type=1" method="post">
	<span>事由：</span><select name="domain.cause"><option value="1">采购</option><option value="2">拜访</option><option value="3">参加会议</option></select><br/>
	<span>地点：</span><input type="text" id="address" style="width: 100%;" name="domain.signInPlace"/><br/>
	<span>说说：</span><textarea class="input" id="signinTxt" name="domain.signInTxt"></textarea><br/>
	<input type="button" class="uploadPic" value="拍照">
	<div id="showimages" style="width: 500px;height: 320px;"></div>
	
	<input type="hidden" name="domain.signInPic" id="pic"/>
	<input type="button" value="签到" class="sub_btn"/>
<div id="map" style="display: none"></div>
</form>
</body>
</html>

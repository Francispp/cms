<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>外勤签到/退</title>
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<script src="/common/jquery/jquery-1.4.2.js" type="text/javascript"></script>
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
	wx.getLocation({
	    success: function (res) {
	       var longitude = res.longitude; // 纬度，浮点数，范围为90 ~ -90
	       var latitude = res.latitude; // 经度，浮点数，范围为180 ~ -180。
	        var map = new BMap.Map("map");   
	        map.centerAndZoom(new BMap.Point(113.443456, 23.168507), 19); //初始化地图，设置地图中心和级别。
	        map.setCurrentCity("广州");       						//地图显示的城市
	        map.enableScrollWheelZoom(true);
	        //创建地理编码实例
	        var myGeo = new BMap.Geocoder();
	        myGeo.getLocation(new BMap.Point(longitude,latitude),function (result){
	        	if(result){
	        		$("#address").val(result.address);
	        		
	        	}
	        });
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
					}
				}
			}
		});
	});
});
function signin(){
	
	
}
</script>
<style type="text/css">
</style>
</head>
<body onload="showTime()">
<span>事由：</span><select name="cause"><option>采购</option><option>拜访</option><option>参加会议</option></select><br/>
<span>地点：</span><input type="text" id="address" style="width: 100%;" name="domain.signInPlace"/><br/>
<span>时间：</span><input type="text" id="showDate" style="width: 100%;" name="domain.signInTime"/><br/>
<span>说说：</span><textarea class="input" id="signinTxt" name="domain.signInTxt"></textarea><br/>
<input type="button" class="uploadPic" value="拍照">
<div id="showimages" style="width: 500px;height: 320px;"></div>

<input type="button" value="签到" onclick="signin()"/>
</body>
</html>

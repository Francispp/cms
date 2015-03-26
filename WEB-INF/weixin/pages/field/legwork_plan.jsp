<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<html>
<head>
<title>外出申请</title>
<!-- 在写移动端的网站的时候， 一定要写一个meta的name为viewport的属性， 因为该属性代表着网站页面的自适应。 代表着网站为驱动设备的宽度。 -->
<!-- <meta name="viewport" content="width=device-width, initial-scale=1">  -->
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<link rel="stylesheet" href="/common/jquery/mobiscroll/css/mobiscroll.custom-2.5.0.min.css" />
<script src="/common/jquery/jquery-1.4.2.js" type="text/javascript"></script>
<script src="/common/jquery/mobiscroll/jquery-1.9.1.min.js"></script>
<script src="/common/jquery/mobiscroll/jquery.mobile-1.3.0.min.js"></script>
<script src="/common/jquery/mobiscroll/mobiscroll.js"></script>
<script src="/common/jquery/mobiscroll/date.js"></script>
<script language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CUvdkSR2lHwp7r1rje07xFmH"></script>
<script type="text/javascript">

$(function () {
	newjavascript.plugdatetime($("#startTime"), "datetime");
	newjavascript.plugdatetime($("#endTime"), "datetime");
});
wx.ready(function(){
// 	alert("fdas");
	var map = new BMap.Map("container");
	var point = new BMap.Point(113.443456,23.168507);
	map.centerAndZoom(point, 15);
	var marker = new BMap.Marker(point);  // 创建标注 
	map.addOverlay(marker);     
	marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画 
	map.setDefaultCursor("crosshair");//设置地图默认的鼠标指针样式
	map.enableScrollWheelZoom();//启用滚轮放大缩小，默认禁用。
	map.addControl(new BMap.NavigationControl());
	var center = map.getCenter();   
	getLocationAddress(center.lng,center.lat);
	map.addEventListener("click", function(e){//地图单击事件
		getLocationAddress(e.point.lng,e.point.lat);
	});
});
${config}
function address(){
	$("#fieldInfo").hide();
	$("#allmap").show();
}
function getLocationAddress(lng,lat){
	var myGeo = new BMap.Geocoder();
	myGeo.getLocation(new BMap.Point(lng,lat),function (result){
		if(result){
			document.getElementById("lonlat").value=result.address;
		}
	});
}
// function iploac(result){//根据IP设置地图中心
// 	var cityName = result.name;
// 	map.setCenter(cityName);
// }
// var myCity = new BMap.LocalCity();
// myCity.get(iploac);
function sear(result){//地图搜索
	var local = new BMap.LocalSearch(map, {
	renderOptions:{map: map,selectFirstResult:true}
});
local.search(result);
}

function submitAddress(){
	$("#allmap").hide();
	$("#fieldInfo").show();
	$("#fieldAddress").val($("#lonlat").val());
}
$(function() {
	$(".sub_btn").click(function() {
		var startTime =  $("#startTime").val();
		var endTime =  $("#endTime").val();
		var fieldAddress =  $("#fieldAddress").val();
		var signinTxt =  $("#signinTxt").val();
		if(startTime==""){ 
			alert("开始时间不能为空");
			return;
		}else if(endTime==""){
			alert("结束时间不能为空");
			return;
		}else if(fieldAddress==""){
			alert("外勤地址不能为空");
			return;
		}else if(signinTxt==""){
			alert("外勤事由不能为空");
			return;
		}else{
			$("#startDate").val(startTime);		
			$("#endDate").val(endTime);		
			$("#myForm").submit();
		}
	});
});
</script>
<style type="text/css">
</style>
</head>
<body>
<form id="myForm" action="/weixin/legworkPlan!doSave.action" method="post">
<input type="hidden" name="domain.userId" value="${wxloginer.userid }"/>
<input type="hidden" name="domain.userName" value="${wxloginer.name }"/>
<input type="hidden" name="domain.startDate" id="startDate" />
<input type="hidden" name="domain.endDate" id="endDate"/>
<div id="fieldInfo">
	<span>外出人：</span><span><ww:property value="wxloginer.name"/></span><br/>
	<span>开始时间：</span><input type="text"  id="startTime"/><br/>
	<span>结束时间：</span><input type="text"  id="endTime"/><br/>
	<span>外出地点：</span><input type="text"  name="domain.fieldPlace" id="fieldAddress" onclick="address()" /><br/>
	<span>外出类型：</span><select name="domain.fieldType"><option value="0">外出</option><option value="1">其它</option></select><br/>
	<span>签到方式：</span><input type="radio" name="domain.signType" value="0" checked="checked"/>签到/退 &nbsp;<input type="radio" name="domain.signType" value="1"/>仅签到<br/>
	<span>事由：</span><textarea name="domain.cause" class="input" id="signinTxt" ></textarea><br/>
	<input type="button" value="提交" class="sub_btn"/>
</div>
</form>
<div id="allmap"  style="display: none">
	<label >地点：</label>
	<input id="where" name="where" type="text" >
	<input type="button" value="地图上找" onclick="sear(document.getElementById('where').value);" />
	<br />
	<div style="width:340px;height:380px;border:1px solid gray" id="container"></div>
	<br />
	经纬度：
	<input id="lonlat" name="lonlat" type="text">
	<input type="button" value="确定" onclick="submitAddress();"/>
</div> 

</body>
</html>

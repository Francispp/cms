<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<!DOCTYPE html>
 <html>
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CUvdkSR2lHwp7r1rje07xFmH"></script>
 <script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
 <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
 <title>SearchInfoWindow</title>
 </head>
 
 <body>
<form action="" method="get">
<label >地点：</label>
<input id="where" name="where" type="text" >
<input type="button" value="地图上找" onClick="sear(document.getElementById('where').value);" />
<br />
<div style="width:500px;height:450px;border:1px solid gray" id="container"></div>
<br />
经纬度：
<input id="lonlat" name="lonlat" type="text">
<input type="button" value="确定"/>
</form>
<script type="text/javascript">
var map = new BMap.Map("container");//在指定的容器内创建地图实例
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

function getLocationAddress(lng,lat){
	var myGeo = new BMap.Geocoder();
	myGeo.getLocation(new BMap.Point(lng,lat),function (result){
		if(result){
			document.getElementById("lonlat").value=result.address
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
</script>
</body>
 </html>

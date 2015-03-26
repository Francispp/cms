<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="地点编辑" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript"></script>
<style type="text/css">
span{padding-right:10px;width:8em;display:inline-block;line-height:26px;} 
</style>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=pET7g2k3LDNDRlEYKm1kj54S"></script>
	<title>单击获取点击的经纬度</title>
<script type='text/javascript'>
	
</script>
</head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
	
		<div id="allmap" style="width: 800px; height: 300px;"></div>
	

		<ww:hidden id="domain.longitude" /> <ww:hidden id="domain.latitude" />
		
		<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
		<input type="button" value="提交地点" onclick="copy()"></input> <input type="button"value="取消" onclick="custom_close();" />
		

<!-- 	<input type="button" onclick="showaaa();" value="asdasd"/> -->

</body>
</html>
<script type="text/javascript" language="javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");   
	map.centerAndZoom(new BMap.Point(113.443456, 23.168507), 19); //初始化地图，设置地图中心和级别。
	map.setCurrentCity("广州");       						//地图显示的城市
	map.enableScrollWheelZoom(true);                        //开启鼠标滚轮缩放
	
	//单击获取点击的经纬度
	map.addEventListener("click",function(e){
		alert("经度："+e.point.lng + "," +"纬度："+ e.point.lat);
		document.getElementById("domain.longitude").value=e.point.lng;	
		document.getElementById("domain.latitude").value=e.point.lat;
	});
	
	function custom_close(){
		if
		(confirm("您确定要关闭本页吗？")){
		window.opener=null;
		window.open('','_self');
		window.close();
		}
		else{}
		}
	
	function showaaa(){
		var a=document.getElementById("domain.longitude").value;
		var b=document.getElementById("domain.latitude").value;
		alert(a +"," + b);
	}
	
	function copy(){
		window.opener.document.getElementById("domain.longitude1").value=document.getElementById("domain.longitude").value;
		window.opener.document.getElementById("domain.latitude1").value=document.getElementById("domain.latitude").value;
		window.close();
	}
	
	
</script>
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
<!-- <script type='text/javascript' src='/dwr/engine.js'></script> -->
<!-- <script type='text/javascript' src='/dwr/util.js'></script> -->
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

</head>

<body onload="document.forms[0].reset()">
	
<!-- 	<div id="allmap" style="width: 800px; height: 300px;"></div> -->

	<div>
		<form action="">
			<%String longitude1 =request.getParameter("domain.longitude");%>
			<table>
				<tr>
					<span>地点名称:</span><input type="text" id="domain.address" ></input>
				</tr></br>

				<tr>
					<span>详细地址:</span><input type="text" id="domain.detailaddress"></input>
				</tr></br>

				<tr>
					<td>
					<span>经度:</span><input type="text" id="domain.longitude1" ></input>
					</td>
					<td>
					
					<span>纬度:</span><input type="text" id="domain.latitude1" ></input>
					</td>
					
					<td><input type="button" value="地图查找" onclick="search()"/> </td>
					</br>
				</tr>

				<tr>
					<span>考勤范围(米):</span><input type="text" id="domain.range" >(该范围内员工可以考勤)</input>
				</tr></br>
			</table>
			
			<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
			<input type="submit" value="确定" />
			<input type="button" value="取消" onclick="refreshs();"/>
		</form>

	</div>

</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");   
	map.centerAndZoom(new BMap.Point(113.443456, 23.168507), 19); //初始化地图，设置地图中心和级别。
	map.setCurrentCity("广州");       						//地图显示的城市
	map.enableScrollWheelZoom(true);                        //开启鼠标滚轮缩放
	

	map.addEventListener("click",function(e){
		alert(e.point.lng + "," + e.point.lat);
		
	});
	
	function refreshs() {
		location.reload();
	}
	
	function setRange(){
		document.getElementById("domain.range").value="300";
	}
	function search(){
		
		window.open("${ctx}/weixin/sign!openMap.action?");
	}
	window.onload=function setdefault(){
		document.getElementById("domain.range").value=300;
	};
	
</script>
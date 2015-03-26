<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/js.inc"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>签到</title>
	<meta http-equiv="description" content="This is my page">
	<script language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
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
	wx.getLocation({
	    success: function (res) { 
	       var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
	       var latitude = res.latitude; //纬度，浮点数，范围为90 ~ -90
	       var speed = res.speed; // 速度，以米/每秒计
	       var accuracy = res.accuracy; // 位置精度
	       var longt = '${am.longitude}';
	       var lat = '${am.latitude}';
	       var ranges = '${am.range}';
	       var distance = getDistance(latitude,longitude,lat,longt);
	       if(distance < ranges){
	    	  $("#longitude").val(longitude);
	    	   $("#latitude").val(latitude);
	    	   $("#accuracy").val(accuracy);
	    	   $("#myForm").submit();
	       }else{
	    	   alert("“亲，签到需要在办公地点500米范围以内喔~如需请假/调休请联系行政妹妹");
	       }
	    }
	});
});
${config}
function getDistance(wd1, jd1, wd2, jd2){
	var x,y,out;
    var PI=Math.PI;
    var R=6.371229*1e6;
    x=(jd2-jd1)*PI*R*Math.cos( ((parseFloat(wd1)+parseFloat(wd2))/2) *PI/180)/180;
    y=(wd2-wd1)*PI*R/180;
    out=Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
    return out;
}

</script>
  </head>
  <body>
  <form id="myForm" action="/weixin/attendance!doSave.action?type=1" method="post">
  	<input type="hidden" name="domain.longitude" id="longitude"/>
  	<input type="hidden" name="domain.latitude" id="latitude"/>
  	<input type="hidden" name="domain.precision" id="accuracy"/>
  </form>
<!--   页面制作中.... -->
  </body>
</html>

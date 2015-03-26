<%@ page contentType="text/html; charset=UTF-8"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广东省小汽车定编信息查询系统</title>
<link href="sy_style.css" rel="stylesheet" type="text/css"></link>
<%
	int flag = 0;
	if(session.getAttribute("userName") == null){
		flag  = 1;
	}
%> 
<style type="text/css">
body {
	font-family: "宋体", "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	color:#434343;
	margin: 0px;
	padding: 0px;
	background:#0076a9;
}
</style>
<script type="text/javascript">
	var flag = "<%=flag%>";
	
	if(flag == "1")
		window.location = "/portals/car/login.jsp";
</script>
</head>

<body>
<table width="999" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="80" colspan="2" align="right" valign="top" background="images/gdinside_02.jpg"><table width="354" border="0" cellspacing="0" cellpadding="0" class="tt">
        <tr>
          <td width="200" align="center" class="cl"><img src="images/color.jpg" width="16" height="16" align="absmiddle" />
          <script language=JavaScript>
<!--显示当前日期-->
 var today = new Date();
 var strDate = "";
 if (today.getYear() < 2000)
    {
   yearnow=1900+today.getYear();
       strDate = ( yearnow + "年" + (today.getMonth() + 1) + "月" + today.getDate() + "日");
     }
 else{
       strDate = ( today.getYear() + "年" + (today.getMonth() + 1) + "月" + today.getDate() + "日");
 
     }
  var n_day = today.getDay();
 switch (n_day)
 {
 case 0:{
 strDate = strDate + " 星期日"
 }break;
 case 1:{
 strDate = strDate + " 星期一"
 }break;
 case 2:{
 strDate = strDate + " 星期二"
 }break;
 case 3:{
 strDate = strDate + " 星期三"
 }break;
 case 4:{
 strDate = strDate + " 星期四"
 }break;
 case 5:{
 strDate = strDate + " 星期五"
 }break;
 case 6:{
 strDate = strDate + " 星期六"
 }break;
 case 7:{
 strDate = strDate + " 星期日"
 }break;
 }
 strDate="<font color=#ffffff>"+strDate+"</font>"
 document.write(strDate);
 </script></td>
          <td width="154" align="left"><a href="#"><img src="images/exit.jpg" alt="退出" width="77" height="30" onclick="window.location = '/portals/car/login.jsp'" /></a></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="28" colspan="2" background="images/gdinside_04.jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="2%">&nbsp;</td>
        <td width="98%" class="us"><img src="images/gdcar2_06.jpg" width="20" height="16" align="absmiddle" />当前用户：${userName}  所属单位：${deptName}</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="2"><img src="images/gdinside_05.jpg" width="999" height="81" /></td>
  </tr>
  <tr>
    <td width="333"><img src="images/gdinside_06.jpg" width="333" height="428" /></td>
    <td width="666" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="images/gdinside_07.jpg" width="662" height="71" /></td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="38%"><a href="/portals/car/query.jsp?img=18" onmouseout="change('img1','images/gdinside_09.jpg')" onmouseover="change('img1','images/gdinside2_09.jpg')"><img name="img1" id="img1" border="0"  src="images/gdinside_09.jpg" width="256" height="97" /></a></td>
            <td width="10%"><a href="/portals/car/query.jsp?img=19" onmouseout="change('img2','images/gdinside_10.jpg')" onmouseover="change('img2','images/gdinside2_10.jpg')"><img name="img2" id="img2" border="0"  src="images/gdinside_10.jpg" width="207" height="97" /></a></td>
            <td width="52%"><img src="images/gdinside_11.jpg" width="203" height="97" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="21%" rowspan="2">
           <img src="images/gdinside_12.jpg"width="138" height="260" /></td>
            <td width="13%"><a href="/portals/car/query.jsp?img=20" onmouseout="change('img3','images/gdinside_13.jpg')" onmouseover="change('img3','images/gdinside2_13.jpg')"><img name="img3" id="img3" src="images/gdinside_13.jpg" width="213" height="125" /></a></td>
            <td width="66%"><a href="/portals/car/query.jsp?img=21" onmouseout="change('img4','images/gdinside_14.jpg')" onmouseover="change('img4','images/gdinside2_14.jpg')"><img name="img4" id="img4" src="images/gdinside_14.jpg" width="315" height="125" /></a></td>
          </tr>
          <tr>
            <td height="135" colspan="2" align="right" background="images/gdinside_15.jpg"><table width="85%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="64" align="center" valign="bottom">版权所有：广东省小汽车定编中心 Copyright2010,All Rights Reserved </td>
              </tr>
            </table></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
<script type="text/javascript">
    function change(imgID, imgurl) {
        var img = document.getElementById(imgID);
        img.src = imgurl
    }
</script>

</body>
</html>
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
<script type="text/javascript">
	var flag = "<%=flag%>"; 
	if(flag == "1")
		window.location = "/portals/car/login.jsp";
</script>
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

</head>

<body>
<table width="999" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="999" height="80" colspan="2" align="right" valign="top" background="images/gdinside_02.jpg">
      <table width="354" border="0" cellpadding="0" cellspacing="0" class="tt">
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
          <td width="154" align="left"><a href="#"><img src="images/exit.jpg" alt="退出" width="77" height="30" /></a></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="28" colspan="2" background="images/gdinside_04.jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="2%">&nbsp;</td>
        <td width="98%" class="us"><img src="images/gdcar2_06.jpg" width="20" height="16" align="absmiddle" />当前用户：交警001   所属单位：广州交警</td>
      </tr>
    </table></td>
  </tr>
    <tr>
    <td colspan="2"><img src="images/gdlist_03.jpg" width="999" height="5" /></td>
  </tr>
  <tr>
    <td colspan="2"><table width="999" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="5" align="center" valign="top" background="images/gdlist2_08.jpg"></td>
        <td width="225" height="469" align="center" valign="top" background="images/gdlist_06.jpg" class="bor"> <table width="86%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="/portals/car/main_18.jsp" target="main" onclick="change('img18','images/gdlist2_18.jpg')" ><img name="img18" id="img18" border="0" src="images/gdlist_18.jpg" width="201" height="91" /></a></td>
          </tr>
          <tr>
            <td align="center"><a  href="/portals/car/main_19.jsp" target="main" onclick="change('img19','images/gdlist2_19.jpg')" ><img name="img19" id="img19" border="0" src="images/gdlist_19.jpg" width="201" height="89" /></a></td>
          </tr>
          <tr>
            <td align="center"><a  href="/portals/car/main_20.jsp" target="main"  onclick="change('img20','images/gdlist2_20.jpg')" ><img name="img20" id="img20" border="0" src="images/gdlist_20.jpg" width="201" height="94" /></a></td>
          </tr>
          <tr>
            <td align="center"><a  href="/portals/car/main_21.jsp" target="main"  onclick="change('img21','images/gdlist2_21.jpg')"  ><img name="img21" id="img21" border="0" src="images/gdlist_21.jpg" width="201" height="91" /></a></td>
          </tr>
        </table></td>
        <td valign="top" background="images/gdlist_06.jpg"> 
        
        <iframe name=main id=main marginwidth=0 marginheight=0 src="" frameborder=0 width=100% scrolling=no height=480 allowtransparency></iframe> 
        
        </td>
        <td width="4" background="images/gdlist_24.jpg"></td></tr>
    </table>
    
    
    </td>
  </tr>
  <tr>
    <td height="35" colspan="2" align="center" background="images/gdlist_27.jpg">版权所有：广东省小汽车定编中心 Copyright2010,All Rights Reserved </td>
  </tr>
</table>
<script type="text/javascript"> 
    var preimgid = "";
    var id= "${param.img}";
    var preimg = ""; 
   
    
    if(id != ""){   
        preimgid = "img"+id;
        var img =document.getElementById(preimgid);
        img.src = "images/gdlist2_"+id+".jpg";
        document.getElementById("main").src = "/portals/car/main_"+id+".jsp";
    }
    
    
    function change(imgID, imgurl) {
        document.getElementById(preimgid).src="images/gdlist_"+id+".jpg";  
    	preimgid = imgID; 
        document.getElementById(imgID).src = imgurl;
        id=imgID.substring(3,5);  
    } 
</script>

</body>
</html>
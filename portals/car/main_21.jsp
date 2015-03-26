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
}
</style>
</head>

<body>  
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="29">&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><table width="659" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="170"><img src="images/gdlist_15.jpg" width="170" height="67" /></td>
                <td width="489" align="left" background="images/gdlist_16.jpg"><table width="462" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="291" align="center"><input name="textfield" class="searchborder2" size="14" /></td>
                    <td width="171" align="center"><input type="image" name="imageField" id="imageField" src="images/sear_19.jpg" style="blr:expression(this.onFocus=this.blur())" />                    </td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="49">&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><iframe name=contetn marginwidth=0 marginheight=0 src="/portals/car/info.jsp" frameborder=0 width=659 scrolling=no height=280 allowtransparency></iframe></td>
          </tr>
        </table> 
<br />
</body>
</html>
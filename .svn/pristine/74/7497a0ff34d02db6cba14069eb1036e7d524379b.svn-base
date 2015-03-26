<%@ page contentType="text/html; charset=UTF-8"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%> 
<%@page import="com.cyberway.core.utils.StringUtil"%>
<%@page import="com.cyberway.core.acegi.utils.Md5PasswordEncoder"%>
<%@page import="org.acegisecurity.providers.encoding.PasswordEncoder"%>
<%@page import="com.cyberway.core.utils.property.DefaultProperty"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广东省小汽车定编信息查询系统</title>
<link href="sy_style.css" rel="stylesheet" type="text/css"></link>

<%
	String loginno = request.getParameter("loginno");
	String pass = request.getParameter("pass");
	PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
	int flag = 0;
	if(!StringUtil.isEmpty(loginno) && !StringUtil.isEmpty(pass)){ 
		
	//	 String url=DefaultProperty.getProperty("db.url",""); 
	//	  String driver=DefaultProperty.getProperty("db.driver","");  
		 String url="jdbc:sqlserver://192.168.0.190:8433;User=cms3;Password=helloworld;DatabaseName=cms3_qc"; 
		  String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";         
	  try{ 
		  Class.forName(driver);    
		   Connection   conn  =DriverManager.getConnection(url);   
	   Statement   stmt=conn.createStatement();     
	   String sql="SELECT LOGINID, PASSWORD,USERNAME,DEPTNAME FROM CORE_USER WHERE LOGINID='"+loginno+"' AND PASSWORD='"+passwordEncoder.encodePassword(pass,null)+"'";     
	   ResultSet   rs   =   stmt.executeQuery(sql);   
	   while(rs.next())   {     
	       session.setAttribute("userName",rs.getString("USERNAME"));
	       session.setAttribute("deptName",rs.getString("DEPTNAME"));
	       flag = 1;
	   } 
	   rs.close();
	   stmt.close();  
	  }catch(Exception e){   
		   e.printStackTrace();   
		  System.out.println("数据库连接失败");   
	  }
	}else{
		session.removeAttribute("userName");
		session.removeAttribute("deptName");
	}
%> 
<script type="text/javascript">
	var flag = "<%=flag%>";
	
	if(flag == "1")
		window.location = "/portals/car/index.jsp";

	function save(){
	    if(document.getElementById("loginno") == ""){
	       alert("用户名不能为空！"); return false;
	    }
	    
	    if(document.getElementById("pass") == ""){
	       alert("用户名密码不能为空！"); return false;
	    }
	    
	    
	    document.getElementById("loginForm").submit()
	} 
</script>
<style type="text/css">
body {
	font-family: "宋体", "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	color:#434343;
	margin: 0px;
	padding: 0px;
	background:url(images/gdcar_01.jpg) left top repeat-x;
}
</style>
</head>

<body>  
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="14%">&nbsp;</td>
    <td width="70%" align="center"><table width="981" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="981"><img src="images/gdcar_03.jpg" width="981" height="287" /></td>
      </tr>
      <tr>
        <td><img src="images/gdcar_05.jpg" width="981" height="116" /></td>
      </tr>
      <tr>
        <td height="139" valign="top" background="images/gdcar_06.jpg">
        <form action="/portals/car/login.jsp" method="post" id="loginForm" style="margin: 0px;">
        <table width="981" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="397" height="56">&nbsp;</td>
              <td width="584" align="left"><table width="99%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="36%" height="43" valign="bottom"><input id="loginno" name="loginno" class="searchborder" size="14" /></td>
                    <td width="26%" valign="bottom"><input name="pass" id="pass" class="searchborder" size="14" type="password" onkeypress="if(event.keyCode == 13) save();" /></td>
                    <td width="38%" valign="bottom"><img src="images/login.jpg" alt="登录" width="98" height="30" onclick="save()" /></td>
                  </tr>
              </table></td>
            </tr>
        </table></form> 
        </td>
      </tr>
      <tr>
        <td height="88"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" class="w">版权所有：广东省小汽车定编中心 Copyright2010,All Rights Reserved 备案编号：粤ICP 备000000001号</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    <td width="16%">&nbsp;</td>
  </tr>
</table>
<br />
</body>
</html>
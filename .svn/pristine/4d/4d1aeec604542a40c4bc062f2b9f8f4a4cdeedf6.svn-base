<%@ page contentType="text/html; charset=UTF-8"%> 
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.cyberway.core.utils.StringUtil"%>
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

	String keyNo = request.getParameter("keyNo");
	 
	if(!StringUtil.isEmpty(keyNo)){ 
	  String url="jdbc:sqlserver://192.168.0.190:8433;User=cms3;Password=helloworld;DatabaseName=cms3_qc"; 
	  String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";      
	  try{ 
	  Class.forName(driver);    
	   Connection   conn  =DriverManager.getConnection(url);   
	   Statement   stmt=conn.createStatement();     
	   String sql="select * from  car_wrrantcert where WARRANTCERTNO = '"+keyNo+"'";
	   ResultSet   rs   =   stmt.executeQuery(sql);   
	   while(rs.next())   {  
		   //pk,orgid,orgname,orgcharacter,carno,engineno,skeletonno,ishomemade,carclass,cartype,usage,regtime,yearauditdate,
		   //id,单位编号,单位名称,单位性质,车牌号,发动机号,车架号,是否国产,车型,车种,用途,登记日期,年审日期,
		   //isprint,quotacertno,note,issend,sendtime,acctypewhencancel,sentusecert,district		   
		   //是否出证,定编证号,备注,是否发放,发放日期,办理类型,是否已发使用证,区/县
	     	request.setAttribute("orgid",rs.getString("orgid"));
	     	request.setAttribute("cartype",rs.getString("cartype"));
	     	request.setAttribute("orgname",rs.getString("orgname"));
	     	request.setAttribute("carno",rs.getString("carno"));
	     	request.setAttribute("cartype",rs.getString("cartype"));
	     	request.setAttribute("cartype",rs.getString("cartype"));
	     	request.setAttribute("cartype",rs.getString("cartype"));
	     	request.setAttribute("cartype",rs.getString("cartype"));
		   
		   
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
</head>

<body style="background:tranparent;">
<table width="659" border="0" align="center" cellpadding="8" cellspacing="1" bgcolor="#7ecef4">
  <tr>
    <td width="89" class="tdbg">单位编号</td>
    <td width="290" class="tdzi">${orgid}</td>
    <td width="93" class="tdbg">车种</td>
    <td class="tdzi" width="142">${cartype}</td>
  </tr>
  <tr>
    <td class="tdbg">单位名称</td>
    <td class="tdzi">粤电集团</td>
    <td class="tdbg">车牌号</td>
    <td class="tdzi">${carno}</td>
  </tr>
  <tr>
    <td class="tdbg">车架号</td>
    <td class="tdzi">CN-020-666</td>
    <td class="tdzi">粤A CF808</td>
    <td class="tdbg">车牌号</td>
  </tr>
  <tr>
    <td class="tdbg"> 发动机号</td>
    <td class="tdzi">CN20090115CC</td>
    <td class="tdbg">许可证号</td>
    <td class="tdzi">20090115</td> 
  </tr>
  <tr>
    <td class="tdbg">排气量</td>
    <td class="tdzi">5430</td>
    <td class="tdbg">产地</td>
    <td class="tdzi">广州</td>
  </tr>
  <tr>
    <td class="tdbg">登记日期</td>
    <td class="tdzi">2010-01-01</td>
  </tr>
  <tr>
    <td height="52" valign="top" class="tdbg">
    备注</td>
    <td colspan="3" valign="top" class="tdzi">无</td>
  </tr>
</table>
</body>
</html>
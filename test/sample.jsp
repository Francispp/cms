<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="ww" uri="/webwork" %> 
<%@ taglib prefix="cms" uri="cms" %>
<%@ taglib prefix="jodd" uri="http://www.springside.org.cn/jodd_form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>业务资讯网登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<script type="text/javascript">
    function test(errorString, exception)
    {
        alert(errorString);
    }

</script>
<style type="text/css">
	table tr td{border:none;border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #CCC;padding: 3px;}
</style>
<body>

<form name="_item" method="post" action="" >

<table width="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17%">&nbsp;</td>
	<td width="17%">&nbsp;</td>
    <td width="38%">&nbsp;</td>
    <td width="28%">&nbsp;</td>
  </tr> 
   
     <tr>
     <% 
     java.util.Date curr_date=new java.util.Date();
     String loginno="admin@admin@ @ @易联网";
     //com.cyberway.core.utils.RequestUtil.setCookie(response, "mytest_ch",java.net.URLEncoder.encode(loginno,"utf-8"), -1);
     long tparm=com.cyberway.core.utils.EncryptionHelper.getTimeParm(curr_date);
     String after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt(loginno,com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
     after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
     String after_tparm= com.cyberway.core.utils.EncryptionHelper.encrypt(String.valueOf(tparm),com.cyberway.core.utils.EncryptionHelper.PASS_PHRASE);     
     after_tparm=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_tparm);
     %>
	<td align="right">&nbsp;&nbsp;</td>
    <td>&nbsp;&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>

  <tr>
	<td align="right">优惠顾客(PC)：</td>
    <td>
		<select id="PC_role">
			<%
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090215@优惠顾客PC_普通销售A@PC@A@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>普通销售A</option>");
			%>
		</select>
	</td>
    <td><input type="button" onclick="loginAction('PC_role');" value="登录"/></td>
    <td>&nbsp;</td>
  </tr>

  <tr>
	<td align="right">销售代表(SR)：</td>
    <td>
		<select id="SR_role">
			<%
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090245@销售代表SR_普通销售A@SR@A@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>普通销售A</option>");
			%>
		</select>
	</td>
    <td><input type="button" onclick="loginAction('SR_role');" value="登录"/></td>
    <td>&nbsp;</td>
  </tr>

 
  <tr>
	<td align="right">经销商(SA)：</td>
    <td>
		<select id="SA_role">
			<%
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090205@经销商SA_普通销售A@SA@A@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>普通销售A</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN086218@经销商SA_营销助理B@SA@B@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销助理B</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN086214@经销商SA_营销主任C@SA@C@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销主任C</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN086217@经销商SA_高级营销主任N@SA@N@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>高级营销主任(DEFN)</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090445@经销商SA_营销经理G@SA@G@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销经理G</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090345@经销商SA_高级营销经理H@SA@H@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>高级营销经理H</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN010245@经销商SA_营销总监M@SA@M@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销总监(IJKLM)</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN095145@经销商SA_助理营销经理P@SA@P@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>助理营销经理P</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN095195@经销商SA_全球政策咨询委员O@SA@O@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>全球政策咨询委员O</option>");
			%>
		</select>
	</td>
    <td><input type="button" onclick="loginAction('SA_role');" value="登录"/></td>
    <td>&nbsp;</td>
  </tr>

  <tr>
	<td align="right">服务网点(SS)：</td>
    <td>
		<select id="SS_role">
			<%
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090235@服务网点SS_普通销售A@SS@A@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>普通销售A</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN076218@服务网点SS_营销助理B@SS@B@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销助理B</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN085214@服务网点SS_营销主任C@SS@C@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销主任C</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN086017@服务网点SS_高级营销主任F@SS@F@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>高级营销主任(DEFN)</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090415@服务网点SS_营销经理G@SS@G@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销经理G</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090311@服务网点SS_高级营销经理H@SS@H@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>高级营销经理H</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN020245@服务网点SS_营销总监I@SS@I@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销总监(IJKLM)</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090145@服务网点SS_助理营销经理P@SS@P@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>助理营销经理P</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN095245@服务网点SS_全球政策咨询委员O@SS@O@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>全球政策咨询委员O</option>");
			%>
		</select>
	</td>
    <td><input type="button" onclick="loginAction('SS_role');" value="登录"/></td>
    <td>&nbsp;</td>
  </tr>

  <tr>
	<td align="right">准经销商(PAA)：</td>
    <td>
		<select id="PAA_role">
			<%
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090245@准经销商PAA_普通销售A@PA@A@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>普通销售A</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN066218@准经销商PAA_营销助理B@PA@B@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销助理B</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN086314@准经销商PAA_营销主任C@PA@C@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销主任C</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN076217@准经销商PAA_高级营销主任D@PA@D@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>高级营销主任(DEFN)</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090435@准经销商PAA_营销经理G@PA@G@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销经理G</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090349@准经销商PAA_高级营销经理H@PA@H@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>高级营销经理H</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN030245@准经销商PAA_营销总监I@PA@I@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>营销总监(IJKLM)</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN090147@准经销商PAA_助理营销经理P@PA@P@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>助理营销经理P</option>");
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt("CN075215@准经销商PAA_全球政策咨询委员O@PA@O@易联网",com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
			after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
			out.println("<option value='"+after_encrypt+"'>全球政策咨询委员O</option>");
			%>
		</select>
	</td>
    <td><input type="button" onclick="loginAction('PAA_role');" value="登录"/></td>
    <td>&nbsp;</td>
  </tr>

  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>

</table>
</form>
</body>
</html>
<script type="text/javascript">
function decodeFromGb2312(str)
{
var strOut = ''; 
 for (var i=0;i<str.length; i++){ 
var c = str.charAt(i);      // +是空格  
  if (c == '+'){ 
  strOut += ' ';   
     }     
else if (c != '%'){
strOut += c;      
}     
 else{
  i++; 
 var nextC = str.charAt(i);         // 数字，则不是汉字  
 if (!isNaN(parseInt(nextC))){  
i++; strOut += decodeURIComponent(c+nextC+str.charAt(i));         }     
else{ var x = new String(); 
           try {     
 var code = str.substr(i,2)+str.substr(i+3,2); 
   i = i + 4; 
     var index = -1; 
       while ((index = z.indexOf(code,index+1)) != -1){  
       	                if (index%4 == 0){    
       	                                 strOut += String.fromCharCode(index/4+19968);    
       	                                                  break;                  }    
         }            }catch(e){}     
             }      }   }  
              return strOut;
              }
              
              
function getCookie(cookieName, dfltValue)
{
    var lowerCookieName = cookieName.toLowerCase();
    var cookieStr = document.cookie;
    
    if (cookieStr == "")
    {
        return dfltValue;
    }
    
    var cookieArr = cookieStr.split("; ");
    var pos = -1;
    for (var i=0; i<cookieArr.length; i++)
    {
        pos = cookieArr[i].indexOf("=");
        if (pos > 0)
        {
            if (cookieArr[i].substring(0, pos).toLowerCase() == lowerCookieName)
            {
                return unescape(cookieArr[i].substring(pos+1, cookieArr[i].length));
            }
        }
    }
    
    return dfltValue;
}
function utf8_decode(utftext) {
    var string = "";   
    var i = 0;   
    var c = c1 = c2 = 0;   
    while ( i < utftext.length ) {   
        c = utftext.charCodeAt(i);   
        if (c < 128) {   
            string += String.fromCharCode(c);   
            i++;   
        } else if((c > 191) && (c < 224)) {   
            c2 = utftext.charCodeAt(i+1);   
            string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));   
            i += 2;   
        } else {   
            c2 = utftext.charCodeAt(i+1);   
            c3 = utftext.charCodeAt(i+2);   
            string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));   
            i += 3;   
        }   
    }   
    return string;   
}  
              
	function loginAction(id){
		var sel = document.getElementById(id);
		var index  = sel.options.selectedIndex;
		var val = sel.options[index].value;
		var url = '${ctx}/cms/ebizlogon.action?loginid='+val+'&style=<%=after_tparm%>';
		window.open(url);
	}
</script>

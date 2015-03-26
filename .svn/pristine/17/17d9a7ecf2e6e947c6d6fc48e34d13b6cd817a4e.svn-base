<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>
<link href="${ctx}/styles/flow.css" type=text/css rel=stylesheet>
<script language="javascript">
//****************************
//	鼠标移动到对象上显示的颜色
//	obj：对象（例：this）
//	color：颜色码（例：#ffffff）
//****************************
function mouseoverColor(obj,color){
	obj.bgColor=color;	
}

//****************************
//	鼠标移出对象时显示的颜色
//	obj：对象（例：this）
//	color：颜色码（例：#ffffff）
//****************************
function mouseoutColor(obj,color){
	obj.bgColor=color;	
}
function changeTab(obj){
 var features="width=800,height=600,resizable=1,scrollbars=1";
 var wurl='';
 if(obj==''){
  window.location.href="track.action?id=${id}&process=${processid}";
  return ;
  }
 if(obj=='picture'){
  wurl="track!picture.action?id=${id}&process=${processid}";
  window.open(wurl,'myWin',features);
   return ;
 }
}
</script>
<body leftmargin="0" topmargin="0">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="29"><div id="nav_aty">    
          <table width="140" border="0" cellspacing="0" cellpadding="0" style="float:left;">
            <tr>
              <td height="4" colspan="3"></td>
            </tr>
            <tr>
              <td align="center" valign="bottom" id="at1" style="background-image:url(${ctx}/images/common/button1.jpg); height:25px; width:70px;" onclick=""><a class="link1" style="cursor:hand">流程记录</a></td>
              <td  align="center" valign="bottom" id="at2" style="background-image:url(${ctx}/images/common/button.jpg);height:25px; width:70px;" onclick="changeTab('picture')"><a class="link1" style="cursor:hand">流程图型</a></td>            
            </tr>
          </table>
</td>
      </tr>
      </table>          
<TABLE width="100%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#999999" style="font-size:12;"  class="none">
  <TBODY>
   <TR height=24 bgcolor="#E1E1E1">    
    <TD width="4%">
      <DIV align=center>序号</DIV></TD>
    <TD width="10%">
      <DIV align=center>活动名</DIV></TD>
    <TD width="9%" >
      <DIV align=center>部门</DIV></TD>
     <TD width="12%">
      <DIV align=center>姓名</DIV></TD>
    <TD width="14%" >
      <DIV align=center>接收时间</DIV></TD>
     <TD width="14%">
      <DIV align=center>完成时间</DIV></TD>	
     <TD width="24%">
      <DIV align=center>备注</DIV></TD>	        
	  </TR>
<ww:iterator value="items" status="index">
  <TR height=20  bgcolor="#ffffff"  onmouseover="mouseoverColor(this,'#EEFFFF')" onmouseout="mouseoutColor(this,'#FFFFFF')" >    
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><script>document.write(<ww:property value="#index.index"/>+1);</script></TD>
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><ww:property value="activityname"/></TD>
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><ww:property value="deptName"/></TD>
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><ww:property value="userName"/></TD>
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><ww:property value="acceptedDate"/></TD>
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><ww:property value="endDate"/></TD>
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><ww:property value="examineContent"/>&nbsp;</TD>
 </TR>
	</ww:iterator> 
</TBODY>
</TABLE>
<%@ include file="/common/foot.inc"%>
<script language="javascript">
//dyniframesizeforall("trackFrame");
//setParentFrameSize();
</script>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="问卷调查" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
</head>
<body>
<form method="post" action="${ctx}/survey/answer!saveOrUpdate.action" name="myform" id="myform">
<ww:hidden name="domain.id" value=""/>
<ww:hidden name="domain.questionnaireId" value="%{domain.id}"/>
<ww:hidden name="tourl" value="%{domain.turnToPage}"/>

<table style="margin-left:1px;" width="99%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#dfedef">
	<tr>
		<td align="center" valign="top">
		
		<div id="questionnairesubject" style="height:45px;padding:25px 15px 15px 15px;text-align:center;letter-spacing:3px;font-size:32px;font-family:'黑体';color:#000000;font-weight:bold;font-style:normal;line-height:22px;">
		${domain.name }</div>
		
		<div id="welcome" style="height:55px;padding:5px 25px 25px 25px;text-align:left;word-break:break-all;">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		${domain.welcome }</div>
		
		<ww:set name="qtypes" value="{'短答','单选','多选','单选短答','多选短答','自由长答'}"/>
		<c:forEach items="${domain.questions}" var="item" varStatus="sta">
<table width="98%" cellspacing="1" cellpadding="3" border="0" bgcolor="#d8e7fe" align="center" style="margin-top:15px;">
  <tbody>
    <tr bgcolor="#e9f1fd">
      <td bgcolor="#e9f1fd" background="../../images/title_bj_s.jpg" onMouseOut="this.style.background='#E9F1FD'" onMouseOver="this.style.background='#FDEED1'" id="abc"><table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tbody>
            <tr>
              <td width="80%" align="left"><font color="#000000">&nbsp;<b>${sta.count }. ${item.content }</b></font></td>
              <td width="20%" align="right"> </td>
            </tr>
          </tbody>
        </table></td>
    </tr>
    <tr>
      <td valign="top" bgcolor="#ffffff"><table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tbody>
            <tr>
              <td width="26" align="left"></td>
              <td height="26" align="left">
              	<input type="hidden" name="questionIds[${sta.index}]" value="${item.id }"/>
                <c:forEach items="${item.selectOptions}" var="opt" varStatus="optstatus">
		          <input type="${(item.type eq '1' || item.type eq '3')?'radio':'checkbox' }" value="${opt.id }" name="optAnswers[${sta.index}]" id="opt_${opt.id }"/>
		          <label for="opt_${opt.id }">${opt.content }</label>
		          <c:if test="${item.optDirect eq 1}"><br/></c:if>
		        </c:forEach>
                <ww:if test="%{item.type eq '0'}">
                  	回答：<input type="text" name="txtAnswers[${sta.index}]"/>
                </ww:if>
                <ww:elseif test="%{item.type eq '3'}">
                	其它：<input type="text" name="txtAnswers[${sta.index}]"/>
                </ww:elseif>
                <ww:elseif test="%{item.type eq '4'}">
                	其它：<input type="text" name="txtAnswers[${sta.index}]"/>
                </ww:elseif>
                <ww:elseif test="%{item.type eq '5'}">
                	回答：<textarea name="txtAnswers[${sta.index}]"></textarea>
                </ww:elseif>
                </td>
            </tr>
          </tbody>
        </table></td>
    </tr>
  </tbody>
</table>
		</c:forEach><%-- 问题结束 --%>
		
                
	  <br/><br/>
	  <input type="submit" value=" 提 交 "/>
	  <input type="reset" value=" 重 置 "/>
	  <br/>
	</td>
</tr>
</table>
</form>
</body>
</html>


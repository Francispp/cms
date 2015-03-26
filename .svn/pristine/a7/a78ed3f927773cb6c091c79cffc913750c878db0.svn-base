<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/js.inc"%>
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<title>流程处理</title>
<body leftmargin="0" topmargin="0"><br>
<form method="post" action=""  name="myform">
<div align="center">
<%@ include file="head.inc"%>
<table width="100%" border="0" cellspacing="1">
    <tr> 
      <td height="30"><div align="center">${processname} ${activityname}${activityid}</div></td>
    </tr>
    <tr> 
      <td height="200"><div align="center">
<table width="100%" border="0">
  <tr> 
    <td width="20%">&nbsp;</td>
    <td width="91%">&nbsp;
    <!--<input type="checkbox" name="checkbox" value="checkbox">指定下一步执行者
      --></td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td>处理意见:
      <select name="select_examineContent" onchange="onchangeExamineContent();">
      <option value=""></option>
      <option value="同意">同意</option>
      <option value="不同意">不同意</option>
      </select></td>
  </tr>
  <tr>
    <td height="122">&nbsp;</td>
    <td><textarea name="textarea" name="permObj.params.examineContent" id='examineContent' rows="6" cols="60"></textarea></td>
  </tr>
</table>
        </div></td>
    </tr> 
    </table>
<%@ include file="foot.inc"%>
</div>
</form>
</body>
</html>
<script language="javascript">
function onchangeExamineContent(){
 document.getElementById('examineContent').value=document.getElementById('select_examineContent').value;
}
alert();
var url=replaceAll(window.location.href,'transact','comple');
 myform.action=url;
</script>
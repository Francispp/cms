<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<html>
<head>
<title>公共选择链接</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
<%@ include file="/common/js.inc"%>
</head>
<script type="text/javascript">
    function test(errorString, exception)
    {
        alert(errorString);
    }

</script>
<body>
<%@ include file="/test/menu.inc"%>
<form name="_item" method="post" action="">
<table width="89%" border="0">
  <tr>
    <td width="14%">&nbsp;</td>
    <td width="58%">&nbsp;</td>
    <td width="28%">&nbsp;</td>
  </tr>
  <tr>
    <td>1.<a href="javascript:">用户选择</a></td>
    <td>用户ID
      <input name="userid" type="text" id="userid" size="10">
      用户名
      <input name="username" type="text" id="username" size="15">
      多选
       <input type="checkbox" name="checkbox" id='user_multi' value="checkbox">
      </td>
    <td><input type="button" name="button" onclick="selectUserEx(document.getElementById('userid'),document.getElementById('username'),document.getElementById('user_multi').checked)" value="选择用户"></td>
  </tr>
  <tr>
    <td>2.<a href="javascript:">部门选择</a></td>
    <td>部门ID
      <input name="deptid" type="text" id="deptid" size="10">
      部门名
      <input name="deptname" type="text" id="deptname" size="15">
      多选
       <input type="checkbox" name="checkbox" id='dept_multi' value="checkbox">
      </td>
    <td><input type="button" name="button" onclick="selectDeptEx(document.getElementById('deptid'),document.getElementById('deptname'),document.getElementById('dept_multi').checked)" value="选择部门"></td>
  </tr>
  <tr>
    <td>3.<a href="javascript:">角色选择</a></td>
    <td>角色ID 
      <input name="userid" type="text" id="groupid" size="10">
      角色名
      <input name="username" type="text" id="groupname" size="15">
      多选
       <input type="checkbox" name="checkbox" id='group_multi' value="checkbox">
      </td>
    <td><input type="button" name="button" onclick="selectRoleEx(document.getElementById('groupid'),document.getElementById('groupname'),document.getElementById('group_multi').checked)" value="选择角色"></td>
  </tr> 
   <tr>
    <td>4.<a href="javascript:">用户对象选择</a></td>
    <td>对象ID 
      <input name="userid" type="text" id="objectid" size="10">
      对象名
      <input name="username" type="text" id="objectname" size="15">
      多选
       <input type="checkbox" name="checkbox" id='object_multi' value="checkbox">
      </td>
    <td><input type="button" name="button" onclick="selectUserObjcectEx(document.getElementById('objectid'),document.getElementById('objectname'),document.getElementById('object_multi').checked)" value="选择用户对象"></td>
  </tr>    
   <tr valign="top">
    <td valign="top">5.<a href="javascript:">JS组件选择</a></td>
    <td>功能名 <input name="jsfuncName" type="text" id="jsfuncName" size="10">
	<br/>
      脚&nbsp;&nbsp;&nbsp;&nbsp;本:
      <br><textarea name="jscode" cols="42" rows="6" id="jscode"></textarea>
      </td>
    <td><input type="button" name="button" onclick="selectJSComponent()" value="选择JS组件"></td>
  </tr>
  <tr>
    <td>4.<a href="javascript:">外部用户对象选择</a></td>
    <td>对象ID 
      <input name="webuserid" type="text" id="webuserid" size="10">
      对象名
      <input name="webusername" type="text" id="webusername" size="15">
      </td>
    <td><input type="button" name="button" onclick="selectWebusers('${ctx }/cms/webuser!selectUsers.action',$('webuserid'),$('webusername'))" value="选择用户对象"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>

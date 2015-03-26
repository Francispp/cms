<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="用户组管理" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/js.inc"%>
<script type="text/javascript">
function save(){
 myform.submit();
}
function goBack(){
 location.href='group.action?style=<ww:property value="style" />&deptid=<ww:property value="deptid" />';
}
</script>
<ww:head/>
</head>
<body  nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<div id="titleDiv"><c:out value="${title}"/></div>
<!-- 操作功能按钮条 -->
<div id="operationDivNoBorder">
 <button id="backbutton" onclick="save()" class="button">保存</button>
 <button id="backbutton" onclick="goBack()" class="button">返回</button>
</div>
<!-- 页面主要内容 -->
<div id="page-content-no-border">
<form method="post" action="group!saveOrUpdate.action" name="myform">
<!--ww:hidden  name="domain.groupid" id="deptid"/-->
<ww:hidden  name="deptid" id="deptid"/>
<ww:hidden  name="style" id="style"/>
<table align="center" width="100%" border="1">
     <tr>
            <td width="40%"  class="right">用户组编号：</td>
            <td width="60%" class="left">
             <div align="left"><ww:textfield name="domain.groupid" readonly="true" /></div>
            </td>
    </tr>
     <tr>
            <td  class="right">用户组名称：</td>
            <td  class="left">
             <div align="left"><ww:textfield name="domain.groupname"  cssClass="textField_ab textField-w150_ab max-length-24"/></div>
            </td>
     </tr>
     <tr>            
            <td  class="right">所属部门：</td>
            <td  class="left">
             <div align="left"> <ww:textfield name="domain.coreDept.deptname" cssClass="textField_ab textField-w150_ab max-length-24"/></div>
             <ww:hidden  name="domain.coreDept.deptid" id="coreDept.deptid"/>
            </td>
    </tr>
      <tr>
            <td class="right">备注：</td>
            <td class="left">
             <div align="left"><ww:textarea name="domain.remark" cols="50" rows="5" cssClass="textField_ab textField-w150_ab max-length-60"/></div>
            </td>
    </tr>                                   
</table>
</form>
</div>
<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>

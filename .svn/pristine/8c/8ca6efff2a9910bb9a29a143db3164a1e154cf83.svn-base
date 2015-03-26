<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="部门管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<script type="text/javascript">
function save(){
 if(valid.validate())
   myform.submit();
}
function goBack(){
 location.href='dept.action?pageStyle=<ww:property value="pageStyle" />';
}
</script>
<ww:head/>
</head>
<body style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->

<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<tr><td  align="left">
	<table width="100%" align="left"   border="0" cellspacing="0" cellpadding="0"  bgcolor="#ffffff"> 
	<tr>
		<td >
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>
	</table>
	</td>
	</tr>
	<!-- 操作栏 -->
	<tr>
		<td valign="top"  height="100%" >
		<table width="100%" align="left"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td >
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				 <ul>
				  <c:if test="${isEdit==true}"> 
				  <li id="button"><a href="javascript:save()">保存</a></li>
				  </c:if>
				 <c:if test="${domain.deptid!=null}&&#session.loginer.haveThePermission(\"SYS_DEPT_MANAGE_GRANT\")">
				 <li id="button"><a href="javascript:doAccreditPermission(${domain.deptid},0);">赋权</a></li>
				 </c:if>
				  <ww:if test="pageStyle!=0">
				  <li id="button"><a href="javascript:goBack()">返回</a></li>
				  </ww:if>
				  </ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<!-- 页面主要内容 -->	
       <tr>
        <td align="center" valign="top">
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#dfedef;">
   <tr>
     <td align="center"> 
<!-- 页面主要内容 -->
<form method="post" action="dept!saveOrUpdate.action" name="myform">
<!--ww:hidden  name="domain.deptid" id="deptid"/-->
<ww:hidden  name="domain.coreOrg.oid" id="coreOrg.oid"/>
<ww:hidden  name="pageStyle" id="pageStyle"/>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td width="50%" align="center" class="view_content_td">
      <table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td width="23%" align="left">部门编号：</td>
          <td width="77%" align="left">
            <ww:textfield name="domain.deptid" readonly="true" />
           </td>
          </tr>
        <tr>
          <td align="left">部门名称：</td>
          <td align="left">
          <ww:textfield name="domain.deptname" cssClass="required  max-length-24" />
          </td>
          </tr>
        <tr>
          <td align="left">上级部门名称：</td>
          <td align="left">
          <ww:textfield name="domain.pdeptname" id="pdeptname" readonly="true" />
          <ww:hidden  name="domain.pdeptid" id="pdeptid"/>
          </td>
          </tr>
        <tr>
          <td align="left">部门类型：</td>
          <td align="left">
          <ww:textfield name="domain.depttype"  cssClass="max-length-30" />
          </td>
          </tr>
        <tr>
          <td align="left">状态：</td>
          <td align="left">
          <ww:select name="domain.state" list="trueOfFalseMap1" />
          </td>
          </tr>                                        
      </table></td>
    </tr>
    <tr>
      <td align="center" class="view_content_td1">
      <table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td width="21%" align="left" valign="top">备注：</td>
          <td width="79%" align="left">
          <ww:textarea name="domain.remark" cols="40" rows="6" cssClass="max-length-120" />
          </td>
        </tr>
      </table>
      </td></tr></table>
    </form>
    </td></tr></table>
   </td></tr></table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>
</script>
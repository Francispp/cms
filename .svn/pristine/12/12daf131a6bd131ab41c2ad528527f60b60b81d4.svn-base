<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="模块管理" />
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
 location.href='module.action?pageStyle=<ww:property value="pageStyle" />';
}
</script>
<ww:head/>
</head>
<body style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
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
		<td valign="top"  height="100%">
		<table  width="100%" align="left"   border="0" cellspacing="0" cellpadding="0"  bgcolor="#ffffff">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2"> 
				<ul>
				  <c:if test="${isEdit==true}"> 
				  <li id="button"><a href="javascript:save()">保存</a></li>
				  </c:if>
				
				  <li id="button"><a href="javascript:goBack()">返回</a></li>
				
				</ul> 
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
	
	<tr><td>
       <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#dfedef">
       <tr><td align="center" valign="top">
<form method="post" action="module!saveOrUpdate.action" name="myform" id="myform">
<ww:hidden  name="pageStyle" id="pageStyle"/>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="view_content_table">
    <tr>
      <td width="50%" align="center" class="view_content_td">
      <table width="80%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td width="23%" align="left" valign="top">模块编号：</td>
          <td width="77%" align="left">
           <ww:textfield name="domain.oid" readonly="true" cssClass="textField_ab textField-w150_ab max-length-20"/>
           </td>
          </tr>
        <tr>
          <td align="left" valign="top">所属系统：</td>
          <td align="left">
          <ww:textfield name="domain.pcode" readonly="true"/>
          </td>
          </tr>
        <tr>
          <td align="left" valign="top">模块代码：</td>
          <td align="left">
          <ww:textfield name="domain.moCode"  size="40" cssClass="textField_ab textField-w150_ab max-length-20"/>
          </td>
          </tr>
        <tr>
          <td align="left" valign="top">模块名称：</td>
          <td align="left">
        <ww:textfield name="domain.moName"  size="40" cssClass="required textField_ab textField-w150_ab max-length-24"/>
          </td>
          </tr>
        <tr>
          <td align="left" valign="top">上级模块代码：</td>
          <td align="left">
        <ww:textfield name="domain.pmoCode"  readonly="true" size="40"/>
          </td>
          </tr>  
           <tr>
          <td align="left" valign="top">流程版本名称：</td>
          <td align="left">
           <ww:select name="domain.flowMgrname" list="processMgrs" emptyOption="true" />         
          </td>
          </tr> 
           <tr>
          <td align="left" valign="top">流程中文名称：</td>
          <td align="left">
        <ww:textfield name="domain.flowCname"  size="40" cssClass="textField_ab textField-w150_ab max-length-24"/>
          </td>
          </tr>                                       
      </table></td>
    </tr>
    <tr>
      <td align="center" class="view_content_td1">
      <table width="80%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td width="25%" align="left" valign="top">备注：</td>
          <td width="75%" align="left">
          <ww:textarea name="domain.remark" cols="50" rows="5" cssClass="textField_ab textField-w150_ab max-length-60"/>
          </td>
        </tr>
      </table>
      </td>
    </tr>
    </table>
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
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="菜单管理" />

<html>
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
 location.href='menu.action?pageStyle=<ww:property value="pageStyle" />';
}
function addMenu(pid,portalid){
 
 location.href='menu!edit.action?pmenuid='+pid+'&portalid='+portalid+'&pageStyle=<ww:property value="pageStyle" />';
}
</script>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}"/></div>
		</div>
		</div>
		</div>
		</td>
	</tr>



	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
  <c:if test="${isEdit==true}"> 
  <li id="button"><a href="javascript:save()">保存</a></li>
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
</table>
<!-- 页面主要内容 -->
<div id="view_scroll_content" align=justify>
<form method="post" action="menu!saveOrUpdate.action" name="myform">
<!--ww:hidden  name="domain.deptid" id="deptid"/-->
<ww:hidden  name="domain.portalid" id="portalid"/>
<ww:hidden  name="pageStyle" id="pageStyle"/>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="view_content_table">
    <tr>
      <td width="50%" align="center" class="view_content_td">
      <table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
            <td width="30%" align="left">菜单编号：</td>
            <td width="70%" align="left">
             <ww:textfield name="domain.oid" readonly="true" />
            </td>
    </tr>
     <tr>
            <td align="left">菜单名称：</td>
            <td align="left">
             <ww:textfield name="domain.menuname" cssClass="required max-length-24"/>
            </td>
     </tr>
     <tr>
            <td align="left">上级菜单名称：</td>
            <td align="left">
             <ww:textfield name="domain.pmname" id="pmname" readonly="true" />
             <ww:hidden  name="domain.pmid" id="pmid"/>
            </td>
     </tr>     
     <tr>            
            <td align="left">排序号：</td>
            <td align="left">
             <ww:textfield name="domain.orderno" cssClass="validate-digits"/>
            </td>
    </tr>
     <tr>            
            <td align="left">图标：</td>
            <td align="left">
             <ww:textfield name="domain.inco" cssClass="textField_ab textField-w150_ab max-length-24"/>
            </td>
    </tr> 
     <tr>            
            <td align="left">打开图标：</td>
            <td align="left">
             <ww:textfield name="domain.openInco"  cssClass="textField_ab textField-w150_ab max-length-24"/>
            </td>
    </tr>
     <tr>            
            <td align="left">关闭图标：</td>
            <td align="left">
             <ww:textfield name="domain.closeInco"  cssClass="textField_ab textField-w150_ab max-length-24"/>
            </td>
    </tr>        
    <tr>            
            <td align="left">菜单地址：</td>
            <td align="left">
             <ww:textfield name="domain.url" cssClass="required max-length-60" />
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
          <td width="30%" align="left" valign="top">备注：</td>
          <td width="70%" align="left">
             <ww:textarea name="domain.remark" cols="35" rows="4" cssClass="required max-length-120"/>
            </td>
    </tr>                                   
      </table>
      </td>
    </tr>
    </table>
</form>
</div>
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
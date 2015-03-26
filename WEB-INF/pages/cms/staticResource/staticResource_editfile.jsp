<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="静态资源基本属性" />

<html> 
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
<%@ include file="/common/validation.inc"%>
<script type="text/javascript">
function save(){
if(valid.validate())
 myform.submit();
}
function goBack(){
 location.href='staticResource.action?siteid=${domain.siteid}&pageStyle=<ww:property value="pageStyle" />';
}
</script>
<ww:head/>
</head>
<body  nowrap topmargin="0" leftmargin="0"  style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<!--<div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
  </div>
</div>

-->
<!-- 操作功能按钮条 -->
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	bgcolor="#FFFFFF">
	<tr>
		<td>
		<div id="titel">
		<div id="titel_left">
		<div id="titel_right">
		<div id="nav">
		<div>
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
		</div>
		</td>
	</tr>

<!-- 页面主要内容 -->

	<tr>
		<td>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" style="background-color:#dfedef;">
			<tr>
				<td align="center" valign="top">

<div id="view_scroll_content" align=justify style="height:auto;">
<form method="post" action="staticResource!saveResourceFile.action" name="myform" enctype="multipart/form-data">
<ww:hidden  name="pageStyle" id="pageStyle"/>
<ww:hidden  name="domain.id" id="domain.id"/>
<ww:hidden  name="domain.siteid" id="domain.siteid"/>
<ww:hidden  name="domain.uploadman" id="domain.uploadman"/>
<ww:hidden  name="domain.uploadtime" id="domain.uploadtime"/>
<ww:hidden  name="domain.serverpath" id="domain.serverpath"/>
<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="view_content_table">
    <tr>
      <td align="center" class="view_content_td" height="100%">
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="10">

        
           
    
        <tr>
          <td align="center" colspan="2" class="inside_list"><ww:property value="domain.name" />文件内容</td>
        
          </tr>  
          <tr>
          <td align="center" colspan="2" height="400"><ww:textarea name="domain._filecontent" cssStyle="width:100%;height:400  max-length-240"/></td>
        
          </tr>  
          
          
         	
  
      </td>
    </tr>
   
    </table>
    </form>
</div>

</td></tr></table></td></tr></table>

<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
</script>


<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="创建留言" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>
<link href="/styles/cms3/leaveWord.css" rel="stylesheet" type="text/css"></link>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script type="text/javascript">
function save(){
 if($F("content") == ""){
    alert("留言内容不能为空！");
    $("content").focus();
   	return false;
 } 
 myform.submit();
} 

function goBack(){
	window.location="/component/leaveword!list.action";
}
 
  
</script> 
<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0" style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
<!-- style="background-color:#dfedef;" -->	
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>

	<!-- 操作栏 -->
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
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
					<li id="button"><a href="javascript:goBack()">返回</a></li>
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
	
	<tr><td style="padding-left:2px;padding-right:1px;">
<table width="100%" border="0" align="center" style="margin-top:-3px;" bgcolor="#dfedef" height="100%">
	<tr>
		<td align="center" valign="top">
	 <table style="margin-top: -3px" border="0" width="100%" bgcolor="#ffffff" align="center" height="100%">
            <tbody>
                <tr>
                    <td valign="top" align="center">  
                    <form method="post" name="myform" action="/component/leaveword!saveOrUpdate.action">
                        <ww:hidden id="domain.oid" name="domain.oid"></ww:hidden>
						<ww:hidden id="status" name="domain.status" value="0"></ww:hidden>  
						<table  cellspacing="0" cellpadding="0" class="mframe">
						<tr> 
						  <td align="right"> <div class="fbart">标题：</div></td>
						  <td><div class="fbart"><ww:textfield name="domain.title" cssClass="text" maxlength="80" size="80"></ww:textfield></div></td> 
						</tr> 
						<tr> 
						  <td align="right"> <div class="fbart">联系方式：</div></td>
						  <td><div class="fbart"><ww:textfield name="domain.link" cssClass="text" maxlength="80" size="80"></ww:textfield></div></td> 
						</tr> 		
						<tr> 
						  <td align="right"> <div class="fbart">E-mail：</div></td>
						  <td><div class="fbart"><ww:textfield name="domain.email" cssClass="text" maxlength="80" size="80"></ww:textfield></div></td> 
						</tr>
						<tr> 
						  <td align="right"> <div class="fbart">站点编号：</div></td>
						  <td><div class="fbart"><ww:textfield name="domain.siteId" cssClass="text" maxlength="80" size="80"></ww:textfield></div></td> 
						</tr>
						<tr> 
						  <td align="right"> <div class="fbart">站点名称：</div></td>
						  <td><div class="fbart"><ww:textfield name="domain.siteName" cssClass="text" maxlength="80" size="80"></ww:textfield></div></td> 
						</tr>
						<tr> 
						  <td colspan="2"> 	
							<table cellspacing="0" cellpadding="0" class="mtxt">
								<tr><td align="center"> <ww:textarea id="content" name="domain.content" cssStyle="width:98%; height:150px; font-size:14px;"></ww:textarea></td></tr>
							</table>
						  </td> </tr> 
						  <tr>
						  <td  align="right" colspan="2">
						  	<div class="fbarb">
						   	<ul>
							<li><a href="javascript:;" onclick="save()" class="dr">提交</a></li>
							<li><a href="javascript:;" onclick="reset()" class="dr nob">清空</a></li>
							</ul> 
							</div> 
							</td></tr> 
						</table>  
                    </form>
               	 	</td>
				</tr>
			</tbody>
		</table> 
		</td>
	</tr>
</table>
</td></tr></table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%> 

</body>
</html>  
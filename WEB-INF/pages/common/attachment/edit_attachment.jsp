<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="附件上传" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css"/>	
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<script type="text/javascript">
var contextPath = '${ctx}';
</script>
<ww:head/>
</head>
<xml id="myXML"></xml>
<body>
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
  </div>
</div>
<!-- 操作栏 -->
<div id="nav">
 <div>
 <ul>
  <li id="button"><input type="button" value="上传新附件" onclick="newAttachment(window.dv1,'','',0);"/></li>
  </ul>
  </div>
</div>

<!-- 页面主要内容 -->
<div id="view_scroll_content" align=justify>
<form method="post" action="${ctx}/base/attachment.action" name="formList">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="view_content_table">
    
    <tr>
      <td align="center">
      <div id=dv1></div>
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
viewAttachment(window.dv1,'',0);
</script>

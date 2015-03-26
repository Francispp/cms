<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/menu/menu.inc"%>
<script src="${ctx}/common/core_js/core.js"></script>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script type="text/javascript"> 

     
</script>
</head>
<body style="background:#F1F1F1;"  >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<c:if test="${not empty session.ActionError}">
  <script>alertMessage("${session.ActionError}");</script>
  <% session.removeAttribute("ActionError"); %>
</c:if>
<table align="center" width="100%"  hight="100%" border="0">
     <tr>
            <td width="100%" align="left" hight="100%"> 
  
  <div id="siteTree"></div> 
 

            </td>
    </tr>               
</table> 
</body>
</html>
<script language="javascript">

<!--		
  tree=new dhtmlXTreeObject("siteTree","100%","100%",'0');
  tree.loadXML("${ctx}/base/distribution!addFile.action");
-->
</script>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="请选择导出报表" /> 
<html>
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<link href="${ctx}/styles/dtree.css" type="text/css" rel="stylesheet">
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/dtree/dtree.js"></script>
<script src="${ctx}/common/frame_js/select.js"></script>
<script language="JavaScript">
 var contextPath = '${ctx}';
</script>
</head>
<body topmargin="0"> 
<table width="100%" border="0" cellspacing="4">
  <tr>
    <td height="28" bgcolor="#FFFFFF"> <input type="button" name="btnOpen" value="展 开" class="bt-zhankai" onClick="d.openAll();">
      <input type='button' name='btnClose' value='折 叠' class='bt-zhedie' onClick='d.closeAll();'>
      <c:if test="${!empty multiSelect and multiSelect eq 'TRUE'}" >
      <input type='button' name='btnReturn' value='确 定' class='bt-enter' onClick='doReturn();'>
      </c:if>
      <input type="button" class="bt-cancel" onclick="doExit()" value="取消">
		  <input type="button" class="bt-empty" onclick="doEmpty()" value="清空">
       </td>
  </tr>
  <tr class="row-hd">
    <td height="2"> </td>
  </tr>
  <tr>
    <td> <div class="scroll-div">
        <div class="dtree">
          <script type="text/javascript">
					<!--
					d = new dTree('d');
					<c:if test="${!empty multiSelect and multiSelect eq 'TRUE'}" >
					d.config.multiSelect = true;
					</c:if>
					<c:choose>
						<c:when test="${!empty selectChild and selectChild eq 'TRUE'}" >
							d.config.selectChild = true;
						</c:when>
						<c:otherwise>
							d.config.selectChild = false;
						</c:otherwise>
					</c:choose> 
				  <c:forEach var="item" items="${requestScope.reports}" varStatus="status">
				   d.add('<c:out value="${item.reportid}" />',-1,'<c:out value="${item.reportName}" />',"javascript:selectOne(\'<c:out value="${item.reportid}" />;<c:out value="${item.reportName}" />\');","<c:out value="${item.reportid}" />;<c:out value="${item.reportName}" />", "", "${ctx}/images/dtree/page.gif",null,null,null,false);
				 </c:forEach>
			    document.write(d);
					//-->
				</script>
        </div>
      </div></td>
  </tr>
</table>
</body>
</html>
<script language="JavaScript">
 <c:if test="${!empty multiSelect and multiSelect eq 'TRUE' and !empty selectedids}" >
  doSetSelected('<c:out value="${selectedids}" />');
 </c:if>
</script>
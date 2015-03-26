<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="用户选择" />
<html>
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/styles/dtree.css" type="text/css" rel="stylesheet">
<link href="${default_style}" rel="stylesheet" type="text/css">
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
    <td height="28">
 				  
      <input type="button" name="btnOpen" value="展 开" class="bt-zhankai" onClick="d.openAll();">
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
    <td> <div class="scroll-div" >
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
 <c:forEach var="item" items="${requestScope.orgs}" varStatus="status">
   d.add('P_<c:out value="${item.oid}" />',-1,'<c:out value="${item.orgName}" />',null,"<c:out value="${item.oid}" />;<c:out value="${item.orgName}" />", "", "${ctx}/images/dtree/dept.gif",null,null,null,false);
 </c:forEach>
					<c:forEach var="item" items="${depts}" varStatus="status">
						<c:choose>
							<c:when test="${empty item.pdeptid}" >
								d.add('D_<c:out value="${item.deptid}" />','P_<c:out value="${item.coreOrg.oid}" />','<c:out value="${item.deptname}" />',null,"<c:out value="${item.deptid}" />;<c:out value="${item.deptname}" />", "", "${ctx}/images/dtree/dept.gif", "${ctx}/images/dtree/dept.gif",null,null,false);
							</c:when>
							<c:otherwise>
								d.add('D_<c:out value="${item.deptid}" />','D_<c:out value="${item.pdeptid}" />','<c:out value="${item.deptname}" />',null,"<c:out value="${item.deptid}" />;<c:out value="${item.deptname}" />", "", "${ctx}/images/dtree/dept.gif", "${ctx}/images/dtree/dept.gif",null,null,false);
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:forEach var="item" items="${users}">
					<c:set var="item_dept" value="${item.coreDept}" />
                                        d.add('<c:out value="${item.userid}" />','D_<c:out value="${item_dept.deptid}" />','<c:out value="${item.username}" />',"javascript:selectOne(\'<c:out value="${item.userid}" />;<c:out value="${item.username}" />\');","<c:out value="${item.userid}" />;<c:out value="${item.username}" />", "", "${ctx}/images/dtree/user.gif");
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
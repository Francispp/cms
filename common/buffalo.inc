<%@ page contentType="text/html; charset=UTF-8"%>
<script language="JavaScript" src='<c:url value="/common/buffalo/prototype.js"/>'></script>
<script language="JavaScript" src='<c:url value="/common/buffalo/template.js"/>'></script>
<script language="JavaScript" src='<c:url value="/common/buffalo/buffalo.js"/>'></script>
<script language="JavaScript" src='<c:url value="/common/core_js/core.js"/>'></script>
<script language="JavaScript">
var endPoint="<%=request.getContextPath()%>/ajax";
var waitgifpath="<%=request.getContextPath()%>";
var buffalo = new Buffalo(endPoint);
</script>
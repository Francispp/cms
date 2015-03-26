<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    <c:forEach var="msg" items="${actionErrors}">
        alert("${msg}");
    </c:forEach>
    location = '${request.contextPath }/survey/questionnaire!survey.action?rs=yes&id=${domain.questionnaireId }';
</script>
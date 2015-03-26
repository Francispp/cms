<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%//fn:escapeXml(msg)%>
<c:if test="${not empty actionMessages}">
    <!--span class="message" id="actionMessages"></span-->
        <c:forEach var="msg" items="${actionMessages}">
            <script>alertMessage("${msg}");</script>
        </c:forEach>    
</c:if>
<c:if test="${not empty actionErrors}">
    <!--span class="error" id="actionErrors"></span-->
        <c:forEach var="err" items="${actionErrors}">
        	<script>alert("${err}");</script>
        </c:forEach>    
</c:if>
 

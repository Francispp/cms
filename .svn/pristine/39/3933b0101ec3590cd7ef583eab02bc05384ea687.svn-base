<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <title>user search demo</title>
</head>

<body>
<div class="content">
<div class="search_field">
	<form method="post" action="${ctx}/sample/user!search.action">
		<div class="search_form">
			<p>Search users: <input type="text" name="queryString" class="search" value='${queryString}'/>
				<input type="submit" value="Search" class="submit"/>
				<a class="grey" href="#">Advanced</a>
			</p>
		</div>
	</form>
</div>
<div class="left">
    <c:if test="${not empty searchResults}">
        elapsed: <c:out value="${searchResults.searchTime}"/>ms
        <c:if test="${empty searchResults.hits}">
            没有找到符合条件的用户。<span style="color:red;font-weight:bold">请确保已在管理后台初始化索引。</span>
        </c:if>
        <c:forEach var="hit" items="${searchResults.hits}">

                    <div class="left_content">
                        <p>                             
                            <br/>
                            用户名:<a href="${ctx}/sample/user!edit.action?id=${hit.data.userId}"> ${hit.data.loginId}</a>
                            <br/>
                            电话: ${hit.data.phone}
                            <br/>
                           备注: ${hit.data.mark}
                        </p>
                    </div>
        </c:forEach>
    </c:if>
</div>
<c:if test="${! empty searchResults.pages}">
    <div class="left_pages">
        <c:forEach var="page" items="${searchResults.pages}" varStatus="pagesStatus">
            <c:choose>
                <c:when test="${page.selected}">
                    ${page.from}-${page.to}items
                </c:when>
                <c:otherwise>
                    <a href="javascript:$('advanceSearch').page.value=${pagesStatus.index};$('advanceSearch').submit();">${page.from}-${page.to}条</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</c:if>
</div>

</div>
</body>
</html>

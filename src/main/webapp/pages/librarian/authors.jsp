<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="users.users"/></title>
</head>
<body>

<br/>
<br/>

<table class="table">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="authors.name"/></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${authors_list}" var="author">
        <tr>
            <th><a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=go_to_author_page&author_id=${author.getId()}">
                    ${author.getFirstname()} ${author.getLastname()}</a></th>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br/>
<br/>


<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>
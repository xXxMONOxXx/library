<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="genres.genres"/></title>
</head>
<body>

<br/>
<br/>

<c:if test="${requestScope.delete_genre_success}">
    <p class="text-success"><fmt:message key="librarian.genre.delete_genre_success"/></p>
</c:if>

<table class="table">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="genres.genre_name"/></th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${genres_list}" var="genre">
        <tr>
            <td>${genre.getName()}</td>
            <td>
                <c:if test="${sessionScope.user_role eq 'ADMIN' or sessionScope.user_role eq 'LIBRARIAN'}">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/controller?command=go_to_update_genre_page&genre_id=${genre.getId()}">
                        <button class="btn btn-primary"><fmt:message key="edit"/></button>
                    </a>
                </c:if></td>
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
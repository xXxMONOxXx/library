<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
            <td><a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=go_to_update_genre_page&genre_id=${genre.getId()}">
                <button class="btn btn-primary"><fmt:message key="edit"/></button>
            </a></td>
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
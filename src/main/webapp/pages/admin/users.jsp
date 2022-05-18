<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="i18n/language"/>

<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<body>

<header>
    <jsp:include page="/pages/parts/header.jsp"/>
</header>

<br/>

<table class="table table-dark">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="users.login"/></th>
        <th scope="col"><fmt:message key="users.role"/></th>
        <th scope="col"><fmt:message key="users.is_blocked"/></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${users_list}" var="user">
        <tr>
            <th>${user.getName()}</th>
            <th>${user.getRole()}</th>
            <th>${user.isBlocked()}</th>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br/>
<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>
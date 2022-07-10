<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${not empty sessionScope.locale}"><fmt:setLocale value="${sessionScope.locale}"/></c:when>
    <c:when test="${empty sessionScope.locale}"><fmt:setLocale value="${sessionScope.locale = 'en_US'}"/></c:when>
</c:choose>

<fmt:setBundle basename="language"/>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/bootstrap/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/stylesheets/bootstrap/js/bootstrap.js"></script>
    <title><fmt:message key="error.500"/></title>
</head>
<body>
<div class="page-wrap d-flex flex-row align-items-center">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-12 text-center">
                <span class="display-1 d-block"><fmt:message key="error.500"/></span>
                <div class="mb-4 lead"><fmt:message key="error.server_internal"/></div>
                ${error_msg}
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/pages/home/index.jsp" class="btn btn-link"><fmt:message key="error.go_home"/></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>

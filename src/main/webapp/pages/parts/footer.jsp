<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>

<%--todo FIX FOOTER!!!--%>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/bootstrap/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/stylesheets/bootstrap/js/bootstrap.js"></script>
</head>
<body class="d-flex flex-column min-vh-100">
<footer class="bg-light text-center text-lg-start">
    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        <fmt:message key="footer.copyright"/>
        <a class="text-dark" href="${pageContext.request.contextPath}/pages/home/index.jsp"><fmt:message key="footer.company_name"/></a>
    </div>
</footer>
</body>
</html>

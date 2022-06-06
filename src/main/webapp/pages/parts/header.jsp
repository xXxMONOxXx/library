<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/bootstrap/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/stylesheets/bootstrap/js/bootstrap.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/pages/home/index.jsp"> <fmt:message key="header.library"/> </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item">
                <a class="nav-link" href="#"><fmt:message key="header.books"/></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="#"><fmt:message key="header.profile"/></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="#"><fmt:message key="entry.sign_out"/></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=get_all_users"><fmt:message key="header.users"/></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/pages/entry/sign_up.jsp"><fmt:message key="entry.sign_up"/></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/pages/entry/sign_in.jsp"><fmt:message key="entry.sign_in"/></a>
            </li>

        </ul>
    </div>
</nav>
</body>
</html>

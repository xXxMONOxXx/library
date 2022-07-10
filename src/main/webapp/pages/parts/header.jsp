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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/bootstrap/css/bootstrap.css"
          type="text/css">
    <script src="${pageContext.request.contextPath}/stylesheets/bootstrap/js/bootstrap.bundle.js"></script>
    <script src="${pageContext.request.contextPath}/stylesheets/bootstrap/js/bootstrap.js"></script>
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/pages/home/index.jsp"> <fmt:message
            key="header.library"/> </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <ul class="navbar-nav mr-auto">

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink"
                   role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <fmt:message key="header.language"/>
                </a>

                <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=set_localization&locale=en_US">
                        <fmt:message key="header.language.english"/></a>
                    </li>
                    <li>
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=set_localization&locale=ru_RU">
                            <fmt:message key="header.language.russian"/></a>
                    </li>

                    </li>
                </ul>
            </li>

            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=go_to_all_books_page"><fmt:message
                        key="header.books"/></a>
            </li>

            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=get_all_genres"><fmt:message
                        key="header.genres"/></a>
            </li>

            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=get_all_authors"><fmt:message
                        key="header.authors"/></a>
            </li>

            <c:if test="${sessionScope.user_id != null}">

                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/controller?command=get_user_info_by_id&user_id=${sessionScope.user_id}"><fmt:message
                            key="header.profile"/></a>
                </li>

                <c:if test="${sessionScope.user_role eq 'LIBRARIAN' or sessionScope.user_role eq 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/controller?command=go_to_add_book_page"><fmt:message
                                key="header.add_book"/></a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/pages/librarian/add_author.jsp"><fmt:message
                                key="header.add_author"/></a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/pages/librarian/add_genre.jsp"><fmt:message
                                key="header.add_genre"/></a>
                    </li>

                </c:if>

                <c:if test="${sessionScope.user_role eq 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/controller?command=get_all_users"><fmt:message
                                key="header.users"/></a>
                    </li>
                </c:if>

                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/controller?command=sign_out"><fmt:message
                            key="entry.sign_out"/></a>
                </li>

            </c:if>

            <c:if test="${sessionScope.user_id == null}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/pages/entry/sign_up.jsp"><fmt:message
                            key="entry.sign_up"/></a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/pages/entry/sign_in.jsp"><fmt:message
                            key="entry.sign_in"/></a>
                </li>
            </c:if>

        </ul>
    </div>
</nav>
</body>
</html>

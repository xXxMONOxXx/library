<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/pages/parts/header.jsp" %>
<html>
<head>
    <title>Main</title>
</head>
<body>

<c:if test="${requestScope.delete_book_success}">
    <p class="text-success"><fmt:message key="librarian.book.delete_book_success"/></p>
</c:if>
<c:if test="${requestScope.failed_to_get_book}">
    <p class="text-danger"><fmt:message key="librarian.failed_to_get_book"/></p>
</c:if>

<table class="table">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="books.name"/></th>
        <th scope="col"><fmt:message key="books.cover_photo"/></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${books_list}" var="book">
        <tr>
            <th><a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=go_to_book_page&book_id=${book.getId()}">${book.getName()}</a>
            </th>

            <th><img src="data:image/png;base64,${book.getPhotoCoverAsBase64()}" width="240" height="300"
                     alt=
                         <fmt:message key="books.cover_photo"/>/></th>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br/>

<div class="d-flex justify-content-center">
    <c:if test="${requestScope.current_page > 1}">
        <a href="${pageContext.request.contextPath}/controller?command=go_to_all_books_page&page=${requestScope.current_page - 1}">
            <fmt:message key="books.previous"/></a>
    </c:if>

    ${requestScope.current_page}

    <c:if test="${requestScope.current_page < requestScope.number_of_pages}">
        <a href="${pageContext.request.contextPath}/controller?command=go_to_all_books_page&page=${requestScope.current_page + 1}">
            <fmt:message key="books.next"/></a>
    </c:if>
</div>

<br/>
<br/>


<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>

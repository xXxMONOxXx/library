<%@include file="/pages/parts/header.jsp" %>

<html>
<head>
    <title>Main</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="get">

    <input type="hidden" name="command" value="go_to_all_books_page"/>

    <div class="input-group">
        <div class="form-outline">
            <input type="search" id="search_form" class="form-control" name="search_input"
                   value="${search_input}"/>
        </div>
        <button type="submit"
                class="btn btn-success btn-block mb-4"><fmt:message key="search"/></button>
    </div>
</form>


<c:if test="${requestScope.delete_book_success}">
    <p class="text-success"><fmt:message key="librarian.book.delete_book_success"/></p>
</c:if>
<c:if test="${requestScope.failed_to_get_book}">
    <p class="text-danger"><fmt:message key="librarian.failed_to_get_book"/></p>
</c:if>

<c:if test="${books_list.size() == 0}">
    <p class="text"><fmt:message key="books.search_found_nothing"/></p>
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
            <td><a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=go_to_book_page&book_id=${book.getId()}">${book.getName()}</a>
            </td>

            <td><img src="data:image/png;base64,${book.getPhotoCoverAsBase64()}" width="240" height="300"
                     alt=
                    <fmt:message key="books.cover_photo"/>/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br/>

<div class="d-flex justify-content-center">

    <c:if test="${requestScope.current_page > 1}">

        <form action="${pageContext.request.contextPath}/controller" method="get">

            <input type="hidden" name="command" value="go_to_all_books_page"/>

            <input type="hidden" name="page" value="${requestScope.current_page - 1}"/>

            <c:if test="${requestScope.search_input != null}">
                <input type="hidden" name="search_input" value="${search_input}"/>
            </c:if>

            <button type="submit"
                    class="btn btn-primary btn-block mb-4"><fmt:message key="books.previous"/></button>

        </form>
    </c:if>

    ${requestScope.current_page}

    <c:if test="${requestScope.current_page < requestScope.number_of_pages}">

        <form action="${pageContext.request.contextPath}/controller" method="get">

            <input type="hidden" name="command" value="go_to_all_books_page"/>

            <input type="hidden" name="page" value="${requestScope.current_page + 1}"/>

            <c:if test="${requestScope.search_input != null}">
                <input type="hidden" name="search_input" value="${search_input}"/>
            </c:if>

            <button type="submit"
                    class="btn btn-primary btn-block mb-4"><fmt:message key="books.next"/></button>
        </form>
    </c:if>
</div>

<br/>
<br/>


<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>

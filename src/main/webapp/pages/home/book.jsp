<%@include file="/pages/parts/header.jsp" %>

<html>
<head>
    <title>${book.getName()}</title>
</head>
<body>

<c:if test="${requestScope.got_book_successfully}">
    <p class="text-success"><fmt:message key="book.got_successfully"/></p>
</c:if>

<c:if test="${requestScope.got_book_failed}">
    <p class="text-danger"><fmt:message key="book.got_failed"/></p>
</c:if>

<h1><fmt:message key="book.name"/></h1>
<h2>${book.getName()}</h2>

<th><img src="data:image/png;base64,${book.getPhotoCoverAsBase64()}" width="240" height="300"
         alt=
        <fmt:message key="books.cover_photo"/>/></th>

<h1><fmt:message key="book.authors"/></h1>
<h2>
    <c:forEach items="${book.getAuthors()}" var="author">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?command=go_to_author_page&author_id=${author.getId()}">
                ${author.getFirstname()} ${author.getLastname()}</a>
    </c:forEach>
</h2>

<h1><fmt:message key="book.genres"/></h1>
<h2>
    <c:forEach items="${book.getGenres()}" var="genre">
        ${genre.getName()}
        <%--    todo add link to genre--%>
    </c:forEach>
</h2>

<h1><fmt:message key="book.release_date"/> ${book.getReleaseDate()}</h1>

<h1><fmt:message key="book.info"/></h1>
<h2>${book.getInfo()}</h2>

<c:if test="${book.getAgeLimitation() != null}">
    <h1><fmt:message key="book.age_limitations"/></h1>
    <h2>${book.getAgeLimitation()}</h2>
</c:if>

<h1><fmt:message key="book.quantity"/></h1>
<h2>${book.getQuantity()}</h2>

<c:if test="${sessionScope.user_id != null && book.getQuantity() > 0}">
    <form action="${pageContext.request.contextPath}/controller" method="post">

        <input type="hidden" name="command" value="add_book_to_user"/>

        <input type="hidden" name="user_id" value="${sessionScope.user_id}"/>

        <input type="hidden" name="book_id" value="${book.getId()}"/>

        <br/>

        <div class="col text-center">
            <button type="submit" <c:if test="${book.getQuantity() == 0}"><c:out value="disabled='disabled'"/></c:if>
                    class="btn btn-success btn-block mb-4"><fmt:message key="book.get"/></button>
        </div>
    </form>
</c:if>

<c:if test="${sessionScope.user_role eq 'ADMIN' or sessionScope.user_role eq 'LIBRARIAN'}">

    <br/>

    <div class="col text-center">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?command=go_to_update_book_page&book_id=${book.getId()}">
            <button class="btn btn-primary"><fmt:message key="edit"/></button>
        </a>
    </div>

</c:if>

<br/>
<br/>

<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>

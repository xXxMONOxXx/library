<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>${author.getFirstname()} ${author.getLastname()}</title>
</head>
<body>

<h1>${author.getFirstname()} ${author.getLastname()}</h1>

<c:if test="${sessionScope.user_role eq 'ADMIN' || sessionScope.user_role eq 'LIBRARIAN'}">
    <a class="nav-link"
       href="${pageContext.request.contextPath}/controller?command=go_to_update_author_page&author_id=${author.getId()}">
        <button class="btn btn-primary"><fmt:message key="edit"/></button>
    </a>
</c:if>

<c:if test="${author.getBiography() != null && author.getBiography().length() != 0}">
    <h1><fmt:message key="author.bio"/></h1>
    <h2>${author.getBiography()}</h2>
</c:if>


<c:if test="${books_list.size() != 0}">
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
</c:if>


<br/>
<br/>

<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>

</body>
</html>

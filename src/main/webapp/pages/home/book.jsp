<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/pages/parts/header.jsp" %>
<html>
<head>
    <title>Main</title>
</head>
<body>

<h1><fmt:message key="book.name"/></h1>
<h2>${book.getName()}</h2>
<th><img src="data:image/png;base64,${book.getPhotoCoverAsBase64()}" width="240" height="300"
         alt=<fmt:message key="books.cover_photo"/> /></th>

<h1><fmt:message key="book.authors"/></h1>
<h2>
    <c:forEach items="${book.getAuthors()}" var="author">
        ${author.getFirstname()} ${author.getLastname()}
        <%--    todo add link to author--%>
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


<h1><fmt:message key="book.age_limitations"/></h1>
<h2>${book.getAgeLimitation()}</h2> <%--todo check null--%>

<h1><fmt:message key="book.quantity"/></h1>
<h2>${book.getQuantity()}</h2>


<br/>
<br/>

<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>

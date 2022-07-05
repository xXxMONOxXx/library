<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/pages/parts/header.jsp"%>
<html>
<head>
    <title>Main</title>
</head>
<body>

<table class="table table-dark">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="books.name"/></th>
        <th scope="col"><fmt:message key="books.cover_photo"/></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${books_list}" var="book">
        <tr>
            <th>${book.getName()}</th>
                <%--            todo add link to book page    --%>

            <th><img src="data:image/png;base64,${book.getPhotoCoverAsBase64()}" width="240" height="300"/></th>
        </tr>
    </c:forEach>
    </tbody>
</table>

<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>

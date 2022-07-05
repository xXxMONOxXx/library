<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/pages/parts/header.jsp" %>

<html>
<head>
    <title>${user.getLogin()}</title>
</head>
<body>
<h1><fmt:message key="profile.login"/> ${user.getLogin()}</h1>
<h1><fmt:message key="profile.firstname"/> ${user.getFirstname()}</h1>
<h1><fmt:message key="profile.lastname"/> ${user.getLastname()}</h1>
<h1><fmt:message key="profile.email"/> ${user.getEmail()}</h1>
<h1><fmt:message key="profile.birthdate"/> ${user.getBirthdate()}</h1>
<h1><fmt:message key="profile.balance"/> ${user.getDaysBalance()}</h1>

<form action="${pageContext.request.contextPath}/controller" method="post">

    <input type="hidden" name="command" value="add_balance_to_user"/>

    <input type="hidden" name="user_id" value="${sessionScope.user_id}"/>

    <c:if test="${requestScope.balance_changed_successfully != null}">
        <%--        todo not sure about this ↑ --%>
        <c:choose>
            <c:when test="${requestScope.balance_changed_successfully}">
                <p class="text-success"><fmt:message key="user.balance_changed_successfully"/></p>
            </c:when>
            <c:otherwise>
                <p class="text-danger"><fmt:message key="user.balance_change_failed"/></p>
            </c:otherwise>
        </c:choose>
    </c:if>
    <div class="col">
        <div class="form-outline">
            <label for="change_balance"><fmt:message key="book.quantity"/></label>
            <input class="form-control" type="number" id="change_balance" name="change_balance"
                   min="1" max="100">
        </div>
    </div>

    <div class="col text-center">
        <button type="submit" class="btn btn-primary btn-block mb-4"><fmt:message key="user.change_balance"/></button>
    </div>
    <c:if test="${books_list.size() !=0}">
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
                    <th><img src="data:image/png;base64,${book.getPhotoCoverAsBase64()}" width="240" height="300" alt=<fmt:message key="books.cover_photo"/>/></th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</form>

</body>
</html>

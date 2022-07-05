<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<html>
<head>
    <title><fmt:message key="librarian.add_author"/></title>
</head>
<body>

<form action="${pageContext.request.contextPath}/controller" method="post">

    <input type="hidden" name="command" value="add_author"/>

    <c:if test="${requestScope.added_author_successfully}">
        <p class="text-success"> <fmt:message key="librarian.author.added_successfully"/></p>
    </c:if>
    <c:if test="${requestScope.author_exists}">
        <p class="text-danger"> <fmt:message key="librarian.author.already_exists"/></p>
    </c:if>

    <div class="row mb-4">
        <div class="col">
            <c:if test="${requestScope.author_firstname_invalid}">
                <p class="text-danger"> <fmt:message key="librarian.add_author.invalid.firstname"/></p>
            </c:if>
            <div class="form-outline">
                <label for="first_name"><fmt:message key="librarian.add_author.firstname"/></label>
                <input type="text" name ="first_name" class="form-control" id="first_name">
            </div>
        </div>
        <div class="col">
            <c:if test="${requestScope.author_lastname_invalid}">
                <p class="text-danger"> <fmt:message key="librarian.add_author.invalid.lastname"/></p>
            </c:if>
            <div class="form-outline">
                <label for="last_name"><fmt:message key="librarian.add_author.lastname"/></label>
                <input type="text" name ="last_name" class="form-control" id="last_name">
            </div>
        </div>
    </div>

    <div class="form-outline mb-4">
        <c:if test="${requestScope.author_biography_invalid}">
            <p class="text-danger"> <fmt:message key="librarian.add_author.invalid.biography"/></p>
        </c:if>
        <label for="biography"><fmt:message key="librarian.add_author.biography"/></label>
        <textarea class="form-control" name ="biography" id="biography" rows="6"></textarea>
    </div>

    <div class="col text-center">
        <button type="submit" class="btn btn-primary btn-block mb-4"><fmt:message key="librarian.add_author"/></button>
    </div>
</form>


<br/>
<br/>


<jsp:include page="/pages/parts/footer.jsp"/>

</body>
</html>

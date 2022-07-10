<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="librarian.add_genre"/></title>
</head>
<body>

<form action="${pageContext.request.contextPath}/controller" method="post">

    <input type="hidden" name="command" value="add_genre"/>

    <c:if test="${requestScope.added_genre_successfully}">
        <p class="text-success"><fmt:message key="librarian.genre.added_successfully"/></p>
    </c:if>

    <c:if test="${requestScope.add_genre_invalid_or_exists}">
        <p class="text-danger"><fmt:message key="librarian.genre.invalid.name"/></p>
    </c:if>

    <div class="form-outline">
        <label for="genre_name"><fmt:message key="librarian.genre_name"/></label>
        <input type="text" name="genre_name" class="form-control" id="genre_name">
    </div>

    <br/>

    <div class="col text-center">
        <button type="submit" class="btn btn-primary btn-block mb-4"><fmt:message key="librarian.add_genre"/></button>
    </div>
</form>


<br/>
<br/>


<jsp:include page="/pages/parts/footer.jsp"/>

</body>
</html>

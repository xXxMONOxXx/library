<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="librarian.update_author"/></title>
</head>
<body>

<form action="${pageContext.request.contextPath}/controller" method="post">

    <input type="hidden" name="command" value="update_author"/>

    <input type="hidden" name="author_id" value="${author.getId()}"/>

    <c:if test="${requestScope.failed_to_delete_author}">
        <p class="text-danger"><fmt:message key="librarian.author.failed_to_delete"/></p>
    </c:if>
    <c:if test="${requestScope.updated_author_successfully}">
        <p class="text-success"><fmt:message key="librarian.author.updated_successfully"/></p>
    </c:if>
    <c:if test="${requestScope.author_update_failed}">
        <p class="text-danger"><fmt:message key="librarian.author.update_failed"/></p>
    </c:if>

    <div class="row mb-4">
        <div class="col">
            <c:if test="${requestScope.author_firstname_invalid}">
                <p class="text-danger"><fmt:message key="librarian.add_author.invalid.firstname"/></p>
            </c:if>
            <div class="form-outline">
                <label for="first_name"><fmt:message key="librarian.add_author.firstname"/></label>
                <input type="text" name="first_name" class="form-control" id="first_name"
                       value="${author.getFirstname()}">
            </div>
        </div>
        <div class="col">
            <c:if test="${requestScope.author_lastname_invalid}">
                <p class="text-danger"><fmt:message key="librarian.add_author.invalid.lastname"/></p>
            </c:if>
            <div class="form-outline">
                <label for="last_name"><fmt:message key="librarian.add_author.lastname"/></label>
                <input type="text" name="last_name" class="form-control" id="last_name" value="${author.getLastname()}">
            </div>
        </div>
    </div>

    <div class="form-outline mb-4">
        <c:if test="${requestScope.author_biography_invalid}">
            <p class="text-danger"><fmt:message key="librarian.add_author.invalid.biography"/></p>
        </c:if>
        <label for="biography"><fmt:message key="librarian.add_author.biography"/></label>
        <textarea class="form-control" name="biography" id="biography" rows="6"> ${author.getBiography()} </textarea>
    </div>

    <div class="col text-center">
        <button type="submit" class="btn btn-primary btn-block mb-4"><fmt:message key="edit"/></button>
    </div>
</form>

<form action="${pageContext.request.contextPath}/controller" method="post">

    <input type="hidden" name="command" value="delete_author"/>

    <input type="hidden" name="author_id" value="${author.getId()}"/>

    <div class="col text-center">
        <button type="submit" class="btn btn-danger btn-block mb-4"><fmt:message key="delete"/></button>
    </div>

</form>

<br/>
<br/>


<jsp:include page="/pages/parts/footer.jsp"/>

</body>
</html>

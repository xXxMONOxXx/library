<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<html>
<head>
    <title><fmt:message key="book.add_book"/></title>
</head>
<body>

<c:if test="${requestScope.added_book_successfully}">
    <p class="text-success"> <fmt:message key="librarian.book.added_successfully"/></p>
</c:if>

<form action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">

    <input type="hidden" name="command" value="add_book"/>

    <div class="row mb-4">
        <c:if test="${requestScope.invalid_book_name}">
            <p class="text-danger"> <fmt:message key="librarian.add_book.invalid_book_name"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="book_name"><fmt:message key="book.name"/></label>
                <input type="text" name="book_name" class="form-control" id="book_name">
            </div>
        </div>
        <c:if test="${requestScope.invalid_release_date}">
            <p class="text-danger"> <fmt:message key="librarian.add_book.invalid_release_date"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="release_date"><fmt:message key="book.release_date"/></label>
                <input type="date" id="release_date" class="form-control form-control-lg" name="release_date"/>
            </div>
        </div>
    </div>


    <div class="row mb-4">
        <c:if test="${requestScope.invalid_book_genres}">
            <p class="text-danger"> <fmt:message key="librarian.add_book.invalid_book_genres"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="book_genres"><fmt:message key="book.genres"/></label>
                <select multiple class="form-control" id="book_genres" name="book_genres">
                    <c:forEach items="${genres_list}" var="genre">
                        <option value="${genre.getId()}">${genre.getName()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <c:if test="${requestScope.invalid_book_authors}">
            <p class="text-danger"> <fmt:message key="librarian.add_book.invalid_book_authors"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="book_authors"><fmt:message key="book.authors"/></label>
                <select multiple class="form-control" id="book_authors" name="book_authors">
                    <c:forEach items="${authors_list}" var="author">
                        <option value="${author.getId()}">${author.getFirstname()} ${author.getLastname()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>


    <div class="row mb-4">
        <c:if test="${requestScope.invalid_age_limitationa}">
            <p class="text-danger"> <fmt:message key="librarian.add_book.invalid_book_age_limitations"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="age_limitation"><fmt:message key="book.age_limitations"/></label>
                <input class="form-control" type="number" id="age_limitation" name="age_limitations"
                       min="3" max="24">
            </div>
        </div>
        <c:if test="${requestScope.invalid_book_quantity}">
            <p class="text-danger"> <fmt:message key="librarian.add_book.invalid_book_book_quantity"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="quantity"><fmt:message key="book.quantity"/></label>
                <input class="form-control" type="number" id="quantity" name="quantity"
                       min="1" max="100">
            </div>
        </div>
    </div>

    <c:if test="${requestScope.invalid_book_info}">
        <p class="text-danger"> <fmt:message key="librarian.add_book.invalid_book_info"/></p>
    </c:if>
    <div class="form-outline mb-4">
        <label for="book_info"><fmt:message key="book.info"/></label>
        <textarea class="form-control" id="book_info" rows="6" name="book_info"></textarea>
    </div>

    <c:if test="${requestScope.invalid_book_cover_photo}">
        <p class="text-danger"> <fmt:message key="librarian.add_book.invalid_book_cover_photo"/></p>
    </c:if>
    <div class="form-outline mb-4">
        <label for="cover_photo"><fmt:message key="book.cover_photo"/></label>
        <input type="file" class="form-control-file" id="cover_photo" name="cover_photo">
    </div>

    <div class="col text-center">
        <button type="submit" class="btn btn-primary btn-block mb-4"><fmt:message key="book.add_book"/></button>
    </div>
</form>


<br/>
<br/>


<jsp:include page="/pages/parts/footer.jsp"/>

</body>
</html>

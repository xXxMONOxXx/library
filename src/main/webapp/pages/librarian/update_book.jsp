<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="librarian.update_book"/></title>
</head>
<body>

<c:if test="${requestScope.failed_to_delete_book}">
    <p class="text-danger"><fmt:message key="librarian.book.failed_to_delete_book"/></p>
</c:if>
<c:if test="${requestScope.update_book_success}">
    <p class="text-success"><fmt:message key="librarian.book.updated_book_successfully"/></p>
</c:if>

<form action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">

    <input type="hidden" name="command" value="update_book"/>

    <input type="hidden" name="book_id" value="${book.getId()}"/>

    <div class="row mb-4">
        <c:if test="${requestScope.invalid_book_name}">
            <p class="text-danger"><fmt:message key="librarian.add_book.invalid_book_name"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="book_name"><fmt:message key="book.name"/></label>
                <input type="text" name="book_name" class="form-control" id="book_name" value="${book.getName()}">
            </div>
        </div>
        <c:if test="${requestScope.invalid_release_date}">
            <p class="text-danger"><fmt:message key="librarian.add_book.invalid_release_date"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="release_date"><fmt:message key="book.release_date"/></label>
                <input type="date" id="release_date" class="form-control form-control-lg" name="release_date"
                       value="${book.getReleaseDate()}"/>
            </div>
        </div>
    </div>


    <div class="row mb-4">
        <div class="col">
            <div class="form-outline">
                <label for="book_genres"><fmt:message key="book.genres"/></label>
                <select multiple class="form-control" id="book_genres" name="book_genres">
                    <c:forEach items="${genres_list}" var="genre">
                        <option ${book.hasGenresId(genre.getId()) ? 'selected="selected"' : ''}
                                value="${genre.getId()}">${genre.getName()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col">
            <div class="form-outline">
                <label for="book_authors"><fmt:message key="book.authors"/></label>
                <select multiple class="form-control" id="book_authors" name="book_authors">
                    <c:forEach items="${authors_list}" var="author">
                        <option ${book.hasAuthorsId(author.getId()) ? 'selected="selected"' : ''}
                                value="${author.getId()}">${author.getFirstname()} ${author.getLastname()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>


    <div class="row mb-4">
        <c:if test="${requestScope.invalid_age_limitationa}">
            <p class="text-danger"><fmt:message key="librarian.add_book.invalid_book_age_limitations"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="age_limitation"><fmt:message key="book.age_limitations"/></label>
                <input class="form-control" type="number" id="age_limitation" name="age_limitations"
                       min="3" max="24" value="${book.getAgeLimitation()}">
            </div>
        </div>
        <c:if test="${requestScope.invalid_book_quantity}">
            <p class="text-danger"><fmt:message key="librarian.add_book.invalid_book_book_quantity"/></p>
        </c:if>
        <div class="col">
            <div class="form-outline">
                <label for="quantity"><fmt:message key="book.quantity"/>
                    (<fmt:message key="book.quantity_with_used"/>${actual_quantity})</label>
                <input class="form-control" type="number" id="quantity" name="quantity"
                       min="1" max="100" value="${book.getQuantity()}">
            </div>
        </div>
    </div>

    <c:if test="${requestScope.invalid_book_info}">
        <p class="text-danger"><fmt:message key="librarian.add_book.invalid_book_info"/></p>
    </c:if>
    <div class="form-outline mb-4">
        <label for="book_info"><fmt:message key="book.info"/></label>
        <textarea class="form-control" id="book_info" rows="6" name="book_info">${book.getInfo()}</textarea>
    </div>

    <c:if test="${requestScope.invalid_book_cover_photo}">
        <p class="text-danger"><fmt:message key="librarian.add_book.invalid_book_cover_photo"/></p>
    </c:if>
    <div class="form-outline mb-4">
        <label for="cover_photo"><fmt:message key="book.cover_photo"/></label>
        <img src="data:image/png;base64,${book.getPhotoCoverAsBase64()}" width="240" height="300"
             alt=
             <fmt:message key="books.cover_photo"/>/>
        <input type="file" class="form-control-file" id="cover_photo" name="cover_photo">

        <label for="delete_cover_photo"><fmt:message key="book.delete_cover_photo"/></label>
        <input type="checkbox" id="delete_cover_photo" name="delete_cover_photo" value="delete_cover_photo">

    </div>

    <div class="col text-center">
        <button type="submit" class="btn btn-primary btn-block mb-4"><fmt:message key="edit"/></button>
    </div>
</form>

<form action="${pageContext.request.contextPath}/controller" method="post">

    <input type="hidden" name="command" value="delete_book"/>

    <input type="hidden" name="book_id" value="${book.getId()}"/>

    <div class="col text-center">
        <button type="submit" class="btn btn-danger btn-block mb-4"><fmt:message key="delete"/></button>
    </div>

</form>

<br/>
<br/>


<jsp:include page="/pages/parts/footer.jsp"/>

</body>
</html>

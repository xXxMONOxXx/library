<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<html>
<head>
    <title><fmt:message key="book.add_book"/></title>
</head>
<body>

<form action="${pageContext.request.contextPath}/controller" method="post">

    <input type="hidden" name="command" value="add_book"/>

    <div class="row mb-4">
        <div class="col">
            <div class="form-outline">
                <label for="book_name"><fmt:message key="book.name"/></label>
                <input type="text" name="book_name" class="form-control" id="book_name">
            </div>
        </div>
        <div class="col">
            <div class="form-outline">
                <label for="release_date"><fmt:message key="book.release_date"/></label>
                <input type="date" id="release_date" class="form-control form-control-lg"/>
            </div>
        </div>
    </div>


    <div class="row mb-4">
        <div class="col">
            <div class="form-outline">
                <label for="book_genres"><fmt:message key="book.genres"/></label>
                <select multiple class="form-control" id="book_genres">
                    <c:forEach items="${genres_list}" var="genre">
                        <option value="${genre.getId()}">${genre.getName()}</option>
                    </c:forEach>

                    <%--                    <select name="department">
                        <c:forEach var="item" items="${dept}">
                            <option value="${item.key}" ${item.key == selectedDept ? 'selected="selected"' : ''}>${item.value}</option>
                        </c:forEach>
                    </select>--%>
                </select>
            </div>
        </div>
        <div class="col">
            <div class="form-outline">
                <label for="book_authors"><fmt:message key="book.authors"/></label>
                <select multiple class="form-control" id="book_authors">
                    <c:forEach items="${authors_list}" var="author">
                        <option value="${author.getId()}">${author.getFirstname()} ${author.getLastname()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>


    <div class="row mb-4">
        <div class="col">
            <div class="form-outline">
                <label for="age_limitation"><fmt:message key="book.age_limitations"/></label>
                <input class="form-control" type="number" id="age_limitation" name="age_limitations"
                       min="3" max="24">
            </div>
        </div>
        <div class="col">
            <div class="form-outline">
                <label for="quantity"><fmt:message key="book.quantity"/></label>
                <input class="form-control" type="number" id="quantity" name="age_limitations"
                       min="1" max="100">
            </div>
        </div>
    </div>

    <div class="form-outline mb-4">
        <textarea class="form-control" id="book_info" rows="6"></textarea>
        <label for="book_info"><fmt:message key="book.about"/></label>
    </div>

    <div class="form-outline mb-4">
        <label for="cover_photo"><fmt:message key="book.cover_photo"/></label>
        <input type="file" class="form-control-file" id="cover_photo">
    </div>

    <div class="col text-center">
        <button type="submit" class="btn btn-primary btn-block mb-4"><fmt:message key="book.add_book"/></button>
    </div>
</form>


<br/>

<jsp:include page="/pages/parts/footer.jsp"/>

</body>
</html>

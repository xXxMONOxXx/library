<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
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

<c:if test="${sessionScope.user_role eq 'ADMIN'}">
    <c:if test="${requestScope.change_role_success}">
        <p class="text-success"><fmt:message key="change_role_success"/></p>
    </c:if>

    <c:if test="${requestScope.failed_to_change_role}">
        <p class="text-danger"><fmt:message key="failed_to_change_role"/></p>
    </c:if>

    <h2><fmt:message key="profile.change_role"/></h2>

    <form action="${pageContext.request.contextPath}/controller" method="post">

        <input type="hidden" name="command" value="change_users_role"/>

        <input type="hidden" name="user_id" value="${requestScope.user.getId()}"/>

        <input type="hidden" name="user_role" value="USER"/>

        <button type="submit" <c:if test="${requestScope.user.getRole() eq 'USER' }"><c:out
                value="disabled='disabled'"/></c:if>
                class="btn btn-primary"><fmt:message key="role.user"/></button>

    </form>

    <form action="${pageContext.request.contextPath}/controller" method="post">

        <input type="hidden" name="command" value="change_users_role"/>

        <input type="hidden" name="user_id" value="${requestScope.user.getId()}"/>

        <input type="hidden" name="user_role" value="LIBRARIAN"/>

        <button type="submit" <c:if test="${requestScope.user.getRole() eq 'LIBRARIAN' }"><c:out
                value="disabled='disabled'"/> </c:if>
                class="btn btn-primary"><fmt:message key="role.librarian"/></button>

    </form>

    <form action="${pageContext.request.contextPath}/controller" method="post">

        <input type="hidden" name="command" value="change_users_role"/>

        <input type="hidden" name="user_id" value="${requestScope.user.getId()}"/>

        <input type="hidden" name="user_role" value="ADMIN"/>

        <button type="submit" <c:if test="${requestScope.user.getRole() eq 'ADMIN'}"><c:out
                value="disabled='disabled'"/></c:if>
                class="btn btn-primary"><fmt:message key="role.admin"/></button>

    </form>
</c:if>

<c:if test="${sessionScope.user_role eq 'ADMIN' or sessionScope.user_id == user.getId()}">

    <form action="${pageContext.request.contextPath}/controller" method="post">

        <input type="hidden" name="command" value="add_balance_to_user"/>

        <input type="hidden" name="user_id" value="${user.getId()}"/>


        <c:if test="${requestScope.balance_changed_successfully}">
            <p class="text-success"><fmt:message key="user.balance_changed_successfully"/></p>
        </c:if>

        <c:if test="${requestScope.balance_changed_failed}">
            <p class="text-danger"><fmt:message key="user.balance_change_failed"/></p>
        </c:if>

        <div class="col">
            <div class="form-outline">
                <label for="change_balance"><fmt:message key="profile.balance"/></label>
                <input class="form-control" type="number" id="change_balance" name="change_balance"
                       min="1" max="100">
            </div>
        </div>

        <div class="col text-center">
            <button type="submit" class="btn btn-primary btn-block mb-4"><fmt:message
                    key="user.change_balance"/></button>
        </div>
    </form>

    <c:if test="${requestScope.updated_users_password_successfully}">
        <p class="text-success"><fmt:message key="profile.change_password_success"/></p>
    </c:if>

    <c:if test="${requestScope.update_users_password_failed}">
        <p class="text-danger"><fmt:message key="profile.change_password_failed"/></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/controller" method="post">

        <input type="hidden" name="command" value="change_users_password"/>

        <input type="hidden" name="user_id" value="${user.getId()}"/>

        <div class="row mb-4">
            <div class="col">
                <div class="form-outline">
                    <label for="old_password"><fmt:message key="profile.old_password"/></label>
                    <input type="password" name="old_password" class="form-control" id="old_password">
                </div>
            </div>
            <div class="col">
                <div class="form-outline">
                    <label for="new_password"><fmt:message key="profile.new_password"/></label>
                    <input type="password" name="new_password" class="form-control" id="new_password">
                </div>
            </div>

            <div class="d-flex justify-content-center">
                <input class="btn btn-primary"
                       type="submit" name="submit" value="<fmt:message key="profile.change_password"/>"/>
            </div>
        </div>
    </form>

    <c:if test="${requestScope.returned_book_successfully}">
        <p class="text-success"><fmt:message key="user_returned_book_success"/></p>
    </c:if>

    <c:if test="${requestScope.returned_book_failed}">
        <p class="text-danger"><fmt:message key="user_returned_book_failed"/></p>
    </c:if>

    <c:if test="${books_list.size() !=0}">

        <form action="${pageContext.request.contextPath}/controller" method="post">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="books.name"/></th>
                    <th scope="col"><fmt:message key="books.cover_photo"/></th>
                    <th scope="col"><fmt:message key="books.return"/></th>
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

                        <td>


                            <input type="hidden" name="command" value="return_book"/>

                            <input type="hidden" name="book_id" value="${book.getId()}"/>

                            <input type="hidden" name="user_id" value="${user.getId()}"/>

                            <button type="submit" class="btn btn-primary"><fmt:message key="books.return"/></button>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>
    </c:if>
</c:if>

<br/>
<br/>


<jsp:include page="/pages/parts/footer.jsp"/>

</body>
</html>

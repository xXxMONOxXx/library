<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="users.users"/></title>
</head>
<body>

<br/>
<br/>

<c:if test="${blocked_unblocked_success}">
    <p class="text-success"> <fmt:message key="users.block_unblock_success"/></p>
</c:if>
<c:if test="${blocked_unblocked_failed}">
    <p class="text-danger"> <fmt:message key="users.block_unblock_failed"/></p>
</c:if>

    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="users.user"/></th>
            <th scope="col"><fmt:message key="users.role"/></th>
            <th scope="col"><fmt:message key="users.block_unblock"/></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${users_list}" var="user">
            <tr>
                <td><a class="nav-link"
                       href="${pageContext.request.contextPath}/controller?command=get_user_info_by_id&user_id=${user.getId()}">${user.getLogin()}</a>
                </td>
                <td>${user.getRole()}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="user_id" value="${user.getId()}"/>

                    <c:choose>
                        <c:when test="${user.isBlocked()}">
                            <input type="hidden" name="command" value="unblock_user"/>
                            <button type="submit" class="btn btn-success"><fmt:message key="users.unblock"/></button>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="command" value="block_user"/>
                            <button type="submit" class="btn btn-danger"><fmt:message key="users.block"/></button>
                        </c:otherwise>
                    </c:choose>

                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

<br/>
<br/>

<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>
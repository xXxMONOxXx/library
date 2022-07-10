<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="entry.sign_up"/></title>
</head>
<body>

<section class="vh-100 bg-image">
    <div class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">
                            <h2 class="text-uppercase text-center mb-5"><fmt:message key="entry.create_account"/></h2>

                            <form action="${pageContext.request.contextPath}/controller" method="post">

                                <input type="hidden" name="command" value="sign_up"/>

                                <c:if test="${requestScope.sign_up_login_is_invalid}">
                                    <p class="text-danger"><fmt:message key="entry.invalid.login"/></p>
                                </c:if>
                                <div class="form-outline mb-4">
                                    <label class="form-label"><fmt:message key="entry.login"/></label>
                                    <input type="text" name="login" class="form-control form-control-lg"/>
                                </div>

                                <c:if test="${requestScope.sign_up_firstname_is_invalid}">
                                    <p class="text-danger"><fmt:message key="entry.invalid.firstname"/></p>
                                </c:if>
                                <div class="form-outline mb-4">
                                    <label class="form-label"><fmt:message key="entry.firstname"/></label>
                                    <input type="text" name="first_name" class="form-control form-control-lg"/>
                                </div>

                                <c:if test="${requestScope.sign_up_lastname_is_invalid}">
                                    <p class="text-danger"><fmt:message key="entry.invalid.lastname"/></p>
                                </c:if>
                                <div class="form-outline mb-4">
                                    <label class="form-label"><fmt:message key="entry.lastname"/></label>
                                    <input type="text" name="last_name" class="form-control form-control-lg"/>
                                </div>

                                <c:if test="${requestScope.sign_up_email_is_invalid}">
                                    <p class="text-danger"><fmt:message key="entry.invalid.email"/></p>
                                </c:if>
                                <div class="form-outline mb-4">
                                    <label class="form-label"><fmt:message key="entry.email"/></label>
                                    <input type="email" name="email" class="form-control form-control-lg"/>
                                </div>

                                <c:if test="${requestScope.sign_up_birthdate_is_invalid}">
                                    <p class="text-danger"><fmt:message key="entry.invalid.birthdate"/></p>
                                </c:if>
                                <div class="form-outline mb-4">
                                    <label class="form-label"><fmt:message key="entry.birthdate"/></label>
                                    <input type="date" name="birthdate" class="form-control form-control-lg"/>
                                </div>

                                <c:if test="${requestScope.sign_up_password_is_invalid}">
                                    <p class="text-danger"><fmt:message key="entry.invalid.password"/></p>
                                </c:if>
                                <div class="form-outline mb-4">
                                    <label class="form-label"><fmt:message key="entry.password"/></label>
                                    <input type="password" name="password" class="form-control form-control-lg"/>
                                </div>

                                <c:if test="${requestScope.sign_up_password_repeat_is_invalid}">
                                    <p class="text-danger"><fmt:message key="entry.invalid.password_repeat"/></p>
                                </c:if>
                                <div class="form-outline mb-4">
                                    <label class="form-label"><fmt:message key="entry.password_repeat"/></label>
                                    <input type="password" name="password_confirm"
                                           class="form-control form-control-lg"/>
                                </div>

                                <div class="d-flex justify-content-center">
                                    <button type="submit" name="submit"
                                            class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">
                                        <fmt:message key="entry.sign_up"/>
                                    </button>
                                </div>

                                <p class="text-center text-muted mt-5 mb-0"><fmt:message
                                        key="entry.already_have_account"/><a
                                        href="${pageContext.request.contextPath}/pages/entry/sign_in.jsp"
                                        class="fw-bold text-body"><u><fmt:message key="entry.sign_in_here"/></u></a></p>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<br/>
<footer>
    <c:import url="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>

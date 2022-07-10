<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="entry.sign_in"/></title>
</head>
<body>

<section class="vh-100 bg-image">
    <div class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">
                            <h2 class="text-uppercase text-center mb-5"><fmt:message key="entry.sign_in"/></h2>

                            <p class="text-danger"> ${sign_in_msg}</p>

                            <form action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="sign_in"/>
                                <div class="form-outline mb-4">
                                    <label class="form-label"><fmt:message key="entry.login"/></label>
                                    <input type="text" name="login" class="form-control form-control-lg"/>
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label"><fmt:message key="entry.password"/></label>
                                    <input type="password" name="password" class="form-control form-control-lg"/>
                                </div>


                                <div class="d-flex justify-content-center">
                                    <input class="btn btn-success btn-block btn-lg gradient-custom-4 text-body"
                                           type="submit" name="submit" value="<fmt:message key="entry.sign_in"/>"/>
                                </div>

                                <p class="text-center text-muted mt-5 mb-0"><fmt:message
                                        key="entry.do_not_have_account"/> <a
                                        href="${pageContext.request.contextPath}/pages/entry/sign_up.jsp"
                                        class="fw-bold text-body"><u><fmt:message key="entry.sign_up_here"/></u></a></p>

                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<br/>

<jsp:include page="/pages/parts/footer.jsp"/>
</body>
</html>
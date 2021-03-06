<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Library</title>
</head>
<body>

<c:if test="${requestScope.invalid_locale}">
    <p class="text-danger"><fmt:message key="invalid_locale"/></p>
</c:if>

<h1><fmt:message key="index.welcome"/></h1>
<h2><fmt:message key="index.info"/></h2>

<br/>
<br/>


<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>
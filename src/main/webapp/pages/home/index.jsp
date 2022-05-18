<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="i18n/language"/>

<!DOCTYPE html>
<html>
<head>
    <title>Library</title>
</head>
<body>
<header>
    <jsp:include page="/pages/parts/header.jsp"/>
</header>
<%--            ${pageContext.session.id}--%   todo use session>
//todo add locale to jsp
--%>

<br/>
<footer>
    <jsp:include page="/pages/parts/footer.jsp"/>
</footer>
</body>
</html>
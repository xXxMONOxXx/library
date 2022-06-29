<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/pages/parts/header.jsp" %>

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
</body>
</html>

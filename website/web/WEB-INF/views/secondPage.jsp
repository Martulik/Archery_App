<jsp:useBean id="studentJSP" scope="request" type="spring.web.ProfileController"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Second Page</title>
</head>
<body>
Введенный логин: ${studentJSP.email};
<br/>
Введенный пароль: ${studentJSP.password};
<br/>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Hp
  Date: 02.05.2022
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>

<spring: method="get"  modelAttribute="studentNameJSP">

    Name: <spring:input path="email"/> <br/>
    Password: <spring:input path="password"/>   <br/>
    <spring:button>Next Page</spring:button>

</spring:>

</body>
</html>

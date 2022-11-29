<%--
  Created by IntelliJ IDEA.
  User: Tom
  Date: 29/11/2022
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Todo list</title>
    </head>
    <body>
        <c:if test="${!empty sessionScope.username && !empty sessionScope.role}">
            <h1>${sessionScope.username} as ${sessionScope.role}</h1>
        </c:if>

        <form method="POST" action="todos">
            <input type="submit" name="action" value="Logout">
        </form>
    </body>
</html>

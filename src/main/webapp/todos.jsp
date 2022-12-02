<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Todo list</title>
    </head>
    <body>
        <div class="container">
            <c:if test="${!empty sessionScope.username && !empty sessionScope.role}">
                <h1>${sessionScope.username} as ${sessionScope.role}</h1>
                <c:if test="${sessionScope.role == \"instructor\"}">
                    <form method="GET" action="todo-form">
                        <input type="submit" name="action" value="Add">
                    </form>
                </c:if>
            </c:if>
            <form method="POST" action="todos">
                <input type="submit" name="action" value="Logout">
            </form>
        </div>
    </body>
</html>

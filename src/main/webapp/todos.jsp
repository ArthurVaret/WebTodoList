<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String message = (String) request.getAttribute("message"); %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Todo list</title>
        <link type="text/css" rel="stylesheet" href="style/main.css">
    </head>
    <body>
        <div class="container">
            <c:if test="${!empty sessionScope.username && !empty sessionScope.role}">
                <h1>${sessionScope.username} as ${sessionScope.role}</h1>
                <table>
                    <tr>
                        <th>Id </th>
                        <th>Description</th>
                        <c:if test="${sessionScope.role == \"instructor\"}">
                        <th>Edit</th>
                        <th>Delete</th>
                        </c:if>
                    </tr>
                    <c:forEach var="todo" items="${todos}">
                        <tr>
                            <td>${todo.id}</td>
                            <td>${todo.description}</td>
                            <c:if test="${sessionScope.role == \"instructor\"}">
                            <td>
                                <form method="POST" action="todos">
                                    <input class="button orange" type="submit" name="action" value="Edit" >
                                    <input type="hidden" name="id" value="${todo.id}">
                                </form>
                            </td>
                            <td>
                                <form method="POST" action="todos">
                                    <input class="button red" type="submit" name="action" value="Delete" >
                                    <input type="hidden" name="id" value="${todo.id}">
                                </form>
                            </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>

                <c:if test="${sessionScope.role == \"instructor\"}">
                    <form method="POST" action="todos">
                        <input class="button green" type="submit" name="action" value="Add">
                    </form>
                </c:if>
            </c:if>
            <form method="POST" action="todos">
                <input class="button" type="submit" name="action" value="Logout">
            </form>
        </div>
    </body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String username = (String) request.getAttribute("username"); %>
<!DOCTYPE html>
<html>
    <head>
        <title>WebTodoList</title>
    </head>
    <body>

        <div class="container">
            <h1>Login</h1>
            <div>
                <form method="POST" action="login">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="username" value="${username}">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="password">
                    <input type="submit" name="submit" value="Login">
                </form>
                <a href="login">Register</a>
                <h2>${message}</h2>
            </div>
        </div>
    </body>
</html>
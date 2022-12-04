<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String username = (String) request.getAttribute("username"); %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login - WebTodoList</title>
        <link type="text/css" rel="stylesheet" href="style/main.css">
    </head>
    <body>

        <div class="container">
            <h1>Login</h1>
            <div>
                <form method="POST" action="login">
                    <div class="row">
                        <label for="username">Username</label>
                        <input class="text-input" type="text" id="username" name="username" placeholder="username" value="${username}">
                    </div>
                    <div class="row">
                        <label for="password">Password</label>
                        <input class="text-input" type="password" id="password" name="password" placeholder="password">
                    </div>
                    <input class="button" type="submit" name="submit" value="Login">
                </form>
                <a class="link" href="register">>Register</a>
                <h2>${message}</h2>
            </div>
        </div>
    </body>
</html>
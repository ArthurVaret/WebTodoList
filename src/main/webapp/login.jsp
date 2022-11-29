<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String username = (String) request.getAttribute("username"); %>
<!DOCTYPE html>
<html>
    <head>
        <title>WebTodoList</title>
    </head>
    <body>
        <h1>Login</h1>
        <br/>
        <div>
            <form method="POST" action="login">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" placeholder="username" value="<%=username%>">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="password">
                <input type="submit" name="submit" value="Login">
            </form>
            <h2>${message}</h2>
        </div>
    </body>
</html>
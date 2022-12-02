<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Register - WebTodoList</title>
        <link type="text/css" rel="stylesheet" href="style/main.css">
    </head>
    <body>
        <div class="container">
            <h1>Register</h1>
            <div class="form">
                <form method="POST" action="register">
                    <fieldset>
                        <legend> Role </legend>
                        <label for="instructor"> Instructor </label>
                        <input type="radio" id="instructor" name="role" value="instructor">
                        <label for="student"> Student </label>
                        <input type="radio" id="student" name="role" value="student">
                    </fieldset>
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="username">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="password">
                    <label for="confirm">Confirm password</label>
                    <input type="password" id="confirm" name="confirm-password" placeholder="confirm your password">
                    <input type="submit" name="submit" value="Register">
                </form>
                <a class="link" href="login">>Login</a>
                <h2>${message}</h2>
            </div>
        </div>
    </body>
</html>